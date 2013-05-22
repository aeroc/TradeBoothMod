package mods.tradebooth.handler;

import mods.tradebooth.TradeBoothMod;
import mods.tradebooth.render.RenderTradeBoothTop;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRenderHandler implements ISimpleBlockRenderingHandler{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if( modelID == TradeBoothMod.BoothTopRenderID ){
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if( modelId == TradeBoothMod.BoothTopRenderID ){
			return RenderTradeBoothTop.renderTradeBoothTop( block, x, y, z, renderer );
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return 0;
	}

}