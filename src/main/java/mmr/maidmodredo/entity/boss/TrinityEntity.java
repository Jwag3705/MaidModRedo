package mmr.maidmodredo.entity.boss;

import com.google.common.collect.Maps;
import mmr.maidmodredo.api.IMaidAnimation;
import mmr.maidmodredo.api.MaidAnimation;
import mmr.maidmodredo.entity.ai.JumpToAttackTargetGoal;
import mmr.maidmodredo.entity.ai.StartRushGoal;
import mmr.maidmodredo.init.LittleDamageSource;
import mmr.maidmodredo.init.LittleItems;
import mmr.maidmodredo.network.MaidPacketHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TrinityEntity extends CreatureEntity implements IMaidAnimation {
    private int animationTick;
    private MaidAnimation animation = NO_ANIMATION;

    public static final MaidAnimation TALK_ANIMATION = MaidAnimation.create(100);
    public static final MaidAnimation RUSHING_ANIMATION = MaidAnimation.create(80);
    public static final MaidAnimation JUMP_ANIMATION = MaidAnimation.create(20);

    protected static final DataParameter<Boolean> WAITING = EntityDataManager.createKey(TrinityEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> RUSHING = EntityDataManager.createKey(TrinityEntity.class, DataSerializers.BOOLEAN);

    private static final MaidAnimation[] ANIMATIONS = {
            TALK_ANIMATION,
            RUSHING_ANIMATION,
            JUMP_ANIMATION
    };

    private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
    private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Rush speed penalty", 0.28D, AttributeModifier.Operation.ADDITION)).setSaved(false);


    public float prevShadowX, prevShadowY, prevShadowZ;
    public float shadowX, shadowY, shadowZ;
    public float prevShadowX2, prevShadowY2, prevShadowZ2;
    public float shadowX2, shadowY2, shadowZ2;

    public TrinityEntity(EntityType<? extends TrinityEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new JumpToAttackTargetGoal(this, 40, 48F));
        this.goalSelector.addGoal(2, new StartRushGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.25F, true));
        this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, AbstractIllagerEntity.class, true)));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, RavagerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, VexEntity.class, true));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(WAITING, false);
        this.dataManager.register(RUSHING, false);

    }

    @Override
    public void tick() {
        if (this.getAnimation() != IMaidAnimation.NO_ANIMATION) {
            this.setAnimationTick(this.getAnimationTick() + 1);

            if (this.getAnimationTick() >= this.getAnimation().getDuration()) {
                this.onAnimationFinish(this.getAnimation());
                this.resetPlayingAnimationToDefault();
            }
        }
        super.tick();
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

    public boolean isRushing() {
        return this.dataManager.get(RUSHING);
    }

    public void setRushing(boolean pflag) {
        IAttributeInstance iattributeinstance = this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        this.dataManager.set(RUSHING, pflag);
        if (pflag) {
            MaidPacketHandler.animationModel(this, RUSHING_ANIMATION);
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

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (amount > 0.0F && canRushingDamageSource(source)) {
            this.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1.0F, 1.45F);
            return false;
        }

        return super.attackEntityFrom(source, amount);
    }


    private boolean canRushingDamageSource(DamageSource damageSourceIn) {
        Entity entity = damageSourceIn.getImmediateSource();
        boolean flag = false;
        if (entity instanceof AbstractArrowEntity) {
            AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity) entity;
            if (abstractarrowentity.getPierceLevel() > 0) {
                flag = true;
            }
        }

        if (!damageSourceIn.isUnblockable() && (this.isRushing()) && !flag) {
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
        super.livingTick();
        this.updateAttackAblityTick();
    }

    private void updateAttackAblityTick() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox();

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

                if (entity instanceof LivingEntity) {
                    if (entity.attackEntityFrom(LittleDamageSource.causeRushingDamage(this), (float) (d1 * 1.25F))) {
                        ((LivingEntity) entity).knockBack(this, d2 + 0.4F, (double) MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F))));
                        entity.setMotion(entity.getMotion().add(0.0D, (double) 0.25F, 0.0D));
                        this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setEquipmentBasedOnDifficulty(difficultyIn);
        this.setEnchantmentBasedOnDifficulty(difficultyIn);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        ItemStack itemstack = new ItemStack(LittleItems.LONG_SPEAR);

        Map<Enchantment, Integer> map = Maps.newHashMap();
        map.put(Enchantments.SHARPNESS, 2);
        EnchantmentHelper.setEnchantments(map, itemstack);


        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        if (distance > 1.0F) {
            this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.4F, 1.0F);
        }

        int i = this.func_225508_e_(distance, damageMultiplier);

        i -= 3;
        if (i <= 0) {
            return false;
        } else {
            this.attackEntityFrom(DamageSource.FALL, (float) i);
            if (this.isBeingRidden()) {
                for (Entity entity : this.getRecursivePassengers()) {
                    entity.attackEntityFrom(DamageSource.FALL, (float) i);
                }
            }

            this.playFallSound();
            return true;
        }
    }

    @Override
    public boolean isNonBoss() {
        return false;
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    public void resetPlayingAnimationToDefault() {
        this.setAnimation(IMaidAnimation.NO_ANIMATION);
    }

    protected void onAnimationFinish(MaidAnimation animation) {
        if (animation == RUSHING_ANIMATION) {
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
}
