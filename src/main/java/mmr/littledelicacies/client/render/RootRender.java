package mmr.littledelicacies.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mmr.littledelicacies.LittleDelicacies;
import mmr.littledelicacies.client.model.RootModel;
import mmr.littledelicacies.entity.projectile.RootEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RootRender extends EntityRenderer<RootEntity> {
    private static final ResourceLocation ROOT = new ResourceLocation(LittleDelicacies.MODID, "textures/entity/root.png");
    private final RootModel<RootEntity> model = new RootModel<>();

    public RootRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    public void render(RootEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        float f = entityIn.getAnimationProgress(partialTicks);
        if (f != 0.0F) {
            float f1 = 2.0F;
            if (f > 0.9F) {
                f1 = (float) ((double) f1 * ((1.0D - (double) f) / (double) 0.1F));
            }

            matrixStackIn.push();
            matrixStackIn.scale(-f1, -f1, f1);
            float f2 = 0.03125F;
            matrixStackIn.translate(0.0D, (double) -0.626F, 0.0D);
            matrixStackIn.scale(0.65F, 0.65F, 0.65F);
            this.model.render(entityIn, f, 0.0F, 0.0F, entityIn.rotationYaw, entityIn.rotationPitch);
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(ROOT));
            this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.DEFAULT_LIGHT, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.pop();
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    public ResourceLocation getEntityTexture(RootEntity entity) {
        return ROOT;
    }
}