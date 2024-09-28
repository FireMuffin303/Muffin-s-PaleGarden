package net.firemuffin303.palegarden.common.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.firemuffin303.palegarden.common.registry.ModTreeType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class PaleTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<PaleTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return fillTrunkPlacerFields(instance)
                .and(instance.group(
                        IntProvider.createValidatingCodec(1,3).fieldOf("branch_count").forGetter((placer) -> placer.branchCount),
                                IntProvider.createValidatingCodec(1,5).fieldOf("branch_horizontal_length").forGetter((placer) -> placer.branchHorizontalLength),
                        IntProvider.createValidatingCodec(1,3).fieldOf("base_size").forGetter((placer) -> placer.baseSize)
                        )).apply(instance,PaleTrunkPlacer::new);
    });

    private final IntProvider branchCount;
    private final IntProvider branchHorizontalLength;
    private final IntProvider baseSize;

    public PaleTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight,IntProvider branchCount, IntProvider branchHorizontalLength, IntProvider baseSize) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.branchCount = branchCount;
        this.branchHorizontalLength = branchHorizontalLength;
        this.baseSize = baseSize;

    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTreeType.PALE_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {

        int realBaseSize = this.baseSize.get(random);
        for(int i = 0; i < realBaseSize; ++i){
            Direction direction = Direction.Type.HORIZONTAL.random(random);
            if(world.testBlockState(startPos.offset(direction), blockState -> blockState.isOf(Blocks.AIR)) && world.testBlockState(startPos.offset(direction).down(),blockState -> !blockState.isOf(Blocks.AIR))){
                this.getAndSetState(world,replacer,random,startPos.offset(direction),config);
            }

        }

        int realHeight = random.nextBetween(height-2,height+3);

        for(int i = 0; i < realHeight; ++i){
            this.getAndSetState(world,replacer,random,startPos.up(i),config);
        }

        int branch = this.branchCount.get(random);
        List<FoliagePlacer.TreeNode> list = new ArrayList();
        list.add(new FoliagePlacer.TreeNode(startPos.up(height),0,false));

        return list;
    }
}
