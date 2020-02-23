package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.init.MaidJob;
import mmr.maidmodredo.item.ButcherKnifeItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class ButcherTask extends Task<LittleMaidBaseEntity> {

    private boolean canButcher;
    private AnimalEntity animalEntity;
    protected int attackTick;

    public ButcherTask() {
        super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT), 300);
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (owner.getMaidData().getJob() != MaidJob.BUTCHER) {
            return false;
        } else if (!(owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof ButcherKnifeItem)) {
            return false;
        } else {
            this.canButcher = false;
            Inventory inventory = owner.getInventoryMaidMain();
            int i = inventory.getSizeInventory();

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (itemstack.isEmpty()) {
                    this.canButcher = true;
                    break;
                }
            }

            animalEntity = findAnimal(owner);

            return animalEntity != null && !animalEntity.isChild() && animalEntity.isAlive() && !(animalEntity instanceof TameableEntity) && !(animalEntity instanceof AbstractHorseEntity) && animalEntity.getType() != EntityType.PANDA && this.canButcher;
        }
    }

    private AnimalEntity findAnimal(LittleMaidBaseEntity owner) {
        List<AnimalEntity> list = owner.world.getEntitiesWithinAABB(AnimalEntity.class, owner.getBoundingBox().grow(12.0D, 4.0D, 12.0D));
        if (!list.isEmpty()) {
            return list.get(owner.getRNG().nextInt(list.size()));
        } else {
            return null;
        }
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        AnimalEntity livingEntity = this.animalEntity;
        return entityIn.getHeldItem(Hand.MAIN_HAND).getItem() instanceof ButcherKnifeItem && this.animalEntity != null && !livingEntity.isChild() && ((AnimalEntity) livingEntity).isAlive() && entityIn.getDistanceSq(livingEntity) < 12 * 12;
    }

    @Override
    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        super.startExecuting(worldIn, entityIn, gameTimeIn);
        if (this.animalEntity != null) {
            entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(this.animalEntity));
            entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityPosWrapper(this.animalEntity), 0.575F, 1));
        }
    }

    @Override
    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        super.resetTask(worldIn, entityIn, gameTimeIn);
        entityIn.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
        entityIn.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidBaseEntity owner, long gameTime) {
        super.updateTask(worldIn, owner, gameTime);
        if (this.animalEntity != null) {
            double d0 = owner.getDistanceSq(this.animalEntity.getPosX(), this.animalEntity.getBoundingBox().minY, this.animalEntity.getPosZ());

            owner.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityPosWrapper(this.animalEntity), 0.575F, 1));
            checkAndPerformAttack(owner, this.animalEntity, d0);
        }
        this.attackTick = Math.max(this.attackTick - 1, 0);
    }

    protected void checkAndPerformAttack(LittleMaidBaseEntity attacker, Entity enemy, double distToEnemySqr) {
        double d0 = this.getAttackReachSqr(attacker, enemy);
        if (distToEnemySqr <= d0 && this.attackTick <= 0) {
            this.attackTick = 20;
            attacker.attackEntityAsMob(enemy);
            attacker.swingArm(Hand.MAIN_HAND);
            attacker.getHeldItem(Hand.MAIN_HAND).damageItem(1, attacker, (p_213625_1_) -> {
                p_213625_1_.sendBreakAnimation(Hand.MAIN_HAND);
            });
        }

    }

    protected double getAttackReachSqr(LittleMaidBaseEntity attacker, Entity attackTarget) {
        return (double) (attacker.getWidth() * 2.6F * attacker.getWidth() * 2.6F + attackTarget.getWidth());
    }

}
