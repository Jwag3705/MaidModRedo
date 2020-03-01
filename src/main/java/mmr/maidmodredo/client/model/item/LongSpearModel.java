package mmr.maidmodredo.client.model.item;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LongSpearModel extends EntityModel<Entity> {
    public ModelRenderer stick;
    public ModelRenderer shape;
    public ModelRenderer shape2;
    public ModelRenderer shape3;

    public LongSpearModel() {
        super(RenderType::entityTranslucent);
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shape2 = new ModelRenderer(this, 4, 5);
        this.shape2.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.shape2.addBox(-0.5F, -3.0F, -0.5F, 1, 5, 1, 0.0F);
        this.shape3 = new ModelRenderer(this, 4, 3);
        this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape3.addBox(-1.0F, -3.0F, -0.5F, 2, 1, 1, 0.0F);
        this.shape = new ModelRenderer(this, 4, 0);
        this.shape.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape.addBox(-1.5F, -2.0F, -0.5F, 3, 2, 1, 0.0F);
        this.stick = new ModelRenderer(this, 0, 0);
        this.stick.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.stick.addBox(-0.5F, 0.0F, -0.5F, 1, 22, 1, 0.0F);
        this.stick.addChild(this.shape2);
        this.stick.addChild(this.shape3);
        this.stick.addChild(this.shape);
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.stick).forEach((p_228272_8_) -> {
            p_228272_8_.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
