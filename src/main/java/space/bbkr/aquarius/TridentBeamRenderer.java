package space.bbkr.aquarius;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class TridentBeamRenderer extends ProjectileEntityRenderer<TridentBeamEntity> {

	public TridentBeamRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	protected Identifier getTexture(TridentBeamEntity entity) {
		return new Identifier("aquarius", "textures/entity/trident_beam.png");
	}
}
