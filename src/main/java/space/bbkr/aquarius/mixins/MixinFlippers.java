package space.bbkr.aquarius.mixins;

import net.minecraft.entity.*;
import net.minecraft.entity.effect.MobEffectInstance;
import net.minecraft.entity.effect.MobEffects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.bbkr.aquarius.Aquarius;

@Mixin(EntityPlayer.class)
public abstract class MixinFlippers extends EntityLiving {

    private boolean wasLastAirSwimming = false;

    @Shadow public abstract boolean isSwimming();

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot equipmentSlot);

    public MixinFlippers(EntityFactory<?> factory, World world) {
        super(factory, world);
    }

    @Inject(method = "method_7330", at = @At("HEAD"))
    private void updateTurtleHelmet(CallbackInfo ci) {
        ItemStack stackFeet = this.getEquippedStack(EquipmentSlot.FEET);
        if (stackFeet.getItem() == Aquarius.FLIPPERS) {
            if (this.isInsideWater()) this.addPotionEffect(new MobEffectInstance(MobEffects.field_5900, 20, 0, true, false, true));
            else this.addPotionEffect(new MobEffectInstance(MobEffects.field_5909, 20, 0, true, false, true));
        }
    }

    @Inject(method = "method_5790", at = @At("TAIL"))
    public void updateAirSwimming(CallbackInfo ci) {
        if (this.hasPotionEffect(Aquarius.AIR_SWIMMER)) {
            this.method_5796(this.isSprinting() && !this.hasVehicle());
            this.wasLastAirSwimming = (this.isSwimming());
            this.insideWater = this.isSwimming();
            if (this.isSwimming()) {
                this.fallDistance = 0.0F;
                Vec3d look = this.getRotationVecClient();
                //TODO: figure out how to get this to only happen when there's key input
                move(MovementType.SELF, look.x/4, look.y/4, look.z/4);
            }
        }
    }



}
