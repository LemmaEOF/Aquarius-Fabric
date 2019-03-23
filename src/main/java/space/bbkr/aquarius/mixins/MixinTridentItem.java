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
		if (EnchantmentHelper.getLevel(Aquarius.GUARDIAN_SIGHT, stack) > 0) ci.cancel();
	}

	@Override
	public void onUsingTick(World world, LivingEntity user, ItemStack stack, int ticksLeft) {
		if (EnchantmentHelper.getLevel(Aquarius.GUARDIAN_SIGHT, stack) <= 0) return;
		if (ticksLeft < getMaxUseTime(stack) && ticksLeft % 20 == 0) {
			TridentBeamEntity beam = new TridentBeamEntity(world, user);
			beam.method_7474(user, user.pitch, user.yaw, 0.0F, 2.5F, 1.0F);
			beam.pickupType = ProjectileEntity.PickupType.NO_PICKUP;
			world.spawnEntity(beam);
			world.playSoundFromEntity((PlayerEntity)user, beam, SoundEvents.ENTITY_GUARDIAN_ATTACK, SoundCategory.PLAYER, 1.0F, 1.0F);
			stack.applyDamage(1, user);
		}
	}
}
