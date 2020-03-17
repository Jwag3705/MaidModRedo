package mmr.littledelicacies.client.maidmodel;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class ModelMulti_Steve<T extends LittleMaidBaseEntity> extends ModelMultiBase<T> {

    public MaidModelRenderer bipedHead;
    public MaidModelRenderer bipedHeadwear;
    public MaidModelRenderer bipedBody;
    public MaidModelRenderer bipedRightArm;
    public MaidModelRenderer bipedLeftArm;
    public MaidModelRenderer bipedRightLeg;
    public MaidModelRenderer bipedLeftLeg;
    public MaidModelRenderer bipedEars;
    public MaidModelRenderer bipedCloak;
    public MaidModelRenderer bipedTorso;
    public MaidModelRenderer bipedNeck;
    public MaidModelRenderer bipedPelvic;

    public MaidModelRenderer eyeR;
    public MaidModelRenderer eyeL;


	public ModelMulti_Steve() {
		super();
	}


    public ModelMulti_Steve(float psize) {
		super(psize);
	}
	public ModelMulti_Steve(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
		super(psize, pyoffset, pTextureWidth, pTextureHeight);
	}

	@Override
	public void initModel(float psize, float pyoffset) {
        bipedCloak = new MaidModelRenderer(this, 0, 0);
		bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, psize);
        bipedEars = new MaidModelRenderer(this, 24, 0);
		bipedEars.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, psize);

        bipedHead = new MaidModelRenderer(this, 0, 0);
		bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, psize);
		bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        bipedHeadwear = new MaidModelRenderer(this, 32, 0);
		bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, psize + 0.5F);
		bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        eyeL = new MaidModelRenderer(this, 0, 0);
		eyeL.addBox(0.0F, -5F, -4.001F, 4, 4, 0, psize);
		eyeL.setRotationPoint(0.0F, 0.0F, 0.0F);
        eyeR = new MaidModelRenderer(this, 0, 4);
		eyeR.addBox(-4F, -5F, -4.001F, 4, 4, 0, psize);
		eyeR.setRotationPoint(0.0F, 0.0F, 0.0F);

        bipedBody = new MaidModelRenderer(this, 16, 16);
		bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, psize);
		bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);

        bipedRightArm = new MaidModelRenderer(this, 40, 16);
		bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, psize);
		bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        bipedLeftArm = new MaidModelRenderer(this, 40, 16);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, psize);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);

        bipedRightLeg = new MaidModelRenderer(this, 0, 16);
		bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, psize);
		bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        bipedLeftLeg = new MaidModelRenderer(this, 0, 16);
		bipedLeftLeg.mirror = true;
		bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, psize);
		bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		
		HeadMount.setRotationPoint(0.0F, -4.0F, 0.0F);
		HeadTop.setRotationPoint(0.0F, -12.0F, 0.0F);
        Arms[0] = new MaidModelRenderer(this);
		Arms[0].setRotationPoint(-1.5F, 7.2F, -1F);
        Arms[1] = new MaidModelRenderer(this);
		Arms[1].setRotationPoint(1.5F, 7.2F, -1F);

        bipedTorso = new MaidModelRenderer(this);
        bipedNeck = new MaidModelRenderer(this);
        bipedPelvic = new MaidModelRenderer(this);

        mainFrame = new MaidModelRenderer(this);
		mainFrame.setRotationPoint(0F, pyoffset, 0F);
		mainFrame.addChild(bipedTorso);
		bipedTorso.addChild(bipedNeck);
		bipedTorso.addChild(bipedPelvic);
		bipedTorso.addChild(bipedBody);
		bipedNeck.addChild(bipedHead);
		bipedHead.addChild(bipedHeadwear);
		bipedHead.addChild(bipedEars);
		bipedHead.addChild(HeadMount);
		bipedHead.addChild(HeadTop);
		bipedHead.addChild(eyeL);
		bipedHead.addChild(eyeR);
		bipedNeck.addChild(bipedRightArm);
		bipedNeck.addChild(bipedLeftArm);
		bipedPelvic.addChild(bipedRightLeg);
		bipedPelvic.addChild(bipedLeftLeg);
		bipedRightArm.addChild(Arms[0]);
		bipedLeftArm.addChild(Arms[1]);
		bipedBody.addChild(bipedCloak);
		
		bipedEars.showModel = false;
		bipedCloak.showModel = false;
		
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.mainFrame).forEach((p_228292_8_) -> {
            p_228292_8_.render(matrixStack, iVertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
	}

    /*public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.mainFrame);
    }
    */


	public void setDefaultPause(T entity, float par1, float par2, float pTicksExisted,
								float pHeadYaw, float pHeadPitch) {
		// 初期姿勢
		bipedBody.setRotationPoint2(0.0F, 0.0F, 0.0F);bipedBody.setRotateAngle(0.0F, 0.0F, 0.0F);
		bipedHead.setRotationPoint2(0.0F, 0.0F, 0.0F);bipedHead.setRotateAngleDeg(pHeadPitch, pHeadYaw, 0.0F);
		bipedRightArm.setRotationPoint2(-5.0F, 2.0F, 0.0F);bipedRightArm.setRotateAngle(0.0F, 0.0F, 0.0F);
		bipedLeftArm.setRotationPoint2(5.0F, 2.0F, 0.0F);bipedLeftArm.setRotateAngle(0.0F, 0.0F, 0.0F);
		bipedRightLeg.setRotationPoint2(-1.9F, 12.0F, 0.0F);bipedRightLeg.setRotateAngle(0.0F, 0.0F, 0.0F);
		bipedLeftLeg.setRotationPoint2(1.9F, 12.0F, 0.0F);bipedLeftLeg.setRotateAngle(0.0F, 0.0F, 0.0F);
		bipedTorso.setRotationPoint2(0.0F, 0.0F, 0.0F);bipedTorso.setRotateAngle(0.0F, 0.0F, 0.0F);
		bipedNeck.setRotationPoint2(0.0F, 0.0F, 0.0F);bipedNeck.setRotateAngle(0.0F, 0.0F, 0.0F);
		bipedPelvic.setRotationPoint2(0.0F, 0.0F, 0.0F);bipedPelvic.setRotateAngle(0.0F, 0.0F, 0.0F);
	}

	@Override
	public void render(T entity, float par1, float par2, float pTicksExisted,
					   float pHeadYaw, float pHeadPitch) {
		setDefaultPause(entity, par1, par2, pTicksExisted, pHeadYaw, pHeadPitch);
		
		// 腕ふり、腿上げ
		float lf1 = mh_cos(par1 * 0.6662F);
		float lf2 = mh_cos(par1 * 0.6662F + PI);
		this.bipedRightArm.rotateAngleX = lf2 * 2.0F * par2 * 0.5F;
		this.bipedLeftArm.rotateAngleX = lf1 * 2.0F * par2 * 0.5F;
		this.bipedRightLeg.rotateAngleX = lf1 * 1.4F * par2;
		this.bipedLeftLeg.rotateAngleX = lf2 * 1.4F * par2;


		if (entity.isPassenger() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit())) {
			bipedRightArm.addRotateAngleDegX(-36.0F);
			bipedLeftArm.addRotateAngleDegX(-36.0F);
			bipedRightLeg.addRotateAngleDegX(-72.0F);
			bipedLeftLeg.addRotateAngleDegX(-72.0F);
			bipedRightLeg.addRotateAngleDegY(18.0F);
			bipedLeftLeg.addRotateAngleDegY(-18.0F);
		}

		if (this.swingProgress > 0.0F) {
			HandSide handside = this.getMainHand(entity);
			ModelRenderer modelrenderer = this.getArmForSide(handside);
			float f1 = this.swingProgress;
			this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;
			if (handside == HandSide.LEFT) {
				this.bipedBody.rotateAngleY *= -1.0F;
			}
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

		float lonGround = getMainHand(entity).ordinal();
		if (entity.isMaidWait()) {
			// 待機状態の特別表示
			bipedRightArm.rotateAngleX = mh_sin(pTicksExisted * 0.067F) * 0.05F - 0.7F;
			bipedRightArm.rotateAngleY = 0.0F;
			bipedRightArm.rotateAngleZ = -0.4F;
			bipedLeftArm.rotateAngleX = mh_sin(pTicksExisted * 0.067F) * 0.05F - 0.7F;
			bipedLeftArm.rotateAngleY = 0.0F;
			bipedLeftArm.rotateAngleZ = 0.4F;
		} else {
			if (entity.isShooting()) {
				// 弓構え
				float f6 = mh_sin(lonGround * 3.141593F);
				float f7 = mh_sin((1.0F - (1.0F - lonGround)
						* (1.0F - lonGround)) * 3.141593F);
				bipedRightArm.rotateAngleZ = 0.0F;
				bipedLeftArm.rotateAngleZ = 0.0F;
				bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
				bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
				// bipedRightArm.rotateAngleX = -1.570796F;
				// bipedLeftArm.rotateAngleX = -1.570796F;
				bipedRightArm.rotateAngleX = -1.470796F;
				bipedLeftArm.rotateAngleX = -1.470796F;
				bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
				bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
				bipedRightArm.rotateAngleZ += mh_cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
				bipedLeftArm.rotateAngleZ -= mh_cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
				bipedRightArm.rotateAngleX += mh_sin(pTicksExisted * 0.067F) * 0.05F;
				bipedLeftArm.rotateAngleX -= mh_sin(pTicksExisted * 0.067F) * 0.05F;
				bipedRightArm.rotateAngleX += bipedHead.rotateAngleX;
				bipedLeftArm.rotateAngleX += bipedHead.rotateAngleX;
				bipedRightArm.rotateAngleY += bipedHead.rotateAngleY;
				bipedLeftArm.rotateAngleY += bipedHead.rotateAngleY;
			} else {
				// 通常
				bipedRightArm.rotateAngleZ += 0.5F;
				bipedLeftArm.rotateAngleZ -= 0.5F;
				bipedRightArm.rotateAngleZ += mh_cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
				bipedLeftArm.rotateAngleZ -= mh_cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
				bipedRightArm.rotateAngleX += mh_sin(pTicksExisted * 0.067F) * 0.05F;
				bipedLeftArm.rotateAngleX -= mh_sin(pTicksExisted * 0.067F) * 0.05F;
			}
		}

		float lf;
		// 腕の揺らぎ
		lf = mh_cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleZ += lf;
		this.bipedLeftArm.rotateAngleZ -= lf;
		lf = mh_sin(pTicksExisted * 0.067F) * 0.05F;
		this.bipedRightArm.rotateAngleX += lf;
		this.bipedLeftArm.rotateAngleX -= lf;
		
	}

	@Override
	public void renderItems(MatrixStack stack, boolean left) {
        if (left) {
            this.bipedLeftArm.setAnglesAndRotation(stack);
        } else {
            this.bipedRightArm.setAnglesAndRotation(stack);
        }
	/*	// 手持ちの表示
		GL11.glPushMatrix();
		
		// R
		Arms[0].loadMatrix();
//		GL11.glTranslatef(0F, 0.05F, -0.05F);
		Arms[0].renderItems(this, pEntityCaps, false, 0);
		// L
		Arms[1].loadMatrix();
//		GL11.glTranslatef(0F, 0.05F, -0.05F);
		Arms[1].renderItems(this, pEntityCaps, false, 1);
		// 頭部装飾品
		boolean lplanter = ModelCapsHelper.getCapsValueBoolean(pEntityCaps, caps_isPlanter);
		if (ModelCapsHelper.getCapsValueBoolean(pEntityCaps, caps_isCamouflage) || lplanter) {
			if (lplanter) {
				HeadTop.loadMatrix().renderItemsHead(this, pEntityCaps);
			} else {
				HeadMount.loadMatrix().renderItemsHead(this, pEntityCaps);
			}
		}
		GL11.glPopMatrix();*/
	}

	@Override
	public float[] getArmorModelsSize() {
		return new float[] {0.5F, 1.0F};
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
		return 1.62F;
	}

	@Override
	public float getMountedYOffset() {
		return getHeight() * 0.75F;
	}

	@Override
	public boolean isItemHolder() {
		return true;
	}

	/*@Override
	public int showArmorParts(int parts, int index) {
		if (index == 0) {
			bipedHead.isRendering = parts == 3;
			bipedHeadwear.isRendering = parts == 3;
			bipedBody.isRendering = parts == 1;
			bipedRightArm.isRendering = parts == 2;
			bipedLeftArm.isRendering = parts == 2;
			bipedRightLeg.isRendering = parts == 1;
			bipedLeftLeg.isRendering = parts == 1;
		} else {
			bipedHead.isRendering = parts == 3;
			bipedHeadwear.isRendering = parts == 3;
			bipedBody.isRendering = parts == 2;
			bipedRightArm.isRendering = parts == 2;
			bipedLeftArm.isRendering = parts == 2;
			bipedRightLeg.isRendering = parts == 0;
			bipedLeftLeg.isRendering = parts == 0;
		}
		return -1;
	}*/

	protected ModelRenderer getArmForSide(HandSide side) {
		return side == HandSide.LEFT ? this.bipedLeftArm : this.bipedRightArm;
	}

	protected HandSide getMainHand(T entityIn) {
		HandSide handside = entityIn.getPrimaryHand();
		return entityIn.swingingHand == Hand.MAIN_HAND ? handside : handside.opposite();
	}
}
