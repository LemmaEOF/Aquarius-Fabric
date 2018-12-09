package space.bbkr.aquarius.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.Sound;
import net.minecraft.sound.Sounds;
import net.minecraft.util.HitResult;
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

    @Inject(method = "method_7454",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isThundering()Z"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            remap = false)
    public void onHitEntity(HitResult p_onHitEntity_1_, CallbackInfo ci, Entity var2, float var3, Entity var10, DamageSource var5, Sound var6, float var11) {
        if ((this.world.isThundering() && EnchantmentHelper.hasChanneling(this.tridentStack)) || (this.world.isRaining() && getChannelingLevel(tridentStack) >= 2) || getChannelingLevel(tridentStack) == 3) {
            BlockPos entityPos = var2.getPos();
            if (this.world.getSkyLightLevel(entityPos)) {
                LightningEntity lightning = new LightningEntity(this.world, (double)entityPos.getX(), (double)entityPos.getY(), (double)entityPos.getZ(), false);
                lightning.method_6961(this.getOwner() instanceof ServerPlayerEntity ? (ServerPlayerEntity) this.getOwner() : null);
                this.world.addGlobalEntity(lightning);
                var6 = Sounds.ITEM_TRIDENT_THUNDER;
                var11 = 5.0F;
            }
        }

        this.playSoundAtEntity(var6, var11, 1.0F);
        ci.cancel();
    }

}
