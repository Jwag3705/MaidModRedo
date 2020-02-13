package mmr.maidmodredo.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mmr.maidmodredo.entity.phantom.SugarPhantomEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class SugarPhantomRender extends LittleMaidBaseRender<SugarPhantomEntity> {
    public SugarPhantomRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(SugarPhantomEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    protected RenderType func_230042_a_(SugarPhantomEntity p_230042_1_, boolean p_230042_2_, boolean p_230042_3_) {
        ResourceLocation resourcelocation = this.getEntityTexture(p_230042_1_);
        return RenderType.entityTranslucent(resourcelocation);
    }

    @Override
    protected boolean isVisible(SugarPhantomEntity livingEntityIn) {
        return false;
    }
}