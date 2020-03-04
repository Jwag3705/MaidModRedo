package mmr.maidmodredo.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mmr.maidmodredo.entity.projectile.FlowerSapEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.LlamaSpitModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlowerSapRender extends EntityRenderer<FlowerSapEntity> {
    private static final ResourceLocation LLAMA_SPIT_TEXTURE = new ResourceLocation("textures/entity/llama/spit.png");
    private final LlamaSpitModel<FlowerSapEntity> model = new LlamaSpitModel<>();

    public FlowerSapRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    public void render(FlowerSapEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, (double) 0.15F, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));
        this.model.render(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.model.getRenderType(LLAMA_SPIT_TEXTURE));
        this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.DEFAULT_LIGHT, 0.9F, 1.0F, 0.0F, 0.8F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public ResourceLocation getEntityTexture(FlowerSapEntity entity) {
        return LLAMA_SPIT_TEXTURE;
    }
}