package mmr.maidmodredo.client.maidmodel;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.LivingEntity;

public interface IModelBaseMMM extends IModelCaps {

	public void renderItems(LivingEntity pEntity, MobRenderer pRender);
	public void showArmorParts(int pParts);
	public void setEntityCaps(IModelCaps pModelCaps);
	public void setRender(MobRenderer pRender);
	public void setArmorRendering(boolean pFlag);

}
