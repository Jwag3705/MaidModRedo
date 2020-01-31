package mmr.maidmodredo.init;

import mmr.maidmodredo.MaidModRedo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = MaidModRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MaidMemoryModuleType {

    public static final MemoryModuleType<LivingEntity> TARGET_HOSTILES = new MemoryModuleType<>(Optional.empty());

    @SubscribeEvent
    public static void registerSensor(RegistryEvent.Register<MemoryModuleType<?>> event) {
        event.getRegistry().register(TARGET_HOSTILES.setRegistryName("target_hostiles"));
    }
}