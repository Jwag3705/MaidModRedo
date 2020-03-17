package mmr.littledelicacies.init;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleDataGenerators {

    @SubscribeEvent
    public static void onItemRegistry(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeClient()) {

            // 今後こちらにはクライアント用のモデル関連やlangのProviderが追加される予定

        }
    }
}
