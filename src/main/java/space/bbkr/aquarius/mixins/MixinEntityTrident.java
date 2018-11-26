package space.bbkr.aquarius.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.EntityPlayerServer;
import net.minecraft.entity.projectile.EntityProjectile;
import net.minecraft.entity.projectile.EntityTrident;
import net.minecraft.item.ItemStack;
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

@Mixin(EntityTrident.class)
public abstract class MixinEntityTrident extends EntityProjectile {

    @Shadow
    private ItemStack tridentStack;

    public MixinEntityTrident(World world, EntityLiving thrower, ItemStack stack) {
        super(EntityFactory.TRIDENT, thrower, world);
        this.tridentStack = stack;
    }

    private int getChannelingLevel(ItemStack stack) {
        return EnchantmentHelper.getLevel(Enchantments.field_9117, stack);
    }

    @Inject(method = "method_7454",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isThundering()Z"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            remap = false)
    public void onHitEntity(HitResult p_onHitEntity_1_, CallbackInfo ci, Entity var2, float var3, Entity var10, DamageSource var5, Sound var6, float var11) {
        if ((this.world.isThundering() && EnchantmentHelper.method_8228(this.tridentStack)) || (this.world.isRaining() && getChannelingLevel(tridentStack) >= 2) || getChannelingLevel(tridentStack) == 3) {
            BlockPos entityPos = var2.method_5704();
            if (this.world.getSkyLightLevel(entityPos)) {
                EntityLightning lightning = new EntityLightning(this.world, (double)entityPos.getX(), (double)entityPos.getY(), (double)entityPos.getZ(), false);
                lightning.method_6961(this.getOwner() instanceof EntityPlayerServer ? (EntityPlayerServer)this.getOwner() : null);
                this.world.addGlobalEntity(lightning);
                var6 = Sounds.field_14896;
                var11 = 5.0F;
            }
        }

        this.playSoundAtEntity(var6, var11, 1.0F);
        ci.cancel();
    }

}
