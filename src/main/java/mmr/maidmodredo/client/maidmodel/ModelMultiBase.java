package mmr.maidmodredo.client.maidmodel;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

/**
 * マルチモデル用の基本クラス、これを継承していればマルチモデルとして使用できる。
 * Mincraftネイティブなクラスや継承関数などを排除して、難読化対策を行う。
 * 継承クラスではなくなったため、直接的な互換性はない。
 */
public abstract class ModelMultiBase<T extends LivingEntity> extends ModelBase<T> {

    public ModelRenderer mainFrame;
    public ModelRenderer HeadMount;
    public ModelRenderer HeadTop;
    public ModelRenderer Arms[];
    public ModelRenderer HardPoint[];

    public float entityIdFactor;
    public int entityTicksExisted;
    // 変数である意味ない？
    public float scaleFactor = 0.9375F;
    /**
     * モデルが持っている機能群
     */


    public ModelMultiBase() {
        this(0.0F);
    }

    public ModelMultiBase(float pSizeAdjust) {
        this(pSizeAdjust, 0.0F, 64, 32);
    }

    public ModelMultiBase(float pSizeAdjust, float pYOffset, int pTextureWidth, int pTextureHeight) {
        textureWidth = pTextureWidth;
        textureHeight = pTextureHeight;


//			LittleMaidReengaged.Debug("ModelMulti.InitClient");
        // ハードポイント
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Arms = new ModelRenderer[2];
            HeadMount = new ModelRenderer(this);
            HeadTop = new ModelRenderer(this);
            initModel(pSizeAdjust, pYOffset);
        }

    }

    // 独自定義関数群

    /**
     * モデルの初期化コード
     */
    public abstract void initModel(float psize, float pyoffset);

    /**
     * モデル指定詞に依らずに使用するテクスチャパック名。
     * 一つのテクスチャに複数のモデルを割り当てる時に使う。
     *
     * @return
     */
    public String getUsingTexture() {
        return null;
    }

    /**
     * 身長
     */
    public abstract float getHeight();

    /**
     * 横幅
     */
    @Deprecated
    public abstract float getWidth();

    /**
     * モデルのYオフセット
     */
    public abstract float getyOffset();

    /**
     * 上に乗せる時のオフセット高
     */
    public abstract float getMountedYOffset();


    /**
     * アイテムを持っているときに手を前に出すかどうか。
     */
    public boolean isItemHolder() {
        return false;
    }

    /**
     * 表示すべきすべての部品
     */
    public void showAllParts() {
    }

    ;
    /**
     * 部位ごとの装甲表示。
     *
     * @param parts 3:頭部。
     *              2:胴部。
     *              1:脚部
     *              0:足部
     * @param index 0:inner
     *              1:outer
     * @return 戻り値は基本 -1
     */
    public int showArmorParts(int parts, int index) {
        return -1;
    }

    /**
     * ハードポイントに接続されたアイテムを表示する
     */
    public abstract void renderItems(MatrixStack stack, boolean left);

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

    }

    public static final float mh_sqrt_float(float f) {
        return MathHelper.sqrt(f);
    }

    public static final float mh_sqrt_double(double d) {
        return MathHelper.sqrt(d);
    }

    public static final int mh_floor_float(float f) {
        return MathHelper.floor(f);
    }

    public static final int mh_floor_double(double d) {
        return MathHelper.floor(d);
    }

    public static final long mh_floor_double_long(double d) {
        return MathHelper.floor(d);
    }
}
