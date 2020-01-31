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
    public static final Schedule FREEDOM = new ScheduleBuilder(new Schedule()).add(10, Activity.IDLE).add(11000, Activity.REST).build();
    public static final Schedule FOLLOW = new ScheduleBuilder(new Schedule()).add(0, LittleActivitys.FOLLOW).build();
    public static final Schedule WAITING = new ScheduleBuilder(new Schedule()).add(0, LittleActivitys.WAITING).build();
    public static final Schedule LITTLEMAID_WORK = new ScheduleBuilder(new Schedule()).add(10, Activity.IDLE).add(2000, Activity.WORK).add(11000, Activity.IDLE).add(12000, Activity.REST).build();

    /*
     * manage the maid schedule here
     * performs a certain activity at a certain time
     */

    @SubscribeEvent
    public static void registerSchedule(RegistryEvent.Register<Schedule> event) {
        event.getRegistry().register(FREEDOM.setRegistryName("freedom"));
        event.getRegistry().register(FOLLOW.setRegistryName("follow"));
        event.getRegistry().register(WAITING.setRegistryName("waiting"));
        event.getRegistry().register(LITTLEMAID_WORK.setRegistryName("littlemaid_work"));
    }
}
