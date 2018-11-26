package space.bbkr.aquarius;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.MobEffect;
import net.minecraft.gui.ItemGroup;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Aquarius implements ModInitializer {

	public static final Item FLIPPERS = new ItemArmor(ArmorMaterial.TURTLE, EquipmentSlot.FEET, new Item.Builder().creativeTab(ItemGroup.COMBAT));
    public static final Item PRISMARINE_ROD = new Item(new Item.Builder().creativeTab(ItemGroup.MISC));

	public static MobEffect AIR_SWIMMER = new AquariusMobEffect(false, 0x1dd186);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

        System.out.println("More water features!");

		Registry.register(Registry.ITEMS, Identifier.create("aquarius:flippers"), FLIPPERS);
		Registry.register(Registry.ITEMS, Identifier.create("aquarius:prismarine_rod"), PRISMARINE_ROD);
		Registry.register(Registry.POTION_EFFECT_TYPES, Identifier.create("aquarius:air_swimmer"), AIR_SWIMMER);

	}
}
