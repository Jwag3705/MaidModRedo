package mmr.maidmodredo.init;

import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.client.screen.MaidInventoryScreen;
import mmr.maidmodredo.entity.LittleMaidEntity;
import mmr.maidmodredo.inventory.MaidInventoryContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MaidModRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleContainers {
    public static final ContainerType<MaidInventoryContainer> MAID_INVENTORY = IForgeContainerType.create((windowId, inv, data) -> {
        Entity entity = inv.player.world.getEntityByID(data.readInt());

        if (entity instanceof LittleMaidEntity) {

            return new MaidInventoryContainer(windowId, inv, (LittleMaidEntity) entity);

        } else {
            return null;
        }
    });

    @SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(MAID_INVENTORY.setRegistryName("maid_inventory"));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreenFactories() {
        ScreenManager.registerFactory(LittleContainers.MAID_INVENTORY, MaidInventoryScreen::new);
    }
}