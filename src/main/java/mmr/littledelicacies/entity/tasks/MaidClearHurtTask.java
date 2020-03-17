package mmr.littledelicacies.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.PanicTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class MaidClearHurtTask extends Task<LittleMaidBaseEntity> {
    public MaidClearHurtTask() {
        super(ImmutableMap.of());
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        boolean flag = PanicTask.hasBeenHurt(entityIn) || PanicTask.hostileNearby(entityIn) || func_220394_a(entityIn);
        if (!flag) {
            entityIn.getBrain().removeMemory(MemoryModuleType.NEAREST_HOSTILE);
            entityIn.getBrain().removeMemory(MemoryModuleType.HURT_BY);
            entityIn.getBrain().removeMemory(MemoryModuleType.HURT_BY_ENTITY);
            entityIn.getBrain().updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
        }

    }

    private static boolean func_220394_a(LittleMaidBaseEntity p_220394_0_) {
        return p_220394_0_.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).filter((p_223523_1_) -> {
            return p_223523_1_.getDistanceSq(p_220394_0_) <= 10.0D * 10.0D;
        }).isPresent();
    }
}