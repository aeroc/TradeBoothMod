package mods.tradebooth.gui;

import org.lwjgl.opengl.GL11;


import mods.tradebooth.CommonProxy;
import mods.tradebooth.container.ContainerTradeBoothStorage;
import mods.tradebooth.tileentity.TileEntityTradeBoothStorage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiTradeBoothStorage extends GuiContainer{
	
	private TileEntityTradeBoothStorage tileEntity;
	
	public GuiTradeBoothStorage( InventoryPlayer inventoryPlayer, TileEntityTradeBoothStorage tileEntity ){
		super( new ContainerTradeBoothStorage( inventoryPlayer, tileEntity ) );
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
		fontRenderer.drawString( "Trade Booth Storage", 8, -26, 4210752 );
		fontRenderer.drawString( "Owner: " + this.getOwnerName(), 8, -2, 4210752 );
		fontRenderer.drawString( StatCollector.translateToLocal( "container.inventory" ), 8, ySize - 96 + 14, 4210752 );
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer( float par1, int par2, int par3 ){
		GL11.glColor4f( 1.0F, 1.0F, 1.0F, 1.0F );
		this.mc.renderEngine.bindTexture( CommonProxy.GuiTradeBoothBottomPNG );
		int x = ( width - xSize ) / 2;
		int y = ( height - ySize ) / 2;
		this.drawTexturedModalRect( x, y - 34, 0, 0, 175, 210 );
	}
}
