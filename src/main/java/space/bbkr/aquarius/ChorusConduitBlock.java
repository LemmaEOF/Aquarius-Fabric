package space.bbkr.aquarius;

import net.minecraft.block.Block;
import net.minecraft.block.ConduitBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipOptions;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.world.BlockView;

import java.util.List;

public class ChorusConduitBlock extends ConduitBlock {

    public ChorusConduitBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new ChorusConduitBlockEntity();
    }
}
