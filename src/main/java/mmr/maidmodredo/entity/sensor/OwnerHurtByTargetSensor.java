package mmr.maidmodredo.entity.sensor;

import com.google.common.collect.ImmutableSet;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;
import java.util.Set;

public class OwnerHurtByTargetSensor extends Sensor<LittleMaidBaseEntity> {
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_HOSTILE);
    }

    protected void update(ServerWorld p_212872_1_, LittleMaidBaseEntity p_212872_2_) {
        p_212872_2_.getBrain().setMemory(MemoryModuleType.NEAREST_HOSTILE, Optional.ofNullable(this.func_220989_a(p_212872_2_)));
    }

    private LivingEntity func_220989_a(LittleMaidBaseEntity p_220989_1_) {
        if (p_220989_1_.isTamed()) {
            LivingEntity livingentity = p_220989_1_.getOwner();

            if (livingentity != null) {
                return livingentity.getRevengeTarget();
            }
        }
        return null;
    }
}