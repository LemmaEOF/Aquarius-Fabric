package space.bbkr.aquarius;

import net.minecraft.entity.effect.MobEffect;

public class AquariusMobEffect extends MobEffect {
    public AquariusMobEffect(boolean isBadEffect, int color) {
        super(isBadEffect, color);
    }

    public AquariusMobEffect setIconNew(int x, int y) {
        return (AquariusMobEffect) super.setIcon(x, y);
    }
}