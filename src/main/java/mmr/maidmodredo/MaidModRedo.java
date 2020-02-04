package mmr.maidmodredo;

import mmr.maidmodredo.client.render.LittleButlerRender;
import mmr.maidmodredo.client.render.LittleMaidBaseRender;
import mmr.maidmodredo.client.render.MaidFishingBobberRender;
import mmr.maidmodredo.client.render.WanderMaidRender;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.init.LittleContainers;
import mmr.maidmodredo.init.LittleEntitys;
import mmr.maidmodredo.init.MaidDataSerializers;
import mmr.maidmodredo.init.MaidJob;
import mmr.maidmodredo.network.MaidPacketHandler;
import mmr.maidmodredo.utils.CommonHelper;
import mmr.maidmodredo.utils.FileList;
import mmr.maidmodredo.utils.ModelManager;
import mmr.maidmodredo.utils.manager.StabilizerManager;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
@Mod(MaidModRedo.MODID)
public class MaidModRedo
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "maidmodredo";

    public MaidModRedo() {
        MaidPacketHandler.register();
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
        MaidModRedo.LOGGER.debug(s);
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

        StabilizerManager.init();
        ModelManager.instance.init();

        ModelManager.instance.loadTextures();
        if (CommonHelper.isClient) {
            debug("Localmode: InitTextureList.");

            ModelManager.instance.initTextureList(true);
        } else {
            ModelManager.instance.loadTextureServer();
        }

        MaidDataSerializers.registerData();
        MaidJob.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        //((SimpleReloadableResourceManager)Minecraft.getInstance().getResourceManager()).addResourcePack(new OldZipTexturesWrapper());
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.WANDERMAID, WanderMaidRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.LITTLEMAID, LittleMaidBaseRender::new);
        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.LITTLEBUTLER, LittleButlerRender::new);


        RenderingRegistry.registerEntityRenderingHandler(LittleEntitys.MAID_FISHING_BOBBER, MaidFishingBobberRender::new);

        LittleContainers.registerScreenFactories();
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {

    }

    private void processIMC(final InterModProcessEvent event)
    {

    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {

        if (event.getEntity() instanceof AbstractIllagerEntity) {
            ((AbstractIllagerEntity) event.getEntity()).targetSelector.addGoal(1, new NearestAttackableTargetGoal(((AbstractIllagerEntity) event.getEntity()), LittleMaidBaseEntity.class, true));
        }

        if (event.getEntity() instanceof LittleMaidBaseEntity) {
            LittleMaidBaseEntity maid = (LittleMaidBaseEntity) event.getEntity();
            if (maid.isContract() || maid.isWildSaved) return;
            maid.onSpawnWithEgg();
//			int c = maid.getTextureBox()[0].getWildColorBits();
//			if(c<=0) maid.setColor(12); else for(int i=15;i>=0;i--){
//				int x = (int) Math.pow(2, i);
//				if((c&x)==x) maid.setColor(i);
//			}
            maid.isWildSaved = true;
//			event.setResult(Result.ALLOW);
//			NBTTagCompound t = new NBTTagCompound();
//			maid.writeEntityToNBT(t);
//			maid.readEntityFromNBT(t);
            if (event.getWorld().isRemote) maid.setTextureNames();
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

}
