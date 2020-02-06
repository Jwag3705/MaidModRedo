package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.init.MaidJob;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Map;

public class RipperTask extends Task<LittleMaidBaseEntity> {
    private final EntityPredicate field_220845_e = (new EntityPredicate()).setDistance(10.0D);

    private static final Map<DyeColor, IItemProvider> WOOL_BY_COLOR = Util.make(Maps.newEnumMap(DyeColor.class), (p_203402_0_) -> {
        p_203402_0_.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        p_203402_0_.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        p_203402_0_.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        p_203402_0_.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        p_203402_0_.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        p_203402_0_.put(DyeColor.LIME, Blocks.LIME_WOOL);
        p_203402_0_.put(DyeColor.PINK, Blocks.PINK_WOOL);
        p_203402_0_.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        p_203402_0_.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        p_203402_0_.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        p_203402_0_.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        p_203402_0_.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        p_203402_0_.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        p_203402_0_.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        p_203402_0_.put(DyeColor.RED, Blocks.RED_WOOL);
        p_203402_0_.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    });
    private boolean canShear;
    private SheepEntity sheepEntity;

    public RipperTask() {
        super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT), 300);
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (owner.getMaidData().getJob() != MaidJob.RIPPER) {
            return false;
        } else if (!(owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof ShearsItem)) {
            return false;
        } else {
            this.canShear = false;
            Inventory inventory = owner.getInventoryMaidMain();
            int i = inventory.getSizeInventory();

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (itemstack.isEmpty()) {
                    this.canShear = true;
                    break;
                }
            }

            sheepEntity = findSheep(owner);

            return sheepEntity != null && !sheepEntity.isChild() && !sheepEntity.getSheared() && this.canShear;
        }
    }

    private SheepEntity findSheep(LittleMaidBaseEntity owner) {
        List<SheepEntity> list = owner.world.getEntitiesWithinAABB(SheepEntity.class, owner.getBoundingBox().grow(12.0D, 4.0D, 12.0D), LivingEntity::isAlive);
        return list.get(owner.getRNG().nextInt(list.size()));
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        SheepEntity livingEntity = this.sheepEntity;
        return entityIn.getHeldItem(Hand.MAIN_HAND).getItem() instanceof ShearsItem && this.sheepEntity != null && !livingEntity.isChild() && !((SheepEntity) livingEntity).getSheared() && entityIn.getDistanceSq(livingEntity) < 12 * 12;
    }

    @Override
    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        super.startExecuting(worldIn, entityIn, gameTimeIn);
        if (this.sheepEntity != null) {
            entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(this.sheepEntity));
            entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityPosWrapper(this.sheepEntity), 0.5F, 1));
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
        if (this.sheepEntity != null) {
            if (owner.getDistanceSq(this.sheepEntity) < 4 * 3) {
                if (!this.sheepEntity.isChild() && !((SheepEntity) this.sheepEntity).getSheared()) {
                    this.sheepEntity.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);

                    ((SheepEntity) this.sheepEntity).setSheared(true);

                    int i = 1 + worldIn.rand.nextInt(3);

                    for (int j = 0; j < i; ++j) {
                        ItemEntity itementity = this.sheepEntity.entityDropItem(WOOL_BY_COLOR.get(((SheepEntity) this.sheepEntity).getFleeceColor()), 1);
                        if (itementity != null) {
                            itementity.setMotion(itementity.getMotion().add((double) ((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.1F), (double) (worldIn.rand.nextFloat() * 0.05F), (double) ((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.1F)));
                        }
                    }
                    owner.giveExperiencePoints(2 + owner.getRNG().nextInt(1));
                }
            }
        }
    }
}
