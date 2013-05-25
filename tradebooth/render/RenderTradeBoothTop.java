package tradebooth.render;

import tradebooth.block.BlockTradeBoothTop;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.ModLoader;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public class RenderTradeBoothTop{

	public static boolean renderTradeBoothTop( IBlockAccess blockAccess, Block block, int x, int y, int z, RenderBlocks renderBlocks ){
		
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness( blockAccess.getLightBrightnessForSkyBlocks( x, y, z, 0 ) );
		
		//Just a stick in each corner
		renderBlocks.setRenderBounds( 0.1F, .0F, 0.1F, 0.02F, 0.98F, 0.02F );
		tessellator.setColorOpaque_F( 0.82F, 0.82F, 0.82F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconTop );

		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.setRenderBounds( 0.98F, .0F, 0.1F, 0.9F, 0.98F, 0.02F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconTop );

		renderBlocks.setRenderBounds( 0.98F, .0F, 0.98F, 0.9F, 0.98F, 0.9F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconTop );

		renderBlocks.setRenderBounds( 0.1F, .0F, 0.98F, 0.02F, 0.98F, 0.9F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		
		//Top platform
		renderBlocks.setRenderBounds( 0.0F, .9F, 0.0F, 0.1F, 1.0F, 1.0F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(0.9F, 0.9F, 0.9F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		
		renderBlocks.setRenderBounds( 0.9F, .9F, 0.0F, 1.0F, 1.0F, 1.0F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(0.9F, 0.9F, 0.9F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconTop );

		renderBlocks.setRenderBounds( 0.1F, .9F, 0.9F, 0.9F, 1.0F, 1.0F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(0.9F, 0.9F, 0.9F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconTop );

		renderBlocks.setRenderBounds( 0.1F, .9F, 0.0F, 0.9F, 1.0F, 0.1F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		tessellator.setColorOpaque_F(0.9F, 0.9F, 0.9F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconTop );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconTop );
		
		return true;
	}
}
