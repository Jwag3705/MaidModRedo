package mmr.littledelicacies.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.client.model.ZombieMaidModel;
import mmr.littledelicacies.entity.ZombieButlerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;

public class ZombieButlerRender<T extends ZombieButlerEntity> extends MobRenderer<T, ZombieMaidModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(LittleDelicacies.MODID, "textures/entity/zombie_butler.png");

    public ZombieButlerRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ZombieMaidModel<>(), 0.4F);
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new HeldItemLayer<>(this));
    }

    public ResourceLocation getEntityTexture(T entity) {
        return TEXTURE;
    }

    protected void applyRotations(T entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        if (entityLiving.isConverting()) {
            rotationYaw += (float) (Math.cos((double) entityLiving.ticksExisted * 3.25D) * Math.PI * 0.25D);
        }

        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }
}