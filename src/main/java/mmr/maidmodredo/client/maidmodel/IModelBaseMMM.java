package mmr.maidmodredo.client.maidmodel;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.LivingEntity;

public interface IModelBaseMMM extends IModelCaps {

    public void renderItems(LivingEntity pEntity, MatrixStack stack, boolean left);
	public void showArmorParts(int pParts);
	public void setEntityCaps(IModelCaps pModelCaps);
	public void setRender(MobRenderer pRender);
	public void setArmorRendering(boolean pFlag);

}
