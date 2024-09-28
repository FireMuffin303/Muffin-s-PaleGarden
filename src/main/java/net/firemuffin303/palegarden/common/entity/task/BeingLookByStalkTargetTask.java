package net.firemuffin303.palegarden.common.entity.task;

import net.firemuffin303.palegarden.Palegarden;
import net.firemuffin303.palegarden.common.entity.CreakerEntity;
import net.firemuffin303.palegarden.common.registry.ModAI;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class BeingLookByStalkTargetTask {

    public static Task<CreakerEntity> create(){
        return TaskTriggerer.task(context -> {
            return context.group(context.queryMemoryOptional(ModAI.STALKING_TARGET),context.queryMemoryAbsent(MemoryModuleType.ROAR_TARGET),context.queryMemoryOptional(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE))
                    .apply(context,((stalkingTarget, roarTarget, cantReachWalkTargetSince) -> {
                        return (world, entity, time) -> {
                            PlayerEntity player = entity.getBrain().getOptionalRegisteredMemory(ModAI.STALKING_TARGET).get();
                            if(isPlayerStaring(entity,player) && entity.isValidTarget(player)){
                                roarTarget.remember(player);
                                cantReachWalkTargetSince.forget();
                                stalkingTarget.forget();
                                Palegarden.LOGGER.info("Should Roar");
                                return true;
                            }
                            return false;
                        };
                    }));
        });
    }

    static boolean isPlayerStaring(CreakerEntity creakerEntity, PlayerEntity player) {
        ItemStack itemStack = (ItemStack)player.getInventory().armor.get(3);
        if (itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem())) {
            return false;
        } else {
            Vec3d vec3d = player.getRotationVec(1.0F).normalize();
            Vec3d vec3d2 = new Vec3d(creakerEntity.getX() - player.getX(), creakerEntity.getEyeY() - player.getEyeY(), creakerEntity.getZ() - player.getZ());
            double d = vec3d2.length();
            vec3d2 = vec3d2.normalize();
            double e = vec3d.dotProduct(vec3d2);
            return e > 1.0 - 0.025 / d ? player.canSee(creakerEntity) : false;
        }
    }
}
