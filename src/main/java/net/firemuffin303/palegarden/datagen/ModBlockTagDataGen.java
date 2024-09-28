package net.firemuffin303.palegarden.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.firemuffin303.palegarden.common.registry.ModBlockTags;
import net.firemuffin303.palegarden.common.registry.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagDataGen extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(ModBlockTags.PALE_SAPLING_CAN_GROW)
                .add(Blocks.END_STONE)
                .add(ModBlocks.PALE_STONE);

        this.getOrCreateTagBuilder(BlockTags.LOGS).add(ModBlocks.PALE_LOG);
        this.getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(ModBlocks.PALE_LOG);
    }
}
