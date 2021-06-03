package minimalmenu.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.Text;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void init(CallbackInfo info) {
        if (ConfigHandler.DEV_MODE) {
            MinimalMenu.printButtonInfo(this, this.buttons);
        }

        if (ConfigHandler.REMOVE_REALMS_NOTIF) {
            for (AbstractButtonWidget button : this.buttons) {
                if (this.client.world == null) {
                    if (MinimalMenu.buttonMatchesKey(button, "options.realmsNotifications")) {
                        button.visible = false;
                    }
                    if (MinimalMenu.buttonMatchesKey(button, "options.fov")) {
                        button.setWidth(310);
                    }
                }
            }
        }
    }
}
