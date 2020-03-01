package mmr.maidmodredo.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.JumpGoal;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class JumpToTargetGoal extends JumpGoal {
    private static final int[] ranges = new int[]{0, 1, 4, 5, 6, 7};
    private final CreatureEntity creatureEntity;
    private final int chance;
    private final double distance;
    private boolean isWater;

    public JumpToTargetGoal(CreatureEntity creature, int chance, double maxDistance) {
        this.creatureEntity = creature;
        this.chance = chance;
        this.distance = maxDistance;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.creatureEntity.getRNG().nextInt(this.chance) != 0) {
            return false;
        } else {
            Direction direction = this.creatureEntity.getAdjustedHorizontalFacing();
            int i = direction.getXOffset();
            int j = direction.getZOffset();
            BlockPos blockpos = new BlockPos(this.creatureEntity);

            for (int k : ranges) {
                if (!this.findSpace(blockpos, i, j, k)) {
                    return false;
                }
            }
            LivingEntity entity = this.creatureEntity.getAttackTarget();

            return entity != null && this.creatureEntity.getDistanceSq(entity) < distance;
        }
    }

    private boolean findSpace(BlockPos pos, int x, int z, int range) {
        return this.creatureEntity.world.getBlockState(pos.add(x * range, 1, z * range)).isAir() && this.creatureEntity.world.getBlockState(pos.add(x * range, 2, z * range)).isAir();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        double d0 = this.creatureEntity.getMotion().y;
        return (!(d0 * d0 < (double) 0.03F) || this.creatureEntity.rotationPitch == 0.0F || !(Math.abs(this.creatureEntity.rotationPitch) < 10.0F)) && !this.creatureEntity.onGround;
    }

    public boolean isPreemptible() {
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        LivingEntity entity = this.creatureEntity.getAttackTarget();
        Direction direction = this.creatureEntity.getAdjustedHorizontalFacing();

        if (entity != null && this.creatureEntity.getDistanceSq(entity) < distance) {
            this.creatureEntity.getLookController().setLookPositionWithEntity(entity, 60.0F, 30.0F);
            Vec3d vec3d = (new Vec3d(entity.getPosX() - this.creatureEntity.getPosX(), entity.getPosY() - this.creatureEntity.getPosY(), entity.getPosZ() - this.creatureEntity.getPosZ())).normalize();
            this.creatureEntity.setMotion(this.creatureEntity.getMotion().add(vec3d.x * 0.95D, 0.9F, vec3d.z * 0.95D));
        }
        this.creatureEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.creatureEntity.rotationPitch = 0.0F;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {

        Vec3d vec3d = this.creatureEntity.getMotion();
        if (vec3d.y * vec3d.y < (double) 0.03F && this.creatureEntity.rotationPitch != 0.0F) {
            this.creatureEntity.rotationPitch = MathHelper.rotLerp(this.creatureEntity.rotationPitch, 0.0F, 0.2F);
        } else {
            double d0 = Math.sqrt(Entity.horizontalMag(vec3d));
            double d1 = Math.signum(-vec3d.y) * Math.acos(d0 / vec3d.length()) * (double) (180F / (float) Math.PI);
            this.creatureEntity.rotationPitch = (float) d1;
        }
        LivingEntity entity = this.creatureEntity.getAttackTarget();

        //when
        if (entity != null && this.creatureEntity.getDistanceSq(entity) <= getAttackReachSqr(entity)) {
            this.creatureEntity.attackEntityAsMob(entity);
        }

    }

    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return (double) (this.creatureEntity.getWidth() * 1.5F * this.creatureEntity.getWidth() * 1.5F + attackTarget.getWidth());
    }
}