package tradebooth.block;

import java.util.ArrayList;
import java.util.Random;

import tradebooth.CommonProxy;
import tradebooth.TradeBoothMod;
import tradebooth.tileentity.TileEntityTradeBoothTop;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockTradeBoothTop extends BlockContainer{
	
	public static Icon iconTop;
	
	public BlockTradeBoothTop( int id ){
		super( id, Material.wood );
		this.setHardness( 5.0F );
		this.setResistance( 9999.0F );
		this.setUnlocalizedName( "blockTradeBoothTop" );
	}
	
	@Override
	public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int var1, float var2, float var3, float var4 ){
		TileEntity tileEntity = world.getBlockTileEntity( x, y, z );
		if( tileEntity == null || player.isSneaking() ){
			return false;
		}
		else if( tileEntity instanceof TileEntityTradeBoothTop ){
			TileEntityTradeBoothTop tradeBoothTop = (TileEntityTradeBoothTop) tileEntity;
			String playerOwnerName = tradeBoothTop.getPlayerOwner();
			if( playerOwnerName == null || playerOwnerName.isEmpty() ){ //If this TradeBooth is unowned
				tradeBoothTop.setPlayerOwner( player.getEntityName() );
				if( !world.isRemote ){ //Server
					tradeBoothTop.sendTileEntityDataPacketToNearbyPlayers( player.dimension );
				}
			}
			if( tradeBoothTop.getPlayerOwner().equals( player.getEntityName() ) ){
				player.openGui( TradeBoothMod.instance, 1, world, x, y, z );	//Owner's GUI
				return true;
			}
			else{
				player.openGui( TradeBoothMod.instance, 2, world, x, y, z ); //Non-owner's GUI
				return true;
			}
		}
		else{
			return true;
		}
	}
	
	@Override
	public void breakBlock( World world, int x, int y, int z, int par5, int par6 ){
		this.dropItems( world, x, y, z );
		super.breakBlock( world, x, y, z, par5, par6 );
	}
	
	private void dropItems( World world, int x, int y, int z ){
		Random rand = new Random();
		
		TileEntity tileEntity = world.getBlockTileEntity( x, y, z );
		if( !( tileEntity instanceof IInventory ) ){
			return;
		}
		IInventory inventory = (IInventory) tileEntity;
		
		for( int i = 0; i < inventory.getSizeInventory(); i++ ){
			ItemStack itemStack = inventory.getStackInSlot( i );
			
			if( itemStack != null && itemStack.stackSize > 0 ){
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;
				
				EntityItem entityItem = new EntityItem( world, x + rx, y + ry, z + rz, new ItemStack( itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage() ) );
				if( itemStack.hasTagCompound() ){
					entityItem.getEntityItem().setTagCompound( (NBTTagCompound) itemStack.getTagCompound().copy() );
				}
				
				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld( entityItem );
				itemStack.stackSize = 0;
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntityTradeBoothTop();
	}
	@Override
	public boolean hasTileEntity( int metadata ){
		return true;
	}
	
	@Override
	public float getPlayerRelativeBlockHardness( EntityPlayer player, World world, int x, int y, int z ){
		TileEntity tileEntity = world.getBlockTileEntity( x, y, z );
		if( tileEntity != null && tileEntity instanceof TileEntityTradeBoothTop ){
			TileEntityTradeBoothTop tradeBoothTop = (TileEntityTradeBoothTop) tileEntity;
			String ownerName = tradeBoothTop.getPlayerOwner();
			if( ownerName == null || ownerName.isEmpty() ){ //If this is unclaimed
				return super.getPlayerRelativeBlockHardness( player, world, x, y, z );
			}
			else if( ownerName.equals( player.getEntityName() ) ){ //If this block is claimed by the player breaking it
				return super.getPlayerRelativeBlockHardness( player, world, x, y, z );
			}
			else{ //If this block belongs to someone else
				return 0.0F;
			}
		}
		return super.getPlayerRelativeBlockHardness( player, world, x, y, z );
	}
	
	@Override
	public void registerIcons( IconRegister iconRegister ){
        this.blockIcon = iconRegister.registerIcon( "tradebooth:tradeboothtopside" );
        this.iconTop = iconRegister.registerIcon( "tradebooth:tradeboothtoptop" );
    }
	@Override
	public boolean renderAsNormalBlock(){
		return false;
	}
	@Override 
	public boolean isOpaqueCube(){
		return false;
	}
	@Override
	public int getRenderType(){
		return TradeBoothMod.BoothTopRenderID;
	}
	@Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune){
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        
        ret.add( new ItemStack( TradeBoothMod.itemTradeBoothTop, 1, 0 ) );
        
        return ret;
    }
	public void setBlockBoundsForItemRender(){
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		System.out.println( "Hello" );
	}

}