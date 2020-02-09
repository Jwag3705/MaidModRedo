package mmr.maidmodredo.client.render;

import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.client.model.ZombieMaidModel;
import mmr.maidmodredo.entity.ZombieButlerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;

public class ZombieButlerRender<T extends ZombieButlerEntity> extends MobRenderer<T, ZombieMaidModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MaidModRedo.MODID, "textures/entity/zombie_butler.png");

    public ZombieButlerRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ZombieMaidModel<>(), 0.4F);
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new HeldItemLayer<>(this));
    }

    public ResourceLocation getEntityTexture(T entity) {
        return TEXTURE;
    }

}