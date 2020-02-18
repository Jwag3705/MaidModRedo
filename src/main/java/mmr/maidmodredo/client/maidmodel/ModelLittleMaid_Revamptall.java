package mmr.maidmodredo.client.maidmodel;//Made with Blockbench
//Paste this code into your mod.

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mmr.maidmodredo.api.IMaidAnimation;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class ModelLittleMaid_Revamptall<T extends LittleMaidBaseEntity> extends ModelMultiMMMBase<T> {
    public MaidModelRenderer handR;
    public MaidModelRenderer handRLayer;
    public MaidModelRenderer handR2;
    public MaidModelRenderer handRLayer2;
    public MaidModelRenderer handL;
    public MaidModelRenderer handLLayer;
    public MaidModelRenderer handL2;
    public MaidModelRenderer handLLayer2;
    public MaidModelRenderer body;
    public MaidModelRenderer bodyLayer;
    public MaidModelRenderer boob;
    public MaidModelRenderer boob2;
    public MaidModelRenderer head;
    public MaidModelRenderer hair;
    public MaidModelRenderer lefttail;
    public MaidModelRenderer hairheadLayer;
    public MaidModelRenderer headFace;
    public MaidModelRenderer ahoge;
    public MaidModelRenderer leftchignon;
    public MaidModelRenderer backchignon;
    public MaidModelRenderer hairtail;
    public MaidModelRenderer righttail;
    public MaidModelRenderer rightchignon;
    public MaidModelRenderer skirt;
    public MaidModelRenderer Apron;
    public MaidModelRenderer legR;
    public MaidModelRenderer legRLayer;
    public MaidModelRenderer legR2;
    public MaidModelRenderer legRLayer2;
    public MaidModelRenderer legL;
    public MaidModelRenderer legLLayer;
    public MaidModelRenderer legL2;
    public MaidModelRenderer legLLayer2;

    public ModelLittleMaid_Revamptall() {
        this(0.0F);
    }

    public ModelLittleMaid_Revamptall(float psize) {
        this(psize, 0.0F, 128, 128);
    }

    public ModelLittleMaid_Revamptall(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
        super(psize, 0.0F, 128, 128);
    }

    @Override
    public void initModel(float psize, float pyoffset) {
        textureWidth = 128;
        textureHeight = 128;

        handR = new MaidModelRenderer(this, 72, 31);
        handR.setRotationPoint(-3.0F, 9.0F, 0.0F);
        handR.addBox(-2.0F, -3.5F, -1.0F, 2, 5, 2, 0.0F);

        handR = new MaidModelRenderer(this);
        handR.setRotationPoint(-3.0F, 9.0F, 0.0F);
        handR.setTextureOffset(64, 31).addBox(-2.0F, -5.5F, -1.0F, 2, 6, 2, 0.0F, false);

        handRLayer = new MaidModelRenderer(this);
        handRLayer.setRotationPoint(0.0F, 0.0F, 0.0F);
        handR.addChild(handRLayer);
        handRLayer.setTextureOffset(80, 32).addBox(-2.0F, -5.25F, -1.0F, 2, 5, 2, 0.5F, false);

        handR2 = new MaidModelRenderer(this);
        handR2.setRotationPoint(0.0F, 0.0F, 0.0F);
        handR.addChild(handR2);
        handR2.setTextureOffset(64, 40).addBox(-2.0F, -0.5F, -1.0F, 2, 6, 2, 0.0F, false);

        handRLayer2 = new MaidModelRenderer(this);
        handRLayer2.setRotationPoint(0.0F, 0.0F, 0.0F);
        handR2.addChild(handRLayer2);
        handRLayer2.setTextureOffset(80, 40).addBox(-2.0F, -0.75F, -1.0F, 2, 6, 2, 0.5F, false);

        handL = new MaidModelRenderer(this);
        handL.setRotationPoint(3.0F, 9.0F, 0.0F);
        handL.setTextureOffset(72, 31).addBox(0.0F, -5.5F, -1.0F, 2, 6, 2, 0.0F, false);

        handLLayer = new MaidModelRenderer(this);
        handLLayer.setRotationPoint(0.0F, 0.0F, 0.0F);
        handL.addChild(handLLayer);
        handLLayer.setTextureOffset(89, 32).addBox(0.0F, -5.25F, -1.0F, 2, 5, 2, 0.5F, false);

        handL2 = new MaidModelRenderer(this);
        handL2.setRotationPoint(0.0F, 0.0F, 0.0F);
        handL.addChild(handL2);
        handL2.setTextureOffset(72, 40).addBox(0.0F, -0.5F, -1.0F, 2, 6, 2, 0.0F, false);

        handLLayer2 = new MaidModelRenderer(this);
        handLLayer2.setRotationPoint(0.0F, 0.0F, 0.0F);
        handL2.addChild(handLLayer2);
        handLLayer2.setTextureOffset(88, 40).addBox(0.0F, -0.75F, -1.0F, 2, 6, 2, 0.5F, false);

        body = new MaidModelRenderer(this);
        body.setRotationPoint(0.0F, 8.0F, 0.0F);
        body.setTextureOffset(14, 21).addBox(-3.0F, -5.0F, -2.0F, 6, 9, 4, 0.0F, false);

        bodyLayer = new MaidModelRenderer(this);
        bodyLayer.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(bodyLayer);
        bodyLayer.setTextureOffset(94, 56).addBox(-3.0F, -5.25F, -2.0F, 6, 9, 4, 0.5F, false);

        boob = new MaidModelRenderer(this);
        boob.setRotationPoint(-1.5F, -0.5F, -1.5F);
        setRotationAngle(boob, -0.6981F, 0.0F, -0.1745F);
        body.addChild(boob);
        boob.setTextureOffset(0, 23).addBox(-1.2389F, -2.2813F, -3.1957F, 3, 3, 3, 0.0F, false);

        boob2 = new MaidModelRenderer(this);
        boob2.setRotationPoint(1.5F, -0.5F, -1.5F);
        setRotationAngle(boob2, -0.6981F, 0.0F, 0.1745F);
        body.addChild(boob2);
        boob2.setTextureOffset(0, 23).addBox(-1.7611F, -2.2813F, -3.1957F, 3, 3, 3, 0.0F, true);

        head = new MaidModelRenderer(this);
        head.setRotationPoint(0.0F, 4.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-5.0F, -11.0F, -5.0F, 10, 10, 10, 0.0F, false);
        head.setTextureOffset(0, 47).addBox(-5.0F, -14.25F, -5.0F, 10, 3, 1, 0.0F, false);

        hair = new MaidModelRenderer(this);
        hair.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(hair);
        hair.setTextureOffset(0, 63).addBox(-5.0F, -1.0F, 3.25F, 10, 10, 2, 0.0F, false);

        lefttail = new MaidModelRenderer(this);
        lefttail.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(lefttail);
        lefttail.setTextureOffset(14, 102).addBox(5.5F, -9.5F, 0.5F, 1, 8, 2, 0.0F, false);

        hairheadLayer = new MaidModelRenderer(this);
        hairheadLayer.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(hairheadLayer);
        hairheadLayer.setTextureOffset(40, 0).addBox(-5.0F, -11.0F, -5.0F, 10, 10, 10, 0.5F, false);

        headFace = new MaidModelRenderer(this);
        headFace.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(headFace);
        headFace.setTextureOffset(80, 0).addBox(-5.0F, -11.0F, -5.25F, 10, 10, 0, 0.0F, false);

        ahoge = new MaidModelRenderer(this);
        ahoge.setRotationPoint(0.0F, -8.0F, 0.0F);
        setRotationAngle(ahoge, 0.0F, 2.3562F, 0.0F);
        head.addChild(ahoge);
        ahoge.setTextureOffset(0, 89).addBox(-0.2197F, -8.0F, 0.0303F, 5, 5, 5, 0.0F, false);

        leftchignon = new MaidModelRenderer(this);
        leftchignon.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(leftchignon);
        leftchignon.setTextureOffset(8, 83).addBox(5.0F, -10.0F, 0.0F, 1, 3, 3, 0.0F, false);

        backchignon = new MaidModelRenderer(this);
        backchignon.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(backchignon);
        backchignon.setTextureOffset(0, 76).addBox(-2.0F, -10.0F, 5.0F, 4, 4, 2, 0.0F, false);

        hairtail = new MaidModelRenderer(this);
        hairtail.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(hairtail);
        hairtail.setTextureOffset(0, 100).addBox(-1.5F, -9.5F, 5.0F, 3, 9, 3, 0.0F, false);

        righttail = new MaidModelRenderer(this);
        righttail.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(righttail);
        righttail.setTextureOffset(14, 102).addBox(-6.5F, -9.0F, 0.5F, 1, 8, 2, 0.0F, false);

        rightchignon = new MaidModelRenderer(this);
        rightchignon.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(rightchignon);
        rightchignon.setTextureOffset(0, 83).addBox(-6.0F, -9.5F, 0.0F, 1, 3, 3, 0.0F, false);

        skirt = new MaidModelRenderer(this);
        skirt.setRotationPoint(0.0F, 15.0F, 0.0F);
        skirt.setTextureOffset(64, 70).addBox(0.0F, -5.25F, -3.0F, 4, 5, 6, 0.0F, true);
        skirt.setTextureOffset(64, 70).addBox(-4.0F, -5.25F, -3.0F, 4, 5, 6, 0.0F, false);
        skirt.setTextureOffset(69, 84).addBox(0.0F, -1.0F, -4.0F, 5, 7, 8, 0.0F, false);
        skirt.setTextureOffset(69, 84).addBox(-5.0F, -1.0F, -4.0F, 5, 7, 8, 0.0F, true);

        Apron = new MaidModelRenderer(this);
        Apron.setRotationPoint(0.0F, 9.0F, 0.0F);
        skirt.addChild(Apron);
        Apron.setTextureOffset(95, 72).addBox(-3.5F, -14.25F, -4.25F, 7, 10, 2, 0.0F, false);
        Apron.setTextureOffset(0, 0).addBox(0.0F, -25.5F, 0.0F, 1, 1, 0, 0.0F, false);

        legR = new MaidModelRenderer(this);
        legR.setRotationPoint(-1.0F, 15.0F, 0.0F);
        legR.setTextureOffset(32, 38).addBox(-2.0F, -3.5F, -2.0F, 3, 5, 4, 0.0F, false);

        legRLayer = new MaidModelRenderer(this);
        legRLayer.setRotationPoint(0.0F, 0.0F, 0.0F);
        legR.addChild(legRLayer);
        legRLayer.setTextureOffset(48, 70).addBox(-2.0F, -3.5F, -2.0F, 3, 5, 4, 0.5F, false);

        legR2 = new MaidModelRenderer(this);
        legR2.setRotationPoint(0.0F, 0.0F, 0.0F);
        legR.addChild(legR2);
        legR2.setTextureOffset(48, 52).addBox(-2.0F, 1.0F, -2.0F, 3, 8, 4, 0.0F, false);

        legRLayer2 = new MaidModelRenderer(this);
        legRLayer2.setRotationPoint(0.0F, 0.0F, 0.0F);
        legR2.addChild(legRLayer2);
        legRLayer2.setTextureOffset(32, 69).addBox(-2.0F, 2.0F, -2.0F, 3, 7, 4, 0.5F, false);

        legL = new MaidModelRenderer(this);
        legL.setRotationPoint(1.0F, 15.0F, 0.0F);
        legL.setTextureOffset(48, 38).addBox(-1.0F, -3.5F, -2.0F, 3, 5, 4, 0.0F, false);

        legLLayer = new MaidModelRenderer(this);
        legLLayer.setRotationPoint(0.0F, 0.0F, 0.0F);
        legL.addChild(legLLayer);
        legLLayer.setTextureOffset(48, 86).addBox(-1.0F, -3.5F, -2.0F, 3, 5, 4, 0.5F, false);

        legL2 = new MaidModelRenderer(this);
        legL2.setRotationPoint(0.0F, 0.0F, 0.0F);
        legLLayer.addChild(legL2);
        legL2.setTextureOffset(32, 52).addBox(-1.0F, 1.0F, -2.0F, 3, 8, 4, 0.0F, false);

        legLLayer2 = new MaidModelRenderer(this);
        legLLayer2.setRotationPoint(0.0F, 0.0F, 0.0F);
        legL2.addChild(legLLayer2);
        legLLayer2.setTextureOffset(32, 85).addBox(-1.0F, 2.0F, -2.0F, 3, 7, 4, 0.5F, false);
    }

    @Override
    public float getHeight() {
        return 1.35F;
    }

    @Override
    public float getWidth() {
        return 0.5F;
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
            this.handL.setAnglesAndRotation(stack);
        } else {
            this.handR.setAnglesAndRotation(stack);
        }
    }

    @Override
    public float[] getArmorModelsSize() {
        return new float[]{0.1F, 0.5F};
    }


    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.handR, this.handL, this.legR, this.legL, this.head, this.skirt, this.body).forEach((p_228292_8_) -> {
            p_228292_8_.render(matrixStack, iVertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void setLivingAnimations(T entity, float par2, float par3, float pRenderPartialTicks) {
        super.setLivingAnimations(entity, par2, par3, pRenderPartialTicks);

        float f3 = entity.ticksExisted + pRenderPartialTicks + entity.entityIdFactor;

        // 目パチ
        if (entity.isSleeping()) {
            headFace.setVisible(true);
        } else if (0 > mh_sin(f3 * 0.05F) + mh_sin(f3 * 0.13F) + mh_sin(f3 * 0.7F) + 2.55F) {
            headFace.setVisible(true);
        } else {
            headFace.setVisible(false);
        }
    }

    @Override
    public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                       float pHeadYaw, float pHeadPitch) {
        setDefaultPause(entity, limbSwing, limbSwingAmount, ageInTicks, pHeadYaw, pHeadPitch);

        if (entity.isPassenger() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit())) {
            // 乗り物に乗っている
            handR.addRotateAngleX(-0.6283185F);
            handL.addRotateAngleX(-0.6283185F);
            legR.setRotateAngleX(-1.256637F);
            legL.setRotateAngleX(-1.256637F);
            legR.setRotateAngleY(0.3141593F);
            legL.setRotateAngleY(-0.3141593F);
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
            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;
            if (handside == HandSide.LEFT) {
                this.body.rotateAngleY *= -1.0F;
            }

            this.handR.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.handR.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.handL.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.handL.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.handR.rotateAngleY += this.body.rotateAngleY;
            this.handL.rotateAngleY += this.body.rotateAngleY;
            this.handL.rotateAngleX += this.body.rotateAngleY;
            f1 = 1.0F - this.swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float) Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
            modelrenderer.rotateAngleX = (float) ((double) modelrenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
            modelrenderer.rotateAngleY += this.body.rotateAngleY * 2.0F;
            modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
        }

        if (entity.isMaidWait()) {
            //待機状態の特別表示
            float lx = mh_sin(ageInTicks * 0.067F) * 0.05F - 0.7F;
            handR.setRotateAngle(lx, 0.0F, -0.25F);
            handL.setRotateAngle(lx, 0.0F, 0.25F);
        } else {
            float la, lb, lc;

            if (entity.isShooting()) {
                if (entity.isCharging()) {
                    this.handR.rotateAngleY = -0.8F;
                    this.handR.rotateAngleX = -0.97079635F;
                    this.handL.rotateAngleX = -0.97079635F;
                    float f2 = MathHelper.clamp(entity.getItemInUseMaxCount(), 0.0F, 25.0F);
                    this.handL.rotateAngleY = MathHelper.lerp(f2 / 25.0F, 0.4F, 0.85F);
                    this.handL.rotateAngleX = MathHelper.lerp(f2 / 25.0F, this.handL.rotateAngleX, (-(float) Math.PI / 2F));
                } else {
                    // 弓構え
                    float lonGround = getMainHand(entity).ordinal();
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
                lc = 0.25F + mh_cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                handR.addRotateAngleX(la);
                handL.addRotateAngleX(-la);
                handR.addRotateAngleZ(lc);
                handL.addRotateAngleZ(-lc);
            }
        }

        float f2 = -(float) Math.PI / 1.5F;

        if (entity.isRotationAttack()) {
            this.handR.setRotateAngle(f2, 0.0F, -f2);
            this.handL.setRotateAngle(f2, 0.0F, f2);
        }

        if (entity.isGuard()) {
            this.handR.setRotateAngle(-0.95F, -0.77F, 0.0F);
        }

        if (entity instanceof IMaidAnimation) {
            setAnimations(limbSwing, limbSwingAmount, ageInTicks, pHeadYaw, pHeadPitch, entity, ((IMaidAnimation) entity));
        }
    }

    public void setAnimations(float par1, float par2, float ageInTicks, float pHeadYaw, float pHeadPitch, T pEntityCaps, IMaidAnimation animation) {


        animator.update(animation);
        if (animation.getAnimation() == LittleMaidBaseEntity.TALK_ANIMATION) {
            handR.setRotateAngle(0.0F, 0.0F, 0.0F);
            handL.setRotateAngle(0.0F, 0.0F, 0.0F);

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

        if (animation.getAnimation() == LittleMaidBaseEntity.RUSHING_ANIMATION) {
            handR.setRotateAngle(0.0F, 0.0F, 0.0F);
            handL.setRotateAngle(0.0F, 0.0F, 0.0F);
            legR.setRotateAngle(0.0F, 0.0F, 0.0F);
            legL.setRotateAngle(0.0F, 0.0F, 0.0F);

            animator.setAnimation(LittleMaidBaseEntity.RUSHING_ANIMATION);
            animator.startKeyframe(4);
            animator.rotate(this.handR, -0.95F, -0.77F, 0.0F);
            animator.rotate(this.handL, 1.0471975511965976F, 0.6F, -0.27314402793711257F);
            animator.rotate(this.legR, 0.5F, 0.0F, 0.0F);
            animator.rotate(this.legL, 0.5F, 0.0F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(72);
            animator.rotate(this.handR, -0.95F, -0.77F, 0.0F);
            animator.rotate(this.handL, 1.0471975511965976F, 0.6F, -0.27314402793711257F);
            animator.rotate(this.legR, 0.5F, 0.0F, 0.0F);
            animator.rotate(this.legL, 0.5F, 0.0F, 0.0F);
            animator.endKeyframe();

            animator.resetKeyframe(4);
        }
    }

    public void setDefaultPause(T entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                                float pHeadYaw, float pHeadPitch) {
        this.head.rotateAngleY = pHeadYaw * ((float) Math.PI / 180F);
        this.head.rotateAngleX = pHeadPitch * ((float) Math.PI / 180F);

        this.body.rotateAngleY = 0.0F;

        this.handR.rotateAngleY = 0.0F;
        this.handL.rotateAngleY = 0.0F;
        this.handR.rotateAngleZ = 0.0F;
        this.handL.rotateAngleZ = 0.0F;

        this.body.rotateAngleY = 0.0F;

        this.legR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.legL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.legR.rotateAngleY = 0.0F;
        this.legL.rotateAngleY = 0.0F;

        if (entity.isMaidWait()) {
            handR.rotateAngleZ = -0.2F;
            handL.rotateAngleZ = 0.2F;
            handR.rotateAngleX = -0.5F;
            handL.rotateAngleX = -0.5F;
        } else {
            this.handR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / 1.0F;
            this.handL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / 1.0F;
        }

        this.handR.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handL.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handR.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.handL.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
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
        return side == HandSide.LEFT ? this.handL : this.handR;
    }

    protected HandSide getMainHand(T entityIn) {
        HandSide handside = entityIn.getPrimaryHand();
        return entityIn.swingingHand == Hand.MAIN_HAND ? handside : handside.opposite();
    }
}