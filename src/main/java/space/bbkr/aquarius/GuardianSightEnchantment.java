package space.bbkr.aquarius;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class GuardianSightEnchantment extends Enchantment {
	protected GuardianSightEnchantment() {
		super(Weight.COMMON, EnchantmentTarget.TRIDENT, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
	}

	public int getMinimumPower(int level) {
		return 1 + (level - 1) * 10;
	}

	public int getMaximumLevel() {
		return 4;
	}

	public boolean differs(Enchantment enchantment) {
		return super.differs(enchantment) && enchantment != Enchantments.RIPTIDE && enchantment != Enchantments.CHANNELING && enchantment != Enchantments.LOYALTY;
	}
}
