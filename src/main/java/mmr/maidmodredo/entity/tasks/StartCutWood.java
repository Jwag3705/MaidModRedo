package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.init.LittleActivitys;
import mmr.maidmodredo.init.MaidJob;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class StartCutWood extends Task<LittleMaidBaseEntity> {
    private int field_220426_e;
    private boolean canCutting;
    @Nullable
    private BlockPos blockPostion;
    private final List<BlockPos> field_223518_f = Lists.newArrayList();

    public StartCutWood() {
        super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (owner.getMaidData().getJob() != MaidJob.LUMBERJACK) {
            return false;
        } else if (!(owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof AxeItem)) {
            return false;
        } else {
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

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        if (this.blockPostion != null) {
            entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(this.blockPostion));
            entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosWrapper(this.blockPostion), 0.5F, 1));
        }
        entityIn.getBrain().switchTo(LittleActivitys.LUMBERJACK);
    }


    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
    }
}
