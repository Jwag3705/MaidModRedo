package mmr.maidmodredo;

import mmr.maidmodredo.client.ClientRegistrar;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.init.*;
import mmr.maidmodredo.network.MaidPacketHandler;
import mmr.maidmodredo.utils.CommonHelper;
import mmr.maidmodredo.utils.FileList;
import mmr.maidmodredo.utils.ModelManager;
import mmr.maidmodredo.utils.manager.StabilizerManager;
import mmr.maidmodredo.world.event.WanderMaidSpawner;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MaidModRedo.MODID)
public class MaidModRedo {
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "maidmodredo";


    private WanderMaidSpawner wanderMaidSpawner = new WanderMaidSpawner();

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

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistrar::setup));
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    public static void debug(String s) {
        MaidModRedo.LOGGER.debug(s);
    }

    private void setup(final FMLCommonSetupEvent event) {
        MaidModels.registerModel();

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

        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {

            biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(LittleEntitys.ZOMBIEMAID, 5, 1, 2));
            biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(LittleEntitys.ZOMBIEBUTLER, 5, 1, 2));


            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.PLAINS) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
                biome.addStructure(LittleFeatures.MAIDCAFE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
            }

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
                biome.addStructure(LittleFeatures.BIGTREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
            }

            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, LittleFeatures.MAIDCAFE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, LittleFeatures.BIGTREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        }
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        //((SimpleReloadableResourceManager)Minecraft.getInstance().getResourceManager()).addResourcePack(new OldZipTexturesWrapper());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        MaidRecipes.registerRecipe();
    }

    private void processIMC(final InterModProcessEvent event) {

    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {

        if (event.getEntity() instanceof AbstractIllagerEntity) {
            ((AbstractIllagerEntity) event.getEntity()).targetSelector.addGoal(1, new NearestAttackableTargetGoal(((AbstractIllagerEntity) event.getEntity()), LittleMaidBaseEntity.class, true));
        }
    }


    @SubscribeEvent
    public void onEntityHurted(LivingHurtEvent event) {
        if (event.getSource().getImmediateSource() instanceof AbstractArrowEntity) {
            AbstractArrowEntity arrowEntity = (AbstractArrowEntity) event.getSource().getImmediateSource();

            if (arrowEntity.getShooter() instanceof LittleMaidBaseEntity) {
                LittleMaidBaseEntity littleMaidBase = (LittleMaidBaseEntity) arrowEntity.getShooter();

                if (littleMaidBase.getOwner() == event.getEntityLiving()) {
                    event.setCanceled(true);
                }

                if (event.getEntityLiving() instanceof LittleMaidBaseEntity) {
                    LittleMaidBaseEntity ownersLittleMaid = (LittleMaidBaseEntity) event.getEntityLiving();
                    if (littleMaidBase.getOwner() == ownersLittleMaid.getOwner()) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.WorldTickEvent event) {

        if (event.world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.world;
            wanderMaidSpawner.tick(serverWorld);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

}
