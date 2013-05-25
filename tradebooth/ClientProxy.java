package tradebooth;

import cpw.mods.fml.client.registry.RenderingRegistry;
import tradebooth.handler.BlockRenderHandler;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerTextures(){
		RenderingRegistry.registerBlockHandler( TradeBoothMod.BoothTopRenderID = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler() );
	}
}