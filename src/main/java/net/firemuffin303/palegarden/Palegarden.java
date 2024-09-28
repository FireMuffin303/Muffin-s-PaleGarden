package net.firemuffin303.palegarden;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.firemuffin303.palegarden.common.entity.CreakerEntity;
import net.firemuffin303.palegarden.common.registry.*;
import net.firemuffin303.palegarden.terrablender.SurfaceRulesData;
import net.minecraft.entity.SpawnLocation;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import org.slf4j.Logger;
import terrablender.api.EndBiomeRegistry;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class Palegarden implements ModInitializer, TerraBlenderApi {

    public static Logger LOGGER = LogUtils.getLogger();
    public static String MOD_ID = "muffins_palegarden";
    public static RegistryKey<Biome> PALE_GARDEN = RegistryKey.of(RegistryKeys.BIOME,Identifier.of(MOD_ID,"pale_garden"));

    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModTreeType.init();
        ModConfiguredFeatures.init();
        ModParticleTypes.init();
        ModAI.init();
        ModSoundEvent.init();

        FabricDefaultAttributeRegistry.register(ModEntities.CREAKER, CreakerEntity.createCreakerAttribute());

        SpawnRestriction.register(ModEntities.CREAKER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,CreakerEntity::canMobSpawn);

        Registry.register(Registries.ITEM_GROUP, Identifier.of(MOD_ID,"main"),
                FabricItemGroup.builder()
                        .displayName(Text.of(Identifier.of(MOD_ID,"item_group")))
                        .icon(() -> new ItemStack(ModItems.PALE_STONE))
                        .entries((displayContext, entries) -> {
                            ModItems.ITEMS.forEach(entries::add);
                        })
                        .build());

    }

    @Override
    public void onTerraBlenderInitialized() {
        EndBiomeRegistry.registerHighlandsBiome(PALE_GARDEN,2);
        EndBiomeRegistry.registerMidlandsBiome(PALE_GARDEN,2);

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.END,MOD_ID, SurfaceRulesData.makeRules());
    }
}
