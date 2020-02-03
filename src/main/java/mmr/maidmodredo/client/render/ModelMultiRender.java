package mmr.maidmodredo.client.render;

import mmr.maidmodredo.client.maidmodel.IModelCaps;
import mmr.maidmodredo.client.maidmodel.ModelBase;
import mmr.maidmodredo.client.maidmodel.ModelBaseDuo;
import mmr.maidmodredo.client.maidmodel.ModelBaseSolo;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

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


    protected int showArmorParts(T par1EntityLiving, int par2, float par3) {
        // アーマーの表示設定
        modelFATT.renderParts = par2;
        modelFATT.renderCount = 0;
        ItemStack is = ((List<ItemStack>) par1EntityLiving.getArmorInventoryList()).get(par2);
        if (!is.isEmpty() && is.getCount() > 0) {
            modelFATT.showArmorParts(par2);
            return is.isEnchanted() ? 15 : 1;
        }

        return -1;
    }

    @Override
    protected void preRenderCallback(T entityliving, float f) {
        Float lscale = (Float) modelMain.getCapsValue(IModelCaps.caps_ScaleFactor);
        if (lscale != null) {
            GL11.glScalef(lscale, lscale, lscale);
        }
    }

    public void setModelValues(T par1EntityLiving, double x, double y, double z, float entityYaw, float partialTicks, IModelCaps pEntityCaps) {

        modelMain.model = par1EntityLiving.getModelConfigCompound().textureModel[0];
        modelFATT.modelInner = par1EntityLiving.getModelConfigCompound().textureModel[1];
        modelFATT.modelOuter = par1EntityLiving.getModelConfigCompound().textureModel[2];
        //modelMain.model = ((TextureBox) par1EntityLiving.getTextureBox()[0]).models[0];
        modelMain.textures = par1EntityLiving.getTextures(0);
        //modelFATT.modelInner = ((TextureBox) par1EntityLiving.getTextureBox()[1]).models[1];
        //modelFATT.modelOuter = ((TextureBox) par1EntityLiving.getTextureBox()[1]).models[2];
        modelFATT.textureInner = par1EntityLiving.getTextures(1);
        modelFATT.textureOuter = par1EntityLiving.getTextures(2);
        modelFATT.textureInnerLight = par1EntityLiving.getTextures(3);
        modelFATT.textureOuterLight = par1EntityLiving.getTextures(4);
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
        modelMain.lighting = modelFATT.lighting = par1EntityLiving.getBrightnessForRender();

        modelMain.setCapsValue(IModelCaps.caps_heldItemLeft, (Integer) 0);
        modelMain.setCapsValue(IModelCaps.caps_heldItemRight, (Integer) 0);
        modelMain.setCapsValue(IModelCaps.caps_onGround, getSwingProgress(par1EntityLiving, partialTicks));
        modelMain.setCapsValue(IModelCaps.caps_isRiding, par1EntityLiving.isPassenger());
        modelMain.setCapsValue(IModelCaps.caps_isSneak, par1EntityLiving.isSneaking());
        modelMain.setCapsValue(IModelCaps.caps_aimedBow, par1EntityLiving.isShooting());
        modelMain.setCapsValue(IModelCaps.caps_crossbow, par1EntityLiving.isCharging());
        modelMain.setCapsValue(IModelCaps.caps_isWait, false);
        modelMain.setCapsValue(IModelCaps.caps_isChild, par1EntityLiving.isChild());
        modelMain.setCapsValue(IModelCaps.caps_entityIdFactor, 0F);
        modelMain.setCapsValue(IModelCaps.caps_ticksExisted, par1EntityLiving.ticksExisted);
        //カスタム設定
        modelMain.setCapsValue(IModelCaps.caps_motionSitting, false);
    }

    public void renderModelMulti(T par1EntityLiving, double x, double y, double z, float entityYaw, float partialTicks, IModelCaps pEntityCaps) {
        setModelValues(par1EntityLiving, x, y, z, entityYaw, partialTicks, pEntityCaps);
    }

    @Override
    public void doRender(T par1EntityLiving, double x, double y, double z, float entityYaw, float partialTicks) {
        fcaps = par1EntityLiving.maidCaps;
        renderModelMulti(par1EntityLiving, x, y, z, entityYaw, partialTicks, fcaps);
        super.doRender(par1EntityLiving, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void renderLeash(T par1EntityLiving, double par2,
                               double par4, double par6, float par8, float par9) {
        // 縄の位置のオフセット
        float lf = 0F;
        if (modelMain.model != null && fcaps != null) {
            lf = modelMain.model.getLeashOffset(fcaps);
        }
        super.renderLeash(par1EntityLiving, par2, par4 - lf, par6, par8, par9);
    }

    @Override
    protected void renderModel(T par1EntityLiving, float par2,
                               float par3, float par4, float par5, float par6, float par7) {
        boolean flag = this.isVisible(par1EntityLiving);
        boolean flag1 = !flag && !par1EntityLiving.isInvisibleToPlayer(Minecraft.getInstance().player);
        if (flag || flag1) {

            if (!par1EntityLiving.isInvisible()) {

                modelMain.setArmorRendering(true);
            } else {
                modelMain.setArmorRendering(false);
            }
        }
        super.renderModel(par1EntityLiving, par2, par3, par4, par5, par6, par7);
    }

    // TODO いらん？
	/*
	@Override
	protected void renderEquippedItems(EntityLivingBase par1EntityLiving, float par2) {
		// ハードポイントの描画
		modelMain.renderItems(par1EntityLiving, this);
		renderArrowsStuckInEntity(par1EntityLiving, par2);
	}


	@Override
	protected void renderArrowsStuckInEntity(EntityLivingBase par1EntityLiving, float par2) {
		Client.renderArrowsStuckInEntity(par1EntityLiving, par2, this, modelMain.model);
	}



	@Override
	protected int getColorMultiplier(EntityLivingBase par1EntityLivingBase,
			float par2, float par3) {
		modelMain.renderCount = 16;
		return super.getColorMultiplier(par1EntityLivingBase, par2, par3);
	}

	*/

    @Override
    protected ResourceLocation getEntityTexture(T par1EntityLiving) {
        // テクスチャリソースを返すところだけれど、基本的に使用しない。
        return par1EntityLiving.getTextures(0)[0];
    }
}