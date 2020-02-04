package mmr.maidmodredo.client.maidmodel;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;

public class ModelBaseSolo<T extends LivingEntity> extends ModelBaseNihil<T> implements IModelBaseMMM {

    public ModelMultiBase<T> model;
    public ResourceLocation[] textures;
    public static final ResourceLocation[] blanks = new ResourceLocation[0];


    public ModelBaseSolo(MobRenderer pRender) {
        rendererLivingEntity = pRender;
    }

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        if (model != null) {
            try {
                model.setLivingAnimations(entityCaps, limbSwing, limbSwingAmount, partialTick);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isAlphablend = true;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (model == null) {
            isAlphablend = false;
            return;
        }
        LivingEntity livingEntity = (LivingEntity) entityCaps.getCapsValue(IModelCaps.caps_Entity);


        if (isAlphablend) {
		/*	if (LittleMaidReengaged.cfg_isModelAlphaBlend) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			} else {*/
            //}
        }
        if (textures.length > 2 && textures[2] != null) {
            // Actors用
            //model.setRotationAngles(par2, par3, par4, par5, par6, par7, entityCaps);
            // Face
            // TODO テクスチャのロードはなんか考える。
            //Minecraft.getInstance().getTextureManager().bindTexture(textures[2]);
            //model.setCapsValue(caps_renderFace, entityCaps, matrixStack,iVertexBuilder,packedLightIn,packedOverlayIn,red,green,blue,alpha, isRendering);
            // Body
            //Minecraft.getInstance().getTextureManager().bindTexture(textures[0]);
            //model.setCapsValue(caps_renderBody, entityCaps, matrixStack,iVertexBuilder,packedLightIn,packedOverlayIn,red,green,blue,alpha, isRendering);
        } else {
            matrixStack.push();

            // 通常
            if (textures.length > 0 && textures[0] != null) {
                Minecraft.getInstance().getTextureManager().bindTexture(textures[0]);
            }


            model.render(entityCaps, matrixStack, iVertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha, isRendering);
            matrixStack.pop();
        }
        isAlphablend = false;
        if (textures.length > 1 && textures[1] != null && renderCount == 0) {
            matrixStack.push();
            // 発光パーツ
            Minecraft.getInstance().getTextureManager().bindTexture(textures[1]);
            float var4 = 1.0F;
            model.render(entityCaps, matrixStack, iVertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha, true);
            matrixStack.pop();
        }
//		textures = blanks;
        renderCount++;
    }

    @Nullable
    protected RenderType func_230042_a_(ResourceLocation location, LivingEntity p_230042_1_, boolean p_230042_2_, boolean p_230042_3_) {
        ResourceLocation resourcelocation = location;
        if (p_230042_3_) {
            return RenderType.entityTranslucent(resourcelocation);
        } else if (p_230042_2_) {
            return this.getRenderType(resourcelocation);
        } else {
            return p_230042_1_.isGlowing() ? RenderType.outline(resourcelocation) : null;
        }
    }

    public static int getPackedOverlay(LivingEntity livingEntityIn, float uIn) {
        return OverlayTexture.packLight(OverlayTexture.lightToInt(uIn), OverlayTexture.getV(livingEntityIn.hurtTime > 0 || livingEntityIn.deathTime > 0));
    }

    protected boolean isVisible(LivingEntity livingEntityIn) {
        return !livingEntityIn.isInvisible();
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (model != null) {
            model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, entityCaps);
        }
    }


    // IModelMMM追加分

    @Override
    public void renderItems(LivingEntity pEntity, MobRenderer pRender) {
        if (model != null) {
            model.renderItems(entityCaps);
        }
    }

    @Override
    public void showArmorParts(int pParts) {
        if (model != null) {
            model.showArmorParts(pParts, 0);
        }
    }

    /**
     * Renderer辺でこの変数を設定する。
     * 設定値はIModelCapsを継承したEntitiyとかを想定。
     */
    @Override
    public void setEntityCaps(IModelCaps pEntityCaps) {
        entityCaps = pEntityCaps;
        if (capsLink != null) {
            capsLink.setEntityCaps(pEntityCaps);
        }
    }

    @Override
    public void setRender(MobRenderer pRender) {
        if (model != null) {
            model.render = pRender;
        }
    }

    @Override
    public void setArmorRendering(boolean pFlag) {
        isRendering = pFlag;
    }


    // IModelCaps追加分

    @Override
    public Map<String, Integer> getModelCaps() {
        return model == null ? null : model.getModelCaps();
    }

    @Override
    public Object getCapsValue(int pIndex, Object... pArg) {
        return model == null ? null : model.getCapsValue(pIndex, pArg);
    }

    @Override
    public boolean setCapsValue(int pIndex, Object... pArg) {
        if (capsLink != null) {
            capsLink.setCapsValue(pIndex, pArg);
        }
        if (model != null) {
            model.setCapsValue(pIndex, pArg);
        }
        return false;
    }

    @Override
    public void showAllParts() {
        if (model != null) {
            model.showAllParts();
        }
    }


    @Override
    public float[] getArmorModelsSize() {
        return new float[0];
    }
}
