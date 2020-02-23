package mmr.maidmodredo.client.render.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.client.model.item.StuffModel;
import mmr.maidmodredo.init.LittleItems;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MageStuffItemRender extends ItemStackTileEntityRenderer {
    private final static ResourceLocation STUFF_TEXTURE = new ResourceLocation(MaidModRedo.MODID, "textures/entity/stuff/mage_stuff.png");


    private final StuffModel modelStuff = new StuffModel(); // TODO model rockshroom

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);

        if (itemStackIn.getItem() == LittleItems.MAGE_STUFF) {
            matrixStackIn.push();
            matrixStackIn.scale(1.0F, -1.0F, -1.0F);
            IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelStuff.getRenderType(STUFF_TEXTURE), true, itemStackIn.hasEffect());
            this.modelStuff.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);

            matrixStackIn.pop();
        }
    }

}