package mmr.littlemaidredo.init;

import mmr.littlemaidredo.LittleMaidRedo;
import mmr.littlemaidredo.entity.LittleMaidEntity;
import mmr.littlemaidredo.entity.WanderMaidEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = LittleMaidRedo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LittleEntitys {

    public static final EntityType<LittleMaidEntity> LITTLEMAID = EntityType.Builder.create(LittleMaidEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.6F).build(prefix("littlemaid"));
    public static final EntityType<WanderMaidEntity> WANDERMAID = EntityType.Builder.create(WanderMaidEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.75F).build(prefix("wandermaid"));


    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(LITTLEMAID.setRegistryName("littlemaid"));
        event.getRegistry().register(WANDERMAID.setRegistryName("wandermaid"));

    }

    private static String prefix(String path) {

        return LittleMaidRedo.MODID + "." + path;

    }

    public static void spawnEntity() {
        for (Biome biome : ForgeRegistries.BIOMES) {

        }

    }
}