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
import mmr.maidmodredo.client.maidmodel.IModelEntity;
import mmr.maidmodredo.client.maidmodel.ModelConfigCompound;
import mmr.maidmodredo.client.maidmodel.TextureBox;
import mmr.maidmodredo.client.maidmodel.TextureBoxBase;
import mmr.maidmodredo.entity.data.MaidData;
import mmr.maidmodredo.entity.misc.MaidFishingBobberEntity;
import mmr.maidmodredo.entity.pathnavigator.MaidGroundPathNavigator;
import mmr.maidmodredo.entity.phantom.SugarPhantomEntity;
import mmr.maidmodredo.entity.tasks.JobTasks;
import mmr.maidmodredo.entity.tasks.MaidTasks;
import mmr.maidmodredo.init.*;
import mmr.maidmodredo.inventory.InventoryMaidEquipment;
import mmr.maidmodredo.inventory.InventoryMaidMain;
import mmr.maidmodredo.inventory.MaidInventoryContainer;
import mmr.maidmodredo.network.MaidPacketHandler;
import mmr.maidmodredo.utils.ModelManager;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
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
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class LittleMaidBaseEntity extends TameableEntity implements IModelEntity, IMaidAnimation, ICrossbowUser {
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.MEETING_POINT, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.INTERACTABLE_DOORS, MemoryModuleType.field_225462_q, MemoryModuleType.NEAREST_BED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MaidMemoryModuleType.TARGET_HOSTILES, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.HIDING_PLACE, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LAST_SLEPT, MemoryModuleType.field_226332_A_, MemoryModuleType.LAST_WORKED_AT_POI);
    private static final ImmutableList<SensorType<? extends Sensor<? super LittleMaidBaseEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.INTERACTABLE_DOORS, SensorType.NEAREST_BED, SensorType.HURT_BY, LittleSensorTypes.MAID_HOSTILES, LittleSensorTypes.DEFEND_OWNER);
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<LittleMaidBaseEntity, PointOfInterestType>> field_213774_bB = ImmutableMap.of(MemoryModuleType.HOME, (p_213769_0_, p_213769_1_) -> {
        return p_213769_1_ == PointOfInterestType.HOME;
    });
    private static final Set<Item> SWEETITEM = Sets.newHashSet(Items.SUGAR, Items.COOKIE, Items.PUMPKIN_PIE, LittleItems.CARAMEL_APPLE
            , LittleItems.STRAWBERRY_CAKE, LittleItems.REDVELVET_CAKE, LittleItems.LAVENDER_CAKE, LittleItems.ICECREAM_CAKE, LittleItems.DEVILSFOOD_CAKE, LittleItems.COFFEE_CAKE, LittleItems.CHEESE_CAKE, LittleItems.CARROT_CAKE, LittleItems.BIRTHDAY_CAKE
            , LittleItems.COMBINED_DONUT, LittleItems.JELLY_DONUT, LittleItems.POWDERD_DONUT, LittleItems.SUGAR_DONUT
            , LittleItems.BLUEBERRY_PIE, LittleItems.CHEESECAKE_PIE, LittleItems.CHERRY_PIE, LittleItems.CHOCOLATE_PIE, LittleItems.STRAWBERRY_PIE);

    private InventoryMaidMain inventoryMaidMain;
    private InventoryMaidEquipment inventoryMaidEquipment;

    private int animationTick;
    private MaidAnimation animation = NO_ANIMATION;

    public static final MaidAnimation TALK_ANIMATION = MaidAnimation.create(100);
    public static final MaidAnimation PET_ANIMATION = MaidAnimation.create(100);
    public static final MaidAnimation FARM_ANIMATION = MaidAnimation.create(15);
    public static final MaidAnimation EAT_ANIMATION = MaidAnimation.create(14);
    public static final MaidAnimation RUSHING_ANIMATION = MaidAnimation.create(80);

    private static final MaidAnimation[] ANIMATIONS = {
            TALK_ANIMATION,
            PET_ANIMATION,
            FARM_ANIMATION,
            EAT_ANIMATION,
            RUSHING_ANIMATION
    };

    private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Rush speed penalty", 0.28D, AttributeModifier.Operation.ADDITION)).setSaved(false);

    public ModelConfigCompound textureData = new ModelConfigCompound(this);

    private boolean mstatOpenInventory;

    public float entityIdFactor;

    public boolean isModelSaved;

    private int experienceLevel;
    private int experienceTotal;
    public float experience;

    public float prevShadowX, prevShadowY, prevShadowZ;
    public float shadowX, shadowY, shadowZ;
    public float prevShadowX2, prevShadowY2, prevShadowZ2;
    public float shadowX2, shadowY2, shadowZ2;

    private int rotationAttackDuration;
    public int ablityCharge;

    protected static final DataParameter<Float> dataWatch_MaidExpValue = EntityDataManager.createKey(LittleMaidBaseEntity.class, DataSerializers.FLOAT);

    private static final DataParameter<String> TEXTURE_MAIN = EntityDataManager.createKey(LittleMaidBaseEntity.class, DataSerializers.STRING);
    private static final DataParameter<String> TEXTURE_ARMOR = EntityDataManager.createKey(LittleMaidBaseEntity.class, DataSerializers.STRING);

    protected static final DataParameter<MaidData> MAID_DATA = EntityDataManager.createKey(LittleMaidBaseEntity.class, MaidDataSerializers.MAID_DATA);

    protected static final DataParameter<Boolean> FREEDOM = EntityDataManager.createKey(LittleMaidBaseEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> WAITING = EntityDataManager.createKey(LittleMaidBaseEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> RUSHING = EntityDataManager.createKey(LittleMaidBaseEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> ROTATION_ATTACK = EntityDataManager.createKey(LittleMaidBaseEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> GUARD = EntityDataManager.createKey(LittleMaidBaseEntity.class, DataSerializers.BOOLEAN);


    protected static final DataParameter<Boolean> CONTRACT = EntityDataManager.createKey(LittleMaidBaseEntity.class, DataSerializers.BOOLEAN);


    private static final DataParameter<Boolean> DATA_CHARGING_STATE = EntityDataManager.createKey(LittleMaidBaseEntity.class, DataSerializers.BOOLEAN);


    protected static final DataParameter<Byte> dataWatch_Color = EntityDataManager.createKey(LittleMaidBaseEntity.class, DataSerializers.BYTE);

    /**
     * MSB|0x0000 0000|LSB<br>
     * |    |本体のテクスチャインデックス<br>
     * |アーマーのテクスチャインデックス<br>
     */
    /**
     * モデルパーツの表示フラグ(Integer)
     */
    protected int maidContractLimit;        // 契約期間

    @Nullable
    public MaidFishingBobberEntity fishingBobber;

    public LittleMaidBaseEntity(EntityType<? extends LittleMaidBaseEntity> p_i48575_1_, World p_i48575_2_) {
        super(p_i48575_1_, p_i48575_2_);
        if (this.getNavigator() instanceof GroundPathNavigator) {
            ((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
        }
        this.getNavigator().setCanSwim(true);
        this.setCanPickUpLoot(true);
        this.brain = this.createBrain(new Dynamic<>(NBTDynamicOps.INSTANCE, new CompoundNBT()));

        entityIdFactor = getEntityId() * 70;

        this.setPathPriority(PathNodeType.LAVA, -4.0F);
        this.setPathPriority(PathNodeType.DANGER_FIRE, -4.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, -4.0F);
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new MaidGroundPathNavigator(this, worldIn);
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
        this.dataManager.register(dataWatch_MaidExpValue, Float.valueOf(0));
        this.dataManager.register(TEXTURE_MAIN, "default_" + ModelManager.defaultModelName);
        this.dataManager.register(TEXTURE_ARMOR, "default_" + ModelManager.defaultModelName);
        this.dataManager.register(MAID_DATA, new MaidData(MaidJob.WILD, 0));
        this.dataManager.register(FREEDOM, false);
        this.dataManager.register(WAITING, false);
        this.dataManager.register(RUSHING, false);
        this.dataManager.register(ROTATION_ATTACK, false);
        this.dataManager.register(GUARD, false);
        this.dataManager.register(CONTRACT, false);

        this.dataManager.register(DATA_CHARGING_STATE, false);

        this.dataManager.register(dataWatch_Color, (byte) 0xc);
        // 20:選択テクスチャインデックス
        // TODO いらん？
    }

    public Brain<LittleMaidBaseEntity> getBrain() {
        return (Brain<LittleMaidBaseEntity>) super.getBrain();
    }

    protected Brain<?> createBrain(Dynamic<?> p_213364_1_) {
        Brain<LittleMaidBaseEntity> brain = new Brain<>(MEMORY_TYPES, SENSOR_TYPES, p_213364_1_);
        this.initBrain(brain);
        return brain;
    }

    public void resetBrain(ServerWorld p_213770_1_) {
        Brain<LittleMaidBaseEntity> brain = this.getBrain();
        brain.stopAllTasks(p_213770_1_, this);
        this.brain = brain.copy();
        this.initBrain(this.getBrain());
    }

    protected void initBrain(Brain<LittleMaidBaseEntity> p_213744_1_) {
        float f = (float) this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();


        if (this.isTamed() && !this.isMaidWaitEx() && !isFreedom()) {
            p_213744_1_.setSchedule(LittleSchedules.FOLLOW);
            p_213744_1_.registerActivity(LittleActivitys.FOLLOW, MaidTasks.follow(getMaidData().getJob(), f));
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
        p_213744_1_.registerActivity(Activity.MEET, MaidTasks.meet(f), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryModuleStatus.VALUE_PRESENT)));
        p_213744_1_.registerActivity(Activity.WORK, JobTasks.work(getMaidData().getJob(), f), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryModuleStatus.VALUE_PRESENT)));
        p_213744_1_.registerActivity(Activity.REST, MaidTasks.rest(f));
        p_213744_1_.registerActivity(Activity.IDLE, MaidTasks.idle(f));
        p_213744_1_.registerActivity(Activity.PANIC, MaidTasks.panic(f));
        p_213744_1_.registerActivity(LittleActivitys.LUMBERJACK, MaidTasks.cutWood(f));
        p_213744_1_.registerActivity(LittleActivitys.ATTACK, MaidTasks.attack(f));
        p_213744_1_.registerActivity(LittleActivitys.SHOT, MaidTasks.shot(f));
        p_213744_1_.registerActivity(LittleActivitys.DUAL_BLADER, MaidTasks.doubleSwordAttack(f));
        p_213744_1_.registerActivity(LittleActivitys.SHIELDER, MaidTasks.shieldAttack(f));
        p_213744_1_.registerActivity(LittleActivitys.SIMPLE_CASTER, MaidTasks.magicAttack(f));
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

        compound.putInt("LimitCount", maidContractLimit);

        compound.putByte("ColorB", getColor());
        compound.putString("texName", textureData.getTextureName(0));
        compound.putString("texArmor", textureData.getTextureName(1));

        compound.putString("textureModelNameForClient", getModelNameMain());

        compound.putString("textureArmorNameForClient", getModelNameArmor());
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
        this.dataManager.set(dataWatch_MaidExpValue, experience);

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

        setTextureNameMain(compound.getString("textureModelNameForClient"));
        setTextureNameArmor(compound.getString("textureArmorNameForClient"));

        if (compound.contains("Color")) {
            setColor((byte) compound.getInt("Color"));
            compound.remove("Color");
        } else {
            setColor(compound.getByte("ColorB"));
        }

        refreshModels();
        //


        this.setGrowingAge(Math.max(0, this.getGrowingAge()));
        this.setCanPickUpLoot(true);
        if (this.world instanceof ServerWorld) {
            this.resetBrain((ServerWorld) this.world);
        }
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

        dataManager.set(dataWatch_MaidExpValue, experience);
        this.setMaidData(getMaidData().withLevel(this.experienceLevel));
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
            this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, this.getSoundCategory(), f * 0.75F, 1.0F);
            this.lastXPSound = this.ticksExisted;
        }*/
        this.setMaidData(getMaidData().withLevel(this.experienceLevel));
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

    @OnlyIn(Dist.CLIENT)
    public float getXp() {
        return this.experience;
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
        if (!isTamed()) {
            InventoryHelper.dropInventoryItems(world, this, this.getInventoryMaidMain());
            InventoryHelper.dropInventoryItems(world, this, this.getInventoryMaidEquipment());
        }

        if (!this.world.isRemote && isContract()) {
            SugarPhantomEntity phantomEntity = LittleEntitys.SUGAR_PHANTOM.create(world);
            phantomEntity.setNoAI(this.isAIDisabled());
            CompoundNBT nbt = this.writeWithoutTypeId(new CompoundNBT());
            phantomEntity.setPhantom(this.getOwnerId(), this.getType(), nbt);
            phantomEntity.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
            phantomEntity.setMotion(0, 0, 0);
            phantomEntity.maidContractLimit = 24000 * 7;
            phantomEntity.setTamed(true);
            phantomEntity.setMaidWait(false);
            phantomEntity.setHealth(phantomEntity.getMaxHealth());
            phantomEntity.dead = false;

            TextureBoxBase mainModel = modelBoxAutoSelect(getModelNameMain());

            TextureBoxBase armorModel = modelBoxAutoSelect(getModelNameArmor());

            if (mainModel != null && armorModel != null) {
                phantomEntity.setTextureBox(new TextureBoxBase[]{mainModel, armorModel});
            }
            phantomEntity.isModelSaved = false;

            if (this.hasCustomName()) {
                phantomEntity.setCustomName(this.getCustomName());
                phantomEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            this.world.addEntity(phantomEntity);
        }

        super.onDeath(cause);
    }


    public void func_213742_a(MemoryModuleType<GlobalPos> p_213742_1_) {
        if (this.world instanceof ServerWorld) {
            MinecraftServer minecraftserver = ((ServerWorld) this.world).getServer();
            this.brain.getMemory(p_213742_1_).ifPresent((p_213752_3_) -> {
                ServerWorld serverworld = minecraftserver.getWorld(p_213752_3_.getDimension());
                PointOfInterestManager pointofinterestmanager = serverworld.getPointOfInterestManager();
                Optional<PointOfInterestType> optional = pointofinterestmanager.func_219148_c(p_213752_3_.getPos());
                BiPredicate<LittleMaidBaseEntity, PointOfInterestType> bipredicate = field_213774_bB.get(p_213742_1_);
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


    @Override
    public boolean canDespawn(double p_213397_1_) {
        return false;
    }

    @Override
    public byte getColor() {
        return dataManager.get(LittleMaidBaseEntity.dataWatch_Color);
    }

    @Override
    public void setColor(byte index) {
        textureData.setColor(index);

        dataManager.set(LittleMaidBaseEntity.dataWatch_Color, index);
    }

    private void eatSweets(boolean recontract) {
        ItemStack itemstack = findFood();

        if (!itemstack.isEmpty()) {
            if (itemstack.isFood()) {
                Item itemfood = (Item) itemstack.getItem();
                this.heal((float) itemfood.getFood().getHealing());
                if (!this.world.isRemote) {
                    itemstack.shrink(1);
                }
                addContractLimit(recontract);
                MaidPacketHandler.animationModel(this, EAT_ANIMATION);

                this.playSound(SoundEvents.ENTITY_GENERIC_EAT, this.getSoundVolume(), this.getSoundPitch());
            } else if (itemstack.getItem() == Items.SUGAR) {
                this.heal(1);
                if (!this.world.isRemote) {
                    itemstack.shrink(1);
                }
                addContractLimit(recontract);
                MaidPacketHandler.animationModel(this, EAT_ANIMATION);
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

        //Charge one by one to use the ability
        this.ablityCharge = this.ablityCharge + 1;


        return flag;
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {
        super.onCollideWithPlayer(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (amount > 0.0F && canBlockDamageSource(source)) {
            this.damageShield(amount);
            this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 1.0F);
            return false;
        }

        if (amount > 0.0F && canRushingOrGuardDamageSource(source)) {
            this.damageSword(amount);
            this.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1.0F, 1.45F);
            return false;
        }

        return super.attackEntityFrom(source, amount);
    }

    private boolean canBlockDamageSource(DamageSource damageSourceIn) {
        Entity entity = damageSourceIn.getImmediateSource();
        boolean flag = false;
        if (entity instanceof AbstractArrowEntity) {
            AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity) entity;
            if (abstractarrowentity.getPierceLevel() > 0) {
                flag = true;
            }
        }

        if (!damageSourceIn.isUnblockable() && this.isActiveItemStackBlocking() && !flag) {
            Vec3d vec3d2 = damageSourceIn.getDamageLocation();
            if (this.getMaidData().getJob() == MaidJob.SHIELDER) {
                return true;
            }
            if (vec3d2 != null) {
                Vec3d vec3d = this.getLook(1.0F);
                Vec3d vec3d1 = vec3d2.subtractReverse(this.getPositionVec()).normalize();
                vec3d1 = new Vec3d(vec3d1.x, 0.0D, vec3d1.z);
                if (vec3d1.dotProduct(vec3d) < 0.0D) {
                    return true;
                }
            }

        }

        return false;
    }

    protected void damageSword(float damage) {
        if (damage >= 2.0F && this.isRushing() && this.getHeldItem(Hand.OFF_HAND).getItem() instanceof SwordItem) {
            int i = 1 + MathHelper.floor(damage);
            Hand hand = this.getActiveHand();
            this.getHeldItem(Hand.OFF_HAND).damageItem(i, this, (p_213833_1_) -> {
                p_213833_1_.sendBreakAnimation(hand);
            });

            this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);

        }
    }

    protected void damageShield(float damage) {
        if (damage >= 3.0F && this.activeItemStack.isShield(this)) {
            int i = 1 + MathHelper.floor(damage);
            Hand hand = this.getActiveHand();
            this.activeItemStack.damageItem(i, this, (p_213833_1_) -> {
                p_213833_1_.sendBreakAnimation(hand);
            });
            if (this.activeItemStack.isEmpty()) {
                if (hand == Hand.MAIN_HAND) {
                    this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
                } else {
                    this.setItemStackToSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
                }

                this.activeItemStack = ItemStack.EMPTY;
                this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
            }
        }
    }

    private boolean canRushingOrGuardDamageSource(DamageSource damageSourceIn) {
        Entity entity = damageSourceIn.getImmediateSource();
        boolean flag = false;
        if (entity instanceof AbstractArrowEntity) {
            AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity) entity;
            if (abstractarrowentity.getPierceLevel() > 0) {
                flag = true;
            }
        }

        if (!damageSourceIn.isUnblockable() && (this.isGuard() || this.isRushing()) && !flag) {
            Vec3d vec3d2 = damageSourceIn.getDamageLocation();
            if (vec3d2 != null) {
                Vec3d vec3d = this.getLook(1.0F);
                Vec3d vec3d1 = vec3d2.subtractReverse(this.getPositionVec()).normalize();
                vec3d1 = new Vec3d(vec3d1.x, 0.0D, vec3d1.z);
                if (vec3d1.dotProduct(vec3d) < 0.0D) {
                    return true;
                }
            }
        }

        return false;
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

        this.updateAttackAblityTick();
    }

    private void updateAttackAblityTick() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();
        if (this.rotationAttackDuration > 0) {
            --this.rotationAttackDuration;
            this.updateRotationAttack(axisalignedbb, this.getBoundingBox());
        }

        if (isRushing() && Entity.horizontalMag(this.getMotion()) > 0.05D) {
            this.updateRushAttack(axisalignedbb, this.getBoundingBox());
        }
    }

    private void updateRushAttack(AxisAlignedBB p_204801_1_, AxisAlignedBB p_204801_2_) {
        Vec3d vec3d = this.getLook(1.0F);
        AxisAlignedBB axisalignedbb = p_204801_1_.union(p_204801_2_);
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb.expand(0.25F, 0.0F, 0.25F).offset(vec3d.x * 0.25D, 0.0F, vec3d.z * 0.25D));
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                Entity entity = list.get(i);
                float d1 = (float) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
                float d2 = (float) this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).getValue();

                d1 += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), this.getCreatureAttribute());

                if (entity instanceof LivingEntity && this.getOwner() != entity && !(entity instanceof LittleMaidBaseEntity)) {
                    if (entity.attackEntityFrom(LittleDamageSource.causeRushingDamage(this), (float) (d1 * 1.25F))) {
                        ((LivingEntity) entity).knockBack(this, d2 + 0.4F, (double) MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F))));
                        entity.setMotion(entity.getMotion().add(0.0D, (double) 0.25F, 0.0D));
                        this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1.0F, 1.0F);
                       /* this.getHeldItem(Hand.MAIN_HAND).damageItem(1, this, (p_213625_1_) -> {
                            p_213625_1_.sendBreakAnimation(Hand.MAIN_HAND);
                        });*/
                        this.getHeldItem(Hand.OFF_HAND).damageItem(1, this, (p_213625_1_) -> {
                            p_213625_1_.sendBreakAnimation(Hand.OFF_HAND);
                        });
                        giveExperiencePoints(3 + this.getRNG().nextInt(3));
                    }
                }
            }
        }
    }

    private void updateRotationAttack(AxisAlignedBB p_204801_1_, AxisAlignedBB p_204801_2_) {
        AxisAlignedBB axisalignedbb = p_204801_1_.union(p_204801_2_);
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb.expand(2.0F, 0.0F, 2.0F));
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                Entity entity = list.get(i);
                double d1 = this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
                if (entity instanceof LivingEntity && this.getOwner() != entity && !(entity instanceof LittleMaidBaseEntity)) {
                    if (entity.attackEntityFrom(LittleDamageSource.causeRotationAttackDamage(this), (float) (d1 + 3.0F))) {
                        ((LivingEntity) entity).knockBack(this, 0.6F, (double) MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F))));
                        entity.setMotion(entity.getMotion().add(0.0D, (double) 0.2F, 0.0D));
                        this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1.0F, 1.0F);
                        this.getHeldItem(Hand.MAIN_HAND).damageItem(1, this, (p_213625_1_) -> {
                            p_213625_1_.sendBreakAnimation(Hand.MAIN_HAND);
                        });
                        this.getHeldItem(Hand.OFF_HAND).damageItem(1, this, (p_213625_1_) -> {
                            p_213625_1_.sendBreakAnimation(Hand.OFF_HAND);
                        });
                        giveExperiencePoints(4 + this.getRNG().nextInt(3));
                    }
                }
            }
        }

        if (!this.world.isRemote && this.rotationAttackDuration <= 0) {
            this.setRotationAttack(false);
        }
    }

    @Override
    public void tick() {

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
            lupd |= updateTexturePack();
            if (lupd) {

                setTextureNames();
            }
/*
            setMaidMode(dataManager.get(EntityLittleMaid.dataWatch_Mode));
            setDominantArm(dataManager.get(EntityLittleMaid.dataWatch_DominamtArm));
            updateMaidFlagsClient();
            updateGotcha();
*/


            // メイド経験値
            if (ticksExisted % 10 == 0) {
                experience = dataManager.get(dataWatch_MaidExpValue);
            }
/*
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
*/

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

        if (!isModelSaved) {
            isModelSaved = true;
            if (this.world.isRemote()) {
                setTextureNames();
            }
        }

        super.tick();

        this.updatePose();
        this.updateShadow();

    }

    private void updateShadow() {

        double elasticity = 0.3;

        this.prevShadowX = this.shadowX;
        this.prevShadowY = this.shadowY;
        this.prevShadowZ = this.shadowZ;


        this.prevShadowX2 = this.shadowX2;
        this.prevShadowY2 = this.shadowY2;
        this.prevShadowZ2 = this.shadowZ2;

        this.shadowX += (this.getPosX() - this.shadowX) * elasticity;
        this.shadowY += (this.getPosY() - this.shadowY) * elasticity;
        this.shadowZ += (this.getPosZ() - this.shadowZ) * elasticity;

        this.shadowX2 += (this.shadowX - this.shadowX2) * elasticity;
        this.shadowY2 += (this.shadowY - this.shadowY2) * elasticity;
        this.shadowZ2 += (this.shadowZ - this.shadowZ2) * elasticity;
    }

    /**
     * テクスチャパックの更新を確認
     *
     * @return
     */
    public boolean updateTexturePack() {

        boolean lflag = false;
        String ltexture = dataManager.get(TEXTURE_MAIN);
        String larmor = dataManager.get(TEXTURE_ARMOR);

        TextureBoxBase mainModel = modelBoxAutoSelect(ltexture);

        TextureBoxBase armorModel = modelBoxAutoSelect(larmor);
        if (!textureData.getTextureName(0).equals(ltexture)) {
            lflag = true;
        }
        if (!textureData.getTextureName(0).equals(ltexture)) {
            lflag = true;
        }
        if (lflag) {
            setTextureBox(new TextureBoxBase[]{mainModel, armorModel});
        }
        return lflag;
    }

    protected void updatePose() {
        if (this.isPoseClear(Pose.SWIMMING)) {
            Pose pose;
            if (this.isSleeping()) {
                pose = Pose.SLEEPING;
            } else if (this.isSwimming()) {
                pose = Pose.SWIMMING;
            } else if (this.isSpinAttacking()) {
                pose = Pose.SPIN_ATTACK;
            } else {
                pose = Pose.STANDING;
            }

            Pose pose1;
            if (!this.isSpectator() && !this.isPassenger() && !this.isPoseClear(pose)) {
                pose1 = Pose.SWIMMING;
            } else {
                pose1 = pose;
            }

            this.setPose(pose1);
        }
    }

  /*  public void travel(Vec3d p_213352_1_) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(0.01F, p_213352_1_);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
        } else {
            super.travel(p_213352_1_);
        }

    }

    public void updateSwimming() {
        if (!this.world.isRemote) {
            if (this.isServerWorld() && this.isInWater()) {
                this.setSwimming(true);
            } else {
                this.setSwimming(false);
            }
        }
    }*/

    public boolean isShooting() {
        return isAggressive() && getHeldItem(Hand.MAIN_HAND).getItem() instanceof BowItem;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        onSpawnWithEgg();
        refreshModels();

        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }


    public void onSpawnWithEgg() {
        // テクスチャーをランダムで選択
        String ls;
        /*if (LMRConfig.cfg_isFixedWildMaid) {
            ls = "default_Orign";
        } else {*/
        ls = ModelManager.instance.getRandomTextureString(this, rand);
        //}
        textureData.setTextureInitServer(ls);
        MaidModRedo.LOGGER.debug("init-ID:%d, %s:%d", getEntityId(), textureData.textureBox[0].textureName, textureData.getColor());
//		setTexturePackIndex(textureData.getColor(), textureData.textureIndex);
        setTextureNameMain(textureData.textureBox[0].textureName);
        setTextureNameArmor(textureData.textureBox[1].textureName);
        setTextureNames();
//		recallRenderParamTextureName(textureModelNameForClient, textureArmorNameForClient);
        if (!isContract()) {
            //setMaidMode(EntityMode_Basic.mmode_Wild);
            //onSpawnWild();
        }
    }

    private void onSpawnWild() {
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

    }

    public boolean isOpenInventory() {
        return mstatOpenInventory;
    }

    public boolean isFreedom() {
        return this.dataManager.get(FREEDOM);
    }

    public void setFreedom(boolean pflag) {
        this.dataManager.set(FREEDOM, pflag);
        if (!world.isRemote()) {
            this.resetBrain((ServerWorld) this.world);
        }
    }


    public void setMaidWait(boolean pflag) {
        this.dataManager.set(WAITING, pflag);
        isJumping = false;
        setAttackTarget(null);
        setRevengeTarget(null);
        getNavigator().clearPath();
        if (pflag) {

            getNavigator().clearPath();
        }
        if (!world.isRemote()) {
            this.resetBrain((ServerWorld) this.world);
        }
        velocityChanged = true;
    }

    public boolean isRushing() {
        return this.dataManager.get(RUSHING);
    }

    public void setRushing(boolean pflag) {
        IAttributeInstance iattributeinstance = this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        this.dataManager.set(RUSHING, pflag);
        if (pflag) {
            MaidPacketHandler.animationModel(this, LittleMaidBaseEntity.RUSHING_ANIMATION);
            iattributeinstance.removeModifier(MODIFIER);
            iattributeinstance.applyModifier(MODIFIER);
        } else {
            iattributeinstance.removeModifier(MODIFIER);
        }
        this.shadowX = (float) this.getPosX();
        this.shadowY = (float) this.getPosY();
        this.shadowZ = (float) this.getPosZ();
        this.shadowX2 = (float) this.getPosX();
        this.shadowY2 = (float) this.getPosY();
        this.shadowZ2 = (float) this.getPosZ();

    }

    public boolean isGuard() {
        return this.dataManager.get(GUARD);
    }

    public void setGuard(boolean pflag) {
        this.dataManager.set(GUARD, pflag);
    }

    public boolean isRotationAttack() {
        return this.dataManager.get(ROTATION_ATTACK);
    }

    public void startRotationAttack(int duration) {
        this.rotationAttackDuration = duration;
        if (!this.world.isRemote) {
            this.setRotationAttack(true);
        }
    }

    public void setRotationAttack(boolean pflag) {
        this.dataManager.set(ROTATION_ATTACK, pflag);
        this.shadowX = (float) this.getPosX();
        this.shadowY = (float) this.getPosY();
        this.shadowZ = (float) this.getPosZ();
        this.shadowX2 = (float) this.getPosX();
        this.shadowY2 = (float) this.getPosY();
        this.shadowZ2 = (float) this.getPosZ();
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

                getEntityWorld().addParticle(ParticleTypes.NOTE, getPosX(), getPosY() + getHeight() + 0.1D, getPosZ(), d6, d7, d8);
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
            this.world.addParticle(particleData, this.getPosX() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), this.getPosY() + 1.0D + (double) (this.rand.nextFloat() * this.getHeight()), this.getPosZ() + (double) (this.rand.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(), d0, d1, d2);
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
                } else {
                    return processInteract(player, hand);
                }
            } else {

                if (!itemstack.isEmpty() && SWEETITEM.contains(item) && this.getHealth() < this.getMaxHealth()) {
                    if (item.isFood()) {
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }

                        this.addContractLimit(false);
                        this.heal((float) item.getFood().getHealing());
                        MaidPacketHandler.animationModel(this, EAT_ANIMATION);
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

                /*if (this.isOwner(player) && player.() && itemstack.isEmpty() && getAnimation() != PET_ANIMATION) {
                    MaidPacketHandler.animationModel(this, PET_ANIMATION);

                    return true;
                } else*/
                if (this.isOwner(player) && !item.itemInteractionForEntity(itemstack, player, this, hand)) {
                    if (player instanceof ServerPlayerEntity && !(player instanceof FakePlayer)) {
                        if (!player.world.isRemote) {
                            ServerPlayerEntity entityPlayerMP = (ServerPlayerEntity) player;
                            NetworkHooks.openGui(entityPlayerMP, new INamedContainerProvider() {
                                @Override
                                public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
                                    return new MaidInventoryContainer(windowId, inventory, (LittleMaidBaseEntity) player.world.getEntityByID(getEntityId()));
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

            maidContractLimit = (24000 * 7);

            if (!this.world.isRemote) {
                if (!net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setMaidData(this.getMaidData().withJob(MaidJob.NORMAL));
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity) null);
                    this.setMaidWait(true);
                    this.setFreedom(false);
                    this.setHealth(this.getMaxHealth());
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

    //TODO
    public void setTexturePackName(TextureBox[] pTextureBox) {
        // Client
        textureData.setTexturePackName(pTextureBox);
        setTextureNames();
        MaidModRedo.LOGGER.debug("ID:%d, TextureModel:%s", getEntityId(), textureData.getTextureName(0));

        // モデルの初期化

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

        this.dataManager.set(TEXTURE_MAIN, textureData.getTextureName(0));
        this.dataManager.set(TEXTURE_ARMOR, textureData.getTextureName(1));

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
        return this.dataManager.get(TEXTURE_MAIN);
    }

    public String getModelNameArmor() {
        return this.dataManager.get(TEXTURE_ARMOR);
    }


    public void setTextureNameMain(String modelNameMain) {
        this.dataManager.set(TEXTURE_MAIN, modelNameMain);

        refreshModels();
    }


    public void setTextureNameArmor(String modelNameArmor) {
        this.dataManager.set(TEXTURE_ARMOR, modelNameArmor);

        refreshModels();
    }


    public void refreshModels() {
        String defName = ModelManager.instance.getRandomTextureString(this, rand);

        TextureBoxBase mainModel = modelBoxAutoSelect(getModelNameMain());

        if (mainModel == null) {
            mainModel = modelBoxAutoSelect(defName);
        }

        TextureBoxBase armorModel = modelBoxAutoSelect(getModelNameArmor());

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


    public void resetPlayingAnimationToDefault() {
        this.setAnimation(IMaidAnimation.NO_ANIMATION);
    }

    protected void onAnimationFinish(MaidAnimation animation) {
        if (animation == LittleMaidBaseEntity.RUSHING_ANIMATION) {
            setRushing(false);
        }
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
            double d0 = target.getPosX() - this.getPosX();
            double d1 = target.getBoundingBox().minY + (double) (target.getHeight() / 3.0F) - abstractarrowentity.getPosY();
            double d2 = target.getPosZ() - this.getPosZ();
            double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
            abstractarrowentity.shoot(d0, d1 + d3 * (double) 0.2F, d2, distanceFactor * 1.75F, (float) (1.0F));
            this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
            this.world.addEntity(abstractarrowentity);
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
        double d0 = p_213670_1_.getPosX() - this.getPosX();
        double d1 = p_213670_1_.getPosZ() - this.getPosZ();
        double d2 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1);
        double d3 = p_213670_1_.getBoundingBox().minY + (double) (p_213670_1_.getHeight() / 3.0F) - entity.getPosY() + d2 * (double) 0.2F;
        Vector3f vector3f = this.func_213673_a(new Vec3d(d0, d3, d1), p_213670_4_);
        p_213670_3_.shoot((double) vector3f.getX(), (double) vector3f.getY(), (double) vector3f.getZ(), 1.6F, (float) (14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
    }

    private Vector3f func_213673_a(Vec3d p_213673_1_, float p_213673_2_) {
        Vec3d vec3d = p_213673_1_.normalize();
        Vec3d vec3d1 = vec3d.crossProduct(new Vec3d(0.0D, 1.0D, 0.0D));
        if (vec3d1.lengthSquared() <= 1.0E-7D) {
            vec3d1 = vec3d.crossProduct(this.getUpVector(1.0F));
        }

        Quaternion quaternion = new Quaternion(new Vector3f(vec3d1), 90.0F, true);
        Vector3f vector3f = new Vector3f(vec3d);
        vector3f.transform(quaternion);
        Quaternion quaternion1 = new Quaternion(vector3f, p_213673_2_, true);
        Vector3f vector3f1 = new Vector3f(vec3d);
        vector3f1.transform(quaternion1);
        return vector3f1;
    }

    public ItemStack findAmmo(ItemStack shootable) {
        if (shootable.getItem() instanceof ShootableItem) {
            Predicate<ItemStack> predicate = ((ShootableItem) shootable.getItem()).getAmmoPredicate();
            ItemStack itemstack = ShootableItem.getHeldAmmo(this, predicate);
            return itemstack.isEmpty() ? new ItemStack(Items.ARROW) : itemstack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        if (poseIn == Pose.STANDING) {
            return sizeIn.height * 0.75F;
        } else {
            return super.getStandingEyeHeight(poseIn, sizeIn);
        }
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return LittleCreatureAttribute.MAID;
    }

    /*@Override
    public EntitySize getSize(Pose poseIn) {
        if(poseIn == Pose.STANDING && this.getTextureBox()[0] != null && this.getTextureBox()[0].getWidth() != 0 && this.getTextureBox()[0].getHeight() != 0){
            return EntitySize.flexible(this.getTextureBox()[0].getWidth(),this.getTextureBox()[0].getHeight()).scale(this.getRenderScale());
        }else {
            return poseIn == Pose.SLEEPING ? SLEEPING_SIZE : super.getSize(poseIn);
        }
    }*/
}
