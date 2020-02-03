package mmr.maidmodredo.init;

import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.item.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MaidModRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleItems {
    public static final Item APPLE_JUICE = new DrinkItem((new Item.Properties()).food(LittleFoods.APPLE_JUICE).group(LittleItemGroups.LITTLEMAID));

    public static final Item DEVILSFOOD_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.LITTLEMAID));
    public static final Item REDVELVET_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.LITTLEMAID));
    public static final Item CARROT_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.LITTLEMAID));
    public static final Item LAVENDER_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.LITTLEMAID));
    public static final Item ICECREAM_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.LITTLEMAID));
    public static final Item CHEESE_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.LITTLEMAID));
    public static final Item COFFEE_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.LITTLEMAID));
    public static final Item STRAWBERRY_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.LITTLEMAID));
    public static final Item BIRTHDAY_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.LITTLEMAID));
    public static final Item BAGU_HAT = new BaguHatItem(MaidArmorMaterial.BAGU_HAT, EquipmentSlotType.HEAD, (new Item.Properties()).group(LittleItemGroups.LITTLEMAID));

    public static final Item CARAMEL_APPLE = new Item((new Item.Properties()).food(LittleFoods.CARAMEL_APPLE).group(LittleItemGroups.LITTLEMAID));
    public static final Item BROOM = new BroomItem((new Item.Properties()).group(LittleItemGroups.LITTLEMAID));
    public static final Item SUGARCOIN = new Item((new Item.Properties()).group(LittleItemGroups.LITTLEMAID));
    public static final Item HOUSEWAND = new HouseWandItem((new Item.Properties()).group(LittleItemGroups.LITTLEMAID));
    public static final Item JOBBOOK = new JobBookItem((new Item.Properties()).group(LittleItemGroups.LITTLEMAID));


    public static final Item LITTLEMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.LITTLEMAID, 0xe3e3e3, 0xa45131, (new Item.Properties()).group(LittleItemGroups.LITTLEMAID));
    public static final Item WANDERMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.WANDERMAID, 4547222, 15377456, (new Item.Properties()).group(LittleItemGroups.LITTLEMAID));

    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(APPLE_JUICE.setRegistryName("apple_juice"));

        event.getRegistry().register(DEVILSFOOD_CAKE.setRegistryName("devilsfood_cake"));
        event.getRegistry().register(REDVELVET_CAKE.setRegistryName("redvelvet_cake"));
        event.getRegistry().register(CARROT_CAKE.setRegistryName("carrot_cake"));
        event.getRegistry().register(LAVENDER_CAKE.setRegistryName("lavender_cake"));
        event.getRegistry().register(ICECREAM_CAKE.setRegistryName("icecream_cake"));
        event.getRegistry().register(CHEESE_CAKE.setRegistryName("cheese_cake"));
        event.getRegistry().register(COFFEE_CAKE.setRegistryName("coffee_cake"));
        event.getRegistry().register(STRAWBERRY_CAKE.setRegistryName("strawberry_cake"));
        event.getRegistry().register(BIRTHDAY_CAKE.setRegistryName("birthday_cake"));

        event.getRegistry().register(BAGU_HAT.setRegistryName("bagu_hat"));

        event.getRegistry().register(CARAMEL_APPLE.setRegistryName("caramel_apple"));
        event.getRegistry().register(BROOM.setRegistryName("broom"));
        event.getRegistry().register(SUGARCOIN.setRegistryName("sugar_coin"));
        event.getRegistry().register(HOUSEWAND.setRegistryName("housewand"));
        event.getRegistry().register(JOBBOOK.setRegistryName("job_book"));
        event.getRegistry().register(LITTLEMAID_SPAWNEGG.setRegistryName("littlemaid_spawnegg"));
        event.getRegistry().register(WANDERMAID_SPAWNEGG.setRegistryName("wandermaid_spawnegg"));
    }
}
