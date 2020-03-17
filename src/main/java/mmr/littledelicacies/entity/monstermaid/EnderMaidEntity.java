package mmr.littledelicacies.entity.monstermaid;

import mmr.littledelicacies.api.MaidAnimation;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.network.MaidPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EnderMaidEntity extends LittleMaidBaseEntity {

    public static final MaidAnimation HOLD_ANIMATION = MaidAnimation.create(60);

    private static final MaidAnimation[] ANIMATIONS = {
            TALK_ANIMATION,
            PET_ANIMATION,
            FARM_ANIMATION,
            EAT_ANIMATION,
            HOLD_ANIMATION
    };

    public EnderMaidEntity(EntityType<? extends LittleMaidBaseEntity> p_i48575_1_, World p_i48575_2_) {
        super(p_i48575_1_, p_i48575_2_);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    protected void updateAITasks() {


        if (this.getOwner() != null && this.onGround && this.getOwner().getPosY() < -10 && this.getOwner().isAlive()) {
            if (!((PlayerEntity) this.getOwner()).isCreative() && !((PlayerEntity) this.getOwner()).isSpectator()) {
                this.helpOwner(this.getOwner());
            }
        }

        if (this.getOwner() != null && this.getPosY() < -10 && this.getOwner().onGround && this.getOwner().isAlive()) {
            this.teleportTo(this.getOwner().getPosX(), this.getOwner().getPosY(), this.getOwner().getPosZ());
        }

        if (this.getOwner() != null && this.onGround && this.getOwner().isBurning() && this.getOwner().isInLava() && this.getOwner().getActivePotionEffect(Effects.FIRE_RESISTANCE) == null && this.onGround && this.getOwner().isAlive()) {
            if (!((PlayerEntity) this.getOwner()).isCreative() && !((PlayerEntity) this.getOwner()).isSpectator()) {
                this.helpOwner(this.getOwner());
            }
        }

        if (this.getOwner() != null && this.onGround && !((PlayerEntity) this.getOwner()).isCreative() && !((PlayerEntity) this.getOwner()).isSpectator()) {
            this.teleportAndHelpFallOwner();
        }

        super.updateAITasks();
    }

    public void livingTick() {
        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.PORTAL, this.getPosXRandom(0.5D), this.getPosYRandom() - 0.25D, this.getPosZRandom(0.5D), (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }

        super.livingTick();
    }

    /**
     * Help Owner and teleport endermaid and owner
     */
    protected boolean teleportAndHelpFallOwner() {
        if (!this.world.isRemote() && this.isAlive() && this.getOwner() != null && this.getOwner().isAlive() && this.getOwner().fallDistance > 10D) {
            for (int i = 0; i < 16; ++i) {
                double d0 = this.getOwner().getPosX() + (this.rand.nextDouble() - 0.5D) * 8.0D;
                double d1 = this.getOwner().getPosY() + (double) (this.rand.nextInt(16) - 8);
                double d2 = this.getOwner().getPosZ() + (this.rand.nextDouble() - 0.5D) * 8.0D;
                if (this.teleportTo(d0, d1, d2)) {
                    this.helpOwner(this.getOwner());
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    protected boolean teleportRandomly() {
        if (!this.world.isRemote() && this.isAlive()) {
            double d0 = this.getPosX() + (this.rand.nextDouble() - 0.5D) * 32.0D;
            double d1 = this.getPosY() + (double) (this.rand.nextInt(32) - 16);
            double d2 = this.getPosZ() + (this.rand.nextDouble() - 0.5D) * 32.0D;
            return this.teleportTo(d0, d1, d2);
        } else {
            return false;
        }
    }

    /**
     * Teleport the endermaid to another entity
     */
    public boolean teleportToEntity(Entity p_70816_1_) {
        Vec3d vec3d = new Vec3d(this.getPosX() - p_70816_1_.getPosX(), this.getPosYHeight(0.5D) - p_70816_1_.getPosYEye(), this.getPosZ() - p_70816_1_.getPosZ());
        vec3d = vec3d.normalize();
        double d0 = 16.0D;
        double d1 = this.getPosX() + (this.rand.nextDouble() - 0.5D) * 16.0D - vec3d.x * 32.0D;
        double d2 = this.getPosY() + (double) (this.rand.nextInt(16) - 8) - vec3d.y * 32.0D;
        double d3 = this.getPosZ() + (this.rand.nextDouble() - 0.5D) * 16.0D - vec3d.z * 32.0D;
        return this.teleportTo(d1, d2, d3);
    }

    public boolean helpOwner(LivingEntity p_70816_1_) {
        p_70816_1_.fallDistance = 0.0F;

        net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(p_70816_1_, this.getPosX(), this.getPosY(), this.getPosZ(), 0);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
        boolean flag2 = p_70816_1_.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
        if (flag2) {
            MaidPacketHandler.animationModel(this, HOLD_ANIMATION);
            this.world.playSound((PlayerEntity) null, p_70816_1_.prevPosX, p_70816_1_.prevPosY, p_70816_1_.prevPosZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
        }
        return flag2;
    }

    /**
     * Teleport the endermaid
     */
    private boolean teleportTo(double x, double y, double z) {
        this.fallDistance = 0.0F;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(x, y, z);

        while (blockpos$mutable.getY() > 0 && !this.world.getBlockState(blockpos$mutable).getMaterial().blocksMovement()) {
            blockpos$mutable.move(Direction.DOWN);
        }

        BlockState blockstate = this.world.getBlockState(blockpos$mutable);
        boolean flag = blockstate.getMaterial().blocksMovement();
        boolean flag1 = blockstate.getFluidState().isTagged(FluidTags.WATER);
        if (flag && !flag1) {
            net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
            boolean flag2 = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2) {
                this.world.playSound((PlayerEntity) null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return flag2;
        } else {
            return false;
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDERMAN_DEATH;
    }

    @Override
    protected float getSoundPitch() {
        return 1.2F;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!(source instanceof IndirectEntityDamageSource) && source != DamageSource.FIREWORKS) {
            boolean flag = super.attackEntityFrom(source, amount);
            if (!this.world.isRemote() && source.isUnblockable() && this.rand.nextInt(10) != 0 && !isMaidWait()) {
                this.teleportRandomly();
            }

            return flag;
        } else {
            for (int i = 0; i < 64; ++i) {
                if (!isMaidWait() && this.teleportRandomly()) {
                    return true;
                } else if (this.getOwner() != null && this.teleportToEntity(this.getOwner())) {
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.9F;
    }

    @Override
    public MaidAnimation[] getAnimations() {
        return ANIMATIONS;
    }
}
