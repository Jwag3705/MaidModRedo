package mmr.maidmodredo.client.model.item;

import com.google.common.collect.ImmutableList;
import mmr.maidmodredo.client.maidmodel.MaidModelRenderer;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class StuffModel extends SegmentedModel {
    public MaidModelRenderer bone;
    public MaidModelRenderer bone2;

    public StuffModel() {
        textureWidth = 32;
        textureHeight = 32;

        bone = new MaidModelRenderer(this);
        bone.setRotationPoint(-8.0F, 16.0F, 8.0F);
        bone.addBox(bone, 0, 0, 7.59F, -11.73F, -8.5F, 1, 14, 1, 0.0F, false);
        bone.addBox(bone, 8, 26, 7.25F, -12.56F, -8.84F, 1, 1, 2, 0.0F, false);
        bone.addBox(bone, 10, 22, 5.55F, -12.24F, -8.5F, 5, 0, 1, 0.0F, false);
        bone.addBox(bone, 12, 30, 10.8F, -14.92F, -8.5F, 0, 1, 1, 0.0F, false);
        bone.addBox(bone, 8, 30, 4.52F, -14.92F, -8.5F, 0, 1, 1, 0.0F, false);
        bone.addBox(bone, 4, 30, 10.47F, -13.92F, -8.5F, 0, 1, 1, 0.0F, false);
        bone.addBox(bone, 0, 30, 4.85F, -13.92F, -8.5F, 0, 1, 1, 0.0F, false);

        bone2 = new MaidModelRenderer(this);
        bone2.setRotationPoint(-8.0F, 16.0F, 8.25F);
        setRotationAngle(bone2, 0.0F, 0.7854F, 0.0F);
        bone2.addBox(bone2, 14, 26, 10.5637F, -16.26F, -0.75F, 1, 3, 1, 0.0F, false);
        bone2.addBox(bone2, 0, 26, 10.2237F, -15.6F, -1.09F, 2, 2, 2, 0.0F, false);
        bone2.addBox(bone2, 0, 22, 9.8837F, -15.94F, -1.43F, 3, 2, 2, 0.0F, false);
    }


    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.bone, this.bone2);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}