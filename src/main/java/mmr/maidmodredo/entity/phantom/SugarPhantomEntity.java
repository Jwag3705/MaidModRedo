package mmr.maidmodredo.entity.phantom;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.Dynamic;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.entity.ai.PhantomFollowOwnerGoal;
import mmr.maidmodredo.init.LittleEntitys;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class SugarPhantomEntity extends LittleMaidBaseEntity {
    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(SugarPhantomEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    protected static final DataParameter<CompoundNBT> MAID_PHANTOM_DATA = EntityDataManager.createKey(SugarPhantomEntity.class, DataSerializers.COMPOUND_NBT);

    protected EntityType<?> phantomEntity;

    private int respawnTime = 1200 + this.rand.nextInt(2400);

    public SugarPhantomEntity(EntityType<? extends SugarPhantomEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new FlyingMovementController(this, 20, true);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PhantomFollowOwnerGoal(this, 1.2F, 4.0F, 11.0F, true));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
    }


    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue((double) 0.6F);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.3F);
    }

    protected Brain<?> createBrain(Dynamic<?> dynamicIn) {
        return new Brain<>(ImmutableList.of(), ImmutableList.of(), dynamicIn);
    }

    protected void initBrain(Brain<LittleMaidBaseEntity> p_213744_1_) {

    }

    protected PathNavigator createNavigator(World worldIn) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanSwim(false);
        flyingpathnavigator.setCanEnterDoors(true);
        return flyingpathnavigator;
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
        this.dataManager.register(MAID_PHANTOM_DATA, new CompoundNBT());
    }

    public void livingTick() {
        super.livingTick();
        if (--this.respawnTime <= 0 && getSugarPhantomData() != null && this.phantomEntity != null && !this.world.isRemote) {
            this.handleRespawn();
        }

        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.ENCHANT, this.getPosXRandom(0.5D), this.getPosYRandom() - 0.25D, this.getPosZRandom(0.5D), (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
    }

    private void handleRespawn() {
        LittleMaidBaseEntity maidEntity = (LittleMaidBaseEntity) this.phantomEntity.create(world);
        maidEntity.setNoAI(this.isAIDisabled());
        maidEntity.read(this.getSugarPhantomData());
        maidEntity.setHealth(maidEntity.getMaxHealth());
        maidEntity.fallDistance = 0F;
        maidEntity.extinguish();
        maidEntity.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 1200, 1));
        maidEntity.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 200, 0));
        if (this.getOwner() != null && this.getOwner().isAlive()) {
            maidEntity.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
            maidEntity.attemptTeleport(this.getOwner().getPosX(), this.getOwner().getPosY(), this.getOwner().getPosZ(), true);
        } else {
            maidEntity.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
        }
        if (this.hasCustomName()) {
            maidEntity.setCustomName(this.getCustomName());
            maidEntity.setCustomNameVisible(this.isCustomNameVisible());
        }
        this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 2.0F, 1.0F);
        this.world.addEntity(maidEntity);
        this.remove();
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("RespawnTime", this.respawnTime);
        compound.put("SugarPhantomData", this.getSugarPhantomData());
        if (this.phantomEntity != null) {
            compound.putString("PhantomEntityType", Registry.ENTITY_TYPE.getKey(this.phantomEntity).toString());
        }
        if (this.getOwnerId() == null) {
            compound.putString("OwnerUUID", "");
        } else {
            compound.putString("OwnerUUID", this.getOwnerId().toString());
        }
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("RespawnTime", 99)) {
            this.respawnTime = compound.getInt("RespawnTime");
        }
        if (compound.contains("PhantomEntityType", 99)) {
            this.phantomEntity = Registry.ENTITY_TYPE.getValue(ResourceLocation.tryCreate(compound.getString("PhantomEntityType"))).orElse(LittleEntitys.LITTLEMAID);
        }

        this.setSugarPhantomData(compound.getCompound("SugarPhantomData"));

        String s;
        if (compound.contains("OwnerUUID", 8)) {
            s = compound.getString("OwnerUUID");
        } else {
            String s1 = compound.getString("Owner");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
        }
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_BEACON_AMBIENT;
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerByUuid(uuid);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public CompoundNBT getSugarPhantomData() {
        return this.dataManager.get(MAID_PHANTOM_DATA);
    }

    public void setSugarPhantomData(CompoundNBT compoundNBT) {
        this.dataManager.set(MAID_PHANTOM_DATA, compoundNBT);
    }

    @Nullable
    public UUID getOwnerId() {
        return this.dataManager.get(OWNER_UNIQUE_ID).orElse((UUID) null);
    }

    public void setOwnerId(@Nullable UUID uuid) {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(uuid));
    }

    public void setPhantom(@Nullable UUID uuid, EntityType entityType, CompoundNBT compoundNBT) {
        setOwnerId(uuid);
        this.phantomEntity = entityType;
        this.setSugarPhantomData(compoundNBT);
        readAdditional(compoundNBT);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        //maid is unbreakable(I guess)
        return false;
    }

    public void tick() {
        this.noClip = true;
        super.tick();
        this.noClip = false;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public void onSpawnWithEgg() {
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isInvisibleToPlayer(PlayerEntity player) {
        return false;
    }
}
