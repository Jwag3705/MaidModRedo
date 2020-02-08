package mmr.maidmodredo.init;

import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.entity.*;
import mmr.maidmodredo.entity.misc.MaidFishingBobberEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MaidModRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleEntitys {

    public static final EntityType<LittleMaidEntity> LITTLEMAID = EntityType.Builder.create(LittleMaidEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.6F).build(prefix("littlemaid"));
    public static final EntityType<LittleButlerEntity> LITTLEBUTLER = EntityType.Builder.create(LittleButlerEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.6F).build(prefix("littlebutler"));
    public static final EntityType<WanderMaidEntity> WANDERMAID = EntityType.Builder.create(WanderMaidEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.75F).build(prefix("wandermaid"));
    public static final EntityType<ZombieMaidEntity> ZOMBIEMAID = EntityType.Builder.create(ZombieMaidEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.6F).build(prefix("zombie_maid"));
    public static final EntityType<ZombieButlerEntity> ZOMBIEBUTLER = EntityType.Builder.create(ZombieButlerEntity::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.6F).build(prefix("zombie_butler"));



    public static final EntityType<MaidFishingBobberEntity> MAID_FISHING_BOBBER = EntityType.Builder.<MaidFishingBobberEntity>create(MaidFishingBobberEntity::new, EntityClassification.MISC).setCustomClientFactory(MaidFishingBobberEntity::new).disableSerialization().disableSummoning().size(0.25F, 0.25F).build(prefix("maid_fishing_bobber"));

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(LITTLEMAID.setRegistryName("littlemaid"));
        event.getRegistry().register(LITTLEBUTLER.setRegistryName("littlebutler"));
        event.getRegistry().register(WANDERMAID.setRegistryName("wandermaid"));
        event.getRegistry().register(ZOMBIEMAID.setRegistryName("zombie_maid"));
        event.getRegistry().register(ZOMBIEBUTLER.setRegistryName("zombie_butler"));

        EntitySpawnPlacementRegistry.register(ZOMBIEMAID, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::func_223325_c);
        EntitySpawnPlacementRegistry.register(ZOMBIEBUTLER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::func_223325_c);
    }

    private static String prefix(String path) {

        return MaidModRedo.MODID + "." + path;

    }

    public static void spawnEntity() {
        for (Biome biome : ForgeRegistries.BIOMES) {

        }

    }
}