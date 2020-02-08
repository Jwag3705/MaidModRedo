package mmr.maidmodredo.init;

import mmr.maidmodredo.MaidModRedo;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class MaidJob {
    public static final DefaultedRegistry<MaidJob> MAID_JOB_REGISTRY = registerDefaulted("maid_job", "normal", () -> {
        return MaidJob.NORMAL;
    });
    public static final MaidJob WILD = new MaidJob("wild", (item) -> {
        return item == ItemStack.EMPTY;
    }).setSchedule(LittleSchedules.FREEDOM);
    public static final MaidJob NORMAL = new MaidJob("normal", (item) -> {
        return item == ItemStack.EMPTY;
    }).setSchedule(LittleSchedules.FREEDOM);
    public static final MaidJob FARMER = new MaidJob("farmer", (item) -> {
        return item.getItem() instanceof HoeItem;
    });
    public static final MaidJob FISHER = new MaidJob("fisher", (item) -> {
        return item.getItem() instanceof FishingRodItem;
    });
    public static final MaidJob CHEF = new MaidJob("chef", (item) -> {
        return item.getItem() == Items.COAL;
    });
    public static final MaidJob LUMBERJACK = new MaidJob("lumberjack", (item) -> {
        return item.getItem() instanceof AxeItem;
    });
    public static final MaidJob TORCHER = new MaidJob("torcher", (item) -> {
        return item.getItem() == Item.getItemFromBlock(Blocks.TORCH);
    });
    public static final MaidJob RIPPER = new MaidJob("ripper", (item) -> {
        return item.getItem() instanceof ShearsItem;
    });

    public static final MaidJob FENCER = new MaidJob("fencer", (item) -> {
        return item.getItem() instanceof SwordItem;
    }).setSubActivity(LittleActivitys.ATTACK).setLockJob();
    public static final MaidJob GUARD = new MaidJob("guard", (item) -> {
        return item.getItem() instanceof SwordItem;
    }).setSubRequireItem((item) -> {
        return item.getItem() instanceof ShieldItem;
    }).setSubActivity(LittleActivitys.ATTACK).setLockJob().setNeedLevel(10);
    public static final MaidJob ARCHER = new MaidJob("archer", (item) -> {
        return item.getItem() instanceof ShootableItem;
    }).setSubActivity(LittleActivitys.SHOT).setLockJob().setNeedLevel(10);

    private final String name;
    private Activity activity;
    private final Predicate<ItemStack> requireItem;
    private Predicate<ItemStack> subRequireItem;
    private Schedule schedule = LittleSchedules.LITTLEMAID_WORK;
    private boolean lockJob;
    private int needLevel;

    public MaidJob(String nameIn, Predicate<ItemStack> p_i50179_3_) {
        this.name = nameIn;
        this.requireItem = p_i50179_3_;
        this.needLevel = 0;
    }

    public MaidJob setLockJob() {
        this.lockJob = true;
        return this;
    }

    public boolean isLockJob() {
        return lockJob;
    }

    public MaidJob setNeedLevel(int needLevel) {
        this.needLevel = needLevel;
        return this;
    }

    public int getNeedLevel() {
        return needLevel;
    }

    public Activity getSubActivity() {
        return this.activity;
    }

    /*
     * add sub activity(like Combat Job)
     */
    public MaidJob setSubActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public Predicate<ItemStack> getSubRequireItem() {
        return this.subRequireItem;
    }

    public MaidJob setSubRequireItem(Predicate<ItemStack> subRequireItem) {
        this.subRequireItem = subRequireItem;
        return this;
    }

    public Predicate<ItemStack> getRequireItem() {
        return this.requireItem;
    }

    public String toString() {
        return this.name;
    }

    /*
     * add default jobs schedule
     */
    public MaidJob setSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    /*
     * manage the maid job here
     * Change the schedule according to the job (or status (?))
     */
    public static void init() {
        MAID_JOB_REGISTRY.register(new ResourceLocation(MaidModRedo.MODID, "wild"), WILD);
        MAID_JOB_REGISTRY.register(new ResourceLocation(MaidModRedo.MODID, "normal"), NORMAL);
        MAID_JOB_REGISTRY.register(new ResourceLocation(MaidModRedo.MODID, "farmer"), FARMER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(MaidModRedo.MODID, "fisher"), FISHER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(MaidModRedo.MODID, "chef"), CHEF);
        MAID_JOB_REGISTRY.register(new ResourceLocation(MaidModRedo.MODID, "lumberjack"), LUMBERJACK);
        MAID_JOB_REGISTRY.register(new ResourceLocation(MaidModRedo.MODID, "torcher"), TORCHER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(MaidModRedo.MODID, "ripper"), RIPPER);

        MAID_JOB_REGISTRY.register(new ResourceLocation(MaidModRedo.MODID, "fencer"), FENCER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(MaidModRedo.MODID, "guard"), GUARD);
        MAID_JOB_REGISTRY.register(new ResourceLocation(MaidModRedo.MODID, "archer"), ARCHER);
    }

    private static <T> DefaultedRegistry<T> registerDefaulted(String p_222933_0_, String p_222933_1_, Supplier<T> p_222933_2_) {
        return register(p_222933_0_, new DefaultedRegistry<>(p_222933_1_), p_222933_2_);
    }

    private static <T, R extends MutableRegistry<T>> R register(String p_222939_0_, R p_222939_1_, Supplier<T> p_222939_2_) {
        ResourceLocation resourcelocation = new ResourceLocation(p_222939_0_);
        return (R) (Registry.REGISTRY.register(resourcelocation, p_222939_1_));
    }
}
