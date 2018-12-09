package space.bbkr.aquarius;

import net.minecraft.block.Block;
import net.minecraft.block.ConduitBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class ChorusConduitBlock extends ConduitBlock {

    public ChorusConduitBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new ChorusConduitBlockEntity();
    }

}
