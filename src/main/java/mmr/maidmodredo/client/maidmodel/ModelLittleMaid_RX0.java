package mmr.maidmodredo.client.maidmodel;


import mmr.maidmodredo.entity.LittleMaidBaseEntity;

public class ModelLittleMaid_RX0<T extends LittleMaidBaseEntity> extends ModelLittleMaidBase<T> {

    public MaidModelRenderer bipedForelock;
    public MaidModelRenderer bipedForelockRight;
    public MaidModelRenderer bipedForelockLeft;
    public MaidModelRenderer bipedBust;
    public MaidModelRenderer bipedTrunk;
    public MaidModelRenderer bipedWaist;
    public MaidModelRenderer bipedHipRight;
    public MaidModelRenderer bipedHipLeft;
    public MaidModelRenderer bipedForearmRight;
    public MaidModelRenderer bipedForearmLeft;
    public MaidModelRenderer bipedShinRight;
    public MaidModelRenderer bipedTiptoeRight;
    public MaidModelRenderer bipedHeelRight;
    public MaidModelRenderer bipedShinLeft;
    public MaidModelRenderer bipedTiptoeLeft;
    public MaidModelRenderer bipedHeelLeft;
    public MaidModelRenderer bipedRibbon;
    public MaidModelRenderer bipedRibbon1;
    public MaidModelRenderer bipedRibbon2;
    public MaidModelRenderer bipedTail;
    public MaidModelRenderer SkirtRU;
    public MaidModelRenderer SkirtRB;
    public MaidModelRenderer SkirtLU;
    public MaidModelRenderer SkirtLB;
    public MaidModelRenderer bipedRibbonR;
    public MaidModelRenderer bipedRibbonRSensorU;
    public MaidModelRenderer bipedRibbonRSensorB;
    public MaidModelRenderer bipedSideTailR;
    public MaidModelRenderer bipedRibbonL;
    public MaidModelRenderer bipedRibbonLSensorU;
    public MaidModelRenderer bipedRibbonLSensorB;
    public MaidModelRenderer bipedSideTailL;


    public ModelLittleMaid_RX0() {
        this(0.0F);
    }

    public ModelLittleMaid_RX0(float psize) {
        this(psize, 0.0F, 128, 64);
    }

    public ModelLittleMaid_RX0(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
        super(psize, pyoffset, pTextureWidth, pTextureHeight);
    }


