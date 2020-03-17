package mmr.littledelicacies.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageTrackMaidWalkStat {
    private int entityID;
    private float forward;
    private float strafe;

    public MessageTrackMaidWalkStat() {
    }

    public MessageTrackMaidWalkStat(int entityID, float forward, float strafe) {
        this.entityID = entityID;
        this.forward = forward;
        this.strafe = strafe;
    }

    public MessageTrackMaidWalkStat(Entity maidEntity, float forward, float strafe) {
        this.entityID = maidEntity.getEntityId();
        this.forward = forward;
        this.strafe = strafe;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public static MessageTrackMaidWalkStat readPacketData(PacketBuffer buf) {
        return new MessageTrackMaidWalkStat(buf.readVarInt(), buf.readFloat(), buf.readFloat());
    }

    public void writePacketData(PacketBuffer buffer) {
        buffer.writeVarInt(this.entityID);
    }


    public static class Handler {
        public static void handle(MessageTrackMaidWalkStat message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            ctx.get().enqueueWork(() -> {

                Entity entity = ctx.get().getSender().getServerWorld().getEntityByID(message.entityID);
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;

                    livingEntity.moveForward = message.forward;
                    livingEntity.moveStrafing = message.strafe;
                }
            });
            context.setPacketHandled(true);
        }
    }
}