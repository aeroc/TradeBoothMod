package mods.tradebooth;

import cpw.mods.fml.client.registry.RenderingRegistry;
import mods.tradebooth.handler.BlockRenderHandler;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerTextures(){
		MinecraftForgeClient.preloadTexture( CommonProxy.GuiTradeBoothTopPNG );
		MinecraftForgeClient.preloadTexture( CommonProxy.GuiTradeBoothBottomPNG );
		
		RenderingRegistry.registerBlockHandler( TradeBoothMod.BoothTopRenderID = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler() );
	}
}