package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Optional;

public class WalkToOwner extends Task<LittleMaidBaseEntity> {
    @Nullable
    private Path field_220488_a;
    @Nullable
    private BlockPos field_220489_b;
    private float field_220490_c;
    private int field_220491_d;

    public WalkToOwner(int p_i50356_1_) {
        super(ImmutableMap.of(MemoryModuleType.PATH, MemoryModuleStatus.VALUE_ABSENT), p_i50356_1_);
    }

    protected boolean shouldExecute(ServerWorld worldIn, LittleMaidBaseEntity owner) {
        Brain<?> brain = owner.getBrain();

        LivingEntity player = owner.getOwner();

        if (player != null && owner.getDistanceSq(player) > 70.0D && !this.hasReachedTarget(owner, player) && this.func_220487_a(owner, player, worldIn.getGameTime())) {
            brain.removeMemory(MemoryModuleType.WALK_TARGET);

            this.field_220489_b = player.getPosition();
            return true;
        } else {
            return false;
        }
    }

    protected boolean shouldContinueExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        if (this.field_220488_a != null && this.field_220489_b != null) {
            PathNavigator pathnavigator = entityIn.getNavigator();
            LivingEntity player = entityIn.getOwner();
            return !pathnavigator.noPath() && player != null && player.isAlive() && !this.hasReachedTarget(entityIn, player);
        } else {
            return false;
        }
    }

    protected void resetTask(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        entityIn.getNavigator().clearPath();
        entityIn.getBrain().removeMemory(MemoryModuleType.PATH);
        this.field_220488_a = null;
    }

    protected void startExecuting(ServerWorld worldIn, LittleMaidBaseEntity entityIn, long gameTimeIn) {
        entityIn.getBrain().setMemory(MemoryModuleType.PATH, this.field_220488_a);
        entityIn.getNavigator().setPath(this.field_220488_a, (double) this.field_220490_c);
        this.field_220491_d = worldIn.getRandom().nextInt(10);
    }

    protected void updateTask(ServerWorld worldIn, LittleMaidBaseEntity owner, long gameTime) {
        --this.field_220491_d;
        if (this.field_220491_d <= 0) {
            Path path = owner.getNavigator().getPath();
            Brain<?> brain = owner.getBrain();
            if (this.field_220488_a != path) {
                this.field_220488_a = path;
                brain.setMemory(MemoryModuleType.PATH, path);
            }

            if (path != null && this.field_220489_b != null) {
                LivingEntity player = owner.getOwner();
                if (player != null && player.getPosition().distanceSq(this.field_220489_b) > 70.0D && this.func_220487_a(owner, player, worldIn.getGameTime())) {
                    this.field_220489_b = player.getPosition();
                    this.startExecuting(worldIn, owner, gameTime);
                }

                if (player != null && player.getDistanceSq(owner) > 200.0D) {
                    this.startTeleport(player, owner);
                }

            }
        }
    }

    private void startTeleport(LivingEntity player, LittleMaidBaseEntity owner) {
        if (!owner.getLeashed() && !owner.isPassenger()) {
            int i = MathHelper.floor(player.posX) - 2;
            int j = MathHelper.floor(player.posZ) - 2;
            int k = MathHelper.floor(player.getBoundingBox().minY);

            for (int l = 0; l <= 4; ++l) {
                for (int i1 = 0; i1 <= 4; ++i1) {
                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.canTeleportToBlock(owner.world, owner, new BlockPos(i + l, k - 1, j + i1))) {
                        owner.setLocationAndAngles((double) ((float) (i + l) + 0.5F), (double) k, (double) ((float) (j + i1) + 0.5F), owner.rotationYaw, owner.rotationPitch);
                        return;
                    }
                }
            }
        }
    }

    private boolean func_220487_a(LittleMaidBaseEntity p_220487_1_, LivingEntity p_220487_2_, long p_220487_3_) {
        BlockPos blockpos = p_220487_2_.getPosition();
        this.field_220488_a = p_220487_1_.getNavigator().getPathToPos(blockpos, 0);
        this.field_220490_c = 0.65F;
        if (!this.hasReachedTarget(p_220487_1_, p_220487_2_)) {
            Brain<?> brain = p_220487_1_.getBrain();
            boolean flag = this.field_220488_a != null && this.field_220488_a.func_224771_h();
            if (flag) {
                brain.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, Optional.empty());
            } else if (!brain.hasMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
                brain.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, p_220487_3_);
            }

            if (this.field_220488_a != null) {
                return true;
            }

            Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards((CreatureEntity) p_220487_1_, 10, 7, new Vec3d(blockpos));
            if (vec3d != null) {
                this.field_220488_a = p_220487_1_.getNavigator().func_225466_a(vec3d.x, vec3d.y, vec3d.z, 0);
                return this.field_220488_a != null;
            }
        }

        return false;
    }

    protected boolean canTeleportToBlock(World world, LivingEntity entity, BlockPos pos) {
        BlockState blockstate = world.getBlockState(pos);
        return blockstate.canEntitySpawn(world, pos, entity.getType()) && world.isAirBlock(pos.up()) && world.isAirBlock(pos.up(2));
    }

    private boolean hasReachedTarget(LittleMaidBaseEntity p_220486_1_, LivingEntity p_220486_2_) {
        return p_220486_2_.getPosition().manhattanDistance(new BlockPos(p_220486_1_)) <= p_220486_1_.getDistance(p_220486_2_);
    }
}