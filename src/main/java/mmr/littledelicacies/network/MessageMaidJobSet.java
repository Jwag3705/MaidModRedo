package mmr.littledelicacies.network;

import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.MaidJob;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageMaidJobSet {
    private int entityID;
    private MaidJob job;

    public MessageMaidJobSet() {
    }

    public MessageMaidJobSet(int entityID, String job) {
        this.entityID = entityID;
        this.job = MaidJob.MAID_JOB_REGISTRY.getOrDefault(new ResourceLocation(job));
    }

    public MessageMaidJobSet(LittleMaidBaseEntity entityIn, MaidJob job) {
        this.entityID = entityIn.getEntityId();
        this.job = job;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public static MessageMaidJobSet readPacketData(PacketBuffer buf) {
        int entityId = buf.readVarInt();
        String job = buf.readString();
        return new MessageMaidJobSet(entityId, job);
    }

    public void writePacketData(PacketBuffer buffer) {
        buffer.writeVarInt(this.entityID);

        buffer.writeString(MaidJob.MAID_JOB_REGISTRY.getKey(this.job).toString());
    }


    public static class Handler {
        public static void handle(MessageMaidJobSet message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            ctx.get().enqueueWork(() -> {

                Entity entity = ctx.get().getSender().getServerWorld().getEntityByID(message.entityID);
                if (entity instanceof LittleMaidBaseEntity) {
                    LittleMaidBaseEntity littlemaid = (LittleMaidBaseEntity) entity;

                    littlemaid.setMaidData(littlemaid.getMaidData().withJob(message.job));
                    littlemaid.resetBrain(ctx.get().getSender().getServerWorld());
                }
            });
            context.setPacketHandled(true);
        }
    }
}