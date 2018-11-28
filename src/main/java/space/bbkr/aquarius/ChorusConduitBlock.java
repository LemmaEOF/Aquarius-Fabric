package space.bbkr.aquarius;

import net.minecraft.block.BlockConduit;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class ChorusConduitBlock extends BlockConduit {

    public ChorusConduitBlock(Builder builder) {
        super(builder);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new ChorusConduitBlockEntity();
    }

}
