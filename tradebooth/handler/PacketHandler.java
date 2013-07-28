package tradebooth.handler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;


import tradebooth.TradeBoothSettings;
import tradebooth.tileentity.TileEntityTradeBoothStorage;
import tradebooth.tileentity.TileEntityTradeBoothTop;
import tradebooth.tileentity.TileNetworkEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler{
	
	public static final byte PACKET_RECEIVE_PLAYER_NAME = 0;
	public static final byte PACKET_REQUEST_TILE_ENTITY_DATA = 1;
	//These two crafting recipe packets aren't really functional
	//Only the client setting gets changed, that event doesn't actually make the client add or remove any recipes
	public static final byte PACKET_ENABLE_CRAFTING_RECIPES = 2;
	public static final byte PACKET_DISABLE_CRAFTING_RECIPES = 3;
	
	public static final byte PACKET_ENABLE_REQUIRE_ITEMSTACK = 4;
	public static final byte PACKET_DISABLE_REQUIRE_ITEMSTACK = 5;
	
	public static final byte PACKET_UPDATE_TRADE_BOOTH_TOP = 6;

	@Override
	public void onPacketData( INetworkManager manager, Packet250CustomPayload packet, Player player ){
		
		DataInputStream dataInputStream = new DataInputStream( new ByteArrayInputStream( packet.data ) );
		
		byte packetType;
		
		try{
			packetType = dataInputStream.readByte();
			
			EntityPlayer entityPlayer = (EntityPlayer) player;
			
			int x, y, z;
			int itemID, stackSize, meta;
			TileEntity tileEntity;
			switch( packetType ){
				case PacketHandler.PACKET_RECEIVE_PLAYER_NAME:
					x = dataInputStream.readInt();
					y = dataInputStream.readInt();
					z = dataInputStream.readInt();
					int stringLength = dataInputStream.readInt();
					byte[] bytes = new byte[stringLength];
					dataInputStream.readFully( bytes );
					String ownerName = new String( bytes );
					
					if( entityPlayer.worldObj != null && entityPlayer.worldObj.isRemote ){
						TileEntity entity = entityPlayer.worldObj.getBlockTileEntity( x, y, z );
						if( entity != null && entity instanceof TileEntityTradeBoothStorage ){
							TileEntityTradeBoothStorage tradeBoothEntity = (TileEntityTradeBoothStorage) entity;
							tradeBoothEntity.setPlayerOwner( ownerName );
						}
						else if( entity != null && entity instanceof TileEntityTradeBoothTop ){
							TileEntityTradeBoothTop tradeBoothTop = (TileEntityTradeBoothTop) entity;
							tradeBoothTop.setPlayerOwner( ownerName );
						}
						
					}
					break;
				case PacketHandler.PACKET_REQUEST_TILE_ENTITY_DATA:
					x = dataInputStream.readInt();
					y = dataInputStream.readInt();
					z = dataInputStream.readInt();
					
					tileEntity = entityPlayer.worldObj.getBlockTileEntity( x, y, z );
					if( tileEntity != null && tileEntity instanceof TileNetworkEntity ){
						TileNetworkEntity tileNetworkEntity = (TileNetworkEntity) tileEntity;
						tileNetworkEntity.sendTileEntityDataPacketToPlayer( player );
					}
					break;
				case PacketHandler.PACKET_ENABLE_CRAFTING_RECIPES:
					TradeBoothSettings.disableCraftingRecipes = false;
					break;
				case PacketHandler.PACKET_DISABLE_CRAFTING_RECIPES:
					TradeBoothSettings.disableCraftingRecipes = true;
					break;
				case PacketHandler.PACKET_ENABLE_REQUIRE_ITEMSTACK:
					TradeBoothSettings.requireItemStack = true;
					break;
				case PacketHandler.PACKET_DISABLE_REQUIRE_ITEMSTACK:
					TradeBoothSettings.requireItemStack = false;
				case PacketHandler.PACKET_UPDATE_TRADE_BOOTH_TOP:
					x = dataInputStream.readInt();
					y = dataInputStream.readInt();
					z = dataInputStream.readInt();
					itemID = dataInputStream.readInt();
					stackSize = dataInputStream.readInt();
					meta = dataInputStream.readInt();
					
					tileEntity = entityPlayer.worldObj.getBlockTileEntity( x, y, z );
					if( tileEntity != null && tileEntity instanceof TileEntityTradeBoothTop ){
						TileEntityTradeBoothTop tradeBoothTop = (TileEntityTradeBoothTop) tileEntity;
						tradeBoothTop.setInventorySlotContents( 16, new ItemStack( itemID, stackSize, meta ) );
						entityPlayer.worldObj.markBlockForRenderUpdate( x, y, z );
					}
			}
		}
		catch( Exception e ){
			//e.printStackTrace();
		}
	}
}