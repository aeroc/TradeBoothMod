package mods.tradebooth.render;

import mods.tradebooth.block.BlockTradeBoothTop;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

public class RenderTradeBoothTop {

	public static boolean renderTradeBoothTop( Block block, int x, int y, int z, RenderBlocks renderBlocks ){

		renderBlocks.renderBottomFace( block, (double) x, ( (double) y + 0.9f ), (double) z, block.getBlockTextureFromSide( 0 ) );
		//Just a stick in each corner
		renderBlocks.setRenderBounds( 0.1F, .0F, 0.1F, 0.02F, 1.0F, 0.02F );
		renderBlocks.renderStandardBlockWithColorMultiplier( block, x, y, z, 1, 1, 1 );
		
		renderBlocks.setRenderBounds( 0.98F, .0F, 0.98F, 0.9F, 1.0F, 0.02F );
		renderBlocks.renderStandardBlockWithColorMultiplier( block, x, y, z, 1, 1, 1 );
		
		renderBlocks.setRenderBounds( 0.98F, .0F, 0.98F, 0.9F, 1.0F, 0.9F );
		renderBlocks.renderStandardBlockWithColorMultiplier( block, x, y, z, 1, 1, 1 );
		
		renderBlocks.setRenderBounds( 0.1F, .0F, 0.98F, 0.02F, 1.0F, 0.9F );
		renderBlocks.renderStandardBlockWithColorMultiplier( block, x, y, z, 1, 1, 1 );
		
		//Booth top
		renderBlocks.overrideBlockTexture = BlockTradeBoothTop.iconTop;
		renderBlocks.setRenderBounds( 0.9F, 0.9F, 0.0F, 1.0F, 1.0F, 1.0F );
		renderBlocks.renderStandardBlock( block, x, y, z );
		renderBlocks.setRenderBounds( 0.0F, 0.9F, 0.0F, 0.1F, 1.0F, 1.0F );
		renderBlocks.renderStandardBlock( block, x, y, z );
		renderBlocks.setRenderBounds( 0.0F, 0.9F, 0.0F, 1.0F, 1.0F, 0.1F );
		renderBlocks.renderStandardBlock( block, x, y, z );
		renderBlocks.setRenderBounds( 0.0F, 0.9F, 0.9F, 1.0F, 1.0F, 1.0F );
		renderBlocks.renderStandardBlock( block, x, y, z );
		renderBlocks.clearOverrideBlockTexture();
		
		return true;
	}
}
