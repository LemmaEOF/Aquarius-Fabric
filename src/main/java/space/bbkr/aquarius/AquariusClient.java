package space.bbkr.aquarius;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.client.render.BlockEntityRendererRegistry;

public class AquariusClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
//		BlockEntityRendererRegistry.INSTANCE.register(ChorusConduitBlockEntity.class, new ChorusConduitRenderer());
	}
}
