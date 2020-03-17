package mmr.littledelicacies.init;

import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.entity.sensor.MaidHostilesSensor;
import mmr.littledelicacies.entity.sensor.OwnerHurtByTargetSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = LittleDelicacies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleSensorTypes {

    public static final SensorType<MaidHostilesSensor> MAID_HOSTILES = register(MaidHostilesSensor::new);
    public static final SensorType<OwnerHurtByTargetSensor> DEFEND_OWNER = register(OwnerHurtByTargetSensor::new);

    private static <U extends Sensor<?>> SensorType<U> register(Supplier<U> p_220996_1_) {
        return new SensorType<>(p_220996_1_);
    }

    @SubscribeEvent
    public static void registerSensor(RegistryEvent.Register<SensorType<?>> event) {
        event.getRegistry().register(MAID_HOSTILES.setRegistryName("maid_hostiles"));
        event.getRegistry().register(DEFEND_OWNER.setRegistryName("defend_owner"));
    }
}

