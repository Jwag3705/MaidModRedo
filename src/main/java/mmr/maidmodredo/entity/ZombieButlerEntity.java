package mmr.maidmodredo.entity;

import mmr.maidmodredo.init.LittleEntitys;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ZombieButlerEntity extends ZombieMaidEntity {
    public ZombieButlerEntity(EntityType<? extends ZombieMaidEntity> p_i50186_1_, World p_i50186_2_) {
        super(p_i50186_1_, p_i50186_2_);
    }

    protected void func_213791_a(ServerWorld p_213791_1_) {
        LittleButlerEntity maidentity = LittleEntitys.LITTLEBUTLER.create(p_213791_1_);


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
}
