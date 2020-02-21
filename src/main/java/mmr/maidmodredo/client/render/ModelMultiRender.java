package mmr.maidmodredo.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mmr.maidmodredo.client.maidmodel.ModelBaseDuo;
import mmr.maidmodredo.client.maidmodel.ModelBaseSolo;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;

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

    public void setModelValues(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, int modelNum) {
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<T, ModelBaseSolo<T>>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn)))
            return;
        matrixStackIn.push();

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

        if (this.entityModel.model != null) {
            this.entityModel.swingProgress = this.getSwingProgress(entityIn, partialTicks);
            this.entityModel.model.swingProgress = this.getSwingProgress(entityIn, partialTicks);

            boolean shouldSit = entityIn.isPassenger() && (entityIn.getRidingEntity() != null && entityIn.getRidingEntity().shouldRiderSit());
            this.entityModel.isSitting = shouldSit;
            this.entityModel.isChild = entityIn.isChild();
            float f = MathHelper.interpolateAngle(partialTicks, entityIn.prevRenderYawOffset, entityIn.renderYawOffset);
            float f1 = MathHelper.interpolateAngle(partialTicks, entityIn.prevRotationYawHead, entityIn.rotationYawHead);
            float f2 = f1 - f;
            if (shouldSit && entityIn.getRidingEntity() instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entityIn.getRidingEntity();
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

                f2 = f1 - f;
            }

            float f6 = MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch);

            if (entityIn.getPose() == Pose.SLEEPING) {
                Direction direction = entityIn.getBedDirection();
                if (direction != null) {
                    float f4 = entityIn.getEyeHeight(Pose.STANDING) - 0.1F;
                    matrixStackIn.translate((double) ((float) (-direction.getXOffset()) * f4), 0.0D, (double) ((float) (-direction.getZOffset()) * f4));
                }
            }

            float f7 = this.handleRotationFloat(entityIn, partialTicks);
            this.applyRotations(entityIn, matrixStackIn, f7, f, partialTicks);
            matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback(entityIn, matrixStackIn, partialTicks);
            matrixStackIn.translate(0.0D, (double) -1.501F, 0.0D);
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

            this.getEntityModel().setLivingAnimations(entityIn, f5, f8, partialTicks);
            this.getEntityModel().render(entityIn, f5, f8, f7, f2, f6);
            boolean flag = this.isVisible(entityIn);
            boolean flag1 = !flag && !entityIn.isInvisibleToPlayer(Minecraft.getInstance().player);
            boolean flag2 = modelNum == 1 || modelNum == 2;
            RenderType rendertype = this.func_230042_a_(entityIn, flag, flag1);
            if (rendertype != null) {
                IVertexBuilder ivertexbuilder = bufferIn.getBuffer(rendertype);
                int i = getPackedOverlay(entityIn, this.getOverlayProgress(entityIn, partialTicks));

                if (flag2) {
                    this.getEntityModel().render(matrixStackIn, ivertexbuilder, packedLightIn, i, modelNum == 1 ? 1.0F : 0.5F, modelNum == 2 ? 1.0F : 0.5F, 0.5F, 0.15F * modelNum);
                } else {
                    this.getEntityModel().render(matrixStackIn, ivertexbuilder, packedLightIn, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
                }
            }

            if (!entityIn.isSpectator()) {
                for (LayerRenderer<T, ModelBaseSolo<T>> layerrenderer : this.layerRenderers) {
                    layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f8, partialTicks, f7, f2, f6);
                }
            }
        }
        matrixStackIn.pop();
        net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(entityIn, entityIn.getDisplayName().getFormattedText(), matrixStackIn, bufferIn);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.canRenderName(entityIn))) {
            this.renderName(entityIn, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
        }
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<T, ModelBaseSolo<T>>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn));

    }

    public void renderModelMulti(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, int modelNum) {
        setModelValues(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, modelNum);
    }


    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        //super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.push();
        renderModelMulti(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, 0);
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
            renderModelMulti(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, 1);
            matrixStackIn.pop();

            matrixStackIn.push();
            matrixStackIn.translate(deltaX2 * 4.0F, deltaY2 * 4.0F, deltaZ2 * 4.0F);
            renderModelMulti(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, 2);
            matrixStackIn.pop();
        }

        Entity entity = entityIn.getLeashHolder();
        if (entity != null) {
            this.renderLeash(entityIn, partialTicks, matrixStackIn, bufferIn, entity);
        }
    }

    private <E extends Entity> void renderLeash(T entityLivingIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, E leashHolder) {
        matrixStackIn.push();
        double d0 = (double) (MathHelper.lerp(partialTicks * 0.5F, leashHolder.rotationYaw, leashHolder.prevRotationYaw) * ((float) Math.PI / 180F));
        double d1 = (double) (MathHelper.lerp(partialTicks * 0.5F, leashHolder.rotationPitch, leashHolder.prevRotationPitch) * ((float) Math.PI / 180F));
        double d2 = Math.cos(d0);
        double d3 = Math.sin(d0);
        double d4 = Math.sin(d1);
        if (leashHolder instanceof HangingEntity) {
            d2 = 0.0D;
            d3 = 0.0D;
            d4 = -1.0D;
        }

        double d5 = Math.cos(d1);
        double d6 = MathHelper.lerp((double) partialTicks, leashHolder.prevPosX, leashHolder.getPosX()) - d2 * 0.7D - d3 * 0.5D * d5;
        double d7 = MathHelper.lerp((double) partialTicks, leashHolder.prevPosY + (double) leashHolder.getEyeHeight() * 0.7D, leashHolder.getPosY() + (double) leashHolder.getEyeHeight() * 0.7D) - d4 * 0.5D - 0.25D;
        double d8 = MathHelper.lerp((double) partialTicks, leashHolder.prevPosZ, leashHolder.getPosZ()) - d3 * 0.7D + d2 * 0.5D * d5;
        double d9 = (double) (MathHelper.lerp(partialTicks, entityLivingIn.renderYawOffset, entityLivingIn.prevRenderYawOffset) * ((float) Math.PI / 180F)) + (Math.PI / 2D);
        d2 = Math.cos(d9) * (double) entityLivingIn.getWidth() * 0.4D;
        d3 = Math.sin(d9) * (double) entityLivingIn.getWidth() * 0.4D;
        double d10 = MathHelper.lerp((double) partialTicks, entityLivingIn.prevPosX, entityLivingIn.getPosX()) + d2;
        double d11 = MathHelper.lerp((double) partialTicks, entityLivingIn.prevPosY, entityLivingIn.getPosY());
        double d12 = MathHelper.lerp((double) partialTicks, entityLivingIn.prevPosZ, entityLivingIn.getPosZ()) + d3;
        matrixStackIn.translate(d2, -(1.6D - (double) entityLivingIn.getHeight()) * 0.5D, d3);
        float f = (float) (d6 - d10);
        float f1 = (float) (d7 - d11);
        float f2 = (float) (d8 - d12);
        float f3 = 0.025F;
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.leash());
        Matrix4f matrix4f = matrixStackIn.getLast().getPositionMatrix();
        float f4 = MathHelper.fastInvSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        int i = this.getBlockLight(entityLivingIn, partialTicks);
        int j = this.getBlockLight(entityLivingIn, partialTicks);
        int k = entityLivingIn.world.getLightFor(LightType.SKY, new BlockPos(entityLivingIn.getEyePosition(partialTicks)));
        int l = entityLivingIn.world.getLightFor(LightType.SKY, new BlockPos(leashHolder.getEyePosition(partialTicks)));
        renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6);
        renderSide(ivertexbuilder, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6);
        matrixStackIn.pop();
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