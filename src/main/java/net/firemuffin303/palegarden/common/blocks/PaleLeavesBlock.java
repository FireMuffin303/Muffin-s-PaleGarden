package net.firemuffin303.palegarden.common.blocks;

import com.mojang.serialization.MapCodec;
import net.firemuffin303.palegarden.common.registry.ModParticleTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class PaleLeavesBlock extends LeavesBlock {
    public static final MapCodec<PaleLeavesBlock> CODEC = createCodec(PaleLeavesBlock::new);

    public PaleLeavesBlock(Settings settings) {
        super(settings);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (random.nextInt(10) == 0) {
            BlockPos blockPos = pos.down();
            BlockState blockState = world.getBlockState(blockPos);
            if (!isFaceFullSquare(blockState.getCollisionShape(world, blockPos), Direction.UP)) {
                ParticleUtil.spawnParticle(world, pos, random, ModParticleTypes.PALE_LEAVES);
            }
        }
    }

    @Override
    public MapCodec<PaleLeavesBlock> getCodec() {
        return CODEC;
    }
}
