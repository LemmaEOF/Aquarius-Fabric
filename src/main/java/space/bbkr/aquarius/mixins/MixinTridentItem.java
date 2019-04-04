package space.bbkr.aquarius.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.bbkr.aquarius.Aquarius;
import space.bbkr.aquarius.TridentBeamEntity;

@Mixin(TridentItem.class)
public abstract class MixinTridentItem extends Item {

	@Shadow public abstract int getMaxUseTime(ItemStack itemStack_1);

	public MixinTridentItem(Settings settings) {
		super(settings);
	}

	@Inject(method = "onItemStopUsing", at = @At(value = "HEAD"), cancellable = true)
	public void makeTridentPiercing(ItemStack stack, World world, LivingEntity entity, int usedTicks, CallbackInfo ci) {
		if (EnchantmentHelper.getLevel(Aquarius.GUARDIAN_SIGHT, stack) > 0) {
			world.playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.PLAYER, 0.5f, 0.95f);
			ci.cancel();
		}
	}

	@Override
	public void onUsingTick(World world, LivingEntity user, ItemStack stack, int ticksLeft) {
		int sightLevel = EnchantmentHelper.getLevel(Aquarius.GUARDIAN_SIGHT, stack);
		if (sightLevel <= 0) return;
		if (ticksLeft < getMaxUseTime(stack) && ticksLeft % 20 == 0) {
			TridentBeamEntity beam = new TridentBeamEntity(world, user, sightLevel);
			beam.method_7474(user, user.pitch, user.yaw, 0.0F, 2.5F, 1.0F);
			beam.pickupType = ProjectileEntity.PickupType.NO_PICKUP;
			world.spawnEntity(beam);
			if (ticksLeft == getMaxUseTime(stack) - 20) world.playSoundFromEntity((PlayerEntity)user, beam, SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.PLAYER, 0.8F, 1.0F);
			stack.applyDamage(sightLevel, user, (entity) -> entity.method_20236(user.getActiveHand()));
		}
	}

	@Override
	public boolean canRepair(ItemStack target, ItemStack repairMat) {
		return repairMat.getItem() == Aquarius.PRISMARINE_ROD;
	}
}
