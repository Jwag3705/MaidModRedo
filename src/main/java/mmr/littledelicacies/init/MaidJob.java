package mmr.littledelicacies.init;

import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.maidjob.EffectCasterMaidJob;
import mmr.littledelicacies.maidjob.FlameCasterMaidJob;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class MaidJob {
    public static final DefaultedRegistry<MaidJob> MAID_JOB_REGISTRY = registerDefaulted("maid_job", "normal", () -> {
        return MaidJob.NORMAL;
    });
    public static final MaidJob WILD = new MaidJob("wild", ItemStack.EMPTY).setSchedule(LittleSchedules.FREEDOM);
    public static final MaidJob NORMAL = new MaidJob("normal", ItemStack.EMPTY).setSchedule(LittleSchedules.FREEDOM);
    public static final MaidJob BUTCHER = new MaidJob("butcher", new ItemStack(LittleItems.BUTCHER_KNIFE));
    public static final MaidJob FARMER = new MaidJob("farmer", new ItemStack(Items.DIAMOND_HOE));
    public static final MaidJob FISHER = new MaidJob("fisher", new ItemStack(Items.FISHING_ROD));
    public static final MaidJob CHEF = new MaidJob("chef", new ItemStack(Items.COAL));
    public static final MaidJob BARISTA = new MaidJob("barista", new ItemStack(Items.GLASS_BOTTLE));
    public static final MaidJob LUMBERJACK = new MaidJob("lumberjack", new ItemStack(Items.DIAMOND_AXE));
    public static final MaidJob MINER = new MaidJob("miner", new ItemStack(Items.DIAMOND_PICKAXE));
    public static final MaidJob HEALER = new MaidJob("healer", new ItemStack(Items.GOLDEN_APPLE));
    public static final MaidJob TORCHER = new MaidJob("torcher", new ItemStack(Items.TORCH));
    public static final MaidJob RIPPER = new MaidJob("ripper", new ItemStack(Items.SHEARS));

    public static final MaidJob FENCER = new MaidJob("fencer", new ItemStack(Items.DIAMOND_SWORD)).setSubActivity(LittleActivitys.ATTACK).setLockJob();
    public static final MaidJob GUARD = new MaidJob("guard", new ItemStack(Items.DIAMOND_SWORD)).setSubRequireItem(new ItemStack(Items.SHIELD)).setSubActivity(LittleActivitys.ATTACK).setLockJob().setNeedLevel(10);
    public static final MaidJob DUAL_BLADER = new MaidJob("dual_blader", new ItemStack(Items.DIAMOND_SWORD)).setSubRequireItem(new ItemStack(Items.DIAMOND_SWORD)).setSubActivity(LittleActivitys.DUAL_BLADER).setLockJob().setNeedLevel(5);
    public static final MaidJob SHIELDER = new MaidJob("shielder", new ItemStack(Items.SHIELD)).setSubRequireItem(new ItemStack(Items.SHIELD)).setSubActivity(LittleActivitys.SHIELDER).setLockJob().setNeedLevel(15);
    public static final MaidJob ARCHER = new MaidJob("archer", new ItemStack(Items.BOW)).setSubActivity(LittleActivitys.SHOT).setLockJob().setNeedLevel(10);
    public static final MaidJob FLAME_CASTER = new FlameCasterMaidJob("flame_caster", new ItemStack(LittleItems.MAGE_STUFF)).setLockJob().setSubActivity(LittleActivitys.SIMPLE_CASTER);
    public static final MaidJob POWER_CASTER = new EffectCasterMaidJob("power_caster", new ItemStack(LittleItems.MAGE_STUFF), Effects.STRENGTH).setLockJob().setSubActivity(LittleActivitys.SUPORT_CASTER);


    private final String name;
    private Activity activity;
    private final ItemStack requireItem;
    private ItemStack subRequireItem;
    private Schedule schedule = LittleSchedules.LITTLEMAID_WORK;
    private boolean lockJob;
    private int needLevel;

    public MaidJob(String nameIn, ItemStack p_i50179_3_) {
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

    public ItemStack getSubRequireItem() {
        return this.subRequireItem;
    }

    public MaidJob setSubRequireItem(ItemStack subRequireItem) {
        this.subRequireItem = subRequireItem;
        return this;
    }

    //used render
    public ItemStack getRequireItem() {
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
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "wild"), WILD);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "normal"), NORMAL);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "butcher"), BUTCHER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "farmer"), FARMER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "fisher"), FISHER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "barista"), BARISTA);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "chef"), CHEF);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "lumberjack"), LUMBERJACK);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "miner"), MINER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "healer"), HEALER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "torcher"), TORCHER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "ripper"), RIPPER);

        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "fencer"), FENCER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "guard"), GUARD);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "dual_blader"), DUAL_BLADER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "shielder"), SHIELDER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "archer"), ARCHER);

        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "flame_caster"), FLAME_CASTER);
        MAID_JOB_REGISTRY.register(new ResourceLocation(LittleDelicacies.MODID, "power_caster"), POWER_CASTER);
    }

    private static <T> DefaultedRegistry<T> registerDefaulted(String p_222933_0_, String p_222933_1_, Supplier<T> p_222933_2_) {
        return register(p_222933_0_, new DefaultedRegistry<>(p_222933_1_), p_222933_2_);
    }

    private static <T, R extends MutableRegistry<T>> R register(String p_222939_0_, R p_222939_1_, Supplier<T> p_222939_2_) {
        ResourceLocation resourcelocation = new ResourceLocation(p_222939_0_);
        return (R) (Registry.REGISTRY.register(resourcelocation, p_222939_1_));
    }
}
