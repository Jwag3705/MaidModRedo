package mmr.littledelicacies.client.maidmodel;


import mmr.littledelicacies.entity.LittleMaidBaseEntity;

public class ModelLittleMaid_Orign<T extends LittleMaidBaseEntity> extends ModelLittleMaidBase<T> {

	/**
	 * コンストラクタは全て継承させること
	 */
	public ModelLittleMaid_Orign() {
		super();
	}
	/**
	 * コンストラクタは全て継承させること
	 */
	public ModelLittleMaid_Orign(float psize) {
		super(psize);
	}
	/**
	 * コンストラクタは全て継承させること
	 */
	public ModelLittleMaid_Orign(float psize, float pyoffset, int pTextureWidth, int pTextureHeight) {
		super(psize, pyoffset, pTextureWidth, pTextureHeight);
	}


	@Override
	public String getUsingTexture() {
		return "default";
	}

}
