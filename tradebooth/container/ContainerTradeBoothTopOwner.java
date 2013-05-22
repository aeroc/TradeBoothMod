package mods.tradebooth.container;

import mods.tradebooth.tileentity.TileEntityTradeBoothTop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTradeBoothTopOwner extends Container{

	protected TileEntityTradeBoothTop tileEntity;
	public static final int SLOTS_WIDE = 4;
	public static final int SLOTS_TALL = 4;
	
	public ContainerTradeBoothTopOwner( InventoryPlayer inventoryPlayer, TileEntityTradeBoothTop tileEntity ){
		this.tileEntity = tileEntity;
		
		for( int y = 0; y < SLOTS_TALL; y++ ){
			for( int x = 0; x < SLOTS_WIDE; x++ ){
				this.addSlotToContainer( new Slot( this.tileEntity, x + y * SLOTS_WIDE, 26 + x * 36, 10 + y * 21 ) );
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
		
		if( slot != null && slot.getHasStack() ){
			ItemStack itemStackInSlot = slot.getStack();
			itemStack = itemStackInSlot.copy();
			
			if( inventorySlot < this.totalSlotsInContainer() ){
				if( !this.mergeItemStack( 	itemStackInSlot,
											this.totalSlotsInContainer(),
											this.totalSlotsInContainer() + this.totalSlotsInPlayer(),
											true ) ){
					return null;
				}
			}
			else if( !this.mergeItemStack( itemStackInSlot, 0, this.totalSlotsInContainer(), false ) ){
				return null;
			}
			
			if( itemStackInSlot.stackSize == 0 ){
				slot.putStack( null );
			}
			else{
				slot.onSlotChanged();
			}
			
			if( itemStackInSlot.stackSize == itemStack.stackSize ){
				return null;
			}
			slot.onPickupFromSlot( player, itemStackInSlot );
		}
		return itemStack;
	}
	public int totalSlotsInContainer(){
		return SLOTS_WIDE * SLOTS_TALL;
	}
	public int totalSlotsInPlayer(){
		return 36;
	}
}