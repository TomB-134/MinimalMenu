package minimalmenu.mixin;

import minimalmenu.MinimalMenu;
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
        if (MinimalMenu.getConfigHandler().DO_REALMS_REMOVAL) {
            for (AbstractButtonWidget button : this.buttons) {
                if (button instanceof OptionButtonWidget && buttons.indexOf(button) == 1) {
                    button.setWidth(0);
                    button.setMessage(new TranslatableText(""));
                }
            }
        }
    }
}
