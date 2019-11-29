package space.bbkr.aquarius;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;

public class AquariusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.INSTANCE.register(Aquarius.CHORUS_CONDUIT_BE, ChorusConduitRenderer::new);
		EntityRendererRegistry.INSTANCE.register(Aquarius.TRIDENT_BEAM, (dispatcher, context) -> new TridentBeamRenderer(dispatcher));
		ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEX).register((provider, registry) -> {
			registry.register(new Identifier(Aquarius.MODID, "entity/chorus_conduit/base"));
			registry.register(new Identifier(Aquarius.MODID, "entity/chorus_conduit/cage"));
			registry.register(new Identifier(Aquarius.MODID, "entity/chorus_conduit/wind"));
			registry.register(new Identifier(Aquarius.MODID, "entity/chorus_conduit/wind_vertical"));
			registry.register(new Identifier(Aquarius.MODID, "entity/chorus_conduit/open_eye"));
			registry.register(new Identifier(Aquarius.MODID, "entity/chorus_conduit/closed_eye"));
		});
	}
}
