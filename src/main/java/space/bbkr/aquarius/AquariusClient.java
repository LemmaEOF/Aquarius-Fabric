package space.bbkr.aquarius;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class AquariusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.INSTANCE.register(Aquarius.CHORUS_CONDUIT_BE, ChorusConduitRenderer::new);
		EntityRendererRegistry.INSTANCE.register(Aquarius.TRIDENT_BEAM, (dispatcher, context) -> new TridentBeamRenderer(dispatcher));
	}
}
