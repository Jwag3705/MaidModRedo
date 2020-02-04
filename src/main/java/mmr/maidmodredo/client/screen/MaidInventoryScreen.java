package mmr.maidmodredo.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.inventory.MaidInventoryContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MaidInventoryScreen extends ContainerScreen<MaidInventoryContainer> {

    private static final ResourceLocation MAID_INVENTORY = new ResourceLocation(MaidModRedo.MODID, "textures/gui/maid_inventory.png");
    /**
     * The player inventory bound to this GUI.
     */
    private final PlayerInventory player;
    private MaidInventoryContainer maidinventory;
    private float mousePosx;
    private float mousePosY;
    public ChangePageButton txbutton[] = new ChangePageButton[4];

    public MaidInventoryScreen(MaidInventoryContainer maidinventory, PlayerInventory playerInventory, ITextComponent p_i51105_3_) {
        super(maidinventory, playerInventory, p_i51105_3_);
        this.ySize = 222;
        this.player = playerInventory;
        this.passEvents = false;
        this.maidinventory = maidinventory;
    }

    @Override
    public void init() {
        super.init();

        txbutton[0] = this.addButton(new ChangePageButton(guiLeft + 25, guiTop + 17, false, (p_214158_1_) -> {
            maidinventory.getLittleMaidEntity().setNextTexturePackege(0);
            maidinventory.getLittleMaidEntity().setTextureNames();
            maidinventory.getLittleMaidEntity().syncModelNames();
        }, true));
        txbutton[1] = this.addButton(new ChangePageButton(guiLeft + 55, guiTop + 17, true, (p_214158_1_) -> {
            maidinventory.getLittleMaidEntity().setPrevTexturePackege(0);
            maidinventory.getLittleMaidEntity().setTextureNames();
            maidinventory.getLittleMaidEntity().syncModelNames();
        }, true));
        txbutton[2] = this.addButton(new ChangePageButton(guiLeft + 25, guiTop + 57, false, (p_214158_1_) -> {
            maidinventory.getLittleMaidEntity().setNextTexturePackege(1);
            maidinventory.getLittleMaidEntity().setTextureNames();
            maidinventory.getLittleMaidEntity().syncModelNames();
        }, true));
        txbutton[3] = this.addButton(new ChangePageButton(guiLeft + 55, guiTop + 57, true, (p_214158_1_) -> {
            maidinventory.getLittleMaidEntity().setPrevTexturePackege(1);
            maidinventory.getLittleMaidEntity().setTextureNames();
            maidinventory.getLittleMaidEntity().syncModelNames();
        }, true));

        int buttonPosX = ((this.width - this.xSize) / 2) + 102;

        int buttonPosY = ((this.height - this.ySize) / 2) + 41;

    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
            return true;
        } else {
            switch (p_keyPressed_1_) {
                case 266:
                    txbutton[0].onPress();
                    return true;
                case 267:
                    txbutton[1].onPress();
                    return true;
                default:
                    return false;
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int xMouse, int yMouse) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.getMinecraft().getTextureManager().bindTexture(MAID_INVENTORY);

        int originPosX = (this.width - this.xSize) / 2;
        int originPosY = (this.height - this.ySize) / 2;
        this.blit(originPosX, originPosY, 0, 0, this.xSize, this.ySize);

        int entityPosX = (originPosX + 51);
        int entityPosY = (originPosY + 60);
        InventoryScreen.func_228187_a_(entityPosX, entityPosY, 25, (float) (entityPosX - xMouse), (float) ((entityPosY / 2) - yMouse), this.maidinventory.getLittleMaidEntity());
    }

    private void func_214130_a(int p_214130_1_, int p_214130_2_) {
        this.minecraft.getTextureManager().bindTexture(MAID_INVENTORY);

        int i = (int) this.container.getLittleMaidEntity().xpBarCap();

        //XP bar
        blit(p_214130_1_ + 180, p_214130_2_ + 26, this.getBlitOffset(), 0.0F, 237.0F, 102, 15, 256, 256);
        blit(p_214130_1_ + 180, p_214130_2_ + 29, this.getBlitOffset(), 0.0F, 227.0F, 102, 5, 256, 256);
        if (i > 0) {
            int f = (int) (100 * (this.container.getLittleMaidEntity().experience));
            blit(p_214130_1_ + 180, p_214130_2_ + 29, this.getBlitOffset(), 0.0F, 232.0F, f + 1, 5, 256, 256);
        }

        //Job and Level info
        String ls1 = maidinventory.getLittleMaidEntity().getMaidData().getJob().toString();

        int ltw1 = this.minecraft.fontRenderer.getStringWidth(ls1);

        drawString(this.minecraft.fontRenderer, ls1, p_214130_1_ + 135 - ltw1 / 2, p_214130_2_ + 28, -1);

        String ls2 = "Level" + maidinventory.getLittleMaidEntity().getMaidData().getLevel();

        int ltw2 = this.minecraft.fontRenderer.getStringWidth(ls2);

        drawString(this.minecraft.fontRenderer, ls2, p_214130_1_ + 135 - ltw2 / 2, p_214130_2_ + 38, -1);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int xMouse, int yMouse) {
        ITextComponent namemaid = this.maidinventory.getLittleMaidEntity().getName();
        this.font.drawString(namemaid.getString(), this.xSize / 2 - this.font.getStringWidth(namemaid.getString()) / 2, 6, 4210752);
        this.font.drawString(this.player.getDisplayName().getUnformattedComponentText(), 8, 128, 4210752);
    }


    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        this.mousePosx = (float) mouseX;
        this.mousePosY = (float) mouseY;
        super.render(mouseX, mouseY, partialTicks);

        int ii = mouseX - guiLeft;
        int jj = mouseY - guiTop;

        GlStateManager.pushMatrix();
        this.func_214130_a(guiLeft, guiTop);

        GlStateManager.popMatrix();

        if (maidinventory.getLittleMaidEntity().canChangeModel() && ii > 7 && ii < 96 && jj > 7 && jj < 70) {
            // ボタンの表示
            txbutton[0].visible = true;
            txbutton[1].visible = true;
            txbutton[2].visible = true;
            txbutton[3].visible = true;

            // テクスチャ名称の表示
            GL11.glPushMatrix();
            GL11.glTranslatef(mouseX - ii, mouseY - jj, 0.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//			RenderHelper.disableStandardItemLighting();

            if (maidinventory.getLittleMaidEntity().textureData.textureBox[0] != null) {
                String ls1 = maidinventory.getLittleMaidEntity().textureData.getTextureName(0);
                String ls2 = maidinventory.getLittleMaidEntity().textureData.getTextureName(1);
                int ltw1 = this.minecraft.fontRenderer.getStringWidth(ls1);
                int ltw2 = this.minecraft.fontRenderer.getStringWidth(ls2);
                int ltwmax = (ltw1 > ltw2) ? ltw1 : ltw2;
                int lbx = 52 - ltwmax / 2;
                int lby = 68;
                int lcolor;
                lcolor = jj < 20 ? 0xc0882222 : 0xc0000000;
                GlStateManager.disableLighting();
                GlStateManager.disableDepthTest();
                GlStateManager.colorMask(true, true, true, false);
                fillGradient(lbx - 3, lby - 4, lbx + ltwmax + 3, lby + 8, lcolor, lcolor);
                drawString(this.minecraft.fontRenderer, ls1, 52 - ltw1 / 2, lby - 2, -1);
                lcolor = jj > 46 ? 0xc0882222 : 0xc0000000;
                fillGradient(lbx - 3, lby + 8, lbx + ltwmax + 3, lby + 16 + 4, lcolor, lcolor);
                drawString(this.minecraft.fontRenderer, ls2, 52 - ltw2 / 2, lby + 10, -1);
                GlStateManager.enableLighting();
                GlStateManager.enableDepthTest();
                GlStateManager.colorMask(true, true, true, true);
            }
            GL11.glPopMatrix();
        } else {
            txbutton[0].visible = false;
            txbutton[1].visible = false;
            txbutton[2].visible = false;
            txbutton[3].visible = false;
        }
        /*if (ii > 25 && ii < 79 && jj > 24 && jj < 44) {
            selectbutton.visible = true;
        } else {
            selectbutton.visible = false;
        }*/

        this.renderHoveredToolTip(mouseX, mouseY);
    }

}