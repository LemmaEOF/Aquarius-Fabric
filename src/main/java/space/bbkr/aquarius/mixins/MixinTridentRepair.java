package space.bbkr.aquarius.mixins;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.bbkr.aquarius.Aquarius;

@Mixin(Item.class)
public class MixinTridentRepair {

    @Inject(method = "canRepair", at = @At(value = "HEAD"), cancellable = true)
    private void canRepair(ItemStack var1, ItemStack var2, CallbackInfoReturnable<Boolean> cir) {
        if (var1.getItem() instanceof TridentItem) {
            cir.setReturnValue(var2.getItem() == Aquarius.PRISMARINE_ROD);
        }
    }
}
