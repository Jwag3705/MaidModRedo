package mmr.maidmodredo.init;


import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.block.ChairBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MaidModRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleBlocks {
    public static final Block OAK_CHAIR = new ChairBlock(Block.Properties.create(Material.WOOD).tickRandomly().harvestTool(ToolType.AXE).hardnessAndResistance(1.0F).sound(SoundType.WOOD));

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> registry) {
        //Terrain
        registry.getRegistry().register(OAK_CHAIR.setRegistryName("oak_chair"));
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> registry) {
        registry.getRegistry().register(new BlockItem(OAK_CHAIR, (new Item.Properties()).group(LittleItemGroups.MISC)).setRegistryName("oak_chair"));
    }
}
