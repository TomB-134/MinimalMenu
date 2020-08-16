package minimalmenu.mixin;

import minimalmenu.MinimalMenu;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    protected void init(CallbackInfo info) {
        for (AbstractButtonWidget buttonWidget : this.buttons) { //Remove the stupid buttons.
            if (buttonWidget instanceof TexturedButtonWidget) {
                buttonWidget.setWidth(0);
            }
        }

        for (AbstractButtonWidget buttonWidget : this.buttons) {
            if (buttonWidget instanceof ButtonWidget) {
                if(this.buttons.indexOf(buttonWidget) == 2) { //Hide realms button.
                    buttonWidget.setWidth(0);
                    buttonWidget.setMessage(new TranslatableText(""));
                }
                if(this.buttons.indexOf(buttonWidget) == 3 && MinimalMenu.getIsModInstalled("modmenu")) {
                    buttonWidget.y-=24;
                }

                if (!MinimalMenu.getIsModInstalled("modmenu")) {
                    if (buttons.indexOf(buttonWidget) == 4 || buttons.indexOf(buttonWidget) == 5) {
                        buttonWidget.y-=24;
                    }
                } else {
                    if (buttons.indexOf(buttonWidget) == 5 || buttons.indexOf(buttonWidget) == 6) {
                        buttonWidget.y-=24;
                    }
                }

                buttonWidget.y+=24; //Move all down
            }
        }
    }

    //Removes realms notifications from the screen.
    @Inject(at = @At("HEAD"), method = "init()V")
    protected void setRealmsNotificationsToFalse(CallbackInfo info) {
        assert this.client != null;
        this.client.options.realmsNotifications = false;
    }
}
