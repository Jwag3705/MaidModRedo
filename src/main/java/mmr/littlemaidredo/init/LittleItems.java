package mmr.littlemaidredo.init;

import mmr.littlemaidredo.LittleMaidRedo;
import mmr.littlemaidredo.item.BroomItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LittleMaidRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleItems {
    public static final Item BROOM = new BroomItem((new Item.Properties()).group(LittleItemGroups.LITTLEMAID));
    public static final Item WANDERMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.WANDERMAID, 4547222, 15377456, (new Item.Properties()).group(LittleItemGroups.LITTLEMAID));

    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(BROOM.setRegistryName("broom"));
        event.getRegistry().register(WANDERMAID_SPAWNEGG.setRegistryName("wandermaid_spawnegg"));
    }
}
