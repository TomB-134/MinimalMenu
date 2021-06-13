package minimalmenu.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends ScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    protected void init(CallbackInfo info) {
        if (ConfigHandler.REMOVE_REALMS_NOTIF) {
            for (ClickableWidget button : Screens.getButtons((Screen)(Object)this)) {
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
