package mmr.maidmodredo.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.client.model.TrinityModel;
import mmr.maidmodredo.entity.boss.TrinityEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;

public class TrinityRender<T extends TrinityEntity> extends MobRenderer<T, TrinityModel<T>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MaidModRedo.MODID, "textures/entity/trinity.png");

    public TrinityRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new TrinityModel<>(), 0.4F);
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new HeldItemLayer<>(this));
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();

        if (entityIn.isRushing()) {
            double shadowX = entityIn.prevShadowX + (entityIn.shadowX - entityIn.prevShadowX) * partialTicks;
            double shadowY = entityIn.prevShadowY + (entityIn.shadowY - entityIn.prevShadowY) * partialTicks;
            double shadowZ = entityIn.prevShadowZ + (entityIn.shadowZ - entityIn.prevShadowZ) * partialTicks;

            double shadowX2 = entityIn.prevShadowX2 + (entityIn.shadowX2 - entityIn.prevShadowX2) * partialTicks;
            double shadowY2 = entityIn.prevShadowY2 + (entityIn.shadowY2 - entityIn.prevShadowY2) * partialTicks;
            double shadowZ2 = entityIn.prevShadowZ2 + (entityIn.shadowZ2 - entityIn.prevShadowZ2) * partialTicks;


            double ownerInX = entityIn.prevPosX + (entityIn.getPosX() - entityIn.prevPosX) * partialTicks;
            double ownerInY = entityIn.prevPosY + (entityIn.getPosY() - entityIn.prevPosY) * partialTicks;
            double ownerInZ = entityIn.prevPosZ + (entityIn.getPosZ() - entityIn.prevPosZ) * partialTicks;

            double deltaX = shadowX - ownerInX;
            double deltaY = shadowY - ownerInY;
            double deltaZ = shadowZ - ownerInZ;
            double deltaX2 = shadowX2 - shadowX;
            double deltaY2 = shadowY2 - shadowY;
            double deltaZ2 = shadowZ2 - shadowZ;

            matrixStackIn.push();
            matrixStackIn.translate(deltaX * 2.0F, deltaY * 2.0F, deltaZ * 2.0F);
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.pop();

            matrixStackIn.push();
            matrixStackIn.translate(deltaX2 * 4.0F, deltaY2 * 4.0F, deltaZ2 * 4.0F);
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.pop();
        }
    }

    public ResourceLocation getEntityTexture(T entity) {
        return TEXTURE;
    }

}