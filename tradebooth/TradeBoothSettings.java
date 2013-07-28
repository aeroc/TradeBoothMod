package tradebooth;

import net.minecraftforge.common.Configuration;

public class TradeBoothSettings {
	
	public static int blockIDTop = 961;
	public static int blockIDBottom = 960;
	public static int itemIDTop = 10960;
	public static int explosionResistance = 9999;
	public static int redstoneTransactionDuration = 2;
	public static boolean disableCraftingRecipes = false;
	public static boolean requireItemStack = false;

	public static void config( Configuration config ){
		blockIDTop = config.get( "Block IDs", "Trade Booth Top", blockIDTop ).getInt();
		blockIDBottom = config.get( "Block IDs", "Trade Booth Storage", blockIDBottom ).getInt();
		itemIDTop = config.get( "Item IDs", "Trade Booth Top", itemIDTop ).getInt();
		explosionResistance = config.get( "Settings", "Explosion Resistance", explosionResistance ).getInt();
		if( explosionResistance < 0 ){
			explosionResistance = 0;
		}
		redstoneTransactionDuration = config.get( "Settings", "Redstone Transaction Duration", redstoneTransactionDuration ).getInt();
		if( redstoneTransactionDuration < 1 ){
			redstoneTransactionDuration = 1;
		}
		disableCraftingRecipes = config.get( "Settings", "Disable Crafting Recipes", disableCraftingRecipes ).getBoolean( disableCraftingRecipes );
		requireItemStack = config.get( "Settings", "Require ItemStack", requireItemStack ).getBoolean( requireItemStack );
	}
}
