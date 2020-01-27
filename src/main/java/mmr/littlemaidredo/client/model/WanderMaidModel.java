package mmr.littlemaidredo.client.model;

import mmr.littlemaidredo.entity.WanderMaidEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WanderMaidModel <T extends WanderMaidEntity> extends EntityModel<T> implements IHasArm, IHasHead {
    public RendererModel head;
    public RendererModel body;
    public RendererModel handR;
    public RendererModel handL;
    public RendererModel skirt;
    public RendererModel legR;
    public RendererModel legL;
    public RendererModel body2;
    public RendererModel chestR;
    public RendererModel chestL;

    public ArmPose armPose = ArmPose.EMPTY;

    public WanderMaidModel() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.body = new RendererModel(this, 32, 0);
        this.body.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.body.addBox(-3.0F, 0.0F, -2.0F, 6, 9, 4, 0.0F);
        this.head = new RendererModel(this, 0, 0);
        this.head.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.handR = new RendererModel(this, 60, 0);
        this.handR.setRotationPoint(-4.0F, 3.0F, 0.0F);
        this.handR.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.handL = new RendererModel(this, 52, 0);
        this.handL.setRotationPoint(4.0F, 3.0F, 0.0F);
        this.handL.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.chestL = new RendererModel(this, 15, 30);
        this.chestL.setRotationPoint(4.0F, -1.1F, 0.0F);
        this.chestL.addBox(0.0F, 0.0F, -2.5F, 1, 5, 5, 0.0F);
        this.legL = new RendererModel(this, 68, 0);
        this.legL.setRotationPoint(1.6F, 15.0F, 0.0F);
        this.legL.addBox(-1.5F, 0.0F, -2.0F, 3, 9, 4, 0.0F);
        this.body2 = new RendererModel(this, 32, 13);
        this.body2.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.body2.addBox(-3.5F, 0.0F, -2.5F, 7, 3, 5, 0.0F);
        this.chestR = new RendererModel(this, 0, 30);
        this.chestR.setRotationPoint(-4.0F, -1.1F, 0.0F);
        this.chestR.addBox(-1.0F, 0.0F, -2.5F, 1, 5, 5, 0.0F);
        this.legR = new RendererModel(this, 68, 0);
        this.legR.setRotationPoint(-1.6F, 15.0F, 0.0F);
        this.legR.addBox(-1.5F, 0.0F, -2.0F, 3, 9, 4, 0.0F);
        this.skirt = new RendererModel(this, 0, 16);
        this.skirt.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.skirt.addBox(-4.0F, -2.0F, -4.0F, 8, 6, 8, 0.0F);
        this.skirt.addChild(this.chestL);
        this.body.addChild(this.body2);
        this.skirt.addChild(this.chestR);
    }

    @Override
    public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.body.render(scale);
        this.legR.render(scale);
        this.head.render(scale);
        this.skirt.render(scale);
        this.handL.render(scale);
        this.legL.render(scale);
        this.handR.render(scale);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.armPose = ArmPose.EMPTY;
        ItemStack itemstack = entityIn.getHeldItem(Hand.MAIN_HAND);
        if (itemstack.getItem() instanceof net.minecraft.item.BowItem && entityIn.isAggressive()) {
            this.armPose = ArmPose.BOW_AND_ARROW;
        }else if(entityIn.isAggressive()){
            this.armPose = ArmPose.ATTACKING;
        }

        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.head.rotateAngleY = (float) Math.toRadians(netHeadYaw);
        this.head.rotateAngleX = (float) Math.toRadians(headPitch);
        this.legR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.legL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.legR.rotateAngleY = 0.0F;
        this.legL.rotateAngleY = 0.0F;

        this.handR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.2F;
        this.handR.rotateAngleY = 0.0F;
        this.handR.rotateAngleZ = 0.0F;
        this.handL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
        this.handL.rotateAngleY = 0.0F;
        this.handL.rotateAngleZ = 0.0F;


        if (this.armPose == ArmPose.BOW_AND_ARROW) {
            this.handR.rotateAngleY = -0.1F + this.head.rotateAngleY;
            this.handR.rotateAngleX = (-(float)Math.PI / 2F) + this.head.rotateAngleX;
            this.handL.rotateAngleX = -0.9424779F + this.head.rotateAngleX;
            this.handL.rotateAngleY = this.head.rotateAngleY - 0.4F;
            this.handL.rotateAngleZ = ((float)Math.PI / 2F);
        }else if(this.armPose == ArmPose.ATTACKING){
            float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);

            this.handR.rotateAngleX = -0.4F;
            this.handR.rotateAngleY = 0.0F;
            this.handR.rotateAngleZ = 0.0F;
            this.handL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
            this.handL.rotateAngleY = 0.0F;
            this.handL.rotateAngleZ = 0.0F;

            if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
                this.handR.rotateAngleX = -0.4F;
                this.handR.rotateAngleY = 0.0F;
                this.handR.rotateAngleZ = 0.0F;
                this.handL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
                this.handL.rotateAngleY = 0.0F;
                this.handL.rotateAngleZ = 0.0F;
                this.handR.rotateAngleX += f * 2.2F - f1 * 0.4F;
                this.handL.rotateAngleX += f * 1.2F - f1 * 0.4F;
            } else {
                this.handR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.2F;
                this.handR.rotateAngleY = 0.0F;
                this.handR.rotateAngleZ = 0.0F;
                this.handL.rotateAngleX = -0.4F;
                this.handL.rotateAngleY = 0.0F;
                this.handL.rotateAngleZ = 0.0F;
                this.handR.rotateAngleX += f * 1.2F - f1 * 0.4F;
                this.handL.rotateAngleX += f * 2.2F - f1 * 0.4F;
            }
        }

        this.handR.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handL.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.handR.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.handL.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }

    @Override
    public RendererModel func_205072_a() {
        return this.head;
    }

    public RendererModel getArm(HandSide handSide) {
        return handSide == HandSide.LEFT ? this.handL : this.handR;
    }

    @Override
    public void postRenderArm(float scale, HandSide side) {
        this.getArm(side).postRender(0.0625F);
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @OnlyIn(Dist.CLIENT)
    public static enum ArmPose {
        EMPTY,
        ATTACKING,
        BOW_AND_ARROW,
        THROW_SPEAR,
        CROSSBOW_CHARGE,
        CROSSBOW_HOLD;
    }
}
