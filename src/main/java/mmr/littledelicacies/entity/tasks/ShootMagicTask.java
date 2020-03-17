package mmr.littledelicacies.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.LittleItems;
import mmr.littledelicacies.maidjob.CasterMaidJob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

public class ShootMagicTask extends Task<LittleMaidBaseEntity> {
    private final MemoryModuleType<? extends Entity> field_220541_a;
    private final float field_220542_b;
    protected int attackTick = -1;
    private int seeTime;

    public ShootMagicTask(MemoryModuleType<? extends Entity> p_i50346_1_, float p_i50346_2_) {
        super(ImmutableMap.of(p_i50346_1_, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220541_a = p_i50346_1_;
        this.field_220542_b = p_i50346_2_;
    }

    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        Entity entity = owner.getBrain().getMemory(this.field_220541_a).get();
        double d0 = getTargetDistance(owner);

        if (!isYourFriend(owner) && !isYourOwner(owner) && owner.getHeldItem(Hand.MAIN_HAND).getItem() == LittleItems.MAGE_STUFF && owner.getDistanceSq(entity) < d0 * d0) {
            return true;
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
            if (entity != null && entity.isAlive()) {
                double d0 = getTargetDistance(entityIn) * getTargetDistance(entityIn);
                return entityIn.getDistanceSq(entity) < d0 * 1.15f && entityIn.getHeldItem(Hand.MAIN_HAND).getItem() == LittleItems.MAGE_STUFF;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        super.resetTask(worldIn, entityIn, gameTimeIn);
        this.seeTime = 0;
        this.attackTick = -1;
        entityIn.setAggroed(false);

        entityIn.getNavigator().clearPath();


        Brain<?> brain = entityIn.getBrain();
        entityIn.getBrain().removeMemory(this.field_220541_a);
        brain.updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
    }

    protected double getTargetDistance(LittleMaidBaseEntity entity) {
        IAttributeInstance iattributeinstance = entity.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getValue();
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        Entity entity = entityIn.getBrain().getMemory(this.field_220541_a).get();
    }


    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidBaseEntity owner, long gameTime) {
        Entity entity = owner.getBrain().getMemory(this.field_220541_a).get();
        if (entity != null && entity instanceof LivingEntity) {
            double d0 = owner.getDistanceSq(entity.getPosX(), entity.getBoundingBox().minY, entity.getPosZ());

            boolean flag = owner.getEntitySenses().canSee(entity);

            boolean flag1 = this.seeTime > 0;

            if (flag != flag1) {
                this.seeTime = 0;
            }

            if (flag) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            if (!(d0 > (double) getTargetDistance(owner) * getTargetDistance(owner)) && this.seeTime >= 20) {
                owner.getNavigator().clearPath();
            } else {
                owner.getNavigator().tryMoveToEntityLiving(entity, this.field_220542_b);
            }
            owner.getLookController().setLookPositionWithEntity(entity, 40.0F, 40.0F);

            this.attackTick = Math.max(this.attackTick - 1, 0);
            if (owner.getHeldItem(Hand.MAIN_HAND).getItem() == LittleItems.MAGE_STUFF) {

                if (flag) {
                    if (attackTick <= 0) {
                        owner.giveExperiencePoints(1 + owner.getRNG().nextInt(2));

                        if (owner.getMaidData().getJob() instanceof CasterMaidJob) {
                            ((CasterMaidJob) owner.getMaidData().getJob()).castMagic(owner, entity);
                        }

                        this.attackTick = 40;
                    }
                }
            }
        }
    }

}