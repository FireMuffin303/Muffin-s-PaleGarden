package net.firemuffin303.palegarden.common.registry;

import net.firemuffin303.palegarden.Palegarden;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
    public static TagKey<Block> PALE_SAPLING_CAN_GROW = TagKey.of(RegistryKeys.BLOCK, Identifier.of(Palegarden.MOD_ID,"pale_sapling_can_grow"));
}
