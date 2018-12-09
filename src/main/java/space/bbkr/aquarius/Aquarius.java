package space.bbkr.aquarius;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.helpers.FabricBlockBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Aquarius implements ModInitializer {

	public static final Block CHORUS_CONDUIT = new ChorusConduitBlock(FabricBlockBuilder.create(Material.GLASS).setStrength(3.0F, 3.0F).setLuminance(15).build());
	public static final Item FLIPPERS = new ArmorItem(ArmorMaterials.TURTLE, EquipmentSlot.FEET, new Item.Settings().itemGroup(ItemGroup.COMBAT));
    public static final Item PRISMARINE_ROD = new Item(new Item.Settings().itemGroup(ItemGroup.MISC));
    public static BlockEntityType<ChorusConduitBlockEntity> CHORUS_CONDUIT_BE;

	public static StatusEffect ATLANTEAN = new AquariusStatusEffect(false, 0x1dd186).setIconNew(9, 0);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

        System.out.println("More water features!");

        Registry.register(Registry.BLOCKS, Identifier.create("aquarius:chorus_conduit"), CHORUS_CONDUIT);
		Registry.register(Registry.ITEMS, Identifier.create("aquarius:flippers"), FLIPPERS);
		Registry.register(Registry.ITEMS, Identifier.create("aquarius:prismarine_rod"), PRISMARINE_ROD);
		Registry.register(Registry.POTION_EFFECT_TYPES, Identifier.create("aquarius:atlantean"), ATLANTEAN);
		Registry.register(Registry.BLOCK_ENTITIES, Identifier.create("aquarius:chorus_conduit"), CHORUS_CONDUIT_BE);
		BlockEntityRendererRegistry.INSTANCE.register(ChorusConduitBlockEntity.class, new ChorusConduitRenderer());

	}
}
