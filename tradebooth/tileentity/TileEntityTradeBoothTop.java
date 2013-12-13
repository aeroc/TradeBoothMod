package tradebooth.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import tradebooth.handler.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityTradeBoothTop extends TileNetworkEntity implements IInventory{

	private ItemStack[] inventory;
	private String playerOwner;
	private static int ticksPerCycle = 60;
	private int tickCount = 0;
	
	public TileEntityTradeBoothTop(){
		this.inventory = new ItemStack[17];
		this.playerOwner = "";
	}
	
	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot( int inventorySlot ) {
		return this.inventory[inventorySlot];
	}

	@Override
	public ItemStack decrStackSize( int inventorySlot, int amount ) {
		ItemStack itemStack = this.getStackInSlot( inventorySlot );
		if( itemStack != null ){
			if( itemStack.stackSize <= amount ){
				this.setInventorySlotContents( inventorySlot, null );
			}
			else{
				itemStack = itemStack.splitStack( amount );
				if( itemStack.stackSize == 0 ){
					this.setInventorySlotContents( inventorySlot, null );
				}
			}
		}
		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing( int inventorySlot ) {
		ItemStack itemStack = getStackInSlot( inventorySlot );
		if( itemStack != null ){
			this.setInventorySlotContents( inventorySlot, null );
		}
		return itemStack;
	}

	@Override
	public void setInventorySlotContents( int inventorySlot, ItemStack itemStack ) {
		this.inventory[inventorySlot] = itemStack;
		if( itemStack != null && itemStack.stackSize > this.getInventoryStackLimit() ){
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "tradebooth.tileentitytradeboothtop";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer( EntityPlayer player ) {
		return this.worldObj.getBlockTileEntity( this.xCoord, this.yCoord, this.zCoord ) == this &&
				player.getDistanceSq( xCoord + 0.5, yCoord + 0.5, zCoord + 0.5 ) < 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
	
	@Override
	public void readFromNBT( NBTTagCompound tagCompound ){
		super.readFromNBT( tagCompound );
		
		NBTTagList tagList = tagCompound.getTagList( "Inventory" );
		for( int i = 0; i < tagList.tagCount(); i++ ){
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt( i );
			byte slot = tag.getByte( "Slot" );
			if( slot >= 0 && slot < this.inventory.length ){
				this.inventory[slot] = ItemStack.loadItemStackFromNBT( tag );
			}
		}
		this.playerOwner = tagCompound.getString( "PlayerOwner" );
	}

	@Override
	public void writeToNBT( NBTTagCompound tagCompound ){
		super.writeToNBT( tagCompound );
		
		NBTTagList tagList = new NBTTagList();
		for( int i = 0; i < this.inventory.length; i++ ){
			ItemStack itemStack = this.inventory[i];
			if( itemStack != null ){
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte( "Slot", (byte) i );
				itemStack.writeToNBT( tag );
				tagList.appendTag( tag );
			}
		}
		tagCompound.setTag( "Inventory", tagList );
		tagCompound.setString( "PlayerOwner", this.playerOwner );
	}
	
	public String getPlayerOwner(){
		return this.playerOwner;
	}
	
	public void setPlayerOwner( String newOwner ){
		this.playerOwner = newOwner;
	}
	
	@Override
	public void sendTileEntityDataPacketToPlayer( Player destinationPlayer ){
		if( this.playerOwner != null && !this.playerOwner.isEmpty() ){
			//I made this check in case I decide that ownership only takes place on the initial block activation instead of on placement
			PacketDispatcher.sendPacketToPlayer( this.createTileEntityDataPacket(), destinationPlayer );
		}
	}
	@Override
	public void sendTileEntityDataPacketToNearbyPlayers( int dimensionID ){
		if( this.playerOwner != null && !this.playerOwner.isEmpty() ){
			PacketDispatcher.sendPacketToAllAround( this.xCoord, this.yCoord, this.zCoord, 220, dimensionID, this.createTileEntityDataPacket() );
		}
	}
	@Override
	public Packet250CustomPayload createTileEntityDataPacket(){
		int dataLength = 13 + this.getPlayerOwner().length(); // 13 = 1 byte + 3 int
		ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream( dataLength );
		DataOutputStream outputStream = new DataOutputStream( byteArrayStream );
		
		try{
			outputStream.writeByte( PacketHandler.PACKET_RECEIVE_PLAYER_NAME );
			outputStream.writeInt( this.xCoord );
			outputStream.writeInt( this.yCoord );
			outputStream.writeInt( this.zCoord );
			outputStream.writeInt( this.getPlayerOwner().length() ); //The length of the owner's name
			outputStream.writeBytes( this.getPlayerOwner() );
		}
		catch( Exception e){
			e.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "TradeBooth";
		packet.data = byteArrayStream.toByteArray();
		packet.length = byteArrayStream.size();
		return packet;
	}
	public Packet250CustomPayload createUpdatePacket(){
		int dataLength = 25; // 1 byte + 6 ints
		ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream( dataLength );
		DataOutputStream outputStream = new DataOutputStream( byteArrayStream );
		
		try{
			outputStream.writeByte( PacketHandler.PACKET_UPDATE_TRADE_BOOTH_TOP );
			outputStream.writeInt( this.xCoord );
			outputStream.writeInt( this.yCoord );
			outputStream.writeInt( this.zCoord );
			outputStream.writeInt( this.getStackInSlot(16).itemID );
			outputStream.writeInt( this.getStackInSlot(16).stackSize );
			outputStream.writeInt( this.getStackInSlot(16).getItemDamage() );
		}
		catch( Exception e){
			e.printStackTrace();
			//System.out.println( "error" );
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "TradeBooth";
		packet.data = byteArrayStream.toByteArray();
		packet.length = byteArrayStream.size();
		return packet;
	}
	public boolean isConnectedToStorageAndSameOwner( World world ){
		if( this.yCoord > 1 ){ //Do not allow a TradeBoothTop to attempt a connection when placed below y: 2
			TileEntity checkEntity = world.getBlockTileEntity( this.xCoord, this.yCoord - 1, this.zCoord );
			if( checkEntity != null && checkEntity instanceof TileEntityTradeBoothStorage ){
				TileEntityTradeBoothStorage storageEntity = (TileEntityTradeBoothStorage) checkEntity;
				String topOwner = this.getPlayerOwner();
				String storageOwner = storageEntity.getPlayerOwner();
				if( !topOwner.equals( "" ) && topOwner.equals( storageOwner ) ){ //If at least one of them isn't empty and they are both the same
					return true;
				}
			}
		}
		return false;
	}
	public TileEntityTradeBoothStorage getConnectedTileEntityStorage( World world ){
		if( this.isConnectedToStorageAndSameOwner( world ) ){
			return (TileEntityTradeBoothStorage) world.getBlockTileEntity( this.xCoord, this.yCoord - 1, this.zCoord );
		}
		return null;
	}
	public boolean canConnectedStorageBuy( ItemStack buyStack, World world ){
		TileEntity checkEntity = world.getBlockTileEntity( this.xCoord, this.yCoord - 1, this.zCoord );
		if( checkEntity != null && checkEntity instanceof TileEntityTradeBoothStorage ){
			TileEntityTradeBoothStorage storageEntity = (TileEntityTradeBoothStorage) checkEntity;
			return storageEntity.canAcceptItemStack( buyStack );
		}
		return false;
	}
	public boolean canConnectedStorageSell( ItemStack sellStack, World world ){
		TileEntity checkEntity = world.getBlockTileEntity( this.xCoord, this.yCoord - 1, this.zCoord );
		if( checkEntity != null && checkEntity instanceof TileEntityTradeBoothStorage ){
			TileEntityTradeBoothStorage storageEntity = (TileEntityTradeBoothStorage) checkEntity;
			return storageEntity.canProvideItemStack( sellStack );
		}
		return false;
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void updateEntity(){
		if( this.tickCount < TileEntityTradeBoothTop.ticksPerCycle ){
			this.tickCount++;
		}
		else{
			this.tickCount = 0;
			if( !this.worldObj.isRemote && this.getStackInSlot(16) != null ){ //If server && slot is not empty
				PacketDispatcher.sendPacketToAllPlayers( this.createUpdatePacket() );
			}
		}
	}
}