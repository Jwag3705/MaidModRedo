package mmr.littledelicacies.event;

import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.api.trackmaid.TrackMaidProvider;
import mmr.littledelicacies.network.MaidPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LittleDelicacies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TrackMaidEventHandler {
    @SubscribeEvent
    public static void onControlEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity playerEntity = event.player;

        Minecraft mc = Minecraft.getInstance();

        if (playerEntity == mc.player && playerEntity.world instanceof ServerWorld) {
            playerEntity.getCapability(TrackMaidProvider.TRACKMAID_CAPABILITY).ifPresent(cap -> {
                if (cap.getLinkedEntity(((ServerWorld) playerEntity.world)) != null) {
                    MaidPacketHandler.syncTrackMaidWalk(cap.getLinkedEntity(((ServerWorld) playerEntity.world)), mc.player.movementInput.moveForward, mc.player.movementInput.moveStrafe);
                }
            });
        }
    }
}