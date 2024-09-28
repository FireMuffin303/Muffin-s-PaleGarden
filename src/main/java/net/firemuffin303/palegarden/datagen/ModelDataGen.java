package net.firemuffin303.palegarden.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.firemuffin303.palegarden.common.registry.ModBlocks;
import net.firemuffin303.palegarden.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModelDataGen extends FabricModelProvider {
    private static final Model SPAWN_EGG = createMincraftItem("template_spawn_egg");

    public ModelDataGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        createFullBlock(ModBlocks.PALE_STONE,blockStateModelGenerator);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(ModBlocks.PALE_LOG,Models.CUBE_COLUMN.upload(ModBlocks.PALE_LOG,TextureMap.sideEnd(ModBlocks.PALE_LOG), blockStateModelGenerator.modelCollector)));
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(ModBlocks.PALE_LEAVES,Models.LEAVES.upload(ModBlocks.PALE_LEAVES,TextureMap.all(ModBlocks.PALE_LEAVES), blockStateModelGenerator.modelCollector)));
        createCrossBlock(ModBlocks.PALE_SAPLING,blockStateModelGenerator);
        createCrossBlock(ModBlocks.PALE_GRASS,blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.CREAKER_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(ModItems.PALE_SAPLING,Models.GENERATED);
        itemModelGenerator.register(ModItems.PALE_GRASS,Models.GENERATED);
    }

    private void createFullBlock(Block block, BlockStateModelGenerator blockModelGenerators){
        blockModelGenerators.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block,Models.CUBE_ALL.upload(block,TextureMap.all(block),blockModelGenerators.modelCollector)));
    }

    private void createCrossBlock(Block block, BlockStateModelGenerator blockStateModelGenerator){
        blockStateModelGenerator.blockStateCollector.accept(
                BlockStateModelGenerator.createSingletonBlockState(block,Models.CROSS.upload(block,TextureMap.cross(block), blockStateModelGenerator.modelCollector)));

    }

    private static Model createMincraftItem(String string, TextureKey... textureSlots) {
        return new Model(Optional.of(Identifier.of("item/" + string)),Optional.empty(), textureSlots);
    }
}
