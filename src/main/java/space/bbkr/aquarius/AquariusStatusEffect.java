package space.bbkr.aquarius;

import net.minecraft.entity.effect.StatusEffect;

public class AquariusStatusEffect extends StatusEffect {
    public AquariusStatusEffect(boolean isBadEffect, int color) {
        super(isBadEffect, color);
    }

    public AquariusStatusEffect setIconNew(int x, int y) {
        return (AquariusStatusEffect) super.setIcon(x, y);
    }
}