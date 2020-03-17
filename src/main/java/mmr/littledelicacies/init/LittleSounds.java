package mmr.littledelicacies.init;

import mmr.littledelicacies.LittleDelicacies;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LittleDelicacies.MODID)
public final class LittleSounds {
    public static final SoundEvent ROOT_CRACK = createEvent("entity.root.creak");
    public static final SoundEvent LITTLEBUTLER_HI = createEvent("entity.littlebutler.hi");
    public static final SoundEvent LITTLEBUTLER_IDLE = createEvent("entity.littlebutler.idle");


    private static SoundEvent createEvent(String name) {

        SoundEvent sound = new SoundEvent(new ResourceLocation(LittleDelicacies.MODID, name));

        sound.setRegistryName(new ResourceLocation(LittleDelicacies.MODID, name));

        return sound;
    }


    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) {
        evt.getRegistry().register(ROOT_CRACK);
        evt.getRegistry().register(LITTLEBUTLER_HI);
        evt.getRegistry().register(LITTLEBUTLER_IDLE);
    }

    private LittleSounds() {

    }
}