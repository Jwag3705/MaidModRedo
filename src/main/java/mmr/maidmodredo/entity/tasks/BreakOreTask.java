package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.init.MaidJob;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class BreakOreTask extends Task<LittleMaidBaseEntity> {
    private int field_220426_e;
    private boolean isMineOre;
    private boolean isFinishMining;
    private boolean canMining;
    protected int breakingTime;
    protected int previousBreakProgress = -1;
    @Nullable
    private BlockPos blockPostion;
    private final List<BlockPos> field_223518_f = Lists.newArrayList();

    public BreakOreTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        if (owner.getMaidData().getJob() != MaidJob.MINER) {
            return false;
        } else if (!(owner.getHeldItem(Hand.MAIN_HAND).getItem() instanceof PickaxeItem)) {
            return false;
        } else {
            this.isMineOre = false;
            this.isFinishMining = false;
            this.canMining = false;
            Inventory inventory = owner.getInventoryMaidMain();
            int i = inventory.getSizeInventory();

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (itemstack.isEmpty()) {
                    this.canMining = true;
                    break;
                }
            }

            BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable(owner.getPosX(), owner.getPosY(), owner.getPosZ());
            this.field_223518_f.clear();

            for (int i1 = 0; i1 <= 10; ++i1) {

                double d0 = owner.getPosX() + (owner.getRNG().nextDouble() - 0.5D) * 6.0D;
                double d1 = owner.getPosY() + (double) (owner.getRNG().nextInt(12) - 6);
                double d2 = owner.getPosZ() + (owner.getRNG().nextDouble() - 0.5D) * 6.0D;
                blockpos$mutableblockpos.setPos(d0, d1, (double) d2);
                if (this.func_223516_a(blockpos$mutableblockpos, worldIn)) {
                    this.field_223518_f.add(new BlockPos(blockpos$mutableblockpos));
                }

            }

            this.blockPostion = this.func_223517_a(worldIn);

            return this.canMining && this.blockPostion != null;
        }
    }


    @Nullable
    private BlockPos func_223517_a(ServerWorld p_223517_1_) {
        return this.field_223518_f.isEmpty() ? null : this.field_223518_f.get(p_223517_1_.getRandom().nextInt(this.field_223518_f.size()));
    }


    private boolean func_223516_a(BlockPos p_223516_1_, ServerWorld p_223516_2_) {
        BlockState blockstate = p_223516_2_.getBlockState(p_223516_1_);
        Block block = blockstate.getBlock();
        return block instanceof OreBlock;
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        return !this.isFinishMining && !this.isMineOre || entityIn.getHeldItem(Hand.MAIN_HAND).getItem() instanceof PickaxeItem && this.isMineOre && this.blockPostion.withinDistance(entityIn.getPositionVec(), 16.0D) && !worldIn.getBlockState(this.blockPostion).isAir();
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
            if (!worldIn.getBlockState(this.blockPostion).isAir()) {
                owner.swingArm(Hand.MAIN_HAND);
                isMineOre = true;
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
                owner.giveExperiencePoints(3 + owner.getRNG().nextInt(4));
                isMineOre = false;
                isFinishMining = true;

                owner.getHeldItem(Hand.MAIN_HAND).damageItem(1, owner, (p_220000_1_) -> {
                    p_220000_1_.sendBreakAnimation(Hand.MAIN_HAND);
                });

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
