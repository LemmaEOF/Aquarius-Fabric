package space.bbkr.aquarius;

import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class TridentBeamEntity extends ProjectileEntity {

	private int ticksExisted;
	private int sightLevel;

	protected TridentBeamEntity(World world) {
		super(Aquarius.TRIDENT_BEAM, world);
		this.setUnaffectedByGravity(true);
		setSound(SoundEvents.ENTITY_GUARDIAN_ATTACK);
	}

	public TridentBeamEntity(double x, double y, double z, World world) {
		super(Aquarius.TRIDENT_BEAM, x, y, z, world);
		this.setUnaffectedByGravity(true);
		setSound(SoundEvents.ENTITY_GUARDIAN_ATTACK);
	}

	public TridentBeamEntity(World world, LivingEntity owner, int sightLevel) {
		super(Aquarius.TRIDENT_BEAM, owner, world);
		this.sightLevel = sightLevel;
		this.setUnaffectedByGravity(true);
		setSound(SoundEvents.ENTITY_GUARDIAN_ATTACK);
	}

	@Override
	protected ItemStack asItemStack() {
		return ItemStack.EMPTY;
	}

	@Override
	public void tick() {
		if (!this.world.isClient) {
			ticksExisted++;
			if (this.ticksExisted >= 500) {
				this.remove();
				return;
			}
		}
		this.world.addParticle(ParticleTypes.ENCHANTED_HIT, this.x + (this.random.nextDouble() - 0.5D) * (double)this.getWidth(), this.y + this.random.nextDouble() * (double)this.getHeight(), this.z + (this.random.nextDouble() - 0.5D) * (double)this.getWidth(), 1.0D, 1.0D, 1.0D);

		super.tick();
	}

	@Override
	protected void onHit(LivingEntity target) {
		super.onHit(target);
		target.damage(DamageSource.MAGIC, 1.5F*sightLevel);
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return new EntitySpawnS2CPacket(this, 1 + (this.getOwner() == null ? this.getEntityId() : this.getOwner().getEntityId()));
	}

	@Override
	public boolean collides() {
		return false;
	}

}
