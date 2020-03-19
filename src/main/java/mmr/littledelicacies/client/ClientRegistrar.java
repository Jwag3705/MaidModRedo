package mmr.littledelicacies.client;

import mmr.littledelicacies.client.render.*;
import mmr.littledelicacies.client.resource.NewZipTexturesWapper;
import mmr.littledelicacies.client.resource.OldZipTexturesWrapper;
import mmr.littledelicacies.init.LittleBlocks;
import mmr.littledelicacies.init.LittleContainers;
import mmr.littledelicacies.init.LittleEntitys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.resources.SimpleReloadableResourceManager;
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
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.COWGIRL, CowGirlRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.SQURRIEL_MAID, SqurrielMaidRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.SQURRIEL_BUTLER, SqurrielButlerRender::new);

        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.SUGAR_PHANTOM, SugarPhantomRender::new);

        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.PLANTER, PlanterRender::new);

        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.TRINITY, TrinityRender::new);

        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.MAID_FISHING_BOBBER, MaidFishingBobberRender::new);

        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.ROOT, RootRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.FLOWER_SAP, FlowerSapRender::new);
    }

    public static void renderTileEntity() {
    }

    public static void renderBlock() {
        RenderTypeLookup.setRenderLayer(LittleBlocks.SUGARN_PORTAL, RenderType.translucent());
    }

    public static void setup(final FMLCommonSetupEvent event) {
        if (Minecraft.getInstance().resourceManager instanceof SimpleReloadableResourceManager) {
            ((SimpleReloadableResourceManager) Minecraft.getInstance().resourceManager).addResourcePack(new OldZipTexturesWrapper());
            ((SimpleReloadableResourceManager) Minecraft.getInstance().resourceManager).addResourcePack(new NewZipTexturesWapper());
        }

        ClientRegistrar.renderEntity();
        ClientRegistrar.renderBlock();

        LittleContainers.registerScreenFactories();
    }
}
