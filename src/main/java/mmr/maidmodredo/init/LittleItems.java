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
    public static final Item APPLE_JUICE = new DrinkItem((new Item.Properties()).food(LittleFoods.APPLE_JUICE).group(LittleItemGroups.FOODS));

    public static final Item BLUEBERRY_PIE = new Item((new Item.Properties()).food(LittleFoods.PIE).group(LittleItemGroups.FOODS));
    public static final Item CHEESECAKE_PIE = new Item((new Item.Properties()).food(LittleFoods.PIE).group(LittleItemGroups.FOODS));
    public static final Item CHERRY_PIE = new Item((new Item.Properties()).food(LittleFoods.PIE).group(LittleItemGroups.FOODS));
    public static final Item CHOCOLATE_PIE = new Item((new Item.Properties()).food(LittleFoods.PIE).group(LittleItemGroups.FOODS));
    public static final Item STRAWBERRY_PIE = new Item((new Item.Properties()).food(LittleFoods.PIE).group(LittleItemGroups.FOODS));


    public static final Item COMBINED_DONUT = new Item((new Item.Properties()).food(LittleFoods.DONUT).group(LittleItemGroups.FOODS));
    public static final Item JELLY_DONUT = new Item((new Item.Properties()).food(LittleFoods.DONUT).group(LittleItemGroups.FOODS));
    public static final Item SUGAR_DONUT = new Item((new Item.Properties()).food(LittleFoods.DONUT).group(LittleItemGroups.FOODS));
    public static final Item POWDERD_DONUT = new Item((new Item.Properties()).food(LittleFoods.DONUT).group(LittleItemGroups.FOODS));


    public static final Item DEVILSFOOD_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.FOODS));
    public static final Item REDVELVET_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.FOODS));
    public static final Item CARROT_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.FOODS));
    public static final Item LAVENDER_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.FOODS));
    public static final Item ICECREAM_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.FOODS));
    public static final Item CHEESE_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.FOODS));
    public static final Item COFFEE_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.FOODS));
    public static final Item STRAWBERRY_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.FOODS));
    public static final Item BIRTHDAY_CAKE = new Item((new Item.Properties()).food(LittleFoods.CAKE).group(LittleItemGroups.FOODS));
    public static final Item BAGU_HAT = new BaguHatItem(MaidArmorMaterial.BAGU_HAT, EquipmentSlotType.HEAD, (new Item.Properties()).group(LittleItemGroups.MISC));

    public static final Item CARAMEL_APPLE = new Item((new Item.Properties()).food(LittleFoods.CARAMEL_APPLE).group(LittleItemGroups.FOODS));
    public static final Item BROOM = new BroomItem((new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item SUGARCOIN = new Item((new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item HOUSEWAND = new HouseWandItem((new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item JOBBOOK = new JobBookItem((new Item.Properties()).group(LittleItemGroups.MISC));


    public static final Item LITTLEMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.LITTLEMAID, 0xe3e3e3, 0xa45131, (new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item LITTLEBUTLER_SPAWNEGG = new SpawnEggItem(LittleEntitys.LITTLEBUTLER, 0xe3e3e3, 0xa45131, (new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item WANDERMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.WANDERMAID, 4547222, 15377456, (new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item ENDERMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.ENDERMAID, 1447446, 0, (new Item.Properties()).group(LittleItemGroups.MISC));

    public static final Item ZOMBIEMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.ZOMBIEMAID, 7969893, 0xe3e3e3, (new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item ZOMBIEBUTLER_SPAWNEGG = new SpawnEggItem(LittleEntitys.ZOMBIEBUTLER, 7969893, 0x382929, (new Item.Properties()).group(LittleItemGroups.MISC));



    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(APPLE_JUICE.setRegistryName("apple_juice"));

        event.getRegistry().register(BLUEBERRY_PIE.setRegistryName("blueberry_pie"));
        event.getRegistry().register(CHEESECAKE_PIE.setRegistryName("cheesecake_pie"));
        event.getRegistry().register(CHERRY_PIE.setRegistryName("cherry_pie"));
        event.getRegistry().register(CHOCOLATE_PIE.setRegistryName("chocolate_pie"));
        event.getRegistry().register(STRAWBERRY_PIE.setRegistryName("strawberry_pie"));

        event.getRegistry().register(COMBINED_DONUT.setRegistryName("combined_donut"));
        event.getRegistry().register(JELLY_DONUT.setRegistryName("jelly_donut"));
        event.getRegistry().register(SUGAR_DONUT.setRegistryName("sugar_donut"));
        event.getRegistry().register(POWDERD_DONUT.setRegistryName("powderd_donut"));

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
        event.getRegistry().register(LITTLEBUTLER_SPAWNEGG.setRegistryName("littlebutler_spawnegg"));
        event.getRegistry().register(WANDERMAID_SPAWNEGG.setRegistryName("wandermaid_spawnegg"));

        event.getRegistry().register(ENDERMAID_SPAWNEGG.setRegistryName("endermaid_spawnegg"));
        event.getRegistry().register(ZOMBIEMAID_SPAWNEGG.setRegistryName("zombiemaid_spawnegg"));
        event.getRegistry().register(ZOMBIEBUTLER_SPAWNEGG.setRegistryName("zombiebutler_spawnegg"));
    }
}
