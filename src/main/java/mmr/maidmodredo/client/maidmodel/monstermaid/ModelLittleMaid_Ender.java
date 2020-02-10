package mmr.maidmodredo.client.maidmodel.monstermaid;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mmr.maidmodredo.api.IMaidAnimation;
import mmr.maidmodredo.client.maidmodel.IModelCaps;
import mmr.maidmodredo.client.maidmodel.MaidModelRenderer;
import mmr.maidmodredo.client.maidmodel.ModelMultiMMMBase;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.entity.monstermaid.EnderMaidEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;

public class ModelLittleMaid_Ender<T extends EnderMaidEntity> extends ModelMultiMMMBase<T> {
    private MaidModelRenderer maidCap;
    public MaidModelRenderer handR;
    public MaidModelRenderer legR;
    public MaidModelRenderer head;
    public MaidModelRenderer body;
    public MaidModelRenderer handL;
    public MaidModelRenderer legL;
    public MaidModelRenderer jaw;
    public MaidModelRenderer hair;

    public ModelLittleMaid_Ender() {
        super();
    }

    public ModelLittleMaid_Ender(float psize) {
        super(psize);
    }

    public ModelLittleMaid_Ender(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
        super(psize, 0.0F, 128, 64);
    }


    @Override
    public void initModel(float psize, float pyoffset) {
        textureWidth = 128;
        textureHeight = 64;

        mainFrame = new MaidModelRenderer(this, 0, 0);
        mainFrame.setRotationPoint(0F, 0F + 0F, 0F);
        this.handL = new MaidModelRenderer(this, 56, 0);
        this.handL.setRotationPoint(4.5F, -1.6F, 0.0F);
        this.handL.addBox(-1.0F, -2.0F, -1.0F, 2, 16, 2, 0.0F);
        this.setRotateAngle(handL, 0.0F, 0.0F, -0.10000736613927509F);
        this.jaw = new MaidModelRenderer(this, 0, 16);
        this.jaw.setRotationPoint(0.0F, -4.0F, -0.0F);
        this.jaw.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F);
        this.head = new MaidModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -4.0F, -0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.body = new MaidModelRenderer(this, 32, 16);
        this.body.setRotationPoint(0.0F, -4.0F, -0.0F);
        this.body.addBox(-3.5F, 0.0F, -2.0F, 7, 11, 4, 0.0F);
        this.hair = new MaidModelRenderer(this, 0, 32);
        this.hair.setRotationPoint(0.0F, -4.0F, -0.0F);
        this.hair.addBox(-4.0F, -8.0F, -4.0F, 8, 12, 8, 0.75F);
        this.handR = new MaidModelRenderer(this, 56, 0);
        this.handR.setRotationPoint(-4.5F, -1.6F, 0.0F);
        this.handR.addBox(-1.0F, -2.0F, -1.0F, 2, 16, 2, 0.0F);
        this.setRotateAngle(handR, 0.0F, 0.0F, 0.10000736613927509F);
        this.legR = new MaidModelRenderer(this, 56, 0);
        this.legR.setRotationPoint(-2.0F, 7.0F, 0.0F);
        this.legR.addBox(-1.0F, 0.0F, -1.0F, 2, 17, 2, 0.0F);
        this.legL = new MaidModelRenderer(this, 56, 0);
        this.legL.setRotationPoint(2.0F, 7.0F, 0.0F);
        this.legL.addBox(-1.0F, 0.0F, -1.0F, 2, 17, 2, 0.0F);

