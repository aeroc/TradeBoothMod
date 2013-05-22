package mods.tradebooth;

import net.minecraftforge.common.Configuration;

public class TradeBoothSettings {
	
	public static int BlockIDTop = 961;
	public static int BlockIDBottom = 960;
	public static int ItemIDTop = 10960;

	public static void config( Configuration config ){
		BlockIDTop = config.get( "Block IDs", "Trade Booth Top", BlockIDTop ).getInt();
		BlockIDBottom = config.get( "Block IDs", "Trade Booth Storage", BlockIDBottom ).getInt();
		ItemIDTop = config.get( "Item IDs", "Trade Booth Top", ItemIDTop ).getInt();
	}
}
