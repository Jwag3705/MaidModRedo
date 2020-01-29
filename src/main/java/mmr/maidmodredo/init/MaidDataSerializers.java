package mmr.maidmodredo.init;

import mmr.maidmodredo.entity.data.MaidData;
import mmr.maidmodredo.entity.data.MaidJob;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;

public class MaidDataSerializers {
    public static final IDataSerializer<MaidData> MAID_DATA = new IDataSerializer<MaidData>() {
        public void write(PacketBuffer buf, MaidData value) {
            buf.writeVarInt(MaidJob.MAID_JOB_REGISTRY.getId(value.getJob()));
            buf.writeVarInt(value.getLevel());
        }

        public MaidData read(PacketBuffer buf) {
            return new MaidData(MaidJob.MAID_JOB_REGISTRY.getByValue(buf.readVarInt()), buf.readVarInt());
        }

        public MaidData copyValue(MaidData value) {
            return value;
        }
    };

    public static void registerData() {
        DataSerializers.registerSerializer(MAID_DATA);
    }
}
