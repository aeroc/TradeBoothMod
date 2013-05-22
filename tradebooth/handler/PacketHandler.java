package mods.tradebooth.handler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;


import mods.tradebooth.tileentity.TileEntityTradeBoothStorage;
import mods.tradebooth.tileentity.TileEntityTradeBoothTop;
import mods.tradebooth.tileentity.TileNetworkEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler{
	
	public static final byte PACKET_RECEIVE_PLAYER_NAME = 0;
	public static final byte PACKET_REQUEST_TILE_ENTITY_DATA = 1;

	@Override
	public void onPacketData( INetworkManager manager, Packet250CustomPayload packet, Player player ){
		
		DataInputStream dataInputStream = new DataInputStream( new ByteArrayInputStream( packet.data ) );
		
		byte packetType;
		
		try{
			packetType = dataInputStream.readByte();
			
			EntityPlayer entityPlayer = (EntityPlayer) player;
			
			int x, y, z;
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
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}
}