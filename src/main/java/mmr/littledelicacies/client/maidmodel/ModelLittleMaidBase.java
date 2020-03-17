package mmr.littledelicacies.client.maidmodel;

import com.mojang.blaze3d.matrix.MatrixStack;
import mmr.littledelicacies.api.IMaidAnimation;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

/**
 * LMM用に最適化
 */
public abstract class ModelLittleMaidBase<T extends LittleMaidBaseEntity> extends ModelMultiMMMBase<T> {

    //fields
    public MaidModelRenderer bipedTorso;
    public MaidModelRenderer bipedNeck;
    public MaidModelRenderer bipedHead;
    public MaidModelRenderer bipedRightArm;
    public MaidModelRenderer bipedLeftArm;
    public MaidModelRenderer bipedBody;
    public MaidModelRenderer bipedPelvic;
    public MaidModelRenderer bipedRightLeg;
    public MaidModelRenderer bipedLeftLeg;
    public MaidModelRenderer Skirt;

    /**
     * コンストラクタは全て継承させること
     */
    public ModelLittleMaidBase() {
        super();
    }

    /**
     * コンストラクタは全て継承させること
     */
    public ModelLittleMaidBase(float psize) {
        super(psize);
    }

    /**
     * コンストラクタは全て継承させること
     */
    public ModelLittleMaidBase(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
        super(psize, pyoffset, pTextureWidth, pTextureHeight);
    }


    @Override
    public void initModel(float psize, float pyoffset) {
        // 標準型
        // 手持ち
        Arms[0] = new MaidModelRenderer(this);
        Arms[0].setRotationPoint(-1F, 5F, -1F);
        Arms[1] = new MaidModelRenderer(this);
        Arms[1].setRotationPoint(1F, 5F, -1F);
        //Arms[1].isInvertX = true;

        HeadMount.setRotationPoint(0F, -4F, 0F);
        HeadTop.setRotationPoint(0F, -13F, 0F);


        bipedHead = new MaidModelRenderer(this, 0, 0);
        bipedHead.setTextureOffset(0, 0).addBox(-4F, -8F, -4F, 8, 8, 8, psize);        // Head
        bipedHead.setTextureOffset(24, 0).addBox(-4F, 0F, 1F, 8, 4, 3, psize);            // Hire
        bipedHead.setTextureOffset(24, 18).addBox(-4.995F, -7F, 0.2F, 1, 3, 3, psize);        // ChignonR
        bipedHead.setTextureOffset(24, 18).addBox(3.995F, -7F, 0.2F, 1, 3, 3, psize);        // ChignonL
        bipedHead.setTextureOffset(52, 10).addBox(-2F, -7.2F, 4F, 4, 4, 2, psize);        // ChignonB
        bipedHead.setTextureOffset(46, 20).addBox(-1.5F, -6.8F, 4F, 3, 9, 3, psize);    // Tail
        bipedHead.setTextureOffset(58, 21).addBox(-5.5F, -6.8F, 0.9F, 1, 8, 2, psize);    // SideTailR
        bipedHead.mirror = true;
        bipedHead.setTextureOffset(58, 21).addBox(4.5F, -6.8F, 0.9F, 1, 8, 2, psize);    // SideTailL
        bipedHead.setRotationPoint(0F, 0F, 0F);

        bipedRightArm = new MaidModelRenderer(this, 48, 0);
        bipedRightArm.addBox(-2.0F, -1F, -1F, 2, 8, 2, psize);
        bipedRightArm.setRotationPoint(-3.0F, 1.5F, 0F);

        bipedLeftArm = new MaidModelRenderer(this, 56, 0);
        bipedLeftArm.addBox(0.0F, -1F, -1F, 2, 8, 2, psize);
        bipedLeftArm.setRotationPoint(3.0F, 1.5F, 0F);

        bipedRightLeg = new MaidModelRenderer(this, 32, 19);
        bipedRightLeg.addBox(-2F, 0F, -2F, 3, 9, 4, psize);
        bipedRightLeg.setRotationPoint(-1F, 0F, 0F);

        bipedLeftLeg = new MaidModelRenderer(this, 32, 19);
        bipedLeftLeg.mirror = true;
        bipedLeftLeg.addBox(-1F, 0F, -2F, 3, 9, 4, psize);
        bipedLeftLeg.setRotationPoint(1F, 0F, 0F);

        Skirt = new MaidModelRenderer(this, 0, 16);
        Skirt.addBox(-4F, -2F, -4F, 8, 8, 8, psize);
        Skirt.setRotationPoint(0F, 0F, 0F);

        bipedBody = new MaidModelRenderer(this, 32, 8);
        bipedBody.addBox(-3F, 0F, -2F, 6, 7, 4, psize);
        bipedBody.setRotationPoint(0F, 0F, 0F);

        bipedTorso = new MaidModelRenderer(this);
        bipedNeck = new MaidModelRenderer(this);
        bipedPelvic = new MaidModelRenderer(this);
        bipedPelvic.setRotationPoint(0F, 7F, 0F);
        mainFrame = new MaidModelRenderer(this, 0, 0);
        mainFrame.setRotationPoint(0F, 0F + pyoffset + 8F, 0F);
        mainFrame.addChild(bipedTorso);
        bipedTorso.addChild(bipedNeck);
        bipedTorso.addChild(bipedBody);
        bipedTorso.addChild(bipedPelvic);
        bipedNeck.addChild(bipedHead);
        bipedNeck.addChild(bipedRightArm);
        bipedNeck.addChild(bipedLeftArm);
        bipedHead.addChild(HeadMount);
        bipedHead.addChild(HeadTop);
        bipedRightArm.addChild(Arms[0]);
        bipedLeftArm.addChild(Arms[1]);
        bipedPelvic.addChild(bipedRightLeg);
        bipedPelvic.addChild(bipedLeftLeg);
        bipedPelvic.addChild(Skirt);
    }

