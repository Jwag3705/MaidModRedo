package mmr.littledelicacies.init;


import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.block.AwningBlock;
import mmr.littledelicacies.block.ChairBlock;
import net.minecraft.block.Block;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LittleDelicacies.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleBlocks {
    public static final Block MAGICALOAK_LOG = new Block(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.1F).sound(SoundType.WOOD));
    public static final Block OAK_LOG_AMBER = new Block(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.0F).lightValue(8).sound(SoundType.WOOD));
    public static final Block SUGARN_PORTAL = new Block(Block.Properties.create(Material.PORTAL).notSolid().hardnessAndResistance(10000.0F).lightValue(10).sound(SoundType.GLASS));
    public static final Block BLUE_SPOOKYMUSHROOM_BLOCK = new HugeMushroomBlock(Block.Properties.create(Material.WOOD, MaterialColor.BLUE).hardnessAndResistance(0.2F).sound(SoundType.WOOD));
    public static final Block GREEN_SPOOKYMUSHROOM_BLOCK = new HugeMushroomBlock(Block.Properties.create(Material.WOOD, MaterialColor.GREEN).hardnessAndResistance(0.2F).sound(SoundType.WOOD));
    public static final Block SPOOKYMUSHROOM_STEM = new HugeMushroomBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(0.2F).sound(SoundType.WOOD));

    public static final Block PINK_AWNING = new AwningBlock(Block.Properties.create(Material.WOOL).notSolid().doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.CLOTH));
    public static final Block PURPLE_AWNING = new AwningBlock(Block.Properties.create(Material.WOOL).notSolid().doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.CLOTH));

    public static final Block OAK_CHAIR = new ChairBlock(Block.Properties.create(Material.WOOD).notSolid().harvestTool(ToolType.AXE).hardnessAndResistance(1.0F).sound(SoundType.WOOD));

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> registry) {
        //Terrain
        registry.getRegistry().register(MAGICALOAK_LOG.setRegistryName("magicaloak_log"));
        registry.getRegistry().register(OAK_LOG_AMBER.setRegistryName("oak_log_amber"));
        registry.getRegistry().register(SUGARN_PORTAL.setRegistryName("sugarn_portal"));

        registry.getRegistry().register(BLUE_SPOOKYMUSHROOM_BLOCK.setRegistryName("blue_spooky_mushroom_block"));
        registry.getRegistry().register(GREEN_SPOOKYMUSHROOM_BLOCK.setRegistryName("green_spooky_mushroom_block"));
        registry.getRegistry().register(SPOOKYMUSHROOM_STEM.setRegistryName("spooky_mushroom_stem"));

        registry.getRegistry().register(PINK_AWNING.setRegistryName("pink_awning"));
        registry.getRegistry().register(PURPLE_AWNING.setRegistryName("purple_awning"));
        registry.getRegistry().register(OAK_CHAIR.setRegistryName("oak_chair"));
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> registry) {
        registry.getRegistry().register(new BlockItem(MAGICALOAK_LOG, (new Item.Properties()).group(LittleItemGroups.MISC)).setRegistryName("magicaloak_log"));
        registry.getRegistry().register(new BlockItem(OAK_LOG_AMBER, (new Item.Properties()).group(LittleItemGroups.MISC)).setRegistryName("oak_log_amber"));

        registry.getRegistry().register(new BlockItem(SUGARN_PORTAL, (new Item.Properties())).setRegistryName("sugarn_portal"));

        registerToItem(registry, new BlockItem(PINK_AWNING, (new Item.Properties()).group(LittleItemGroups.DECOR_FUNITURE)));
        registerToItem(registry, new BlockItem(PURPLE_AWNING, (new Item.Properties()).group(LittleItemGroups.DECOR_FUNITURE)));

        registry.getRegistry().register(new BlockItem(OAK_CHAIR, (new Item.Properties()).group(LittleItemGroups.DECOR_FUNITURE)).setRegistryName("oak_chair"));

        registerToItem(registry, new BlockItem(BLUE_SPOOKYMUSHROOM_BLOCK, (new Item.Properties()).group(LittleItemGroups.MISC)));
        registerToItem(registry, new BlockItem(GREEN_SPOOKYMUSHROOM_BLOCK, (new Item.Properties()).group(LittleItemGroups.MISC)));
        registerToItem(registry, new BlockItem(SPOOKYMUSHROOM_STEM, (new Item.Properties()).group(LittleItemGroups.MISC)));
    }

    public static void registerToItem(RegistryEvent.Register<Item> registry, Item item) {

        if (item instanceof BlockItem && item.getRegistryName() == null) {
            item.setRegistryName(((BlockItem) item).getBlock().getRegistryName());

            Item.BLOCK_TO_ITEM.put(((BlockItem) item).getBlock(), item);
        }

        registry.getRegistry().register(item);
    }
}
