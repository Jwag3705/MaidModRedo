package mmr.littledelicacies.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.MaidMemoryModuleType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class StopAttackTask extends Task<LittleMaidBaseEntity> {
    protected int attackTick;

    public StopAttackTask() {
        super(ImmutableMap.of());
    }

    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (!owner.getBrain().getMemory(MaidMemoryModuleType.TARGET_HOSTILES).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        return false;
    }

    @Override
    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        Brain<?> brain = entityIn.getBrain();
        brain.updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
    }
}
