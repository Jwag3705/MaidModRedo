package mmr.littledelicacies.init;

import mmr.littledelicacies.LittleDelicacies;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LittleDelicacies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleActivitys {
    public static final Activity LUMBERJACK = new Activity("littlemaid.lumberjack");
    public static final Activity ATTACK = new Activity("littlemaid.attack");
    public static final Activity SHOT = new Activity("littlemaid.shot");
    public static final Activity DUAL_BLADER = new Activity("littlemaid.dual_blader");
    public static final Activity SHIELDER = new Activity("littlemaid.shielder");
    public static final Activity SIMPLE_CASTER = new Activity("littlemaid.simple_caster");
    public static final Activity SUPORT_CASTER = new Activity("littlemaid.suport_caster");
    public static final Activity FOLLOW = new Activity("littlemaid.follow");
    public static final Activity WAITING = new Activity("littlemaid.waiting");

    @SubscribeEvent
    public static void registerActivity(RegistryEvent.Register<Activity> event) {
        event.getRegistry().register(LUMBERJACK.setRegistryName("lumberjack"));
        event.getRegistry().register(ATTACK.setRegistryName("attack"));
        event.getRegistry().register(SHOT.setRegistryName("shot"));
        event.getRegistry().register(DUAL_BLADER.setRegistryName("dual_blader"));
        event.getRegistry().register(SHIELDER.setRegistryName("shielder"));
        event.getRegistry().register(SIMPLE_CASTER.setRegistryName("simple_caster"));
        event.getRegistry().register(SUPORT_CASTER.setRegistryName("suport_caster"));
        event.getRegistry().register(FOLLOW.setRegistryName("follow"));
        event.getRegistry().register(WAITING.setRegistryName("waiting"));
    }
}
