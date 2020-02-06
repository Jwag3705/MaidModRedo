package mmr.maidmodredo.client.render;

import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.client.model.ZombieMaidModel;
import mmr.maidmodredo.entity.ZombieMaidEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieMaidRender<T extends ZombieMaidEntity> extends MobRenderer<T, ZombieMaidModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MaidModRedo.MODID, "textures/entity/zombie_maid.png");

    public ZombieMaidRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ZombieMaidModel<>(), 0.5F);
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new HeldItemLayer<>(this));
    }

    public ResourceLocation getEntityTexture(T entity) {
        return TEXTURE;
    }

}