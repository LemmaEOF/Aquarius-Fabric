package space.bbkr.aquarius;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.gui.CreativeTab;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Aquarius implements ModInitializer {

	public static final Item FLIPPERS = new ItemArmor(ArmorMaterial.TURTLE, EquipmentSlot.FEET, new Item.Builder().creativeTab(CreativeTab.COMBAT));
    public static final Item PRISMARINE_ROD = new Item(new Item.Builder().creativeTab(CreativeTab.MISC));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

        System.out.println("More water features!");

		Registry.ITEMS.add(Identifier.create("aquarius:flippers"), FLIPPERS);
		Registry.ITEMS.add(Identifier.create("aquarius:prismarine_rod"), PRISMARINE_ROD);

	}
}
