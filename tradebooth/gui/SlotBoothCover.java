package tradebooth.gui;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBoothCover extends Slot{

	public SlotBoothCover(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid( ItemStack itemStack ){
		return itemStack.itemID == Block.cloth.blockID;
	}
	@Override
	public int getSlotStackLimit(){
		return 1;
	}
}
