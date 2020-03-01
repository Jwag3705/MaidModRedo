package mmr.maidmodredo.maidjob;

import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class FlameCasterMaidJob extends CasterMaidJob {
    public FlameCasterMaidJob(String nameIn, ItemStack stack) {
        super(nameIn, stack);
    }

    public void castMagic(LittleMaidBaseEntity owner, Entity target) {
        double d0 = owner.getDistanceSq(target.getPosX(), target.getBoundingBox().minY, target.getPosZ());

        double d1 = target.getPosX() - owner.getPosX();
        double d2 = target.getPosYHeight(0.5D) - owner.getPosYHeight(1.0D);
        double d3 = target.getPosZ() - owner.getPosZ();

        float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.25F;
        owner.world.playEvent((PlayerEntity) null, 1018, new BlockPos(owner), 0);


        for (int i2 = 0; i2 < 2; ++i2) {
            SmallFireballEntity smallfireballentity = new SmallFireballEntity(owner.world, owner, d1 + owner.getRNG().nextGaussian() * (double) f, d2, d3 + owner.getRNG().nextGaussian() * (double) f);
            smallfireballentity.setPosition(smallfireballentity.getPosX(), owner.getPosYHeight(0.5D) + 0.5D, smallfireballentity.getPosZ());
            owner.world.addEntity(smallfireballentity);
        }
    }
}
