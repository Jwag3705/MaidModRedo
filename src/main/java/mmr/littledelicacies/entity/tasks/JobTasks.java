package mmr.littledelicacies.entity.tasks;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.LittleEntitys;
import mmr.littledelicacies.init.MaidJob;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;

public class JobTasks {
    public static ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidBaseEntity>>> work(MaidJob p_220639_0_, float p_220639_1_) {
        if (p_220639_0_ == MaidJob.BUTCHER) {
            return workButcher(p_220639_0_, p_220639_1_);
        } else if (p_220639_0_ == MaidJob.FARMER) {
            return workFarmer(p_220639_0_, p_220639_1_);
        } else if (p_220639_0_ == MaidJob.FISHER) {
            return workFisher(p_220639_0_, p_220639_1_);
        } else if (p_220639_0_ == MaidJob.CHEF) {
            return workChef(p_220639_0_, p_220639_1_);
        } else if (p_220639_0_ == MaidJob.BARISTA) {
            return workBarista(p_220639_0_, p_220639_1_);
        } else if (p_220639_0_ == MaidJob.LUMBERJACK) {
            return workLumberJack(p_220639_0_, p_220639_1_);
        } else if (p_220639_0_ == MaidJob.MINER) {
            return workMiner(p_220639_0_, p_220639_1_);
        } else if (p_220639_0_ == MaidJob.RIPPER) {
            return workRipper(p_220639_1_);
        } else {
            return ImmutableList.of(func_220646_b(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 1, 10), 5), Pair.of(new MaidWalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, 0.4F, 1, 6, MemoryModuleType.JOB_SITE), 5)))), Pair.of(10, new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)), Pair.of(2, new MaidStayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 19200, 25600)), Pair.of(99, new UpdateActivityTask()));
        }
    }

    private static ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidBaseEntity>>> workButcher(MaidJob p_220639_0_, float p_220639_1_) {
        return ImmutableList.of(Pair.of(0, new ButcherTask()), func_220646_b(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 1, 10), 5), Pair.of(new MaidWalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, 0.4F, 1, 6, MemoryModuleType.JOB_SITE), 5)))), Pair.of(2, new MaidStayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 19200, 25600)), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidBaseEntity>>> workFarmer(MaidJob p_220639_0_, float p_220639_1_) {
        return ImmutableList.of(func_220646_b(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 1, 10), 5), Pair.of(new MaidWalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, 0.4F, 1, 6, MemoryModuleType.JOB_SITE), 5), Pair.of(new MaidFarmTask(), p_220639_0_ == MaidJob.FARMER ? 1 : 5)))), Pair.of(10, new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)), Pair.of(2, new MaidStayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 19200, 25600)), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidBaseEntity>>> workFisher(MaidJob p_220639_0_, float p_220639_1_) {
        return ImmutableList.of(Pair.of(p_220639_0_ == MaidJob.FISHER ? 0 : 5, new FishingTask()), func_220646_b(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 1, 10), 5), Pair.of(new MaidWalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, 0.4F, 1, 6, MemoryModuleType.JOB_SITE), 5)))), Pair.of(2, new MaidStayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 100, 1200)), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidBaseEntity>>> workChef(MaidJob p_220639_0_, float p_220639_1_) {
        return ImmutableList.of(Pair.of(p_220639_0_ == MaidJob.CHEF ? 0 : 5, new ChefTask()), func_220646_b(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 1, 10), 5), Pair.of(new MaidWalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, 0.4F, 1, 6, MemoryModuleType.JOB_SITE), 5)))), Pair.of(2, new MaidStayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 100, 600)), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidBaseEntity>>> workBarista(MaidJob p_220639_0_, float p_220639_1_) {
        return ImmutableList.of(Pair.of(p_220639_0_ == MaidJob.BARISTA ? 0 : 5, new BaristaTask()), func_220646_b(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 1, 10), 5), Pair.of(new MaidWalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, 0.4F, 1, 6, MemoryModuleType.JOB_SITE), 5)))), Pair.of(2, new MaidStayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 100, 600)), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidBaseEntity>>> workLumberJack(MaidJob p_220639_0_, float p_220639_1_) {
        return ImmutableList.of(Pair.of(p_220639_0_ == MaidJob.LUMBERJACK ? 0 : 5, new StartCutWood()), func_220646_b(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 1, 10), 5), Pair.of(new MaidWalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, 0.4F, 1, 6, MemoryModuleType.JOB_SITE), 5)))), Pair.of(2, new MaidStayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 19200, 25600)), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidBaseEntity>>> workMiner(MaidJob p_220639_0_, float p_220639_1_) {
        return ImmutableList.of(Pair.of(p_220639_0_ == MaidJob.MINER ? 0 : 5, new BreakOreTask()), func_220646_b(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 1, 10), 5), Pair.of(new MaidWalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, 0.4F, 1, 6, MemoryModuleType.JOB_SITE), 5)))), Pair.of(2, new MaidStayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 19200, 25600)), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidBaseEntity>>> workRipper(float p_220639_1_) {
        return ImmutableList.of(Pair.of(0, new RipperTask()), func_220646_b(), Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 1, 10), 5), Pair.of(new MaidWalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, 0.4F, 1, 6, MemoryModuleType.JOB_SITE), 5)))), Pair.of(2, new MaidStayNearPointTask(MemoryModuleType.JOB_SITE, p_220639_1_, 9, 19200, 25600)), Pair.of(99, new UpdateActivityTask()));
    }

    private static Pair<Integer, Task<LivingEntity>> func_220643_a() {
        return Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new LookAtEntityTask(LittleEntitys.WANDERMAID, 8.0F), 2), Pair.of(new LookAtEntityTask(LittleEntitys.LITTLEMAID, 8.0F), 2), Pair.of(new LookAtEntityTask(EntityType.CAT, 8.0F), 8), Pair.of(new LookAtEntityTask(EntityType.VILLAGER, 8.0F), 2), Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0F), 2), Pair.of(new LookAtEntityTask(EntityClassification.CREATURE, 8.0F), 1), Pair.of(new LookAtEntityTask(EntityClassification.WATER_CREATURE, 8.0F), 1), Pair.of(new LookAtEntityTask(EntityClassification.MONSTER, 8.0F), 1), Pair.of(new DummyTask(30, 60), 2))));
    }

    private static Pair<Integer, Task<LivingEntity>> func_220646_b() {
        return Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new LookAtEntityTask(LittleEntitys.WANDERMAID, 8.0F), 2), Pair.of(new LookAtEntityTask(LittleEntitys.LITTLEMAID, 8.0F), 2), Pair.of(new LookAtEntityTask(EntityType.VILLAGER, 8.0F), 2), Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0F), 2), Pair.of(new DummyTask(30, 60), 8))));
    }
}
