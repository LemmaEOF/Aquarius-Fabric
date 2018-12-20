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
import space.bbkr.aquarius.Aquarius;

@Mixin(PlayerEntity.class)
public abstract class MixinSwimming extends LivingEntity {

    @Shadow public abstract boolean isSwimming();

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot equipmentSlot);

    public MixinSwimming(EntityType<?> factory, World world) {
        super(factory, world);
    }
    int maxSwimTime = 10;
    int swimTime = 0;
    @Inject(method = "method_7330", at = @At("HEAD"))
    private void updateTurtleHelmet(CallbackInfo ci) {
        ItemStack stackFeet = this.getEquippedStack(EquipmentSlot.FEET);
        if (stackFeet.getItem() == Aquarius.FLIPPERS) {
            if (this.method_5721()) {
                swimTime = 0;
                this.addPotionEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 20, 0, true, false, true));
            }
            else {
                if (swimTime >= maxSwimTime) {
                    this.addPotionEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 0, true, false, true));
                } else swimTime++;
            }
        }
    }

    @Override
    public boolean method_5869() {
        return (this.field_6000 && this.isInsideWater()) || this.hasPotionEffect(Aquarius.ATLANTEAN);
    }

    @Override
    public boolean method_5721() {
        if (this.hasPotionEffect(Aquarius.ATLANTEAN)) return true;
        return super.method_5721();
    }

    @Inject(method = "method_5790", at = @At("TAIL"))
    public void updateAirSwimming(CallbackInfo ci) {
        if (this.hasPotionEffect(Aquarius.ATLANTEAN)) {
            this.method_5796(this.isSprinting() && !this.hasVehicle());
            this.insideWater = this.isSwimming();
            if (this.isSwimming()) {
                this.fallDistance = 0.0F;
                Vec3d look = this.getRotationVecClient();
                move(MovementType.SELF, look.x/4, look.y/4, look.z/4);
            }
        }
    }



}
