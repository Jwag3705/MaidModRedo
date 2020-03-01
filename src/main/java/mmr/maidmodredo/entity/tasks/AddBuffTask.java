package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.maidjob.EffectCasterMaidJob;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class AddBuffTask extends Task<LittleMaidBaseEntity> {
    public AddBuffTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (!(owner.getMaidData().getJob() instanceof EffectCasterMaidJob)) {
            return false;
        } else if (owner.ticksExisted % 60 != 0) {
            return false;
        } else {
            return owner.getOwner() != null && owner.getOwner().isAlive() && (owner.getOwner().getAttackingEntity() != null);
        }
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        if (entityIn.getOwner() != null && entityIn.getOwner().isAlive()) {
            if (entityIn.getMaidData().getJob() instanceof EffectCasterMaidJob) {
                ((EffectCasterMaidJob) entityIn.getMaidData().getJob()).castMagic(entityIn, entityIn.getOwner());
            }
        }
    }


    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        return false;
    }

    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidBaseEntity owner, long gameTime) {
        super.updateTask(worldIn, owner, gameTime);
    }

    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
    }
}
