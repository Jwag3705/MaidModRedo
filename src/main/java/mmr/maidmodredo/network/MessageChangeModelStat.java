package mmr.maidmodredo.network;

import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageChangeModelStat {
    private int entityID;
    private CompoundNBT tag;

    public MessageChangeModelStat() {
    }

    public MessageChangeModelStat(int entityID, CompoundNBT tag) {
        this.entityID = entityID;
        this.tag = tag;
    }

    public MessageChangeModelStat(LittleMaidBaseEntity entityIn, CompoundNBT tag) {
        this.entityID = entityIn.getEntityId();
        this.tag = tag;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public static MessageChangeModelStat readPacketData(PacketBuffer buf) {
        int entityId = buf.readVarInt();
        CompoundNBT tag = buf.readCompoundTag();
        return new MessageChangeModelStat(entityId, tag);
    }

    public void writePacketData(PacketBuffer buffer) {
        buffer.writeVarInt(this.entityID);
        buffer.writeCompoundTag(this.tag);
    }


    public static class Handler {
        public static void handle(MessageChangeModelStat message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            ctx.get().enqueueWork(() -> {

                Entity entity = ctx.get().getSender().world.getEntityByID(message.entityID);
                if (entity instanceof LittleMaidBaseEntity) {
                    LittleMaidBaseEntity littlemaid = (LittleMaidBaseEntity) entity;

                    littlemaid.setTextureNameMain(message.tag.getString("Main"));
                    littlemaid.setTextureNameArmor(message.tag.getString("Armor"));
                }
            });
            context.setPacketHandled(true);
        }
    }
}