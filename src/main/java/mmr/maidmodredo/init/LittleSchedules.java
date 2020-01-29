package mmr.maidmodredo.init;

import mmr.maidmodredo.MaidModRedo;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.schedule.ScheduleBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MaidModRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleSchedules {
    public static final Schedule WILDMAID = new ScheduleBuilder(new Schedule()).add(10, Activity.IDLE).add(11000, Activity.REST).build();
    public static final Schedule FOLLOW = new ScheduleBuilder(new Schedule()).add(0, LittleActivitys.FOLLOW).build();
    public static final Schedule WAITING = new ScheduleBuilder(new Schedule()).add(0, LittleActivitys.WAITING).build();


    @SubscribeEvent
    public static void registerActivity(RegistryEvent.Register<Schedule> event) {
        event.getRegistry().register(WILDMAID.setRegistryName("wildmaid"));
        event.getRegistry().register(FOLLOW.setRegistryName("follow"));
        event.getRegistry().register(WAITING.setRegistryName("waiting"));
    }
}
