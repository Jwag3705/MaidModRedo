package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.init.MaidMemoryModuleType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class StopAttackTask extends Task<LittleMaidBaseEntity> {
    protected int attackTick;

    public StopAttackTask() {
        super(ImmutableMap.of(MaidMemoryModuleType.TARGET_HOSTILES, MemoryModuleStatus.VALUE_PRESENT));
    }

    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        double d0 = getTargetDistance(owner);
        if (owner.getBrain().getMemory(MaidMemoryModuleType.TARGET_HOSTILES).isPresent()) {
            Entity entity = owner.getBrain().getMemory(MaidMemoryModuleType.TARGET_HOSTILES).get();
            return !owner.isAlive() || owner.getDistanceSq(entity) > d0 * d0 * 1.15f;
        } else {
            return false;
        }
    }

    protected double getTargetDistance(LittleMaidBaseEntity entity) {
        IAttributeInstance iattributeinstance = entity.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getValue();
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        return false;
    }

    @Override
    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        Brain<?> brain = entityIn.getBrain();
        entityIn.getBrain().removeMemory(MaidMemoryModuleType.TARGET_HOSTILES);
        brain.updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
    }
}
