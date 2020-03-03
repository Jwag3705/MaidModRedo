package mmr.maidmodredo.client.render;

import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.client.model.PlanterModel;
import mmr.maidmodredo.entity.PlanterEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;

public class PlanterRender<T extends PlanterEntity> extends MobRenderer<T, PlanterModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MaidModRedo.MODID, "textures/entity/planter.png");

    public PlanterRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PlanterModel<>(), 0.4F);
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new HeldItemLayer<>(this));
    }

    public ResourceLocation getEntityTexture(T entity) {
        return TEXTURE;
    }

}