package mmr.littledelicacies.network;

import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageMaidNameSet {
    private int entityID;
    private String name;

    public MessageMaidNameSet() {
    }

    public MessageMaidNameSet(int entityID, String name) {
        this.entityID = entityID;
        this.name = name;
    }

    public MessageMaidNameSet(LittleMaidBaseEntity entityIn, String name) {
        this.entityID = entityIn.getEntityId();
        this.name = name;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public static MessageMaidNameSet readPacketData(PacketBuffer buf) {
        int entityId = buf.readVarInt();
        String job = buf.readString();
        return new MessageMaidNameSet(entityId, job);
    }

    public void writePacketData(PacketBuffer buffer) {
        buffer.writeVarInt(this.entityID);

        buffer.writeString(this.name);
    }


    public static class Handler {
        public static void handle(MessageMaidNameSet message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            ctx.get().enqueueWork(() -> {

                Entity entity = ctx.get().getSender().getServerWorld().getEntityByID(message.entityID);
                if (entity instanceof LittleMaidBaseEntity) {
                    LittleMaidBaseEntity littlemaid = (LittleMaidBaseEntity) entity;

                    littlemaid.setCustomName(new StringTextComponent(message.name));
                }
            });
            context.setPacketHandled(true);
        }
    }
}