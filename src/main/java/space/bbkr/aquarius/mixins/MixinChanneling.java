package space.bbkr.aquarius.mixins;

import net.minecraft.enchantment.ChannelingEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ChannelingEnchantment.class)
public class MixinChanneling {

    /**
     * @author b0undarybreaker
     * @reason let there be multiple channeling levels
     * @return 3, the new max for Channeling
     */
    @Overwrite
    public int getMaximumLevel() {
        return 3;
    }
}
