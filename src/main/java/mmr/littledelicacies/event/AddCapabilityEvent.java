package mmr.littledelicacies.event;

import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.api.trackmaid.TrackMaidProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LittleDelicacies.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AddCapabilityEvent {

    private static final ResourceLocation TRACK_MAID = new ResourceLocation(LittleDelicacies.MODID, "track_maid");

    @SubscribeEvent
    public static void onAttachCapabilitiesToEntity(AttachCapabilitiesEvent<Entity> e) {
        //For player capabilities
        if (e.getObject() instanceof PlayerEntity) {

            e.addCapability(TRACK_MAID, new TrackMaidProvider());
        }

    }


}
