package net.firemuffin303.palegarden.common.registry;

import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.blocks.EndGrassBlock;
import net.firemuffin303.palegarden.common.blocks.PaleLeavesBlock;
import net.firemuffin303.palegarden.common.blocks.PaleSaplingBlock;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static Block PALE_STONE = register("pale_stone",new Block(AbstractBlock.Settings.copy(Blocks.END_STONE)));
    public static Block PALE_LOG = register("pale_log",new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)));
    public static Block PALE_LEAVES = register("pale_leaves",new PaleLeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    public static Block PALE_SAPLING = register("pale_sapling",new PaleSaplingBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    public static Block PALE_GRASS = register("pale_grass",new EndGrassBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS)));

    public static void init(){}

    public static Block register(String id, Block block){
        return Registry.register(Registries.BLOCK, Identifier.of(Palegarden.MOD_ID,id), block);
    }
}
