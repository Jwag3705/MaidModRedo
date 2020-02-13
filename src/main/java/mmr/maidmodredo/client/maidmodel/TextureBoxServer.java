package mmr.maidmodredo.client.maidmodel;


public class TextureBoxServer extends TextureBoxBase {

	// ローカルでモデルを保持している時にリンク
	protected TextureBox localBox;


	public TextureBoxServer() {
	}

	public TextureBoxServer(TextureBox pBox) {
		modelEntity = pBox.modelEntity;
		localBox		= pBox;
		contractColor	= pBox.getContractColorBits();
		wildColor		= pBox.getWildColorBits();
		textureName		= pBox.textureName;
		isUpdateSize = (pBox.models != null && pBox.models[0] != null) ?
				true : false;
/*
		if (pBox.models != null) {
			modelHeight			= pBox.models[0].getHeight(null);
			modelWidth			= pBox.models[0].getWidth(null);
			modelYOffset		= pBox.models[0].getyOffset(null);
			modelMountedYOffset	= pBox.models[0].getMountedYOffset(null);
		}
*/
	}

	/*
	 * MMM_Statics.Server_GetTextureIndex
	 */
	public void setValue(byte[] pData) {
		//TODO
	/*	contractColor		= NetworkHelper.getShortFromPacket(pData, 2);
		wildColor			= NetworkHelper.getShortFromPacket(pData, 4);
		modelHeight			= NetworkHelper.getFloatFromPacket(pData, 6);
		modelWidth			= NetworkHelper.getFloatFromPacket(pData, 10);
		modelYOffset		= NetworkHelper.getFloatFromPacket(pData, 14);
		modelMountedYOffset	= NetworkHelper.getFloatFromPacket(pData, 18);
		textureName			= NetworkHelper.getStrFromPacket(pData, 22);*/
	}

	@Override
	public float getHeight() {
		return localBox != null ? localBox.models[0].getHeight() : modelHeight;
	}

	@Override
	public float getWidth() {
		return localBox != null ? localBox.models[0].getWidth() : modelWidth;
	}

	@Override
	public float getYOffset() {
		return localBox != null ? localBox.models[0].getyOffset() : modelYOffset;
	}

	@Override
	public float getMountedYOffset() {
		return localBox != null ? localBox.models[0].getMountedYOffset() : modelMountedYOffset;
	}

}
