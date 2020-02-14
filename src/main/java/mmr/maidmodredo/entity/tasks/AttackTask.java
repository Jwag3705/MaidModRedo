package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.init.MaidJob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;

public class AttackTask extends Task<LittleMaidBaseEntity> {
    private final MemoryModuleType<? extends Entity> field_220541_a;
    private final float field_220542_b;
    protected int attackTick;

    public AttackTask(MemoryModuleType<? extends Entity> p_i50346_1_, float p_i50346_2_) {
        super(ImmutableMap.of(p_i50346_1_, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220541_a = p_i50346_1_;
        this.field_220542_b = p_i50346_2_;
    }

    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        double d0 = getTargetDistance(owner);
        if (owner.getBrain().getMemory(this.field_220541_a).isPresent()) {
            Entity entity = owner.getBrain().getMemory(this.field_220541_a).get();
            return !isYourFriend(owner) && !isYourOwner(owner) && owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem && owner.getDistanceSq(entity) < d0 * d0;
        } else {
            Brain<?> brain = owner.getBrain();
            owner.getBrain().removeMemory(this.field_220541_a);
            brain.updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
            return false;
        }
    }

    private boolean isYourOwner(LittleMaidBaseEntity entityIn) {
        return entityIn.getBrain().getMemory(this.field_220541_a).isPresent() && entityIn.getOwner() == entityIn.getBrain().getMemory(this.field_220541_a).get();
    }

    private boolean isYourFriend(LittleMaidBaseEntity entityIn) {
        if (entityIn.getBrain().getMemory(this.field_220541_a).isPresent()) {
            if (entityIn.getBrain().getMemory(this.field_220541_a).get() instanceof LittleMaidBaseEntity) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isTimedOut(long gameTime) {
        return false;
    }

    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        if (entityIn.getBrain().getMemory(this.field_220541_a).isPresent()) {
            Entity entity = entityIn.getBrain().getMemory(this.field_220541_a).get();
            if (entity.isAlive() && entity != null) {
                double d0 = getTargetDistance(entityIn);
                return entityIn.getDistanceSq(entity) < d0 * d0 * 1.15f;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected double getTargetDistance(LittleMaidBaseEntity entity) {
        IAttributeInstance iattributeinstance = entity.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getValue();
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        Entity entity = entityIn.getBrain().getMemory(this.field_220541_a).get();
        setWalk(entityIn, entity, this.field_220542_b);
    }

    @Override
    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        super.resetTask(worldIn, entityIn, gameTimeIn);
        entityIn.resetActiveHand();
        entityIn.getNavigator().clearPath();
        Brain<?> brain = entityIn.getBrain();
        entityIn.getBrain().removeMemory(this.field_220541_a);
        brain.updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
    }

    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidBaseEntity owner, long gameTime) {
        Entity entity = owner.getBrain().getMemory(this.field_220541_a).get();
        if (entity != null) {
            setWalk(owner, entity, this.field_220542_b);

            owner.getLookController().setLookPositionWithEntity(entity, 30.0F, 30.0F);
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
    }

    private void setGuard(LittleMaidBaseEntity owner) {
        if (owner.getHeldItem(Hand.OFF_HAND).getItem() instanceof ShieldItem) {
            owner.setActiveHand(Hand.OFF_HAND);
        }
    }

    public void setWalk(LittleMaidBaseEntity p_220540_0_, Entity p_220540_1_, float p_220540_2_) {
        Vec3d vec3d = new Vec3d(p_220540_1_.getPosX(), p_220540_1_.getPosY(), p_220540_1_.getPosZ());
        p_220540_0_.getNavigator().tryMoveToEntityLiving(p_220540_1_, p_220540_2_);
        double d0 = p_220540_0_.getDistanceSq(p_220540_1_.getPosX(), p_220540_1_.getBoundingBox().minY, p_220540_1_.getPosZ());

        this.checkAndPerformAttack(p_220540_0_, p_220540_1_, d0);
    }

    protected void checkAndPerformAttack(LittleMaidBaseEntity attacker, Entity enemy, double distToEnemySqr) {
        double d0 = this.getAttackReachSqr(attacker, enemy);
        if (distToEnemySqr <= d0 && this.attackTick <= 0) {
            this.attackTick = 20;
            attacker.attackEntityAsMob(enemy);
            attacker.getHeldItem(Hand.MAIN_HAND).damageItem(1, attacker, (p_213625_1_) -> {
                p_213625_1_.sendBreakAnimation(Hand.MAIN_HAND);
            });

            if (attacker.getMaidData().getJob() == MaidJob.GUARD) {
                attacker.resetActiveHand();
            }
        } else {
            if (attacker.getMaidData().getJob() == MaidJob.GUARD) {
                setGuard(attacker);
            }
        }

    }

    protected double getAttackReachSqr(LittleMaidBaseEntity attacker, Entity attackTarget) {
        return (double) (attacker.getWidth() * 2.05F * attacker.getWidth() * 2.05F + attackTarget.getWidth());
    }
}