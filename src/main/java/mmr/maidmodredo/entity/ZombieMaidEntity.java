package mmr.maidmodredo.entity;

import com.mojang.datafixers.Dynamic;
import mmr.maidmodredo.entity.data.MaidData;
import mmr.maidmodredo.init.LittleEntitys;
import mmr.maidmodredo.init.MaidDataSerializers;
import mmr.maidmodredo.init.MaidJob;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.UUID;

public class ZombieMaidEntity extends ZombieEntity {
    private static final DataParameter<Boolean> CONVERTING = EntityDataManager.createKey(ZombieMaidEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<MaidData> field_213795_c = EntityDataManager.createKey(ZombieMaidEntity.class, MaidDataSerializers.MAID_DATA);
    private int conversionTime;
    protected UUID converstionStarter;

    public ZombieMaidEntity(EntityType<? extends ZombieMaidEntity> p_i50186_1_, World p_i50186_2_) {
        super(p_i50186_1_, p_i50186_2_);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(CONVERTING, false);
        this.dataManager.register(field_213795_c, new MaidData(MaidJob.NORMAL, 1));
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("MaidData", this.getMaidData().serialize(NBTDynamicOps.INSTANCE));

        compound.putInt("ConversionTime", this.isConverting() ? this.conversionTime : -1);
        if (this.converstionStarter != null) {
            compound.putUniqueId("ConversionPlayer", this.converstionStarter);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("MaidData", 10)) {
            this.func_213792_a(new MaidData(new Dynamic<>(NBTDynamicOps.INSTANCE, compound.get("MaidData"))));
        }


        if (compound.contains("ConversionTime", 99) && compound.getInt("ConversionTime") > -1) {
            this.startConverting(compound.hasUniqueId("ConversionPlayer") ? compound.getUniqueId("ConversionPlayer") : null, compound.getInt("ConversionTime"));
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (!this.world.isRemote && this.isAlive() && this.isConverting()) {
            int i = this.getConversionProgress();
            this.conversionTime -= i;
            if (this.conversionTime <= 0) {
                this.func_213791_a((ServerWorld) this.world);
            }
        }

        super.tick();
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.GOLDEN_APPLE && this.isPotionActive(Effects.WEAKNESS)) {
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote) {
                this.startConverting(player.getUniqueID(), this.rand.nextInt(2401) + 3600);
                player.func_226292_a_(hand, true);
            }

            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Override
    public boolean isChild() {
        return false;
    }

    protected boolean shouldDrown() {
        return false;
    }

    public boolean canDespawn(double distanceToClosestPlayer) {
        return !this.isConverting();
    }

    /**
     * Returns whether this zombie is in the process of converting to a villager
     */
    public boolean isConverting() {
        return this.getDataManager().get(CONVERTING);
    }

    /**
     * Starts conversion of this zombie villager to a villager
     */
    private void startConverting(@Nullable UUID conversionStarterIn, int conversionTimeIn) {
        this.converstionStarter = conversionStarterIn;
        this.conversionTime = conversionTimeIn;
        this.getDataManager().set(CONVERTING, true);
        this.removePotionEffect(Effects.WEAKNESS);
        this.addPotionEffect(new EffectInstance(Effects.STRENGTH, conversionTimeIn, Math.min(this.world.getDifficulty().getId() - 1, 0)));
        this.world.setEntityState(this, (byte) 16);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 16) {
            if (!this.isSilent()) {
                this.world.playSound(this.getPosX(), this.getPosYEye(), this.getPosZ(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }

        } else {
            super.handleStatusUpdate(id);
        }
    }

    protected void func_213791_a(ServerWorld p_213791_1_) {
        LittleMaidEntity maidentity = LittleEntitys.LITTLEMAID.create(p_213791_1_);


        maidentity.copyLocationAndAnglesFrom(this);
        maidentity.setMaidData(this.getMaidData());

        maidentity.onInitialSpawn(p_213791_1_, p_213791_1_.getDifficultyForLocation(new BlockPos(maidentity)), SpawnReason.CONVERSION, (ILivingEntityData) null, (CompoundNBT) null);

        this.remove();
        maidentity.setNoAI(this.isAIDisabled());
        if (this.hasCustomName()) {
            maidentity.setCustomName(this.getCustomName());
            maidentity.setCustomNameVisible(this.isCustomNameVisible());
        }

        if (this.isNoDespawnRequired()) {
            maidentity.enablePersistence();
        }

        maidentity.setInvulnerable(this.isInvulnerable());
        p_213791_1_.addEntity(maidentity);
        if (this.converstionStarter != null) {
            maidentity.maidContractLimit = 68000;
            PlayerEntity playerentity = p_213791_1_.getPlayerByUuid(this.converstionStarter);
            maidentity.setTamedBy(playerentity);
        }

        maidentity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 200, 0));
        p_213791_1_.playEvent((PlayerEntity) null, 1027, new BlockPos(this), 0);
    }

    private int getConversionProgress() {
        int i = 1;
        if (this.rand.nextFloat() < 0.01F) {
            int j = 0;
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for (int k = (int) this.getPosX() - 4; k < (int) this.getPosX() + 4 && j < 14; ++k) {
                for (int l = (int) this.getPosY() - 4; l < (int) this.getPosY() + 4 && j < 14; ++l) {
                    for (int i1 = (int) this.getPosZ() - 4; i1 < (int) this.getPosZ() + 4 && j < 14; ++i1) {
                        Block block = this.world.getBlockState(blockpos$mutable.setPos(k, l, i1)).getBlock();
                        if (block == Blocks.IRON_BARS || block instanceof BedBlock) {
                            if (this.rand.nextFloat() < 0.3F) {
                                ++i;
                            }

                            ++j;
                        }
                    }
                }
            }
        }

        return i;
    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch() {
        return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 2.0F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F;
    }

    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
    }

    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ZOMBIE_HURT;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_DEATH;
    }

    public SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected ItemStack getSkullDrop() {
        return ItemStack.EMPTY;
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.func_213792_a(this.getMaidData().withLevel(5 + worldIn.getRandom().nextInt(5)));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public void func_213792_a(MaidData p_213792_1_) {
        MaidData maidData = this.getMaidData();

        this.dataManager.set(field_213795_c, p_213792_1_);
    }

    public MaidData getMaidData() {
        return this.dataManager.get(field_213795_c);
    }

}