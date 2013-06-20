package tradebooth.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import tradebooth.handler.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;

public class TileEntityTradeBoothStorage extends TileNetworkEntity implements IInventory{
	
	private ItemStack[] inventory;
	private String playerOwner;
	public boolean providePower = false;

	public TileEntityTradeBoothStorage(){
		this.inventory = new ItemStack[36];
		this.playerOwner = "";
	}
	
	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot( int inventorySlot ) {
		return this.inventory[inventorySlot];
	}

	@Override
	public ItemStack decrStackSize( int inventorySlot, int amount ) {
		ItemStack itemStack = this.getStackInSlot( inventorySlot );
		if( itemStack != null ){
			if( itemStack.stackSize <= amount ){
				this.setInventorySlotContents( inventorySlot, null );
			}
			else{
				itemStack = itemStack.splitStack( amount );
				if( itemStack.stackSize == 0 ){
					this.setInventorySlotContents( inventorySlot, null );
				}
			}
		}
		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing( int inventorySlot ) {
		ItemStack itemStack = getStackInSlot( inventorySlot );
		if( itemStack != null ){
			this.setInventorySlotContents( inventorySlot, null );
		}
		return itemStack;
	}

	@Override
	public void setInventorySlotContents( int inventorySlot, ItemStack itemStack ) {
		this.inventory[inventorySlot] = itemStack;
		if( itemStack != null && itemStack.stackSize > this.getInventoryStackLimit() ){
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "tradebooth.tileentitytradeboothstorage";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer( EntityPlayer player ) {
		return 	this.worldObj.getBlockTileEntity( this.xCoord, this.yCoord, this.zCoord ) == this &&
				player.getDistanceSq( xCoord + 0.5, yCoord + 0.5, zCoord + 0.5 ) < 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
	@Override
	public void readFromNBT( NBTTagCompound tagCompound ){
		super.readFromNBT( tagCompound );
		
		NBTTagList tagList = tagCompound.getTagList( "Inventory" );
		for( int i = 0; i < tagList.tagCount(); i++ ){
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt( i );
			byte slot = tag.getByte( "Slot" );
			if( slot >= 0 && slot < this.inventory.length ){
				this.inventory[slot] = ItemStack.loadItemStackFromNBT( tag );
			}
		}
		this.playerOwner = tagCompound.getString( "PlayerOwner" );
	}
	@Override
	public void writeToNBT( NBTTagCompound tagCompound ){
		super.writeToNBT( tagCompound );
		
		NBTTagList tagList = new NBTTagList();
		for( int i = 0; i < this.inventory.length; i++ ){
			ItemStack itemStack = this.inventory[i];
			if( itemStack != null ){
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte( "Slot", (byte) i );
				itemStack.writeToNBT( tag );
				tagList.appendTag( tag );
			}
		}
		tagCompound.setTag( "Inventory", tagList );
		tagCompound.setString( "PlayerOwner", this.playerOwner );
	}
	public String getPlayerOwner(){
		return this.playerOwner;
	}
	public void setPlayerOwner( String newOwner ){
		this.playerOwner = newOwner;
	}
	@Override
	public void sendTileEntityDataPacketToPlayer( Player destinationPlayer ){
		if( this.playerOwner != null && !this.playerOwner.isEmpty() ){
			//I made this check in case I decide that ownership only takes place on the initial block activation instead of on placement
			
			PacketDispatcher.sendPacketToPlayer( this.createTileEntityDataPacket(), destinationPlayer );
		}
	}
	@Override
	public void sendTileEntityDataPacketToNearbyPlayers( int dimensionID ){
		if( this.playerOwner != null && !this.playerOwner.isEmpty() ){
			PacketDispatcher.sendPacketToAllAround( this.xCoord, this.yCoord, this.zCoord, 220, dimensionID, this.createTileEntityDataPacket() );
		}
	}
	@Override
	public Packet250CustomPayload createTileEntityDataPacket(){
		int dataLength = 13 + this.getPlayerOwner().length(); // 13 = 1 byte + 3 int
		ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream( dataLength );
		DataOutputStream outputStream = new DataOutputStream( byteArrayStream );
		
		try{
			outputStream.writeByte( PacketHandler.PACKET_RECEIVE_PLAYER_NAME );
			outputStream.writeInt( this.xCoord );
			outputStream.writeInt( this.yCoord );
			outputStream.writeInt( this.zCoord );
			outputStream.writeInt( this.getPlayerOwner().length() ); //The length of the owner's name
			outputStream.writeBytes( this.getPlayerOwner() );
		}
		catch( Exception e){
			e.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "TradeBooth";
		packet.data = byteArrayStream.toByteArray();
		packet.length = byteArrayStream.size();
		return packet;
	}
	public boolean canAcceptItemStack( ItemStack itemStack ){
		boolean hasEmptyInventoryStack = this.hasEmptyInventoryStack();
		if( !hasEmptyInventoryStack ){ //If an empty itemstack was not found
			//Check for space among same-itemstacks with enough empty space
			int count = 0;
			for( int i = 0; i < this.getSizeInventory(); i++ ){
				ItemStack checkStack = this.getStackInSlot( i );
				if( checkStack != null && checkStack.isItemEqual( itemStack ) ){
					if( checkStack.stackSize <= checkStack.getItem().getItemStackLimit() ){
						//This previous if statement is in case the stack was forced to accept a stackSize large than its limit (creative mode can do this)
						count += checkStack.getItem().getItemStackLimit() - checkStack.stackSize;
					}
				}
			}
			if( count >= itemStack.stackSize ){ //If we counted enough empty spaces in all the itemstacks of the same type
				return true;
			}
		}
		else{ //If an empty itemstack was found
			return true;
		}
		return false;
	}
	public boolean canProvideItemStack( ItemStack itemStack ){
		int count = 0;
		for( int i = 0; i < this.getSizeInventory(); i++ ){
			ItemStack checkStack = this.getStackInSlot( i );
			if( checkStack != null && checkStack.isItemEqual( itemStack ) ){
				count += checkStack.stackSize;
			}
		}
		if( count >= itemStack.stackSize ){
			return true;
		}
		return false;
	}
	public boolean hasEmptyInventoryStack(){
		//Searches the storage for a single empty storage stack.
		for( int i = 0; i < this.getSizeInventory(); i++ ){
			ItemStack itemStack = this.getStackInSlot( i );
			if( itemStack == null ){
				return true;
			}
		}
		return false;
	}
	public void addToStorage( ItemStack addStack ){
		int numberToAdd = addStack.stackSize;
		//Try to add the quantity in the addStack to existing itemStacks of the same kind
		for( int i = 0; i < this.getSizeInventory() && numberToAdd > 0; i++ ){
			ItemStack itemStack = this.getStackInSlot( i );
			if( itemStack != null && itemStack.isItemEqual( addStack ) ){
				if( itemStack.stackSize < itemStack.getItem().getItemStackLimit() ){
					if( itemStack.getItem().getItemStackLimit() - itemStack.stackSize >= numberToAdd ){ //If we have enough room in this stack to completely use up the numberToAdd
						itemStack.stackSize += numberToAdd;
						numberToAdd = 0;
						this.updateEntity();
					}
					else{
						numberToAdd -= itemStack.getItem().getItemStackLimit() - itemStack.stackSize;
						itemStack.stackSize = itemStack.getItem().getItemStackLimit();
						this.updateEntity();
					}
				}
			}
		}
		if( numberToAdd > 0 ){ //If there are still items to add after trying to fill same-type itemStacks
			for( int i = 0; i < this.getSizeInventory() && numberToAdd > 0; i++ ){
				ItemStack itemStack = this.getStackInSlot( i );
				if( itemStack == null ){ //we're looking for null/empty itemStacks
					ItemStack newItemStack = addStack.copy();
					newItemStack.stackSize = numberToAdd;
					this.setInventorySlotContents( i , newItemStack );
					numberToAdd = 0;
					this.updateEntity();
				}
			}
		}
	}
	public int countNullItemStacks(){
		int count = 0;
		for( int i = 0; i < this.getSizeInventory(); i++ ){
			if( this.getStackInSlot( i ) == null ){
				count++;
			}
		}
		return count;
	}

	public void removeStack(ItemStack removeStack) {
		int numberToRemove = removeStack.stackSize;
		for( int i = 0; i < this.getSizeInventory(); i++ ){
			ItemStack checkStack = this.getStackInSlot( i );
			if( checkStack != null && checkStack.isItemEqual( removeStack ) ){
				if( checkStack.stackSize > numberToRemove ){ //If the player has more than enough in this stack to complete the removal
					checkStack.stackSize -= numberToRemove;
					numberToRemove = 0;
					this.updateEntity();
				}
				else if( checkStack.stackSize <= removeStack.stackSize ){ //If this checkStack has less than or just enough of the removeStack amount
					numberToRemove -= checkStack.stackSize; //Decrement the amount we're looking for
					this.setInventorySlotContents( i , null ); //And nullify this stack
					this.updateEntity();
				}
			}
		}
		
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}
}