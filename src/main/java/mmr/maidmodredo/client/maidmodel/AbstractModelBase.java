package mmr.maidmodredo.client.maidmodel;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

/**
 * マルチモデル用識別クラス<br>
 * インターフェースでもいいような気がする。
 *
 */
public abstract class AbstractModelBase<T extends LivingEntity> extends SegmentedModel<T> {

	/**
	 * アーマーモデルのサイズを返す。
	 * サイズは内側のものから。
	 */
	public abstract float[] getArmorModelsSize();

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of();
    }

    ;
}
