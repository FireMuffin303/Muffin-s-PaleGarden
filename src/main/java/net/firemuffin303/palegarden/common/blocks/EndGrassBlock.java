package net.firemuffin303.palegarden.common.blocks;

import net.firemuffin303.palegarden.common.registry.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.ShortPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class EndGrassBlock extends ShortPlantBlock {
    public EndGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return  super.canPlantOnTop(floor, world, pos) || floor.isIn(ModBlockTags.PALE_SAPLING_CAN_GROW);
    }
}