    @Override
    public void initModel(float psize, float pyoffset) {
        bipedHead = new MaidModelRenderer(this);
        bipedHead.setTextureOffset(0, 0).addBox(-4F, -8F, -4F, 8, 8, 8, psize - 0.2F);
        bipedForelock = new MaidModelRenderer(this);
        bipedForelockRight = new MaidModelRenderer(this);
        bipedForelockRight.setTextureOffset(0, 50).addBox(0F, 0F, -0.5F, 3, 13, 1, psize);
        bipedForelockLeft = new MaidModelRenderer(this);
        bipedForelockLeft.setTextureOffset(56, 50).addBox(-3F, 0F, -0.5F, 3, 13, 1, psize);
        bipedRibbon = new MaidModelRenderer(this);
        bipedRibbon.setTextureOffset(116, 10).addBox(-1.5F, 0F, -1.5F, 3, 3, 3);
        bipedRibbon1 = new MaidModelRenderer(this);
        bipedRibbon1.setTextureOffset(116, 0).addBox(-4F, 0F, -2F, 4, 3, 2);
        bipedRibbon2 = new MaidModelRenderer(this);
        bipedRibbon2.setTextureOffset(116, 5).addBox(0F, 0F, -2F, 4, 3, 2);
        bipedTail = new MaidModelRenderer(this);
        bipedTail.setTextureOffset(108, 0).addBox(-1.5F, -0.5F, -0.5F, 3, 11, 1, psize);

        bipedRibbonR = new MaidModelRenderer(this);
        bipedRibbonR.setTextureOffset(80, 0).addBox(-1F, 0F, -1F, 2, 2, 2, 0.1F);
        bipedRibbonRSensorU = new MaidModelRenderer(this);
        bipedRibbonRSensorU.setTextureOffset(80, 4).addBox(-1F, 0F, -1F, 2, 5, 2, 0.2F);
        bipedRibbonRSensorB = new MaidModelRenderer(this);
        bipedRibbonRSensorB.setTextureOffset(74, 0).addBox(-1F, 0F, 0F, 2, 2, 1, 0.1F);
        bipedSideTailR = new MaidModelRenderer(this);
        bipedSideTailR.setTextureOffset(96, 0).addBox(-1F, -0.5F, -0.5F, 2, 11, 1, psize);

        bipedRibbonL = new MaidModelRenderer(this);
        bipedRibbonL.setTextureOffset(88, 0).addBox(-1F, 0F, -1F, 2, 2, 2, 0.1F);
        bipedRibbonLSensorU = new MaidModelRenderer(this);
        bipedRibbonLSensorU.setTextureOffset(88, 4).addBox(-1F, 0F, -1F, 2, 5, 2, 0.2F);
        bipedRibbonLSensorB = new MaidModelRenderer(this);
        bipedRibbonLSensorB.setTextureOffset(74, 3).addBox(-1F, 0F, 0F, 2, 2, 1, 0.1F);
        bipedSideTailL = new MaidModelRenderer(this);
        bipedSideTailL.setTextureOffset(102, 0).addBox(-1F, -0.5F, -0.5F, 2, 11, 1, psize);


        bipedBody = new MaidModelRenderer(this);
        bipedBody.setTextureOffset(32, 7).addBox(-3F, 0F, -1F, 6, 3, 3, psize);
        bipedBody.setTextureOffset(26, 40).addBox(-1.5F, -1.0F, -1.5F, 3, 3, 3, psize);
        bipedBust = new MaidModelRenderer(this);
        bipedBust.setTextureOffset(32, 0).addBox(-3F, -2.5F, 0F, 6, 4, 3, psize - 0.04F);

        bipedRightArm = new MaidModelRenderer(this);
        bipedRightArm.setTextureOffset(8, 47).addBox(-2F, -0.5F, -1F, 2, 7, 2, psize);
        bipedForearmRight = new MaidModelRenderer(this);
        bipedForearmRight.setTextureOffset(0, 40).addBox(-1F, -1F, -1F, 2, 8, 2, psize - 0.05F);
        bipedLeftArm = new MaidModelRenderer(this);
        bipedLeftArm.setTextureOffset(48, 47).addBox(0F, -0.5F, -1F, 2, 7, 2, psize);
        bipedForearmLeft = new MaidModelRenderer(this);
        bipedForearmLeft.setTextureOffset(56, 40).addBox(-1F, -1F, -1F, 2, 8, 2, psize - 0.05F);

        bipedTrunk = new MaidModelRenderer(this);
        bipedWaist = new MaidModelRenderer(this);
        bipedWaist.setTextureOffset(24, 46).addBox(-2.5F, 0F, -1.95F, 5, 7, 3, psize);
        bipedHipRight = new MaidModelRenderer(this);
        bipedHipRight.setTextureOffset(50, 0).addBox(0F, -1.5F, -2F, 3, 4, 4, psize);
        bipedHipLeft = new MaidModelRenderer(this);
        bipedHipLeft.setTextureOffset(50, 8).addBox(-3F, -1.5F, -2F, 3, 4, 4, psize);

        bipedRightLeg = new MaidModelRenderer(this);
        bipedRightLeg.setTextureOffset(0, 29).addBox(-3F, 0F, -2F, 3, 7, 4, psize);
        bipedShinRight = new MaidModelRenderer(this);
        bipedShinRight.setTextureOffset(0, 16).addBox(-3F, 0F, -3F, 3, 9, 4, psize - 0.2F);
        bipedTiptoeRight = new MaidModelRenderer(this);
        bipedTiptoeRight.setTextureOffset(12, 26).addBox(-1.5F, 0F, -4F, 3, 2, 4, psize);
        bipedHeelRight = new MaidModelRenderer(this);
        bipedHeelRight.setTextureOffset(10, 16).addBox(-1F, 0.25F, -3.25F, 2, 1, 3, psize + 0.25F);

        bipedLeftLeg = new MaidModelRenderer(this);
        bipedLeftLeg.setTextureOffset(50, 29).addBox(0F, 0F, -2F, 3, 7, 4, psize);
        bipedShinLeft = new MaidModelRenderer(this);
        bipedShinLeft.setTextureOffset(50, 16).addBox(0F, 0F, -3F, 3, 9, 4, psize - 0.2F);
        bipedTiptoeLeft = new MaidModelRenderer(this);
        bipedTiptoeLeft.setTextureOffset(38, 26).addBox(-1.5F, 0F, -4F, 3, 2, 4, psize);
        bipedHeelLeft = new MaidModelRenderer(this);
        bipedHeelLeft.setTextureOffset(20, 16).addBox(-1F, 0.25F, -3.25F, 2, 1, 3, psize + 0.25F);

        Skirt = new MaidModelRenderer(this);
        Skirt.setTextureOffset(20, 26).addBox(-3F, 0F, -3F, 6, 8, 6, psize + 0.05F);
        SkirtRU = new MaidModelRenderer(this);
        SkirtRU.setTextureOffset(8, 34).addBox(2F, 2F, -3F, 3, 7, 6, psize);
        SkirtRB = new MaidModelRenderer(this);
        SkirtRB.setTextureOffset(8, 48).addBox(-4F, 0F, -4F, 4, 8, 8, psize);
        SkirtLU = new MaidModelRenderer(this);
        SkirtLU.setTextureOffset(38, 34).addBox(-5F, 2F, -3F, 3, 7, 6, psize);
        SkirtLB = new MaidModelRenderer(this);
        SkirtLB.setTextureOffset(32, 48).addBox(0F, 0F, -4F, 4, 8, 8, psize);

        mainFrame = new MaidModelRenderer(this);
        bipedNeck = new MaidModelRenderer(this);
        bipedTorso = new MaidModelRenderer(this);
        bipedPelvic = new MaidModelRenderer(this);

        Arms[0] = new MaidModelRenderer(this);
        Arms[0].setRotationPoint(0F, 5F, -1F);
        Arms[1] = new MaidModelRenderer(this);
        Arms[1].setRotationPoint(0F, 5F, -1F);
        //Arms[1].isInvertX = true;

        HeadMount.setRotationPoint(0F, -4F, 0F);
        HeadTop.setRotationPoint(0F, -13F, 0F);

        mainFrame.addChild(bipedTorso);
        bipedTorso.addChild(bipedNeck);
        bipedNeck.addChild(bipedHead);
        bipedHead.addChild(bipedForelock);
        bipedForelock.addChild(bipedForelockRight);
        bipedForelock.addChild(bipedForelockLeft);
        bipedHead.addChild(bipedRibbon);
        bipedRibbon.addChild(bipedRibbon1);
        bipedRibbon.addChild(bipedRibbon2);
        bipedRibbon.addChild(bipedTail);
        bipedHead.addChild(bipedRibbonR);
        bipedRibbonR.addChild(bipedRibbonRSensorU);
        bipedRibbonR.addChild(bipedRibbonRSensorB);
        bipedRibbonR.addChild(bipedSideTailR);
        bipedHead.addChild(bipedRibbonL);
        bipedRibbonL.addChild(bipedRibbonLSensorU);
        bipedRibbonL.addChild(bipedRibbonLSensorB);
        bipedRibbonL.addChild(bipedSideTailL);
        bipedNeck.addChild(bipedRightArm);
        bipedRightArm.addChild(bipedForearmRight);
        bipedNeck.addChild(bipedLeftArm);
        bipedLeftArm.addChild(bipedForearmLeft);
        bipedTorso.addChild(bipedBody);
        bipedBody.addChild(bipedBust);
        bipedTorso.addChild(bipedTrunk);
        bipedTrunk.addChild(bipedWaist);
        bipedWaist.addChild(bipedHipRight);
        bipedWaist.addChild(bipedHipLeft);
        bipedTrunk.addChild(bipedPelvic);
        bipedPelvic.addChild(bipedRightLeg);
        bipedRightLeg.addChild(bipedShinRight);
        bipedShinRight.addChild(bipedTiptoeRight);
        bipedTiptoeRight.addChild(bipedHeelRight);
        bipedPelvic.addChild(bipedLeftLeg);
        bipedLeftLeg.addChild(bipedShinLeft);
        bipedShinLeft.addChild(bipedTiptoeLeft);
        bipedTiptoeLeft.addChild(bipedHeelLeft);
        bipedPelvic.addChild(Skirt);
        Skirt.addChild(SkirtRU);
        SkirtRU.addChild(SkirtRB);
        Skirt.addChild(SkirtLU);
        SkirtLU.addChild(SkirtLB);

        bipedForearmRight.addChild(Arms[0]);
        bipedForearmLeft.addChild(Arms[1]);
        bipedHead.addChild(HeadTop);
        bipedHead.addChild(HeadMount);
		
		/*
		MaidModelRenderer ltest = new MaidModelRenderer(this);
		ltest.setTextureOffset( 8, 8).addPlate(-4F, -4F, -4F, 8, 8, ModelPlate.planeXYFront, 0.3F);
		ltest.setTextureOffset(24, 8).addPlate(-4F, -4F,  4F, 8, 8, ModelPlate.planeXYBack, 0.3F);
		ltest.setTextureOffset( 0, 8).addPlate(-4F, -4F, -4F, 8, 8, ModelPlate.planeZYRight, 0.3F);
		ltest.setTextureOffset(16, 8).addPlate( 4F, -4F, -4F, 8, 8, ModelPlate.planeZYLeft, 0.3F);
		ltest.setTextureOffset( 8, 0).addPlate(-4F, -4F, -4F, 8, 8, ModelPlate.planeXZTop, 0.3F);
		ltest.setTextureOffset(16, 0).addPlate(-4F,  4F, -4F, 8, 8, ModelPlate.planeXZBottom, 0.3F);
		ltest.setRotationPoint(0F, -20F, 0F);
		bipedHead.addChild(ltest);
		*/


    }

