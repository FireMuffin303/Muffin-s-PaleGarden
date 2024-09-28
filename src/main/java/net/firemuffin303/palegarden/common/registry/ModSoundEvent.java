package net.firemuffin303.palegarden.common.registry;

import net.firemuffin303.palegarden.Palegarden;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModSoundEvent {
    public static final SoundEvent CREAKER_GROWL = register("entity.creaker.growl");
    public static final SoundEvent CREAKER_MOVE = register("entity.creaker.move");

    public static SoundEvent register(String id){
        Identifier ID = Identifier.of(Palegarden.MOD_ID,id);
        return Registry.register(Registries.SOUND_EVENT, ID,SoundEvent.of(ID));
    }

    public static void init(){}
}
