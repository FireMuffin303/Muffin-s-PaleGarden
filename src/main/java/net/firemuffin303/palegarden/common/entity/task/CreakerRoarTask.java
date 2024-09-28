package net.firemuffin303.palegarden.common.entity.task;

import com.google.common.collect.ImmutableMap;
import net.firemuffin303.palegarden.common.entity.CreakerEntity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.mob.WardenBrain;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Unit;

import java.util.Objects;
import java.util.Optional;

public class CreakerRoarTask extends MultiTickTask<CreakerEntity> {
    public CreakerRoarTask() {
        super(ImmutableMap.of(MemoryModuleType.ROAR_TARGET,MemoryModuleState.VALUE_PRESENT,
                MemoryModuleType.ATTACK_TARGET,MemoryModuleState.VALUE_ABSENT,
                MemoryModuleType.ROAR_SOUND_COOLDOWN,MemoryModuleState.REGISTERED,
                MemoryModuleType.ROAR_SOUND_DELAY,MemoryModuleState.REGISTERED),WardenBrain.ROAR_DURATION);
    }

    @Override
    protected void run(ServerWorld world, CreakerEntity entity, long time) {
        Brain<CreakerEntity> brain = entity.getBrain();
        brain.remember(MemoryModuleType.ROAR_SOUND_DELAY, Unit.INSTANCE,10L);
        brain.forget(MemoryModuleType.WALK_TARGET);
        LivingEntity livingEntity = entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ROAR_TARGET).get();
        LookTargetUtil.lookAt(entity,livingEntity);
        entity.setPose(EntityPose.ROARING);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, CreakerEntity entity, long time) {
        return true;
    }

    @Override
    protected void keepRunning(ServerWorld world, CreakerEntity creakerEntity, long time) {
        if(!creakerEntity.getBrain().hasMemoryModule(MemoryModuleType.ROAR_SOUND_DELAY) && !creakerEntity.getBrain().hasMemoryModule(MemoryModuleType.ROAR_SOUND_COOLDOWN)){
            creakerEntity.getBrain().remember(MemoryModuleType.ROAR_SOUND_COOLDOWN, Unit.INSTANCE, (long)(WardenBrain.ROAR_DURATION - 25));
            creakerEntity.playSound(SoundEvents.ENTITY_WARDEN_ROAR, 3.0F, 1.0F);
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, CreakerEntity entity, long time) {
        if(entity.isInPose(EntityPose.ROARING)){
            entity.setPose(EntityPose.STANDING);
        }

        Optional<LivingEntity> livingEntity = entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ROAR_TARGET);
        Objects.requireNonNull(livingEntity);
        livingEntity.ifPresent(entity::updateAttackTarget);
        entity.getBrain().forget(MemoryModuleType.ROAR_TARGET);
    }
}
