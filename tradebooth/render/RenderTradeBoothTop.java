package tradebooth.render;

import tradebooth.block.BlockTradeBoothTop;
import tradebooth.tileentity.TileEntityTradeBoothTop;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public class RenderTradeBoothTop{

	public static boolean renderTradeBoothTop( IBlockAccess blockAccess, Block block, int x, int y, int z, RenderBlocks renderBlocks ){
		
		int meta = renderBlocks.blockAccess.getBlockMetadata( x, y, z );
		
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness( blockAccess.getLightBrightnessForSkyBlocks( x, y, z, 0 ) );
		
		//Just a stick in each corner
		renderBlocks.setRenderBounds( 0.1F, .0F, 0.1F, 0.02F, 0.96F, 0.02F );
		tessellator.setColorOpaque_F( 0.82F, 0.82F, 0.82F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );

		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.setRenderBounds( 0.98F, .0F, 0.1F, 0.9F, 0.96F, 0.02F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );

		renderBlocks.setRenderBounds( 0.98F, .0F, 0.98F, 0.9F, 0.96F, 0.9F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );

		renderBlocks.setRenderBounds( 0.1F, .0F, 0.98F, 0.02F, 0.96F, 0.9F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		
		//Top platform
		renderBlocks.setRenderBounds( 0.01F, .9F, 0.01F, 0.1F, 0.99F, 0.99F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(0.9F, 0.9F, 0.9F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		
		renderBlocks.setRenderBounds( 0.9F, .9F, 0.01F, 0.99F, 0.99F, 0.99F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(0.9F, 0.9F, 0.9F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );

		renderBlocks.setRenderBounds( 0.1F, .9F, 0.9F, 0.9F, 0.99F, 0.99F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(0.9F, 0.9F, 0.9F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );

		renderBlocks.setRenderBounds( 0.1F, .9F, 0.01F, 0.9F, 0.99F, 0.1F );
		tessellator.setColorOpaque_F(0.75F, 0.75F, 0.75F);
		renderBlocks.renderFaceXNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceXPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
		renderBlocks.renderFaceYNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlocks.renderFaceYPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		tessellator.setColorOpaque_F(0.9F, 0.9F, 0.9F);
		renderBlocks.renderFaceZNeg( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		renderBlocks.renderFaceZPos( block, x, y, z, BlockTradeBoothTop.iconArray[meta] );
		
		TileEntityTradeBoothTop tileEntity = (TileEntityTradeBoothTop) renderBlocks.blockAccess.getBlockTileEntity( x, y, z );
		ItemStack coverItemStack = tileEntity.getStackInSlot( 16 );
		if( coverItemStack != null && coverItemStack.itemID == Block.cloth.blockID ){
			int woolMeta = coverItemStack.getItemDamage();		
		
			//Top cover
			renderBlocks.setRenderBounds( 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F );
			tessellator.setColorOpaque_F( 0.75F, 0.75F, 0.75F );
			renderBlocks.renderFaceYPos( block, x, y, z, Block.cloth.getIcon( 0, woolMeta ) );
			renderBlocks.renderFaceYNeg( block, x, y, z, Block.cloth.getIcon( 0, woolMeta ) );
			
			//Side flaps (cover)
			renderBlocks.setRenderBounds( 0.0F, 1.0F, 1.0F, 1.0F, 0.75F, 1.0F ); //south flap
			tessellator.setColorOpaque_F( 0.75F, 0.75F, 0.75F );
			renderBlocks.renderFaceZPos( block, x, y, z, Block.cloth.getIcon( 0, woolMeta ) );
			renderBlocks.renderFaceZNeg( block, x, y, z, Block.cloth.getIcon( 0, woolMeta ) );
			
			renderBlocks.setRenderBounds( 0.0F, 1.0F, 0.0F, 1.0F, 0.75F, 0.0F ); //north flap
			tessellator.setColorOpaque_F( 0.75F, 0.75F, 0.75F );
			renderBlocks.renderFaceZPos( block, x, y, z, Block.cloth.getIcon( 0, woolMeta ) );
			renderBlocks.renderFaceZNeg( block, x, y, z, Block.cloth.getIcon( 0, woolMeta ) );
			
			renderBlocks.setRenderBounds( 0.0F, 1.0F, 0.0F, 0.0F, 0.75F, 1.0F ); //west flap
			tessellator.setColorOpaque_F( 0.75F, 0.75F, 0.75F );
			renderBlocks.renderFaceXPos( block, x, y, z, Block.cloth.getIcon( 0, woolMeta ) );
			renderBlocks.renderFaceXNeg( block, x, y, z, Block.cloth.getIcon( 0, woolMeta ) );
			
			renderBlocks.setRenderBounds( 1.0F, 1.0F, 0.0F, 1.0F, 0.75F, 1.0F ); //east flap
			tessellator.setColorOpaque_F( 0.75F, 0.75F, 0.75F );
			renderBlocks.renderFaceXPos( block, x, y, z, Block.cloth.getIcon( 0, woolMeta ) );
			renderBlocks.renderFaceXNeg( block, x, y, z, Block.cloth.getIcon( 0, woolMeta ) );
		}
		
		return true;
	}
}
