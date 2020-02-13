package mmr.maidmodredo.client.maidmodel;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class ModelBaseSolo<T extends LivingEntity> extends ModelBaseNihil<T> implements IModelBaseMMM {

    public ModelMultiBase<T> model;
    public ResourceLocation[] textures;
    public static final ResourceLocation[] blanks = new ResourceLocation[0];


    public ModelBaseSolo() {

    }

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        if (model != null) {
            try {
                model.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
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

        if (isAlphablend) {
		/*	if (LittleMaidReengaged.cfg_isModelAlphaBlend) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			} else {*/
            //}
        }
        if (textures.length > 2 && textures[2] != null) {
            matrixStack.push();

            // Actors用
            //model.render(par2, par3, par4, par5, par6, par7, entityCaps);
            // Face
            // TODO テクスチャのロードはなんか考える。
            Minecraft.getInstance().getTextureManager().bindTexture(textures[2]);

            // Body
            Minecraft.getInstance().getTextureManager().bindTexture(textures[0]);

            matrixStack.pop();
        } else {
            matrixStack.push();

            // 通常
            if (textures.length > 0 && textures[0] != null) {
                Minecraft.getInstance().getTextureManager().bindTexture(textures[0]);
            }

            model.render(matrixStack, iVertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStack.pop();
        }
        isAlphablend = false;
        if (textures.length > 1 && textures[1] != null && renderCount == 0) {
            matrixStack.push();
            // 発光パーツ
            Minecraft.getInstance().getTextureManager().bindTexture(textures[1]);
            float var4 = 1.0F;
            model.render(matrixStack, iVertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStack.pop();
        }
//		textures = blanks;
        renderCount++;
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (model != null) {
            model.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }


    // IModelMMM追加分

    @Override
    public void renderItems(LivingEntity pEntity, MatrixStack stack, boolean left) {
        if (model != null) {
            model.renderItems(stack, left);
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
