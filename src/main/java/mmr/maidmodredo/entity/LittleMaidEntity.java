package mmr.maidmodredo.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.util.Pair;
import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.api.IMaidAnimation;
import mmr.maidmodredo.api.MaidAnimation;
import mmr.maidmodredo.client.maidmodel.*;
import mmr.maidmodredo.entity.data.MaidData;
import mmr.maidmodredo.entity.tasks.MaidTasks;
import mmr.maidmodredo.init.*;
import mmr.maidmodredo.inventory.InventoryMaidEquipment;
import mmr.maidmodredo.inventory.InventoryMaidMain;
import mmr.maidmodredo.inventory.MaidInventoryContainer;
import mmr.maidmodredo.network.MaidPacketHandler;
import mmr.maidmodredo.utils.Counter;
import mmr.maidmodredo.utils.EntityCaps;
import mmr.maidmodredo.utils.ModelManager;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class LittleMaidEntity extends TameableEntity implements IModelCaps, IModelEntity, IMaidAnimation, ICrossbowUser {
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.MEETING_POINT, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.INTERACTABLE_DOORS, MemoryModuleType.field_225462_q, MemoryModuleType.NEAREST_BED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MaidMemoryModuleType.TARGET_HOSTILES, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.HIDING_PLACE, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LAST_SLEPT, MemoryModuleType.LAST_WORKED_AT_POI);
    private static final ImmutableList<SensorType<? extends Sensor<? super LittleMaidEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.INTERACTABLE_DOORS, SensorType.NEAREST_BED, SensorType.HURT_BY, LittleSensorTypes.MAID_HOSTILES, LittleSensorTypes.DEFEND_OWNER);
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<LittleMaidEntity, PointOfInterestType>> field_213774_bB = ImmutableMap.of(MemoryModuleType.HOME, (p_213769_0_, p_213769_1_) -> {
        return p_213769_1_ == PointOfInterestType.HOME;
    }, MemoryModuleType.MEETING_POINT, (p_213772_0_, p_213772_1_) -> {
        return p_213772_1_ == PointOfInterestType.MEETING;
    });
    private static final Set<Item> SWEETITEM = Sets.newHashSet(Items.SUGAR, Items.COOKIE, Items.PUMPKIN_PIE, LittleItems.CARAMEL_APPLE);

    private InventoryMaidMain inventoryMaidMain;
    private InventoryMaidEquipment inventoryMaidEquipment;

    private int animationTick;
    private MaidAnimation animation = NO_ANIMATION;

    public static final MaidAnimation TALK_ANIMATION = MaidAnimation.create(100);
    public static final MaidAnimation PET_ANIMATION = MaidAnimation.create(100);

    private static final MaidAnimation[] ANIMATIONS = {
            TALK_ANIMATION,
            PET_ANIMATION
    };

    public ModelConfigCompound textureData;
    public EntityCaps maidCaps;

    protected boolean mstatOpenInventory;

    public float entityIdFactor;

    protected String textureNameMain;
    protected String textureNameArmor;

    public boolean isWildSaved = false;

    protected Counter maidOverDriveTime;
    protected Counter workingCount;

    public int experienceLevel;
    public int experienceTotal;
    public float experience;

    protected static final DataParameter<MaidData> MAID_DATA = EntityDataManager.createKey(LittleMaidEntity.class, MaidDataSerializers.MAID_DATA);

    protected static final DataParameter<Boolean> FREEDOM = EntityDataManager.createKey(LittleMaidEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> WAITING = EntityDataManager.createKey(LittleMaidEntity.class, DataSerializers.BOOLEAN);

    protected static final DataParameter<Boolean> CONTRACT = EntityDataManager.createKey(LittleMaidEntity.class, DataSerializers.BOOLEAN);


    private static final DataParameter<Boolean> DATA_CHARGING_STATE = EntityDataManager.createKey(LittleMaidEntity.class, DataSerializers.BOOLEAN);


    protected static final DataParameter<Byte> dataWatch_Color = EntityDataManager.createKey(LittleMaidEntity.class, DataSerializers.BYTE);

    /**
     * MSB|0x0000 0000|LSB<br>
     * |    |本体のテクスチャインデックス<br>
     * |アーマーのテクスチャインデックス<br>
     */
    protected static final DataParameter<Integer> dataWatch_Texture = EntityDataManager.createKey(LittleMaidEntity.class, DataSerializers.VARINT);
    /**
     * モデルパーツの表示フラグ(Integer)
     */
    protected static final DataParameter<Integer> dataWatch_Parts = EntityDataManager.createKey(LittleMaidEntity.class, DataSerializers.VARINT);

    protected Counter registerTick;
    protected int maidContractLimit;        // 契約期間

    public LittleMaidEntity(EntityType<? extends LittleMaidEntity> p_i48575_1_, World p_i48575_2_) {
        super(p_i48575_1_, p_i48575_2_);
        ((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
        this.getNavigator().setCanSwim(true);
        this.setCanPickUpLoot(true);
        this.brain = this.createBrain(new Dynamic<>(NBTDynamicOps.INSTANCE, new CompoundNBT()));

        maidOverDriveTime = new Counter(5, 300, -32);
        workingCount = new Counter(11, 10, -10);
        registerTick = new Counter(200, 200, -20);

        maidCaps = new EntityCaps(this);
        textureData = new ModelConfigCompound(this, maidCaps);

//		if (getEntityWorld().isRemote) {

        // 形態形成場

        textureData.setColor((byte) 0xc);

        TextureBox ltb[] = new TextureBox[2];

        ltb[0] = ltb[1] = ModelManager.instance.getDefaultTexture(this);

        setTexturePackName(ltb);

        entityIdFactor = getEntityId() * 70;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(26.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(MAID_DATA, new MaidData(MaidJob.WILD, 0));
        this.dataManager.register(FREEDOM, false);
        this.dataManager.register(WAITING, false);
        this.dataManager.register(CONTRACT, false);

        this.dataManager.register(DATA_CHARGING_STATE, false);

        this.dataManager.register(dataWatch_Color, (byte) 0xc);
        // 20:選択テクスチャインデックス
        // TODO いらん？
        this.dataManager.register(dataWatch_Texture, Integer.valueOf(0));
        // 21:モデルパーツの表示フラグ
        this.dataManager.register(dataWatch_Parts, Integer.valueOf(0));
    }

    public Brain<LittleMaidEntity> getBrain() {
        return (Brain<LittleMaidEntity>) super.getBrain();
    }

    protected Brain<?> createBrain(Dynamic<?> p_213364_1_) {
        Brain<LittleMaidEntity> brain = new Brain<>(MEMORY_TYPES, SENSOR_TYPES, p_213364_1_);
        this.initBrain(brain);
        return brain;
    }

    public void resetBrain(ServerWorld p_213770_1_) {
        Brain<LittleMaidEntity> brain = this.getBrain();
        brain.stopAllTasks(p_213770_1_, this);
        this.brain = brain.copy();
        this.initBrain(this.getBrain());
    }

    private void initBrain(Brain<LittleMaidEntity> p_213744_1_) {
        float f = (float) this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();


        if (this.isTamed() && !this.isMaidWaitEx() && !isFreedom()) {
            p_213744_1_.setSchedule(LittleSchedules.FOLLOW);
            p_213744_1_.registerActivity(LittleActivitys.FOLLOW, MaidTasks.follow());
        } else if (this.isTamed() && this.isMaidWaitEx()) {
            p_213744_1_.setSchedule(LittleSchedules.WAITING);
            p_213744_1_.registerActivity(LittleActivitys.WAITING, MaidTasks.waiting());
        } else {
            //If it is not a specific action, set the schedule set in job
            if (this.getMaidData().getJob() != null) {
                p_213744_1_.setSchedule(this.getMaidData().getJob().getSchedule());
            }
        }


        p_213744_1_.registerActivity(Activity.CORE, MaidTasks.core(f));
        p_213744_1_.registerActivity(Activity.WORK, MaidTasks.work(getMaidData().getJob(), f), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT)));
        p_213744_1_.registerActivity(Activity.REST, MaidTasks.rest(f));
        p_213744_1_.registerActivity(Activity.IDLE, MaidTasks.idle(f));
        p_213744_1_.registerActivity(Activity.PANIC, MaidTasks.panic(f));
        p_213744_1_.registerActivity(LittleActivitys.ATTACK, MaidTasks.attack(f));
        p_213744_1_.registerActivity(LittleActivitys.SHOT, MaidTasks.shot(f));
        p_213744_1_.setDefaultActivities(ImmutableSet.of(Activity.CORE));
        p_213744_1_.setFallbackActivity(Activity.IDLE);
        p_213744_1_.switchTo(Activity.IDLE);
        p_213744_1_.updateActivity(this.world.getDayTime(), this.world.getGameTime());
    }

    protected void updateAITasks() {
        this.world.getProfiler().startSection("brain");
        this.getBrain().tick((ServerWorld) this.world, this);
        this.world.getProfiler().endSection();
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    public int colorMultiplier(float pLight, float pPartialTicks) {
        // 発光処理用
        int lbase = 0, i = 0, j = 0, k = 0, x = 0, y = 0;
        if (maidOverDriveTime.isDelay()) {
            j = 0x00df0000;
            if (maidOverDriveTime.isEnable()) {
                x = 128;
            } else {
                x = (int) (128 - maidOverDriveTime.getValue() * (128f / 32));
            }
        }
        if (registerTick.isDelay()) {
            k = 0x0000df00;
            if (registerTick.isEnable()) {
                y = 128;
            } else {
                y = (int) (128 - registerTick.getValue() * (128f / 20));
            }
        }
        i = x == 0 ? (y >= 128 ? y : 0) : (y == 0 ? x : Math.min(x, y));
        lbase = i << 24 | j | k;

        /*if (isActiveModeClass()) {
            lbase = lbase | getActiveModeClass().colorMultiplier(pLight, pPartialTicks);
        }*/

        return lbase;
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("MaidInventory", this.getInventoryMaidMain().writeInventoryToNBT());
        compound.put("MaidEquipment", this.getInventoryMaidEquipment().writeInventoryToNBT());

        compound.put("MaidData", this.getMaidData().serialize(NBTDynamicOps.INSTANCE));

        compound.putFloat("XpP", this.experience);
        compound.putInt("XpLevel", this.experienceLevel);
        compound.putInt("XpTotal", this.experienceTotal);

        compound.putBoolean("Freedom", isFreedom());
        compound.putBoolean("Wait", isMaidWait());

        compound.putBoolean("isWildSaved", isWildSaved);
        compound.putInt("LimitCount", maidContractLimit);

        compound.putByte("ColorB", getColor());
        compound.putString("texName", textureData.getTextureName(0));
        compound.putString("texArmor", textureData.getTextureName(1));

        if (textureNameMain == null) textureNameMain = "default_Orign";
        compound.putString("textureModelNameForClient", textureNameMain);

        if (textureNameArmor == null) textureNameArmor = "default_Orign";
        compound.putString("textureArmorNameForClient", textureNameArmor);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        this.getInventoryMaidMain().readInventoryFromNBT(compound.getList("MaidInventory", 10));
        this.getInventoryMaidEquipment().readInventoryFromNBT(compound.getList("MaidEquipment", 10));

        if (compound.contains("MaidData", 10)) {
            this.setMaidData(new MaidData(new Dynamic<>(NBTDynamicOps.INSTANCE, compound.get("MaidData"))));
        }

        this.experience = compound.getFloat("XpP");
        this.experienceLevel = compound.getInt("XpLevel");
        this.experienceTotal = compound.getInt("XpTotal");

        setFreedom(compound.getBoolean("Freedom"));
        setMaidWait(compound.getBoolean("Wait"));

        if (compound.contains("LimitCount")) {
            maidContractLimit = compound.getInt("LimitCount");
        } else {

            long lcl = compound.getLong("Limit");

            if (isContract() && lcl == 0) {

                maidContractLimit = 24000;

            } else {
                maidContractLimit = (int) ((lcl - getEntityWorld().getDayTime()));
            }
        }

        if (isContract() && maidContractLimit == 0) {
            // 値がおかしい時は１日分
            maidContractLimit = 24000;
        }

        textureNameMain = compound.getString("textureModelNameForClient");

        if (textureNameMain.isEmpty()) {

            textureNameMain = "default_" + ModelManager.defaultModelName;

        }

        textureNameArmor = compound.getString("textureArmorNameForClient");

        if (textureNameArmor.isEmpty()) {
            textureNameArmor = "default_" + ModelManager.defaultModelName;
        }

        if (compound.contains("Color")) {
            setColor((byte) compound.getInt("Color"));
            compound.remove("Color");
        } else {
            setColor(compound.getByte("ColorB"));
        }
        refreshModels();

        isWildSaved = compound.getBoolean("isWildSaved");

        this.setGrowingAge(Math.max(0, this.getGrowingAge()));

        this.setCanPickUpLoot(true);
        this.resetBrain((ServerWorld) this.world);
    }


    public void giveExperiencePoints(int p_195068_1_) {

        this.experience += (float) p_195068_1_ / (float) this.xpBarCap();
        this.experienceTotal = MathHelper.clamp(this.experienceTotal + p_195068_1_, 0, Integer.MAX_VALUE);

        while (this.experience < 0.0F) {
            float f = this.experience * (float) this.xpBarCap();
            if (this.experienceLevel > 0) {
                this.addExperienceLevel(-1);
                this.experience = 1.0F + f / (float) this.xpBarCap();
            } else {
                this.addExperienceLevel(-1);
                this.experience = 0.0F;
            }
        }

        while (this.experience >= 1.0F) {
            this.experience = (this.experience - 1.0F) * (float) this.xpBarCap();
            this.addExperienceLevel(1);
            this.experience /= (float) this.xpBarCap();
        }

        this.getMaidData().withLevel(this.experienceLevel);
    }

    /**
     * Add experience levels to this maid.(Vannila)
     */
    public void addExperienceLevel(int levels) {

        this.experienceLevel += levels;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0F;
            this.experienceTotal = 0;
        }

        /*if (levels > 0 && this.experienceLevel % 5 == 0 && (float)this.lastXPSound < (float)this.ticksExisted - 100.0F) {
            float f = this.experienceLevel > 30 ? 1.0F : (float)this.experienceLevel / 30.0F;
            this.world.playSound((PlayerEntity)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, this.getSoundCategory(), f * 0.75F, 1.0F);
            this.lastXPSound = this.ticksExisted;
        }*/

    }

    /**
     * This method returns the cap amount of experience that the experience bar can hold. With each level, the experience
     * cap on the maid's experience bar is raised by 10.(Vannila)
     */
    public int xpBarCap() {
        if (this.experienceLevel >= 30) {
            return 112 + (this.experienceLevel - 30) * 9;
        } else {
            return this.experienceLevel >= 15 ? 37 + (this.experienceLevel - 15) * 5 : 7 + this.experienceLevel * 2;
        }
    }

    protected int getExperiencePoints(PlayerEntity player) {
        int i = this.experienceLevel * 7;
        return i > 100 ? 100 : i;
    }

    public void setMaidData(MaidData p_213753_1_) {
        MaidData maidData = this.getMaidData();
        if (maidData.getJob() != p_213753_1_.getJob()) {
        }

        this.dataManager.set(MAID_DATA, p_213753_1_);
    }

    public MaidData getMaidData() {
        return this.dataManager.get(MAID_DATA);
    }

    public void onDeath(DamageSource cause) {
        Entity entity = cause.getTrueSource();
       /* if (entity != null) {
            this.func_223361_a(entity);
        }*/

        this.func_213742_a(MemoryModuleType.HOME);
        this.func_213742_a(MemoryModuleType.JOB_SITE);
        this.func_213742_a(MemoryModuleType.MEETING_POINT);

        InventoryHelper.dropInventoryItems(world, this, this.getInventoryMaidMain());
        InventoryHelper.dropInventoryItems(world, this, this.getInventoryMaidEquipment());

        super.onDeath(cause);
    }


    public void func_213742_a(MemoryModuleType<GlobalPos> p_213742_1_) {
        if (this.world instanceof ServerWorld) {
            MinecraftServer minecraftserver = ((ServerWorld) this.world).getServer();
            this.brain.getMemory(p_213742_1_).ifPresent((p_213752_3_) -> {
                ServerWorld serverworld = minecraftserver.getWorld(p_213752_3_.getDimension());
                PointOfInterestManager pointofinterestmanager = serverworld.func_217443_B();
                Optional<PointOfInterestType> optional = pointofinterestmanager.func_219148_c(p_213752_3_.getPos());
                BiPredicate<LittleMaidEntity, PointOfInterestType> bipredicate = field_213774_bB.get(p_213742_1_);
                if (optional.isPresent() && bipredicate.test(this, optional.get())) {
                    pointofinterestmanager.func_219142_b(p_213752_3_.getPos());
                    DebugPacketSender.func_218801_c(serverworld, p_213752_3_.getPos());
                }

            });
        }
    }


    public float getSwingProgress(float ltime, Hand hand) {
        if (this.swingingHand == hand) {
            float lf = swingProgress - prevSwingProgress;

            if (lf < 0.0F) {
                ++lf;
            }

            return prevSwingProgress + lf * ltime;
        } else {
            return 0.0F;
        }
    }

    public Hand getSwingHand() {
        if (this.swingingHand != null) {
            return this.swingingHand;
        } else {
            return Hand.MAIN_HAND;
        }
    }

    @Override
    public boolean canDespawn(double p_213397_1_) {
        return false;
    }

    @Override
    public byte getColor() {
        return dataManager.get(LittleMaidEntity.dataWatch_Color);
    }

    @Override
    public void setColor(byte index) {
        textureData.setColor(index);

        dataManager.set(LittleMaidEntity.dataWatch_Color, index);
    }

    // お仕事チュ

    /**
     * 仕事中かどうかの設定
     */
    public void setWorking(boolean pFlag) {
        workingCount.setEnable(pFlag);
    }

    /**
     * 仕事中かどうかを返す
     */
    public boolean isWorking() {
        return workingCount.isEnable();
    }

    /**
     * 仕事が終了しても余韻を含めて返す
     */
    public boolean isWorkingDelay() {
        return workingCount.isDelay();
    }

    public Counter getMaidOverDriveTime() {
        return maidOverDriveTime;
    }

    private void eatSweets(boolean recontract) {
        ItemStack itemstack = findFood();

        if (!itemstack.isEmpty()) {
            if (itemstack.isFood()) {
                Item itemfood = (Item) itemstack.getItem();
                this.heal((float) itemfood.getFood().getHealing());
                itemstack.shrink(1);

                addContractLimit(recontract);

                this.playSound(SoundEvents.ENTITY_GENERIC_EAT, this.getSoundVolume(), this.getSoundPitch());
            } else if (itemstack.getItem() == Items.SUGAR) {
                this.heal(1);
                itemstack.shrink(1);
                addContractLimit(recontract);
                this.playSound(SoundEvents.ENTITY_GENERIC_EAT, this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }

    private void addContractLimit(boolean recontract) {
        if (recontract) {
            // 契約期間の延長
            maidContractLimit += 24000;

            if (maidContractLimit > 168000) {
                maidContractLimit = 168000;    // 24000 * 7
            }
        }
    }

    public void clearMaidContractLimit() {
        maidContractLimit = 0;
    }

    protected void updateRemainsContract() {
        boolean lflag = false;

        if (maidContractLimit > 0) {
            maidContractLimit--;

            lflag = true;
        }

        if (this.dataManager.get(CONTRACT) != lflag) {
            this.dataManager.set(CONTRACT, lflag);
        }
    }

    public boolean isRemainsContract() {

        return this.dataManager.get(CONTRACT);
    }

    @Override
    public boolean isTamed() {
        return isContract();
    }

    public boolean isContract() {
        return super.isTamed();
    }

    public boolean isContractEX() {
        return isContract() && isRemainsContract();
    }

    public float getContractLimitDays() {
        return maidContractLimit > 0 ? (maidContractLimit / 24000F) : -1F;
    }

    @Override
    public void setTamed(boolean tamed) {
        setContract(tamed);
    }

    public InventoryMaidMain getInventoryMaidMain() {
        if (this.inventoryMaidMain == null) {
            this.inventoryMaidMain = new InventoryMaidMain(this);
        }

        return this.inventoryMaidMain;
    }

    public InventoryMaidEquipment getInventoryMaidEquipment() {
        if (this.inventoryMaidEquipment == null) {
            this.inventoryMaidEquipment = new InventoryMaidEquipment(this);
        }

        return this.inventoryMaidEquipment;
    }

    private ItemStack findFood() {
        ItemStack stack;

        for (int i = 0; i < this.getInventoryMaidMain().getSizeInventory(); ++i) {
            stack = getInventoryMaidMain().getStackInSlot(i);

            if (SWEETITEM.contains(stack.getItem())) {
                return stack;
            } else {
                stack = getInventoryMaidEquipment().getOffhandItem();

                if (SWEETITEM.contains(stack.getItem())) {
                    return stack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();

        if (!stack.isEmpty()) {
            stack = onItemStackPickup(stack);


            this.onItemPickup(itemEntity, stack.getCount());
            if (stack.isEmpty()) {
                itemEntity.remove();
            } else {
                itemEntity.setItem(stack);
            }

        }
    }

    public ItemStack onItemStackPickup(ItemStack stack) {

        return getInventoryMaidMain().addItem(stack);

    }

    public boolean isFarmItemInInventory() {
        Inventory inventory = this.getInventoryMaidMain();
        return inventory.hasAny(ImmutableSet.of(Items.WHEAT_SEEDS, Items.POTATO, Items.CARROT, Items.BEETROOT_SEEDS));
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

        switch (slotIn) {
            case CHEST:

                this.getInventoryMaidEquipment().setInventorySlotContents(0, stack);
                break;
            case FEET:

                this.getInventoryMaidEquipment().setInventorySlotContents(1, stack);
                break;
            case HEAD:

                this.getInventoryMaidEquipment().setInventorySlotContents(2, stack);
                break;
            case LEGS:

                this.getInventoryMaidEquipment().setInventorySlotContents(3, stack);
                break;
            case OFFHAND:

                this.getInventoryMaidEquipment().setInventorySlotContents(4, stack);
                break;
            case MAINHAND:

                this.getInventoryMaidEquipment().setInventorySlotContents(5, stack);
                break;
        }
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        ItemStack itemStack;

        switch (slotIn) {
            case CHEST:

                itemStack = this.getInventoryMaidEquipment().getChestItem();
                break;
            case FEET:

                itemStack = this.getInventoryMaidEquipment().getbootItem();
                break;
            case HEAD:

                itemStack = this.getInventoryMaidEquipment().getheadItem();
                break;
            case LEGS:

                itemStack = this.getInventoryMaidEquipment().getLegItem();
                break;
            case OFFHAND:

                itemStack = this.getInventoryMaidEquipment().getOffhandItem();
                break;
            case MAINHAND:

                itemStack = this.getInventoryMaidEquipment().getMainhandItem();
                break;
            default:

                itemStack = ItemStack.EMPTY;
                break;
        }

        return itemStack;
    }

    protected void damageArmor(float damage) {
        this.getInventoryMaidEquipment().damageArmor(damage);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag) {
            if (entityIn.isNonBoss()) {
                giveExperiencePoints(1 + this.getRNG().nextInt(1));
            } else {
                giveExperiencePoints(2 + this.getRNG().nextInt(2));
            }
        }


        return flag;
    }

    @Override
    public void livingTick() {
        this.updateArmSwingProgress();
        if (getHealth() < getMaxHealth() && ticksExisted % 30 == 0) {
            eatSweets(false);
        }
        super.livingTick();

        if (isContractEX()) {
            float f = getContractLimitDays();
            if (f <= 6) {
                // 契約更新
                eatSweets(true);
            }
        }
    }

    @Override
    public void tick() {
        if (registerTick.isDelay()) {
            registerTick.onUpdate();

            if (!registerTick.isEnable() && registerTick.getValue() == 0 && !getEntityWorld().isRemote) {
                //getMaidMasterEntity().sendMessage(new TextComponentTranslation("littleMaidMob.chat.text.cancelregistration").setStyle(new Style().setColor(TextFormatting.DARK_RED)));
            }
        }

        textureData.onUpdate();

        if (this.getAnimation() != IMaidAnimation.NO_ANIMATION) {
            this.setAnimationTick(this.getAnimationTick() + 1);

            if (this.getAnimationTick() >= this.getAnimation().getDuration()) {
                this.onAnimationFinish(this.getAnimation());
                this.resetPlayingAnimationToDefault();
            }
        }

        if (getEntityWorld().isRemote) {

            // クライアント側
            boolean lupd = false;

            lupd |= updateMaidContract();

            lupd |= updateMaidColor();

//			lupd |= updateTexturePack();

            //updateTexturePack();
            if (lupd) {

                setTextureNames();

            }
/*
            setMaidMode(dataManager.get(EntityLittleMaid.dataWatch_Mode));
            setDominantArm(dataManager.get(EntityLittleMaid.dataWatch_DominamtArm));
            updateMaidFlagsClient();
            updateGotcha();
*/
/*

            // メイド経験値
            if (ticksExisted%10 == 0) {
                maidExperience = dataManager.get(EntityLittleMaid.dataWatch_MaidExpValue);
            }

            // 腕の挙動関連
            litemuse = dataManager.get(EntityLittleMaid.dataWatch_ItemUse);
            for (int li = 0; li < mstatSwingStatus.length; li++) {
                ItemStack lis = mstatSwingStatus[li].getItemStack(this);
                if ((litemuse & (1 << li)) > 0 && !lis.isEmpty()) {
                    mstatSwingStatus[li].setItemInUse(lis, lis.getMaxItemUseDuration(), this);
                } else {
                    mstatSwingStatus[li].stopUsingItem(this);
                }

            }



            // Entity初回生成時のインベントリ更新用
            // ClientサイドにおいてthePlayerが取得できるまでに時間がかかる？ので待機
            // サーバーの方が先に起動するのでクライアント側が更新を受け取れない

            if (firstload > 0) {
                if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().player != null) {
                    syncNet(LMRMessage.EnumPacketMode.SERVER_REQUEST_MODEL, null);
                    syncNet(LMRMessage.EnumPacketMode.REQUEST_CURRENT_ITEM, null);
                    firstload = 0;
                }
            }*/

        } else {
            updateRemainsContract();
            // 拗ねる

            if (!isRemainsContract() && !isFreedom()) {
                setFreedom(true);
                setMaidWait(false);
                if (!this.world.isRemote()) {
                    this.resetBrain((ServerWorld) this.world);
                }
            }
        }

        super.tick();
    }

    public boolean isShooting() {
        return isAggressive() && getHeldItem(Hand.MAIN_HAND).getItem() instanceof BowItem;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        onSpawnWithEgg();

        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public boolean canCombat() {
        return this.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem;
    }


    public void onSpawnWithEgg() {
        // テクスチャーをランダムで選択
        String ls;
        /*if (LMRConfig.cfg_isFixedWildMaid) {
            ls = "default_Orign";
        } else {*/
        ls = ModelManager.instance.getRandomTextureString(rand);
        //}
        textureData.setTextureInitServer(ls);
        MaidModRedo.LOGGER.debug("init-ID:%d, %s:%d", getEntityId(), textureData.textureBox[0].textureName, textureData.getColor());
//		setTexturePackIndex(textureData.getColor(), textureData.textureIndex);
        setTextureNameMain(textureData.textureBox[0].textureName);
        setTextureNameArmor(textureData.textureBox[1].textureName);
//		recallRenderParamTextureName(textureModelNameForClient, textureArmorNameForClient);
        if (!isContract()) {
            //setMaidMode(EntityMode_Basic.mmode_Wild);
            onSpawnWild();
        }
    }

    protected void onSpawnWild() {
        // 野生メイドの色設定処理
        int nsize = 0;
        byte avaliableColor[] = new byte[16];
        TextureBoxBase box = getModelConfigCompound().textureBox[0];
        for (byte i = 0; i < 16; i++) {
            if ((box.wildColor & 1 << i) > 0) {
                avaliableColor[nsize++] = i;
            }
        }
        setColor(avaliableColor[rand.nextInt(nsize)]);
    }

    public boolean updateMaidColor() {
        // 同一性のチェック
        byte lc = getColor();

        if (textureData.getColor() != lc) {

            textureData.setColor(lc);

            return true;

        }
        return false;
    }

    @Override
    public void setContract(boolean flag) {
        super.setTamed(flag);

        textureData.setContract(flag);
    }

    public boolean isMaidWait() {
        return this.dataManager.get(WAITING);
    }

    public boolean isMaidWaitEx() {
        return isMaidWait() || isOpenInventory();
    }

    public void setOpenInventory(boolean flag) {
        mstatOpenInventory = flag;

        if (!this.world.isRemote() && (!this.getMaidData().getJob().isLockJob()) || this.getMaidData().getJob().isLockJob() && this.getMaidData().getLevel() == 0) {
            MaidJob.MAID_JOB_REGISTRY.stream().filter((job) -> {
                if (job.getSubRequireItem() != null) {
                    return this.getMaidData().getLevel() >= job.getNeedLevel() && job.getRequireItem().test(this.getHeldItem(Hand.MAIN_HAND)) && job.getSubRequireItem().test(this.getHeldItem(Hand.OFF_HAND));
                } else {
                    return this.getMaidData().getLevel() >= job.getNeedLevel() && job.getRequireItem().test(this.getHeldItem(Hand.MAIN_HAND));
                }
            }).findFirst().ifPresent((p_220388_2_) -> {
                this.setMaidData(this.getMaidData().withJob(p_220388_2_));
            });
        }
    }

    public boolean isOpenInventory() {
        return mstatOpenInventory;
    }

    public boolean isFreedom() {
        return this.dataManager.get(FREEDOM);
    }

    public void setFreedom(boolean pflag) {
        this.dataManager.set(FREEDOM, pflag);
    }


    public void setMaidWait(boolean pflag) {
        this.dataManager.set(WAITING, pflag);
        isJumping = false;
        setAttackTarget(null);
        setRevengeTarget(null);
        getNavigator().clearPath();
        if (pflag) {

            //setMaidModeAITasks(null,null);

            setWorking(false);

            getNavigator().clearPath();

            //clearTilePosAll();

			/*

			setHomePosAndDistance(

					new BlockPos(MathHelper.floor(lastTickPosX),

					MathHelper.floor(lastTickPosY),

					MathHelper.floor(lastTickPosZ)), 0);

					*/

        }
        if (!world.isRemote()) {
            this.resetBrain((ServerWorld) this.world);
        }
        velocityChanged = true;
    }

    @Override
    public void handleStatusUpdate(byte id) {
        // getEntityWorld().setEntityState(this, (byte))で指定されたアクションを実行
        switch (id) {
            case 10:
                // 不機嫌
                func_213718_a(ParticleTypes.SMOKE);
                break;
            case 11:
                // ゴキゲン
                double a = getContractLimitDays() / 7D;
                double d6 = a * 0.3D;
                double d7 = a;
                double d8 = a * 0.3D;

                getEntityWorld().addParticle(ParticleTypes.NOTE, posX, posY + getHeight() + 0.1D, posZ, d6, d7, d8);
                break;
            case 13:
                // 不自由行動
                func_213718_a(ParticleTypes.SMOKE);
                break;
            case 14:
                // トレーサー
                func_213718_a(ParticleTypes.EXPLOSION);
                break;
            case 17:
                // トリガー登録
                func_213718_a(ParticleTypes.FIREWORK);
                break;
            default:
                super.handleStatusUpdate(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void func_213718_a(IParticleData particleData) {
        for (int i = 0; i < 5; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.addParticle(particleData, this.posX + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), this.posY + 1.0D + (double) (this.rand.nextFloat() * this.getHeight()), this.posZ + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), d0, d1, d2);
        }

    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (this.isContract()) {
            if (!isRemainsContract() && !itemstack.isEmpty()) {
                // ストライキ
                if (item == Items.SUGAR) {
                    // 受取拒否
                    getEntityWorld().setEntityState(this, (byte) 10);
                    return true;
                } else if (item == Items.CAKE) {
                    // 再契約
                    this.setMaidData(this.getMaidData().withJob(MaidJob.NORMAL));
                    itemstack.shrink(1);
                    maidContractLimit = (24000 * 7);
                    setFreedom(false);
                    setMaidWait(false);
                    if (!isOwner(player)) {
                        // あんなご主人なんか捨てて、僕のもとへおいで(洗脳)
                        this.setTamedBy(player);
                        //playLittleMaidSound(EnumSound.getCake, true);
                        getEntityWorld().setEntityState(this, (byte) 7);
                        maidContractLimit = (24000 * 7);
                        //maidAnniversary = getEntityWorld().getTotalWorldTime();
                    } else {
                        // ごめんねメイドちゃん
                        getEntityWorld().setEntityState(this, (byte) 11);
                        //playLittleMaidSound(EnumSound.Recontract, true);

                    }
                    return true;
                }
            } else {
                if (!itemstack.isEmpty()) {

                    if (SWEETITEM.contains(item) && this.getHealth() < this.getMaxHealth()) {
                        if (item.isFood()) {
                            if (!player.abilities.isCreativeMode) {
                                itemstack.shrink(1);
                            }

                            this.addContractLimit(false);
                            this.heal((float) item.getFood().getHealing());
                            this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, 0.7F);
                            getEntityWorld().setEntityState(this, (byte) 11);
                            return true;
                        }
                    } else if (item instanceof DyeItem) {
                        DyeColor dyecolor = ((DyeItem) item).getDyeColor();
                        if (dyecolor.getId() != 15 - this.getColor()) {
                            if (!player.abilities.isCreativeMode) {
                                itemstack.shrink(1);
                            }
                            if (!getEntityWorld().isRemote) {
                                this.setColor((byte) (dyecolor.getId()));
                            }

                            this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 0.7F);
                            return true;
                        }
                    } else if (item.getItem() == Items.FEATHER) {

                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }
                        if (!this.world.isRemote()) {
                            this.setFreedom(!isFreedom());
                            this.resetBrain((ServerWorld) this.world);
                        }

                        this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 0.7F);
                        //getEntityWorld().setEntityState(this, (byte) 11);
                        return true;
                    }
                }

                if (this.isOwner(player) && item == Items.SUGAR) {
                    if (!this.world.isRemote) {
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }

                        this.heal(1.0F);

                        this.maidContractLimit += 3000;

                        this.setMaidWait(!this.isMaidWait());
                        this.isJumping = false;
                        this.navigator.clearPath();
                        this.setAttackTarget((LivingEntity) null);
                        this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 0.7F);
                        getEntityWorld().setEntityState(this, (byte) 11);
                    }
                    this.addContractLimit(false);
                    return true;
                }

                if (this.isOwner(player) && player.isSneaking() && itemstack.isEmpty() && getAnimation() != PET_ANIMATION) {
                    MaidPacketHandler.animationModel(this, PET_ANIMATION);

                    return true;
                } else if (item.itemInteractionForEntity(itemstack, player, this, hand)) {
                } else if (this.isOwner(player) && !SWEETITEM.contains(item) && item != Items.FEATHER) {
                    if (player instanceof ServerPlayerEntity && !(player instanceof FakePlayer)) {
                        if (!player.world.isRemote) {
                            ServerPlayerEntity entityPlayerMP = (ServerPlayerEntity) player;
                            NetworkHooks.openGui(entityPlayerMP, new INamedContainerProvider() {
                                @Override
                                public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
                                    return new MaidInventoryContainer(windowId, inventory, (LittleMaidEntity) player.world.getEntityByID(getEntityId()));
                                }

                                @Override
                                public ITextComponent getDisplayName() {
                                    return new TranslationTextComponent("container.maidmobredo.maid_inventory");
                                }
                            }, buf -> {
                                buf.writeInt(this.getEntityId());
                            });
                        }
                    }

                    return true;
                }
            }
        } else if (item == Items.CAKE) {
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }

            maidContractLimit = 24000;

            if (!this.world.isRemote) {
                if (!net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setMaidData(this.getMaidData().withJob(MaidJob.NORMAL));
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity) null);
                    this.setMaidWait(true);
                    this.setFreedom(false);
                    this.setHealth(20.0F);
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte) 7);
                    resetBrain((ServerWorld) this.world);
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte) 6);
                }
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
    }

    public boolean updateMaidContract() {
        // 同一性のチェック
        boolean lf = isContract();
        if (textureData.isContract() != lf) {
            textureData.setContract(lf);

            return true;
        }
        return false;
    }

    @Override
    public void setTexturePackName(TextureBox[] pTextureBox) {
        // Client
        textureData.setTexturePackName(pTextureBox);
        setTextureNames();
        MaidModRedo.LOGGER.debug("ID:%d, TextureModel:%s", getEntityId(), textureData.getTextureName(0));

        // モデルの初期化
        ((TextureBox) textureData.textureBox[0]).models[0].setCapsValue(IModelCaps.caps_changeModel, maidCaps);

        // スタビの付け替え
//		for (Entry<String, MMM_EquippedStabilizer> le : pEntity.maidStabilizer.entrySet()) {
//			if (le.getValue() != null) {
//				le.getValue().updateEquippedPoint(pEntity.textureModel0);
//			}
//		}
    }


    /**
     * Client用
     */

    public void setTextureNames() {
        textureData.setTextureNames();
        if (getEntityWorld().isRemote) {
            textureNameMain = textureData.getTextureName(0);

            textureNameArmor = textureData.getTextureName(1);
        }
    }

    public void setNextTexturePackege(int pTargetTexture) {
        textureData.setNextTexturePackege(pTargetTexture);
    }

    public void setPrevTexturePackege(int pTargetTexture) {
        textureData.setPrevTexturePackege(pTargetTexture);
    }

    // textureEntity
    @Override
    public void setTextureBox(TextureBoxBase[] pTextureBox) {
        textureData.setTextureBox(pTextureBox);
    }

    public String getModelNameMain() {
        return textureNameMain;
    }

    public String getModelNameArmor() {
        return textureNameArmor;
    }


    public void setTextureNameMain(String modelNameMain) {
        this.textureNameMain = modelNameMain;

        refreshModels();
    }


    public void setTextureNameArmor(String modelNameArmor) {
        this.textureNameArmor = modelNameArmor;

        refreshModels();
    }


    protected void refreshModels() {
        String defName = ModelManager.instance.getRandomTextureString(rand);

        TextureBoxBase mainModel = modelBoxAutoSelect(textureNameMain);

        if (mainModel == null) {
            mainModel = modelBoxAutoSelect(defName);
        }

        TextureBoxBase armorModel = modelBoxAutoSelect(textureNameArmor);

        if (armorModel == null) {
            armorModel = modelBoxAutoSelect(defName);
        }


        setTextureBox(new TextureBoxBase[]{mainModel, armorModel});
        setTextureNames();

        getModelConfigCompound().setSize();
    }

    private TextureBoxBase modelBoxAutoSelect(String pName) {
        return getEntityWorld().isRemote ? ModelManager.instance.getTextureBox(pName) : ModelManager.instance.getTextureBoxServer(pName);
    }

    @Override
    public TextureBoxBase[] getTextureBox() {
        return textureData.getTextureBox();
    }

    @Override
    public void setTextures(int pIndex, ResourceLocation[] pNames) {
        textureData.setTextures(pIndex, pNames);
    }

    @Override
    public ResourceLocation[] getTextures(int pIndex) {
        ResourceLocation[] r = textureData.getTextures(pIndex);

        return r;
    }

    @Override
    public ModelConfigCompound getModelConfigCompound() {
        return textureData;
    }

    public boolean canChangeModel() {
        return true;
    }

    public void syncModelNames() {
        CompoundNBT tagCompound = new CompoundNBT();
        tagCompound.putString("Main", getModelNameMain());
        tagCompound.putString("Armor", getModelNameArmor());
        MaidPacketHandler.syncModel(this, tagCompound);
    }

    @Override
    public Map<String, Integer> getModelCaps() {
        return maidCaps.getModelCaps();
    }


    @Override
    public Object getCapsValue(int pIndex, Object... pArg) {
        return maidCaps.getCapsValue(pIndex, pArg);
    }


    @Override
    public boolean setCapsValue(int pIndex, Object... pArg) {
        return maidCaps.setCapsValue(pIndex, pArg);
    }

    public void resetPlayingAnimationToDefault() {
        this.setAnimation(IMaidAnimation.NO_ANIMATION);
    }

    protected void onAnimationFinish(MaidAnimation animation) {
    }

    @Override
    public int getAnimationTick() {
        return this.animationTick;
    }

    @Override
    public void setAnimationTick(int tick) {
        this.animationTick = tick;
    }

    @Override
    public MaidAnimation getAnimation() {
        return this.animation;
    }

    @Override
    public void setAnimation(MaidAnimation animation) {
        if (animation == NO_ANIMATION) {
            onAnimationFinish(this.animation);
        }
        this.animation = animation;

        setAnimationTick(0);
    }

    @Override
    public MaidAnimation[] getAnimations() {
        return ANIMATIONS;
    }

    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        ItemStack itemstack = this.findAmmo(this.getHeldItem(ProjectileHelper.getHandWith(this, this.getHeldItem(Hand.MAIN_HAND).getItem())));

        Hand hand = ProjectileHelper.getHandWith(this, Items.CROSSBOW);
        ItemStack itemstack2 = this.getHeldItem(hand);
        if (this.isHolding(Items.CROSSBOW)) {
            CrossbowItem.fireProjectiles(this.world, this, Hand.MAIN_HAND, itemstack2, 1.9F, (float) (1.0F));
        } else {
            ArrowItem arrowitem = (ArrowItem) (itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
            AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(world, itemstack, this);
            if (this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.BowItem)
                abstractarrowentity = ((net.minecraft.item.BowItem) this.getHeldItemMainhand().getItem()).customeArrow(abstractarrowentity);
            double d0 = target.posX - this.posX;
            double d1 = target.getBoundingBox().minY + (double) (target.getHeight() / 3.0F) - abstractarrowentity.posY;
            double d2 = target.posZ - this.posZ;
            double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
            abstractarrowentity.shoot(d0, d1 + d3 * (double) 0.2F, d2, distanceFactor * 1.75F, (float) (1.0F));
            this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            this.world.addEntity(abstractarrowentity);
            itemstack.shrink(1);
            this.getHeldItem(ProjectileHelper.getHandWith(this, this.getHeldItem(Hand.MAIN_HAND).getItem())).damageItem(1, this, (p_213625_1_) -> {
                p_213625_1_.sendBreakAnimation(hand);
            });
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isCharging() {
        return this.dataManager.get(DATA_CHARGING_STATE);
    }

    public void setCharging(boolean p_213671_1_) {
        this.dataManager.set(DATA_CHARGING_STATE, p_213671_1_);
    }

    public void shoot(LivingEntity p_213670_1_, ItemStack p_213670_2_, IProjectile p_213670_3_, float p_213670_4_) {
        Entity entity = (Entity) p_213670_3_;
        double d0 = p_213670_1_.posX - this.posX;
        double d1 = p_213670_1_.posZ - this.posZ;
        double d2 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1);
        double d3 = p_213670_1_.getBoundingBox().minY + (double) (p_213670_1_.getHeight() / 3.0F) - entity.posY + d2 * (double) 0.2F;
        Vector3f vector3f = this.func_213673_a(new Vec3d(d0, d3, d1), p_213670_4_);
        p_213670_3_.shoot((double) vector3f.getX(), (double) vector3f.getY(), (double) vector3f.getZ(), 1.6F, (float) (14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
    }

    private Vector3f func_213673_a(Vec3d p_213673_1_, float p_213673_2_) {
        Vec3d vec3d = p_213673_1_.normalize();
        Vec3d vec3d1 = vec3d.crossProduct(new Vec3d(0.0D, 1.0D, 0.0D));
        if (vec3d1.lengthSquared() <= 1.0E-7D) {
            vec3d1 = vec3d.crossProduct(this.func_213286_i(1.0F));
        }

        Quaternion quaternion = new Quaternion(new Vector3f(vec3d1), 90.0F, true);
        Vector3f vector3f = new Vector3f(vec3d);
        vector3f.func_214905_a(quaternion);
        Quaternion quaternion1 = new Quaternion(vector3f, p_213673_2_, true);
        Vector3f vector3f1 = new Vector3f(vec3d);
        vector3f1.func_214905_a(quaternion1);
        return vector3f1;
    }


    public ItemStack findAmmo(ItemStack shootable) {
        if (!(shootable.getItem() instanceof ShootableItem)) {
            return ItemStack.EMPTY;
        } else {
            Predicate<ItemStack> predicate = ((ShootableItem) shootable.getItem()).getAmmoPredicate();
            ItemStack itemstack = ShootableItem.getHeldAmmo(this, predicate);
            if (!itemstack.isEmpty()) {
                return itemstack;
            } else {
                predicate = ((ShootableItem) shootable.getItem()).getInventoryAmmoPredicate();
                for (int i = 0; i < this.getInventoryMaidMain().getSizeInventory(); ++i) {
                    ItemStack itemstack1 = this.getInventoryMaidMain().getStackInSlot(i);
                    if (predicate.test(itemstack1)) {
                        return itemstack1;
                    }
                }
                return ItemStack.EMPTY;
            }
        }
    }
}
