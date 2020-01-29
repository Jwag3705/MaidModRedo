package mmr.maidmodredo.entity.sensor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mmr.maidmodredo.entity.LittleMaidEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;
import java.util.Set;

public class OwnerHurtByTargetSensor extends Sensor<LittleMaidEntity> {
    private static final ImmutableMap<EntityType<?>, Float> field_220991_b = ImmutableMap.<EntityType<?>, Float>builder().put(EntityType.DROWNED, 8.0F).put(EntityType.EVOKER, 12.0F).put(EntityType.HUSK, 8.0F).put(EntityType.ILLUSIONER, 12.0F).put(EntityType.PILLAGER, 15.0F).put(EntityType.RAVAGER, 12.0F).put(EntityType.VEX, 8.0F).put(EntityType.VINDICATOR, 10.0F).put(EntityType.ZOMBIE, 8.0F).put(EntityType.ZOMBIE_VILLAGER, 8.0F).build();

    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_HOSTILE);
    }

    protected void update(ServerWorld p_212872_1_, LittleMaidEntity p_212872_2_) {
        p_212872_2_.getBrain().setMemory(MemoryModuleType.NEAREST_HOSTILE, Optional.ofNullable(this.func_220989_a(p_212872_2_)));
    }

    private LivingEntity func_220989_a(LittleMaidEntity p_220989_1_) {
        if (p_220989_1_.isTamed()) {
            LivingEntity livingentity = p_220989_1_.getOwner();

            if (livingentity != null) {
                return livingentity.getRevengeTarget();
            }
        }
        return null;
    }
}