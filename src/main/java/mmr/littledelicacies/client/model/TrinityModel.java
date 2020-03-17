package mmr.littledelicacies.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import mmr.littledelicacies.api.IMaidAnimation;
import mmr.littledelicacies.client.maidmodel.MaidModelRenderer;
import mmr.littledelicacies.client.maidmodel.animator.MaidModelAnimator;
import mmr.littledelicacies.entity.boss.TrinityEntity;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class TrinityModel<T extends TrinityEntity> extends SegmentedModel<T> implements IHasArm, IHasHead {
    public MaidModelRenderer head;
    public MaidModelRenderer headFace;
    public MaidModelRenderer Hair;
    public MaidModelRenderer HairExtentions;
    public MaidModelRenderer HairExtentions2;
    public MaidModelRenderer hairheadLayer;
    public MaidModelRenderer body;
    public MaidModelRenderer boob;
    public MaidModelRenderer boob2;
    public MaidModelRenderer handR;
    public MaidModelRenderer handRLayer;
    public MaidModelRenderer handR2;
    public MaidModelRenderer handRLayer2;
    public MaidModelRenderer handL;
    public MaidModelRenderer handLLayer;
    public MaidModelRenderer handL2;
    public MaidModelRenderer handLLayer2;
    public MaidModelRenderer legR;
    public MaidModelRenderer legRLayer;
    public MaidModelRenderer legR2;
    public MaidModelRenderer legRLayer2;
    public MaidModelRenderer ArmorSkirtR;
    public MaidModelRenderer legL;
    public MaidModelRenderer legLLayer;
    public MaidModelRenderer legL2;
    public MaidModelRenderer legLLayer2;
    public MaidModelRenderer ArmorSkirtL;

    public MaidModelAnimator animator = MaidModelAnimator.create();

    public TrinityModel() {
        textureWidth = 128;
        textureHeight = 128;

        head = new MaidModelRenderer(this);
        head.setRotationPoint(0.0F, 0.75F, 0.0F);
        head.addBox(head, 0, 19, -4.5F, -11.5F, -4.5F, 9, 9, 9, 0.0F, false);

        headFace = new MaidModelRenderer(this);
        headFace.setRotationPoint(0.0F, 2.025F, 0.0F);
        head.addChild(headFace);
        headFace.addBox(headFace, 20, 43, -4.5F, -13.525F, -4.525F, 9, 9, 0, 0.0F, false);

        Hair = new MaidModelRenderer(this);
        Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(Hair);

        HairExtentions = new MaidModelRenderer(this);
        HairExtentions.setRotationPoint(0.0F, 16.2F, 0.0F);
        setRotationAngle(HairExtentions, -0.4363F, -0.0873F, 0.0F);
        Hair.addChild(HairExtentions);
        HairExtentions.addBox(HairExtentions, 27, 25, 1.5F, -26.825F, -15.1F, 3, 3, 0, 0.0F, false);

        HairExtentions2 = new MaidModelRenderer(this);
        HairExtentions2.setRotationPoint(0.0F, 16.2F, 0.0F);
        setRotationAngle(HairExtentions2, -0.4363F, 0.0873F, 0.0F);
        Hair.addChild(HairExtentions2);
        HairExtentions2.addBox(HairExtentions2, 27, 25, -4.5F, -26.825F, -15.1F, 3, 3, 0, 0.0F, true);

        hairheadLayer = new MaidModelRenderer(this);
        hairheadLayer.setRotationPoint(0.0F, 2.025F, 0.0F);
        Hair.addChild(hairheadLayer);
        hairheadLayer.addBox(hairheadLayer, 0, 0, -4.5F, -13.525F, -4.5F, 9, 10, 9, 0.5F, false);

        body = new MaidModelRenderer(this);
        body.setRotationPoint(0.0F, 5.5F, 0.0F);
        body.addBox(body, 36, 8, -1.25F, -8.1F, -0.75F, 2, 2, 1, 0.0F, false);
        body.addBox(body, 0, 37, -3.0F, -6.6F, -2.0F, 6, 5, 4, 0.0F, false);
        body.addBox(body, 49, 52, -2.25F, -1.6F, -1.5F, 4, 2, 3, 0.0F, false);
        body.addBox(body, 27, 0, -3.25F, 0.15F, -2.5F, 6, 3, 5, 0.0F, false);
        body.addBox(body, 36, 25, -1.75F, -8.1F, -1.35F, 3, 2, 2, 0.0F, false);

        boob = new MaidModelRenderer(this);
        boob.setRotationPoint(-1.5F, -0.5F, -1.5F);
        setRotationAngle(boob, -0.6981F, 0.1745F, -0.0873F);
        body.addChild(boob);
        boob.addBox(boob, 50, 16, -1.2515F, -4.2678F, -4.1017F, 3, 3, 4, 0.0F, false);

        boob2 = new MaidModelRenderer(this);
        boob2.setRotationPoint(1.5F, -0.5F, -1.5F);
        setRotationAngle(boob2, -0.6981F, -0.1745F, 0.0873F);
        body.addChild(boob2);
        boob2.addBox(boob2, 48, 39, -1.7485F, -4.2678F, -4.1017F, 3, 3, 4, 0.0F, false);

        handR = new MaidModelRenderer(this);
        handR.setRotationPoint(-4.75F, 1.0F, 0.0F);
        setRotationAngle(handR, 0.0F, 0.0F, 0.2618F);
        handR.addBox(handR, 46, 57, -1.1259F, -1.7018F, -1.0F, 2, 6, 2, 0.0F, false);

        handRLayer = new MaidModelRenderer(this);
        handRLayer.setRotationPoint(1.0F, 8.0F, 0.0F);
        handR.addChild(handRLayer);
        handRLayer.addBox(handRLayer, 4, 58, -2.1259F, -9.4518F, -1.0F, 2, 5, 2, 0.5F, false);

        handR2 = new MaidModelRenderer(this);
        handR2.setRotationPoint(1.0F, 8.0F, 0.0F);
        handR.addChild(handR2);
        handR2.addBox(handR2, 0, 19, -2.1259F, -3.9518F, -1.0F, 2, 6, 2, 0.0F, false);

        handRLayer2 = new MaidModelRenderer(this);
        handRLayer2.setRotationPoint(0.0F, 0.0F, 0.0F);
        handR2.addChild(handRLayer2);
        handRLayer2.addBox(handRLayer2, 0, 0, -2.1259F, -4.2018F, -1.0F, 2, 6, 2, 0.5F, false);

        handL = new MaidModelRenderer(this);
        handL.setRotationPoint(4.75F, 1.0F, 0.0F);
        setRotationAngle(handL, 0.0F, 0.0F, -0.2618F);
        handL.addBox(handL, 55, 0, -0.8741F, -1.7018F, -1.0F, 2, 6, 2, 0.0F, false);

        handLLayer = new MaidModelRenderer(this);
        handLLayer.setRotationPoint(-1.0F, 8.0F, 0.0F);
        handL.addChild(handLLayer);
        handLLayer.addBox(handLLayer, 58, 58, 0.1259F, -9.4518F, -1.0F, 2, 5, 2, 0.5F, false);

        handL2 = new MaidModelRenderer(this);
        handL2.setRotationPoint(-1.0F, 8.0F, 0.0F);
        handL.addChild(handL2);
        handL2.addBox(handL2, 38, 55, 0.1259F, -3.9518F, -1.0F, 2, 6, 2, 0.0F, false);

        handLLayer2 = new MaidModelRenderer(this);
        handLLayer2.setRotationPoint(0.0F, 0.0F, 0.0F);
        handL2.addChild(handLLayer2);
        handLLayer2.addBox(handLLayer2, 30, 52, 0.1259F, -4.2018F, -1.0F, 2, 6, 2, 0.5F, false);

        legR = new MaidModelRenderer(this);
        legR.setRotationPoint(-1.0F, 10.0F, 0.0F);
        legR.addBox(legR, 45, 4, -2.25F, -2.0F, -2.0F, 3, 8, 4, 0.0F, false);

        legRLayer = new MaidModelRenderer(this);
        legRLayer.setRotationPoint(0.0F, 5.0F, 0.0F);
        legR.addChild(legRLayer);
        legRLayer.addBox(legRLayer, 48, 70, -2.0F, -5.0F, -2.0F, 3, 6, 4, 0.5F, false);

        legR2 = new MaidModelRenderer(this);
        legR2.setRotationPoint(0.0F, 5.0F, 0.0F);
        legR.addChild(legR2);
        legR2.addBox(legR2, 44, 25, -2.0F, 0.75F, -2.0F, 3, 8, 4, 0.0F, false);

        legRLayer2 = new MaidModelRenderer(this);
        legRLayer2.setRotationPoint(0.0F, 0.0F, 0.0F);
        legR2.addChild(legRLayer2);
        legRLayer2.addBox(legRLayer2, 0, 70, -2.0F, 1.5F, -2.0F, 3, 7, 4, 0.5F, false);

        legL = new MaidModelRenderer(this);
        legL.setRotationPoint(1.0F, 10.0F, 0.0F);
        legL.addBox(legL, 0, 46, -1.25F, -2.0F, -2.0F, 3, 8, 4, 0.0F, false);

        legLLayer = new MaidModelRenderer(this);
        legLLayer.setRotationPoint(0.0F, 5.0F, 0.0F);
        legL.addChild(legLLayer);
        legLLayer.addBox(legLLayer, 48, 86, -1.0F, -5.0F, -2.0F, 3, 6, 4, 0.5F, false);

        legL2 = new MaidModelRenderer(this);
        legL2.setRotationPoint(0.0F, 0.0F, 0.0F);
        legLLayer.addChild(legL2);
        legL2.addBox(legL2, 38, 43, -1.0F, 0.75F, -2.0F, 3, 8, 4, 0.0F, false);

        legLLayer2 = new MaidModelRenderer(this);
        legLLayer2.setRotationPoint(0.0F, 0.0F, 0.0F);
        legL2.addChild(legLLayer2);
        legLLayer2.addBox(legLLayer2, 63, 11, -1.0F, 1.5F, -2.0F, 3, 7, 4, 0.5F, false);

        ArmorSkirtL = new MaidModelRenderer(this);
        ArmorSkirtL.setRotationPoint(0.0F, 21.5F, 0.0F);
        ArmorSkirtL.addBox(ArmorSkirtL, 30, 31, -0.7F, -15.85F, -3.0F, 4, 6, 6, 0.0F, false);
        ArmorSkirtL.addBox(ArmorSkirtL, 0, 58, -0.7F, -15.85F, -3.1F, 2, 11, 0, 0.0F, false);
        ArmorSkirtL.addBox(ArmorSkirtL, 22, 52, -0.7F, -15.85F, 3.0F, 4, 12, 0, 0.0F, false);

        ArmorSkirtR = new MaidModelRenderer(this);
        ArmorSkirtR.setRotationPoint(0.0F, 21.5F, 0.0F);
        ArmorSkirtR.addBox(ArmorSkirtR, 30, 13, -3.8F, -15.85F, -3.0F, 4, 6, 6, 0.0F, false);
        ArmorSkirtR.addBox(ArmorSkirtR, 54, 57, -1.8F, -15.85F, -3.1F, 2, 11, 0, 0.0F, false);
        ArmorSkirtR.addBox(ArmorSkirtR, 14, 52, -3.8F, -15.85F, 3.0F, 4, 12, 0, 0.0F, false);
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleY = (float) Math.toRadians(netHeadYaw);
        this.head.rotateAngleX = (float) Math.toRadians(headPitch);
        this.legR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.legL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.legR.rotateAngleY = 0.0F;
        this.legL.rotateAngleY = 0.0F;

        this.handR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.2F;
        this.handR.rotateAngleY = 0.0F;
        this.handR.rotateAngleZ = 0.2618F;
        this.handL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
        this.handL.rotateAngleY = 0.0F;
        this.handL.rotateAngleZ = -0.2618F;


        if (this.swingProgress > 0.0F) {
            HandSide handside = this.getMainHand(entityIn);
            ModelRenderer modelrenderer = this.getArm(handside);
            float f1 = this.swingProgress;
            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;
            if (handside == HandSide.LEFT) {
                this.body.rotateAngleY *= -1.0F;
            }


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

        this.handR.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handL.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handR.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.handL.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        if (entityIn instanceof IMaidAnimation) {
            setAnimations(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, entityIn, ((IMaidAnimation) entityIn));
        }
    }

    public void setAnimations(float par1, float par2, float ageInTicks, float pHeadYaw, float pHeadPitch, T pEntityCaps, IMaidAnimation animation) {


        animator.update(animation);
        if (animation.getAnimation() == TrinityEntity.TALK_ANIMATION) {
            handR.setRotateAngle(0.0F, 0.0F, 0.0F);
            handL.setRotateAngle(0.0F, 0.0F, 0.0F);

        }


        if (animation.getAnimation() == TrinityEntity.RUSHING_ANIMATION) {
            handR.setRotateAngle(0.0F, 0.0F, 0.0F);
            handR2.setRotateAngle(0.0F, 0.0F, 0.0F);
            handL.setRotateAngle(0.0F, 0.0F, 0.0F);
            legR.setRotateAngle(0.0F, 0.0F, 0.0F);
            legL.setRotateAngle(0.0F, 0.0F, 0.0F);

            animator.setAnimation(TrinityEntity.RUSHING_ANIMATION);
            animator.startKeyframe(4);
            animator.rotate(this.handR, 1.0016444577195458F, -0.4553564018453205F, 0.0F);
            animator.rotate(this.handL, 1.0016444577195458F, 0.4553564018453205F, 0.0F);
            animator.rotate(this.handR2, 0.0F, 0.0F, -0.35F);
            animator.rotate(this.legR, 0.5F, 0.0F, 0.0F);
            animator.rotate(this.legL, 0.5F, 0.0F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(72);
            animator.rotate(this.handR, 1.0016444577195458F, -0.4553564018453205F, 0.0F);
            animator.rotate(this.handL, 1.0016444577195458F, 0.4553564018453205F, 0.0F);
            animator.rotate(this.handR2, 0.0F, 0.0F, -0.35F);
            animator.rotate(this.legR, 0.5F, 0.0F, 0.0F);
            animator.rotate(this.legL, 0.5F, 0.0F, 0.0F);
            animator.endKeyframe();

            animator.resetKeyframe(4);
        }

        if (animation.getAnimation() == TrinityEntity.JUMP_ANIMATION) {
            handR.setRotateAngle(0.0F, 0.0F, 0.0F);
            handR2.setRotateAngle(0.0F, 0.0F, 0.0F);
            handL.setRotateAngle(0.0F, 0.0F, 0.0F);
            legR.setRotateAngle(0.0F, 0.0F, 0.0F);
            legL.setRotateAngle(0.0F, 0.0F, 0.0F);
            legR2.setRotateAngle(0.0F, 0.0F, 0.0F);
            legL2.setRotateAngle(0.0F, 0.0F, 0.0F);

            animator.setAnimation(TrinityEntity.JUMP_ANIMATION);
            animator.startKeyframe(4);
            animator.rotate(this.handR, 0.0F, 0.0F, 0.4618F);
            animator.rotate(this.handL, 0.0F, 0.0F, -0.4618F);
            animator.rotate(this.legR, 0.5F, 0.0F, 0.0F);
            animator.rotate(this.legL, -0.5F, 0.0F, 0.0F);
            animator.rotate(this.legR2, 0.15F, 0.0F, 0.0F);
            animator.rotate(this.legL2, -0.25F, 0.0F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(12);
            animator.rotate(this.handR, 0.0F, 0.0F, 0.4618F);
            animator.rotate(this.handL, 0.0F, 0.0F, -0.4618F);
            animator.rotate(this.legR, 0.5F, 0.0F, 0.0F);
            animator.rotate(this.legL, 0.5F, 0.0F, 0.0F);
            animator.rotate(this.legR2, 0.15F, 0.0F, 0.0F);
            animator.rotate(this.legL2, -0.25F, 0.0F, 0.0F);
            animator.endKeyframe();

            animator.resetKeyframe(4);
        }
    }

    @Override
    public void setLivingAnimations(T entity, float par2, float par3, float pRenderPartialTicks) {
        super.setLivingAnimations(entity, par2, par3, pRenderPartialTicks);

        float f3 = entity.ticksExisted + pRenderPartialTicks + entity.getEntityId() * 7;

        // 目パチ
        if (entity.isSleeping()) {
            headFace.setVisible(false);
        } else if (0 > mh_sin(f3 * 0.05F) + mh_sin(f3 * 0.13F) + mh_sin(f3 * 0.7F) + 2.55F) {
            headFace.setVisible(false);
        } else {
            headFace.setVisible(true);
        }
    }

    public static final float mh_sin(float f) {
        f = f % 6.283185307179586F;
        f = (f < 0F) ? 360 + f : f;
        return MathHelper.sin(f);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(head, body, handR, handL, legR, legL, ArmorSkirtR, ArmorSkirtL);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    protected HandSide getMainHand(T entityIn) {
        HandSide handside = entityIn.getPrimaryHand();
        return entityIn.swingingHand == Hand.MAIN_HAND ? handside : handside.opposite();
    }

    private ModelRenderer getArm(HandSide p_191216_1_) {
        return p_191216_1_ == HandSide.LEFT ? this.handL : this.handR;
    }

    @Override
    public void translateHand(HandSide sideIn, MatrixStack matrixStackIn) {
        this.getArm(sideIn).setAnglesAndRotation(matrixStackIn);
    }

    @Override
    public ModelRenderer getModelHead() {
        return this.head;
    }
}