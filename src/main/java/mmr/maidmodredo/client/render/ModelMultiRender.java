package mmr.maidmodredo.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mmr.maidmodredo.client.maidmodel.IModelCaps;
import mmr.maidmodredo.client.maidmodel.ModelBase;
import mmr.maidmodredo.client.maidmodel.ModelBaseDuo;
import mmr.maidmodredo.client.maidmodel.ModelBaseSolo;
import mmr.maidmodredo.client.model.ModelFake;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class ModelMultiRender<T extends LittleMaidBaseEntity> extends MobRenderer<T, ModelBase<T>> {
    public ModelBaseSolo<T> modelMain;
    public ModelBaseDuo<T> modelFATT;
    public IModelCaps fcaps;

    public ModelMultiRender(EntityRendererManager manager, float pShadowSize) {
        super(manager, new ModelFake<>(), pShadowSize);
        modelFATT = new ModelBaseDuo<>();
        modelFATT.isModelAlphablend = true;
        modelFATT.isRendering = true;
        modelMain = new ModelBaseSolo<>();
        modelMain.isModelAlphablend = true;
        modelMain.capsLink = modelFATT;
        //entityModel = modelMain;
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
        matrixStackIn.push();
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
        modelMain.setCapsValue(IModelCaps.caps_onGround, entityIn.getSwingProgress(partialTicks, Hand.MAIN_HAND), entityIn.getSwingProgress(partialTicks, Hand.OFF_HAND));
        modelMain.setCapsValue(IModelCaps.caps_isRiding, entityIn.isPassenger());
        //modelMain.setCapsValue(IModelCaps.caps_isSneak, entityIn.isSneaking());
        modelMain.setCapsValue(IModelCaps.caps_aimedBow, entityIn.isShooting());
        modelMain.setCapsValue(IModelCaps.caps_crossbow, entityIn.isCharging());
        modelMain.setCapsValue(IModelCaps.caps_isWait, entityIn.isMaidWait());
        modelMain.setCapsValue(IModelCaps.caps_isChild, entityIn.isChild());
        modelMain.setCapsValue(IModelCaps.caps_entityIdFactor, entityIn.entityIdFactor);
        modelMain.setCapsValue(IModelCaps.caps_ticksExisted, entityIn.ticksExisted);
        //カスタム設定
        modelMain.setCapsValue(IModelCaps.caps_motionSitting, false);

        modelMain.setCapsValue(IModelCaps.caps_heldItemLeft, (Integer) 0);
        modelMain.setCapsValue(IModelCaps.caps_heldItemRight, (Integer) 0);
        //modelMain.setCapsValue(IModelCaps.caps_onGround, renderSwingProgress(entityIn, par9));
        modelMain.setCapsValue(IModelCaps.caps_onGround, entityIn.getSwingProgress(partialTicks, entityIn.getSwingHand()), entityIn.getSwingProgress(partialTicks, entityIn.getSwingHand()));
        //modelMain.setCapsValue(IModelCaps.caps_isRiding, entityIn.isRidingRender());
        //modelMain.setCapsValue(IModelCaps.caps_isSneak, entityIn.isSneaking());
        /* modelMain.setCapsValue(IModelCaps.caps_aimedBow, entityIn.isAimebow());*/
        modelMain.setCapsValue(IModelCaps.caps_isWait, entityIn.isMaidWait());
        modelMain.setCapsValue(IModelCaps.caps_isChild, entityIn.isChild());
        modelMain.setCapsValue(IModelCaps.caps_entityIdFactor, entityIn.entityIdFactor);
        modelMain.setCapsValue(IModelCaps.caps_ticksExisted, entityIn.ticksExisted);
        modelMain.setCapsValue(IModelCaps.caps_dominantArm, entityIn.getPrimaryHand().ordinal());

        modelFATT.setModelAttributes(entityModel);
        modelMain.setModelAttributes(entityModel);
        //entityModel = modelMain;

        //途中でRenderされなくなるのを防ぐためにあえてここで描画する
        //Draw here to continue drawing

        boolean shouldSit = entityIn.isPassenger() && (entityIn.getRidingEntity() != null && entityIn.getRidingEntity().shouldRiderSit());


        float f = MathHelper.interpolateAngle(partialTicks, entityIn.prevRenderYawOffset, entityIn.renderYawOffset);
        float f1 = MathHelper.interpolateAngle(partialTicks, entityIn.prevRotationYawHead, entityIn.rotationYawHead);
        float f2 = f1 - f;
        if (shouldSit && entityIn.getRidingEntity() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entityIn.getRidingEntity();
            f = MathHelper.interpolateAngle(partialTicks, livingentity.prevRenderYawOffset, livingentity.renderYawOffset);
            f2 = f1 - f;
            float f3 = MathHelper.wrapDegrees(f2);
            if (f3 < -85.0F) {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
                f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
                f += f3 * 0.2F;
            }
        }

        float f6 = MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch);

        if (entityIn.getPose() == Pose.SLEEPING) {
            Direction direction = entityIn.getBedDirection();
            if (direction != null) {
                float f4 = entityIn.getEyeHeight(Pose.STANDING) - 0.1F;
                matrixStackIn.translate((double)((float)(-direction.getXOffset()) * f4), 0.0D, (double)((float)(-direction.getZOffset()) * f4));
            }
        }

        float f7 = this.handleRotationFloat(entityIn, partialTicks);
        this.applyRotations(entityIn, matrixStackIn, f7, f, partialTicks);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        this.preRenderCallback(entityIn, matrixStackIn, partialTicks);
        matrixStackIn.translate(0.0D, (double)-1.501F, 0.0D);
        float f8 = 0.0F;
        float f5 = 0.0F;
        if (!shouldSit && entityIn.isAlive()) {
            f8 = MathHelper.lerp(partialTicks, entityIn.prevLimbSwingAmount, entityIn.limbSwingAmount);
            f5 = entityIn.limbSwing - entityIn.limbSwingAmount * (1.0F - partialTicks);
            if (entityIn.isChild()) {
                f5 *= 3.0F;
            }

            if (f8 > 1.0F) {
                f8 = 1.0F;
            }
        }

        this.modelMain.setLivingAnimations(entityIn, f5, f8, partialTicks);
        this.modelMain.render(entityIn, f5, f8, f7, f2, f6);
        boolean flag = this.isVisible(entityIn);
        boolean flag1 = !flag && !entityIn.isInvisibleToPlayer(Minecraft.getInstance().player);
        RenderType rendertype = this.func_230042_a_(entityIn, flag, flag1);
        if (rendertype != null) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(rendertype);
            int i = getPackedOverlay(entityIn, this.getOverlayProgress(entityIn, partialTicks));
            modelMain.render(matrixStackIn, ivertexbuilder, packedLightIn, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
        }
        matrixStackIn.pop();
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