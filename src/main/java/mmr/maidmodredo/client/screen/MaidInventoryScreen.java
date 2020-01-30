package mmr.maidmodredo.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import mmr.maidmodredo.MaidModRedo;
import mmr.maidmodredo.inventory.MaidInventoryContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MaidInventoryScreen extends ContainerScreen<MaidInventoryContainer> {

    private static final ResourceLocation RES_FRIENDS_INVENTORY = new ResourceLocation(MaidModRedo.MODID, "textures/gui/maid_inventory.png");
    /**
     * The player inventory bound to this GUI.
     */
    private final PlayerInventory player;
    private MaidInventoryContainer friendinventory;
    private float mousePosx;
    private float mousePosY;

    public MaidInventoryScreen(MaidInventoryContainer friendinventory, PlayerInventory playerInventory, ITextComponent p_i51105_3_) {
        super(friendinventory, playerInventory, p_i51105_3_);
        this.ySize = 222;
        this.player = playerInventory;
        this.passEvents = false;
        this.friendinventory = friendinventory;
    }

    @Override
    public void init() {

        super.init();


        int buttonPosX = ((this.width - this.xSize) / 2) + 102;

        int buttonPosY = ((this.height - this.ySize) / 2) + 41;

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int xMouse, int yMouse) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.getMinecraft().getTextureManager().bindTexture(RES_FRIENDS_INVENTORY);

        int originPosX = (this.width - this.xSize) / 2;
        int originPosY = (this.height - this.ySize) / 2;
        this.blit(originPosX, originPosY, 0, 0, this.xSize, this.ySize);

        int entityPosX = (originPosX + 51);
        int entityPosY = (originPosY + 60);
        InventoryScreen.drawEntityOnScreen(entityPosX, entityPosY, 25, (float) (entityPosX - xMouse), (float) ((entityPosY / 2) - yMouse), this.friendinventory.getLittleMaidEntity());
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int xMouse, int yMouse) {
        ITextComponent nameFriend = this.friendinventory.getLittleMaidEntity().getName();
        this.font.drawString(nameFriend.getString(), this.xSize / 2 - this.font.getStringWidth(nameFriend.getString()) / 2, 6, 4210752);
        this.font.drawString(this.player.getDisplayName().getUnformattedComponentText(), 8, 128, 4210752);
    }


    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        this.mousePosx = (float) mouseX;
        this.mousePosY = (float) mouseY;
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

}