package mmr.maidmodredo.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mmr.maidmodredo.client.maidmodel.IModelCaps;
import mmr.maidmodredo.client.maidmodel.ModelBase;
import mmr.maidmodredo.client.maidmodel.ModelBaseDuo;
import mmr.maidmodredo.client.maidmodel.ModelBaseSolo;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ModelMultiRender<T extends LittleMaidBaseEntity> extends MobRenderer<T, ModelBase<T>> {
    public ModelBaseSolo<T> modelMain;
    public ModelBaseDuo<T> modelFATT;
    public IModelCaps fcaps;

    public ModelMultiRender(EntityRendererManager manager, float pShadowSize) {
        super(manager, null, pShadowSize);
        modelFATT = new ModelBaseDuo<>(this);
        modelFATT.isModelAlphablend = true;
        modelFATT.isRendering = true;
        modelMain = new ModelBaseSolo<>(this);
        modelMain.isModelAlphablend = true;
        modelMain.capsLink = modelFATT;
        entityModel = modelMain;
        //setRenderPassModel(modelFATT);
    }


    protected int showArmorParts(T entityIn, int par2, float par3) {
        // アーマーの表示設定
        modelFATT.renderParts = par2;
        modelFATT.renderCount = 0;
        ItemStack is = ((List<ItemStack>) entityIn.getArmorInventoryList()).get(par2);
        if (!is.isEmpty() && is.getCount() > 0) {
            modelFATT.showArmorParts(par2);
            return is.isEnchanted() ? 15 : 1;
        }

        return -1;
    }


    @Override
    protected void preRenderCallback(T entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        super.preRenderCallback(entitylivingbaseIn, matrixStackIn, partialTickTime);
        Float lscale = (Float) modelMain.getCapsValue(IModelCaps.caps_ScaleFactor);
        if (lscale != null) {
            matrixStackIn.scale(lscale, lscale, lscale);
        }
    }

    public void setModelValues(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, IModelCaps pEntityCaps) {

        modelMain.model = entityIn.getModelConfigCompound().textureModel[0];
        modelFATT.modelInner = entityIn.getModelConfigCompound().textureModel[1];
        modelFATT.modelOuter = entityIn.getModelConfigCompound().textureModel[2];
        //modelMain.model = ((TextureBox) entityIn.getTextureBox()[0]).models[0];
        modelMain.textures = entityIn.getTextures(0);
        //modelFATT.modelInner = ((TextureBox) entityIn.getTextureBox()[1]).models[1];
        //modelFATT.modelOuter = ((TextureBox) entityIn.getTextureBox()[1]).models[2];
        modelFATT.textureInner = entityIn.getTextures(1);
        modelFATT.textureOuter = entityIn.getTextures(2);
        modelFATT.textureInnerLight = entityIn.getTextures(3);
        modelFATT.textureOuterLight = entityIn.getTextures(4);
        modelFATT.textureLightColor = (float[]) modelFATT.getCapsValue(IModelCaps.caps_textureLightColor, pEntityCaps);

        modelMain.setEntityCaps(pEntityCaps);
        modelFATT.setEntityCaps(pEntityCaps);
        modelMain.setRender(this);
        modelFATT.setRender(this);
        modelMain.showAllParts();
        modelFATT.showAllParts();
        modelMain.isAlphablend = true;
        modelFATT.isAlphablend = true;
        modelMain.renderCount = 0;
        modelFATT.renderCount = 0;
        modelMain.lighting = modelFATT.lighting = entityIn.getBrightness();

        modelMain.setCapsValue(IModelCaps.caps_heldItemLeft, (Integer) 0);
        modelMain.setCapsValue(IModelCaps.caps_heldItemRight, (Integer) 0);
        modelMain.setCapsValue(IModelCaps.caps_onGround, getSwingProgress(entityIn, partialTicks));
        modelMain.setCapsValue(IModelCaps.caps_isRiding, entityIn.isPassenger());
        //modelMain.setCapsValue(IModelCaps.caps_isSneak, entityIn.isSneaking());
        modelMain.setCapsValue(IModelCaps.caps_aimedBow, entityIn.isShooting());
        modelMain.setCapsValue(IModelCaps.caps_crossbow, entityIn.isCharging());
        modelMain.setCapsValue(IModelCaps.caps_isWait, false);
        modelMain.setCapsValue(IModelCaps.caps_isChild, entityIn.isChild());
        modelMain.setCapsValue(IModelCaps.caps_entityIdFactor, 0F);
        modelMain.setCapsValue(IModelCaps.caps_ticksExisted, entityIn.ticksExisted);
        //カスタム設定
        modelMain.setCapsValue(IModelCaps.caps_motionSitting, false);
    }

    public void renderModelMulti(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, IModelCaps pEntityCaps) {
        setModelValues(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, pEntityCaps);
    }


    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        fcaps = entityIn.maidCaps;
        renderModelMulti(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, fcaps);
    }

    // TODO いらん？
	/*
	@Override
	protected void renderEquippedItems(EntityLivingBase entityIn, float par2) {
		// ハードポイントの描画
		modelMain.renderItems(entityIn, this);
		renderArrowsStuckInEntity(entityIn, par2);
	}


	@Override
	protected void renderArrowsStuckInEntity(EntityLivingBase entityIn, float par2) {
		Client.renderArrowsStuckInEntity(entityIn, par2, this, modelMain.model);
	}



	@Override
	protected int getColorMultiplier(EntityLivingBase entityInBase,
			float par2, float par3) {
		modelMain.renderCount = 16;
		return super.getColorMultiplier(entityInBase, par2, par3);
	}

	*/

    @Override
    public ResourceLocation getEntityTexture(T entityIn) {
        // テクスチャリソースを返すところだけれど、基本的に使用しない。
        return entityIn.getTextures(0)[0];
    }
}