package mmr.littledelicacies.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.MaidJob;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Optional;

public class ChefTask extends Task<LittleMaidBaseEntity> {
    private boolean isCooking;
    private boolean isFinishCooking;
    private boolean canCooking;
    private int cookingTick;
    @Nullable
    private BlockPos cookingPos;


    public ChefTask() {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT), Integer.MAX_VALUE);
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (owner.getMaidData().getJob() != MaidJob.CHEF) {
            return false;
        } else if (!(owner.getHeldItem(Hand.MAIN_HAND).getItem() == Items.COAL)) {
            return false;
        } else {
            this.isCooking = false;
            this.isFinishCooking = false;
            this.canCooking = false;
            this.cookingTick = 0;
            Inventory inventory = owner.getInventoryMaidMain();
            int i = inventory.getSizeInventory();

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (itemstack.isFood()) {
                    this.canCooking = true;
                    break;
                }
            }


            if (this.func_223516_a(owner.getBrain().getMemory(MemoryModuleType.JOB_SITE).get().getPos(), worldIn)) {
                this.cookingPos = owner.getBrain().getMemory(MemoryModuleType.JOB_SITE).get().getPos();
            }


            return this.canCooking && this.cookingPos != null;
        }
    }


    private boolean func_223516_a(BlockPos p_223516_1_, ServerWorld p_223516_2_) {
        BlockState blockstate = p_223516_2_.getBlockState(p_223516_1_);
        Block block = blockstate.getBlock();
        return block.getBlock() instanceof FurnaceBlock || block.getBlock() instanceof CampfireBlock;
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        return !this.isFinishCooking;
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        if (this.cookingPos != null) {
            entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(this.cookingPos));
            entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosWrapper(this.cookingPos), 0.5F, 5));
        }
    }

    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidBaseEntity owner, long gameTime) {
        if (this.cookingPos != null) {
            TileEntity tileentity = worldIn.getTileEntity(cookingPos);

            if (isCooking) {
                if (tileentity instanceof CampfireTileEntity) {
                    if (this.cookingTick >= 300) {
                        this.isFinishCooking = true;
                    }
                }

                if (tileentity instanceof FurnaceTileEntity) {
                    ItemStack cookedItem = ((FurnaceTileEntity) tileentity).getStackInSlot(2);
                    if (!((FurnaceTileEntity) tileentity).getStackInSlot(2).isEmpty()) {
                        for (int j = 0; j < owner.getInventoryMaidMain().getSizeInventory(); ++j) {
                            ItemStack itemstack = owner.getInventoryMaidMain().getStackInSlot(j);
                            if (itemstack.getCount() < owner.getInventoryMaidMain().getInventoryStackLimit() && (itemstack.isEmpty() || itemstack.getItem() == cookedItem.getItem())) {
                                owner.getInventoryMaidMain().addItem(cookedItem);
                                ((FurnaceTileEntity) tileentity).setInventorySlotContents(2, ItemStack.EMPTY);

                                owner.giveExperiencePoints(1 + worldIn.rand.nextInt(2));
                                owner.swingArm(Hand.MAIN_HAND);
                                owner.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.6F, 0.7F);
                                break;
                            }
                        }
                    }


                    if (this.cookingTick >= 600) {
                        this.isFinishCooking = true;
                    }
                }

                this.cookingTick++;
            } else {
                this.cookingTick = 0;
                if (owner.getDistanceSq(cookingPos.getX(), cookingPos.getY(), cookingPos.getZ()) < 12F) {

                    if (tileentity instanceof FurnaceTileEntity) {
                        ItemStack food = findFood(owner);

                        if (worldIn.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(food), worldIn).isPresent()) {
                            ItemStack itemstack = worldIn.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(food), worldIn).get().getRecipeOutput();
                            if (!itemstack.isEmpty() || food.isItemEqual(itemstack)) {
                                if (((FurnaceTileEntity) tileentity).getStackInSlot(0).getCount() < ((FurnaceTileEntity) tileentity).getStackInSlot(0).getMaxStackSize()) {
                                    owner.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.6F, 0.7F);


                                    if (((FurnaceTileEntity) tileentity).getStackInSlot(0).isEmpty()) {

                                        ((FurnaceTileEntity) tileentity).setInventorySlotContents(0, food.copy());

                                        food.setCount(1);
                                        food.shrink(1);

                                    } else {
                                        isCooking = true;
                                    }

                                    owner.swingArm(Hand.MAIN_HAND);
                                    isCooking = true;
                                }
                            } else {
                                this.isFinishCooking = true;
                            }
                        } else {
                            this.isFinishCooking = true;
                        }

                        if (((FurnaceTileEntity) tileentity).getStackInSlot(1).isEmpty() && !owner.getHeldItem(Hand.MAIN_HAND).isEmpty()) {
                            ItemStack coal = owner.getHeldItem(Hand.MAIN_HAND).copy();
                            coal.setCount(1);
                            ((FurnaceTileEntity) tileentity).setInventorySlotContents(1, coal);

                            owner.giveExperiencePoints(1);
                            owner.getHeldItem(Hand.MAIN_HAND).shrink(1);
                        }

                    }

                    if (tileentity instanceof CampfireTileEntity) {
                        ItemStack food = findFood(owner);
                        Optional<CampfireCookingRecipe> optional = ((CampfireTileEntity) tileentity).findMatchingRecipe(food);

                        if (optional.isPresent()) {
                            for (int i = 0; i < 4; i++) {
                                if (!worldIn.isRemote) {
                                    if (((CampfireTileEntity) tileentity).addItem(food, optional.get().getCookTime())) {
                                        owner.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.6F, 0.7F);
                                        owner.swingArm(Hand.MAIN_HAND);
                                    } else {
                                        if (((CampfireTileEntity) tileentity).getInventory().isEmpty()) {
                                            this.isFinishCooking = true;
                                            break;
                                        } else {
                                            isCooking = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            this.isFinishCooking = true;
                        }

                    }
                }
            }
        }

    }

    private ItemStack findFood(LittleMaidBaseEntity owner) {
        Inventory inventory = owner.getInventoryMaidMain();
        int i = inventory.getSizeInventory();

        for (int j = 0; j < i; ++j) {
            ItemStack itemstack = inventory.getStackInSlot(j);
            if (itemstack.isFood()) {
                return itemstack;
            }
        }

        return ItemStack.EMPTY;
    }

    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        entityIn.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
        entityIn.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
        Brain<?> brain = entityIn.getBrain();
        brain.updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
    }

    @Override
    protected boolean isTimedOut(long gameTime) {
        return false;
    }
}
