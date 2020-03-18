package mmr.littledelicacies.client.screen;


import mmr.littledelicacies.entity.LittleMaidBaseEntity;
import mmr.littledelicacies.network.MaidPacketHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class EditMaidNameScreen extends Screen {
    protected final LittleMaidBaseEntity littleMaid;
    private TextFieldWidget nameTextField;

    protected EditMaidNameScreen(LittleMaidBaseEntity entity) {
        super(new TranslationTextComponent("gui.littledelicacies.edit_maidname"));
        this.littleMaid = entity;
    }

    @Override
    public void init() {
        this.buttons.clear();
        this.children.clear();
        super.init();

        this.minecraft.keyboardListener.enableRepeatEvents(true);
        int topX = this.width / 2;
        int topY = this.height / 2;
        nameTextField = new TextFieldWidget(this.font, topX - 100, this.height / 2 + 10, 200, 20, "TEST");
        nameTextField.setResponder(text -> {
            if (!text.isEmpty()) {
                MaidPacketHandler.syncMaidName(littleMaid, text);
            }
        });
        nameTextField.setFocused2(false);
        nameTextField.setMaxStringLength(32);

        this.addButton(nameTextField);

        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, I18n.format("gui.done"), (p_214266_1_) -> {
            this.onClose();
        }));
    }


}
