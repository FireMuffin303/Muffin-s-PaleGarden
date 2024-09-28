package net.firemuffin303.palegarden.common.registry;

import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.ModSimpleParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticleTypes {
    public static final SimpleParticleType PALE_LEAVES = Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Palegarden.MOD_ID,"pale_leaves"),new ModSimpleParticleType(false));

    public static void init() {

    }
}