    @Override
    public float[] getArmorModelsSize() {
        return new float[]{0.1F, 0.5F};
    }

    @Override
    public float getHeight() {
        return 1.55F;
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


    /**
     * 姿勢制御用
     * 独自追加分
     */
    @Override
    public void render(T entity, float par1, float par2, float pTicksExisted,
                       float pHeadYaw, float pHeadPitch) {
        setDefaultPause(entity, par1, par2, pTicksExisted, pHeadYaw, pHeadPitch);

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

        //カスタム設定
        //お座りモーションの場合はモデル側で位置を調整する
       /* if (motionSitting && entity.isPassenger() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit()) {
            mainFrame.rotationPointY += 5.00F;
        }*/

        if (this.swingProgress > 0.0F) {
            HandSide handside = this.getMainHand(entity);
            ModelRenderer modelrenderer = this.getArmForSide(handside);
            float f1 = this.swingProgress;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;
            if (handside == HandSide.LEFT) {
                this.bipedBody.rotateAngleY *= -1.0F;
            }
            Skirt.rotateAngleY = bipedBody.rotateAngleY;

            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
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
      /*  if (isSneak) {
            // しゃがみ
            bipedTorso.rotateAngleX += 0.5F;
            bipedNeck.rotateAngleX -= 0.5F;
            bipedRightArm.rotateAngleX += 0.2F;
            bipedLeftArm.rotateAngleX += 0.2F;

            bipedPelvic.addRotationPointY(-0.5F);
            bipedPelvic.addRotationPointZ(-0.6F);
            bipedPelvic.addRotateAngleX(-0.5F);
            bipedHead.rotationPointY = 1.0F;
//			Skirt.setRotationPoint(0.0F, 5.8F, 2.7F);
            Skirt.rotationPointY -= 0.25F;
            Skirt.rotationPointZ += 0.00F;
            Skirt.addRotateAngleX(0.2F);
            bipedTorso.rotationPointY += 1.00F;
        } else {
            // 通常立ち
        }*/
        if (entity.isMaidWait()) {
            //待機状態の特別表示
            float lx = mh_sin(pTicksExisted * 0.067F) * 0.05F - 0.7F;
            bipedRightArm.setRotateAngle(lx, 0.0F, -0.4F);
            bipedLeftArm.setRotateAngle(lx, 0.0F, 0.4F);
        } else {
            float la, lb, lc;

            LittleMaidBaseEntity maidentity = (LittleMaidBaseEntity) entity;

            if (entity.isShooting()) {
                if (maidentity.isCharging()) {
                    this.bipedRightArm.rotateAngleY = -0.8F;
                    this.bipedRightArm.rotateAngleX = -0.97079635F;
                    this.bipedLeftArm.rotateAngleX = -0.97079635F;
                    float f2 = MathHelper.clamp(maidentity.getItemInUseMaxCount(), 0.0F, 25.0F);
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
                    lb = mh_sin(pTicksExisted * 0.067F) * 0.05F;
                    lc = f6 * 1.2F - f7 * 0.4F;
                    bipedRightArm.addRotateAngleX(la + lb - lc);
                    bipedLeftArm.addRotateAngleX(la - lb - lc);
                    la = bipedHead.rotateAngleY;
                    bipedRightArm.addRotateAngleY(la);
                    bipedLeftArm.addRotateAngleY(la);
                    la = mh_cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
                    bipedRightArm.addRotateAngleZ(la);
                    bipedLeftArm.addRotateAngleZ(-la);
                }
            } else if (maidentity.isHolding(Items.CROSSBOW)) {
                this.bipedRightArm.rotateAngleY = -0.3F + this.bipedHead.rotateAngleY;
                this.bipedLeftArm.rotateAngleY = 0.6F + this.bipedHead.rotateAngleY;
                this.bipedRightArm.rotateAngleX = (-(float) Math.PI / 2F) + this.bipedHead.rotateAngleX + 0.1F;
                this.bipedLeftArm.rotateAngleX = -1.5F + this.bipedHead.rotateAngleX;
            } else {
                // 通常
                la = mh_sin(pTicksExisted * 0.067F) * 0.05F;
                lc = 0.5F + mh_cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
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
            setAnimations(par1, par2, pTicksExisted, pHeadYaw, pHeadPitch, entity, ((IMaidAnimation) entity));
        }
    }

    public void setAnimations(float par1, float par2, float pTicksExisted, float pHeadYaw, float pHeadPitch, T entity, IMaidAnimation animation) {
        animator.update(animation);
        if (animation.getAnimation() == LittleMaidBaseEntity.TALK_ANIMATION) {

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

    public static float getPartialTicks() {
        return Minecraft.getInstance().isGamePaused() ? 0 : Minecraft.getInstance().getRenderPartialTicks();
    }

    @Override
    public void setDefaultPause() {
    }

    @Override
    public void setDefaultPause(T entity, float par1, float par2, float pTicksExisted,
                                float pHeadYaw, float pHeadPitch) {
        super.setDefaultPause(entity, par1, par2, pTicksExisted, pHeadYaw, pHeadPitch);
        mainFrame.setRotationPoint(0F, 8F, 0F);
        bipedTorso.setRotationPoint(0F, 0F, 0F);
        bipedTorso.setRotateAngle(0F, 0F, 0F);
        bipedNeck.setRotationPoint(0F, 0F, 0F);
        bipedNeck.setRotateAngle(0F, 0F, 0F);
        bipedPelvic.setRotationPoint(0F, 7F, 0F);
        bipedPelvic.setRotateAngle(0F, 0F, 0F);
        bipedHead.setRotationPoint(0F, 0F, 0F);
        bipedHead.setRotateAngleDegY(pHeadYaw);
        bipedHead.setRotateAngleDegX(pHeadPitch);
        bipedBody.setRotationPoint(0F, 0F, 0F);
        bipedBody.setRotateAngle(0F, 0F, 0F);
        bipedRightArm.setRotationPoint(-3.0F, 1.6F, 0F);
        bipedRightArm.setRotateAngle(mh_cos(par1 * 0.6662F + 3.141593F) * 2.0F * par2 * 0.5F, 0F, 0F);
        bipedLeftArm.setRotationPoint(3.0F, 1.6F, 0F);
        bipedLeftArm.setRotateAngle(mh_cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F, 0F, 0F);
        bipedRightLeg.setRotationPoint(-1F, 0F, 0F);
        bipedRightLeg.setRotateAngle(mh_cos(par1 * 0.6662F) * 1.4F * par2, 0F, 0F);
        bipedLeftLeg.setRotationPoint(1F, 0F, 0F);
        bipedLeftLeg.setRotateAngle(mh_cos(par1 * 0.6662F + 3.141593F) * 1.4F * par2, 0F, 0F);

        Skirt.setRotationPoint(0F, 0F, 0F);
        Skirt.setRotateAngle(0F, 0F, 0F);

    }

    @Override
    public void showAllParts() {
        //mainFrame.render(0.0625F);
        // 表示制限を解除してすべての部品を表示
        bipedHead.setVisible(true);
        bipedBody.setVisible(true);
        bipedRightArm.setVisible(true);
        bipedLeftArm.setVisible(true);
        Skirt.setVisible(true);
        bipedRightLeg.setVisible(true);
        bipedLeftLeg.setVisible(true);
    }

    @Override
    public int showArmorParts(int parts, int index) {
        //mainFrame.render(0.0625F);
        // 鎧の表示用
        boolean f;
        // 兜
        f = parts == 3 ? true : false;
        bipedHead.setVisible(f);
        // 鎧
        f = parts == 2 ? true : false;
        bipedBody.setVisible(f);
        bipedRightArm.setVisible(f);
        bipedLeftArm.setVisible(f);
        // 脚甲
        f = parts == 1 ? true : false;
        Skirt.setVisible(f);
        // 臑当
        f = parts == 0 ? true : false;
        bipedRightLeg.setVisible(f);
        bipedLeftLeg.setVisible(f);

        return -1;
    }

    @Override
    public void renderItems(MatrixStack stack, boolean left) {
        stack.translate(0.0F, 0.4F, 0.0F);
        if (left) {
            this.bipedLeftArm.setAnglesAndRotation(stack);
        } else {
            this.bipedRightArm.setAnglesAndRotation(stack);
        }
		/*// 手持ちの表示
		GL11.glPushMatrix();
		// R
		Arms[0].loadMatrix();
		GL11.glTranslatef(0F, 0.05F, -0.05F);
		Arms[0].renderItems(this, pEntityCaps, false, 0);
		// L
		Arms[1].loadMatrix();
		GL11.glTranslatef(0F, 0.05F, -0.05F);
		Arms[1].renderItems(this, pEntityCaps, false, 1);
		// 頭部装飾品
		boolean lplanter = ModelCapsHelper.getCapsValueBoolean(pEntityCaps, caps_isPlanter);
		if (ModelCapsHelper.getCapsValueBoolean(pEntityCaps, caps_isCamouflage) || lplanter) {
			HeadMount.loadMatrix();
			if (lplanter) {
				HeadTop.loadMatrix().renderItemsHead(this, pEntityCaps);
			} else {
				HeadMount.loadMatrix().renderItemsHead(this, pEntityCaps);
			}
		}
		GL11.glPopMatrix();*/
    }

    protected ModelRenderer getArmForSide(HandSide side) {
        return side == HandSide.LEFT ? this.bipedLeftArm : this.bipedRightArm;
    }

    protected HandSide getMainHand(T entityIn) {
        HandSide handside = entityIn.getPrimaryHand();
        return entityIn.swingingHand == Hand.MAIN_HAND ? handside : handside.opposite();
    }
}
