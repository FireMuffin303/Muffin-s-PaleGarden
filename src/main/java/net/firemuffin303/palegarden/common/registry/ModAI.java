package net.firemuffin303.palegarden.common.registry;

import com.mojang.serialization.Codec;
import net.firemuffin303.palegarden.Palegarden;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModAI {
    public static final Activity STALK = register("stalk");
    public static final MemoryModuleType<PlayerEntity> STALKING_TARGET = registerMemory("stalking_target",new MemoryModuleType<>(Optional.empty()));
    public static final MemoryModuleType<Integer> STALKING_COOLDOWN = registerMemory("stalking_cooldown",new MemoryModuleType<>(Optional.of(Codec.INT)));


    public static <T> MemoryModuleType<T> registerMemory(String id,MemoryModuleType<T> memoryModuleType){
        return Registry.register(Registries.MEMORY_MODULE_TYPE,Identifier.of(Palegarden.MOD_ID,id),memoryModuleType);
    }

    public static Activity register(String id){
        return Registry.register(Registries.ACTIVITY, Identifier.of(Palegarden.MOD_ID,id),new Activity(id));
    }

    public static void init(){}
}
