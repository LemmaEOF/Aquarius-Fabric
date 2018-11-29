package space.bbkr.aquarius;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftGame;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.BoxEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.BoxEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Polar2f;

public class ChorusConduitRenderer extends BlockEntityRenderer<ChorusConduitBlockEntity> {
    private static final Identifier baseTex = new Identifier("aquarius:textures/entity/chorus_conduit/base.png");
    private static final Identifier cageTex = new Identifier("aquarius:textures/entity/chorus_conduit/cage.png");
    private static final Identifier windTex = new Identifier("aquarius:textures/entity/chorus_conduit/wind.png");
    private static final Identifier verticalWindTex = new Identifier("aquarius:textures/entity/chorus_conduit/wind_vertical.png");
    private static final Identifier openEyeTex = new Identifier("aquarius:textures/entity/chorus_conduit/open_eye.png");
    private static final Identifier closedEyeTex = new Identifier("aquarius:textures/entity/chorus_conduit/closed_eye.png");
    private final EntityModel shellModel = new ChorusConduitRenderer.ShellModel();
    private final EntityModel cageModel = new ChorusConduitRenderer.CageModel();
    private final ChorusConduitRenderer.WindModel windModel = new ChorusConduitRenderer.WindModel();
    private final ChorusConduitRenderer.EyeModel eyeModel = new ChorusConduitRenderer.EyeModel();

    public ChorusConduitRenderer() {

    }

    public void render(ChorusConduitBlockEntity conduit, double x, double y, double z, float partialTicks, int p_render_9_) {
        float currentTicks = (float)conduit.ticksExisted + partialTicks;
        float rotPoint;
        if (!conduit.isActive()) {
            rotPoint = conduit.drawTESR(0.0F);
            this.bindTexture(baseTex);
            GlStateManager.pushMatrix();
            GlStateManager.translatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
            GlStateManager.rotatef(rotPoint, 0.0F, 1.0F, 0.0F);
            this.shellModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GlStateManager.popMatrix();
        } else {
            rotPoint = conduit.drawTESR(partialTicks) * 57.295776F;
            float bobPoint = MathHelper.sin(currentTicks * 0.1F) / 2.0F + 0.5F;
            bobPoint += bobPoint * bobPoint;
            this.bindTexture(cageTex);
            GlStateManager.disableCull();
            GlStateManager.pushMatrix();
            GlStateManager.translatef((float)x + 0.5F, (float)y + 0.3F + bobPoint * 0.2F, (float)z + 0.5F);
            GlStateManager.rotatef(rotPoint, 0.5F, 1.0F, 0.5F);
            this.cageModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
            GlStateManager.popMatrix();
            int windFrame = conduit.ticksExisted / 3 % ChorusConduitRenderer.WindModel.windCount;
            this.windModel.setFrame(windFrame);
            int frameLoop = conduit.ticksExisted / (3 * ChorusConduitRenderer.WindModel.windCount) % 3;
            switch(frameLoop) {
                case 0:
                    this.bindTexture(windTex);
                    GlStateManager.pushMatrix();
                    GlStateManager.translatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
                    this.windModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                    GlStateManager.popMatrix();
                    GlStateManager.pushMatrix();
                    GlStateManager.translatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
                    GlStateManager.scalef(0.875F, 0.875F, 0.875F);
                    GlStateManager.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    this.windModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                    GlStateManager.popMatrix();
                    break;
                case 1:
                    this.bindTexture(verticalWindTex);
                    GlStateManager.pushMatrix();
                    GlStateManager.translatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
                    GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
                    this.windModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                    GlStateManager.popMatrix();
                    GlStateManager.pushMatrix();
                    GlStateManager.translatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
                    GlStateManager.scalef(0.875F, 0.875F, 0.875F);
                    GlStateManager.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    this.windModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                    GlStateManager.popMatrix();
                    break;
                case 2:
                    this.bindTexture(windTex);
                    GlStateManager.pushMatrix();
                    GlStateManager.translatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
                    GlStateManager.rotatef(90.0F, 0.0F, 0.0F, 1.0F);
                    this.windModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                    GlStateManager.popMatrix();
                    GlStateManager.pushMatrix();
                    GlStateManager.translatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
                    GlStateManager.scalef(0.875F, 0.875F, 0.875F);
                    GlStateManager.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    this.windModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
                    GlStateManager.popMatrix();
            }

            Entity viewer = MinecraftGame.getInstance().getCameraEntity();
            Polar2f look = Polar2f.field_1335;
            if (viewer != null) {
                look = viewer.getRotationClient();
            }

            if (conduit.isEyeOpen()) {
                this.bindTexture(openEyeTex);
            } else {
                this.bindTexture(closedEyeTex);
            }

            GlStateManager.pushMatrix();
            GlStateManager.translatef((float)x + 0.5F, (float)y + 0.3F + bobPoint * 0.2F, (float)z + 0.5F);
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.rotatef(-look.pitch, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(look.yaw, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
            this.eyeModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.083333336F);
            GlStateManager.popMatrix();
        }

        super.render(conduit, x, y, z, partialTicks, p_render_9_);
    }

    static class EyeModel extends EntityModel {
        private final BoxEntityModel renderer;

        public EyeModel() {
            this.textureWidth = 8;
            this.textureHeight = 8;
            this.renderer = new BoxEntityModel(this, 0, 0);
            this.renderer.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 0, 0.01F);
        }

        public void render(Entity conduit, float x, float y, float z, float p_render_5_, float p_render_6_, float p_render_7_) {
            this.renderer.render(p_render_7_);
        }
    }

    static class WindModel extends EntityModel {
        public static int windCount = 22;
        private final BoxEntityModel[] renderer;
        private int frame;

        public WindModel() {
            this.renderer = new BoxEntityModel[windCount];
            this.textureWidth = 64;
            this.textureHeight = 1024;

            for(int i = 0; i < windCount; ++i) {
                this.renderer[i] = new BoxEntityModel(this, 0, 32 * i);
                this.renderer[i].method_2844(-8.0F, -8.0F, -8.0F, 16, 16, 16);
            }

        }

        public void render(Entity conduit, float x, float y, float z, float p_render_5_, float p_render_6_, float p_render_7_) {
            this.renderer[this.frame].render(p_render_7_);
        }

        public void setFrame(int frame) {
            this.frame = frame;
        }
    }

    static class CageModel extends EntityModel {
        private final BoxEntityModel renderer;

        public CageModel() {
            this.textureWidth = 32;
            this.textureHeight = 16;
            this.renderer = new BoxEntityModel(this, 0, 0);
            this.renderer.method_2844(-4.0F, -4.0F, -4.0F, 8, 8, 8);
        }

        public void render(Entity conduit, float x, float y, float z, float p_render_5_, float p_render_6_, float p_render_7_) {
            this.renderer.render(p_render_7_);
        }
    }

    static class ShellModel extends EntityModel {
        private final BoxEntityModel renderer;

        public ShellModel() {
            this.textureWidth = 32;
            this.textureHeight = 16;
            this.renderer = new BoxEntityModel(this, 0, 0);
            this.renderer.method_2844(-3.0F, -3.0F, -3.0F, 6, 6, 6);
        }

        public void render(Entity conduit, float x, float y, float z, float p_render_5_, float p_render_6_, float p_render_7_) {
            this.renderer.render(p_render_7_);
        }
    }
}
