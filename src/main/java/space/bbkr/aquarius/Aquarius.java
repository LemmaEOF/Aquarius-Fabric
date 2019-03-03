package space.bbkr.aquarius;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.class_4081;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.item.block.BlockItem;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class Aquarius implements ModInitializer {

	public static final Block CHORUS_CONDUIT = register("chorus_conduit", new ChorusConduitBlock(FabricBlockSettings.of(Material.GLASS).strength(3.0F, 3.0F).lightLevel(15).build()), ItemGroup.MISC);
	public static final Item FLIPPERS = register("flippers", new ArmorItem(ArmorMaterials.TURTLE, EquipmentSlot.FEET, new Item.Settings().itemGroup(ItemGroup.COMBAT)));
    public static final Item PRISMARINE_ROD = register("prismarine_rod", new Item(new Item.Settings().itemGroup(ItemGroup.MISC)));
    public static BlockEntityType<ChorusConduitBlockEntity> CHORUS_CONDUIT_BE = register("chorus_conduit", ChorusConduitBlockEntity::new);

    public static BaseFluid RAINBOW_FLUID = fluidRegister("rainbow_fluid", new RainbowFluid.Still());
    public static BaseFluid RAINBOW_FLUID_FLOWING = fluidRegister("rainbow_fluid_flowing", new RainbowFluid.Flowing());
    public static Item RAINBOW_FLUID_BUCKET = register("rainbow_fluid_bucket", new BucketItem(RAINBOW_FLUID, new Item.Settings().itemGroup(ItemGroup.MISC)));
    public static Tag<Fluid> RAINBOW_FLUID_TAG = register("rainbow_fluid");
    public static Block RAINBOW_FLUID_BLOCK = register("rainbow_fluid", new AquariusFluidBlock(RAINBOW_FLUID, FabricBlockSettings.of(Material.WATER).noCollision().strength(100.0F, 100.0F).dropsNothing().build()));

	public static StatusEffect ATLANTEAN = register("atlantean", new AquariusStatusEffect(class_4081.BENEFICIAL, 0x1dd186));

	public static Enchantment TRIDENT_PIERCING = register("piercing", new TridentPiercingEnchantment());

	@Override
	public void onInitialize() {
	}

	public static Block register(String name, Block block) {
		Registry.register(Registry.BLOCK, "aquarius:" + name, block);
		return block;
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

	public static Enchantment register(String name, Enchantment enchantment) {
		return Registry.register(Registry.ENCHANTMENT, "aquarius:"+name, enchantment);
	}

	private static <T extends Fluid> T fluidRegister(String id, T fluid) {
		return Registry.register(Registry.FLUID, "aquarius:"+id, fluid);
	}

	private static Tag<Fluid> register(String id) {
		return new FluidTags.class_3487(new Identifier("aquarius", id));
	}
}
