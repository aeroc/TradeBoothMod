package tradebooth.player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import tradebooth.TradeBoothSettings;
import tradebooth.handler.PacketHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PlayerTracker implements IPlayerTracker{
	
	@Override
	public void onPlayerLogin( EntityPlayer player ){
		sendCraftingRecipeSettingPacket();
		sendRequireItemStackSettingPacket();
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		
	}
	public void sendCraftingRecipeSettingPacket(){
		ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream( 1 );
		DataOutputStream outputStream = new DataOutputStream( byteArrayStream );
		
		try{
			if( TradeBoothSettings.disableCraftingRecipes ){
				outputStream.writeByte( PacketHandler.PACKET_DISABLE_CRAFTING_RECIPES );
			}
			else{
				outputStream.writeByte( PacketHandler.PACKET_ENABLE_CRAFTING_RECIPES );
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "TradeBooth";
		packet.data = byteArrayStream.toByteArray();
		packet.length = byteArrayStream.size();
		
		PacketDispatcher.sendPacketToAllPlayers( packet );
	}
	public void sendRequireItemStackSettingPacket(){
		ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream( 1 );
		DataOutputStream outputStream = new DataOutputStream( byteArrayStream );
		
		try{
			if( !TradeBoothSettings.requireItemStack ){
				outputStream.writeByte( PacketHandler.PACKET_DISABLE_REQUIRE_ITEMSTACK );
			}
			else{
				outputStream.writeByte( PacketHandler.PACKET_ENABLE_REQUIRE_ITEMSTACK );
			}
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "TradeBooth";
		packet.data = byteArrayStream.toByteArray();
		packet.length = byteArrayStream.size();
		
		PacketDispatcher.sendPacketToAllPlayers( packet );
	}
}
