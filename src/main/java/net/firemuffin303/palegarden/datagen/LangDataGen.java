package net.firemuffin303.palegarden.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.registry.ModBlocks;
import net.firemuffin303.palegarden.common.registry.ModEntities;
import net.firemuffin303.palegarden.common.registry.ModItems;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class LangDataGen extends FabricLanguageProvider {
    protected LangDataGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.PALE_STONE,"Pale Stone");
        translationBuilder.add(ModItems.PALE_LOG,"Pale Log");
        translationBuilder.add(ModItems.PALE_LEAVES,"Pale Leaves");
        translationBuilder.add(ModItems.CREAKER_SPAWN_EGG,"Creaker Spawn Egg");
        translationBuilder.add(ModItems.PALE_SAPLING,"Pale Sapling");
        translationBuilder.add(ModItems.PALE_GRASS,"Pale Grass");
        translationBuilder.add("muffins_palegarden:pale_garden","Pale Garden");
        translationBuilder.add(ModEntities.CREAKER,"Creaker");
        translationBuilder.add(Palegarden.MOD_ID+":item_group","Muffin's Pale Garden");
        translationBuilder.add("subtitles.entity.creaker.growl","Creaker's Growl");
        translationBuilder.add("subtitles.entity.creaker.move","Creaker Moves");
    }
}
