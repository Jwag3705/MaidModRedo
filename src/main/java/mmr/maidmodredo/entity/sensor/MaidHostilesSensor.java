package mmr.maidmodredo.entity.sensor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MaidHostilesSensor extends Sensor<LittleMaidBaseEntity> {
    private static final ImmutableMap<EntityType<?>, Float> field_220991_b = ImmutableMap.<EntityType<?>, Float>builder().put(EntityType.DROWNED, 8.0F).put(EntityType.EVOKER, 12.0F).put(EntityType.HUSK, 8.0F).put(EntityType.ILLUSIONER, 12.0F).put(EntityType.PILLAGER, 15.0F).put(EntityType.RAVAGER, 12.0F).put(EntityType.VEX, 8.0F).put(EntityType.VINDICATOR, 10.0F).put(EntityType.ZOMBIE, 8.0F).put(EntityType.ZOMBIE_VILLAGER, 8.0F).build();

    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_HOSTILE);
    }

    protected void update(ServerWorld p_212872_1_, LittleMaidBaseEntity p_212872_2_) {
        p_212872_2_.getBrain().setMemory(MemoryModuleType.NEAREST_HOSTILE, this.func_220989_a(p_212872_2_));
    }

    private Optional<LivingEntity> func_220989_a(LittleMaidBaseEntity p_220989_1_) {
        if (p_220989_1_.isTamed()) {
            return this.func_220990_b(p_220989_1_).flatMap((p_220984_2_) -> {
                return p_220984_2_.stream().filter((entity) -> {
                    return entity instanceof IMob;
                }).filter((p_220985_2_) -> {
                    return this.attackMobDistance(p_220989_1_, p_220985_2_);
                }).min((p_220986_2_, p_220986_3_) -> {
                    return this.func_220983_a(p_220989_1_, p_220986_2_, p_220986_3_);
                });
            });
        } else {
            return this.func_220990_b(p_220989_1_).flatMap((p_220984_2_) -> {
                return p_220984_2_.stream().filter(this::func_220988_c).filter((p_220985_2_) -> {
                    return this.func_220987_a(p_220989_1_, p_220985_2_);
                }).min((p_220986_2_, p_220986_3_) -> {
                    return this.func_220983_a(p_220989_1_, p_220986_2_, p_220986_3_);
                });
            });
        }
    }

    private Optional<List<LivingEntity>> func_220990_b(LittleMaidBaseEntity p_220990_1_) {
        return p_220990_1_.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS);
    }

    private int func_220983_a(LittleMaidBaseEntity p_220983_1_, LivingEntity p_220983_2_, LivingEntity p_220983_3_) {
        return MathHelper.floor(p_220983_2_.getDistanceSq(p_220983_1_) - p_220983_3_.getDistanceSq(p_220983_1_));
    }

    private boolean func_220987_a(LivingEntity p_220987_1_, LivingEntity p_220987_2_) {
        float f = field_220991_b.get(p_220987_2_.getType());
        return p_220987_2_.getDistanceSq(p_220987_1_) <= (double)(f * f);
    }

    protected double getTargetDistance(LittleMaidBaseEntity entity) {
        IAttributeInstance iattributeinstance = entity.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getValue();
    }

    private boolean attackMobDistance(LittleMaidBaseEntity p_220987_1_, LivingEntity p_220987_2_) {
        double f = getTargetDistance(p_220987_1_);
        return p_220987_2_.getDistanceSq(p_220987_1_) <= (double) (f * f);
    }

    private boolean func_220988_c(LivingEntity p_220988_1_) {
        return field_220991_b.containsKey(p_220988_1_.getType());
    }
}