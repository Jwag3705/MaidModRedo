package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import mmr.maidmodredo.entity.LittleMaidEntity;
import mmr.maidmodredo.entity.misc.MaidFishingBobberEntity;
import mmr.maidmodredo.init.MaidJob;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class FishingTask extends Task<LittleMaidEntity> {
    private boolean isCatchFish;
    private boolean isFinishFishing;
    private boolean canFishing;
    @Nullable
    private BlockPos field_220422_a;
    private final List<BlockPos> field_223518_f = Lists.newArrayList();

    public FishingTask() {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidEntity owner) {
        if (owner.getMaidData().getJob() != MaidJob.FISHER) {
            return false;
        } else if (!(owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof FishingRodItem)) {
            return false;
        } else {
            this.isCatchFish = false;
            this.isFinishFishing = false;
            this.canFishing = false;
            Inventory inventory = owner.getInventoryMaidMain();
            int i = inventory.getSizeInventory();

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (itemstack.isEmpty()) {
                    this.canFishing = true;
                    break;
                }
            }

            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(owner.posX, owner.posY, owner.posZ);
            this.field_223518_f.clear();

            for (int i1 = -1; i1 <= 1; ++i1) {
                for (int k = -1; k <= 1; ++k) {
                    for (int l = -1; l <= 1; ++l) {
                        blockpos$mutableblockpos.setPos(owner.posX + (double) i1, owner.posY + (double) k, owner.posZ + (double) l);
                        if (this.func_223516_a(blockpos$mutableblockpos, worldIn)) {
                            this.field_223518_f.add(new BlockPos(blockpos$mutableblockpos));
                        }
                    }
                }
            }

            this.field_220422_a = this.func_223517_a(worldIn);
            return this.canFishing && this.field_220422_a != null;
        }
    }


    @Nullable
    private BlockPos func_223517_a(ServerWorld p_223517_1_) {
        return this.field_223518_f.isEmpty() ? null : this.field_223518_f.get(p_223517_1_.getRandom().nextInt(this.field_223518_f.size()));
    }


    private boolean func_223516_a(BlockPos p_223516_1_, ServerWorld p_223516_2_) {
        BlockState blockstate = p_223516_2_.getBlockState(p_223516_1_);
        Block block = blockstate.getBlock();
        Block block1 = p_223516_2_.getBlockState(p_223516_1_.down()).getBlock();
        return block.getFluidState(blockstate).isTagged(FluidTags.WATER);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidEntity entityIn, long gameTimeIn) {
        return !this.isFinishFishing;
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidEntity entityIn, long gameTimeIn) {
        if (this.field_220422_a != null) {
            entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(this.field_220422_a));
        }

        worldIn.playSound((PlayerEntity) null, entityIn.posX, entityIn.posY, entityIn.posZ, SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (entityIn.getRNG().nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            if (entityIn.fishingBobber == null) {
                int k = EnchantmentHelper.getFishingSpeedBonus(entityIn.getHeldItemMainhand());
                int j = EnchantmentHelper.getFishingLuckBonus(entityIn.getHeldItemMainhand());
                worldIn.addEntity(new MaidFishingBobberEntity(entityIn, worldIn, j, k));
            } else {
                entityIn.fishingBobber.remove();
                int k = EnchantmentHelper.getFishingSpeedBonus(entityIn.getHeldItemMainhand());
                int j = EnchantmentHelper.getFishingLuckBonus(entityIn.getHeldItemMainhand());
                worldIn.addEntity(new MaidFishingBobberEntity(entityIn, worldIn, j, k));
            }
        }

        entityIn.swingArm(Hand.MAIN_HAND);
    }

    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidEntity owner, long gameTime) {
        if (owner.fishingBobber != null) {
            if (isCatchFish) {
                if (!worldIn.isRemote) {
                    int i = owner.fishingBobber.handleHookRetraction(owner.getHeldItemMainhand());
                    owner.getHeldItemMainhand().damageItem(i, owner, (p_220000_1_) -> {
                        p_220000_1_.sendBreakAnimation(Hand.MAIN_HAND);
                    });
                }

                owner.giveExperiencePoints(1 + owner.getRNG().nextInt(2));
                owner.swingArm(Hand.MAIN_HAND);
                worldIn.playSound((PlayerEntity) null, owner.posX, owner.posY, owner.posZ, SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (owner.getRNG().nextFloat() * 0.4F + 0.8F));
                isFinishFishing = true;
            } else {
                if (owner.fishingBobber.getTicksCatchable() > 0) {
                    isCatchFish = true;
                }
            }
        }

    }

    protected void resetTask(ServerWorld worldIn, LittleMaidEntity entityIn, long gameTimeIn) {
        entityIn.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
        if (!worldIn.isRemote) {
            if (entityIn.fishingBobber != null) {
                entityIn.fishingBobber.remove();
            }
        }
    }

    @Override
    protected boolean isTimedOut(long gameTime) {
        return false;
    }
}
