package tradebooth;

import net.minecraftforge.common.Configuration;

public class TradeBoothSettings {
	
	public static int BlockIDTop = 961;
	public static int BlockIDBottom = 960;
	public static int ItemIDTop = 10960;
	public static int ExplosionResistance = 9999;
	public static int RedstoneTransactionDuration = 2;

	public static void config( Configuration config ){
		BlockIDTop = config.get( "Block IDs", "Trade Booth Top", BlockIDTop ).getInt();
		BlockIDBottom = config.get( "Block IDs", "Trade Booth Storage", BlockIDBottom ).getInt();
		ItemIDTop = config.get( "Item IDs", "Trade Booth Top", ItemIDTop ).getInt();
		ExplosionResistance = config.get( "Settings", "Explosion Resistance", ExplosionResistance ).getInt();
		if( ExplosionResistance < 0 ){
			ExplosionResistance = 0;
		}
		RedstoneTransactionDuration = config.get( "Settings", "Redstone Transaction Duration", RedstoneTransactionDuration ).getInt();
		if( RedstoneTransactionDuration < 1 ){
			RedstoneTransactionDuration = 1;
		}
	}
}
