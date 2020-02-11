package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.init.MaidMemoryModuleType;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class WalkToItem extends Task<LittleMaidBaseEntity> {
    private ItemEntity itemEntity;

    public WalkToItem() {
        super(ImmutableMap.of(MemoryModuleType.PATH, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MaidMemoryModuleType.TARGET_HOSTILES, MemoryModuleStatus.VALUE_ABSENT));
    }

    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {

        if (owner.isSleeping() || !owner.isTamed() || owner.isMaidWait()) {
            return false;
        }
        itemEntity = findItem(owner);

        return itemEntity != null && owner.canEntityBeSeen(itemEntity);
    }

    private ItemEntity findItem(LittleMaidBaseEntity owner) {
        List<ItemEntity> list = owner.world.getEntitiesWithinAABB(ItemEntity.class, owner.getBoundingBox().grow(6.0D, 4.0D, 6.0D));
        if (!list.isEmpty()) {
            return list.get(owner.getRNG().nextInt(list.size()));
        } else {
            return null;
        }
    }

    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        return itemEntity != null && entityIn.canEntityBeSeen(itemEntity);
    }

    @Override
    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        super.startExecuting(worldIn, entityIn, gameTimeIn);
        if (this.itemEntity != null) {
            entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(this.itemEntity));
            entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityPosWrapper(this.itemEntity), 0.5F, 1));
        }
    }

    @Override
    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        super.resetTask(worldIn, entityIn, gameTimeIn);
        entityIn.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
        entityIn.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
    }
}