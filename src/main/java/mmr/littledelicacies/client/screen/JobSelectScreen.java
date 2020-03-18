package mmr.littledelicacies.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.init.MaidJob;
import mmr.littledelicacies.network.MaidPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class JobSelectScreen extends Screen {
    private JobSelectScreen.List list;
    protected final LittleMaidBaseEntity littleMaid;
    private Button confirmSettingsBtn;

    protected JobSelectScreen(LittleMaidBaseEntity entity) {
        super(new TranslationTextComponent("gui.littledelicacies.job_select"));
        this.littleMaid = entity;
    }

    protected void init() {
        this.list = new JobSelectScreen.List(this.minecraft);
        this.children.add(this.list);
        this.confirmSettingsBtn = this.addButton(new Button(this.width / 2 - 155 + 160, this.height - 38, 150, 20, I18n.format("gui.done"), (p_213036_1_) -> {
            JobSelectScreen.List.MaidJobEntry maidJobscreen$list$maidJobentry = this.list.getSelected();
            if (maidJobscreen$list$maidJobentry != null && maidJobscreen$list$maidJobentry.field_214398_b != this.littleMaid.getMaidData().getJob()) {
                MaidPacketHandler.syncMaidJob(littleMaid, maidJobscreen$list$maidJobentry.field_214398_b);
                /*this.maidJobManager.setCurrentMaidJob(maidJobscreen$list$maidJobentry.field_214398_b);
                this.gameSettings.maidJob = maidJobscreen$list$maidJobentry.field_214398_b.getCode();
                net.minecraftforge.client.ForgeHooksClient.refreshResources(this.minecraft, net.minecraftforge.resource.VanillaResourceType.LANGUAGES);
                this.font.setBidiFlag(this.maidJobManager.isCurrentMaidJobBidirectional());*/
                this.confirmSettingsBtn.setMessage(I18n.format("gui.done"));
            }
            this.onClose();
        }));
        super.init();
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.list.render(p_render_1_, p_render_2_, p_render_3_);
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 16, 16777215);
        this.drawCenteredString(this.font, "(" + I18n.format("gui.littledelicacies.job_select.warning") + ")", this.width / 2, this.height - 56, 8421504);
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    class List extends ExtendedList<JobSelectScreen.List.MaidJobEntry> {
        public List(Minecraft mcIn) {
            super(mcIn, JobSelectScreen.this.width, JobSelectScreen.this.height, 32, JobSelectScreen.this.height - 65 + 4, 18);

            MaidJob.MAID_JOB_REGISTRY.forEach((p_229396_0_) -> {
                JobSelectScreen.List.MaidJobEntry maidJobscreen$list$maidJobentry = new JobSelectScreen.List.MaidJobEntry(p_229396_0_);
                if (JobSelectScreen.this.littleMaid.getMaidData().getLevel() >= p_229396_0_.getNeedLevel() && !JobSelectScreen.this.littleMaid.getMaidData().getJob().isLockJob() ||
                        JobSelectScreen.this.littleMaid.getMaidData().getJob().isLockJob() && JobSelectScreen.this.littleMaid.getMaidData().getLevel() == 0 && JobSelectScreen.this.littleMaid.getMaidData().getLevel() >= p_229396_0_.getNeedLevel() ||
                        JobSelectScreen.this.littleMaid.getMaidData().getLevel() >= p_229396_0_.getNeedLevel() && JobSelectScreen.this.littleMaid.getMaidData().getJob().isLockJob() && p_229396_0_.isLockJob()) {
                    if (p_229396_0_ != MaidJob.WILD) {
                        this.addEntry(maidJobscreen$list$maidJobentry);
                    }
                }
                if (JobSelectScreen.this.littleMaid.getMaidData().getJob() == p_229396_0_) {
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

        public void setSelected(@Nullable JobSelectScreen.List.MaidJobEntry p_setSelected_1_) {
            super.setSelected(p_setSelected_1_);
            if (p_setSelected_1_ != null) {
                NarratorChatListener.INSTANCE.say((new TranslationTextComponent("narrator.select", p_setSelected_1_.field_214398_b)).getString());
            }

        }

        protected void renderBackground() {
            JobSelectScreen.this.renderBackground();
        }

        protected boolean isFocused() {
            return JobSelectScreen.this.getFocused() == this;
        }

        @OnlyIn(Dist.CLIENT)
        public class MaidJobEntry extends ExtendedList.AbstractListEntry<JobSelectScreen.List.MaidJobEntry> {
            private final MaidJob field_214398_b;

            public MaidJobEntry(MaidJob p_i50494_2_) {
                this.field_214398_b = p_i50494_2_;
            }

            public void render(int p_render_1_, int p_render_2_, int p_render_3_, int p_render_4_, int p_render_5_, int p_render_6_, int p_render_7_, boolean p_render_8_, float p_render_9_) {
                JobSelectScreen.this.font.setBidiFlag(true);
                List.this.drawCenteredString(JobSelectScreen.this.font, this.field_214398_b.toString(), List.this.width / 2, p_render_2_ + 1, 16777215);
                if (this.field_214398_b.isLockJob()) {
                    List.this.drawCenteredString(JobSelectScreen.this.font, "(Combat Job)", (int) (List.this.width / 1.5), p_render_2_ + 1, 16777215);
                }
                drawItemStack(this.field_214398_b.getRequireItem(), (int) (List.this.width / 3), p_render_2_, 1.0F);

                if (this.field_214398_b.getSubRequireItem() != null) {
                    drawItemStack(this.field_214398_b.getSubRequireItem(), (int) (List.this.width / 2.65), p_render_2_, 1.0F);
                }
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

    private void drawItemStack(ItemStack stack, int x, int y, float scale) {

        RenderSystem.pushMatrix();
        RenderSystem.scalef(scale, scale, scale);
        RenderSystem.translatef(0, 0, 32.0F);

        this.itemRenderer.zLevel = 200.0F;

        this.itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
        this.itemRenderer.zLevel = 0.0F;
        RenderSystem.popMatrix();
    }
}