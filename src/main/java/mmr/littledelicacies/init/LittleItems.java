package mmr.littledelicacies.init;

import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.client.render.item.LongSpearItemRender;
import mmr.littledelicacies.client.render.item.MageStuffItemRender;
import mmr.littledelicacies.item.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LittleDelicacies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleItems {
    public static final Item APPLE_JUICE = new DrinkItem((new Item.Properties()).food(LittleFoods.APPLE_JUICE).group(LittleItemGroups.FOODS));
    public static final Item BUBBLE_TEA = new DrinkItem((new Item.Properties()).food(LittleFoods.TEA).group(LittleItemGroups.FOODS));
    public static final Item ESPRESSO = new DrinkItem((new Item.Properties()).food(LittleFoods.TEA).group(LittleItemGroups.FOODS));
    public static final Item MOCA = new DrinkItem((new Item.Properties()).food(LittleFoods.TEA).group(LittleItemGroups.FOODS));
    public static final Item GREEN_TEA = new DrinkItem((new Item.Properties()).food(LittleFoods.TEA).group(LittleItemGroups.FOODS));

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

    public static final Item POCKY = new Item((new Item.Properties()).food(LittleFoods.POCKY).group(LittleItemGroups.FOODS));
    public static final Item POCKY_STRAWBERRY = new Item((new Item.Properties()).food(LittleFoods.POCKY).group(LittleItemGroups.FOODS));
    public static final Item POCKY_GREENTEA = new Item((new Item.Properties()).food(LittleFoods.POCKY).group(LittleItemGroups.FOODS));

    public static final Item COOKIE = new Item((new Item.Properties()).food(LittleFoods.COOKIE).group(LittleItemGroups.FOODS));
    public static final Item FORTUNE_COOKIE = new Item((new Item.Properties()).food(LittleFoods.COOKIE).group(LittleItemGroups.FOODS));
    public static final Item DOLL_COOKIE = new Item((new Item.Properties()).food(LittleFoods.DOLL_COOKIE).group(LittleItemGroups.FOODS));
    public static final Item JAM_COOKIE = new Item((new Item.Properties()).food(LittleFoods.COOKIE).group(LittleItemGroups.FOODS));

    public static final Item MACAROON_GREENTEA = new Item((new Item.Properties()).food(LittleFoods.MACAROON).group(LittleItemGroups.FOODS));
    public static final Item MACAROON_LEMON = new Item((new Item.Properties()).food(LittleFoods.MACAROON).group(LittleItemGroups.FOODS));
    public static final Item MACAROON_CHOCOLATE = new Item((new Item.Properties()).food(LittleFoods.MACAROON).group(LittleItemGroups.FOODS));
    public static final Item MACAROON_CARAMEL = new Item((new Item.Properties()).food(LittleFoods.MACAROON).group(LittleItemGroups.FOODS));
    public static final Item MACAROON_STRAWBERRY = new Item((new Item.Properties()).food(LittleFoods.MACAROON).group(LittleItemGroups.FOODS));

    public static final Item DAIFUKU_WAG = new Item((new Item.Properties()).food(LittleFoods.DAIFUKU).group(LittleItemGroups.FOODS));
    public static final Item SAKURA_WAG = new Item((new Item.Properties()).food(LittleFoods.DAIFUKU).group(LittleItemGroups.FOODS));
    public static final Item MIYAGE = new Item((new Item.Properties()).food(LittleFoods.DAIFUKU).group(LittleItemGroups.FOODS));
    public static final Item RAINDROP_CAKE = new Item((new Item.Properties()).food(LittleFoods.DAIFUKU).group(LittleItemGroups.FOODS));
    public static final Item ICECREAM_TAIYAKI = new Item((new Item.Properties()).food(LittleFoods.ICECREAM_TAIYAKI).group(LittleItemGroups.FOODS));
    public static final Item TAIYAKI = new Item((new Item.Properties()).food(LittleFoods.TAIYAKI).group(LittleItemGroups.FOODS));


    public static final Item BAGU_HAT = new BaguHatItem(MaidArmorMaterial.BAGU_HAT, EquipmentSlotType.HEAD, (new Item.Properties()).maxStackSize(1).group(LittleItemGroups.MISC));

    public static final Item MAGE_STUFF = new Item((new Item.Properties()).maxStackSize(1).setISTER(() -> MageStuffItemRender::new).group(LittleItemGroups.COMBAT));

    public static final Item LONG_SPEAR = new LongSpearItem((new Item.Properties()).maxStackSize(1).setISTER(() -> LongSpearItemRender::new).group(LittleItemGroups.COMBAT));

    public static final Item CARAMEL_APPLE = new Item((new Item.Properties()).food(LittleFoods.CARAMEL_APPLE).group(LittleItemGroups.FOODS));
    public static final Item BROOM = new BroomItem((new Item.Properties()).maxStackSize(1).group(LittleItemGroups.COMBAT));
    public static final Item BUTCHER_KNIFE = new ButcherKnifeItem((new Item.Properties()).group(LittleItemGroups.COMBAT));
    public static final Item SUGARCOIN = new Item((new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item AMBER = new Item((new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item HOUSEWAND = new HouseWandItem((new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item JOBBOOK = new JobBookItem((new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item SUGAR_TOTEM = new SugarTotemItem((new Item.Properties()).group(LittleItemGroups.MISC));

    public static final Item LITTLEMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.LITTLEMAID, 0xe3e3e3, 0xa45131, (new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item LITTLEBUTLER_SPAWNEGG = new SpawnEggItem(LittleEntitys.LITTLEBUTLER, 0xe3e3e3, 0xa45131, (new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item WANDERMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.WANDERMAID, 4547222, 15377456, (new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item ENDERMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.ENDERMAID, 0xe3e3e3, 0xa45131, (new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item COWGIRL_SPAWNEGG = new SpawnEggItem(LittleEntitys.COWGIRL, 0xffffff, 0, (new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item SQURRIELMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.SQURRIEL_MAID, 0xc29d80, 0xa37354, (new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item SQURRIELBUTLER_SPAWNEGG = new SpawnEggItem(LittleEntitys.SQURRIEL_BUTLER, 0xc29d80, 0xa37354, (new Item.Properties()).group(LittleItemGroups.MISC));

    public static final Item PLANTER_SPAWNEGG = new SpawnEggItem(LittleEntitys.PLANTER, 0xb6ff75, 0x64a34a, (new Item.Properties()).group(LittleItemGroups.MISC));

    public static final Item ZOMBIEMAID_SPAWNEGG = new SpawnEggItem(LittleEntitys.ZOMBIEMAID, 7969893, 0xe3e3e3, (new Item.Properties()).group(LittleItemGroups.MISC));
    public static final Item ZOMBIEBUTLER_SPAWNEGG = new SpawnEggItem(LittleEntitys.ZOMBIEBUTLER, 7969893, 0x382929, (new Item.Properties()).group(LittleItemGroups.MISC));



    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(APPLE_JUICE.setRegistryName("apple_juice"));
        event.getRegistry().register(BUBBLE_TEA.setRegistryName("bubble_tea"));
        event.getRegistry().register(ESPRESSO.setRegistryName("espresso"));
        event.getRegistry().register(MOCA.setRegistryName("moca"));
        event.getRegistry().register(GREEN_TEA.setRegistryName("green_tea"));

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

        event.getRegistry().register(POCKY.setRegistryName("pocky"));
        event.getRegistry().register(POCKY_STRAWBERRY.setRegistryName("pocky_strawberry"));
        event.getRegistry().register(POCKY_GREENTEA.setRegistryName("pocky_greentea"));

        event.getRegistry().register(COOKIE.setRegistryName("cookie"));
        event.getRegistry().register(FORTUNE_COOKIE.setRegistryName("fortune_cookie"));
        event.getRegistry().register(DOLL_COOKIE.setRegistryName("doll_cookie"));
        event.getRegistry().register(JAM_COOKIE.setRegistryName("jam_cookie"));

        event.getRegistry().register(MACAROON_GREENTEA.setRegistryName("macaroon_greentea"));
        event.getRegistry().register(MACAROON_LEMON.setRegistryName("macaroon_lemon"));
        event.getRegistry().register(MACAROON_CHOCOLATE.setRegistryName("macaroon_chocolate"));
        event.getRegistry().register(MACAROON_CARAMEL.setRegistryName("macaroon_caramel"));
        event.getRegistry().register(MACAROON_STRAWBERRY.setRegistryName("macaroon_strawberry"));

        event.getRegistry().register(DAIFUKU_WAG.setRegistryName("daifuku_wag"));
        event.getRegistry().register(SAKURA_WAG.setRegistryName("sakura_wag"));
        event.getRegistry().register(MIYAGE.setRegistryName("miyage"));
        event.getRegistry().register(RAINDROP_CAKE.setRegistryName("raindrop_cake"));
        event.getRegistry().register(ICECREAM_TAIYAKI.setRegistryName("icecream_taiyaki"));
        event.getRegistry().register(TAIYAKI.setRegistryName("taiyaki"));

        event.getRegistry().register(BAGU_HAT.setRegistryName("bagu_hat"));

        event.getRegistry().register(MAGE_STUFF.setRegistryName("mage_stuff"));

        event.getRegistry().register(LONG_SPEAR.setRegistryName("long_spear"));

        event.getRegistry().register(CARAMEL_APPLE.setRegistryName("caramel_apple"));
        event.getRegistry().register(BROOM.setRegistryName("broom"));
        event.getRegistry().register(BUTCHER_KNIFE.setRegistryName("butcher_knife"));
        event.getRegistry().register(SUGARCOIN.setRegistryName("sugar_coin"));
        event.getRegistry().register(AMBER.setRegistryName("amber"));
        event.getRegistry().register(HOUSEWAND.setRegistryName("housewand"));
        event.getRegistry().register(JOBBOOK.setRegistryName("job_book"));
        event.getRegistry().register(SUGAR_TOTEM.setRegistryName("sugar_totem"));

        event.getRegistry().register(LITTLEMAID_SPAWNEGG.setRegistryName("littlemaid_spawnegg"));
        event.getRegistry().register(LITTLEBUTLER_SPAWNEGG.setRegistryName("littlebutler_spawnegg"));
        event.getRegistry().register(WANDERMAID_SPAWNEGG.setRegistryName("wandermaid_spawnegg"));
        event.getRegistry().register(ENDERMAID_SPAWNEGG.setRegistryName("endermaid_spawnegg"));
        event.getRegistry().register(COWGIRL_SPAWNEGG.setRegistryName("cowgirl_spawnegg"));
        event.getRegistry().register(SQURRIELMAID_SPAWNEGG.setRegistryName("squirrelmaid_spawnegg"));
        event.getRegistry().register(SQURRIELBUTLER_SPAWNEGG.setRegistryName("squirrelbutler_spawnegg"));
        event.getRegistry().register(PLANTER_SPAWNEGG.setRegistryName("planter_spawnegg"));

        event.getRegistry().register(ZOMBIEMAID_SPAWNEGG.setRegistryName("zombiemaid_spawnegg"));
        event.getRegistry().register(ZOMBIEBUTLER_SPAWNEGG.setRegistryName("zombiebutler_spawnegg"));
    }
}
