package mmr.maidmodredo.network;

import mmr.maidmodredo.api.ClientInfo;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
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
            ctx.get().enqueueWork(() -> {
                if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                    Entity entity = ClientInfo.getClientPlayerWorld().getEntityByID(message.entityID);
                    if (entity instanceof LittleMaidBaseEntity) {
                        LittleMaidBaseEntity littlemaid = (LittleMaidBaseEntity) entity;

                        littlemaid.syncModelNames();
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}