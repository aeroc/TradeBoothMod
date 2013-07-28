package tradebooth.ItemBlock;

import tradebooth.TradeBoothMod;
import tradebooth.block.BlockTradeBoothStorage;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockTradeBoothStorage extends ItemBlock{

	public ItemBlockTradeBoothStorage(int par1) {
		super(par1);
		this.setHasSubtypes( true );
	}
	@Override
	public int getMetadata( int meta ){
		return meta;
	}
	@Override
	public String getUnlocalizedName( ItemStack itemStack ){
		return TradeBoothMod.blockTradeBoothStorage.getUnlocalizedName() + BlockTradeBoothStorage.woodType[itemStack.getItemDamage()];
	}
}
