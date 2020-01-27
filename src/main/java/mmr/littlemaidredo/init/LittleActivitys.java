package mmr.littlemaidredo.init;

import mmr.littlemaidredo.LittleMaidRedo;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LittleMaidRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleActivitys {
    public static final Activity ATTACK = new Activity("littlemaid.attack");

    @SubscribeEvent
    public static void registerActivity(RegistryEvent.Register<Activity> event) {
        event.getRegistry().register(ATTACK.setRegistryName("attack"));

    }
}
