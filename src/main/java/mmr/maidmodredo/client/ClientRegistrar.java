package mmr.maidmodredo.client;

import mmr.maidmodredo.client.render.*;
import mmr.maidmodredo.init.LittleBlocks;
import mmr.maidmodredo.init.LittleContainers;
import mmr.maidmodredo.init.LittleEntitys;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientRegistrar {
    public static void renderEntity() {
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.WANDERMAID, WanderMaidRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.LITTLEMAID, LittleMaidBaseRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.LITTLEBUTLER, LittleButlerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.ZOMBIEMAID, ZombieMaidRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.ZOMBIEBUTLER, ZombieButlerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.ENDERMAID, EnderMaidRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.SUGAR_PHANTOM, SugarPhantomRender::new);

        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.TRINITY, TrinityRender::new);

        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.MAID_FISHING_BOBBER, MaidFishingBobberRender::new);
    }

    public static void renderTileEntity() {
    }

    public static void renderBlock() {
        RenderTypeLookup.setRenderLayer(LittleBlocks.SUGARN_PORTAL, RenderType.translucent());
    }

    public static void setup(final FMLCommonSetupEvent event) {
        ClientRegistrar.renderEntity();
        ClientRegistrar.renderBlock();

        LittleContainers.registerScreenFactories();
    }
}
