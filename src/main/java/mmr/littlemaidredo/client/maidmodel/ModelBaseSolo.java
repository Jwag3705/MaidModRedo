package mmr.littlemaidredo.client.maidmodel;

import mmr.littlemaidredo.utils.helper.RendererHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Map;

public class ModelBaseSolo<T extends LivingEntity> extends ModelBaseNihil<T> implements IModelBaseMMM {

	public ModelMultiBase<T> model;
	public ResourceLocation[] textures;
	public static final ResourceLocation[] blanks = new ResourceLocation[0];


	public ModelBaseSolo(MobRenderer pRender) {
		rendererLivingEntity = pRender;
	}

	@Override
	public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
		if (model != null) {
			try
			{
				model.setLivingAnimations(entityCaps, limbSwing,limbSwingAmount,partialTick);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		isAlphablend = true;
	}

	@Override
	public void render(T par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		if (model == null) {
			isAlphablend = false;
			return;
		}
		if (isAlphablend) {
		/*	if (LittleMaidReengaged.cfg_isModelAlphaBlend) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			} else {*/
				GL11.glDisable(GL11.GL_BLEND);
			//}
		}
		if (textures.length > 2 && textures[2] != null) {
			// Actors用
			model.setRotationAngles(par2, par3, par4, par5, par6, par7, entityCaps);
			// Face
			// TODO テクスチャのロードはなんか考える。
			Minecraft.getInstance().getTextureManager().bindTexture(textures[2]);
			model.setCapsValue(caps_renderFace, entityCaps, par2, par3, par4, par5, par6, par7, isRendering);
			// Body
			Minecraft.getInstance().getTextureManager().bindTexture(textures[0]);
			model.setCapsValue(caps_renderBody, entityCaps, par2, par3, par4, par5, par6, par7, isRendering);
		} else {
			// 通常
			if (textures.length > 0 && textures[0] != null) {
				Minecraft.getInstance().getTextureManager().bindTexture(textures[0]);
			}
			model.render(entityCaps, par2, par3, par4, par5, par6, par7, isRendering);
		}
		isAlphablend = false;
		if (textures.length > 1 && textures[1] != null && renderCount == 0) {
			// 発光パーツ
			Minecraft.getInstance().getTextureManager().bindTexture(textures[1]);
			float var4 = 1.0F;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			GL11.glDepthFunc(GL11.GL_LEQUAL);

			RendererHelper.setLightmapTextureCoords(0x00f000f0);//61680
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
			model.render(entityCaps, par2, par3, par4, par5, par6, par7, true);

			RendererHelper.setLightmapTextureCoords(lighting);

//			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			//GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDepthMask(true);
		}
//		textures = blanks;
		renderCount++;
	}


	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		if (model != null) {
			model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor,entityCaps);
		}
	}



	// IModelMMM追加分

	@Override
	public void renderItems(LivingEntity pEntity, MobRenderer pRender) {
		if (model != null) {
			model.renderItems(entityCaps);
		}
	}

	@Override
	public void showArmorParts(int pParts) {
		if (model != null) {
			model.showArmorParts(pParts, 0);
		}
	}

	/**
	 * Renderer辺でこの変数を設定する。
	 * 設定値はIModelCapsを継承したEntitiyとかを想定。
	 */
	@Override
	public void setEntityCaps(IModelCaps pEntityCaps) {
		entityCaps = pEntityCaps;
		if (capsLink != null) {
			capsLink.setEntityCaps(pEntityCaps);
		}
	}

	@Override
	public void setRender(MobRenderer pRender) {
		if (model != null) {
			model.render = pRender;
		}
	}

	@Override
	public void setArmorRendering(boolean pFlag) {
		isRendering = pFlag;
	}


	// IModelCaps追加分

	@Override
	public Map<String, Integer> getModelCaps() {
		return model == null ? null : model.getModelCaps();
	}

	@Override
	public Object getCapsValue(int pIndex, Object ... pArg) {
		return model == null ? null : model.getCapsValue(pIndex, pArg);
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		if (capsLink != null) {
			capsLink.setCapsValue(pIndex, pArg);
		}
		if (model != null) {
			model.setCapsValue(pIndex, pArg);
		}
		return false;
	}

	@Override
	public void showAllParts() {
		if (model != null) {
			model.showAllParts();
		}
	}


	@Override
	public float[] getArmorModelsSize() {
		return new float[0];
	}
}
