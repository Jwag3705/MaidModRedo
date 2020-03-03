package mmr.maidmodredo.entity;

import com.mojang.datafixers.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import mmr.maidmodredo.entity.projectile.RootEntity;
import mmr.maidmodredo.init.LittleCreatureAttribute;
import mmr.maidmodredo.init.MaidTrades;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.village.GossipManager;
import net.minecraft.village.GossipType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class PlanterEntity extends AbstractVillagerEntity implements IReputationTracking {
    private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(PlanterEntity.class, DataSerializers.VARINT);
    private boolean customer;
    private int xp;

    @Nullable
    private PlayerEntity previousCustomer;


    @Nullable
    private BlockPos planterHome;
    private final GossipManager gossip = new GossipManager();

    private int timeUntilReset;

    protected int spellTicks;

    public PlanterEntity(EntityType<? extends PlanterEntity> type, World worldIn) {
        super(type, worldIn);
    }


    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new LookAtCustomerGoal(this));
        this.goalSelector.addGoal(2, new AttackSpellGoal());
        this.goalSelector.addGoal(3, new MoveToHomeGoal(this, 60.0D, 1.2D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 0.9D));
        this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, RavagerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, VexEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, ZombieEntity.class, true));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(26.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    protected void registerData() {
        super.registerData();

        this.dataManager.register(LEVEL, 1);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putInt("Level", getLevel());
        compound.putInt("Xp", this.xp);

        compound.put("Gossips", this.gossip.serialize(NBTDynamicOps.INSTANCE).getValue());

        if (this.planterHome != null) {
            compound.put("PlanterHome", NBTUtil.writeBlockPos(this.planterHome));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        if (compound.contains("Level")) {
            this.setLevel(compound.getInt("Level"));
        }
        if (compound.contains("Xp", 3)) {
            this.xp = compound.getInt("Xp");
        }

        ListNBT listnbt = compound.getList("Gossips", 10);
        this.gossip.deserialize(new Dynamic<>(NBTDynamicOps.INSTANCE, listnbt));

        if (compound.contains("PlanterHome")) {
            this.planterHome = NBTUtil.readBlockPos(compound.getCompound("PlanterHome"));
        }

    }

    public void setOffers(MerchantOffers offersIn) {
        this.offers = offersIn;
    }

    public void setPlanterHome(@Nullable BlockPos pos) {
        this.planterHome = pos;
    }

    @Nullable
    public BlockPos getPlanterHome() {
        return this.planterHome;
    }

    private void populateBuyingList() {
        this.setLevel(this.getLevel() + 1);
        this.populateTradeData();
    }

    public void restock() {
        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetUses();
        }
    }

    protected void resetCustomer() {
        super.resetCustomer();

        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetSpecialPrice();
        }
    }

    //when uses no left
    public boolean isStockOut() {
        for (MerchantOffer merchantoffer : this.getOffers()) {
            if (merchantoffer.hasNoUsesLeft()) {
                return true;
            }
        }

        return false;
    }

    public void tick() {
        super.tick();
       /* if (this.getShakeHeadTicks() > 0) {
            this.setShakeHeadTicks(this.getShakeHeadTicks() - 1);
        }*/

        //this.tickGossip();
    }

    @Override
    protected void updateAITasks() {

        if (!this.hasCustomer() && this.timeUntilReset > 0) {
            --this.timeUntilReset;
            if (this.timeUntilReset <= 0) {
                if (this.customer) {
                    this.populateBuyingList();
                    this.customer = false;
                }
                this.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, 0));
            }
        }

        if (this.previousCustomer != null && this.world instanceof ServerWorld) {
            ((ServerWorld) this.world).updateReputation(IReputationType.TRADE, this.previousCustomer, this);
            this.world.setEntityState(this, (byte) 14);
            this.previousCustomer = null;
        }

        //findHome(false);

        super.updateAITasks();

        if (this.spellTicks > 0) {
            --this.spellTicks;
        }
    }

    @Override
    protected void populateTradeData() {
        Int2ObjectMap<VillagerTrades.ITrade[]> int2objectmap = MaidTrades.planterTrade;

        if (int2objectmap != null && !int2objectmap.isEmpty()) {

            VillagerTrades.ITrade[] avillagertrades$itrade = int2objectmap.get(this.getLevel());

            if (avillagertrades$itrade != null) {
                MerchantOffers merchantoffers = this.getOffers();

                this.addTrades(merchantoffers, avillagertrades$itrade, 3);
            }

        }
    }

    @Override
    protected void onVillagerTrade(MerchantOffer offer) {
        int i = 3 + this.rand.nextInt(4);

        this.xp += offer.getGivenExp();
        this.previousCustomer = this.getCustomer();

        if (this.canLevelUp()) {
            this.timeUntilReset = 40;
            this.customer = true;

            i += 5;
        }

        if (offer.getDoesRewardExp()) {
            this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), i));
        }
    }

    private boolean canLevelUp() {
        int i = this.getLevel();
        return VillagerData.func_221128_d(i) && this.xp >= VillagerData.func_221127_c(i);
    }


    public int getXp() {
        return this.xp;
    }

    public void setXp(int p_213761_1_) {
        this.xp = p_213761_1_;
    }

    public void setLevel(int level) {
        this.dataManager.set(LEVEL, level);
    }

    public int getLevel() {
        return this.dataManager.get(LEVEL);
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = itemstack.getItem() == Items.NAME_TAG;
        if (flag) {
            itemstack.interactWithEntity(player, this, hand);
            return true;
        } else if (this.isAlive() && !this.hasCustomer() && !this.isChild()) {
            boolean flag1 = this.getOffers().isEmpty();

            if (hand == Hand.MAIN_HAND) {
                player.addStat(Stats.TALKED_TO_VILLAGER);
            }

            if (this.getOffers().isEmpty()) {
                //remaking trade
                this.populateTradeData();
                if (!this.world.isRemote) {
                    this.setCustomer(player);
                    this.displayMerchantGui(player);
                }

                return true;
            } else {
                if (!this.world.isRemote) {
                    this.setCustomer(player);
                    this.displayMerchantGui(player);
                }

                return true;
            }
        } else {
            return super.processInteract(player, hand);
        }
    }

    public void setCustomer(@Nullable PlayerEntity player) {

        boolean flag = this.getCustomer() != null && player == null;

        super.setCustomer(player);

        if (flag) {

            this.resetCustomer();

        }
    }

    private void displayMerchantGui(PlayerEntity player) {
        this.recalculateSpecialPricesFor(player);
        this.setCustomer(player);
        this.openMerchantContainer(player, this.getDisplayName(), this.getLevel());
    }


    private void recalculateSpecialPricesFor(PlayerEntity playerIn) {
        int i = this.getPlayerReputation(playerIn);

        if (i != 0) {
            for (MerchantOffer merchantoffer : this.getOffers()) {
                merchantoffer.increaseSpecialPrice(-MathHelper.floor((float) i * merchantoffer.getPriceMultiplier()));
            }
        }

        if (playerIn.isPotionActive(Effects.HERO_OF_THE_VILLAGE)) {

            EffectInstance effectinstance = playerIn.getActivePotionEffect(Effects.HERO_OF_THE_VILLAGE);
            int k = effectinstance.getAmplifier();
            for (MerchantOffer merchantoffer1 : this.getOffers()) {
                double d0 = 0.3D + 0.0625D * (double) k;
                int j = (int) Math.floor(d0 * (double) merchantoffer1.getBuyingStackFirst().getCount());
                merchantoffer1.increaseSpecialPrice(-Math.max(j, 1));
            }
        }
    }


    public int getPlayerReputation(PlayerEntity player) {
        return this.gossip.func_220921_a(player.getUniqueID(), (p_223103_0_) -> {
            return true;
        });
    }


    public void updateReputation(IReputationType type, Entity target) {

        if (type == IReputationType.TRADE) {
            this.gossip.func_220916_a(target.getUniqueID(), GossipType.TRADING, 2);
        } else if (type == IReputationType.VILLAGER_HURT) {
            this.gossip.func_220916_a(target.getUniqueID(), GossipType.MINOR_NEGATIVE, 25);
        } else if (type == IReputationType.VILLAGER_KILLED) {
            this.gossip.func_220916_a(target.getUniqueID(), GossipType.MAJOR_NEGATIVE, 25);
        }
    }

    @Override
    public void playCelebrateSound() {
    }

    @Override
    protected SoundEvent getVillagerYesNoSound(boolean getYesSound) {
        return null;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    public boolean isSpellcasting() {
        return this.spellTicks > 0;
    }

    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getCreatureAttribute() == LittleCreatureAttribute.MAID) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return LittleCreatureAttribute.MAID;
    }

    class MoveToHomeGoal extends Goal {
        final PlanterEntity field_220847_a;
        final double field_220848_b;
        final double field_220849_c;

        MoveToHomeGoal(PlanterEntity p_i50459_2_, double p_i50459_3_, double p_i50459_5_) {
            this.field_220847_a = p_i50459_2_;
            this.field_220848_b = p_i50459_3_;
            this.field_220849_c = p_i50459_5_;
            this.setMutexFlags(EnumSet.of(Flag.MOVE));
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            PlanterEntity.this.navigator.clearPath();
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            BlockPos blockpos = this.field_220847_a.getPlanterHome();
            return blockpos != null && this.func_220846_a(blockpos, this.field_220848_b);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            BlockPos blockpos = this.field_220847_a.getPlanterHome();
            if (blockpos != null && PlanterEntity.this.navigator.noPath()) {
                if (this.func_220846_a(blockpos, 10.0D)) {
                    Vec3d vec3d = (new Vec3d((double) blockpos.getX() - this.field_220847_a.getPosX(), (double) blockpos.getY() - this.field_220847_a.getPosY(), (double) blockpos.getZ() - this.field_220847_a.getPosZ())).normalize();
                    Vec3d vec3d1 = vec3d.scale(10.0D).add(this.field_220847_a.getPosX(), this.field_220847_a.getPosY(), this.field_220847_a.getPosZ());
                    PlanterEntity.this.navigator.tryMoveToXYZ(vec3d1.x, vec3d1.y, vec3d1.z, this.field_220849_c);
                } else {
                    PlanterEntity.this.navigator.tryMoveToXYZ((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.field_220849_c);
                }
            }

        }

        private boolean func_220846_a(BlockPos p_220846_1_, double p_220846_2_) {
            return !p_220846_1_.withinDistance(this.field_220847_a.getPositionVec(), p_220846_2_);
        }
    }

    class AttackSpellGoal extends PlanterEntity.UseSpellGoal {
        private AttackSpellGoal() {
        }

        protected int getCastingTime() {
            return 40;
        }

        protected int getCastingInterval() {
            return 120;
        }

        protected void castSpell() {
            LivingEntity livingentity = PlanterEntity.this.getAttackTarget();
            double d0 = Math.min(livingentity.getPosY(), PlanterEntity.this.getPosY());
            double d1 = Math.max(livingentity.getPosY(), PlanterEntity.this.getPosY()) + 1.0D;
            float f = (float) MathHelper.atan2(livingentity.getPosZ() - PlanterEntity.this.getPosZ(), livingentity.getPosX() - PlanterEntity.this.getPosX());
            if (PlanterEntity.this.getDistanceSq(livingentity) < 9.0D) {
                for (int i = 0; i < 5; ++i) {
                    float f1 = f + (float) i * (float) Math.PI * 0.4F;
                    this.spawnFangs(PlanterEntity.this.getPosX() + (double) MathHelper.cos(f1) * 1.5D, PlanterEntity.this.getPosZ() + (double) MathHelper.sin(f1) * 1.5D, d0, d1, f1, 0);
                }

                for (int k = 0; k < 8; ++k) {
                    float f2 = f + (float) k * (float) Math.PI * 2.0F / 8.0F + 1.2566371F;
                    this.spawnFangs(PlanterEntity.this.getPosX() + (double) MathHelper.cos(f2) * 2.5D, PlanterEntity.this.getPosZ() + (double) MathHelper.sin(f2) * 2.5D, d0, d1, f2, 3);
                }
            } else {
                for (int l = 0; l < 16; ++l) {
                    double d2 = 1.25D * (double) (l + 1);
                    int j = 1 * l;
                    this.spawnFangs(PlanterEntity.this.getPosX() + (double) MathHelper.cos(f) * d2, PlanterEntity.this.getPosZ() + (double) MathHelper.sin(f) * d2, d0, d1, f, j);
                }
            }

        }

        private void spawnFangs(double p_190876_1_, double p_190876_3_, double p_190876_5_, double p_190876_7_, float p_190876_9_, int p_190876_10_) {
            BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
            boolean flag = false;
            double d0 = 0.0D;

            while (true) {
                BlockPos blockpos1 = blockpos.down();
                BlockState blockstate = PlanterEntity.this.world.getBlockState(blockpos1);
                if (blockstate.isSolidSide(PlanterEntity.this.world, blockpos1, Direction.UP)) {
                    if (!PlanterEntity.this.world.isAirBlock(blockpos)) {
                        BlockState blockstate1 = PlanterEntity.this.world.getBlockState(blockpos);
                        VoxelShape voxelshape = blockstate1.getCollisionShape(PlanterEntity.this.world, blockpos);
                        if (!voxelshape.isEmpty()) {
                            d0 = voxelshape.getEnd(Direction.Axis.Y);
                        }
                    }

                    flag = true;
                    break;
                }

                blockpos = blockpos.down();
                if (blockpos.getY() < MathHelper.floor(p_190876_5_) - 1) {
                    break;
                }
            }

            if (flag) {
                PlanterEntity.this.world.addEntity(new RootEntity(PlanterEntity.this.world, p_190876_1_, (double) blockpos.getY() + d0, p_190876_3_, p_190876_9_, p_190876_10_, PlanterEntity.this));
            }

        }

        @Override
        public void tick() {
            super.tick();
            LivingEntity livingentity = PlanterEntity.this.getAttackTarget();
            if (livingentity != null) {
                PlanterEntity.this.getLookController().setLookPositionWithEntity(livingentity, 30.0F, 30.0F);
            }
        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED;
        }

    }

    public abstract class UseSpellGoal extends Goal {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            LivingEntity livingentity = PlanterEntity.this.getAttackTarget();
            if (livingentity != null && livingentity.isAlive()) {
                if (PlanterEntity.this.isSpellcasting()) {
                    return false;
                } else {
                    return PlanterEntity.this.ticksExisted >= this.spellCooldown;
                }
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            LivingEntity livingentity = PlanterEntity.this.getAttackTarget();
            return livingentity != null && livingentity.isAlive() && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            PlanterEntity.this.spellTicks = this.getCastingTime();
            this.spellCooldown = PlanterEntity.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            if (soundevent != null) {
                PlanterEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }

        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.spellWarmup;
            if (this.spellWarmup == 0) {
                this.castSpell();
                PlanterEntity.this.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 1.0F);
            }

        }

        protected abstract void castSpell();

        protected int getCastWarmupTime() {
            return 20;
        }

        protected abstract int getCastingTime();

        protected abstract int getCastingInterval();

        @Nullable
        protected abstract SoundEvent getSpellPrepareSound();
    }
}
