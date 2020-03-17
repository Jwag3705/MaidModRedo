package mmr.littledelicacies.entity.data;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import mmr.littledelicacies.init.MaidJob;
import net.minecraft.util.ResourceLocation;

public class MaidData {
    private final MaidJob job;
    private final int level;

    public MaidData(MaidJob job, int level) {
        this.job = job;
        this.level = Math.max(0, level);
    }

    public MaidData(Dynamic<?> p_i50181_1_) {
        this(MaidJob.MAID_JOB_REGISTRY.getOrDefault(ResourceLocation.tryCreate(p_i50181_1_.get("job").asString(""))), p_i50181_1_.get("level").asInt(0));
    }

    public MaidJob getJob() {
        return this.job;
    }

    public int getLevel() {
        return this.level;
    }

    public MaidData withJob(MaidJob jobIn) {
        return new MaidData(jobIn, this.level);
    }

    public MaidData withLevel(int levelIn) {
        return new MaidData(this.job, levelIn);
    }

    public <T> T serialize(DynamicOps<T> p_221131_1_) {
        return p_221131_1_.createMap(ImmutableMap.of(p_221131_1_.createString("job"), p_221131_1_.createString(MaidJob.MAID_JOB_REGISTRY.getKey(this.job).toString()), p_221131_1_.createString("level"), p_221131_1_.createInt(this.level)));
    }
}