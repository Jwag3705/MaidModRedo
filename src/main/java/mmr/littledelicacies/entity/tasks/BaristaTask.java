package mmr.littledelicacies.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.MaidJob;
import mmr.littledelicacies.utils.recipes.BaristaRecipes;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;

public class BaristaTask extends Task<LittleMaidBaseEntity> {
    private boolean isFinishMaking;
    private boolean canMaking;
    private int cookingTick;

    public BaristaTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (owner.getMaidData().getJob() != MaidJob.BARISTA) {
            return false;
        } else if (!(owner.getHeldItem(Hand.MAIN_HAND).getItem() == Items.GLASS_BOTTLE)) {
            return false;
        } else {
            this.isFinishMaking = false;
            this.canMaking = false;
            this.cookingTick = 0;
            Inventory inventory = owner.getInventoryMaidMain();
            if (getRecipesResult(owner, true) != null) {
                this.canMaking = true;
            }


            return this.canMaking;
        }
    }

    protected BaristaRecipes getRecipesResult(LittleMaidBaseEntity owner, boolean simulator) {
        for (BaristaRecipes recipes : BaristaRecipes.baristaRecipesList) {
            ItemStack[] stack = recipes.getResult(owner.getInventoryMaidMain(), simulator);
            if (stack.length > 0) {
                return recipes;
            }
        }
        return null;
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        return !this.isFinishMaking;
    }

    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidBaseEntity owner, long gameTime) {
        if (++this.cookingTick >= 300) {
            this.cookingTick = 0;

            ItemStack stack = getRecipesResult(owner, false).resultItems[0];

            for (int j = 0; j < owner.getInventoryMaidMain().getSizeInventory(); ++j) {
                ItemStack itemstack = owner.getInventoryMaidMain().getStackInSlot(j);
                if (itemstack.getCount() < owner.getInventoryMaidMain().getInventoryStackLimit() && (itemstack.isEmpty() || itemstack.getItem() == stack.getItem())) {
                    if (itemstack.getItem() == stack.getItem()) {
                        stack.setCount(itemstack.getCount() + stack.getCount());
                        owner.getInventoryMaidMain().setInventorySlotContents(j, stack);
                    } else {
                        owner.getInventoryMaidMain().setInventorySlotContents(j, stack);
                    }

                    owner.giveExperiencePoints(3 + worldIn.rand.nextInt(3));
                    owner.playSound(SoundEvents.ITEM_BOTTLE_FILL, 0.6F, 0.7F);
                    break;
                }
            }

            this.isFinishMaking = true;
        }
    }


    @Override
    protected boolean isTimedOut(long gameTime) {
        return false;
    }
}
