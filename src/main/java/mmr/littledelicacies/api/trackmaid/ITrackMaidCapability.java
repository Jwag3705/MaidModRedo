package mmr.littledelicacies.api.trackmaid;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.INBTSerializable;

public interface ITrackMaidCapability extends INBTSerializable {
    void handleMaid(PlayerEntity player, Entity littleMaidBaseEntity);

    Entity getLinkedEntity(ServerWorld world);

    void setLinkedBodyID(Entity entity);

    int getDimensionID();

    void setDimensionID(int dimensionID);

    INBT serializeNBT();

    void deserializeNBT(INBT nbt);
}