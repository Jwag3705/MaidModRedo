package mmr.maidmodredo.entity.data;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import mmr.maidmodredo.init.MaidJob;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MaidData {
    private static final int[] field_221136_a = new int[]{0, 10, 70, 150, 250};
    private final MaidJob job;
    private final int level;

    public MaidData(MaidJob job, int level) {
        this.job = job;
        this.level = Math.max(1, level);
    }

    public MaidData(Dynamic<?> p_i50181_1_) {
        this(MaidJob.MAID_JOB_REGISTRY.getOrDefault(ResourceLocation.tryCreate(p_i50181_1_.get("job").asString(""))), p_i50181_1_.get("level").asInt(1));
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

    @OnlyIn(Dist.CLIENT)
    public static int func_221133_b(int p_221133_0_) {
        return func_221128_d(p_221133_0_) ? field_221136_a[p_221133_0_ - 1] : 0;
    }

    public static int func_221127_c(int p_221127_0_) {
        return func_221128_d(p_221127_0_) ? field_221136_a[p_221127_0_] : 0;
    }

    public static boolean func_221128_d(int p_221128_0_) {
        return p_221128_0_ >= 1 && p_221128_0_ < 5;
    }
}