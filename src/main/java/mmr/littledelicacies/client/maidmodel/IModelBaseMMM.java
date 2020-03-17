package mmr.littledelicacies.client.maidmodel;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.LivingEntity;

public interface IModelBaseMMM {

    public void renderItems(LivingEntity pEntity, MatrixStack stack, boolean left);
	public void showArmorParts(int pParts);
	public void setRender(MobRenderer pRender);
	public void setArmorRendering(boolean pFlag);

}
