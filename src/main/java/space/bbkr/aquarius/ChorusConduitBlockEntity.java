package space.bbkr.aquarius;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ChorusConduitBlockEntity extends ConduitBlockEntity implements Tickable {

    public int ticksExisted;
    private final List<BlockPos> purpurPositions;
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
            if (!this.world.isClient && this.isActive()) {
                this.addEffectsToPlayers();
            }
        }

        if (time % 80L == 0L && this.isActive()) {
            this.playSound(SoundEvents.BLOCK_CONDUIT_AMBIENT);
        }

        if (time > this.nextSoundTime && this.isActive()) {
            this.nextSoundTime = time + 60L + (long)this.world.getRandom().nextInt(40);
            this.playSound(SoundEvents.BLOCK_CONDUIT_AMBIENT_SHORT);
        }

        if (this.world.isClient) {
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

        for(xOffset = -2; xOffset <= 2; ++xOffset) {
            for(yOffset = -2; yOffset <= 2; ++yOffset) {
                for(zOffset = -2; zOffset <= 2; ++zOffset) {
                    int xAbs = Math.abs(xOffset);
                    int yAbs = Math.abs(yOffset);
                    int zAbs = Math.abs(zOffset);
                    if ((xOffset == 0 && (yAbs == 2 || zAbs == 2) || yOffset == 0 && (xAbs == 2 || zAbs == 2) || zOffset == 0 && (xAbs == 2 || yAbs == 2))) {
                        BlockPos pos = this.pos.add(xOffset, yOffset, zOffset);
                        BlockState state = this.world.getBlockState(pos);
                        if (Aquarius.CHORUS_CONDUIT_ACTIVATORS.contains(state.getBlock())) {
                            this.purpurPositions.add(pos);
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
        Box aabb = (new Box(posX, posY, posZ, posX + 1, posY + 1, posZ + 1)).expand(range).expand(0.0D, this.world.getHeight(), 0.0D);
        List<PlayerEntity> players = this.world.getEntities(PlayerEntity.class, aabb, null);
        if (!players.isEmpty()) {

            for (PlayerEntity player : players) {
                Vec3d position = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
                if (position.distanceTo(player.getPos()) <= (double)range) {
                    player.addStatusEffect(new StatusEffectInstance(Aquarius.ATLANTEAN, 260, 0, true, true));
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

    private Box getAreaOfEffect() {
        int posX = this.pos.getX();
        int posY = this.pos.getY();
        int posZ = this.pos.getZ();
        return (new Box(posX, posX, posZ, posX + 1, posY + 1, posZ + 1)).expand(8.0D);
    }

    private LivingEntity findExistingTarget() {
        List<LivingEntity> entities = this.world.getEntities(LivingEntity.class, this.getAreaOfEffect(), (entity) -> entity.getUuid().equals(this.targetUuid));
        return entities.size() == 1 ? entities.get(0) : null;
    }

    private void spawnParticles() {
        Random rand = this.world.random;
        float rot = MathHelper.sin((float)(this.ticksExisted + 35) * 0.1F) / 2.0F + 0.5F;
        rot = (rot * rot + rot) * 0.3F;
        Vec3d vec = new Vec3d((float)this.pos.getX() + 0.5F, (float)this.pos.getY() + 1.5F + rot, (float)this.pos.getZ() + 0.5F);

        float distX;
        float distY;
        for(BlockPos pos : purpurPositions) {
            if (rand.nextInt(50) == 0) {
                distX = -0.5F + rand.nextFloat();
                distY = -2.0F + rand.nextFloat();
                float distZ = -0.5F + rand.nextFloat();
                BlockPos relPos = pos.subtract(this.pos);
                Vec3d particlePos = (new Vec3d(distX, distY, distZ)).add(relPos.getX(), relPos.getY(), relPos.getZ());
                this.world.addParticle(ParticleTypes.NAUTILUS, vec.x, vec.y, vec.z, particlePos.x, particlePos.y, particlePos.z);
            }
        }

        if (this.target != null) {
            Vec3d playerEyes = new Vec3d(this.target.getX(), this.target.getY() + (double)this.target.getEyeHeight(target.getPose()), this.target.getZ());
            float randVel = (-0.5F + rand.nextFloat()) * (3.0F + this.target.getWidth());
            distX = -1.0F + rand.nextFloat() * this.target.getHeight();
            distY = (-0.5F + rand.nextFloat()) * (3.0F + this.target.getWidth());
            Vec3d velocity = new Vec3d(randVel, distX, distY);
            this.world.addParticle(ParticleTypes.NAUTILUS, playerEyes.x, playerEyes.y, playerEyes.z, velocity.x, velocity.y, velocity.z);
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
            this.playSound(active ? SoundEvents.BLOCK_CONDUIT_ACTIVATE : SoundEvents.BLOCK_CONDUIT_DEACTIVATE);
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
