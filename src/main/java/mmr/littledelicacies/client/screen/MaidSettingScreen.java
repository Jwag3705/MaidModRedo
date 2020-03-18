package mmr.littledelicacies.client.screen;

import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class MaidSettingScreen extends Screen {
    private JobSelectScreen.List list;
    protected final LittleMaidBaseEntity littleMaid;
    private Button jobselect;
    private Button nameSet;
    private Button confirmSettingsBtn;

    protected MaidSettingScreen(LittleMaidBaseEntity entity) {
        super(new TranslationTextComponent("gui.littledelicacies.maidsetting"));
        this.littleMaid = entity;
    }

    protected void init() {
        this.jobselect = this.addButton(new Button(this.width / 2 - 75, this.height - 140, 150, 20, I18n.format("gui.littledelicacies.job_select"), (p_213036_1_) -> {
            this.jobselect.setMessage(I18n.format("gui.littledelicacies.job_select"));

            this.getMinecraft().displayGuiScreen(new JobSelectScreen(this.littleMaid));
        }));

        this.nameSet = this.addButton(new Button(this.width / 2 - 75, this.height - 100, 150, 20, I18n.format("gui.littledelicacies.edit_maidname"), (p_213036_1_) -> {
            this.nameSet.setMessage(I18n.format("gui.littledelicacies.edit_maidname"));

            this.getMinecraft().displayGuiScreen(new EditMaidNameScreen(this.littleMaid));
        }));

        this.confirmSettingsBtn = this.addButton(new Button(this.width / 2 - 75, this.height - 60, 150, 20, I18n.format("gui.done"), (p_213036_1_) -> {
            this.confirmSettingsBtn.setMessage(I18n.format("gui.done"));

            this.onClose();
        }));
        super.init();
    }
}
