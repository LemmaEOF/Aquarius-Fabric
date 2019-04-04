package space.bbkr.aquarius.mixins;

import net.minecraft.enchantment.ChannelingEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChannelingEnchantment.class)
public class MixinChanneling {

    @Inject(method = "getMaximumLevel", at = @At("HEAD"), cancellable = true)
    public void getMaximumLevel(CallbackInfoReturnable cir) {
        cir.setReturnValue(3);
    }
}
