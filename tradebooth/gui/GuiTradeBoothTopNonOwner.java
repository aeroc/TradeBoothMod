package mods.tradebooth.gui;

import org.lwjgl.opengl.GL11;


import mods.tradebooth.CommonProxy;
import mods.tradebooth.container.ContainerTradeBoothTopNonOwner;
import mods.tradebooth.tileentity.TileEntityTradeBoothTop;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiTradeBoothTopNonOwner extends GuiContainer{

	private TileEntityTradeBoothTop tileEntity;
	
	public GuiTradeBoothTopNonOwner( InventoryPlayer inventoryPlayer, TileEntityTradeBoothTop tileEntity ){
		super( new ContainerTradeBoothTopNonOwner( inventoryPlayer, tileEntity ) );
		this.tileEntity = tileEntity;
	}
	public String getOwnerName(){
		if( this.tileEntity != null ){
			return this.tileEntity.getPlayerOwner();
		}
		return "";
	}
	@Override
	protected void drawGuiContainerForegroundLayer( int par1, int par2 ){
		fontRenderer.drawString( this.getOwnerName() + "'s Booth", 8, -26, 4210752 );
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer( float par1, int par2, int par3 ){
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
		this.mc.renderEngine.bindTexture( CommonProxy.GuiTradeBoothTopPNG );
		int x = ( width - xSize ) / 2;
		int y = ( height - ySize ) / 2;
		this.drawTexturedModalRect( x, y - 34, 0, 0, 175, 210 );
	}
}