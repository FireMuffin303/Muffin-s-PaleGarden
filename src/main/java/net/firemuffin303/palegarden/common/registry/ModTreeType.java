package net.firemuffin303.palegarden.common.registry;

import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.worldgen.PaleTrunkPlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.CherryFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTreeType {
    public static final TrunkPlacerType<PaleTrunkPlacer> PALE_TRUNK_PLACER =
            Registry.register(Registries.TRUNK_PLACER_TYPE,
                    Identifier.of(Palegarden.MOD_ID, "pale_trunk_placer"),
                    new TrunkPlacerType<>(PaleTrunkPlacer.CODEC));

    public static TreeFeatureConfig.Builder pale(){
        return new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.PALE_LOG),
                new PaleTrunkPlacer(7,1,0,
                        new WeightedListIntProvider(DataPool.<IntProvider>builder()
                                .add(ConstantIntProvider.create(1),1).add(ConstantIntProvider.create(2),1).add(ConstantIntProvider.create(3),1).build()

                        ),
                        UniformIntProvider.create(1,4),
                        UniformIntProvider.create(1,3)),
                BlockStateProvider.of(ModBlocks.PALE_LEAVES),
                new CherryFoliagePlacer(ConstantIntProvider.create(4),ConstantIntProvider.create(0),ConstantIntProvider.create(5),
                        0.25f,0.5f,0.16666667f,0.33333334F),
                new TwoLayersFeatureSize(1, 0, 2)
        ).ignoreVines();
    }

    public static void init(){}
}
