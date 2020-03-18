package mmr.littledelicacies.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.MaidJob;
import mmr.littledelicacies.utils.recipes.ScienceRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class ScienceTask extends Task<LittleMaidBaseEntity> {
    private boolean isFinishMaking;
    private boolean canMaking;
    private int cookingTick;
    @Nullable
    private BlockPos sciencePos;

    public ScienceTask() {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (owner.getMaidData().getJob() != MaidJob.SCIENTIST) {
            return false;
        } else {
            this.isFinishMaking = false;
            this.canMaking = false;
            this.cookingTick = 0;
            Inventory inventory = owner.getInventoryMaidMain();
            if (getRecipesResult(owner, true) != null) {
                this.canMaking = true;
            }

            if (this.func_223516_a(owner.getBrain().getMemory(MemoryModuleType.JOB_SITE).get().getPos(), worldIn)) {
                this.sciencePos = owner.getBrain().getMemory(MemoryModuleType.JOB_SITE).get().getPos();
            }

            return this.canMaking && this.sciencePos != null;
        }
    }

    private boolean func_223516_a(BlockPos p_223516_1_, ServerWorld p_223516_2_) {
        BlockState blockstate = p_223516_2_.getBlockState(p_223516_1_);
        Block block = blockstate.getBlock();
        return block.getBlock() == Blocks.SMOOTH_STONE || block.getBlock() == Blocks.SMOOTH_STONE_SLAB;
    }

    protected ScienceRecipes getRecipesResult(LittleMaidBaseEntity owner, boolean simulator) {
        for (ScienceRecipes recipes : ScienceRecipes.scienceRecipeList) {
            ItemStack[] stack = recipes.getResult(owner.getInventoryMaidMain(), simulator);
            if (stack.length > 0) {
                return recipes;
            }
        }
        return null;
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        if (this.sciencePos != null) {
            entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(this.sciencePos));
            entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosWrapper(this.sciencePos), 0.5F, 3));
        }
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        return !this.isFinishMaking;
    }

    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidBaseEntity owner, long gameTime) {
        if (this.sciencePos != null) {
            owner.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(this.sciencePos));
            owner.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosWrapper(this.sciencePos), 0.5F, 3));
        }

        if (++this.cookingTick >= 600) {
            this.cookingTick = 0;

            ScienceRecipes recipes = getRecipesResult(owner, false);

            ItemStack stack = recipes.resultItems[0];

            if (worldIn.rand.nextFloat() < recipes.getProbability()) {
                for (int j = 0; j < owner.getInventoryMaidMain().getSizeInventory(); ++j) {
                    ItemStack itemstack = owner.getInventoryMaidMain().getStackInSlot(j);
                    if (itemstack.getCount() < owner.getInventoryMaidMain().getInventoryStackLimit() && (itemstack.isEmpty() || itemstack.getItem() == stack.getItem())) {
                        owner.getInventoryMaidMain().addItem(stack);


                        owner.giveExperiencePoints(7 + worldIn.rand.nextInt(4));
                        owner.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.8F, 0.7F);
                        break;
                    }
                }
            } else {
                owner.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 0.8F, 0.7F);
            }

            this.isFinishMaking = true;
        }
    }

    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        entityIn.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
        entityIn.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected boolean isTimedOut(long gameTime) {
        return false;
    }
}
