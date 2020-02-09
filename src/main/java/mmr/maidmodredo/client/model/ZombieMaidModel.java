package mmr.maidmodredo.client.model;


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import mmr.maidmodredo.entity.ZombieMaidEntity;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieMaidModel<T extends ZombieMaidEntity> extends SegmentedModel<T> implements IHasArm {
    public ModelRenderer mainFrame;
    public ModelRenderer bipedTorso;
    public ModelRenderer bipedNeck;
    public ModelRenderer bipedHead;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedPelvic;
    public ModelRenderer Skirt;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedLeftLeg;

    /**
     * コンストラクタは全て継承させること
     */
    public ZombieMaidModel() {
        super();
        this.textureWidth = 64;
        this.textureHeight = 32;
        mainFrame = new ModelRenderer(this).setTextureSize(this.textureWidth, this.textureHeight);
        mainFrame.setRotationPoint(0F, 0F + getyOffset() + 8F, 0F);

        bipedTorso = new ModelRenderer(this).setTextureSize(this.textureWidth, this.textureHeight);
        bipedNeck = new ModelRenderer(this).setTextureSize(this.textureWidth, this.textureHeight);
        bipedPelvic = new ModelRenderer(this).setTextureSize(this.textureWidth, this.textureHeight);
        bipedPelvic.setRotationPoint(0F, 7F, 0F);

        bipedHead = new ModelRenderer(this, 0, 0);
        bipedHead.setTextureOffset(0, 0).addBox(-4F, -8F, -4F, 8, 8, 8, 0.0F);        // Head
        bipedHead.setTextureOffset(24, 0).addBox(-4F, 0F, 1F, 8, 4, 3, 0.0F);            // Hire
        bipedHead.setTextureOffset(24, 18).addBox(-4.995F, -7F, 0.2F, 1, 3, 3, 0.0F);        // ChignonR
        bipedHead.setTextureOffset(24, 18).addBox(3.995F, -7F, 0.2F, 1, 3, 3, 0.0F);        // ChignonL
        bipedHead.setTextureOffset(52, 10).addBox(-2F, -7.2F, 4F, 4, 4, 2, 0.0F);        // ChignonB
        bipedHead.setTextureOffset(46, 20).addBox(-1.5F, -6.8F, 4F, 3, 9, 3, 0.0F);    // Tail
        bipedHead.setTextureOffset(58, 21).addBox(-5.5F, -6.8F, 0.9F, 1, 8, 2, 0.0F);    // SideTailR
        bipedHead.mirror = true;
        bipedHead.setTextureOffset(58, 21).addBox(4.5F, -6.8F, 0.9F, 1, 8, 2, 0.0F);    // SideTailL
        bipedHead.setRotationPoint(0F, 0F, 0F);

        bipedRightArm = new ModelRenderer(this, 48, 0);
        bipedRightArm.addBox(-2.0F, -1F, -1F, 2, 8, 2, 0.0F);
        bipedRightArm.setRotationPoint(-3.0F, 1.5F, 0F);

        bipedLeftArm = new ModelRenderer(this, 56, 0);
        bipedLeftArm.addBox(0.0F, -1F, -1F, 2, 8, 2, 0.0F);
        bipedLeftArm.setRotationPoint(3.0F, 1.5F, 0F);

        bipedRightLeg = new ModelRenderer(this, 32, 19);
        bipedRightLeg.addBox(-2F, 0F, -2F, 3, 9, 4, 0.0F);
        bipedRightLeg.setRotationPoint(-1F, 0F, 0F);

        bipedLeftLeg = new ModelRenderer(this, 32, 19);
        bipedLeftLeg.addBox(-1F, 0F, -2F, 3, 9, 4, 0.0F);
        bipedLeftLeg.setRotationPoint(1F, 0F, 0F);

        Skirt = new ModelRenderer(this, 0, 16);
        Skirt.addBox(-4F, -2F, -4F, 8, 8, 8, 0.0F);
        Skirt.setRotationPoint(0F, 0F, 0F);

        bipedBody = new ModelRenderer(this, 32, 8);
        bipedBody.addBox(-3F, 0F, -2F, 6, 7, 4, 0.0F);
        bipedBody.setRotationPoint(0F, 0F, 0F);

        mainFrame.addChild(bipedTorso);
        bipedTorso.addChild(bipedNeck);
        bipedTorso.addChild(bipedBody);
        bipedTorso.addChild(bipedPelvic);
        bipedNeck.addChild(bipedHead);
        bipedNeck.addChild(bipedRightArm);
        bipedNeck.addChild(bipedLeftArm);
        bipedPelvic.addChild(bipedRightLeg);
        bipedPelvic.addChild(bipedLeftLeg);
        bipedPelvic.addChild(Skirt);
    }


    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.mainFrame);
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = entityIn.isAggressive();

        float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);

        this.bipedHead.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.bipedHead.rotateAngleX = headPitch * ((float) Math.PI / 180F);

        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
        this.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
        float f2 = -(float) Math.PI / (flag ? 1.5F : 2.25F);
        this.bipedRightArm.rotateAngleX = f2;
        this.bipedLeftArm.rotateAngleX = f2;
        this.bipedRightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.bipedLeftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.bipedRightLeg.rotateAngleY = 0.0F;
        this.bipedLeftLeg.rotateAngleY = 0.0F;
    }

    private ModelRenderer getArm(HandSide p_191216_1_) {
        return p_191216_1_ == HandSide.LEFT ? this.bipedLeftArm : this.bipedRightArm;
    }

    public void translateHand(HandSide sideIn, MatrixStack matrixStackIn) {
        this.getArm(sideIn).setAnglesAndRotation(matrixStackIn);
    }

    public float getHeight() {
        return 1.35F;
    }

    public float getyOffset() {
        return getHeight() * 0.9F;
    }
}
