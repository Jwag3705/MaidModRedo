package mmr.maidmodredo.client.model;

import com.google.common.collect.ImmutableList;
import mmr.maidmodredo.entity.projectile.RootEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RootModel<T extends RootEntity> extends SegmentedModel<T> {
    public ModelRenderer root;
    public ModelRenderer root3;
    public ModelRenderer root5;
    public ModelRenderer root7;
    public ModelRenderer root2;
    public ModelRenderer root4;
    public ModelRenderer root6;
    public ModelRenderer root8;

    public RootModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.root6 = new ModelRenderer(this, 8, 0);
        this.root6.setRotationPoint(0.0F, -4.0F, -0.1F);
        this.root6.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(root6, 0.31869712141416456F, 0.0F, 0.0F);
        this.root8 = new ModelRenderer(this, 8, 0);
        this.root8.setRotationPoint(0.0F, -4.0F, -0.1F);
        this.root8.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(root8, 0.31869712141416456F, 0.0F, 0.0F);
        this.root4 = new ModelRenderer(this, 8, 0);
        this.root4.setRotationPoint(0.0F, -4.0F, -0.1F);
        this.root4.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(root4, 0.31869712141416456F, 0.0F, 0.0F);
        this.root7 = new ModelRenderer(this, 0, 0);
        this.root7.setRotationPoint(5.0F, 29.0F, 0.0F);
        this.root7.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(root7, 0.6373942428283291F, 1.5707963267948966F, 0.0F);
        this.root3 = new ModelRenderer(this, 0, 0);
        this.root3.setRotationPoint(0.0F, 29.0F, -5.0F);
        this.root3.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(root3, 0.6373942428283291F, -3.141592653589793F, 0.0F);
        this.root2 = new ModelRenderer(this, 8, 0);
        this.root2.setRotationPoint(0.0F, -4.0F, -0.1F);
        this.root2.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(root2, 0.31869712141416456F, 0.0F, 0.0F);
        this.root = new ModelRenderer(this, 0, 0);
        this.root.setRotationPoint(0.0F, 29.0F, 5.0F);
        this.root.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(root, 0.6373942428283291F, 0.0F, 0.0F);
        this.root5 = new ModelRenderer(this, 0, 0);
        this.root5.setRotationPoint(-5.0F, 29.0F, 0.0F);
        this.root5.addBox(-1.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(root5, 0.6373942428283291F, -1.5707963267948966F, 0.0F);
        this.root5.addChild(this.root6);
        this.root7.addChild(this.root8);
        this.root3.addChild(this.root4);
        this.root.addChild(this.root2);
    }

    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = limbSwing * 2.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        f = 1.0F - f * f * f;
        float f1 = (limbSwing + MathHelper.sin(limbSwing * 2.7F)) * 0.6F * 12.0F;

        this.root.rotateAngleX = 0.6373942428283291F * limbSwing;
        this.root3.rotateAngleX = 0.6373942428283291F * limbSwing;
        this.root5.rotateAngleX = 0.6373942428283291F * limbSwing;
        this.root7.rotateAngleX = 0.6373942428283291F * limbSwing;

        this.root.rotationPointY = 29.0F - f1;
        this.root3.rotationPointY = this.root.rotationPointY;
        this.root5.rotationPointY = this.root.rotationPointY;
        this.root7.rotationPointY = this.root.rotationPointY;
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.root7, this.root3, this.root5, this.root);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}