package mmr.littledelicacies.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import mmr.littledelicacies.client.maidmodel.TextureBox;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.utils.ModelManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ModelSelectScreen extends Screen {
    private ModelSelectScreen.List list;
    protected final LittleMaidBaseEntity littleMaid;
    private Button confirmSettingsBtn;

    protected ModelSelectScreen(LittleMaidBaseEntity entity) {
        super(new TranslationTextComponent("gui.littledelicacies.model_select"));
        this.littleMaid = entity;
    }

    protected void init() {
        this.list = new ModelSelectScreen.List(this.minecraft);
        this.children.add(this.list);
        this.confirmSettingsBtn = this.addButton(new Button(this.width / 2 - 155 + 160, this.height - 38, 150, 20, I18n.format("gui.done"), (p_213036_1_) -> {
            ModelSelectScreen.List.MaidModelEntry maidJobscreen$list$maidJobentry = this.list.getSelected();
            if (maidJobscreen$list$maidJobentry != null) {

                this.confirmSettingsBtn.setMessage(I18n.format("gui.done"));
            }
            this.onClose();
        }));
        super.init();
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.list.render(p_render_1_, p_render_2_, p_render_3_);
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 16, 16777215);

        MatrixStack matrixstack = new MatrixStack();
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();

        Minecraft.getInstance().getRenderManager().renderEntityStatic(littleMaid, 100, this.width / 1.2, 0.0D, 0.0F, 1.0F, matrixstack, irendertypebuffer$impl, 15728880);

        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    class List extends ExtendedList<ModelSelectScreen.List.MaidModelEntry> {
        public List(Minecraft mcIn) {
            super(mcIn, ModelSelectScreen.this.width, ModelSelectScreen.this.height, 32, ModelSelectScreen.this.height - 65 + 4, 18);

            ModelManager.getTextureList().forEach((p_229396_0_) -> {
                ModelSelectScreen.List.MaidModelEntry maidJobscreen$list$maidJobentry = new ModelSelectScreen.List.MaidModelEntry(p_229396_0_);
                if (p_229396_0_.getModelEntity() == ModelSelectScreen.this.littleMaid.getClass()) {
                    this.addEntry(maidJobscreen$list$maidJobentry);
                }

                if (ModelSelectScreen.this.littleMaid.textureData.textureBox[0] == p_229396_0_) {
                    this.setSelected(maidJobscreen$list$maidJobentry);
                }
            });

            if (this.getSelected() != null) {
                this.centerScrollOn(this.getSelected());
            }

        }

        protected int getScrollbarPosition() {
            return super.getScrollbarPosition() + 20;
        }

        public int getRowWidth() {
            return super.getRowWidth() + 50;
        }

        public void setSelected(@Nullable ModelSelectScreen.List.MaidModelEntry p_setSelected_1_) {
            super.setSelected(p_setSelected_1_);
            if (p_setSelected_1_ != null) {
                NarratorChatListener.INSTANCE.say((new TranslationTextComponent("narrator.select", p_setSelected_1_.field_214398_b)).getString());
            }

            littleMaid.textureData.textureBox[0] = p_setSelected_1_.field_214398_b;
            littleMaid.setTextureNames();
            littleMaid.syncModelNames();

        }

        protected void renderBackground() {
            ModelSelectScreen.this.renderBackground();
        }

        protected boolean isFocused() {
            return ModelSelectScreen.this.getFocused() == this;
        }

        @OnlyIn(Dist.CLIENT)
        public class MaidModelEntry extends ExtendedList.AbstractListEntry<MaidModelEntry> {
            private final TextureBox field_214398_b;

            public MaidModelEntry(TextureBox p_i50494_2_) {
                this.field_214398_b = p_i50494_2_;
            }

            public void render(int p_render_1_, int p_render_2_, int p_render_3_, int p_render_4_, int p_render_5_, int p_render_6_, int p_render_7_, boolean p_render_8_, float p_render_9_) {
                ModelSelectScreen.this.font.setBidiFlag(true);
                List.this.drawCenteredString(ModelSelectScreen.this.font, this.field_214398_b.modelName, List.this.width / 2, p_render_2_ + 1, 16777215);

                //JobSelectScreen.this.font.setBidiFlag(JobSelectScreen.this.maidJobManager.getCurrentMaidJob().isBidirectional());

            }

            public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
                if (p_mouseClicked_5_ == 0) {
                    this.func_214395_a();
                    return true;
                } else {
                    return false;
                }
            }

            private void func_214395_a() {
                List.this.setSelected(this);
            }
        }
    }

}