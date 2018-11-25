package space.bbkr.aquarius.mixins;

import net.minecraft.class_1905;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(class_1905.class)
public class MixinChanneling {

    /**
     * @author b0undarybreaker
     * @reason let there be multiple channeling levels
     * @return 3, the new max for Channeling
     */
    @Overwrite
    public int getHighestLevel() {
        return 3;
    }
}
