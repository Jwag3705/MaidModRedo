package mmr.maidmodredo.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mmr.maidmodredo.client.maidmodel.ModelBaseDuo;
import mmr.maidmodredo.client.maidmodel.ModelBaseSolo;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ModelMultiRender<T extends LittleMaidBaseEntity> extends MobRenderer<T, ModelBaseSolo<T>> {
    public ModelBaseDuo<T> modelFATT;

    public ModelMultiRender(EntityRendererManager manager, float pShadowSize) {
        super(manager, new ModelBaseSolo<>(), pShadowSize);
        modelFATT = new ModelBaseDuo<>();
        modelFATT.isModelAlphablend = true;
        modelFATT.isRendering = true;
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

        //matrixStackIn.scale(lscale, lscale, lscale);

    }

    public void setModelValues(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        //matrixStackIn.push();

        this.getEntityModel().isModelAlphablend = true;
        this.getEntityModel().capsLink = modelFATT;
        this.getEntityModel().model = entityIn.getModelConfigCompound().textureModel[0];
        modelFATT.modelInner = entityIn.getModelConfigCompound().textureModel[1];
        modelFATT.modelOuter = entityIn.getModelConfigCompound().textureModel[2];
        //this.getEntityModel().model = ((TextureBox) entityIn.getTextureBox()[0]).models[0];
        this.getEntityModel().textures = entityIn.getTextures(0);
        //modelFATT.modelInner = ((TextureBox) entityIn.getTextureBox()[1]).models[1];
        //modelFATT.modelOuter = ((TextureBox) entityIn.getTextureBox()[1]).models[2];
        modelFATT.textureInner = entityIn.getTextures(1);
        modelFATT.textureOuter = entityIn.getTextures(2);
        modelFATT.textureInnerLight = entityIn.getTextures(3);
        modelFATT.textureOuterLight = entityIn.getTextures(4);
        this.getEntityModel().setRender(this);
        modelFATT.setRender(this);
        this.getEntityModel().showAllParts();
        modelFATT.showAllParts();
        this.getEntityModel().isAlphablend = true;
        modelFATT.isAlphablend = true;
        this.getEntityModel().renderCount = 0;
        modelFATT.renderCount = 0;
        this.getEntityModel().lighting = modelFATT.lighting = entityIn.getBrightness();

        modelFATT.setModelAttributes(entityModel);
        this.getEntityModel().setModelAttributes(entityModel);
        //entityModel = modelMain;

        //途中でRenderされなくなるのを防ぐためにあえてここで描画する
        //Draw here to continue drawing

        /*boolean shouldSit = entityIn.isPassenger() && (entityIn.getRidingEntity() != null && entityIn.getRidingEntity().shouldRiderSit());


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
        matrixStackIn.pop();*/
    }

    public void renderModelMulti(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        setModelValues(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }


    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        renderModelMulti(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
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