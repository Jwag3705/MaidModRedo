package mmr.maidmodredo.network;

import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.entity.LittleMaidEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MaidPacketHandler {
    public static final String NETWORK_PROTOCOL = "2";

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MaidModRedo.MODID, "net"))
            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    public static void register() {
        CHANNEL.messageBuilder(MessageChangeModelStat.class, 0)
                .encoder(MessageChangeModelStat::writePacketData).decoder(MessageChangeModelStat::readPacketData)
                .consumer(MessageChangeModelStat.Handler::handle)
                .add();
    }

    public static void syncModel(LittleMaidEntity entity, CompoundNBT compoundNBT) {
        MaidPacketHandler.CHANNEL.sendToServer(new MessageChangeModelStat(entity, compoundNBT));
    }
}