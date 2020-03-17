package mmr.littledelicacies.client.maidmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class TextureBoxBase {

	public String textureName;
	protected int contractColor;
	public int wildColor;
	protected float modelHeight;
	protected float modelWidth;
	protected float modelYOffset;
	protected float modelMountedYOffset;
	protected boolean isUpdateSize;
    //Applicable entity for texture
    public Class modelEntity;


	public void setModelSize(float pHeight, float pWidth, float pYOffset, float pMountedYOffset) {
		modelHeight = pHeight;
		modelWidth = pWidth;
		modelYOffset = pYOffset;
		modelMountedYOffset = pMountedYOffset;
	}

	protected byte getRandomColor(int pColor, Random pRand) {
		List<Byte> llist = new ArrayList<Byte>();
		for (byte li = 0; li < 16; li++) {
			if ((pColor & 0x01) > 0) {
				llist.add(li);
			}
			pColor = pColor >>> 1;
		}
		
		if (llist.size() > 0) {
			return llist.get(pRand.nextInt(llist.size()));
		}
		return -1;
	}

	/**
	 * 契約色の有無をビット配列にして返す
	 */
	public int getContractColorBits() {
		return contractColor;
	}

	/**
	 * 野生色の有無をビット配列にして返す
	 */
	public int getWildColorBits() {
		return wildColor;
	}

//	public boolean hasColor(int pIndex, boolean pContract) {
//		return (((pContract ? contractColor : wildColor) >>> pIndex) & 0x01) != 0;
//	}

	/**
	 * 野生のメイドの色をランダムで返す
	 */
	public byte getRandomWildColor(Random pRand) {
		return getRandomColor(getWildColorBits(), pRand);
	}

	/**
	 * 契約のメイドの色をランダムで返す
	 */
	public int getRandomContractColor(Random pRand) {
		return getRandomColor(getContractColorBits(), pRand);
	}

	public float getHeight() {
        return modelHeight;
	}

	public float getWidth() {
        return modelWidth;
	}

	public float getYOffset() {
        return modelYOffset;
	}
	public float getMountedYOffset() {
        return modelMountedYOffset;
	}

    public Class getModelEntity() {
        return modelEntity;
    }
}
