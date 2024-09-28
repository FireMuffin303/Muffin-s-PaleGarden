package net.firemuffin303.palegarden.common.entity.task;

import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.entity.CreakerEntity;
import net.firemuffin303.palegarden.common.registry.ModAI;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.Optional;
import java.util.function.Function;

public class StartStalkingTask {

    public static Task<CreakerEntity> create(float speed){
        return create(speed,StartStalkingTask::findStalkTargetPos);
    }

    public static Task<CreakerEntity> create(float speed, Function<PathAwareEntity, Vec3d> targetGetter){
        return TaskTriggerer.task((context) ->{
           return context.group(
                   context.queryMemoryOptional(ModAI.STALKING_TARGET),context.queryMemoryAbsent(MemoryModuleType.LOOK_TARGET),context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET), context.queryMemoryAbsent(MemoryModuleType.ATTACK_TARGET), context.queryMemoryOptional(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE))
                   .apply(context, (muPlayerEntityMemoryQueryResult,lookTargetMemory, muWalkTargetMemoryQueryResult, muLivingEntityMemoryQueryResult, muLongMemoryQueryResult) -> {
                       return (world, entity, time) -> {
                           Optional<Vec3d> vec3dOptional = Optional.ofNullable(targetGetter.apply(entity));
                           lookTargetMemory.remember(new EntityLookTarget(entity.getBrain().getOptionalRegisteredMemory(ModAI.STALKING_TARGET).get(),true));
                           muWalkTargetMemoryQueryResult.remember(vec3dOptional.map(pos -> new WalkTarget(pos,speed,0)));
                           return true;
                       };
                   });
        });
    }

    private static Vec3d findStalkTargetPos(PathAwareEntity entity){
        PlayerEntity target = entity.getBrain().getOptionalRegisteredMemory(ModAI.STALKING_TARGET).get();
        Vec3d behindVec = target.getRotationVec(1.0f).negate();
        Vec3d vec3d = target.getPos().add(behindVec.multiply(5.2f));
        return vec3d;

    }
}
