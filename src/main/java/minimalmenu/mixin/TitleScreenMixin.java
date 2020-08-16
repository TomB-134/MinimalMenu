package minimalmenu.mixin;

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
                System.out.println(buttonWidget.getMessage().getString());
                if(this.buttons.indexOf(buttonWidget) == 2) { //Hide realms button.
                    buttonWidget.setWidth(0);
                    buttonWidget.setMessage(new TranslatableText(""));
                }
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
