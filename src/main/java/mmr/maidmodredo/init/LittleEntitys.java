package mmr.maidmodredo.init;

import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.entity.*;
import mmr.maidmodredo.entity.boss.TrinityEntity;
import mmr.maidmodredo.entity.misc.MaidFishingBobberEntity;
import mmr.maidmodredo.entity.monstermaid.EnderMaidEntity;
import mmr.maidmodredo.entity.phantom.SugarPhantomEntity;
import mmr.maidmodredo.entity.projectile.FlowerSapEntity;
import mmr.maidmodredo.entity.projectile.RootEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MaidModRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleEntitys {

    public static final EntityType<LittleMaidEntity> LITTLEMAID = EntityType.Builder.create(LittleMaidEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.55F).build(prefix("littlemaid"));
    public static final EntityType<LittleButlerEntity> LITTLEBUTLER = EntityType.Builder.create(LittleButlerEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.55F).build(prefix("littlebutler"));
    public static final EntityType<WanderMaidEntity> WANDERMAID = EntityType.Builder.create(WanderMaidEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.75F).build(prefix("wandermaid"));
    public static final EntityType<ZombieMaidEntity> ZOMBIEMAID = EntityType.Builder.create(ZombieMaidEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.55F).build(prefix("zombie_maid"));
    public static final EntityType<ZombieButlerEntity> ZOMBIEBUTLER = EntityType.Builder.create(ZombieButlerEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.55F).build(prefix("zombie_butler"));
    public static final EntityType<EnderMaidEntity> ENDERMAID = EntityType.Builder.create(EnderMaidEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.55F, 1.995F).build(prefix("ender_maid"));
    public static final EntityType<CowGirlEntity> COWGIRL = EntityType.Builder.create(CowGirlEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.85F).build(prefix("cowgirl"));
    public static final EntityType<SqurrielMaidEntity> SQURRIEL_MAID = EntityType.Builder.create(SqurrielMaidEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.35F, 0.6F).build(prefix("squrriel_maid"));

    public static final EntityType<PlanterEntity> PLANTER = EntityType.Builder.create(PlanterEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.55F).build(prefix("planter"));

    public static final EntityType<TrinityEntity> TRINITY = EntityType.Builder.create(TrinityEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.55F, 1.8F).build(prefix("trinity"));


    public static final EntityType<SugarPhantomEntity> SUGAR_PHANTOM = EntityType.Builder.create(SugarPhantomEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.55F).build(prefix("sugar_phantom"));

    public static final EntityType<MaidFishingBobberEntity> MAID_FISHING_BOBBER = EntityType.Builder.<MaidFishingBobberEntity>create(MaidFishingBobberEntity::new, EntityClassification.MISC).setCustomClientFactory(MaidFishingBobberEntity::new).disableSerialization().disableSummoning().size(0.25F, 0.25F).build(prefix("maid_fishing_bobber"));
    public static final EntityType<RootEntity> ROOT = EntityType.Builder.<RootEntity>create(RootEntity::new, EntityClassification.MISC).setCustomClientFactory(RootEntity::new).size(0.75F, 0.4F).build(prefix("root"));
    public static final EntityType<FlowerSapEntity> FLOWER_SAP = EntityType.Builder.<FlowerSapEntity>create(FlowerSapEntity::new, EntityClassification.MISC).setCustomClientFactory(FlowerSapEntity::new).size(0.5F, 0.5F).build(prefix("root"));


    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(LITTLEMAID.setRegistryName("littlemaid"));
        event.getRegistry().register(LITTLEBUTLER.setRegistryName("littlebutler"));
        event.getRegistry().register(WANDERMAID.setRegistryName("wandermaid"));
        event.getRegistry().register(ZOMBIEMAID.setRegistryName("zombie_maid"));
        event.getRegistry().register(ZOMBIEBUTLER.setRegistryName("zombie_butler"));
        event.getRegistry().register(ENDERMAID.setRegistryName("ender_maid"));
        event.getRegistry().register(COWGIRL.setRegistryName("cowgirl"));
        event.getRegistry().register(SQURRIEL_MAID.setRegistryName("squrriel_maid"));

        event.getRegistry().register(PLANTER.setRegistryName("planter"));

        event.getRegistry().register(TRINITY.setRegistryName("trinity"));

        event.getRegistry().register(SUGAR_PHANTOM.setRegistryName("sugar_phantom"));

        event.getRegistry().register(MAID_FISHING_BOBBER.setRegistryName("maid_fishing_bobber"));
        event.getRegistry().register(ROOT.setRegistryName("root"));
        event.getRegistry().register(FLOWER_SAP.setRegistryName("flower_sap"));

        EntitySpawnPlacementRegistry.register(ZOMBIEMAID, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombieMaidEntity::spawnHandler);
        EntitySpawnPlacementRegistry.register(ZOMBIEBUTLER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ZombieMaidEntity::spawnHandler);
    }

    private static String prefix(String path) {

        return MaidModRedo.MODID + "." + path;

    }

    public static void spawnEntity() {
        for (Biome biome : ForgeRegistries.BIOMES) {

        }

    }
}