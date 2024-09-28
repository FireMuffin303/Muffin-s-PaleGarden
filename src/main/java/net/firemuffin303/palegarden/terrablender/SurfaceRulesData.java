package net.firemuffin303.palegarden.terrablender;

import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.registry.ModBlocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class SurfaceRulesData {
    public static MaterialRules.MaterialRule makeRules(){
        return MaterialRules.sequence(
                MaterialRules.condition(
                        MaterialRules.biome(Palegarden.PALE_GARDEN),
                        MaterialRules.sequence(
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,
                                        MaterialRules.block(ModBlocks.PALE_STONE.getDefaultState()))
                        ))
        );
    }
}
