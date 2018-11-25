package space.bbkr.aquarius.mixins;

import net.minecraft.entity.EntityFactory;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionEffectTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.bbkr.aquarius.Aquarius;

@Mixin(EntityPlayer.class)
public abstract class MixinFlippers extends EntityLiving {

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot equipmentSlot);

    public MixinFlippers(EntityFactory<?> p_i48577_1_, World p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @Inject(method = "method_7330", at = @At("HEAD"))
    private void updateTurtleHelmet(CallbackInfo ci) {
        ItemStack stackFeet = this.getEquippedStack(EquipmentSlot.FEET);
        if (stackFeet.getItem() == Aquarius.FLIPPERS) {
            if (this.isSwimming()) this.addPotionEffect(new PotionEffect(PotionEffectTypes.field_5900, 20, 0, true, false, true));
            else this.addPotionEffect(new PotionEffect(PotionEffectTypes.field_5909, 20, 0, true, false, true));
        }
    }
}
