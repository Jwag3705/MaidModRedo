package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;

public class BowShootTask extends Task<LittleMaidBaseEntity> {
    private final MemoryModuleType<? extends Entity> field_220541_a;
    private final float field_220542_b;
    protected int attackTick = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;
    private CrossbowState field_220749_b = CrossbowState.UNCHARGED;
    private int field_220753_f;

    public BowShootTask(MemoryModuleType<? extends Entity> p_i50346_1_, float p_i50346_2_) {
        super(ImmutableMap.of(p_i50346_1_, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220541_a = p_i50346_1_;
        this.field_220542_b = p_i50346_2_;
    }

    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        Entity entity = owner.getBrain().getMemory(this.field_220541_a).get();
        double d0 = getTargetDistance(owner);

        return !isYourFriend(owner) && !isYourOwner(owner) && owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof ShootableItem && !owner.findAmmo(owner.getHeldItem(Hand.MAIN_HAND)).isEmpty() && owner.getDistanceSq(entity) < d0 * d0;
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
                return entityIn.getDistanceSq(entity) < d0 * 1.2f && entityIn.getHeldItem(Hand.MAIN_HAND).getItem() instanceof ShootableItem && !entityIn.findAmmo(entityIn.getHeldItem(Hand.MAIN_HAND)).isEmpty();
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

        entityIn.resetActiveHand();
        if (entityIn.isHandActive()) {
            ((ICrossbowUser) entityIn).setCharging(false);
            CrossbowItem.setCharged(entityIn.getActiveItemStack(), false);
        }

        Brain<?> brain = entityIn.getBrain();
        entityIn.getBrain().removeMemory(this.field_220541_a);
        brain.setFallbackActivity(Activity.IDLE);
        brain.updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
    }

    protected double getTargetDistance(LittleMaidBaseEntity entity) {
        IAttributeInstance iattributeinstance = entity.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getValue();
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        Entity entity = entityIn.getBrain().getMemory(this.field_220541_a).get();
        if (entity != null && entity instanceof LivingEntity) {
            entityIn.setAttackTarget((LivingEntity) entity);
        }
        entityIn.setAggroed(true);
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

            boolean flag3 = (d0 > (double) 18 * 18 || this.seeTime < 5) && field_220753_f == 0;
            if (owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof CrossbowItem && flag3) {
                owner.getNavigator().tryMoveToEntityLiving(entity, this.field_220542_b);
            } else if (owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof CrossbowItem && !flag3) {
                owner.getNavigator().clearPath();
            } else if (!(d0 > (double) getTargetDistance(owner) * getTargetDistance(owner)) && this.seeTime >= 20 && this.field_220753_f == 0) {
                    owner.getNavigator().clearPath();
                    ++this.strafingTime;
                } else if (this.field_220753_f == 0) {
                    owner.getNavigator().tryMoveToEntityLiving(entity, this.field_220542_b);
                    this.strafingTime = -1;
                } else {
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

            int i = owner.getItemInUseMaxCount();

            if (owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof CrossbowItem) {
                ItemStack itemstack = owner.getActiveItemStack();
                if (this.field_220749_b == CrossbowState.UNCHARGED) {

                    owner.setActiveHand(ProjectileHelper.getHandWith(owner, Items.CROSSBOW));
                    this.field_220749_b = CrossbowState.CHARGING;
                    ((ICrossbowUser) owner).setCharging(true);

                } else if (this.field_220749_b == CrossbowState.CHARGING) {
                    if (!owner.isHandActive()) {
                        this.field_220749_b = CrossbowState.UNCHARGED;
                    }

                    if (i >= CrossbowItem.getChargeTime(itemstack)) {
                        owner.stopActiveHand();
                        this.field_220749_b = CrossbowState.CHARGED;
                        this.field_220753_f = 20 + owner.getRNG().nextInt(15);
                        ((ICrossbowUser) owner).setCharging(false);
                    }
                } else if (this.field_220749_b == CrossbowState.CHARGED) {
                    --this.field_220753_f;
                    if (this.field_220753_f == 0) {
                        this.field_220749_b = CrossbowState.READY_TO_ATTACK;
                    }
                } else if (this.field_220749_b == CrossbowState.READY_TO_ATTACK && flag) {
                    owner.giveExperiencePoints(2 + owner.getRNG().nextInt(2));
                    owner.attackEntityWithRangedAttack((LivingEntity) entity, BowItem.getArrowVelocity(i));
                    ItemStack itemstack1 = owner.getHeldItem(ProjectileHelper.getHandWith(owner, Items.CROSSBOW));
                    CrossbowItem.setCharged(itemstack1, false);
                    this.field_220749_b = CrossbowState.UNCHARGED;
                }
            } else if (owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof BowItem) {
                if (owner.isHandActive()) {
                    if (!flag && this.seeTime < -60) {
                        owner.resetActiveHand();
                    } else if (flag) {
                        if (i >= 20) {
                            owner.giveExperiencePoints(1 + owner.getRNG().nextInt(2));
                            owner.resetActiveHand();
                            owner.attackEntityWithRangedAttack((LivingEntity) entity, BowItem.getArrowVelocity(i));
                            this.attackTick = 20;
                        }
                    }
                } else if (--this.attackTick <= 0 && this.seeTime >= -60) {
                    owner.setActiveHand(ProjectileHelper.getHandWith(owner, owner.getHeldItem(Hand.MAIN_HAND).getItem()));
                }
            }
        }
    }

    static enum CrossbowState {
        UNCHARGED,
        CHARGING,
        CHARGED,
        READY_TO_ATTACK;
    }
}