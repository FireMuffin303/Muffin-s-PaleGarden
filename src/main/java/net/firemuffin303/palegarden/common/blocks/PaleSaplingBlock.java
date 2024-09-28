package net.firemuffin303.palegarden.common.blocks;

import net.firemuffin303.palegarden.common.registry.ModBlockTags;
import net.firemuffin303.palegarden.common.registry.ModConfiguredFeatures;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.Optional;

public class PaleSaplingBlock extends SaplingBlock {
    public PaleSaplingBlock(Settings settings) {
        super(new SaplingGenerator("pale", Optional.empty(),Optional.of(ModConfiguredFeatures.PALE),Optional.empty()), settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos) || floor.isIn(ModBlockTags.PALE_SAPLING_CAN_GROW);
    }
}
