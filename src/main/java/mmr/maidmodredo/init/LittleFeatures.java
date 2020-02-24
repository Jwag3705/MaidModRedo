package mmr.maidmodredo.init;

import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.world.feature.BigTreeStructure;
import mmr.maidmodredo.world.feature.MaidCafeStructure;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MaidModRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleFeatures {

    public static final Structure<NoFeatureConfig> MAIDCAFE = new MaidCafeStructure(NoFeatureConfig::deserialize);
    public static final Structure<NoFeatureConfig> BIGTREE = new BigTreeStructure(NoFeatureConfig::deserialize);


    @SubscribeEvent
    public static void register(RegistryEvent.Register<Feature<?>> registry) {
        registry.getRegistry().register(MAIDCAFE.setRegistryName("maidcafe"));
        registry.getRegistry().register(BIGTREE.setRegistryName("bigtree"));
    }
}
