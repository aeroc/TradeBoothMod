package tradebooth.handler;

import tradebooth.TradeBoothMod;
import tradebooth.render.RenderTradeBoothTop;
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
	public boolean renderWorldBlock( IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderBlocks ){
		if( modelId == TradeBoothMod.BoothTopRenderID ){
			return RenderTradeBoothTop.renderTradeBoothTop( blockAccess, block, x, y, z, renderBlocks );
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