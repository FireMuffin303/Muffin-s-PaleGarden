package net.firemuffin303.palegarden.common.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.firemuffin303.palegarden.common.entity.task.BeingLookByStalkTargetTask;
import net.firemuffin303.palegarden.common.entity.task.CreakerRoarTask;
import net.firemuffin303.palegarden.common.entity.task.FindStalkTarget;
import net.firemuffin303.palegarden.common.entity.task.StartStalkingTask;
import net.firemuffin303.palegarden.common.registry.ModAI;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Set;

public class CreakerAI {
    protected static final List<SensorType<? extends Sensor<? super CreakerEntity>>> SENSORS;
    protected static final List<MemoryModuleType<?>> MEMORY_MODULES;

        public static Brain<?> createBrain(CreakerEntity creakerEntity, Brain<CreakerEntity> brain){
            addCoreActivities(brain);
            addIdleActivity(brain);
            addFightActivity(creakerEntity,brain);
            addRoarActivity(brain);
            addStalkActivity(brain);
            brain.setCoreActivities(Set.of(Activity.CORE));
            brain.setDefaultActivity(Activity.IDLE);
            brain.resetPossibleActivities();
            return brain;
        }

        private static void addCoreActivities(Brain<CreakerEntity> creakerEntityBrain){
            creakerEntityBrain.setTaskList(Activity.CORE,0,
                    ImmutableList.of(
                            new StayAboveWaterTask(0.8f),
                            new LookAroundTask(45,90),
                            new MoveToTargetTask(),
                            new TemptationCooldownTask(ModAI.STALKING_COOLDOWN)
                    ));
        }

        private static void addIdleActivity(Brain<CreakerEntity> creakerEntityBrain){
            creakerEntityBrain.setTaskList(Activity.IDLE,10,
                    ImmutableList.of(
                            new FindStalkTarget(),UpdateAttackTargetTask.create(creakerEntity -> {
                        return creakerEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET);
                    }),new RandomTask<>(
                            ImmutableList.of(
                            Pair.of(StrollTask.create(1.0f),2),
                            Pair.of(new WaitTask(30,60),1))
                            )));
        }

        private static void addStalkActivity(Brain<CreakerEntity> creakerEntityBrain){
            creakerEntityBrain.setTaskList(ModAI.STALK,5,ImmutableList.of(
                    BeingLookByStalkTargetTask.create(),
                    new RandomTask<>(ImmutableList.of(
                            Pair.of(StartStalkingTask.create(1.2f),2),
                            Pair.of(new WaitTask(40,80),1))
                            )

            ),ModAI.STALKING_TARGET);
        }

        private static void addFightActivity(CreakerEntity creakerEntity,Brain<CreakerEntity> creakerEntityBrain){
            creakerEntityBrain.setTaskList(Activity.FIGHT,10,ImmutableList.of(
                    ForgetAttackTargetTask.create(entity -> !creakerEntity.isValidTarget(entity)),
                    RangedApproachTask.create(1.8f),
                    MeleeAttackTask.create(18)
            ),MemoryModuleType.ATTACK_TARGET);
        }

        private static void addRoarActivity(Brain<CreakerEntity> creakerEntityBrain){
            creakerEntityBrain.setTaskList(Activity.ROAR,10,ImmutableList.of(new CreakerRoarTask()),MemoryModuleType.ROAR_TARGET);
        }

        private static boolean isTargeting(CreakerEntity creakerEntity, LivingEntity entity){
            return creakerEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).filter(livingEntity -> livingEntity == entity).isPresent();
        }

        public static void updateActivity(CreakerEntity creakerEntity){
            creakerEntity.getBrain().resetPossibleActivities(ImmutableList.of(ModAI.STALK,Activity.ROAR,Activity.FIGHT,Activity.IDLE));
        }


    static {
        SENSORS = List.of(SensorType.NEAREST_PLAYERS,SensorType.NEAREST_LIVING_ENTITIES,SensorType.HURT_BY);
        MEMORY_MODULES = List.of(
                MemoryModuleType.MOBS,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.NEAREST_VISIBLE_PLAYER,
                MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.ROAR_TARGET,
                MemoryModuleType.ROAR_SOUND_DELAY,
                MemoryModuleType.ROAR_SOUND_COOLDOWN,
                ModAI.STALKING_TARGET,
                ModAI.STALKING_COOLDOWN
                );
    }
}
