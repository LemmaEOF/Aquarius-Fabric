package space.bbkr.aquarius;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.fabricmc.fabric.client.render.BlockEntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Aquarius implements ModInitializer {

	public static final Block CHORUS_CONDUIT = register("chorus_conduit", new ChorusConduitBlock(FabricBlockSettings.create(Material.GLASS).setStrength(3.0F, 3.0F).setLuminance(15).build()), ItemGroup.MISC);
	public static final Item FLIPPERS = register("flippers", new ArmorItem(ArmorMaterials.TURTLE, EquipmentSlot.FEET, new Item.Settings().itemGroup(ItemGroup.COMBAT)));
    public static final Item PRISMARINE_ROD = register("prismarine_rod", new Item(new Item.Settings().itemGroup(ItemGroup.MISC)));
    public static BlockEntityType<ChorusConduitBlockEntity> CHORUS_CONDUIT_BE;

	public static StatusEffect ATLANTEAN = register("atlantean", new AquariusStatusEffect(false, 0x1dd186).setIconNew(9, 0));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

        System.out.println("More water features!");
		Registry.register(Registry.BLOCK_ENTITIES, Identifier.create("aquarius:chorus_conduit"), CHORUS_CONDUIT_BE);
		BlockEntityRendererRegistry.INSTANCE.register(ChorusConduitBlockEntity.class, new ChorusConduitRenderer());
	}

	public static Block register(String name, Block block, ItemGroup tab) {
		Registry.register(Registry.BLOCKS, "aquarius:" + name, block);
		BlockItem item = new BlockItem(block, new Item.Settings().itemGroup(tab));
		item.registerBlockItemMap(Item.BLOCK_ITEM_MAP, item);
		register(name, item);
		return block;
	}

	public static Item register(String name, Item item) {
		Registry.register(Registry.ITEMS, "gluon:" + name, item);
		return item;
	}

	public static StatusEffect register(String name, StatusEffect effect) {
		Registry.register(Registry.POTION_EFFECT_TYPES, "gluon:" + name, effect);
		return effect;
	}
}
