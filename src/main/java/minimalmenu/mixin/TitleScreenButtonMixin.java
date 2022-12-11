package minimalmenu.mixin;

import java.util.function.Supplier;

import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenButtonMixin extends Screen {
    protected TitleScreenButtonMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("HEAD"))
    protected void init(CallbackInfo info) {
        if (ConfigHandler.ADD_FOLDER_TS) {
            this.addDrawableChild(new ButtonWidget(this.width / 2 + 104, (this.height / 4 + 48) + 84 , 20, 20, Text.translatable("minimalmenu.common..minecraft"), (button) -> {
                MinimalMenu.processButtonFolderClick(client);

            }, Supplier::get));
        }
    }


}
