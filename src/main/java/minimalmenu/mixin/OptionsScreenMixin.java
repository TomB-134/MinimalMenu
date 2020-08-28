package minimalmenu.mixin;

import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {
    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    protected void init(CallbackInfo info) {
        if (ConfigHandler.DEV_MODE) {
            for (AbstractButtonWidget button : this.buttons) {
                MinimalMenu.printButtonInfo(button, this.buttons);
            }
        }

        if (ConfigHandler.REMOVE_REALMS_NOTIF) {
            for (AbstractButtonWidget button : this.buttons) {
                if (buttons.indexOf(button) == 1 && this.client.world == null) {
                    button.visible = false;
                }
                else if (buttons.indexOf(button) == 0 && this.client.world == null) {
                    button.setWidth(310);
                }
            }
        }
    }
}
