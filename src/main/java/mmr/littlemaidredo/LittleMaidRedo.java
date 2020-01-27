package mmr.littlemaidredo;

import mmr.littlemaidredo.client.render.LittleMaidRender;
import mmr.littlemaidredo.client.render.WanderMaidRender;
import mmr.littlemaidredo.client.resource.OldZipTexturesWrapper;
import mmr.littlemaidredo.entity.LittleMaidEntity;
import mmr.littlemaidredo.entity.WanderMaidEntity;
import mmr.littlemaidredo.utils.CommonHelper;
import mmr.littlemaidredo.utils.FileList;
import mmr.littlemaidredo.utils.ModelManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LittleMaidRedo.MODID)
public class LittleMaidRedo
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "littlemaidredo";

    public LittleMaidRedo() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    public static void debug(String s) {
        LittleMaidRedo.LOGGER.debug(s);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        String classpath = System.getProperty("java.class.path");
        String separator = System.getProperty("path.separator");

        for (String path : classpath.split(separator)) {
            File pathFile = new File(path);
            if (pathFile.isDirectory()) {
                FileList.dirClasspath.add(pathFile);
            }
        }

        ModelManager.instance.init();

        ModelManager.instance.loadTextures();
        if (CommonHelper.isClient) {
            debug("Localmode: InitTextureList.");

            ModelManager.instance.initTextureList(true);
        } else {
            ModelManager.instance.loadTextureServer();
        }
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        Minecraft.getInstance().getResourceManager().addResourcePack(new OldZipTexturesWrapper());
        RenderingRegistry.registerEntityRenderingHandler(WanderMaidEntity.class, WanderMaidRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleMaidEntity.class, LittleMaidRender::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {

    }

    private void processIMC(final InterModProcessEvent event)
    {

    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

}
