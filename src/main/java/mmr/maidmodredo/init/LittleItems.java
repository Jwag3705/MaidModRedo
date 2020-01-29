package mmr.maidmodredo.init;

import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.item.BroomItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MaidModRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleItems {
    public static final Item CARAMEL_APPLE = new Item((new Item.Properties()).group(LittleItemGroups.LITTLEMAID));
    public static final Item BROOM = new BroomItem((new Item.Properties()).group(LittleItemGroups.LITTLEMAID));

    public static final Item LITTLEMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.LITTLEMAID, 0xe3e3e3, 0xa45131, (new Item.Properties()).group(LittleItemGroups.LITTLEMAID));
    public static final Item WANDERMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.WANDERMAID, 4547222, 15377456, (new Item.Properties()).group(LittleItemGroups.LITTLEMAID));

    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(CARAMEL_APPLE.setRegistryName("caramel_apple"));
        event.getRegistry().register(BROOM.setRegistryName("broom"));
        event.getRegistry().register(LITTLEMAID_SPAWNEGG.setRegistryName("littlemaid_spawnegg"));
        event.getRegistry().register(WANDERMAID_SPAWNEGG.setRegistryName("wandermaid_spawnegg"));
    }
}
