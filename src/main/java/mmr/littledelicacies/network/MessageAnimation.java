package mmr.littledelicacies.network;

import mmr.littledelicacies.api.ClientInfo;
import mmr.littledelicacies.api.IMaidAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageAnimation {
    private int entityID;
    private int index;

    public MessageAnimation() {
    }

    public MessageAnimation(int entityID, int index) {
        this.entityID = entityID;
        this.index = index;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public static MessageAnimation readPacketData(PacketBuffer buf) {
        int entityId = buf.readVarInt();
        int index = buf.readVarInt();
        return new MessageAnimation(entityId, index);
    }

    public void writePacketData(PacketBuffer buffer) {
        buffer.writeVarInt(this.entityID);
        buffer.writeVarInt(this.index);
    }


    public static class Handler {
        public static void handle(MessageAnimation message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            ctx.get().enqueueWork(() -> {
                if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                    Entity entity = ClientInfo.getClientPlayerWorld().getEntityByID(message.entityID);
                    if (entity instanceof IMaidAnimation) {
                        IMaidAnimation littlemaid = (IMaidAnimation) entity;

                        if (message.index == -1) {
                            littlemaid.setAnimation(IMaidAnimation.NO_ANIMATION);
                        } else {
                            littlemaid.setAnimation(littlemaid.getAnimations()[message.index]);
                        }
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}