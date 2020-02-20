package mmr.maidmodredo.entity.tasks;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;

public class WalkToRestPoint extends Task<LivingEntity> {
    private final float field_220524_a;
    private final Long2LongMap field_225455_b = new Long2LongOpenHashMap();
    private int field_225456_c;
    private long field_220525_b;

    public WalkToRestPoint(float p_i50353_1_) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.MEETING_POINT, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220524_a = p_i50353_1_;
    }

    protected boolean shouldExecute(ServerWorld worldIn, LivingEntity owner) {
        if (worldIn.getGameTime() - this.field_220525_b < 20L) {
            return false;
        } else {
            CreatureEntity creatureentity = (CreatureEntity) owner;

            Optional<GlobalPos> optional = owner.getBrain().getMemory(MemoryModuleType.MEETING_POINT);
            return optional.isPresent() && !(optional.get().getPos().distanceSq(new BlockPos(creatureentity)) <= 16.0D);
        }
    }

    protected void startExecuting(ServerWorld worldIn, LivingEntity entityIn, long gameTimeIn) {
        this.field_225456_c = 0;
        this.field_220525_b = worldIn.getGameTime() + (long) worldIn.getRandom().nextInt(20);
        CreatureEntity creatureentity = (CreatureEntity) entityIn;

        Optional<GlobalPos> optional = creatureentity.getBrain().getMemory(MemoryModuleType.MEETING_POINT);
        if (optional.isPresent()) {
            Path path = creatureentity.getNavigator().getPathToPos(optional.get().getPos(), 0);
            if (path != null && path.func_224771_h()) {
                entityIn.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(optional.get().getPos(), this.field_220524_a, 5));
            }
        } else if (this.field_225456_c < 5) {
            this.field_225455_b.long2LongEntrySet().removeIf((p_225454_1_) -> {
                return p_225454_1_.getLongValue() < this.field_220525_b;
            });
        }

    }
}