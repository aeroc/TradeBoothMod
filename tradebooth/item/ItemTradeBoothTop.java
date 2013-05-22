package mods.tradebooth.item;

import cpw.mods.fml.common.network.PacketDispatcher;
import mods.tradebooth.CommonProxy;
import mods.tradebooth.TradeBoothMod;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.world.World;

public class ItemTradeBoothTop extends Item{
	
	public Block placeBlock = TradeBoothMod.blockTradeBoothTop;

	public ItemTradeBoothTop( int i ){
		super( i );
		this.setCreativeTab( CreativeTabs.tabDecorations );
		this.setUnlocalizedName( "TradeBoothTop" );
	}
	@Override
	public void updateIcons( IconRegister iconRegister ){
        this.iconIndex = iconRegister.registerIcon( "tradebooth:tradeboothtop" );
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
		
		if( !world.setBlock( x, y, z, this.placeBlock.blockID ) ){
	        return false;
		}

		if( world.getBlockId( x, y, z ) == this.placeBlock.blockID ){
			placeBlock.onBlockPlacedBy( world, x, y, z, player, stack );
			placeBlock.onPostBlockPlaced(world, x, y, z, metadata);
		}

		return true;
	}
}