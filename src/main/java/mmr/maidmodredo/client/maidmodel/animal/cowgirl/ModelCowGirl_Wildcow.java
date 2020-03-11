package mmr.maidmodredo.client.maidmodel.animal.cowgirl;//Made with Blockbench
//Paste this code into your mod.

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mmr.maidmodredo.api.IMaidAnimation;
import mmr.maidmodredo.client.maidmodel.MaidModelRenderer;
import mmr.maidmodredo.client.maidmodel.ModelMultiMMMBase;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class ModelCowGirl_Wildcow<T extends LittleMaidBaseEntity> extends ModelMultiMMMBase<T> {
    public MaidModelRenderer bipedTorso;
    public MaidModelRenderer bipedNeck;
    public MaidModelRenderer bipedHead;
    public MaidModelRenderer horn1;
    public MaidModelRenderer horn2;
    public MaidModelRenderer bipedHair;
    public MaidModelRenderer SideTailL;
    public MaidModelRenderer SideTailR;
    public MaidModelRenderer bipedRightArm;
    public MaidModelRenderer bipedLeftArm;
    public MaidModelRenderer bipedBody;
    public MaidModelRenderer bipedChest;
    public MaidModelRenderer bipedboob;
    public MaidModelRenderer bipedboobright;
    public MaidModelRenderer bipedPelvic;
    public MaidModelRenderer bipedLeftLeg;
    public MaidModelRenderer bipedRightLeg;
    public MaidModelRenderer bipedTail;

    public ModelCowGirl_Wildcow() {
        this(0.0F);
    }

    public ModelCowGirl_Wildcow(float psize) {
        this(psize, 0.0F, 128, 128);
    }

    public ModelCowGirl_Wildcow(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
        super(psize, 0.0F, 128, 128);
    }

    @Override
    public void initModel(float psize, float pyoffset) {
        textureWidth = 128;
        textureHeight = 128;

        bipedTorso = new MaidModelRenderer(this);
        bipedTorso.setRotationPoint(0.0F, -10.5F, 0.0F);

        bipedNeck = new MaidModelRenderer(this);
        bipedNeck.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedTorso.addChild(bipedNeck);
        bipedNeck.addBox(bipedNeck, 24, 25, -1.0F, 7.5F, -1.0F, 2, 1, 2, 0.5F, false);

        bipedHead = new MaidModelRenderer(this);
        bipedHead.setRotationPoint(0.0F, 9.5F, 0.0F);
        bipedNeck.addChild(bipedHead);
        bipedHead.addBox(bipedHead, 0, 20, -4.0F, -9.0F, -4.0F, 8, 8, 8, 0.0F, false);
        bipedHead.addBox(bipedHead, 0, 0, -5.0F, -10.0F, -5.0F, 10, 10, 10, -0.5F, false);

        horn1 = new MaidModelRenderer(this);
        horn1.setRotationPoint(4.5F, -7.75F, 0.2F);
        setRotationAngle(horn1, 0.0F, 0.0F, -0.1745F);
        bipedHead.addChild(horn1);
        horn1.addBox(horn1, 0, 0, -1.0F, -3.0F, -1.0F, 2, 4, 2, 0.0F, false);

        horn2 = new MaidModelRenderer(this);
        horn2.setRotationPoint(-4.5F, -7.75F, 0.2F);
        setRotationAngle(horn2, 0.0F, 0.0F, 0.1745F);
        bipedHead.addChild(horn2);
        horn2.addBox(horn2, 0, 20, -1.2972F, -3.0396F, -1.0F, 2, 4, 2, 0.0F, false);

        bipedHair = new MaidModelRenderer(this);
        bipedHair.setRotationPoint(0.0F, -18.5F, -0.5F);
        bipedHead.addChild(bipedHair);
        bipedHair.addBox(bipedHair, 32, 32, -4.0F, 8.5F, 3.5F, 8, 17, 2, 0.0F, false);

        SideTailL = new MaidModelRenderer(this);
        SideTailL.setRotationPoint(0.0F, -9.5F, -0.5F);
        setRotationAngle(SideTailL, 0.0F, 0.0F, -0.6109F);
        bipedHead.addChild(SideTailL);
        SideTailL.addBox(SideTailL, 48, 17, -9.0F, -0.5F, -1.5F, 5, 1, 3, 0.0F, false);

        SideTailR = new MaidModelRenderer(this);
        SideTailR.setRotationPoint(0.0F, -9.5F, -0.5F);
        setRotationAngle(SideTailR, 0.0F, 0.0F, 0.6109F);
        bipedHead.addChild(SideTailR);
        SideTailR.addBox(SideTailR, 49, 26, 4.0F, -0.75F, -1.5F, 5, 1, 3, 0.0F, false);

        bipedRightArm = new MaidModelRenderer(this);
        bipedRightArm.setRotationPoint(-3.0F, 9.0F, 0.0F);
        setRotationAngle(bipedRightArm, 0.0F, 0.0F, 0.1745F);
        bipedNeck.addChild(bipedRightArm);
        bipedRightArm.addBox(bipedRightArm, 36, 51, -2.0F, -1.0F, -1.0F, 2, 13, 2, 0.0F, false);

        bipedLeftArm = new MaidModelRenderer(this);
        bipedLeftArm.setRotationPoint(3.0F, 9.0F, 0.0F);
        setRotationAngle(bipedLeftArm, 0.0F, 0.0F, -0.1745F);
        bipedNeck.addChild(bipedLeftArm);
        bipedLeftArm.addBox(bipedLeftArm, 28, 51, 0.0F, -1.0F, -1.0F, 2, 13, 2, 0.0F, false);

        bipedBody = new MaidModelRenderer(this);
        bipedBody.setRotationPoint(0.0F, 7.0F, 0.0F);
        bipedTorso.addChild(bipedBody);
        bipedBody.addBox(bipedBody, 32, 20, -3.0F, 1.0F, -2.0F, 6, 5, 4, 0.0F, false);
        bipedBody.addBox(bipedBody, 49, 49, -2.0F, 5.5F, -2.0F, 4, 7, 3, 0.4F, false);
        bipedBody.addBox(bipedBody, 30, 0, -4.0F, 10.5F, -2.0F, 8, 4, 5, 0.1F, false);
        bipedBody.addBox(bipedBody, 40, 9, -3.0F, 9.25F, -2.0F, 6, 4, 4, 0.25F, false);
        bipedBody.addBox(bipedBody, 0, 6, -1.0F, 1.0F, -4.0F, 2, 2, 2, 0.0F, false);

        bipedChest = new MaidModelRenderer(this);
        bipedChest.setRotationPoint(2.0F, 3.0F, -1.25F);
        setRotationAngle(bipedChest, -0.0873F, 0.0F, 0.0F);
        bipedBody.addChild(bipedChest);

        bipedboob = new MaidModelRenderer(this);
        bipedboob.setRotationPoint(-3.5F, 0.5F, 0.25F);
        setRotationAngle(bipedboob, -0.9599F, 0.2618F, 0.0F);
        bipedChest.addChild(bipedboob);
        bipedboob.addBox(bipedboob, 52, 38, -1.4575F, -0.0561F, -1.3864F, 3, 5, 3, 0.25F, false);

        bipedboobright = new MaidModelRenderer(this);
        bipedboobright.setRotationPoint(-0.5F, 0.0F, 0.0F);
        setRotationAngle(bipedboobright, -0.9599F, -0.2618F, 0.0F);
        bipedChest.addChild(bipedboobright);
        bipedboobright.addBox(bipedboobright, 52, 30, -1.512F, -0.011F, -0.8179F, 3, 5, 3, 0.25F, false);

        bipedPelvic = new MaidModelRenderer(this);
        bipedPelvic.setRotationPoint(0.0F, 10.5F, 0.0F);
        bipedTorso.addChild(bipedPelvic);

        bipedLeftLeg = new MaidModelRenderer(this);
        bipedLeftLeg.setRotationPoint(1.0F, 11.0F, 0.0F);
        bipedPelvic.addChild(bipedLeftLeg);
        bipedLeftLeg.addBox(bipedLeftLeg, 0, 36, -1.0F, -3.0F, -2.0F, 3, 16, 4, 0.0F, false);

        bipedRightLeg = new MaidModelRenderer(this);
        bipedRightLeg.setRotationPoint(-1.0F, 11.0F, 0.0F);
        bipedPelvic.addChild(bipedRightLeg);
        bipedRightLeg.addBox(bipedRightLeg, 14, 36, -2.0F, -3.0F, -2.0F, 3, 16, 4, 0.0F, false);

        bipedTail = new MaidModelRenderer(this);
        bipedTail.setRotationPoint(0.5F, 2.75F, 0.0F);
        setRotationAngle(bipedTail, -2.0944F, 0.0F, 0.0F);
        bipedPelvic.addChild(bipedTail);
        bipedTail.addBox(bipedTail, 28, 36, -1.0F, -12.0F, 2.0F, 1, 12, 1, 0.0F, false);
        bipedTail.addBox(bipedTail, 24, 20, -1.5F, -13.0532F, 1.4127F, 2, 3, 2, 0.0F, false);
    }

    @Override
    public float getHeight() {
        return 1.8F;
    }

    @Override
    public float getWidth() {
        return 0.6F;
    }

    @Override
    public float getyOffset() {
        return getHeight() * 0.9F;
    }

    @Override
    public float getMountedYOffset() {
        return 0.35F;
    }

    @Override
    public void renderItems(MatrixStack stack, boolean left) {
        if (left) {
            this.bipedLeftArm.setAnglesAndRotation(stack);
        } else {
            this.bipedRightArm.setAnglesAndRotation(stack);
        }
    }

    @Override
    public float[] getArmorModelsSize() {
        return new float[]{0.1F, 0.5F};
    }


    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.bipedTorso).forEach((p_228292_8_) -> {
            p_228292_8_.render(matrixStack, iVertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setLivingAnimations(T entity, float par2, float par3, float pRenderPartialTicks) {
        super.setLivingAnimations(entity, par2, par3, pRenderPartialTicks);

        float f3 = entity.ticksExisted + pRenderPartialTicks + entity.entityIdFactor;

        // 目パチ
       /* if (entity.isSleeping()) {
            bipedHead.setVisible(false);
        } else if (0 > mh_sin(f3 * 0.05F) + mh_sin(f3 * 0.13F) + mh_sin(f3 * 0.7F) + 2.55F) {
            bipedHeadFace.setVisible(false);
        } else {
            bipedHeadFace.setVisible(true);
        }*/
    }

    @Override
    public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                       float pHeadYaw, float pHeadPitch) {
        setDefaultPause(entity, limbSwing, limbSwingAmount, ageInTicks, pHeadYaw, pHeadPitch);

        if (entity.isPassenger() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit())) {
            // 乗り物に乗っている
            bipedRightArm.addRotateAngleX(-0.6283185F);
            bipedLeftArm.addRotateAngleX(-0.6283185F);
            bipedRightLeg.setRotateAngleX(-1.256637F);
            bipedLeftLeg.setRotateAngleX(-1.256637F);
            bipedRightLeg.setRotateAngleY(0.3141593F);
            bipedLeftLeg.setRotateAngleY(-0.3141593F);
//			mainFrame.rotationPointY += 5.00F;
        }

        /*//カスタム設定
        //お座りモーションの場合はモデル側で位置を調整する
        if (motionSitting && entity.isPassenger() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit()) {
            mainFrame.rotationPointY += 5.00F;
        }*/

        if (this.swingProgress > 0.0F) {
            HandSide handside = this.getMainHand(entity);
            MaidModelRenderer modelrenderer = this.getArmForSide(handside);
            float f1 = this.swingProgress;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;
            if (handside == HandSide.LEFT) {
                this.bipedBody.rotateAngleY *= -1.0F;
            }


            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
            f1 = 1.0F - this.swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float) Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
            modelrenderer.rotateAngleX = (float) ((double) modelrenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
            modelrenderer.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
            modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
        }

        if (entity.isMaidWait()) {
            //待機状態の特別表示
            float lx = mh_sin(ageInTicks * 0.067F) * 0.05F - 0.7F;
            bipedRightArm.setRotateAngle(lx, 0.0F, -0.25F);
            bipedLeftArm.setRotateAngle(lx, 0.0F, 0.25F);
        } else {
            float la, lb, lc;

            if (entity.isShooting()) {
                if (entity.isCharging()) {
                    this.bipedRightArm.rotateAngleY = -0.8F;
                    this.bipedRightArm.rotateAngleX = -0.97079635F;
                    this.bipedLeftArm.rotateAngleX = -0.97079635F;
                    float f2 = MathHelper.clamp(entity.getItemInUseMaxCount(), 0.0F, 25.0F);
                    this.bipedLeftArm.rotateAngleY = MathHelper.lerp(f2 / 25.0F, 0.4F, 0.85F);
                    this.bipedLeftArm.rotateAngleX = MathHelper.lerp(f2 / 25.0F, this.bipedLeftArm.rotateAngleX, (-(float) Math.PI / 2F));
                } else {
                    // 弓構え
                    float lonGround = getMainHand(entity).ordinal();
                    float f6 = mh_sin(lonGround * 3.141593F);
                    float f7 = mh_sin((1.0F - (1.0F - lonGround) * (1.0F - lonGround)) * 3.141593F);
                    la = 0.1F - f6 * 0.6F;
                    bipedRightArm.setRotateAngle(-1.470796F, -la, 0.0F);
                    bipedLeftArm.setRotateAngle(-1.470796F, la, 0.0F);
                    la = bipedHead.rotateAngleX;
                    lb = mh_sin(ageInTicks * 0.067F) * 0.05F;
                    lc = f6 * 1.2F - f7 * 0.4F;
                    bipedRightArm.addRotateAngleX(la + lb - lc);
                    bipedLeftArm.addRotateAngleX(la - lb - lc);
                    la = bipedHead.rotateAngleY;
                    bipedRightArm.addRotateAngleY(la);
                    bipedLeftArm.addRotateAngleY(la);
                    la = mh_cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                    bipedRightArm.addRotateAngleZ(la);
                    bipedLeftArm.addRotateAngleZ(-la);
                }
            } else if (entity.isHolding(Items.CROSSBOW)) {
                this.bipedRightArm.rotateAngleY = -0.3F + this.bipedHead.rotateAngleY;
                this.bipedLeftArm.rotateAngleY = 0.6F + this.bipedHead.rotateAngleY;
                this.bipedRightArm.rotateAngleX = (-(float) Math.PI / 2F) + this.bipedHead.rotateAngleX + 0.1F;
                this.bipedLeftArm.rotateAngleX = -1.5F + this.bipedHead.rotateAngleX;
            } else {
                // 通常
                la = mh_sin(ageInTicks * 0.067F) * 0.05F;
                lc = 0.25F + mh_cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                bipedRightArm.addRotateAngleX(la);
                bipedLeftArm.addRotateAngleX(-la);
                bipedRightArm.addRotateAngleZ(lc);
                bipedLeftArm.addRotateAngleZ(-lc);
            }
        }

        float f2 = -(float) Math.PI / 1.5F;

        if (entity.isRotationAttack()) {
            this.bipedRightArm.setRotateAngle(f2, 0.0F, -f2);
            this.bipedLeftArm.setRotateAngle(f2, 0.0F, f2);
        }

        if (entity.isGuard()) {
            this.bipedRightArm.setRotateAngle(-0.95F, -0.77F, 0.0F);
        }

        if (entity instanceof IMaidAnimation) {
            setAnimations(limbSwing, limbSwingAmount, ageInTicks, pHeadYaw, pHeadPitch, entity, ((IMaidAnimation) entity));
        }
    }

    public void setAnimations(float par1, float par2, float ageInTicks, float pHeadYaw, float pHeadPitch, T pEntityCaps, IMaidAnimation animation) {


        animator.update(animation);
        if (animation.getAnimation() == LittleMaidBaseEntity.TALK_ANIMATION) {
            bipedRightArm.setRotateAngle(0.0F, 0.0F, 0.0F);
            bipedLeftArm.setRotateAngle(0.0F, 0.0F, 0.0F);

            animator.setAnimation(LittleMaidBaseEntity.TALK_ANIMATION);
            animator.startKeyframe(5);

            this.bipedRightArm.setRotateAngleZ(0.0F);
            this.bipedLeftArm.setRotateAngleZ(0.0F);

            animator.rotate(this.bipedRightArm, -1.2F, 0, 0);
            animator.rotate(this.bipedLeftArm, -1.2F, 0, 0);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.bipedRightArm, 0.2F, 0, 0);
            animator.rotate(this.bipedLeftArm, 0.2F, 0, 0);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.bipedRightArm, -0.2F, 0, 0);
            animator.rotate(this.bipedLeftArm, -0.2F, 0, 0);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.bipedRightArm, 0.2F, 0, 0);
            animator.rotate(this.bipedLeftArm, 0.2F, 0, 0);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.bipedRightArm, -0.2F, 0, 0);
            animator.rotate(this.bipedLeftArm, -0.2F, 0, 0);
            animator.endKeyframe();
            animator.setStaticKeyframe(45);
            animator.resetKeyframe(10);
        }

        if (animation.getAnimation() == LittleMaidBaseEntity.PET_ANIMATION) {
            animator.setAnimation(LittleMaidBaseEntity.PET_ANIMATION);
            animator.startKeyframe(10);
            animator.rotate(this.bipedHead, 0.4F, -0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.bipedHead, 0.4F, 0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.bipedHead, 0.4F, -0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.bipedHead, 0.4F, 0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.bipedHead, 0.4F, -0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.bipedHead, 0.4F, 0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.bipedHead, 0.4F, -0.2F, 0.0F);
            animator.endKeyframe();

            animator.startKeyframe(10);
            animator.rotate(this.bipedHead, 0.4F, 0.2F, 0.0F);
            animator.endKeyframe();

            animator.setStaticKeyframe(10);
            animator.resetKeyframe(10);
        }

        if (animation.getAnimation() == LittleMaidBaseEntity.FARM_ANIMATION) {
            animator.setAnimation(LittleMaidBaseEntity.FARM_ANIMATION);
            animator.startKeyframe(10);
            animator.rotate(this.bipedBody, 0.4F, 0.0F, 0.0F);
            animator.rotate(this.bipedRightArm, -0.4F, 0.0F, 0.0F);
            animator.rotate(this.bipedLeftArm, -0.4F, 0.0F, 0.0F);
            animator.endKeyframe();


            animator.resetKeyframe(5);
        }

        if (animation.getAnimation() == LittleMaidBaseEntity.EAT_ANIMATION) {
            bipedRightArm.setRotateAngle(0.0F, 0.0F, 0.0F);
            bipedLeftArm.setRotateAngle(0.0F, 0.0F, 0.0F);

            animator.setAnimation(LittleMaidBaseEntity.EAT_ANIMATION);
            animator.startKeyframe(2);
            animator.rotate(this.bipedRightArm, -1.6F, -0.6F, 0.0F);
            animator.rotate(this.bipedLeftArm, -1.6F, 0.6F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(2);
            animator.rotate(this.bipedRightArm, -1.8F, -0.6F, 0.0F);
            animator.rotate(this.bipedLeftArm, -1.8F, 0.6F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(2);
            animator.rotate(this.bipedRightArm, -1.6F, -0.6F, 0.0F);
            animator.rotate(this.bipedLeftArm, -1.6F, 0.6F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(2);
            animator.rotate(this.bipedRightArm, -1.8F, -0.6F, 0.0F);
            animator.rotate(this.bipedLeftArm, -1.8F, 0.6F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(2);
            animator.rotate(this.bipedRightArm, -1.6F, -0.6F, 0.0F);
            animator.rotate(this.bipedLeftArm, -1.6F, 0.6F, 0.0F);
            animator.endKeyframe();


            animator.resetKeyframe(4);
        }

        if (animation.getAnimation() == LittleMaidBaseEntity.RUSHING_ANIMATION) {
            bipedRightArm.setRotateAngle(0.0F, 0.0F, 0.0F);
            bipedLeftArm.setRotateAngle(0.0F, 0.0F, 0.0F);
            bipedRightLeg.setRotateAngle(0.0F, 0.0F, 0.0F);
            bipedLeftLeg.setRotateAngle(0.0F, 0.0F, 0.0F);

            animator.setAnimation(LittleMaidBaseEntity.RUSHING_ANIMATION);
            animator.startKeyframe(4);
            animator.rotate(this.bipedRightArm, -0.95F, -0.77F, 0.0F);
            animator.rotate(this.bipedLeftArm, 1.0471975511965976F, 0.6F, -0.27314402793711257F);
            animator.rotate(this.bipedRightLeg, 0.5F, 0.0F, 0.0F);
            animator.rotate(this.bipedLeftLeg, 0.5F, 0.0F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(72);
            animator.rotate(this.bipedRightArm, -0.95F, -0.77F, 0.0F);
            animator.rotate(this.bipedLeftArm, 1.0471975511965976F, 0.6F, -0.27314402793711257F);
            animator.rotate(this.bipedRightLeg, 0.5F, 0.0F, 0.0F);
            animator.rotate(this.bipedLeftLeg, 0.5F, 0.0F, 0.0F);
            animator.endKeyframe();

            animator.resetKeyframe(4);
        }
    }

    public void setDefaultPause(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                                float pHeadYaw, float pHeadPitch) {
        this.bipedHead.rotateAngleY = pHeadYaw * ((float) Math.PI / 180F);
        this.bipedHead.rotateAngleX = pHeadPitch * ((float) Math.PI / 180F);

        this.bipedBody.rotateAngleY = 0.0F;

        this.bipedRightArm.rotateAngleY = 0.0F;
        this.bipedLeftArm.rotateAngleY = 0.0F;
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;

        this.bipedBody.rotateAngleY = 0.0F;

        this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.bipedRightLeg.rotateAngleY = 0.0F;
        this.bipedLeftLeg.rotateAngleY = 0.0F;

        if (entity.isMaidWait()) {
            bipedRightArm.rotateAngleZ = -0.2F;
            bipedLeftArm.rotateAngleZ = 0.2F;
            bipedRightArm.rotateAngleX = -0.5F;
            bipedLeftArm.rotateAngleX = -0.5F;
        } else {
            this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / 1.0F;
            this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / 1.0F;
        }

        this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotationAngle(MaidModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    protected MaidModelRenderer getArmForSide(HandSide side) {
        return side == HandSide.LEFT ? this.bipedLeftArm : this.bipedRightArm;
    }

    protected HandSide getMainHand(T entityIn) {
        HandSide handside = entityIn.getPrimaryHand();
        return entityIn.swingingHand == Hand.MAIN_HAND ? handside : handside.opposite();
    }
}