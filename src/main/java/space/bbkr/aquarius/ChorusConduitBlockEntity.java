package space.bbkr.aquarius;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstnace;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.Sounds;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ChorusConduitBlockEntity extends ConduitBlockEntity implements Tickable {

    public int ticksExisted;
    private final List<BlockPos> purpurPositions;
    private static final Block[] validBlocks = new Block[]{Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR};
    private float rotationPoint;
    private boolean active;
    private boolean eyeOpen;
    private LivingEntity target;
    private UUID targetUuid;
    private long nextSoundTime;

    public ChorusConduitBlockEntity() {
        this(Aquarius.CHORUS_CONDUIT_BE);
    }

    public ChorusConduitBlockEntity(BlockEntityType<?> type) {
        super(type);
        this.purpurPositions = Lists.newArrayList();
    }

    public void tick() {
        ++this.ticksExisted;
        long time = this.world.getTime();
        if (time % 40L == 0L) {
            this.setActive(this.shouldBeActive());
            if (!this.world.isRemote && this.isActive()) {
                this.addEffectsToPlayers();
            }
        }

        if (time % 80L == 0L && this.method_11065()) {
            this.method_11067(Sounds.BLOCK_CONDUIT_AMBIENT);
        }

        if (time > this.nextSoundTime && this.method_11065()) {
            this.nextSoundTime = time + 60L + (long)this.world.getRandom().nextInt(40);
            this.method_11067(Sounds.BLOCK_CONDUIT_AMBIENT_SHORT);
        }

        if (this.world.isRemote) {
            this.updateClientTarget();
            this.spawnParticles();
            if (this.isActive()) {
                ++this.rotationPoint;
            }
        }

    }

    private boolean shouldBeActive() {
        this.purpurPositions.clear();

        int xOffset;
        int yOffset;
        int zOffset;
//        for(xOffset = -1; xOffset <= 1; ++xOffset) {
//            for(yOffset = -1; yOffset <= 1; ++yOffset) {
//                for(zOffset = -1; zOffset <= 1; ++zOffset) {
//                    if (xOffset != 0 || yOffset != 0 || zOffset != 0) {
//                        BlockPos pos = this.pos.add(xOffset, yOffset, zOffset);
//                        if (!this.world.hasWater(pos)) {
//                            return false;
//                        }
//                    }
//                }
//            }
//        }

        for(xOffset = -2; xOffset <= 2; ++xOffset) {
            for(yOffset = -2; yOffset <= 2; ++yOffset) {
                for(zOffset = -2; zOffset <= 2; ++zOffset) {
                    int xAbs = Math.abs(xOffset);
                    int yAbs = Math.abs(yOffset);
                    int zAbs = Math.abs(zOffset);
                    if ((xOffset == 0 && (yAbs == 2 || zAbs == 2) || yOffset == 0 && (xAbs == 2 || zAbs == 2) || zOffset == 0 && (xAbs == 2 || yAbs == 2))) {
                        BlockPos pos = this.pos.add(xOffset, yOffset, zOffset);
                        BlockState state = this.world.getBlockState(pos);
                        for(Block block : validBlocks) {
                            if (state.getBlock() == block) {
                                this.purpurPositions.add(pos);
                            }
                        }
                    }
                }
            }
        }

        this.setEyeOpen(this.purpurPositions.size() >= 42);
        return this.purpurPositions.size() >= 16;
    }

    private void addEffectsToPlayers() {
        int listSize = this.purpurPositions.size();
        int range = listSize / 7 * 16;
        int posX = this.pos.getX();
        int posY = this.pos.getY();
        int posZ = this.pos.getZ();
        BoundingBox aabb = (new BoundingBox((double)posX, (double)posY, (double)posZ, (double)(posX + 1), (double)(posY + 1), (double)(posZ + 1))).expand((double)range).expand(0.0D, (double)this.world.getHeight(), 0.0D);
        List<PlayerEntity> players = this.world.getVisibleEntities(PlayerEntity.class, aabb);
        if (!players.isEmpty()) {

            for (PlayerEntity player : players) {
                if (this.pos.distanceTo(new BlockPos(player)) <= (double)range) {
                    player.addPotionEffect(new StatusEffectInstnace(Aquarius.ATLANTEAN, 260, 0, true, true));
                }
            }

        }
    }

    private void updateClientTarget() {
        if (this.targetUuid == null) {
            this.target = null;
        } else if (this.target == null || !this.target.getUuid().equals(this.targetUuid)) {
            this.target = this.findExistingTarget();
            if (this.target == null) {
                this.targetUuid = null;
            }
        }

    }

    private BoundingBox getAreaOfEffect() {
        int posX = this.pos.getX();
        int posY = this.pos.getY();
        int posZ = this.pos.getZ();
        return (new BoundingBox((double)posX, (double)posX, (double)posZ, (double)(posX + 1), (double)(posY + 1), (double)(posZ + 1))).expand(8.0D);
    }

    @Nullable
    private LivingEntity findExistingTarget() {
        List<LivingEntity> entities = this.world.getEntities(LivingEntity.class, this.getAreaOfEffect(), (entity) -> entity.getUuid().equals(this.targetUuid));
        return entities.size() == 1 ? entities.get(0) : null;
    }

    private void spawnParticles() {
        Random rand = this.world.random;
        float rot = MathHelper.sin((float)(this.ticksExisted + 35) * 0.1F) / 2.0F + 0.5F;
        rot = (rot * rot + rot) * 0.3F;
        Vec3d vec = new Vec3d((double)((float)this.pos.getX() + 0.5F), (double)((float)this.pos.getY() + 1.5F + rot), (double)((float)this.pos.getZ() + 0.5F));

        float distX;
        float distY;
        for(BlockPos pos : purpurPositions) {
            if (rand.nextInt(50) == 0) {
                distX = -0.5F + rand.nextFloat();
                distY = -2.0F + rand.nextFloat();
                float distZ = -0.5F + rand.nextFloat();
                BlockPos relPos = pos.subtract(this.pos);
                Vec3d lvt_10_1_ = (new Vec3d((double)distX, (double)distY, (double)distZ)).add((double)relPos.getX(), (double)relPos.getY(), (double)relPos.getZ());
                this.world.method_8406(ParticleTypes.NAUTILUS, vec.x, vec.y, vec.z, lvt_10_1_.x, lvt_10_1_.y, lvt_10_1_.z);
            }
        }

        if (this.target != null) {
            Vec3d playerEyes = new Vec3d(this.target.x, this.target.y + (double)this.target.getEyeHeight(), this.target.z);
            float randVel = (-0.5F + rand.nextFloat()) * (3.0F + this.target.width);
            distX = -1.0F + rand.nextFloat() * this.target.height;
            distY = (-0.5F + rand.nextFloat()) * (3.0F + this.target.width);
            Vec3d velocity = new Vec3d((double)randVel, (double)distX, (double)distY);
            this.world.method_8406(ParticleTypes.NAUTILUS, playerEyes.x, playerEyes.y, playerEyes.z, velocity.x, velocity.y, velocity.z);
        }

    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isEyeOpen() {
        return this.eyeOpen;
    }

    private void setActive(boolean active) {
        if (active != this.active) {
            this.method_11067(active ? Sounds.BLOCK_CONDUIT_ACTIVATE : Sounds.BLOCK_CONDUIT_DEACTIVATE);
        }

        this.active = active;
    }

    private void setEyeOpen(boolean open) {
        this.eyeOpen = open;
    }

    public float drawTESR(float ticks) {
        return (this.rotationPoint + ticks) * -0.0375F;
    }
}
