package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.maidmodredo.entity.LittleMaidEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.BowItem;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;

public class BowShootTask extends Task<LittleMaidEntity> {
    private final MemoryModuleType<? extends Entity> field_220541_a;
    private final float field_220542_b;
    protected int attackTick = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public BowShootTask(MemoryModuleType<? extends Entity> p_i50346_1_, float p_i50346_2_) {
        super(ImmutableMap.of(p_i50346_1_, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220541_a = p_i50346_1_;
        this.field_220542_b = p_i50346_2_;
    }

    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidEntity owner) {
        Entity entity = owner.getBrain().getMemory(this.field_220541_a).get();
        double d0 = getTargetDistance(owner);

        return !isYourFriend(owner) && !isYourOwner(owner) && owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof BowItem && !owner.findAmmo(owner.getHeldItem(Hand.MAIN_HAND)).isEmpty() && owner.getDistanceSq(entity) < d0 * d0;
    }

    private boolean isYourOwner(LittleMaidEntity entityIn) {
        return entityIn.getBrain().getMemory(this.field_220541_a).isPresent() && entityIn.getOwner() == entityIn.getBrain().getMemory(this.field_220541_a).get();
    }

    private boolean isYourFriend(LittleMaidEntity entityIn) {
        if (entityIn.getBrain().getMemory(this.field_220541_a).isPresent()) {
            if (entityIn.getBrain().getMemory(this.field_220541_a).get() instanceof LittleMaidEntity) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isTimedOut(long gameTime) {
        return false;
    }

    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidEntity entityIn, long gameTimeIn) {
        if (entityIn.getBrain().getMemory(this.field_220541_a).isPresent()) {
            Entity entity = entityIn.getBrain().getMemory(this.field_220541_a).get();
            if (entity != null && entity.isAlive()) {
                double d0 = getTargetDistance(entityIn) * getTargetDistance(entityIn);
                return entityIn.getDistanceSq(entity) < d0 * 1.2f && entityIn.getHeldItem(Hand.MAIN_HAND).getItem() instanceof BowItem && !entityIn.findAmmo(entityIn.getHeldItem(Hand.MAIN_HAND)).isEmpty();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    protected void resetTask(ServerWorld worldIn, LittleMaidEntity entityIn, long gameTimeIn) {
        super.resetTask(worldIn, entityIn, gameTimeIn);
        this.seeTime = 0;
        this.attackTick = -1;
        entityIn.setAggroed(false);
        entityIn.resetActiveHand();
        Brain<?> brain = entityIn.getBrain();
        entityIn.getBrain().removeMemory(this.field_220541_a);
        brain.updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
    }

    protected double getTargetDistance(LittleMaidEntity entity) {
        IAttributeInstance iattributeinstance = entity.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getValue();
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidEntity entityIn, long gameTimeIn) {

        entityIn.setAggroed(true);
    }


    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidEntity owner, long gameTime) {
        Entity entity = owner.getBrain().getMemory(this.field_220541_a).get();
        if (entity != null && entity instanceof LivingEntity) {
            double d0 = owner.getDistanceSq(entity.posX, entity.getBoundingBox().minY, entity.posZ);

            boolean flag = owner.getEntitySenses().canSee(entity);

            boolean flag1 = this.seeTime > 0;

            Brain<?> brain = owner.getBrain();
            boolean flag2 = owner.getNavigator().getPath() != null && owner.getNavigator().getPath().func_224771_h();
            if (flag) {
                brain.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, Optional.empty());
            } else if (!brain.hasMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
                brain.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, worldIn.getGameTime());
            }

            if (flag != flag1) {
                this.seeTime = 0;
            }

            if (flag) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            if (!flag2) {
                if (!(d0 > (double) getTargetDistance(owner) * getTargetDistance(owner)) && this.seeTime >= 20) {
                    owner.getNavigator().clearPath();
                    ++this.strafingTime;
                } else {
                    owner.getNavigator().tryMoveToEntityLiving(entity, this.field_220542_b);
                    this.strafingTime = -1;
                }

                if (this.strafingTime >= 20) {
                    if ((double) owner.getRNG().nextFloat() < 0.3D) {
                        this.strafingClockwise = !this.strafingClockwise;
                    }

                    if ((double) owner.getRNG().nextFloat() < 0.3D) {
                        this.strafingBackwards = !this.strafingBackwards;
                    }

                    this.strafingTime = 0;
                }

                if (this.strafingTime > -1) {
                    if (d0 > (double) (getTargetDistance(owner) * getTargetDistance(owner) * 0.9F)) {
                        this.strafingBackwards = false;
                    } else if (d0 < (double) (getTargetDistance(owner) * getTargetDistance(owner) * 0.75F)) {
                        this.strafingBackwards = true;
                    }

                    owner.getMoveHelper().strafe(this.strafingBackwards ? -0.45F : 0.45F, this.strafingClockwise ? 0.45F : -0.45F);
                    owner.faceEntity(entity, 40.0F, 40.0F);
                } else {
                    owner.getLookController().setLookPositionWithEntity(entity, 40.0F, 40.0F);
                }
            } else {
                owner.getLookController().setLookPositionWithEntity(entity, 40.0F, 40.0F);
                this.strafingTime = -1;
            }


            if (owner.isHandActive()) {
                if (!flag && this.seeTime < -60) {
                    owner.resetActiveHand();
                } else if (flag) {
                    int i = owner.getItemInUseMaxCount();
                    if (i >= 20) {
                        owner.resetActiveHand();
                        owner.attackEntityWithRangedAttack((LivingEntity) entity, BowItem.getArrowVelocity(i));
                        this.attackTick = 40;
                    }
                }
            } else if (--this.attackTick <= 0 && this.seeTime >= -60) {
                owner.setActiveHand(ProjectileHelper.getHandWith(owner, owner.getHeldItem(Hand.MAIN_HAND).getItem()));
            }
        }
    }

}