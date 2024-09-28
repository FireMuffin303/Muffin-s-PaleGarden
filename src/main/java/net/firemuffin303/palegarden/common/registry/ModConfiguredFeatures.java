package net.firemuffin303.palegarden.common.registry;

import net.firemuffin303.palegarden.Palegarden;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> PALE = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Palegarden.MOD_ID,"pale"));
    public static final RegistryKey<ConfiguredFeature<?,?>> PATCH_PALE_GRASS_CONFIG = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Palegarden.MOD_ID,"patch_pale_grass"));

    public static final RegistryKey<PlacedFeature> TREES_PALE = RegistryKey.of(RegistryKeys.PLACED_FEATURE,Identifier.of(Palegarden.MOD_ID,"trees_pale"));
    public static final RegistryKey<PlacedFeature> PATCH_PALE_GRASS = RegistryKey.of(RegistryKeys.PLACED_FEATURE,Identifier.of(Palegarden.MOD_ID,"patch_pale_grass_normal"));
    public static void init(){}
}
