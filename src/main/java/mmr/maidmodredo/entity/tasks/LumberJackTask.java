package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.init.MaidJob;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class LumberJackTask extends Task<LittleMaidBaseEntity> {
    private int field_220426_e;
    private boolean isCutWood;
    private boolean isFinishLumberJack;
    private boolean canCutting;
    protected int breakingTime;
    protected int previousBreakProgress = -1;
    @Nullable
    private BlockPos blockPostion;
    private final List<BlockPos> field_223518_f = Lists.newArrayList();

    public LumberJackTask() {
        super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (owner.getMaidData().getJob() != MaidJob.LUMBERJACK) {
            return false;
        } else if (!(owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof AxeItem)) {
            return false;
        } else {
            this.isCutWood = false;
            this.isFinishLumberJack = false;
            this.canCutting = false;
            Inventory inventory = owner.getInventoryMaidMain();
            int i = inventory.getSizeInventory();

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (itemstack.isEmpty()) {
                    this.canCutting = true;
                    break;
                }
            }

            BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable(owner.getPosX(), owner.getPosY(), owner.getPosZ());
            this.field_223518_f.clear();

            for (int i1 = -3; i1 <= 3; ++i1) {
                for (int k = -3; k <= 3; ++k) {
                    for (int l = -3; l <= 3; ++l) {
                        blockpos$mutableblockpos.setPos(owner.getPosX() + (double) i1, owner.getPosY() + (double) k, owner.getPosZ() + (double) l);
                        if (this.func_223516_a(blockpos$mutableblockpos, worldIn)) {
                            this.field_223518_f.add(new BlockPos(blockpos$mutableblockpos));
                        }
                    }
                }
            }

            this.blockPostion = this.func_223517_a(worldIn);
            return this.canCutting && this.blockPostion != null;
        }
    }


    @Nullable
    private BlockPos func_223517_a(ServerWorld p_223517_1_) {
        return this.field_223518_f.isEmpty() ? null : this.field_223518_f.get(p_223517_1_.getRandom().nextInt(this.field_223518_f.size()));
    }


    private boolean func_223516_a(BlockPos p_223516_1_, ServerWorld p_223516_2_) {
        BlockState blockstate = p_223516_2_.getBlockState(p_223516_1_);
        Block block = blockstate.getBlock();
        return block.isIn(BlockTags.LOGS);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        return !this.isFinishLumberJack && !this.isCutWood || this.isCutWood && this.blockPostion.withinDistance(entityIn.getPositionVec(), 10.0D) && !worldIn.getBlockState(this.blockPostion).isAir();
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        if (this.blockPostion != null) {
            entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(this.blockPostion));
            entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosWrapper(this.blockPostion), 0.5F, 1));
        }
        this.field_220426_e = 0;
        this.breakingTime = 0;
    }

    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidBaseEntity owner, long gameTime) {
        if (this.blockPostion != null) {
            BlockState blockstate = worldIn.getBlockState(this.blockPostion);
            Block block = blockstate.getBlock();
            if (blockstate.isIn(BlockTags.LOGS) && !worldIn.getBlockState(this.blockPostion).isAir()) {
                owner.swingArm(Hand.MAIN_HAND);
                isCutWood = true;
                //worldIn.playEvent(2001, this.blockPostion, Block.getStateId(owner.world.getBlockState(this.blockPostion)));

                ++this.breakingTime;
                int i = (int) ((float) this.breakingTime / (float) worldIn.getBlockState(this.blockPostion).getBlockHardness(worldIn, this.blockPostion) * 10.0F);
                if (i != this.previousBreakProgress) {
                    owner.world.sendBlockBreakProgress(owner.getEntityId(), this.blockPostion, i);
                    this.previousBreakProgress = i;
                }
            }

            if (this.breakingTime == worldIn.getBlockState(this.blockPostion).getBlockHardness(worldIn, this.blockPostion) * 10F) {
                owner.world.destroyBlock(this.blockPostion, true);
                owner.giveExperiencePoints(2 + owner.getRNG().nextInt(3));
                isCutWood = false;

                if (worldIn.getBlockState(this.blockPostion.down()).getBlock() == Blocks.DIRT) {
                    Inventory inventory = owner.getInventoryMaidMain();
                    for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                        ItemStack itemstack = inventory.getStackInSlot(i);
                        boolean flag = false;
                        if (!itemstack.isEmpty()) {
                            if (Block.getBlockFromItem(itemstack.getItem()).isIn(BlockTags.SAPLINGS)) {
                                worldIn.setBlockState(this.blockPostion, Block.getBlockFromItem(itemstack.getItem()).getDefaultState(), 3);
                                flag = true;
                                owner.swingArm(Hand.MAIN_HAND);

                            }
                        }

                        if (flag) {
                            worldIn.playSound((PlayerEntity) null, (double) this.blockPostion.getX(), (double) this.blockPostion.getY(), (double) this.blockPostion.getZ(), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            itemstack.shrink(1);
                            owner.giveExperiencePoints(1);
                            if (itemstack.isEmpty()) {
                                inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                            }
                            break;
                        }
                    }

                    if (worldIn.getBlockState(this.blockPostion.up(1)).isIn(BlockTags.LOGS)) {
                        this.field_220426_e = 0;
                        this.blockPostion = this.blockPostion.up();
                        this.breakingTime = 0;
                        owner.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(this.blockPostion));
                        owner.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosWrapper(this.blockPostion), 0.5F, 1));
                    } else {
                        isFinishLumberJack = true;
                    }
                } else {
                    if (worldIn.getBlockState(this.blockPostion.up(1)).isIn(BlockTags.LOGS)) {
                        this.field_220426_e = 0;
                        this.blockPostion = this.blockPostion.up();
                        this.breakingTime = 0;
                        owner.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(this.blockPostion));
                        owner.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosWrapper(this.blockPostion), 0.5F, 1));
                    } else {
                        isFinishLumberJack = true;
                    }
                }

                owner.world.sendBlockBreakProgress(owner.getEntityId(), this.blockPostion, -1);
            }
        }
        ++this.field_220426_e;

    }

    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        entityIn.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
        entityIn.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
        if (this.blockPostion != null) {
            entityIn.world.sendBlockBreakProgress(entityIn.getEntityId(), this.blockPostion, -1);
        }

        this.field_220426_e = 0;
        this.breakingTime = 0;
        entityIn.getNavigator().clearPath();
        Brain<?> brain = entityIn.getBrain();
        brain.updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
    }

    @Override
    protected boolean isTimedOut(long gameTime) {
        return false;
    }
}
