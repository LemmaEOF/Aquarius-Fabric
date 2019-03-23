package space.bbkr.aquarius;

import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class TridentBeamEntity extends ProjectileEntity {

	private int ticksInAir;

	protected TridentBeamEntity(World world) {
		super(Aquarius.TRIDENT_BEAM, world);
		this.setUnaffectedByGravity(true);
	}

	public TridentBeamEntity(double x, double y, double z, World world) {
		super(Aquarius.TRIDENT_BEAM, x, y, z, world);
		this.setUnaffectedByGravity(true);
	}

	public TridentBeamEntity(World world, LivingEntity owner) {
		super(Aquarius.TRIDENT_BEAM, owner, world);
	}

	@Override
	protected ItemStack asItemStack() {
		return ItemStack.EMPTY;
	}

	@Override
	public void tick() {
		if (!this.world.isClient)
		{
			if (!this.onGround)
			{
				++this.ticksInAir;
			}

			if (this.ticksInAir == 500)
			{
				this.invalidate();

				return;
			}
		}

		super.tick();
	}

	@Override
	protected void onHit(LivingEntity target) {
		super.onHit(target);
		target.damage(DamageSource.MAGIC, 3.0F);
	}

	@Override
	public Packet<?> createSpawnPacket()
	{
		return new EntitySpawnS2CPacket(this, 1 + (this.getOwner() == null ? this.getEntityId() : this.getOwner().getEntityId()));
	}
}
