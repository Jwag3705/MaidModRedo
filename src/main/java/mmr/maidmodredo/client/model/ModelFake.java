package mmr.maidmodredo.client.model;

import mmr.maidmodredo.entity.LittleMaidBaseEntity;

public class ModelFake<T extends LittleMaidBaseEntity> extends mmr.maidmodredo.client.maidmodel.ModelBase<T> {
    @Override
    public float[] getArmorModelsSize() {
        return new float[0];
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
