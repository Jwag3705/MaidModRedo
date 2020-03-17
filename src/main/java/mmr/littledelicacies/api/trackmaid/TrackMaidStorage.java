package mmr.littledelicacies.api.trackmaid;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TrackMaidStorage implements Capability.IStorage<ITrackMaidCapability> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<ITrackMaidCapability> capability, ITrackMaidCapability instance, Direction side) {

        CompoundNBT maidUUID = new CompoundNBT();

        maidUUID.put("data", instance.serializeNBT());

        return maidUUID;
    }

    @Override
    public void readNBT(Capability<ITrackMaidCapability> capability, ITrackMaidCapability instance, Direction side, INBT nbt) {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;

        CompoundNBT data = (CompoundNBT) compoundNBT.get("data");

        instance.deserializeNBT(data);
    }

}