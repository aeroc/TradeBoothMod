package tradebooth.container;

import tradebooth.gui.SlotImmovable;
import tradebooth.tileentity.TileEntityTradeBoothStorage;
import tradebooth.tileentity.TileEntityTradeBoothTop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTradeBoothTopNonOwner extends Container{

	protected TileEntityTradeBoothTop tileEntity;
	public static final int SLOTS_WIDE = 4;
	public static final int SLOTS_TALL = 4;
	
	public ContainerTradeBoothTopNonOwner( InventoryPlayer inventoryPlayer, TileEntityTradeBoothTop tileEntity ){
		this.tileEntity = tileEntity;
		
		for( int y = 0; y < SLOTS_TALL; y++ ){
			for( int x = 0; x < SLOTS_WIDE; x++ ){
				this.addSlotToContainer( new SlotImmovable( this.tileEntity, x + y * SLOTS_WIDE, 26 + x * 36, 10 + y * 21 ) );
			}
		}
		this.bindPlayerInventory( inventoryPlayer );
	}
	
	@Override
	public boolean canInteractWith( EntityPlayer player ){
		return tileEntity.isUseableByPlayer( player );
	}
	
	protected void bindPlayerInventory( InventoryPlayer inventoryPlayer ){
		for( int y = 0; y < 3; y++ ){
			for( int x = 0; x < 9; x++ ){
				this.addSlotToContainer( new Slot( inventoryPlayer, x + y * 9 + 9, 8 + x * 18, 96 + y * 18 ) );
			}
		}
		for( int i = 0; i < 9; i++ ){
			this.addSlotToContainer( new Slot( inventoryPlayer, i, 8 + i * 18, 154 ) );
		}
	}
	@Override
	public ItemStack transferStackInSlot( EntityPlayer player, int inventorySlot ){
		ItemStack itemStack = null;
		Slot slot = (Slot) this.inventorySlots.get( inventorySlot );
		return itemStack;
	}
	@Override
	public ItemStack slotClick(int slotIndex, int mouseButtonID, int shiftClick, EntityPlayer entityPlayer){
		//mouseButtonID 0 = left click
		//mouseButtonID 1 = right click
		//mouseButtonID 2 = center/scroll wheel click, also makes shiftClick = 3
		
		if( !entityPlayer.worldObj.isRemote ){ //Server
			
			if( mouseButtonID == 0 ){ //If left click
				if( slotIndex % 2 == 1 && slotIndex < this.totalSlotsInContainer() ){ //If the left-click was on a buy slot
					
					Slot buyingSlot = (Slot) this.inventorySlots.get( slotIndex );
					Slot priceSlot = (Slot) this.inventorySlots.get( slotIndex - 1 );
					
					ItemStack buyingStack = buyingSlot.getStack();
					ItemStack priceStack = priceSlot.getStack();
					
					if( buyingStack != null && priceStack != null ){ //If neither of the stacks are null
					
						//Check if the player has room to buy something
						if( this.canPlayerAccept( buyingStack, entityPlayer.inventory ) ){
							
							//Check to see if the player has the price to complete this transaction
							if( this.canPlayerProvide( priceStack ) ){ //If the player's inventory has enough to complete a purchase stack
								
								//Check to see if the TradeBoothTop is connected to a TradeBoothStorage that is owned by the same player
								if( this.tileEntity.isConnectedToStorageAndSameOwner( entityPlayer.worldObj ) ){
									
									//Check to see if the TradeBoothStorage has room to buy the priceStack
									//Check to see if the TradeBoothStorage has enough of the buyingStack to sell to the player
									if( this.tileEntity.canConnectedStorageBuy( priceStack, entityPlayer.worldObj ) && this.tileEntity.canConnectedStorageSell( buyingStack, entityPlayer.worldObj ) ){
										
										//Make the transaction!
										
										//Move the price item from player inventory to booth storage
										
										this.removeFromPlayer( entityPlayer, priceStack );
										this.addToStorage( this.tileEntity.getConnectedTileEntityStorage( entityPlayer.worldObj ), priceStack );
										
										//Move the buying item from booth storage to player inventory
										
										this.removeFromStorage( this.tileEntity.getConnectedTileEntityStorage( entityPlayer.worldObj ), buyingStack );
										this.addToPlayer( entityPlayer, buyingStack );
									}
									else{
										entityPlayer.sendChatToPlayer( "Storage is full or sold out." );
									}
								}
								else{
									entityPlayer.sendChatToPlayer( "A valid storage block is not connected to booth top." );
								}
							}
							else{
								entityPlayer.sendChatToPlayer( "You do not have enough items for this transaction." );
							}
						}
						else{
							entityPlayer.sendChatToPlayer( "Your inventory is too full for this transaction." );
						}
					}
					else{
//						if( entityPlayer.worldObj.isRemote ){
//							System.out.println( "Client: One or both of the slots for this transaction are empty/null." );
//						}
//						else{
//							System.out.println( "Server: One or both of the slots for this transaction are empty/null." );
//						}
					}
				}
			}
		}
		
		return null;
	}

	public int totalSlotsInContainer(){
		return SLOTS_WIDE * SLOTS_TALL;
	}
	public int totalSlotsInPlayer(){
		return 36;
	}
	public boolean canPlayerAccept( ItemStack itemStack, InventoryPlayer inventoryPlayer ){
		int emptySlotIndex = inventoryPlayer.getFirstEmptyStack();
		if( emptySlotIndex >= 0 ){
			return true;
		}
		else{
			int count = 0; //Count the amount of free space in the player's stacks of the same item type as the itemStack provided to this method
			for( int i = this.totalSlotsInContainer(); i < this.totalSlotsInContainer() + this.totalSlotsInPlayer(); i++ ){
				ItemStack playerStack = (ItemStack) this.inventoryItemStacks.get( i );
				if( playerStack != null && playerStack.getItem() == itemStack.getItem() ){
					if( playerStack.stackSize < playerStack.getItem().getItemStackLimit() ){
						//If the stacksize is less than the item's stacklimit
						//This check is done in case the stackSize was forced above its limit (can be done in creative mode)
						count += playerStack.getItem().getItemStackLimit() - playerStack.stackSize;
					}
				}
			}
			if( count >= itemStack.stackSize ){
				return true;
			}
		}
		return false;
	}
	public boolean canPlayerProvide( ItemStack priceStack ){
		int count = 0;
		for( int i = this.totalSlotsInContainer(); i < this.totalSlotsInContainer() + this.totalSlotsInPlayer(); i++ ){
			Slot playerSlot = (Slot) this.inventorySlots.get( i );
			ItemStack playerStack = (ItemStack) playerSlot.getStack();
			if( playerStack != null && playerStack.isItemEqual( priceStack ) ){ //If we found a stack that is the same as the priceStack
				count += playerStack.stackSize;
			}
		}
		if( count >= priceStack.stackSize ){
			return true;
		}
		else{
			return false;
		}
	}
	public void removeFromPlayer( EntityPlayer entityPlayer, ItemStack removeStack ){
		int numberToRemove = removeStack.stackSize;
		for( int i = 0; i < entityPlayer.inventory.getSizeInventory() && numberToRemove > 0; i++ ){
			ItemStack checkStack = entityPlayer.inventory.getStackInSlot( i );
			if( checkStack != null && checkStack.isItemEqual( removeStack ) ){
				if( checkStack.stackSize > numberToRemove ){ //If the player has more than enough in this stack to complete the removal
					checkStack.stackSize -= numberToRemove;
					numberToRemove = 0;
					entityPlayer.onUpdate();
				}
				else if( checkStack.stackSize <= removeStack.stackSize ){ //If this checkStack has less than or just enough of the removeStack amount
					numberToRemove -= checkStack.stackSize; //Decrement the amount we're looking for
					entityPlayer.inventory.setInventorySlotContents( i , null ); //And nullify this stack
					entityPlayer.onUpdate();
				}
			}
		}
	}
	public void removeFromStorage( TileEntityTradeBoothStorage storage, ItemStack removeStack ){
		storage.removeStack( removeStack );
	}
	public void addToStorage( TileEntityTradeBoothStorage storage, ItemStack addStack ){
		storage.addToStorage( addStack );
	}
	private void addToPlayer(EntityPlayer entityPlayer, ItemStack buyingStack) {
		int numberToAdd = buyingStack.stackSize;
		//Try to add the quantity in the addStack to existing itemStacks of the same kind
			for( int i = 0; i < entityPlayer.inventory.getSizeInventory() && numberToAdd > 0; i++ ){
				ItemStack itemStack = entityPlayer.inventory.getStackInSlot( i );
				if( itemStack != null && itemStack.isItemEqual( buyingStack ) ){
					if( itemStack.stackSize < itemStack.getItem().getItemStackLimit() ){
						if( itemStack.getItem().getItemStackLimit() - itemStack.stackSize >= numberToAdd ){ //If we have enough room in this stack to completely use up the numberToAdd
							itemStack.stackSize += numberToAdd;
							numberToAdd = 0;
							entityPlayer.onUpdate();
						}
						else{
							numberToAdd -= itemStack.getItem().getItemStackLimit() - itemStack.stackSize;
							itemStack.stackSize = itemStack.getItem().getItemStackLimit();
							entityPlayer.onUpdate();
						}
					}
				}
			}
			if( numberToAdd > 0 ){ //If there are still items to add after trying to fill same-type itemStacks
				for( int i = 0; i < entityPlayer.inventory.getSizeInventory() && numberToAdd > 0; i++ ){
					ItemStack itemStack = entityPlayer.inventory.getStackInSlot( i );
					if( itemStack == null ){ //we're looking for null/empty itemStacks
						ItemStack newItemStack = buyingStack.copy();
						newItemStack.stackSize = numberToAdd;
						entityPlayer.inventory.addItemStackToInventory( newItemStack );
						numberToAdd = 0;
						entityPlayer.onUpdate();
					}
				}
			}
		
	}
}