package mmr.littledelicacies.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.MaidJob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.tuple.Pair;

public class HealingTask extends Task<LittleMaidBaseEntity> {
    private ItemStack stack;

    public HealingTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (owner.getMaidData().getJob() != MaidJob.HEALER) {
            return false;
        } else if (owner.ticksExisted % 40 != 0) {
            return false;
        } else {


            Inventory inventory = owner.getInventoryMaidMain();
            int i = inventory.getSizeInventory();

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (owner.getOwner() != null && owner.getOwner().isAlive()) {
                    if (owner.getOwner() instanceof PlayerEntity && ((PlayerEntity) owner.getOwner()).getFoodStats().needFood() && itemstack.getItem().getFood() != null) {

                        stack = itemstack.copy();
                        stack.setCount(1);
                        itemstack.shrink(1);

                        return true;
                    }
                }
            }


            return false;
        }
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        if (entityIn.getOwner() != null && entityIn.getOwner().isAlive()) {
            this.applyFoodEffects(stack, worldIn, entityIn);
            entityIn.getOwner().onFoodEaten(worldIn, stack);
        }
    }

    private void applyFoodEffects(ItemStack p_213349_1_, World p_213349_2_, LivingEntity p_213349_3_) {
        Item item = p_213349_1_.getItem();
        if (item.isFood()) {
            for (Pair<EffectInstance, Float> pair : item.getFood().getEffects()) {
                if (!p_213349_2_.isRemote && pair.getLeft() != null && p_213349_2_.rand.nextFloat() < pair.getRight()) {
                    p_213349_3_.addPotionEffect(new EffectInstance(pair.getLeft()));
                }
            }
        }

    }


    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        return false;
    }

    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidBaseEntity owner, long gameTime) {
        super.updateTask(worldIn, owner, gameTime);
    }

    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
    }
}
