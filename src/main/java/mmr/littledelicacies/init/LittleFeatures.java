package mmr.littledelicacies.init;

import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.world.feature.BigTreeStructure;
import mmr.littledelicacies.world.feature.MaidCafeStructure;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LittleDelicacies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleFeatures {

    public static final Structure<NoFeatureConfig> MAIDCAFE = new MaidCafeStructure(NoFeatureConfig::deserialize);
    public static final Structure<NoFeatureConfig> BIGTREE = new BigTreeStructure(NoFeatureConfig::deserialize);


    @SubscribeEvent
    public static void register(RegistryEvent.Register<Feature<?>> registry) {
        registry.getRegistry().register(MAIDCAFE.setRegistryName("maidcafe"));
        registry.getRegistry().register(BIGTREE.setRegistryName("bigtree"));
    }
}
