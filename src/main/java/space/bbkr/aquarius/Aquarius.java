package space.bbkr.aquarius;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class Aquarius implements ModInitializer {

	public static final Block CHORUS_CONDUIT = register("chorus_conduit", new ChorusConduitBlock(FabricBlockSettings.of(Material.GLASS).strength(3.0F, 3.0F).lightLevel(15).build()), ItemGroup.MISC);
	public static final Item FLIPPERS = register("flippers", new ArmorItem(ArmorMaterials.TURTLE, EquipmentSlot.FEET, new Item.Settings().itemGroup(ItemGroup.COMBAT)));
    public static final Item PRISMARINE_ROD = register("prismarine_rod", new Item(new Item.Settings().itemGroup(ItemGroup.MISC)));
    public static BlockEntityType<ChorusConduitBlockEntity> CHORUS_CONDUIT_BE = register("chorus_conduit", ChorusConduitBlockEntity::new);

	public static StatusEffect ATLANTEAN = register("atlantean", new AquariusStatusEffect(false, 0x1dd186));

	@Override
	public void onInitialize() {
	}

	public static Block register(String name, Block block, ItemGroup tab) {
		Registry.register(Registry.BLOCK, "aquarius:" + name, block);
		BlockItem item = new BlockItem(block, new Item.Settings().itemGroup(tab));
		register(name, item);
		return block;
	}

	public static Item register(String name, Item item) {
		Registry.register(Registry.ITEM, "aquarius:" + name, item);
		return item;
	}

	public static StatusEffect register(String name, StatusEffect effect) {
		Registry.register(Registry.STATUS_EFFECT, "aquarius:" + name, effect);
		return effect;
	}

	public static BlockEntityType register(String name, Supplier<BlockEntity> be) {
		return Registry.register(Registry.BLOCK_ENTITY, "aquarius:" + name, BlockEntityType.Builder.create(be).build(null));
	}
}
