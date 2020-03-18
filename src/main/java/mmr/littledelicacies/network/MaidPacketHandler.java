package mmr.littledelicacies.network;

import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.api.IMaidAnimation;
import mmr.littledelicacies.api.MaidAnimation;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.MaidJob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.commons.lang3.ArrayUtils;

public class MaidPacketHandler {
    public static final String NETWORK_PROTOCOL = "2";

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(LittleDelicacies.MODID, "net"))
            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    public static void register() {
        CHANNEL.messageBuilder(MessageChangeModelStat.class, 0)
                .encoder(MessageChangeModelStat::writePacketData).decoder(MessageChangeModelStat::readPacketData)
                .consumer(MessageChangeModelStat.Handler::handle)
                .add();
        CHANNEL.messageBuilder(MessageAnimation.class, 1)
                .encoder(MessageAnimation::writePacketData).decoder(MessageAnimation::readPacketData)
                .consumer(MessageAnimation.Handler::handle)
                .add();
        CHANNEL.messageBuilder(MessageMaidJobSet.class, 2)
                .encoder(MessageMaidJobSet::writePacketData).decoder(MessageMaidJobSet::readPacketData)
                .consumer(MessageMaidJobSet.Handler::handle)
                .add();

        CHANNEL.messageBuilder(MessageTrackMaidWalkStat.class, 3)
                .encoder(MessageTrackMaidWalkStat::writePacketData).decoder(MessageTrackMaidWalkStat::readPacketData)
                .consumer(MessageTrackMaidWalkStat.Handler::handle)
                .add();
        CHANNEL.messageBuilder(MessageMaidNameSet.class, 4)
                .encoder(MessageMaidNameSet::writePacketData).decoder(MessageMaidNameSet::readPacketData)
                .consumer(MessageMaidNameSet.Handler::handle)
                .add();
    }

    public static void syncModel(LittleMaidBaseEntity entity, CompoundNBT compoundNBT) {
        MaidPacketHandler.CHANNEL.sendToServer(new MessageChangeModelStat(entity, compoundNBT));
    }

    public static void syncMaidJob(LittleMaidBaseEntity entity, MaidJob job) {
        MaidPacketHandler.CHANNEL.sendToServer(new MessageMaidJobSet(entity, job));
    }

    public static void syncMaidName(LittleMaidBaseEntity entity, String name) {
        MaidPacketHandler.CHANNEL.sendToServer(new MessageMaidNameSet(entity, name));
    }


    public static void syncTrackMaidWalk(Entity entity, float forward, float strafe) {
        MaidPacketHandler.CHANNEL.sendToServer(new MessageTrackMaidWalkStat(entity, forward, strafe));
    }


    public static void animationModel(LittleMaidBaseEntity entity, MaidAnimation animation) {
        if (!entity.getEntityWorld().isRemote()) {
            entity.setAnimation(animation);
            MaidPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new MessageAnimation(entity.getEntityId(), ArrayUtils.indexOf(entity.getAnimations(), animation)));
        }
    }

    public static void animationModel(LivingEntity entity, MaidAnimation animation) {
        if (!entity.getEntityWorld().isRemote()) {
            if (entity instanceof IMaidAnimation) {
                ((IMaidAnimation) entity).setAnimation(animation);
                MaidPacketHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new MessageAnimation(entity.getEntityId(), ArrayUtils.indexOf(((IMaidAnimation) entity).getAnimations(), animation)));
            }
        }
    }
}