package space.bbkr.aquarius.mixins;

import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.bbkr.aquarius.Aquarius;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {

    @Shadow public abstract boolean isSwimming();

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot equipmentSlot);

    @Shadow protected boolean isInWater;

    protected MixinPlayerEntity(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    private int maxSwimCooldown = 10;
    private int swimCooldownTime = 0;
    private int swimDamageTime = 0;
    @Inject(method = "updateTurtleHelmet", at = @At("HEAD"))
    private void updateTurtleHelmet(CallbackInfo ci) {
        ItemStack stackFeet = this.getEquippedStack(EquipmentSlot.FEET);
        if (stackFeet.getItem() == Aquarius.FLIPPERS) {
            if (this.isInsideWaterOrRain()) {
                swimCooldownTime = 0;
                this.addPotionEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 20, 0, true, false, true));
                if (!world.isClient) {
                    swimDamageTime++;
                    if (swimDamageTime % 20 == 0) {
                        swimDamageTime = 0;
                        stackFeet.damage(1, this, (entity) -> entity.sendEquipmentBreakStatus(EquipmentSlot.FEET));
                    }
                }
            }
            else {
                if (swimCooldownTime >= maxSwimCooldown) {
                    this.addPotionEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 0, true, false, true));
                } else swimCooldownTime++;
            }
        }
    }

//    @Override
//    public boolean isInsideWater() {
//        if (hasStatusEffect(Aquarius.ATLANTEAN)) return true;
//        return super.isInsideWater();
//    }

//    @Inject(method = "updateInWater", at = @At("RETURN"))
//    private void updateAtlanteanWater(CallbackInfoReturnable<Boolean> ci) {
//        if (hasStatusEffect(Aquarius.ATLANTEAN)) this.isInWater = true;
//    }

    @Override
    public boolean isInsideWaterOrRain() {
        if (this.hasStatusEffect(Aquarius.ATLANTEAN)) return true;
        return super.isInsideWaterOrRain();
    }

    @Inject(method = "updateSwimming", at = @At("TAIL"))
    private void updateAirSwimming(CallbackInfo ci) {
        if (this.hasStatusEffect(Aquarius.ATLANTEAN)) {
            this.setSwimming(this.isSprinting() && !this.hasVehicle());
            this.insideWater = this.isSwimming();
            if (this.isSwimming()) {
                this.fallDistance = 0.0F;
                Vec3d look = this.getRotationVector();
                move(MovementType.SELF, new Vec3d(look.x/4, look.y/4, look.z/4));
            }
        }
    }

}
