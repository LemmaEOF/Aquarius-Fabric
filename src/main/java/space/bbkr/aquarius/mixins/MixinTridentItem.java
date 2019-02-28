package space.bbkr.aquarius.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import space.bbkr.aquarius.Aquarius;

@Mixin(TridentItem.class)
public class MixinTridentItem {

	@Inject(method = "onItemStopUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;method_7437(Lnet/minecraft/entity/Entity;FFFFF)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	public void makeTridentPiercing(ItemStack stack, World world, LivingEntity entity, int usedTicks, CallbackInfo ci,
									PlayerEntity player, int timeLeft, int riptideLevel, TridentEntity trident) {
		int piercing = EnchantmentHelper.getLevel(Aquarius.TRIDENT_PIERCING, stack);
		if (piercing > 0) trident.setFlagByte((byte)piercing);
	}
}
