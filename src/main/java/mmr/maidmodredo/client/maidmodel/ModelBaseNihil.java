package mmr.maidmodredo.client.maidmodel;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.entity.LivingEntity;

public class ModelBaseNihil<T extends LivingEntity> extends ModelBase<T> {

	public boolean isAlphablend;
	public boolean isModelAlphablend;
	public IModelBaseMMM capsLink;
    public float lighting;
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
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
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

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
