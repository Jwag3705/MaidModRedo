package mmr.maidmodredo.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.client.maidmodel.*;
import mmr.maidmodredo.entity.data.MaidData;
import mmr.maidmodredo.entity.data.MaidJob;
import mmr.maidmodredo.entity.tasks.MaidTasks;
import mmr.maidmodredo.init.LittleActivitys;
import mmr.maidmodredo.init.LittleSchedules;
import mmr.maidmodredo.init.LittleSensorTypes;
import mmr.maidmodredo.init.MaidDataSerializers;
import mmr.maidmodredo.utils.Counter;
import mmr.maidmodredo.utils.EntityCaps;
import mmr.maidmodredo.utils.ModelManager;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;

public class LittleMaidEntity extends TameableEntity implements IModelCaps, IModelEntity {
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.MEETING_POINT, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.INTERACTABLE_DOORS, MemoryModuleType.field_225462_q, MemoryModuleType.NEAREST_BED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.HIDING_PLACE, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LAST_SLEPT, MemoryModuleType.LAST_WORKED_AT_POI);
    private static final ImmutableList<SensorType<? extends Sensor<? super LittleMaidEntity>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.INTERACTABLE_DOORS, SensorType.NEAREST_BED, SensorType.HURT_BY, LittleSensorTypes.MAID_HOSTILES, LittleSensorTypes.DEFEND_OWNER);
    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<LittleMaidEntity, PointOfInterestType>> field_213774_bB = ImmutableMap.of(MemoryModuleType.HOME, (p_213769_0_, p_213769_1_) -> {
        return p_213769_1_ == PointOfInterestType.HOME;
    }, MemoryModuleType.MEETING_POINT, (p_213772_0_, p_213772_1_) -> {
        return p_213772_1_ == PointOfInterestType.MEETING;
    });
    private static final Set<Item> SWEETITEM = Sets.newHashSet(Items.SUGAR, Items.COOKIE, Items.PUMPKIN_PIE);


    public ModelConfigCompound textureData;
    public EntityCaps maidCaps;

    public float entityIdFactor;

    protected String textureNameMain;
    protected String textureNameArmor;


    protected Counter maidOverDriveTime;
    protected Counter workingCount;


    private static final DataParameter<MaidData> MAID_DATA = EntityDataManager.createKey(LittleMaidEntity.class, MaidDataSerializers.MAID_DATA);
    protected static final DataParameter<Boolean> WAITING = EntityDataManager.createKey(LittleMaidEntity.class, DataSerializers.BOOLEAN);

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
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(MAID_DATA, new MaidData(MaidJob.ESCORT, 1));
        this.dataManager.register(WAITING, false);

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


        if (this.isTamed() && !this.isMaidWait()) {
            p_213744_1_.setSchedule(LittleSchedules.FOLLOW);
            p_213744_1_.registerActivity(LittleActivitys.FOLLOW, MaidTasks.follow(f));
        } else if (this.isTamed() && this.isMaidWait()) {
            p_213744_1_.setSchedule(LittleSchedules.WAITING);
            p_213744_1_.registerActivity(LittleActivitys.WAITING, MaidTasks.waiting(f));
        } else {
            if (getMaidData().getJob().getSchedule() != null) {
                p_213744_1_.setSchedule(getMaidData().getJob().getSchedule());
            }

            if (getMaidData().getJob().getActivity() != null) {
                p_213744_1_.registerActivity(getMaidData().getJob().getActivity(), getMaidData().getJob().getTasks());
            }
        }


        p_213744_1_.registerActivity(Activity.CORE, MaidTasks.core(f));
        p_213744_1_.registerActivity(Activity.REST, MaidTasks.rest(f));
        p_213744_1_.registerActivity(Activity.IDLE, MaidTasks.idle(f));
        p_213744_1_.registerActivity(Activity.PANIC, MaidTasks.panic(f));
        p_213744_1_.registerActivity(LittleActivitys.ATTACK, MaidTasks.attack(f));
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

        compound.put("MaidData", this.getMaidData().serialize(NBTDynamicOps.INSTANCE));

        compound.putBoolean("Wait", isMaidWait());

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

        if (compound.contains("MaidData", 10)) {
            this.setMaidData(new MaidData(new Dynamic<>(NBTDynamicOps.INSTANCE, compound.get("MaidData"))));
        }

        setMaidWait(compound.getBoolean("Wait"));

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

        this.setGrowingAge(Math.max(0, this.getGrowingAge()));

        this.setCanPickUpLoot(true);
        this.resetBrain((ServerWorld) this.world);
    }

    public void setMaidData(MaidData p_213753_1_) {
        MaidData MaidData = this.getMaidData();
        if (MaidData.getJob() != p_213753_1_.getJob()) {
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

    public static boolean canCombat(LittleMaidEntity entity) {
        if (entity.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SwordItem || entity.getHeldItem(Hand.OFF_HAND).getItem() instanceof SwordItem) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canDespawn(double p_213397_1_) {
        return false;
    }

    @Override
    public byte getColor() {
        return textureData.getColor();
        //return dataManager.get(LittleMaidEntity.dataWatch_Color);
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

    @Override
    public void tick() {
        if (registerTick.isDelay()) {
            registerTick.onUpdate();

            if (!registerTick.isEnable() && registerTick.getValue() == 0 && !getEntityWorld().isRemote) {
                //getMaidMasterEntity().sendMessage(new TextComponentTranslation("littleMaidMob.chat.text.cancelregistration").setStyle(new Style().setColor(TextFormatting.DARK_RED)));
            }
        }

        textureData.onUpdate();

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

        }

        super.tick();
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        onSpawnWithEgg();

        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
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
    public boolean isTamed() {
        return isContract();
    }

    public boolean isContract() {
        return super.isTamed();
    }

    public boolean isMaidWait() {
        return this.dataManager.get(WAITING);
    }

    public boolean isMaidWaitEx() {

        return isMaidWait();

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
        this.resetBrain((ServerWorld) this.world);
        velocityChanged = true;
    }

   /* public boolean isContractEX() {

        return isContract() && isRemainsContract();
    }*/

    @Override
    public void setTamed(boolean tamed) {
        setContract(tamed);

        if (tamed) {
            this.setMaidData(this.getMaidData().withJob(MaidJob.ESCORT));
        } else {
            this.setMaidData(this.getMaidData().withJob(MaidJob.WILD));
        }
    }

    @Override
    public void setContract(boolean flag) {
        super.setTamed(flag);

        textureData.setContract(flag);
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
        super.setItemStackToSlot(slotIn, stack);

        MaidJob.MAID_JOB_REGISTRY.stream().filter((p_220389_1_) -> {
            return p_220389_1_.getRequireItem().test(stack);
        }).findFirst().ifPresent((p_220388_2_) -> {
            this.setMaidData(this.getMaidData().withJob(p_220388_2_));
            this.resetBrain((ServerWorld) this.world);
        });
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (this.isTamed()) {
            if (!itemstack.isEmpty()) {

                if (SWEETITEM.contains(item) && this.getHealth() < this.getMaxHealth()) {
                    if (item.isFood()) {
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }

                        this.heal((float) item.getFood().getHealing());
                        this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, 0.7F);
                        return true;
                    }
                } else if (item instanceof DyeItem) {
                    DyeColor dyecolor = ((DyeItem) item).getDyeColor();
                    if (dyecolor.getId() != 15 - this.getColor()) {
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }
                        this.setColor((byte) (15 - dyecolor.getId()));

                        return true;
                    }
                }
            }

            if (this.isOwner(player) && !this.world.isRemote && item == Items.SUGAR) {
                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }

                this.heal(1.0F);

                this.setMaidWait(!this.isMaidWait());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget((LivingEntity) null);
                this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 1.0F, 0.7F);
            }
        } else if (item == Items.CAKE) {
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote) {
                if (!net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity) null);
                    this.setMaidWait(true);
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
}