        maidCap = new MaidModelRenderer(this, 35, 10);
        maidCap.setRotationPoint(0.0F, -4.0F, 0.0F);
        maidCap.addBox(-3.5F, -10.0F, -4.0F, 7, 2, 2, 0.0F, false);
        this.mainFrame.addChild(head);
        this.mainFrame.addChild(body);
        this.mainFrame.addChild(hair);
        this.mainFrame.addChild(jaw);
        this.mainFrame.addChild(legR);
        this.mainFrame.addChild(legL);
        this.mainFrame.addChild(handR);
        this.mainFrame.addChild(handL);
        this.mainFrame.addChild(maidCap);
    }

    @Override
    public void render(IModelCaps pEntityCaps, MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, boolean pIsRender) {
        ImmutableList.of(this.mainFrame).forEach((p_228292_8_) -> {
            p_228292_8_.render(matrixStack, iVertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
        //renderStabilizer(pEntityCaps, par2, par3, ticksExisted, pheadYaw, pheadPitch, par7);
    }

    @Override
    public float getHeight() {
        return 1.95F;
    }

    @Override
    public float getWidth() {
        return 0.55F;
    }

    @Override
    public float getyOffset() {
        return getHeight() * 0.85F;
    }

    @Override
    public float getMountedYOffset() {
        return 0.35F;
    }


    @Override
    public void renderItems(IModelCaps pEntityCaps, MatrixStack stack, boolean left) {
        if (left) {
            this.handL.setAnglesAndRotation(stack);
        } else {
            this.handR.setAnglesAndRotation(stack);
        }
    }

    @Override
    public void renderFirstPersonHand(IModelCaps pEntityCaps) {

    }

    @Override
    public float[] getArmorModelsSize() {
        return new float[]{0.55F, 1.95F};
    }


    public void changeModel(IModelCaps pEntityCaps) {
        // カウンタ系の加算値、リミット値の設定など行う予定。
    }

    public boolean supportForEntity(Class entity) {
        return entity == EnderMaidEntity.class;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks,
                                  float pHeadYaw, float pHeadPitch, IModelCaps pEntityCaps) {
        setDefaultPause(limbSwing, limbSwingAmount, ageInTicks, pHeadYaw, pHeadPitch, pEntityCaps);

        if (isRiding) {
            // 乗り物に乗っている
            handR.addRotateAngleX(-0.6283185F);
            handL.addRotateAngleX(-0.6283185F);
            legR.setRotateAngleX(-1.256637F);
            legL.setRotateAngleX(-1.256637F);
            legR.setRotateAngleY(0.3141593F);
            legL.setRotateAngleY(-0.3141593F);
//			mainFrame.rotationPointY += 5.00F;
        }

        //カスタム設定
        //お座りモーションの場合はモデル側で位置を調整する
        if (motionSitting && isRiding) {
            mainFrame.rotationPointY += 5.00F;
        }

        // アイテム持ってるときの腕振りを抑える+表示角オフセット
        if (heldItem[1] != 0) {
            handL.setRotateAngleX(handL.getRotateAngleX() * 0.5F);
            handL.addRotateAngleDegX(-18F * heldItem[1]);
        }
        if (heldItem[0] != 0) {
            handR.setRotateAngleX(handR.getRotateAngleX() * 0.5F);
            handR.addRotateAngleDegX(-18F * heldItem[0]);
        }

//		handR.setRotateAngleY(0.0F);
//		handL.setRotateAngleY(0.0F);

        if ((onGrounds[0] > -9990F || onGrounds[1] > -9990F) && !aimedBow) {
            // 腕振り
            float f6, f7, f8;
            f6 = mh_sin(mh_sqrt(onGrounds[0]) * (float) Math.PI * 2.0F);
            f7 = mh_sin(mh_sqrt(onGrounds[1]) * (float) Math.PI * 2.0F);
            body.setRotateAngleY((f6 - f7) * 0.2F);
            handR.addRotateAngleY(body.rotateAngleY);
            handL.addRotateAngleY(body.rotateAngleY);
            head.addRotateAngleY(-body.rotateAngleY);
            // R
            if (onGrounds[0] > 0F) {
                f6 = 1.0F - onGrounds[0];
                f6 *= f6;
                f6 *= f6;
                f6 = 1.0F - f6;
                f7 = mh_sin(f6 * (float) Math.PI);
                f8 = mh_sin(onGrounds[0] * (float) Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
                handR.addRotateAngleX(-f7 * 1.2F - f8);
                handR.addRotateAngleY(body.rotateAngleY * 2.0F);
                handR.setRotateAngleZ(mh_sin(onGrounds[0] * 3.141593F) * -0.4F);
            } else {
                handR.addRotateAngleX(body.rotateAngleY);
            }
            // L
            if (onGrounds[1] > 0F) {
                f6 = 1.0F - onGrounds[1];
                f6 *= f6;
                f6 *= f6;
                f6 = 1.0F - f6;
                f7 = mh_sin(f6 * (float) Math.PI);
                f8 = mh_sin(onGrounds[1] * (float) Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
                handL.rotateAngleX -= f7 * 1.2D + f8;
                handL.rotateAngleY += body.rotateAngleY * 2.0F;
                handL.setRotateAngleZ(mh_sin(onGrounds[1] * 3.141593F) * 0.4F);
            } else {
                handL.rotateAngleX += body.rotateAngleY;
            }
        }
        if (isSneak) {
           /* // しゃがみ
            body.rotateAngleX += 0.5F;
            bipedNeck.rotateAngleX -= 0.5F;
            handR.rotateAngleX += 0.2F;
            handL.rotateAngleX += 0.2F;

            bipedPelvic.addRotationPointY(-0.5F);
            bipedPelvic.addRotationPointZ(-0.6F);
            bipedPelvic.addRotateAngleX(-0.5F);
            head.rotationPointY = 1.0F;
//			Skirt.setRotationPoint(0.0F, 5.8F, 2.7F);
            Skirt.rotationPointY -= 0.25F;
            Skirt.rotationPointZ += 0.00F;
            Skirt.addRotateAngleX(0.2F);*/
        } else {
            // 通常立ち
        }
        if (isWait) {
            //待機状態の特別表示
            float lx = mh_sin(ageInTicks * 0.067F) * 0.05F - 0.7F;
            handR.setRotateAngle(lx, 0.0F, -0.4F);
            handL.setRotateAngle(lx, 0.0F, 0.4F);
        } else {
            float la, lb, lc;

            LittleMaidBaseEntity entity = (LittleMaidBaseEntity) pEntityCaps.getCapsValue(IModelCaps.caps_Entity);

            if (aimedBow) {
                Boolean isCharging = (Boolean) pEntityCaps.getCapsValue(IModelCaps.caps_crossbow);

                if (isCharging) {
                    this.handR.rotateAngleY = -0.8F;
                    this.handR.rotateAngleX = -0.97079635F;
                    this.handL.rotateAngleX = -0.97079635F;
                    float f2 = MathHelper.clamp(entity.getItemInUseMaxCount(), 0.0F, 25.0F);
                    this.handL.rotateAngleY = MathHelper.lerp(f2 / 25.0F, 0.4F, 0.85F);
                    this.handL.rotateAngleX = MathHelper.lerp(f2 / 25.0F, this.handL.rotateAngleX, (-(float) Math.PI / 2F));
                } else {
                    // 弓構え
                    float lonGround = onGrounds[dominantArm];
                    float f6 = mh_sin(lonGround * 3.141593F);
                    float f7 = mh_sin((1.0F - (1.0F - lonGround) * (1.0F - lonGround)) * 3.141593F);
                    la = 0.1F - f6 * 0.6F;
                    handR.setRotateAngle(-1.470796F, -la, 0.0F);
                    handL.setRotateAngle(-1.470796F, la, 0.0F);
                    la = head.rotateAngleX;
                    lb = mh_sin(ageInTicks * 0.067F) * 0.05F;
                    lc = f6 * 1.2F - f7 * 0.4F;
                    handR.addRotateAngleX(la + lb - lc);
                    handL.addRotateAngleX(la - lb - lc);
                    la = head.rotateAngleY;
                    handR.addRotateAngleY(la);
                    handL.addRotateAngleY(la);
                    la = mh_cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                    handR.addRotateAngleZ(la);
                    handL.addRotateAngleZ(-la);
                }
            } else if (entity.isHolding(Items.CROSSBOW)) {
                this.handR.rotateAngleY = -0.3F + this.head.rotateAngleY;
                this.handL.rotateAngleY = 0.6F + this.head.rotateAngleY;
                this.handR.rotateAngleX = (-(float) Math.PI / 2F) + this.head.rotateAngleX + 0.1F;
                this.handL.rotateAngleX = -1.5F + this.head.rotateAngleX;
            } else {
                // 通常
                la = mh_sin(ageInTicks * 0.067F) * 0.05F;
                lc = 0.5F + mh_cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                handR.addRotateAngleX(la);
                handL.addRotateAngleX(-la);
                handR.addRotateAngleZ(lc);
                handL.addRotateAngleZ(-lc);
            }
        }

        Entity entity = (Entity) pEntityCaps.getCapsValue(IModelCaps.caps_Entity);

        if (entity instanceof IMaidAnimation) {
            setAnimations(limbSwing, limbSwingAmount, ageInTicks, pHeadYaw, pHeadPitch, pEntityCaps, ((IMaidAnimation) entity));
        }
    }

    public void setAnimations(float par1, float par2, float ageInTicks, float pHeadYaw, float pHeadPitch, IModelCaps pEntityCaps, IMaidAnimation animation) {


        animator.update(animation);
        if (animation.getAnimation() == LittleMaidBaseEntity.TALK_ANIMATION) {

            animator.setAnimation(LittleMaidBaseEntity.TALK_ANIMATION);
            animator.startKeyframe(5);

            this.handR.setRotateAngleZ(0.0F);
            this.handL.setRotateAngleZ(0.0F);

            animator.rotate(this.handR, -1.2F, 0, 0);
            animator.rotate(this.handL, -1.2F, 0, 0);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.handR, 0.2F, 0, 0);
            animator.rotate(this.handL, 0.2F, 0, 0);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.handR, -0.2F, 0, 0);
            animator.rotate(this.handL, -0.2F, 0, 0);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.handR, 0.2F, 0, 0);
            animator.rotate(this.handL, 0.2F, 0, 0);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.handR, -0.2F, 0, 0);
            animator.rotate(this.handL, -0.2F, 0, 0);
            animator.endKeyframe();
            animator.setStaticKeyframe(45);
            animator.resetKeyframe(10);
        }

        if (animation.getAnimation() == LittleMaidBaseEntity.PET_ANIMATION) {
            animator.setAnimation(LittleMaidBaseEntity.PET_ANIMATION);
            animator.startKeyframe(10);
            animator.rotate(this.head, 0.4F, -0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.head, 0.4F, 0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.head, 0.4F, -0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.head, 0.4F, 0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.head, 0.4F, -0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.head, 0.4F, 0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.head, 0.4F, -0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.head, 0.4F, 0.2F, 0.0F);
            animator.endKeyframe();

            animator.setStaticKeyframe(10);
            animator.resetKeyframe(10);
        }

        if (animation.getAnimation() == LittleMaidBaseEntity.FARM_ANIMATION) {
            animator.setAnimation(LittleMaidBaseEntity.FARM_ANIMATION);
            animator.startKeyframe(10);
            animator.rotate(this.body, 0.4F, 0.0F, 0.0F);
            animator.rotate(this.handR, -0.4F, 0.0F, 0.0F);
            animator.rotate(this.handL, -0.4F, 0.0F, 0.0F);
            animator.endKeyframe();


            animator.resetKeyframe(5);
        }

        if (animation.getAnimation() == LittleMaidBaseEntity.EAT_ANIMATION) {
            handR.setRotateAngle(0.0F, 0.0F, 0.0F);
            handL.setRotateAngle(0.0F, 0.0F, 0.0F);

            animator.setAnimation(LittleMaidBaseEntity.EAT_ANIMATION);
            animator.startKeyframe(2);
            animator.rotate(this.handR, -1.6F, -0.6F, 0.0F);
            animator.rotate(this.handL, -1.6F, 0.6F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(2);
            animator.rotate(this.handR, -1.8F, -0.6F, 0.0F);
            animator.rotate(this.handL, -1.8F, 0.6F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(2);
            animator.rotate(this.handR, -1.6F, -0.6F, 0.0F);
            animator.rotate(this.handL, -1.6F, 0.6F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(2);
            animator.rotate(this.handR, -1.8F, -0.6F, 0.0F);
            animator.rotate(this.handL, -1.8F, 0.6F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(2);
            animator.rotate(this.handR, -1.6F, -0.6F, 0.0F);
            animator.rotate(this.handL, -1.6F, 0.6F, 0.0F);
            animator.endKeyframe();


            animator.resetKeyframe(4);
        }
    }

    public void setDefaultPause(float limbSwing, float limbSwingAmount, float ageInTicks,
                                float pHeadYaw, float pHeadPitch, IModelCaps pEntityCaps) {
        LittleMaidBaseEntity baseEntity = (LittleMaidBaseEntity) pEntityCaps.getCapsValue(IModelCaps.caps_Entity);

        this.head.rotateAngleY = pHeadYaw * ((float) Math.PI / 180F);
        this.head.rotateAngleX = pHeadPitch * ((float) Math.PI / 180F);


        this.handR.rotateAngleY = 0.0F;
        this.handL.rotateAngleY = 0.0F;
        this.handR.rotateAngleZ = 0.0F;
        this.handL.rotateAngleZ = 0.0F;

        this.body.rotateAngleY = 0.0F;

        this.legR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.legL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.legR.rotateAngleY = 0.0F;
        this.legL.rotateAngleY = 0.0F;

        if (baseEntity.isMaidWait()) {
            handR.rotateAngleZ = -0.4F;
            handL.rotateAngleZ = 0.4F;
            handR.rotateAngleX = -0.4F;
            handL.rotateAngleX = -0.4F;
        } else {
            this.handR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / 1.0F;
            this.handL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / 1.0F;
        }

        this.handR.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handL.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handR.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.handL.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        this.jaw.copyModelAngles(head);
        this.hair.copyModelAngles(head);
        this.maidCap.copyModelAngles(head);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(MaidModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