    @Override
    public void setDefaultPause(T entity, float par1, float par2, float pTicksExisted,
                                float pHeadYaw, float pHeadPitch) {
        super.setDefaultPause(entity, par1, par2, pTicksExisted, pHeadYaw, pHeadPitch);

        bipedRibbon.setVisible(true);
//		Skirt.setVisible((lvisible & 0x0004) == 0);
//		bipedRightLeg.setVisible(true);
//		bipedLeftLeg.setVisible(true);
//		GL11.glScalef(0.85F, 0.85F, 0.85F);
        scaleFactor = 0.80F;

        // bansheeMode
        bipedBust.setRotationPoint(0F, 2.5F, -2F);
        bipedBust.setRotateAngleDegX(-0F);
        bipedForearmRight.setRotationPoint(-1.025F, 0.5F, 0F);
        bipedForearmLeft.setRotationPoint(1.025F, 0.5F, 0F);
        bipedForearmRight.setRotateAngleDegX(0F);
        bipedForearmLeft.setRotateAngleDegX(0F);

        bipedTrunk.setRotationPoint(0F, 0F, 0F);
        bipedForelock.setRotationPoint(0F, -7.5F, -4F);
        bipedForelockRight.setRotationPoint(-3.9F, 0F, 0F);
        bipedForelockLeft.setRotationPoint(3.9F, 0F, 0F);
        bipedForelockRight.setRotateAngleDegY(0F);
        bipedForelockLeft.setRotateAngleDegY(0F);
        float lf = bipedHead.rotateAngleX > 0 ? -bipedHead.rotateAngleX / 2F : 0F;
        bipedForelock.setRotateAngleX(lf);
        bipedRibbon.setRotationPoint(0F, -0.2F, 4F);
        bipedRibbon.setRotateAngleDegX(20F - bipedHead.getRotateAngleDegX() + (0F));
        bipedRibbon1.setRotationPoint(-1.5F, 0F, 1F);
        bipedRibbon2.setRotationPoint(1.5F, 0F, 1F);
        bipedRibbon1.setRotateAngleDegY(0F);
        bipedRibbon2.setRotateAngleDegY(0F);
        bipedTail.setRotationPoint(0F, 3.0F, 0F);
        bipedTail.setRotateAngle(0F, 0F, 0F);

        bipedRibbonR.setRotationPoint(-3.5F, 0F, 4F);
        bipedRibbonR.setRotateAngleDeg(15F - bipedHead.getRotateAngleDegX() + (0F), 0F, 10F);
        bipedRibbonRSensorU.setRotationPoint(0F, 1F, -0.2F);
        bipedRibbonRSensorU.setRotateAngleDeg(10F, 0F, 0F);
        bipedRibbonRSensorB.setRotationPoint(0F, 2F, -0.9F);
        bipedRibbonRSensorB.setRotateAngleDeg(0F, 0F, 0F);
        bipedSideTailR.setRotationPoint(0F, 2F, 0F);
        bipedSideTailR.setRotateAngleDeg(0F, 0F, 0F);

        bipedRibbonL.setRotationPoint(3.5F, 0F, 4F);
        bipedRibbonL.setRotateAngleDeg(15F - bipedHead.getRotateAngleDegX() + (0F), 0F, -10F);
        bipedRibbonLSensorU.setRotationPoint(0F, 1F, -0.2F);
        bipedRibbonLSensorU.setRotateAngleDeg(10F, 0F, 0F);
        bipedRibbonLSensorB.setRotationPoint(0F, 2F, -0.9F);
        bipedRibbonLSensorB.setRotateAngleDeg(0F, 0F, 0F);
        bipedSideTailL.setRotationPoint(0F, 2F, 0F);
        bipedSideTailL.setRotateAngleDeg(0F, 0F, 0F);


        bipedHead.setRotationPoint(0F, 0.2F, 0F);
        bipedHipRight.setRotationPoint(-3F, 4.5F, 0F);
        bipedHipLeft.setRotationPoint(3F, 4.5F, 0F);
        bipedHipRight.setRotateAngleDeg(0F, 0F, 0F);
        bipedHipLeft.setRotateAngleDeg(0F, 0F, 0F);

        bipedRightLeg.setRotationPoint(0F, 0F, 0F);
        bipedRightLeg.setRotateAngleDegZ(0F);
        bipedShinRight.setRotationPoint(0F, 0F, 1F);
        bipedShinRight.setRotateAngleDeg(0F, 0F, 0F);
        bipedTiptoeRight.setRotationPoint(-1.5F, 7F, 1F);
        bipedTiptoeRight.setRotateAngleDeg(0F, 0F, 0F);
        bipedHeelRight.setRotationPoint(0F, 0F, -0.05F);
        bipedHeelRight.setRotateAngleDeg(0F, 0F, 0F);

        bipedLeftLeg.setRotationPoint(0F, 0F, 0F);
        bipedLeftLeg.setRotateAngleDegZ(0F);
        bipedShinLeft.setRotationPoint(0F, 0F, 1F);
        bipedShinLeft.setRotateAngleDeg(0F, 0F, 0F);
        bipedTiptoeLeft.setRotationPoint(1.5F, 7F, 1F);
        bipedTiptoeLeft.setRotateAngleDeg(0F, 0F, 0F);
        bipedHeelLeft.setRotationPoint(0F, 0F, -0.05F);
        bipedHeelLeft.setRotateAngleDeg(0F, 0F, 0F);

        bipedPelvic.setRotationPoint(0F, 7F, 0F);

        Skirt.setRotationPoint(0F, -2F, 0F);
        SkirtRU.setRotationPoint(-5F, -1F, 0F);
        SkirtRU.setRotateAngleDegZ(0F);
        SkirtRB.setRotationPoint(5F, 1F, 0F);
        SkirtLU.setRotationPoint(5F, -1F, 0F);
        SkirtLU.setRotateAngleDegZ(0F);
        SkirtLB.setRotationPoint(-5F, 1F, 0F);

//			bipedTorso.setRotationPointY(4.1F);
        bipedTorso.setRotationPointY(0F);


    }

    @Override
    public int showArmorParts(int parts, int index) {

        // 鎧の表示用
        boolean f;
        // 允E
        f = parts == 3 ? true : false;
        bipedHead.setVisible(f);
        // 鎧
        f = parts == 2 ? true : false;
        bipedBody.setVisible(f);
        bipedWaist.setVisible(f);
        bipedRightArm.setVisible(f);
        bipedLeftArm.setVisible(f);
        // 脚甲
        f = parts == 1 ? true : false;
        Skirt.setVisible(f);
        // 臑彁E
        f = parts == 0 ? true : false;
        bipedRightLeg.setVisible(f);
        bipedLeftLeg.setVisible(f);

        return -1;
    }

}
