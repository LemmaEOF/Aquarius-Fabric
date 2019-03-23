package space.bbkr.aquarius;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;

public class AquariusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.INSTANCE.register(ChorusConduitBlockEntity.class, new ChorusConduitRenderer());
		EntityRendererRegistry.INSTANCE.register(TridentBeamEntity.class, (dispatcher, context) -> new TridentBeamRenderer(dispatcher));
	}
}
