package mmr.littledelicacies.client.render;

import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.client.model.WanderMaidModel;
import mmr.littledelicacies.entity.WanderMaidEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WanderMaidRender <T extends WanderMaidEntity> extends MobRenderer<T, WanderMaidModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(LittleDelicacies.MODID, "textures/entity/wandermaid.png");

    public WanderMaidRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new WanderMaidModel<>(), 0.5F);
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new HeldItemLayer<>(this));
    }

    public ResourceLocation getEntityTexture(WanderMaidEntity entity) {
        return TEXTURE;
    }

}