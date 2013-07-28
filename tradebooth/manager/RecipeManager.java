package tradebooth.manager;

import java.util.List;

import tradebooth.TradeBoothMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.src.ModLoader;

public class RecipeManager {
	public static void registerRecipes(){
		for( int i = 0; i < 4; i++ ){
			ModLoader.addRecipe( new ItemStack( TradeBoothMod.blockTradeBoothStorage, 1, i ), new Object[]{ "aaa", "aba", "aaa", Character.valueOf( 'a' ), new ItemStack( Block.planks, 1, i ), Character.valueOf( 'b' ), new ItemStack( Item.ingotIron ) } );
			ModLoader.addRecipe( new ItemStack( TradeBoothMod.itemTradeBoothTop, 1, i ), new Object[]{ "aaa", "a a", "aba", Character.valueOf( 'a' ), new ItemStack( Block.planks, 1, i ), Character.valueOf( 'b' ), new ItemStack( Item.goldNugget ) } );
		}
	}
}
