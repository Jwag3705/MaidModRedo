package mmr.maidmodredo.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mmr.maidmodredo.client.maidmodel.IModelCaps;
import mmr.maidmodredo.client.maidmodel.ModelBase;
import mmr.maidmodredo.client.maidmodel.ModelLittleMaidBase;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.utils.helper.RendererHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LittleMaidBaseRender<T extends LittleMaidBaseEntity> extends ModelMultiRender<T> {

    // Method
    public LittleMaidBaseRender(EntityRendererManager manager) {
        super(manager, 0.4F);


        //addLayer(new MMMLayerArmor<>(this));
        //addLayer(new MMMLayerHeadArmor<>(this));
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

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_) {
            this.renderArmorLayer(matrixStackIn, bufferIn, packedLightIn, entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, 0, EquipmentSlotType.CHEST);
            this.renderArmorLayer(matrixStackIn, bufferIn, packedLightIn, entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, 1, EquipmentSlotType.LEGS);
            this.renderArmorLayer(matrixStackIn, bufferIn, packedLightIn, entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, 2, EquipmentSlotType.FEET);
            this.renderArmorLayer(matrixStackIn, bufferIn, packedLightIn, entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, 3, EquipmentSlotType.HEAD);
        }

        public void renderArmorLayer(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, int renderParts, EquipmentSlotType slotType) {
//			boolean lri = (renderCount & 0x0f) == 0;
            //if (!lmm.maidInventory.armorItemInSlot(i).isEmpty()) {
            if (!entitylivingbaseIn.getItemStackFromSlot(slotType).isEmpty()) {
                //総合
                modelFATT.showArmorParts(renderParts);

                //Inner
                INNER:
                {
                    /*if (modelFATT.textureInner != null) {
                        ResourceLocation texInner = modelFATT.textureInner[renderParts];
                        if (texInner != null
                            //&& lmm.isArmorVisible(0)
                        ) try {
                            Minecraft.getInstance().getTextureManager().bindTexture(texInner);
                            GL11.glEnable(GL11.GL_BLEND);
                            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                            modelFATT.modelInner.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, fcaps);
                            modelFATT.modelInner.setLivingAnimations(fcaps, limbSwing, limbSwingAmount, partialTicks);
                            modelFATT.modelInner.render(fcaps,matrixStackIn, bufferIn.getBuffer(RendetType), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, renderScale, true);
                        } catch (Exception e) {
                            break INNER;
                        }
                    } else {
                        modelFATT.modelInner.render(entitylivingbaseIn.maidCaps, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, true);
                    }*/
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
                            //modelFATT.modelInner.render(fcaps, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, renderScale, true);
                            RendererHelper.setLightmapTextureCoords((int) modelFATT.lighting);
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
                    /*if (modelFATT.textureOuter != null) {
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
                    }*/

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
                                RendererHelper.setLightmapTextureCoords((int) modelFATT.lighting);
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

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_) {
            boolean flag = entityIn.getPrimaryHand() == HandSide.RIGHT;
            ItemStack itemstack = flag ? entityIn.getHeldItemOffhand() : entityIn.getHeldItemMainhand();
            ItemStack itemstack1 = flag ? entityIn.getHeldItemMainhand() : entityIn.getHeldItemOffhand();
            if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
                matrixStackIn.push();

                this.renderHeldItem(matrixStackIn, bufferIn, packedLightIn, entityIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT);
                this.renderHeldItem(matrixStackIn, bufferIn, packedLightIn, entityIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT);
                matrixStackIn.pop();
            }
        }

        private void renderHeldItem(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, HandSide handSide) {
            if (!p_188358_2_.isEmpty()) {
                boolean flag = handSide == HandSide.LEFT;
                int i = 0;

                i = flag ? 1 : 0;

                matrixStackIn.push();
                /*if (p_188358_1_.shouldRenderSneaking()) {
                    GlStateManager.translatef(0.0F, 0.2F, 0.0F);
                }*/

                matrixStackIn.translate(0.0F, 0.3F, 0.0F);

                // Forge: moved this call down, fixes incorrect offset while sneaking.
                //Force render(?)
                if (modelMain.model instanceof ModelLittleMaidBase) {
                    if (flag) {
                        ((ModelLittleMaidBase) modelMain.model).bipedLeftArm.setAnglesAndRotation(matrixStackIn);
                    } else {
                        ((ModelLittleMaidBase) modelMain.model).bipedRightArm.setAnglesAndRotation(matrixStackIn);
                    }
                }

                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-90.0F));
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));

                matrixStackIn.translate((float) (flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
                Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag, matrixStackIn, bufferIn, packedLightIn);
                matrixStackIn.pop();
            }
        }

    }

    @Override
    public void setModelValues(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, IModelCaps pEntityCaps) {
        LittleMaidBaseEntity lmaid = entityIn;
        super.setModelValues(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn, pEntityCaps);

//		modelMain.setRender(this);
//		modelMain.setEntityCaps(pEntityCaps);
//		modelMain.showAllParts();
//		modelMain.isAlphablend = true;
//		modelFATT.isAlphablend = true;


        //カスタム設定
        //modelMain.setCapsValue(IModelCaps.caps_motionSitting, lmaid.isMotionSitting());
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
    public ResourceLocation getEntityTexture(T par1EntityLiving) {
        // テクスチャリソースを返すところだけれど、基本的に使用しない。
        return par1EntityLiving.getTextures(0)[0];
    }
}