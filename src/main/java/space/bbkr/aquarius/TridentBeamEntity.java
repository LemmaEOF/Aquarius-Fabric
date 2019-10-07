package space.bbkr.aquarius;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class TridentBeamEntity extends ProjectileEntity {

	private int ticksExisted;
	private int sightLevel;

	protected TridentBeamEntity(World world) {
		super(Aquarius.TRIDENT_BEAM, world);
		this.setNoGravity(true);
		setSound(SoundEvents.ENTITY_GUARDIAN_ATTACK);
	}

	public TridentBeamEntity(World world, double x, double y, double z) {
		super(Aquarius.TRIDENT_BEAM, x, y, z, world);
		this.setNoGravity(true);
		setSound(SoundEvents.ENTITY_GUARDIAN_ATTACK);
	}

	public TridentBeamEntity(World world, LivingEntity owner, int sightLevel) {
		super(Aquarius.TRIDENT_BEAM, owner, world);
		this.sightLevel = sightLevel;
		this.setNoGravity(true);
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

		super.tick();
	}

	@Override
	protected void onHit(LivingEntity target) {
		super.onHit(target);
		target.damage(DamageSource.MAGIC, 1.5F*sightLevel);
	}

	@Override
	public boolean collides() {
		return false;
	}

}
