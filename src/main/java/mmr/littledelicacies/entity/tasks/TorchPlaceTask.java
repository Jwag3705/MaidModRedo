package mmr.littledelicacies.entity.tasks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.MaidJob;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class TorchPlaceTask extends Task<LittleMaidBaseEntity> {
    @Nullable
    private BlockPos blockPostion;
    private final List<BlockPos> field_223518_f = Lists.newArrayList();
    private boolean isFinishPlacing;

    public TorchPlaceTask() {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED));
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (owner.getMaidData().getJob() != MaidJob.TORCHER) {
            return false;
        } else if (!(owner.getHeldItem(Hand.MAIN_HAND).getItem() == Items.TORCH)) {
            return false;
        } else {
            this.isFinishPlacing = false;
            /*for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (itemstack.isEmpty()) {
                    this.canPlacing = true;
                    break;
                }
            }*/

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
            return this.blockPostion != null;
        }
    }


    @Nullable
    private BlockPos func_223517_a(ServerWorld p_223517_1_) {
        return this.field_223518_f.isEmpty() ? null : this.field_223518_f.get(p_223517_1_.getRandom().nextInt(this.field_223518_f.size()));
    }


    private boolean func_223516_a(BlockPos p_223516_1_, ServerWorld p_223516_2_) {
        BlockState blockstate = p_223516_2_.getBlockState(p_223516_1_);
        return p_223516_2_.getLight(p_223516_1_) < 9 && blockstate.isAir() && Blocks.TORCH.getDefaultState().isValidPosition(p_223516_2_, p_223516_1_);
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        if (this.blockPostion != null) {
            entityIn.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(this.blockPostion));
            entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosWrapper(this.blockPostion), 0.5F, 1));
        }
    }


    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        return !this.isFinishPlacing;
    }

    @Override
    protected void updateTask(ServerWorld worldIn, LittleMaidBaseEntity owner, long gameTime) {
        super.updateTask(worldIn, owner, gameTime);
        if (this.blockPostion != null) {
            if (this.blockPostion.withinDistance(owner.getPositionVec(), 6.0D)) {
                worldIn.setBlockState(this.blockPostion, Block.getBlockFromItem(owner.getHeldItem(Hand.MAIN_HAND).getItem()).getDefaultState(), 3);
                owner.getHeldItem(Hand.MAIN_HAND).shrink(1);
                owner.swingArm(Hand.MAIN_HAND);
                owner.giveExperiencePoints(1);
                isFinishPlacing = true;
            }
        }
    }

    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {

        entityIn.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
        entityIn.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);


    }
}
