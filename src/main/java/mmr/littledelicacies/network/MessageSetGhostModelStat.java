package mmr.littledelicacies.network;

import mmr.littledelicacies.api.ClientInfo;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageSetGhostModelStat {
    private int entityID;

    public MessageSetGhostModelStat() {
    }

    public MessageSetGhostModelStat(int entityID) {
        this.entityID = entityID;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public static MessageSetGhostModelStat readPacketData(PacketBuffer buf) {
        int entityId = buf.readVarInt();
        return new MessageSetGhostModelStat(entityId);
    }

    public void writePacketData(PacketBuffer buffer) {
        buffer.writeVarInt(this.entityID);
    }


    public static class Handler {
        public static void handle(MessageSetGhostModelStat message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();

                if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                    ctx.get().enqueueWork(() -> {
                        Entity entity = ClientInfo.getClientPlayerWorld().getEntityByID(message.entityID);
                        if (entity instanceof LittleMaidBaseEntity) {
                            LittleMaidBaseEntity littlemaid = (LittleMaidBaseEntity) entity;

                            littlemaid.setTextureNames();
                        }
                    });
                    context.setPacketHandled(true);
                }
        }
    }
}