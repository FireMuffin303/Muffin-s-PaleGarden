package net.firemuffin303.palegarden.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.registry.ModBlocks;
import net.firemuffin303.palegarden.common.registry.ModConfiguredFeatures;
import net.firemuffin303.palegarden.common.registry.ModSoundEvent;
import net.firemuffin303.palegarden.common.registry.ModTreeType;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FeatureDataGen extends FabricDynamicRegistryProvider {
    public FeatureDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.add(ModConfiguredFeatures.PALE, new ConfiguredFeature<>(Feature.TREE,ModTreeType.pale().build()));

        /*RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup  = registries.createRegistryLookup().getOrThrow(RegistryKeys.CONFIGURED_FEATURE);
        var registryEntry = registryEntryLookup.getOrThrow(ModConfiguredFeatures.PALE);
        entries.add(ModConfiguredFeatures.TREES_PALE, new PlacedFeature(registryEntry,
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(10,0.1f,1),ModBlocks.PALE_SAPLING)));
    */
    }


    @Override
    public String getName() {
        return Palegarden.MOD_ID+"_feature";
    }
}
