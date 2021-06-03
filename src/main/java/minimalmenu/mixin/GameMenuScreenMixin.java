package minimalmenu.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.Text;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgets", at = @At("TAIL"))
    private void initWidgets(CallbackInfo info) {
        if (ConfigHandler.DEV_MODE) {
            MinimalMenu.printButtonInfo(this, this.buttons);
        }

        final int buttonWidth = 204;
        final int spacing = 24;
        int yOffset = 0;
        boolean removeLan = this.client.isInSingleplayer() ? ConfigHandler.REMOVE_LANSP : ConfigHandler.REMOVE_LANMP;
        for (AbstractButtonWidget button : this.buttons) {
            if (removeLan) {
                if (MinimalMenu.buttonMatchesKey(button, "menu.shareToLan")) {
                    button.visible = false;
                }
                if (MinimalMenu.buttonMatchesKey(button, "menu.options")) {
                    button.setWidth(buttonWidth);
                    button.x = this.width / 2 - buttonWidth / 2;
                }
            }

            if (ConfigHandler.REMOVE_FEEDBACK) {
                if (MinimalMenu.buttonMatchesKey(button, "menu.sendFeedback")) {
                    button.visible = false;
                }
                if (!ConfigHandler.REMOVE_BUGS) {
                    if (MinimalMenu.buttonMatchesKey(button, "menu.reportBugs")) {
                        button.setWidth(buttonWidth);
                        button.x = this.width / 2 - buttonWidth / 2;
                    }
                }
            }

            if (ConfigHandler.REMOVE_BUGS) {
                if (MinimalMenu.buttonMatchesKey(button, "menu.reportBugs")) {
                    button.visible = false;
                    if (ConfigHandler.REMOVE_FEEDBACK) {
                        yOffset += spacing;
                    }
                }
                if (!ConfigHandler.REMOVE_FEEDBACK) {
                    if (MinimalMenu.buttonMatchesKey(button, "menu.sendFeedback")) {
                        button.setWidth(buttonWidth);
                        button.x = this.width / 2 - buttonWidth / 2;
                    }
                }
            }
            
            button.x -= ConfigHandler.X_OFFSET_PAUSE;
            button.y -= ConfigHandler.Y_OFFSET_PAUSE;
            button.y -= yOffset;
        }
    }
}
