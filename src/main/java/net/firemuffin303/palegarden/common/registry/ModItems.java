package net.firemuffin303.palegarden.common.registry;

import net.firemuffin303.palegarden.Palegarden;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static List<Item> ITEMS = new ArrayList<>();
    public static Item PALE_STONE = register("pale_stone",new BlockItem(ModBlocks.PALE_STONE,new Item.Settings()));
    public static Item PALE_LOG = register("pale_log",new BlockItem(ModBlocks.PALE_LOG,new Item.Settings()));
    public static Item PALE_LEAVES = register("pale_leaves",new BlockItem(ModBlocks.PALE_LEAVES,new Item.Settings()));
    public static Item CREAKER_SPAWN_EGG = register("creaker_spawn_egg",new SpawnEggItem(ModEntities.CREAKER,0x363c45,0xdbdcdd,new Item.Settings()));
    public static Item PALE_SAPLING = register("pale_sapling",new BlockItem(ModBlocks.PALE_SAPLING,new Item.Settings()));
    public static Item PALE_GRASS = register("pale_grass",new BlockItem(ModBlocks.PALE_GRASS,new Item.Settings()));

    public static void init(){}

    public static Item register(String id,Item item){
        ITEMS.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(Palegarden.MOD_ID,id),item);
    }
}
