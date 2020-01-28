package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;

public class AttackTask extends Task<CreatureEntity> {
    private final MemoryModuleType<? extends Entity> field_220541_a;
    private final float field_220542_b;
    protected int attackTick;

    public AttackTask(MemoryModuleType<? extends Entity> p_i50346_1_, float p_i50346_2_) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, p_i50346_1_, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220541_a = p_i50346_1_;
        this.field_220542_b = p_i50346_2_;
    }

    protected boolean shouldExecute(ServerWorld worldIn, CreatureEntity owner) {
        Entity entity = owner.getBrain().getMemory(this.field_220541_a).get();
        double d0 = getTargetDistance(owner);
        return owner.getDistanceSq(entity) < d0;
    }

    protected boolean shouldContinueExecuting(ServerWorld worldIn, CreatureEntity entityIn, long gameTimeIn) {
        if (entityIn.getBrain().getMemory(this.field_220541_a).isPresent()) {
            Entity entity = entityIn.getBrain().getMemory(this.field_220541_a).get();
            if (entityIn.isAlive() && entity != null) {
                double d0 = getTargetDistance(entityIn);
                return entity.getDistanceSq(entity) < d0 * 1.15f;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    protected double getTargetDistance(CreatureEntity entity) {
        IAttributeInstance iattributeinstance = entity.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getValue();
    }

    protected void startExecuting(ServerWorld worldIn, CreatureEntity entityIn, long gameTimeIn) {
        Entity entity = entityIn.getBrain().getMemory(this.field_220541_a).get();
        setWalk(entityIn, entity, this.field_220542_b);
    }


    @Override
    protected void updateTask(ServerWorld worldIn, CreatureEntity owner, long gameTime) {
        Entity entity = owner.getBrain().getMemory(this.field_220541_a).get();
        if (entity != null) {
            setWalk(owner, entity, this.field_220542_b);
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
    }

    public void setWalk(CreatureEntity p_220540_0_, Entity p_220540_1_, float p_220540_2_) {
        Vec3d vec3d = new Vec3d(p_220540_1_.posX, p_220540_1_.posY, p_220540_1_.posZ);
        p_220540_0_.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3d, p_220540_2_, 0));
        p_220540_0_.getLookController().setLookPositionWithEntity(p_220540_1_, 30.0F, 30.0F);

        double d0 = p_220540_0_.getDistanceSq(p_220540_1_.posX, p_220540_1_.getBoundingBox().minY, p_220540_1_.posZ);

        this.checkAndPerformAttack(p_220540_0_, p_220540_1_, d0);
    }

    protected void checkAndPerformAttack(CreatureEntity attacker, Entity enemy, double distToEnemySqr) {
        double d0 = this.getAttackReachSqr(attacker, enemy);
        if (distToEnemySqr <= d0 && this.attackTick <= 0) {
            this.attackTick = 20;
            attacker.swingArm(Hand.MAIN_HAND);
            attacker.attackEntityAsMob(enemy);
        }

    }

    protected double getAttackReachSqr(CreatureEntity attacker, Entity attackTarget) {
        return (double) (attacker.getWidth() * 2.0F * attacker.getWidth() * 2.0F + attackTarget.getWidth());
    }
}