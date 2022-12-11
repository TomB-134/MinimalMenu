package minimalmenu.mixin;

import java.util.function.Supplier;

import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgets", at = @At("HEAD"))
    private void initWidgets(CallbackInfo info) {
        if (ConfigHandler.ADD_FOLDER_PS) {
            this.addDrawableChild(new ButtonWidget(this.width / 2 + 104, this.height / 4 + 120 + -16, 20, 20, Text.translatable("minimalmenu.common..minecraft"), (button) -> {
                MinimalMenu.processButtonFolderClick(client);
            }, Supplier::get));
        }
    }
}
