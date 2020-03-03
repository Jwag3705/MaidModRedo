package mmr.maidmodredo.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import mmr.maidmodredo.client.maidmodel.MaidModelRenderer;
import mmr.maidmodredo.entity.PlanterEntity;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class PlanterModel<T extends PlanterEntity> extends SegmentedModel<T> implements IHasArm, IHasHead {
    public MaidModelRenderer Planter;
    public MaidModelRenderer head;
    public MaidModelRenderer headFace;
    public MaidModelRenderer Hair;
    public MaidModelRenderer hairheadLayer;
    public MaidModelRenderer hairheadLayer2;
    public MaidModelRenderer Flower;
    public MaidModelRenderer Bud;
    public MaidModelRenderer Petal1;
    public MaidModelRenderer Petal2;
    public MaidModelRenderer Petal3;
    public MaidModelRenderer Petal4;
    public MaidModelRenderer Flower2;
    public MaidModelRenderer bone9;
    public MaidModelRenderer bone10;
    public MaidModelRenderer bone11;
    public MaidModelRenderer bone12;
    public MaidModelRenderer body;
    public MaidModelRenderer BackPetal;
    public MaidModelRenderer FrontPetal;
    public MaidModelRenderer legL;
    public MaidModelRenderer bone2;
    public MaidModelRenderer legL2;
    public MaidModelRenderer legR;
    public MaidModelRenderer bone;
    public MaidModelRenderer legR2;
    public MaidModelRenderer handL;
    public MaidModelRenderer handL2;
    public MaidModelRenderer handR;
    public MaidModelRenderer handR2;

    public PlanterModel() {
        textureWidth = 64;
        textureHeight = 64;

        Planter = new MaidModelRenderer(this);
        Planter.setRotationPoint(0.0F, 24.0F, 0.0F);

        head = new MaidModelRenderer(this);
        head.setRotationPoint(0.0F, -17.1F, 0.0F);
        Planter.addChild(head);
        head.addBox(head, 0, 0, -4.3F, -7.12F, -4.7F, 9, 9, 9, 0.0F, false);

        headFace = new MaidModelRenderer(this);
        headFace.setRotationPoint(0.0F, 0.96F, 0.0F);
        head.addChild(headFace);
        headFace.addBox(headFace, 27, 18, -4.3F, -8.08F, -4.72F, 9, 9, 0, 0.0F, false);

        Hair = new MaidModelRenderer(this);
        Hair.setRotationPoint(0.25F, 17.88F, 0.0F);
        head.addChild(Hair);

        hairheadLayer = new MaidModelRenderer(this);
        hairheadLayer.setRotationPoint(0.0F, -16.92F, 0.0F);
        Hair.addChild(hairheadLayer);
        hairheadLayer.addBox(hairheadLayer, 0, 18, -4.488F, -8.1168F, -4.512F, 9, 9, 9, 0.5F, false);

        hairheadLayer2 = new MaidModelRenderer(this);
        hairheadLayer2.setRotationPoint(0.0F, -16.92F, 0.0F);
        setRotationAngle(hairheadLayer2, 0.1745F, 0.0F, 0.0F);
        Hair.addChild(hairheadLayer2);
        hairheadLayer2.addBox(hairheadLayer2, 0, 37, -4.488F, 2.0832F, 5.188F, 9, 10, -1, 0.5F, false);

        Flower = new MaidModelRenderer(this);
        Flower.setRotationPoint(5.95F, 7.08F, 13.9F);
        setRotationAngle(Flower, 0.8727F, 0.3491F, 0.0F);
        head.addChild(Flower);

        Bud = new MaidModelRenderer(this);
        Bud.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(Bud, 0.0F, -0.7854F, 0.0F);
        Flower.addChild(Bud);
        Bud.addBox(Bud, 52, 60, -5.1F, -26.0F, -1.6F, 3, 1, 3, 0.0F, false);

        Petal1 = new MaidModelRenderer(this);
        Petal1.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(Petal1, 0.0F, 0.0F, 0.0873F);
        Flower.addChild(Petal1);
        Petal1.addBox(Petal1, 46, 0, -9.1944F, -24.736F, -4.0766F, 3, 0, 3, 0.0F, false);

        Petal2 = new MaidModelRenderer(this);
        Petal2.setRotationPoint(-0.5F, 0.0F, 0.0F);
        setRotationAngle(Petal2, 0.0F, 0.0F, -0.0873F);
        Flower.addChild(Petal2);
        Petal2.addBox(Petal2, 39, 42, 1.6944F, -25.236F, -4.0766F, 3, 0, 3, 0.0F, false);

        Petal3 = new MaidModelRenderer(this);
        Petal3.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(Petal3, 0.0873F, 0.0F, 0.0F);
        Flower.addChild(Petal3);
        Petal3.addBox(Petal3, 45, 42, -4.0F, -25.1426F, 0.9181F, 3, 0, 3, 0.0F, false);

        Petal4 = new MaidModelRenderer(this);
        Petal4.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(Petal4, -0.0873F, 0.0F, 0.0F);
        Flower.addChild(Petal4);
        Petal4.addBox(Petal4, 44, 3, -4.0F, -24.7293F, -9.2707F, 3, 0, 3, 0.0F, false);

        Flower2 = new MaidModelRenderer(this);
        Flower2.setRotationPoint(-2.7F, -0.2F, 0.1F);
        setRotationAngle(Flower2, 0.0F, -0.7854F, 0.0F);
        Flower.addChild(Flower2);

        bone9 = new MaidModelRenderer(this);
        bone9.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone9, 0.0F, 0.0F, 0.0873F);
        Flower2.addChild(bone9);
        bone9.addBox(bone9, 40, 0, -8.3606F, -24.8734F, -3.2929F, 3, 0, 3, 0.0F, false);

        bone10 = new MaidModelRenderer(this);
        bone10.setRotationPoint(-0.5F, 0.0F, 0.0F);
        setRotationAngle(bone10, 0.0F, 0.0F, -0.0873F);
        Flower2.addChild(bone10);
        bone10.addBox(bone10, 0, 6, 2.5394F, -25.2266F, -3.2929F, 3, 0, 3, 0.0F, false);

        bone11 = new MaidModelRenderer(this);
        bone11.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone11, 0.0873F, 0.0F, 0.0F);
        Flower2.addChild(bone11);
        bone11.addBox(bone11, 0, 3, -3.1574F, -25.1384F, 1.7044F, 3, 0, 3, 0.0F, false);

        bone12 = new MaidModelRenderer(this);
        bone12.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone12, -0.0873F, 0.0F, 0.0F);
        Flower2.addChild(bone12);
        bone12.addBox(bone12, 0, 0, -3.1574F, -24.8616F, -8.4956F, 3, 0, 3, 0.0F, false);

        body = new MaidModelRenderer(this);
        body.setRotationPoint(0.0F, -16.0F, 0.0F);
        Planter.addChild(body);
        body.addBox(body, 36, 14, -1.35F, 0.25F, -1.3F, 3, 1, 3, 0.0F, false);
        body.addBox(body, 27, 0, -2.85F, 1.25F, -1.8F, 6, 4, 4, 0.0F, false);
        body.addBox(body, 46, 46, -2.35F, 5.0F, -1.55F, 5, 2, 3, 0.0F, false);
        body.addBox(body, 36, 8, -2.85F, 5.95F, -1.8F, 6, 2, 4, 0.0F, false);

        BackPetal = new MaidModelRenderer(this);
        BackPetal.setRotationPoint(0.0F, 16.0F, 0.0F);
        setRotationAngle(BackPetal, 0.0F, 1.5708F, 0.0F);
        body.addChild(BackPetal);
        BackPetal.addBox(BackPetal, 0, 14, -2.4F, -14.75F, -1.8F, 0, 6, 4, 0.0F, false);

        FrontPetal = new MaidModelRenderer(this);
        FrontPetal.setRotationPoint(0.0F, 16.0F, 0.0F);
        setRotationAngle(FrontPetal, 0.0F, 1.5708F, 0.0F);
        body.addChild(FrontPetal);
        FrontPetal.addBox(FrontPetal, 0, 14, 1.9F, -14.85F, -1.8F, 0, 6, 4, 0.0F, false);

        legL = new MaidModelRenderer(this);
        legL.setRotationPoint(1.0F, -9.0F, 0.0F);
        Planter.addChild(legL);
        legL.addBox(legL, 0, 46, -0.85F, 0.85F, -1.8F, 3, 4, 4, 0.0F, false);

        bone2 = new MaidModelRenderer(this);
        bone2.setRotationPoint(-1.0F, 9.0F, 0.0F);
        setRotationAngle(bone2, 0.0F, 0.0F, -0.2618F);
        legL.addChild(bone2);
        bone2.addBox(bone2, 0, 14, 5.7F, -8.95F, -1.8F, 0, 8, 4, 0.0F, false);

        legL2 = new MaidModelRenderer(this);
        legL2.setRotationPoint(0.0F, 0.0F, 0.0F);
        legL.addChild(legL2);
        legL2.addBox(legL2, 18, 38, -0.85F, 4.1F, -1.8F, 3, 5, 4, 0.0F, false);

        legR = new MaidModelRenderer(this);
        legR.setRotationPoint(-1.0F, -9.0F, 0.0F);
        Planter.addChild(legR);
        legR.addBox(legR, 45, 14, -1.8F, 0.85F, -1.8F, 3, 4, 4, 0.0F, false);

        bone = new MaidModelRenderer(this);
        bone.setRotationPoint(1.0F, 9.0F, 0.0F);
        setRotationAngle(bone, 0.0F, 0.0F, 0.2618F);
        legR.addChild(bone);
        bone.addBox(bone, 14, 43, -5.4F, -9.05F, -1.8F, 0, 8, 4, 0.0F, false);

        legR2 = new MaidModelRenderer(this);
        legR2.setRotationPoint(0.0F, 0.0F, 0.0F);
        legR.addChild(legR2);
        legR2.addBox(legR2, 32, 42, -1.8F, 4.1F, -1.8F, 3, 5, 4, 0.0F, false);

        handL = new MaidModelRenderer(this);
        handL.setRotationPoint(3.35F, -13.85F, 0.0F);
        setRotationAngle(handL, 0.0F, 0.0F, -0.2618F);
        Planter.addChild(handL);
        handL.addBox(handL, 28, 51, -1.0244F, -0.4966F, -0.9F, 2, 4, 2, 0.0F, false);

        handL2 = new MaidModelRenderer(this);
        handL2.setRotationPoint(0.0F, 3.0F, 0.0F);
        handL.addChild(handL2);
        handL2.addBox(handL2, 50, 28, -1.0244F, 0.1034F, -0.9F, 2, 4, 2, 0.0F, false);

        handR = new MaidModelRenderer(this);
        handR.setRotationPoint(-2.9F, -13.75F, 0.0F);
        setRotationAngle(handR, 0.0F, 0.0F, 0.2618F);
        Planter.addChild(handR);
        handR.addBox(handR, 36, 51, -1.2926F, -0.626F, -0.9F, 2, 4, 2, 0.0F, false);

        handR2 = new MaidModelRenderer(this);
        handR2.setRotationPoint(0.3F, 3.45F, 0.0F);
        handR.addChild(handR2);
        handR2.addBox(handR2, 22, 47, -1.5926F, -0.476F, -0.9F, 2, 4, 2, 0.0F, false);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.Planter);
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

        this.handR.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handL.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handR.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.handL.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

    }

    public void setRotationAngle(MaidModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
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