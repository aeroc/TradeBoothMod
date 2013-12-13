package tradebooth.item;

import java.util.List;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tradebooth.CommonProxy;
import tradebooth.TradeBoothMod;
import tradebooth.block.BlockTradeBoothStorage;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemTradeBoothTop extends Item{
	
	public Block placeBlock = TradeBoothMod.blockTradeBoothTop;
	public static Icon[] iconArray = new Icon[4];

	public ItemTradeBoothTop( int i ){
		super( i );
		this.setCreativeTab( CreativeTabs.tabDecorations );
		this.setUnlocalizedName( "TradeBoothTop" );
		this.setHasSubtypes( true );
	}
	@Override
	public String getUnlocalizedName( ItemStack itemStack ){
		return this.getUnlocalizedName() + BlockTradeBoothStorage.woodType[itemStack.getItemDamage()];
	}
	@Override
	public void getSubItems( int par1, CreativeTabs creativeTabs, List list ){
		for( int i = 0; i < 4; i++ ){
			list.add( new ItemStack( this, 1, i ) );
		}
	}
	@Override
	public int getMetadata( int meta ){
		return meta;
	}
	@Override
    public void registerIcons(IconRegister iconRegister){
		for( int i = 0; i < 4; i++ ){
			this.iconArray[i] = iconRegister.registerIcon( "tradebooth:tradeboothtop" + i );
		}
    }
	@Override
	public Icon getIconFromDamage( int meta ){
		return this.iconArray[meta];
	}
	@Override
	public boolean onItemUse( ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ ){
		int targetBlockID = world.getBlockId( x, y, z );
		if( !Block.blocksList[targetBlockID].isBlockReplaceable( world, x, y, z ) ){
            if( side == 0 ){
                --y;
            }

            if( side == 1 ){
                ++y;
            }

            if( side == 2 ){
            	--z;
            }

            if( side == 3 ){
                ++z;
            }

            if( side == 4 ){
                --x;
            }

            if( side == 5 ){
                ++x;
            }
        }
		if( placeBlockAt( itemStack, entityPlayer, world, x, y, z, side, hitX, hitY, hitZ, 0 ) ){
			world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), Block.stone.stepSound.getPlaceSound(), ( Block.stone.stepSound.getVolume() + 1.0F) / 2.0F, Block.stone.stepSound.getPitch() * 0.8F);
            --itemStack.stackSize;
            return true;
        }
		return false;
	}
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata){
		//System.out.println( stack.getItemDamage() );
		if( !world.setBlock( x, y, z, this.placeBlock.blockID, stack.getItemDamage(), 2 ) ){
	        return false;
		}

		if( world.getBlockId( x, y, z ) == this.placeBlock.blockID ){
			placeBlock.onBlockPlacedBy( world, x, y, z, player, stack );
			placeBlock.onPostBlockPlaced(world, x, y, z, metadata );
		}

		return true;
	}
}