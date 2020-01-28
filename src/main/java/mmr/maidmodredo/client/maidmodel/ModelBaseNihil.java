package mmr.maidmodredo.client.maidmodel;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelBaseNihil<T extends LivingEntity> extends ModelBase<T> {

	public MobRenderer rendererLivingEntity;

	public boolean isAlphablend;
	public boolean isModelAlphablend;
	public IModelBaseMMM capsLink;
	public int lighting;
	public IModelCaps entityCaps;
	public boolean isRendering;
	/**
	 * レンダリングが実行された回数。
	 * ダメージ時などの対策。
	 */
	public int renderCount;


//	@Override
//	public ModelRenderer getRandomModelBox(Random par1Random) {
//		return modelArmorInner.getRandomModelBox(par1Random);
//	}

	public void showAllParts() {
	}

	@Override
	public void render(T par1Entity, float par2, float par3, float par4,
			float par5, float par6, float par7) {
		/*if(par1Entity instanceof IModelCaps) {
			IModelCaps caps = (IModelCaps) par1Entity;
			this.render(caps, par2, par3, par4, par5, par6, par7,true);
		}*/
		renderCount++;
	}

	@Override
	public float[] getArmorModelsSize() {
		return new float[0];
	}
}
