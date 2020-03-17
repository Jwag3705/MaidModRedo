package mmr.littledelicacies.entity;

import mmr.littledelicacies.init.LittleSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LittleButlerEntity extends LittleMaidBaseEntity {
    public LittleButlerEntity(EntityType<? extends LittleMaidBaseEntity> p_i48575_1_, World p_i48575_2_) {
        super(p_i48575_1_, p_i48575_2_);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return LittleSounds.LITTLEBUTLER_IDLE;
    }
}
