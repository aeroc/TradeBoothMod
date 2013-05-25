package tradebooth.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;


import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import tradebooth.handler.PacketHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

//This abstract subclass is for TileEntities that have extra data stored on the server that the client won't otherwise receive. 

public abstract class TileNetworkEntity extends TileEntity{

	@Override
	public void validate(){
		super.validate();
		
		if( this.worldObj.isRemote ){ //If client, request data packet for this tileentity
			PacketDispatcher.sendPacketToServer( requestTileNetworkEntityData() );
		}
	}
	public Packet requestTileNetworkEntityData(){
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(13);
		DataOutputStream dos = new DataOutputStream( baos );
		try{
			dos.writeByte( PacketHandler.PACKET_REQUEST_TILE_ENTITY_DATA );
			dos.writeInt( this.xCoord );
			dos.writeInt( this.yCoord );
			dos.writeInt( this.zCoord );
		}
		catch( IOException e ){
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "TradeBooth";
		packet.data = baos.toByteArray();
		packet.length = baos.size();
		
		return packet;
	}
	public void sendTileEntityDataPacketToPlayer( Player destinationPlayer ){
		
	}
	public void sendTileEntityDataPacketToNearbyPlayers( int dimensionID ){
		
	}
	public Packet250CustomPayload createTileEntityDataPacket(){
		return null;
	}
}