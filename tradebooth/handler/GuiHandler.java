package mods.tradebooth.handler;

import mods.tradebooth.container.ContainerTradeBoothStorage;
import mods.tradebooth.container.ContainerTradeBoothTopNonOwner;
import mods.tradebooth.container.ContainerTradeBoothTopOwner;
import mods.tradebooth.gui.GuiTradeBoothStorage;
import mods.tradebooth.gui.GuiTradeBoothTopNonOwner;
import mods.tradebooth.gui.GuiTradeBoothTopOwner;
import mods.tradebooth.tileentity.TileEntityTradeBoothStorage;
import mods.tradebooth.tileentity.TileEntityTradeBoothTop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity( x, y, z );
		if( tileEntity instanceof TileEntityTradeBoothStorage ){
			return new ContainerTradeBoothStorage( player.inventory, (TileEntityTradeBoothStorage) tileEntity );
		}
		else if( tileEntity instanceof TileEntityTradeBoothTop ){
			if( ID == 1 ){
				return new ContainerTradeBoothTopOwner( player.inventory, (TileEntityTradeBoothTop) tileEntity );
			}
			else if( ID == 2 ){
				return new ContainerTradeBoothTopNonOwner( player.inventory, (TileEntityTradeBoothTop) tileEntity );
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity( x, y, z );
		if( tileEntity instanceof TileEntityTradeBoothStorage ){
			return new GuiTradeBoothStorage( player.inventory, (TileEntityTradeBoothStorage) tileEntity );
		}
		else if( tileEntity instanceof TileEntityTradeBoothTop ){
			if( ID == 1 ){
				return new GuiTradeBoothTopOwner( player.inventory, (TileEntityTradeBoothTop) tileEntity );
			}
			else if( ID == 2 ){
				return new GuiTradeBoothTopNonOwner( player.inventory, (TileEntityTradeBoothTop) tileEntity );
			}
		}
		return null;
	}
}