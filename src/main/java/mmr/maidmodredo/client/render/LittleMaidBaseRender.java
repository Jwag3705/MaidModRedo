package mmr.maidmodredo.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import mmr.maidmodredo.api.IMaidArmor;
import mmr.maidmodredo.client.maidmodel.IModelCaps;
import mmr.maidmodredo.client.maidmodel.ModelBase;
import mmr.maidmodredo.client.maidmodel.ModelLittleMaidBase;
import mmr.maidmodredo.client.maidmodel.ModelLittleMaid_Orign;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.utils.helper.RendererHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class LittleMaidBaseRender<T extends LittleMaidBaseEntity> extends ModelMultiRender<T> {

    // Method
    public LittleMaidBaseRender(EntityRendererManager manager) {
        super(manager, 0.4F);


        addLayer(new MMMLayerArmor<>(this));
        addLayer(new MMMLayerHeadArmor<>(this));
        addLayer(new MMMHeldItemLayer<>(this));
    }

    /**
     * 防具描画レイヤー
     */
    public class MMMLayerArmor<T extends LittleMaidBaseEntity, M extends ModelBase<T>> extends LayerRenderer<T, M> {

        //レイヤーと化した防具描画

        public static final float renderScale = 0.0625F;

        public MMMLayerArmor(IEntityRenderer<T, M> p_i46125_1_) {
            super(p_i46125_1_);
//			this.modelLeggings = mmodel;
//			this.modelArmor = mmodel;
        }

        public void render(T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
            this.renderArmorLayer(entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_, 0, EquipmentSlotType.CHEST);
            this.renderArmorLayer(entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_, 1, EquipmentSlotType.LEGS);
            this.renderArmorLayer(entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_, 2, EquipmentSlotType.FEET);
            this.renderArmorLayer(entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_, 3, EquipmentSlotType.HEAD);
        }

        public void renderArmorLayer(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, int renderParts, EquipmentSlotType slotType) {
//			boolean lri = (renderCount & 0x0f) == 0;
            //if (!lmm.maidInventory.armorItemInSlot(i).isEmpty()) {
            if (!entitylivingbaseIn.getItemStackFromSlot(slotType).isEmpty()) {
                //総合
                modelFATT.showArmorParts(renderParts);

                //Inner
                INNER:
                {
                    if (modelFATT.textureInner != null) {
                        ResourceLocation texInner = modelFATT.textureInner[renderParts];
                        if (texInner != null
                            //&& lmm.isArmorVisible(0)
                        ) try {
                            Minecraft.getInstance().getTextureManager().bindTexture(texInner);
                            GL11.glEnable(GL11.GL_BLEND);
                            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                            modelFATT.modelInner.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, renderScale, fcaps);
                            modelFATT.modelInner.setLivingAnimations(fcaps, limbSwing, limbSwingAmount, partialTicks);
                            modelFATT.modelInner.render(fcaps, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, renderScale, true);
                        } catch (Exception e) {
                            break INNER;
                        }
                    } else {
                        modelFATT.modelInner.render(entitylivingbaseIn.maidCaps, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, renderScale, true);
                    }
                }

                // 発光Inner
                INNERLIGHT:
                if (modelFATT.modelInner != null) {
                    ResourceLocation texInnerLight = modelFATT.textureInnerLight[renderParts];
                    if (texInnerLight != null
                        //&& lmm.isArmorVisible(1)
                    ) {
                        try {
                            Minecraft.getInstance().getTextureManager().bindTexture(texInnerLight);
                            GL11.glEnable(GL11.GL_BLEND);
                            GL11.glDisable(GL11.GL_ALPHA_TEST);
                            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                            GL11.glDepthFunc(GL11.GL_LEQUAL);

                            RendererHelper.setLightmapTextureCoords(0x00f000f0);//61680
                            if (modelFATT.textureLightColor == null) {
                                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                            } else {
                                //発光色を調整
                                GL11.glColor4f(
                                        modelFATT.textureLightColor[0],
                                        modelFATT.textureLightColor[1],
                                        modelFATT.textureLightColor[2],
                                        modelFATT.textureLightColor[3]);
                            }
                            modelFATT.modelInner.render(fcaps, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, renderScale, true);
                            RendererHelper.setLightmapTextureCoords(modelFATT.lighting);
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                            GL11.glDisable(GL11.GL_BLEND);
                            GL11.glEnable(GL11.GL_ALPHA_TEST);
                        } catch (Exception e) {
                            break INNERLIGHT;
                        }
                    }
                }

//			Minecraft.getMinecraft().getTextureManager().deleteTexture(lmm.getTextures(0)[renderParts]);
                //Outer
//			if(LittleMaidReengaged.cfg_isModelAlphaBlend) GL11.glEnable(GL11.GL_BLEND);
                OUTER:
                {
                    if (modelFATT.textureOuter != null) {
                        ResourceLocation texOuter = modelFATT.textureOuter[renderParts];
                        if (texOuter != null
                            //&& lmm.isArmorVisible(2)
                        ) {
                            try {
                                Minecraft.getInstance().getTextureManager().bindTexture(texOuter);
                                GL11.glEnable(GL11.GL_BLEND);
                                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                                modelFATT.modelOuter.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, renderScale, fcaps);
                                modelFATT.modelOuter.setLivingAnimations(fcaps, limbSwing, limbSwingAmount, partialTicks);
                                modelFATT.modelOuter.render(fcaps, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, renderScale, true);
                            } catch (Exception e) {
                                break OUTER;
                            }
                        } else {
                            modelFATT.modelOuter.render(entitylivingbaseIn.maidCaps, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, renderScale, true);
                        }
                    }

                    // 発光Outer
                    OUTERLIGHT:
                    if (modelFATT.modelOuter != null) {
                        ResourceLocation texOuterLight = modelFATT.textureOuterLight[renderParts];
                        if (texOuterLight != null
                            //&& lmm.isArmorVisible(3)
                        ) {
                            try {
                                Minecraft.getInstance().getTextureManager().bindTexture(texOuterLight);
                                GL11.glEnable(GL11.GL_BLEND);
                                GL11.glEnable(GL11.GL_ALPHA_TEST);
                                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                                GL11.glDepthFunc(GL11.GL_LEQUAL);

                                RendererHelper.setLightmapTextureCoords(0x00f000f0);//61680
                                if (modelFATT.textureLightColor == null) {
                                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                                } else {
                                    //発光色を調整
                                    GL11.glColor4f(
                                            modelFATT.textureLightColor[0],
                                            modelFATT.textureLightColor[1],
                                            modelFATT.textureLightColor[2],
                                            modelFATT.textureLightColor[3]);
                                }
                                //if(lmm.isArmorVisible(1)) modelFATT.modelOuter.render(fcaps, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, renderScale, true);
                                RendererHelper.setLightmapTextureCoords(modelFATT.lighting);
                                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                                GL11.glDisable(GL11.GL_BLEND);
                                GL11.glEnable(GL11.GL_ALPHA_TEST);
                            } catch (Exception e) {
                                break OUTERLIGHT;
                            }
                            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        }
                    }
                }
            }
        }

        public boolean shouldCombineTextures() {
            return false;
        }
    }

    /**
     * 手持ちアイテムレイヤー
     */
    public class MMMHeldItemLayer<T extends LittleMaidBaseEntity, M extends ModelBase<T>> extends LayerRenderer<T, M> {

        //レイヤーと化したアイテム描画

        public MMMHeldItemLayer(IEntityRenderer<T, M> p_i46115_1_) {
            super(p_i46115_1_);
        }

        public void render(T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
            boolean flag = entityIn.getPrimaryHand() == HandSide.RIGHT;
            ItemStack itemstack = flag ? entityIn.getHeldItemOffhand() : entityIn.getHeldItemMainhand();
            ItemStack itemstack1 = flag ? entityIn.getHeldItemMainhand() : entityIn.getHeldItemOffhand();
            if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
                GlStateManager.pushMatrix();

                this.renderHeldItem(entityIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT);
                this.renderHeldItem(entityIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT);
                GlStateManager.popMatrix();
            }
        }

        @Override
        public boolean shouldCombineTextures() {
            return false;
        }

        private void renderHeldItem(T p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, HandSide handSide) {
            if (!p_188358_2_.isEmpty()) {
                boolean flag = handSide == HandSide.LEFT;
                int i = 0;

                i = flag ? 1 : 0;

                GlStateManager.pushMatrix();
                if (p_188358_1_.shouldRenderSneaking()) {
                    GlStateManager.translatef(0.0F, 0.2F, 0.0F);
                }

                GlStateManager.translatef(0.0F, 0.3F, 0.0F);

                // Forge: moved this call down, fixes incorrect offset while sneaking.
                //Force render(?)
                if (modelMain.model instanceof ModelLittleMaidBase) {
                    if (flag) {
                        ((ModelLittleMaidBase) modelMain.model).bipedLeftArm.postRender(0.0625F);
                    } else {
                        ((ModelLittleMaidBase) modelMain.model).bipedRightArm.postRender(0.0625F);
                    }
                }

                GlStateManager.rotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);

                GlStateManager.translatef((float) (flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
                Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
                GlStateManager.popMatrix();
            }
        }

        protected void translateToHand(HandSide p_191361_1_) {
            ((IHasArm) this.getEntityModel()).postRenderArm(0.0625F, p_191361_1_);
        }
    }

    @Override
    public void setModelValues(T par1EntityLiving, double par2,
                               double par4, double par6, float par8, float par9, IModelCaps pEntityCaps) {
        LittleMaidBaseEntity lmaid = par1EntityLiving;
        super.setModelValues(par1EntityLiving, par2, par4, par6, par8, par9, pEntityCaps);

//		modelMain.setRender(this);
//		modelMain.setEntityCaps(pEntityCaps);
//		modelMain.showAllParts();
//		modelMain.isAlphablend = true;
//		modelFATT.isAlphablend = true;

        modelMain.setCapsValue(IModelCaps.caps_heldItemLeft, (Integer) 0);
        modelMain.setCapsValue(IModelCaps.caps_heldItemRight, (Integer) 0);
        //modelMain.setCapsValue(IModelCaps.caps_onGround, renderSwingProgress(lmaid, par9));
        modelMain.setCapsValue(IModelCaps.caps_onGround, lmaid.getSwingProgress(par9, lmaid.getSwingHand()), lmaid.getSwingProgress(par9, lmaid.getSwingHand()));
        //modelMain.setCapsValue(IModelCaps.caps_isRiding, lmaid.isRidingRender());
        modelMain.setCapsValue(IModelCaps.caps_isSneak, lmaid.isSneaking());
        /* modelMain.setCapsValue(IModelCaps.caps_aimedBow, lmaid.isAimebow());*/
        modelMain.setCapsValue(IModelCaps.caps_isWait, lmaid.isMaidWait());
        modelMain.setCapsValue(IModelCaps.caps_isChild, lmaid.isChild());
        modelMain.setCapsValue(IModelCaps.caps_entityIdFactor, lmaid.entityIdFactor);
        modelMain.setCapsValue(IModelCaps.caps_ticksExisted, lmaid.ticksExisted);
        modelMain.setCapsValue(IModelCaps.caps_dominantArm, lmaid.getPrimaryHand().ordinal());

        //カスタム設定
        //modelMain.setCapsValue(IModelCaps.caps_motionSitting, lmaid.isMotionSitting());

        modelFATT.setModelAttributes(entityModel);
        modelMain.setModelAttributes(entityModel);
        // だが無意味だ
//		plittleMaid.textureModel0.isChild = plittleMaid.textureModel1.isChild = plittleMaid.textureModel2.isChild = plittleMaid.isChild();
    }

    /*
        @SuppressWarnings("unused")
        protected void renderString(LMM_LittleMaidEntity plittleMaid, double px, double py, double pz, float f, float f1) {
            // ひも
            // TODO 傍目にみた表示がおかしい
            if(plittleMaid.mstatgotcha != null && plittleMaid.mstatgotcha instanceof LivingEntity) {
                LivingEntity lel = (LivingEntity)plittleMaid.mstatgotcha;
                py -= 0.5D;
                Tessellator tessellator = Tessellator.getInstance();
                float f9 = ((lel.prevRotationYaw + (lel.rotationYaw - lel.prevRotationYaw) * f1 * 0.5F) * 3.141593F) / 180F;
                float f3 = ((lel.prevRotationPitch + (lel.rotationPitch - lel.prevRotationPitch) * f1 * 0.5F) * 3.141593F) / 180F;
                double d3 = MathHelper.sin(f9);
                double d5 = MathHelper.cos(f9);
                float f11 = lel.getSwingProgress(f1);
                float f12 = MathHelper.sin(MathHelper.sqrt_float(f11) * 3.141593F);
                Vec3 vec3d = new Vec3d(-0.5D, 0.029999999999999999D, 0.55D);

                vec3d.rotatePitch((-(lel.prevRotationPitch + (lel.rotationPitch - lel.prevRotationPitch) * f1) * 3.141593F) / 180F);
                vec3d.rotateYaw((-(lel.prevRotationYaw + (lel.rotationYaw - lel.prevRotationYaw) * f1) * 3.141593F) / 180F);
                //vec3d.rotateAroundY(f12 * 0.5F);
                //vec3d.rotateAroundX(-f12 * 0.7F);

                double d7 = lel.prevPosX + (lel.posX - lel.prevPosX) * f1 + vec3d.xCoord;
                double d8 = lel.prevPosY + (lel.posY - lel.prevPosY) * f1 + vec3d.yCoord;
                double d9 = lel.prevPosZ + (lel.posZ - lel.prevPosZ) * f1 + vec3d.zCoord;
                if(renderManager.options.thirdPersonView > 0) {
                    float f4 = ((lel.prevRenderYawOffset + (lel.renderYawOffset - lel.prevRenderYawOffset) * f1) * 3.141593F) / 180F;
                    double d11 = MathHelper.sin(f4);
                    double d13 = MathHelper.cos(f4);
                    d7 = (lel.prevPosX + (lel.posX - lel.prevPosX) * f1) - d13 * 0.34999999999999998D - d11 * 0.54999999999999998D;
                    d8 = (lel.prevPosY + (lel.posY - lel.prevPosY) * f1) - 0.45000000000000001D;
                    d9 = ((lel.prevPosZ + (lel.posZ - lel.prevPosZ) * f1) - d11 * 0.34999999999999998D) + d13 * 0.54999999999999998D;
                }
                double d10 = plittleMaid.prevPosX + (plittleMaid.posX - plittleMaid.prevPosX) * f1;
                double d12 = plittleMaid.prevPosY + (plittleMaid.posY - plittleMaid.prevPosY) * f1 + 0.25D - 0.5D;//+ 0.75D;
                double d14 = plittleMaid.prevPosZ + (plittleMaid.posZ - plittleMaid.prevPosZ) * f1;
                double d15 = (float)(d7 - d10);
                double d16 = (float)(d8 - d12);
                double d17 = (float)(d9 - d14);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                //tessellator.startDrawing(3);
                try {
                    tessellator.getWorldRenderer().func_181668_a(3, new VertexFormat()
                        .func_181721_a(new VertexFormatElement(0, EnumType.FLOAT, EnumUsage.NORMAL, 16)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //tessellator.setColorOpaque_I(0);
                GlStateManager.color(1f, 1f, 1f);
                tessellator.getWorldRenderer().putPositionData(new int[]{1, 1, 1, 12, 24});
                int i = 16;
                for(int j = 0; j <= i; j++)
                {
                    float f5 = (float)j / (float)i;
                    try {
                        tessellator.getWorldRenderer().putPosition(px + d15 * f5, py + d16 * (f5 * f5 + f5) * 0.5D + (((float)i - (float)j) / (i * 0.75F) + 0.125F), pz + d17 * f5);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

                tessellator.draw();
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }
        }
    */

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {

        LittleMaidBaseEntity lmm = entity;

        fcaps = lmm.maidCaps;
//
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected int getColorMultiplier(T par1EntityLiving, float par2, float par3) {
        //TODO
        return par1EntityLiving.colorMultiplier(par2, par3);
    }

    @Override
    protected ResourceLocation getEntityTexture(T par1EntityLiving) {
        // テクスチャリソースを返すところだけれど、基本的に使用しない。
        return par1EntityLiving.getTextures(0)[0];
    }

    private class MMMLayerHeadArmor<T extends LittleMaidBaseEntity, M extends ModelBase<T>> extends LayerRenderer<T, M> {
        private final ModelLittleMaid_Orign<LittleMaidBaseEntity> hatModel = new ModelLittleMaid_Orign<>();

        public MMMLayerHeadArmor(IEntityRenderer<T, M> littleMaidRender) {
            super(littleMaidRender);
        }

        @Override
        public void render(T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
            if (entityIn.getInventoryMaidEquipment().getheadItem().getItem() instanceof IMaidArmor) {
                GlStateManager.pushMatrix();

                ResourceLocation resourceLocation = getArmorResource(entityIn.getInventoryMaidEquipment().getheadItem().getItem(), entityIn.getModelNameMain().toLowerCase());

                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.bindTexture(resourceLocation);
                hatModel.setLivingAnimations(entityIn, p_212842_2_, p_212842_3_, p_212842_4_);
                hatModel.render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_, false);

                GlStateManager.popMatrix();
            }
        }

        private ResourceLocation getArmorResource(Item armor, @Nullable String p_177178_3_) {
            int index;
            String loc = p_177178_3_;

            if (p_177178_3_ != null) {
                int _len = p_177178_3_.length();
                index = p_177178_3_.lastIndexOf("_");

                if (index != -1) {
                    String right = "_Origin";
                    loc.replace(right, "");
                }
            }

            if (armor instanceof IMaidArmor) {
                String s = "maidmodredo:textures/models/armor/" + ((IMaidArmor) armor).getMaidArmorTextureName() + ".png";
                return new ResourceLocation(s);
            } else {
                return new ResourceLocation("maidmodredo:textures/models/armor/bagu_hat");
            }
        }

        @Override
        public boolean shouldCombineTextures() {
            return false;
        }
    }
}