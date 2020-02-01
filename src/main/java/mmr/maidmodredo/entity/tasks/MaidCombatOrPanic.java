package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.maidmodredo.entity.LittleMaidEntity;
import mmr.maidmodredo.init.MaidMemoryModuleType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class MaidCombatOrPanic extends Task<LittleMaidEntity> {
    public MaidCombatOrPanic() {
        super(ImmutableMap.of());
    }

    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidEntity entityIn, long gameTimeIn) {
        return func_220512_b(entityIn) || func_220513_a(entityIn) || entityIn.getBrain().hasMemory(MemoryModuleType.HURT_BY_ENTITY);
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidEntity entityIn, long gameTimeIn) {
        Brain<?> brain = entityIn.getBrain();
        if (func_220512_b(entityIn) || func_220513_a(entityIn) || (entityIn.getBrain().hasMemory(MemoryModuleType.HURT_BY_ENTITY))) {
                if (entityIn.isTamed() && !entityIn.isMaidWait() && entityIn.getMaidData().getJob().getSubActivity() != null) {
                    if (!isSetupTarget(entityIn) && func_220513_a(entityIn) || !isSetupTarget(entityIn) && entityIn.getBrain().hasMemory(MemoryModuleType.HURT_BY_ENTITY)) {
                        brain.removeMemory(MemoryModuleType.PATH);
                        brain.removeMemory(MemoryModuleType.WALK_TARGET);
                        brain.removeMemory(MemoryModuleType.LOOK_TARGET);
                        brain.removeMemory(MemoryModuleType.INTERACTION_TARGET);

                        if (entityIn.getBrain().hasMemory(MemoryModuleType.HURT_BY_ENTITY) && entityIn.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).get().isAlive()) {
                            entityIn.getBrain().setMemory(MaidMemoryModuleType.TARGET_HOSTILES, entityIn.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).get());
                        } else if (func_220513_a(entityIn) && entityIn.getBrain().getMemory(MemoryModuleType.NEAREST_HOSTILE).get().isAlive()) {
                            entityIn.getBrain().setMemory(MaidMemoryModuleType.TARGET_HOSTILES, entityIn.getBrain().getMemory(MemoryModuleType.NEAREST_HOSTILE).get());
                        }

                        brain.removeMemory(MemoryModuleType.HURT_BY);
                        brain.removeMemory(MemoryModuleType.HURT_BY_ENTITY);
                        brain.removeMemory(MemoryModuleType.NEAREST_HOSTILE);

                        brain.switchTo(entityIn.getMaidData().getJob().getSubActivity());
                    }
                } else if (!entityIn.isTamed() || func_220512_b(entityIn) && entityIn.isTamed() && !entityIn.isMaidWait()) {
                    if (!brain.hasActivity(Activity.PANIC)) {
                        brain.removeMemory(MemoryModuleType.PATH);
                        brain.removeMemory(MemoryModuleType.WALK_TARGET);
                        brain.removeMemory(MemoryModuleType.LOOK_TARGET);
                        brain.removeMemory(MemoryModuleType.INTERACTION_TARGET);

                        brain.switchTo(Activity.PANIC);
                    }
                }
            }
    }


    public static boolean func_220513_a(LivingEntity p_220513_0_) {
        return p_220513_0_.getBrain().hasMemory(MemoryModuleType.NEAREST_HOSTILE);
    }

    public static boolean func_220512_b(LivingEntity p_220512_0_) {
        return p_220512_0_.getBrain().hasMemory(MemoryModuleType.HURT_BY);
    }

    public static boolean isSetupTarget(LivingEntity p_220512_0_) {
        return p_220512_0_.getBrain().hasMemory(MaidMemoryModuleType.TARGET_HOSTILES);
    }
}
