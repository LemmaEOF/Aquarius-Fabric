package space.bbkr.aquarius;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.item.*;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.loot.condition.RandomChanceLootCondition;
import net.minecraft.world.loot.entry.ItemEntry;

import java.util.function.Supplier;

public class Aquarius implements ModInitializer {
	
	public static final String MODID = "aquarius";

	public static final Block CHORUS_CONDUIT = register("chorus_conduit", new ChorusConduitBlock(FabricBlockSettings.of(Material.GLASS).strength(3.0F, 3.0F).lightLevel(15).build()), ItemGroup.MISC);
	public static final Item FLIPPERS = register("flippers", new ArmorItem(ArmorMaterials.TURTLE, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT)));
    public static final Item PRISMARINE_ROD = register("prismarine_rod", new Item(new Item.Settings().group(ItemGroup.MISC)));
    public static final BlockEntityType<ChorusConduitBlockEntity> CHORUS_CONDUIT_BE = register("chorus_conduit", ChorusConduitBlockEntity::new, CHORUS_CONDUIT);
    public static final Tag<Block> CHORUS_CONDUIT_ACTIVATORS = TagRegistry.block(new Identifier(MODID, "chorus_conduit_activators"));

	public static final StatusEffect ATLANTEAN = register("atlantean", new AquariusStatusEffect(StatusEffectType.BENEFICIAL, 0x1dd186));

	public static final Enchantment GUARDIAN_SIGHT = register("guardian_sight", new GuardianSightEnchantment());

	public static final EntityType<TridentBeamEntity> TRIDENT_BEAM = register("trident_beam", EntityCategory.MISC, EntityDimensions.changing(0.5F, 0.5F), ((entityType, world) -> new TridentBeamEntity(world)));

	@Override
	public void onInitialize() {
		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
			if (id.equals(new Identifier("minecraft", "chests/shipwreck_treasure"))) {
				supplier.withPool(FabricLootPoolBuilder.builder()
						.withEntry(ItemEntry.builder(Items.HEART_OF_THE_SEA)
								.withCondition(RandomChanceLootCondition.builder(0.1f))
						).withEntry(ItemEntry.builder(Items.NAUTILUS_SHELL).withCondition(RandomChanceLootCondition.builder(0.33f)))
				);
			}
		});
	}

	public static Block register(String name, Block block, ItemGroup tab) {
		Registry.register(Registry.BLOCK, new Identifier(MODID, name), block);
		BlockItem item = new BlockItem(block, new Item.Settings().group(tab));
		register(name, item);
		return block;
	}

	public static Item register(String name, Item item) {
		Registry.register(Registry.ITEM, new Identifier(MODID, name), item);
		return item;
	}

	public static StatusEffect register(String name, StatusEffect effect) {
		Registry.register(Registry.STATUS_EFFECT, new Identifier(MODID, name), effect);
		return effect;
	}

	public static BlockEntityType register(String name, Supplier<BlockEntity> be, Block... blocks) {
		return Registry.register(Registry.BLOCK_ENTITY, new Identifier(MODID, name), BlockEntityType.Builder.create(be, blocks).build(null));
	}

	public static Enchantment register(String name, Enchantment enchantment) {
		return Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, name), enchantment);
	}

	public static <T extends Entity> EntityType<T> register(String name, EntityCategory category, EntityDimensions size, EntityType.EntityFactory<T> factory) {
		return Registry.register(Registry.ENTITY_TYPE, new Identifier(MODID, name), FabricEntityTypeBuilder.create(category, factory).size(size).disableSaving().build());
	}
}
