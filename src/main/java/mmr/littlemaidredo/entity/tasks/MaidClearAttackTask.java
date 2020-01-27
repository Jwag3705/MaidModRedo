package mmr.littlemaidredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.littlemaidredo.entity.LittleMaidEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.PanicTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class MaidClearAttackTask extends Task<LittleMaidEntity> {
    public MaidClearAttackTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidEntity entityIn) {
        return !(func_220394_a(entityIn) || PanicTask.func_220513_a(entityIn) || PanicTask.func_220512_b(entityIn));
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidEntity entityIn, long gameTimeIn) {

        entityIn.getBrain().removeMemory(MemoryModuleType.HURT_BY);
        entityIn.getBrain().removeMemory(MemoryModuleType.HURT_BY_ENTITY);
        entityIn.getBrain().updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
    }

    private boolean func_220394_a(LittleMaidEntity p_220394_0_) {
        double d0 = getTargetDistance(p_220394_0_) * 1.15F;
        return p_220394_0_.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).filter((p_223523_1_) -> {

            return p_223523_1_.getDistanceSq(p_220394_0_) <= d0 * d0;
        }).isPresent();
    }

    protected double getTargetDistance(LittleMaidEntity entity) {
        IAttributeInstance iattributeinstance = entity.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getValue();
    }
}