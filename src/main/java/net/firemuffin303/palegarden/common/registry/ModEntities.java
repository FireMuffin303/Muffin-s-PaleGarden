package net.firemuffin303.palegarden.common.registry;

import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.entity.CreakerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<CreakerEntity> CREAKER = register("creaker",
            EntityType.Builder.create(CreakerEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.25f,3.1f)
                    .eyeHeight(2.55f)
                    .passengerAttachments(2.75f)
                    .maxTrackingRange(16)
                    .build(null)
    );

    public static void init(){}

    public static <T extends Entity> EntityType<T> register(String id, EntityType<T> entityType){
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(Palegarden.MOD_ID,id),entityType);
    }
}
