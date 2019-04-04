package space.bbkr.aquarius.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TridentEntity.class)
public abstract class MixinTridentEntity extends ProjectileEntity {

    @Shadow
    private ItemStack tridentStack;

    public MixinTridentEntity(World world, LivingEntity thrower, ItemStack stack) {
        super(EntityType.TRIDENT, thrower, world);
        this.tridentStack = stack;
    }

    private int getChannelingLevel(ItemStack stack) {
        return EnchantmentHelper.getLevel(Enchantments.CHANNELING, stack);
    }

    @Inject(method = "onEntityHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isThundering()Z"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void onHitEntity(EntityHitResult hit, CallbackInfo ci, Entity target, float damage, Entity thrower, DamageSource source, SoundEvent hitSound, float hitVolume) {
        if ((this.world.isThundering() && EnchantmentHelper.hasChanneling(this.tridentStack)) || (this.world.isRaining() && getChannelingLevel(tridentStack) >= 2) || getChannelingLevel(tridentStack) == 3) {
            BlockPos entityPos = new BlockPos(target.getPos());
            if (this.world.isSkyVisible(entityPos)) {
                LightningEntity lightning = new LightningEntity(this.world, (double)entityPos.getX(), (double)entityPos.getY(), (double)entityPos.getZ(), false);
                lightning.setChanneller(this.getOwner() instanceof ServerPlayerEntity ? (ServerPlayerEntity) this.getOwner() : null);
                ((ServerWorld)this.world).addLightning(lightning);
                hitSound = SoundEvents.ITEM_TRIDENT_THUNDER;
                hitVolume = 5.0F;
            }
        }

        this.playSound(hitSound, hitVolume, 1.0F);
        ci.cancel();
    }

}
