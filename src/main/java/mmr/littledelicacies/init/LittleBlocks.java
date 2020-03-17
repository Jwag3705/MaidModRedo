package mmr.littledelicacies.init;


import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.block.ChairBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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


    public static final Block OAK_CHAIR = new ChairBlock(Block.Properties.create(Material.WOOD).notSolid().harvestTool(ToolType.AXE).hardnessAndResistance(1.0F).sound(SoundType.WOOD));

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> registry) {
        //Terrain
        registry.getRegistry().register(MAGICALOAK_LOG.setRegistryName("magicaloak_log"));
        registry.getRegistry().register(OAK_LOG_AMBER.setRegistryName("oak_log_amber"));
        registry.getRegistry().register(SUGARN_PORTAL.setRegistryName("sugarn_portal"));

        registry.getRegistry().register(OAK_CHAIR.setRegistryName("oak_chair"));
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> registry) {
        registry.getRegistry().register(new BlockItem(MAGICALOAK_LOG, (new Item.Properties()).group(LittleItemGroups.MISC)).setRegistryName("magicaloak_log"));
        registry.getRegistry().register(new BlockItem(OAK_LOG_AMBER, (new Item.Properties()).group(LittleItemGroups.MISC)).setRegistryName("oak_log_amber"));

        registry.getRegistry().register(new BlockItem(SUGARN_PORTAL, (new Item.Properties())).setRegistryName("sugarn_portal"));


        registry.getRegistry().register(new BlockItem(OAK_CHAIR, (new Item.Properties()).group(LittleItemGroups.MISC)).setRegistryName("oak_chair"));
    }
}
