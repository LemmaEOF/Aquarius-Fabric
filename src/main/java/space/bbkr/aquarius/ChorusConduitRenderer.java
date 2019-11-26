package space.bbkr.aquarius;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class ChorusConduitRenderer extends BlockEntityRenderer<ChorusConduitBlockEntity> {
    public static final SpriteIdentifier BASE_TEX;
    public static final SpriteIdentifier CAGE_TEX;
    public static final SpriteIdentifier WIND_TEX;
    public static final SpriteIdentifier WIND_VERTICAL_TEX;
    public static final SpriteIdentifier OPEN_EYE_TEX;
    public static final SpriteIdentifier CLOSED_EYE_TEX;
    private final ModelPart eyeModel = new ModelPart(16, 16, 0, 0);
    private final ModelPart windModel;
    private final ModelPart shellModel;
    private final ModelPart cageModel;

    public ChorusConduitRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
        this.eyeModel.addCuboid(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.01F);
        this.windModel = new ModelPart(64, 32, 0, 0);
        this.windModel.addCuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F);
        this.shellModel = new ModelPart(32, 16, 0, 0);
        this.shellModel.addCuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F);
        this.cageModel = new ModelPart(32, 16, 0, 0);
        this.cageModel.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
    }

    public void render(ChorusConduitBlockEntity be, float f, MatrixStack stack, VertexConsumerProvider provider, int i, int j) {
        float g = (float)be.ticks + f;
        float k;
        if (!be.isActive()) {
            k = be.getRotation(0.0F);
            VertexConsumer baseModel = BASE_TEX.getVertexConsumer(provider, RenderLayer::getEntitySolid);
            stack.push();
            stack.translate(0.5D, 0.5D, 0.5D);
            stack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(k));
            this.shellModel.render(stack, baseModel, i, j);
            stack.pop();
        } else {
            k = be.getRotation(f) * 57.295776F;
            float l = MathHelper.sin(g * 0.1F) / 2.0F + 0.5F;
            l += l * l;
            stack.push();
            stack.translate(0.5D, (0.3F + l * 0.2F), 0.5D);
            Vector3f vector3f = new Vector3f(0.5F, 1.0F, 0.5F);
            vector3f.reciprocal();
            stack.multiply(new Quaternion(vector3f, k, true));
            this.cageModel.render(stack, CAGE_TEX.getVertexConsumer(provider, RenderLayer::getEntityCutoutNoCull), i, j);
            stack.pop();
            int m = be.ticks / 66 % 3;
            stack.push();
            stack.translate(0.5D, 0.5D, 0.5D);
            if (m == 1) {
                stack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
            } else if (m == 2) {
                stack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
            }

            VertexConsumer vertexConsumer2 = (m == 1 ? WIND_VERTICAL_TEX : WIND_TEX).getVertexConsumer(provider, RenderLayer::getEntityCutoutNoCull);
            this.windModel.render(stack, vertexConsumer2, i, j);
            stack.pop();
            stack.push();
            stack.translate(0.5D, 0.5D, 0.5D);
            stack.scale(0.875F, 0.875F, 0.875F);
            stack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F));
            stack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
            this.windModel.render(stack, vertexConsumer2, i, j);
            stack.pop();
            Camera camera = this.blockEntityRenderDispatcher.camera;
            stack.push();
            stack.translate(0.5D, (0.3F + l * 0.2F), 0.5D);
            stack.scale(0.5F, 0.5F, 0.5F);
            float n = -camera.getYaw();
            stack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(n));
            stack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));
            stack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
            stack.scale(1.3333334F, 1.3333334F, 1.3333334F);
            this.eyeModel.render(stack, (be.isEyeOpen() ? OPEN_EYE_TEX : CLOSED_EYE_TEX).getVertexConsumer(provider, RenderLayer::getEntityCutoutNoCull), i, j);
            stack.pop();
        }
    }

    static {
        BASE_TEX = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(Aquarius.MODID, "entity/chorus_conduit/base"));
        CAGE_TEX = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(Aquarius.MODID, "entity/chorus_conduit/cage"));
        WIND_TEX = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(Aquarius.MODID, "entity/chorus_conduit/wind"));
        WIND_VERTICAL_TEX = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(Aquarius.MODID, "entity/chorus_conduit/wind_vertical"));
        OPEN_EYE_TEX = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(Aquarius.MODID, "entity/chorus_conduit/open_eye"));
        CLOSED_EYE_TEX = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier(Aquarius.MODID, "entity/chorus_conduit/closed_eye"));
    }
}
