package mmr.maidmodredo.entity.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import mmr.maidmodredo.entity.LittleMaidEntity;
import mmr.maidmodredo.init.LittleSchedules;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.village.PointOfInterestType;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class MaidJob {
    public static final Registry<MaidJob> MAID_JOB_REGISTRY = registerSimple("maid_job", () -> {
        return MaidJob.ESCORT;
    });
    public static final MaidJob ESCORT = new MaidJob("escort", PointOfInterestType.UNEMPLOYED, (item) -> {
        return item == ItemStack.EMPTY;
    });
    public static final MaidJob WILD = new MaidJob("wild", PointOfInterestType.UNEMPLOYED, (item) -> {
        return item == ItemStack.EMPTY;
    }).setSchedule(LittleSchedules.WILDMAID);
    public static final MaidJob FENCER = new MaidJob("fencer", PointOfInterestType.UNEMPLOYED, (item) -> {
        return item.getItem() instanceof SwordItem;
    });

    private final String name;
    private final PointOfInterestType pointOfInterest;
    private final Predicate<ItemStack> field_221168_r;
    private Schedule schedule;
    private Activity activity;
    private ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidEntity>>> tasks;

    public MaidJob(String nameIn, PointOfInterestType pointOfInterestIn, Predicate<ItemStack> p_i50179_3_) {
        this.name = nameIn;
        this.pointOfInterest = pointOfInterestIn;
        this.field_221168_r = p_i50179_3_;
    }

    public PointOfInterestType getPointOfInterest() {
        return this.pointOfInterest;
    }

    public Predicate<ItemStack> getRequireItem() {
        return this.field_221168_r;
    }

    public String toString() {
        return this.name;
    }

    public MaidJob setSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public MaidJob setActivity(Activity activity, ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidEntity>>> tasks) {
        this.activity = activity;
        this.tasks = tasks;
        return this;
    }

    public Activity getActivity() {
        return activity;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public ImmutableList<Pair<Integer, ? extends Task<? super LittleMaidEntity>>> getTasks() {
        return tasks;
    }

    private static <T> Registry<T> registerSimple(String p_222935_0_, Supplier<T> p_222935_1_) {
        return register(p_222935_0_, new SimpleRegistry<>(), p_222935_1_);
    }

    private static <T, R extends MutableRegistry<T>> R register(String p_222939_0_, R p_222939_1_, Supplier<T> p_222939_2_) {
        ResourceLocation resourcelocation = new ResourceLocation(p_222939_0_);
        return (R) (Registry.REGISTRY.register(resourcelocation, p_222939_1_));
    }
}
