package tradebooth;

import tradebooth.ItemBlock.ItemBlockTradeBoothStorage;
import tradebooth.ItemBlock.ItemBlockTradeBoothTop;
import tradebooth.block.BlockTradeBoothStorage;
import tradebooth.block.BlockTradeBoothTop;
import tradebooth.handler.GuiHandler;
import tradebooth.handler.PacketHandler;
import tradebooth.item.ItemTradeBoothTop;
import tradebooth.manager.RecipeManager;
import tradebooth.player.PlayerTracker;
import tradebooth.tileentity.TileEntityTradeBoothStorage;
import tradebooth.tileentity.TileEntityTradeBoothTop;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWood;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
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

@Mod( modid="tradeboothmod", name="Trade Booth Mod", version="0.6.2.1" )

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
	
	@EventHandler
	public void preInit( FMLPreInitializationEvent event ){
		Configuration config = new Configuration( event.getSuggestedConfigurationFile() );
		TradeBoothSettings.config( config );
		config.save();
	}
		
	@EventHandler
	public void init( FMLInitializationEvent event ){
		TradeBoothMod.instance = this;
		
		blockTradeBoothStorage = new BlockTradeBoothStorage( TradeBoothSettings.blockIDBottom );
		blockTradeBoothTop = new BlockTradeBoothTop( TradeBoothSettings.blockIDTop );
		itemTradeBoothTop = new ItemTradeBoothTop( TradeBoothSettings.itemIDTop );
		
		GameRegistry.registerBlock( blockTradeBoothStorage, ItemBlockTradeBoothStorage.class, "blockTradeBoothStorage" );
		GameRegistry.registerBlock( blockTradeBoothTop, ItemBlockTradeBoothTop.class, "blockTradeBoothTop" );
		GameRegistry.registerTileEntity( TileEntityTradeBoothStorage.class, "tileEntityTradeBoothStorage" );
		GameRegistry.registerTileEntity( TileEntityTradeBoothTop.class, "tileEntityTradeBoothTop" );
		
		GameRegistry.registerItem( itemTradeBoothTop, "itemTradeBoothTop" );
		
		NetworkRegistry.instance().registerGuiHandler( this, new GuiHandler() );
		
		ModLoader.addLocalization( "tile.blockTradeBoothStorageOak.name", "Trade Booth Storage - Oak" );
		ModLoader.addLocalization( "tile.blockTradeBoothStorageSpruce.name", "Trade Booth Storage - Spruce" );
		ModLoader.addLocalization( "tile.blockTradeBoothStorageBirch.name", "Trade Booth Storage - Birch" );
		ModLoader.addLocalization( "tile.blockTradeBoothStorageJungle.name", "Trade Booth Storage - Jungle" );

		ModLoader.addLocalization( "tile.blockTradeBoothTopOak.name", "Trade Booth Top - Oak" );
		ModLoader.addLocalization( "tile.blockTradeBoothTopSpruce.name", "Trade Booth Top - Spruce" );
		ModLoader.addLocalization( "tile.blockTradeBoothTopBirch.name", "Trade Booth Top - Birch" );
		ModLoader.addLocalization( "tile.blockTradeBoothTopJungle.name", "Trade Booth Top - Jungle" );
		
		ModLoader.addLocalization( "item.TradeBoothTopOak.name", "Trade Booth Top - Oak" );
		ModLoader.addLocalization( "item.TradeBoothTopSpruce.name", "Trade Booth Top - Spruce" );
		ModLoader.addLocalization( "item.TradeBoothTopBirch.name", "Trade Booth Top - Birch" );
		ModLoader.addLocalization( "item.TradeBoothTopJungle.name", "Trade Booth Top - Jungle" );
		
		MinecraftForge.setBlockHarvestLevel( blockTradeBoothStorage, "axe", 0 );
		MinecraftForge.setBlockHarvestLevel( blockTradeBoothTop, "axe", 0 );
		
		this.commonProxy.registerTextures();
		
		if( !TradeBoothSettings.disableCraftingRecipes ){
			RecipeManager.registerRecipes();
		}
		GameRegistry.registerPlayerTracker( new PlayerTracker() );
	}
}