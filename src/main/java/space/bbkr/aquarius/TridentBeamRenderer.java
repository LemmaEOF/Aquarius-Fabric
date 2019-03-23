package space.bbkr.aquarius;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class TridentBeamRenderer extends ProjectileEntityRenderer<TridentBeamEntity> {

	public TridentBeamRenderer(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
		this.field_4672 = 0.0F;
	}

	@Override
	protected Identifier getTexture(TridentBeamEntity tridentBeamEntity) {
		return new Identifier("minecraft", "textures/entity/guardian_beam.png");
	}
}
