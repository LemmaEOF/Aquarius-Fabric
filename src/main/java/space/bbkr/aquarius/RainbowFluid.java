package space.bbkr.aquarius;

import com.sun.istack.internal.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleParameters;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

import java.util.Random;

public class RainbowFluid extends BaseFluid {

	public Fluid getFlowing() {
		return Aquarius.RAINBOW_FLUID_FLOWING;
	}

	public Fluid getStill() {
		return Aquarius.RAINBOW_FLUID;
	}

	@Environment(EnvType.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	public Item getBucketItem() {
		return Aquarius.RAINBOW_FLUID_BUCKET;
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
		if (!state.isStill() && !state.get(FALLING)) {
			if (random.nextInt(64) == 0) {
				world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCK, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
			}
		} else if (random.nextInt(10) == 0) {
			world.addParticle(ParticleTypes.UNDERWATER, (double)((float)pos.getX() + random.nextFloat()), (double)((float)pos.getY() + random.nextFloat()), (double)((float)pos.getZ() + random.nextFloat()), 0.0D, 0.0D, 0.0D);
		}

	}

	@Nullable
	@Environment(EnvType.CLIENT)
	public ParticleParameters getParticle() {
		return ParticleTypes.DRIPPING_WATER;
	}

	protected boolean method_15737() {
		return true;
	}

	protected void method_15730(IWorld world, BlockPos pos, BlockState state) {
		BlockEntity be = state.getBlock().hasBlockEntity() ? world.getBlockEntity(pos) : null;
		Block.dropStacks(state, world.getWorld(), pos, be);
	}

	public int method_15733(ViewableWorld world) {
		return 4;
	}

	public BlockState toBlockState(FluidState state) {
		return Blocks.WATER.getDefaultState().with(FluidBlock.field_11278, method_15741(state));
	}

	@Override
	public boolean isStill(FluidState state) {
		return false;
	}

	@Override
	public int getLevel(FluidState state) {
		return 0;
	}

	public boolean matchesType(Fluid fluid) {
		return fluid == Aquarius.RAINBOW_FLUID || fluid == Aquarius.RAINBOW_FLUID_FLOWING;
	}

	public int method_15739(ViewableWorld world) {
		return 1;
	}

	public int getTickRate(ViewableWorld world) {
		return 5;
	}

	public boolean method_15777(FluidState world, BlockView view, BlockPos pos, Fluid fluid, Direction dir) {
		return dir == Direction.DOWN && !fluid.matches(Aquarius.RAINBOW_FLUID_TAG);
	}

	protected float getBlastResistance() {
		return 100.0F;
	}

	public static class Flowing extends RainbowFluid {
		public Flowing() {
		}

		protected void appendProperties(StateFactory.Builder<Fluid, FluidState> builder) {
			super.appendProperties(builder);
			builder.with(new Property[]{LEVEL});
		}

		public int getLevel(FluidState state) {
			return state.get(LEVEL);
		}

		public boolean isStill(FluidState state) {
			return false;
		}
	}

	public static class Still extends RainbowFluid {
		public Still() {
		}

		public int getLevel(FluidState state) {
			return 8;
		}

		public boolean isStill(FluidState state) {
			return true;
		}
	}
}
