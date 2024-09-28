package net.firemuffin303.palegarden.common.entity.task;

import com.google.common.collect.ImmutableMap;
import net.firemuffin303.palegarden.common.entity.CreakerEntity;
import net.firemuffin303.palegarden.common.registry.ModAI;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;

public class FindStalkTarget extends MultiTickTask<CreakerEntity> {

    public FindStalkTarget() {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_PLAYER,MemoryModuleState.VALUE_PRESENT, ModAI.STALKING_TARGET,MemoryModuleState.VALUE_ABSENT,ModAI.STALKING_COOLDOWN,MemoryModuleState.VALUE_ABSENT),160);
    }

    @Override
    protected void run(ServerWorld world, CreakerEntity entity, long time) {
        Brain<?> brain = entity.getBrain();
        brain.getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).ifPresent((player) ->{
            if(entity.isValidTarget(player)){
                brain.remember(ModAI.STALKING_TARGET,player);
            }
        });
    }
}
