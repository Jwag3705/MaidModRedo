package mmr.littledelicacies.api.trackmaid;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;
import java.util.UUID;

public class TrackMaidCapability implements ITrackMaidCapability {
    private UUID linkedMaidID = UUID.randomUUID();
    private int dimensionID;

    @Override
    public void handleMaid(PlayerEntity player, Entity littleMaidBaseEntity) {

    }

    @Override
    public INBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("maidDimension", dimensionID);
        compoundNBT.putUniqueId("uniqueID", linkedMaidID);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        linkedMaidID = compoundNBT.getUniqueId("uniqueID");
        dimensionID = compoundNBT.getInt("maidDimension");
    }

    @Override
    public Entity getLinkedEntity(ServerWorld world) {
        ServerWorld physicalBodyDimension = world.getServer().getWorld(Objects.requireNonNull(DimensionType.getById(dimensionID)));
        return physicalBodyDimension.getEntityByUuid(linkedMaidID);
    }

    @Override
    public void setLinkedBodyID(Entity entity) {
        linkedMaidID = entity.getUniqueID();
    }

    @Override
    public int getDimensionID() {
        return dimensionID;
    }

    @Override
    public void setDimensionID(int dimensionID) {
        this.dimensionID = dimensionID;
    }
}
