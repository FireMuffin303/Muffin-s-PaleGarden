package net.firemuffin303.palegarden.common.entity;

import com.mojang.serialization.Dynamic;
import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.registry.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CreakerEntity extends HostileEntity {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState roarAnimationState = new AnimationState();
    public AnimationState attackingAnimationState = new AnimationState();
    public CreakerEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        this.getNavigation().setCanSwim(true);
        this.setPathfindingPenalty(PathNodeType.UNPASSABLE_RAIL, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
        this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
    }

    public static DefaultAttributeContainer.Builder createCreakerAttribute(){
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH,120.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.20000001192092D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,10.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE,64.0);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source,amount);
        if(!this.getWorld().isClient && !this.isAiDisabled()){
            Entity entity = source.getAttacker();
            if(this.brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).isEmpty() && entity instanceof LivingEntity livingEntity){
                if(source.isDirect() || this.isInRange(livingEntity,5.0)){
                    updateAttackTarget(livingEntity);
                }
            }
        }
        return super.damage(source, amount);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos) {
        return 0.0f;
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return CreakerAI.createBrain(this, this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    protected Brain.Profile<CreakerEntity> createBrainProfile() {
        return Brain.createProfile(CreakerAI.MEMORY_MODULES,CreakerAI.SENSORS);
    }

    @Override
    public Brain<CreakerEntity> getBrain() {
        return (Brain<CreakerEntity>) super.getBrain();
    }

    @Override
    protected void mobTick() {
        ServerWorld serverWorld = (ServerWorld)this.getWorld();
        serverWorld.getProfiler().push("creakerBrain");
        this.getBrain().tick(serverWorld, this);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("creakerActivityUpdate");
        CreakerAI.updateActivity(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.getWorld().sendEntityStatus(this,(byte)4);
        this.playSound(SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT,3.0f,this.getSoundPitch());
        return super.tryAttack(target);
    }

    @Override
    public void handleStatus(byte status) {
        if(status == 4){
            this.roarAnimationState.stop();
            this.attackingAnimationState.start(this.age);
        }else{
            super.handleStatus(status);
        }
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if(POSE.equals(data)){
            if (Objects.requireNonNull(this.getPose()) == EntityPose.ROARING) {
                this.roarAnimationState.start(this.age);
            }
        }
        super.onTrackedDataSet(data);
    }

    public void updateAttackTarget(LivingEntity livingEntity){
        this.getBrain().forget(MemoryModuleType.ROAR_TARGET);
        this.getBrain().remember(MemoryModuleType.ATTACK_TARGET,livingEntity);
        this.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    public boolean isValidTarget(@Nullable Entity entity){
        if(entity instanceof LivingEntity livingEntity){
            return this.getWorld() == entity.getWorld() &&
                    EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(entity) &&
                    !this.isTeammate(entity) &&
                    livingEntity.getType() != EntityType.ARMOR_STAND &&
                    livingEntity.getType() != ModEntities.CREAKER &&
                    !livingEntity.isInvulnerable() &&
                    !livingEntity.isDead() &&
                    this.getWorld().getWorldBorder().contains(livingEntity.getBoundingBox());
        }
        return false;
    }

    @Override
    public @Nullable LivingEntity getTarget() {
        return this.getTargetInBrain();
    }
}
