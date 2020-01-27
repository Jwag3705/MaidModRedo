package mmr.littlemaidredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.littlemaidredo.entity.LittleMaidEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.PanicTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class MaidClearHurtTask extends Task<LittleMaidEntity> {
    public MaidClearHurtTask() {
        super(ImmutableMap.of());
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidEntity entityIn, long gameTimeIn) {
        boolean flag = PanicTask.func_220512_b(entityIn) || PanicTask.func_220513_a(entityIn) || func_220394_a(entityIn);
        if (!flag) {
            entityIn.getBrain().removeMemory(MemoryModuleType.HURT_BY);
            entityIn.getBrain().removeMemory(MemoryModuleType.HURT_BY_ENTITY);
            entityIn.getBrain().updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
        }

    }

    private static boolean func_220394_a(LittleMaidEntity p_220394_0_) {
        return p_220394_0_.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).filter((p_223523_1_) -> {
            return p_223523_1_.getDistanceSq(p_220394_0_) <= 36.0D;
        }).isPresent();
    }
}