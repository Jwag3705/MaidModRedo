package mmr.maidmodredo.client.maidmodel.monstermaid;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mmr.maidmodredo.api.IMaidAnimation;
import mmr.maidmodredo.client.maidmodel.MaidModelRenderer;
import mmr.maidmodredo.client.maidmodel.ModelMultiMMMBase;
import mmr.maidmodredo.entity.LittleMaidBaseEntity;
import mmr.maidmodredo.entity.monstermaid.EnderMaidEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class ModelLittleMaid_Ender<T extends EnderMaidEntity> extends ModelMultiMMMBase<T> {
    public MaidModelRenderer root;
    public MaidModelRenderer head;
    public MaidModelRenderer jaw;
    public MaidModelRenderer MaidCap;
    public MaidModelRenderer Hair;
    public MaidModelRenderer BunL;
    public MaidModelRenderer BunR;
    public MaidModelRenderer HairbandB;
    public MaidModelRenderer BunB;
    public MaidModelRenderer body;
    public MaidModelRenderer Breasts;
    public MaidModelRenderer RBreast;
    public MaidModelRenderer bone2;
    public MaidModelRenderer LBreast;
    public MaidModelRenderer legL;
    public MaidModelRenderer legL2;
    public MaidModelRenderer legR;
    public MaidModelRenderer legR2;
    public MaidModelRenderer handL;
    public MaidModelRenderer handL2;
    public MaidModelRenderer handR;
    public MaidModelRenderer handR2;

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

        root = new MaidModelRenderer(this);
        root.setRotationPoint(0.0F, 0.0F, 0.0F);

        head = new MaidModelRenderer(this);
        head.setRotationPoint(0.0F, -3.75F, 0.0F);
        root.addChild(head);
        head.setTextureOffset(64, 43).addBox(-5.0F, -10.75F, -5.0F, 10, 10, 0, 0.0F, true);
        head.setTextureOffset(34, 44).addBox(-5.0F, -10.75F, -5.0F, 10, 10, 10, 0.0F, true);

        jaw = new MaidModelRenderer(this);
        jaw.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(jaw);
        jaw.setTextureOffset(75, 44).addBox(-5.0F, -10.75F, -4.75F, 10, 10, 10, 0.0F, true);

        MaidCap = new MaidModelRenderer(this);
        MaidCap.setRotationPoint(0.0F, 28.0F, 0.0F);
        head.addChild(MaidCap);
        MaidCap.setTextureOffset(35, 10).addBox(-3.5F, -41.75F, -4.0F, 7, 3, 2, 0.0F, false);

        Hair = new MaidModelRenderer(this);
        Hair.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(Hair);
        Hair.setTextureOffset(83, 11).addBox(-5.0F, -10.75F, -5.5F, 10, 14, 11, 0.0F, true);
        Hair.setTextureOffset(106, 1).addBox(-5.75F, -10.0F, -2.5F, 1, 5, 5, 0.0F, false);
        Hair.setTextureOffset(83, 10).addBox(-7.5F, -10.0F, -1.5F, 2, 9, 3, 0.0F, false);
        Hair.setTextureOffset(106, 1).addBox(4.75F, -10.0F, -2.5F, 1, 5, 5, 0.0F, true);
        Hair.setTextureOffset(83, 10).addBox(5.5F, -10.0F, -1.5F, 2, 9, 3, 0.0F, true);
        Hair.setTextureOffset(33, 42).addBox(-1.5F, -10.0F, 6.0F, 3, 9, 2, 0.0F, false);

        BunL = new MaidModelRenderer(this);
        BunL.setRotationPoint(0.0F, 28.0F, 0.0F);
        setRotationAngle(BunL, 0.0F, 0.0F, -0.0873F);
        Hair.addChild(BunL);
        BunL.setTextureOffset(106, 45).addBox(7.8154F, -36.7471F, -2.0F, 4, 4, 4, 0.0F, false);

        BunR = new MaidModelRenderer(this);
        BunR.setRotationPoint(0.0F, 28.0F, 0.0F);
        setRotationAngle(BunR, 0.0F, 0.0F, 0.0873F);
        Hair.addChild(BunR);
        BunR.setTextureOffset(106, 45).addBox(-11.8154F, -36.7471F, -2.0F, 4, 4, 4, 0.0F, true);

        HairbandB = new MaidModelRenderer(this);
        HairbandB.setRotationPoint(0.0F, 28.0F, 0.0F);
        setRotationAngle(HairbandB, 0.0F, -1.5708F, 0.0F);
        Hair.addChild(HairbandB);
        HairbandB.setTextureOffset(106, 1).addBox(5.0F, -38.0F, -2.5F, 1, 5, 5, 0.0F, true);

        BunB = new MaidModelRenderer(this);
        BunB.setRotationPoint(0.0F, 28.0F, 0.0F);
        setRotationAngle(BunB, 0.0873F, 0.0F, 0.0F);
        Hair.addChild(BunB);
        BunB.setTextureOffset(106, 45).addBox(-2.0F, -36.9971F, 8.0654F, 4, 4, 4, 0.0F, true);

        body = new MaidModelRenderer(this);
        body.setRotationPoint(0.0F, -4.0F, 0.0F);
        root.addChild(body);
        body.setTextureOffset(32, 16).addBox(-3.5F, 0.0F, -2.0F, 7, 6, 4, 0.0F, true);
        body.setTextureOffset(34, 35).addBox(-2.5F, 5.0F, -1.5F, 5, 3, 3, 0.0F, true);
        body.setTextureOffset(52, 35).addBox(-3.5F, 7.75F, -2.0F, 7, 4, 4, 0.0F, true);
        body.setTextureOffset(106, 60).addBox(-1.0F, -0.75F, -1.25F, 2, 2, 2, 0.0F, false);

        Breasts = new MaidModelRenderer(this);
        Breasts.setRotationPoint(0.0F, 28.0F, 0.0F);
        body.addChild(Breasts);

        RBreast = new MaidModelRenderer(this);
        RBreast.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(RBreast, 0.0F, 0.1745F, 0.0F);
        Breasts.addChild(RBreast);

        bone2 = new MaidModelRenderer(this);
        bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(bone2, -0.6109F, 0.0F, 0.0F);
        RBreast.addChild(bone2);
        bone2.setTextureOffset(18, 0).addBox(-3.0F, -21.25F, -17.5F, 3, 3, 3, 0.0F, false);

        LBreast = new MaidModelRenderer(this);
        LBreast.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(LBreast, -0.6109F, -0.1745F, 0.0F);
        Breasts.addChild(LBreast);
        LBreast.setTextureOffset(34, 0).addBox(0.0F, -21.25F, -17.5F, 3, 3, 3, 0.0F, false);

        legL = new MaidModelRenderer(this);
        legL.setRotationPoint(-2.0F, 7.0F, 0.0F);
        root.addChild(legL);
        legL.setTextureOffset(56, 0).addBox(-1.5F, 0.0F, -1.0F, 3, 9, 2, 0.0F, false);
        legL.setTextureOffset(0, 7).addBox(-2.0F, -3.25F, -3.0F, 4, 9, 6, 0.0F, false);

        legL2 = new MaidModelRenderer(this);
        legL2.setRotationPoint(0.0F, 0.0F, 0.0F);
        legL.addChild(legL2);
        legL2.setTextureOffset(72, 11).addBox(-1.0F, 9.0F, -1.0F, 2, 8, 2, 0.0F, false);

        legR = new MaidModelRenderer(this);
        legR.setRotationPoint(2.0F, 7.0F, 0.0F);
        root.addChild(legR);
        legR.setTextureOffset(56, 0).addBox(-1.5F, 0.0F, -1.0F, 3, 9, 2, 0.0F, true);
        legR.setTextureOffset(0, 22).addBox(-2.0F, -3.25F, -3.0F, 4, 9, 6, 0.0F, false);

        legR2 = new MaidModelRenderer(this);
        legR2.setRotationPoint(0.0F, 0.0F, 0.0F);
        legR.addChild(legR2);
        legR2.setTextureOffset(72, 20).addBox(-1.0F, 9.0F, -1.0F, 2, 8, 2, 0.0F, true);

        handL = new MaidModelRenderer(this);
        handL.setRotationPoint(-4.5F, -1.6F, 0.0F);
        setRotationAngle(handL, 0.0F, 0.0F, 0.2491F);
        root.addChild(handL);
        handL.setTextureOffset(56, 0).addBox(-1.0F, -2.0F, -1.0F, 2, 9, 2, 0.0F, false);
        handL.setTextureOffset(92, 4).addBox(-1.75F, -2.15F, -1.5F, 3, 3, 3, 0.0F, false);

        handL2 = new MaidModelRenderer(this);
        handL2.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(handL2, 0.0F, 0.0F, 0.0F);
        handL.addChild(handL2);
        handL2.setTextureOffset(64, 11).addBox(-1.0F, 6.75F, -1.0F, 2, 9, 2, 0.0F, false);

        handR = new MaidModelRenderer(this);
        handR.setRotationPoint(4.5F, -1.6F, 0.0F);
        setRotationAngle(handR, 0.0F, 0.0F, -0.2491F);
        root.addChild(handR);
        handR.setTextureOffset(56, 0).addBox(-1.0F, -2.0F, -1.0F, 2, 9, 2, 0.0F, true);
        handR.setTextureOffset(81, 4).addBox(-1.25F, -2.15F, -1.5F, 3, 3, 3, 0.0F, false);

        handR2 = new MaidModelRenderer(this);
        handR2.setRotationPoint(0.0F, 0.0F, 0.0F);
        setRotationAngle(handR2, 0.0F, 0.0F, 0.0F);
        handR.addChild(handR2);
        handR2.setTextureOffset(64, 20).addBox(-1.0F, 6.5F, -1.0F, 2, 9, 2, 0.0F, true);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.root).forEach((p_228292_8_) -> {
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
    public void renderItems(MatrixStack stack, boolean left) {
        stack.translate(0.0F, 0.4F, 0.0F);
        if (left) {
            this.handL.setAnglesAndRotation(stack);
        } else {
            this.handR.setAnglesAndRotation(stack);
        }
    }

    @Override
    public float[] getArmorModelsSize() {
        return new float[]{0.55F, 1.95F};
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
            handR.setRotateAngle(lx, 0.0F, 0.45F);
            handL.setRotateAngle(lx, 0.0F, -0.45F);
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
                handR.setRotateAngleX(la);
                handL.setRotateAngleX(-la);
                handR.setRotateAngleZ(-lc);
                handL.setRotateAngleZ(lc);
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

        if (animation.getAnimation() == EnderMaidEntity.HOLD_ANIMATION) {
            handR.setRotateAngle(0.0F, 0.0F, 0.0F);
            handL.setRotateAngle(0.0F, 0.0F, 0.0F);

            animator.setAnimation(EnderMaidEntity.HOLD_ANIMATION);
            animator.startKeyframe(5);

            animator.rotate(this.handR, -1.2F, 0, 0);
            animator.rotate(this.handL, -1.2F, 0, 0);
            animator.endKeyframe();

            animator.startKeyframe(40);
            animator.rotate(this.handR, -1.2F, 0, 0);
            animator.rotate(this.handL, -1.2F, 0, 0);
            animator.endKeyframe();
            animator.resetKeyframe(5);
        }

        if (animation.getAnimation() == LittleMaidBaseEntity.RUSHING_ANIMATION) {
            handR.setRotateAngle(0.0F, 0.0F, 0.0F);
            handR2.setRotateAngle(0.0F, 0.0F, 0.0F);
            handL.setRotateAngle(0.0F, 0.0F, 0.0F);
            legR.setRotateAngle(0.0F, 0.0F, 0.0F);
            legL.setRotateAngle(0.0F, 0.0F, 0.0F);

            animator.setAnimation(LittleMaidBaseEntity.RUSHING_ANIMATION);
            animator.startKeyframe(4);
            animator.rotate(this.handR, -0.95F, -0.77F, 0.0F);
            animator.rotate(this.handL, 1.0471975511965976F, 0.6F, -0.27314402793711257F);
            animator.rotate(this.handR2, 0.0F, 0.0F, -0.35F);
            animator.rotate(this.legR, 0.5F, 0.0F, 0.0F);
            animator.rotate(this.legL, 0.5F, 0.0F, 0.0F);
            animator.endKeyframe();
            animator.startKeyframe(72);
            animator.rotate(this.handR, -0.95F, -0.77F, 0.0F);
            animator.rotate(this.handL, 1.0471975511965976F, 0.6F, -0.27314402793711257F);
            animator.rotate(this.handR2, 0.0F, 0.0F, -0.35F);
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
            handR.rotateAngleZ = -0.3F;
            handL.rotateAngleZ = 0.3F;
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
