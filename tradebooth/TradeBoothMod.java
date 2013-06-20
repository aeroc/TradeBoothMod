package tradebooth;

import tradebooth.block.BlockTradeBoothStorage;
import tradebooth.block.BlockTradeBoothTop;
import tradebooth.handler.GuiHandler;
import tradebooth.handler.PacketHandler;
import tradebooth.item.ItemTradeBoothTop;
import tradebooth.tileentity.TileEntityTradeBoothStorage;
import tradebooth.tileentity.TileEntityTradeBoothTop;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod( modid="TradeBoothMod", name="Trade Booth Mod", version="0.4.2" )

@NetworkMod( clientSideRequired = true, serverSideRequired = false, channels = {"TradeBooth"}, packetHandler = PacketHandler.class )

public class TradeBoothMod {
	
	@Instance( "TradeBoothMod" )
	public static TradeBoothMod instance;

	public static Block blockTradeBoothStorage;
	public static Block blockTradeBoothTop;
	
	public static Item itemTradeBoothTop;
	
	public static int BoothTopRenderID;
	
	@SidedProxy( clientSide= "tradebooth.ClientProxy", serverSide = "tradebooth.CommonProxy" )
	public static CommonProxy commonProxy;
	
	@PreInit
	public void preInit( FMLPreInitializationEvent event ){
		Configuration config = new Configuration( event.getSuggestedConfigurationFile() );
		TradeBoothSettings.config( config );
		config.save();
	}
		
	@Init
	public void init( FMLInitializationEvent event ){
		TradeBoothMod.instance = this;
		
		blockTradeBoothStorage = new BlockTradeBoothStorage( TradeBoothSettings.BlockIDBottom );
		blockTradeBoothTop = new BlockTradeBoothTop( TradeBoothSettings.BlockIDTop );
		itemTradeBoothTop = new ItemTradeBoothTop( TradeBoothSettings.ItemIDTop );
		
		GameRegistry.registerBlock( blockTradeBoothStorage, "blockTradeBoothStorage" );
		GameRegistry.registerBlock( blockTradeBoothTop, "blockTradeBoothTop" );
		GameRegistry.registerTileEntity( TileEntityTradeBoothStorage.class, "tileEntityTradeBoothStorage" );
		GameRegistry.registerTileEntity( TileEntityTradeBoothTop.class, "tileEntityTradeBoothTop" );
		
		GameRegistry.registerItem( itemTradeBoothTop, "itemTradeBoothTop" );
		
		NetworkRegistry.instance().registerGuiHandler( this, new GuiHandler() );
		
		LanguageRegistry.addName( blockTradeBoothStorage, "Trade Booth Storage" );
		LanguageRegistry.addName( blockTradeBoothTop, "Trade Booth Top" );
		LanguageRegistry.addName( itemTradeBoothTop, "Trade Booth Top" );
		
		MinecraftForge.setBlockHarvestLevel( blockTradeBoothStorage, "axe", 0 );
		MinecraftForge.setBlockHarvestLevel( blockTradeBoothTop, "axe", 0 );
		
		this.commonProxy.registerTextures();
	}
}