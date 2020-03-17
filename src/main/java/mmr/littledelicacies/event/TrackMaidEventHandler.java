package mmr.littledelicacies.event;

import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.api.trackmaid.TrackMaidProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LittleDelicacies.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TrackMaidEventHandler {
    @SubscribeEvent
    public static void onControlEvent(TickEvent.PlayerTickEvent event) {
        PlayerEntity playerEntity = event.player;

        Minecraft mc = Minecraft.getInstance();

        //Still testing
        if (event.side == LogicalSide.SERVER) {
            playerEntity.getCapability(TrackMaidProvider.TRACKMAID_CAPABILITY).ifPresent(cap -> {
                if (cap.getLinkedEntity(((ServerWorld) playerEntity.world)) != null) {

                    /*if(cap.getLinkedEntity(((ServerWorld) playerEntity.world)) instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) cap.getLinkedEntity(((ServerWorld) playerEntity.world));
                        livingEntity.moveStrafing = playerEntity.moveStrafing;
                       livingEntity.moveForward = playerEntity.moveForward;
                        livingEntity.moveVertical = playerEntity.moveVertical;
                    }*/
                }
            });
        }
    }
}