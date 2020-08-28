package minimalmenu.mixin;

import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "initWidgets()V")
    private void initWidgets(CallbackInfo info) {
        if (ConfigHandler.DEV_MODE) {
            for (AbstractButtonWidget button : this.buttons) {
                MinimalMenu.printButtonInfo(button, this.buttons);
            }
        }

        for (AbstractButtonWidget buttonWidget : this.buttons) {
            if ((ConfigHandler.REMOVE_LANSP && this.client.isInSingleplayer()) || (ConfigHandler.REMOVE_LANMP && !this.client.isInSingleplayer())) {
                if (buttons.indexOf(buttonWidget) == 5) {
                    buttonWidget.y = buttons.get(8).y;
                }else if (buttons.indexOf(buttonWidget) == 6) {
                    buttonWidget.visible = false;
                } else if (buttons.indexOf(buttonWidget) == 7) {
                    buttonWidget.y -= 24;
                } else if (buttons.indexOf(buttonWidget) == 8) {
                    buttonWidget.setWidth(98);
                    buttonWidget.x = this.width / 2 + 4;
                }
            }

            if (ConfigHandler.REMOVE_FEEDBACK) {
                if (buttons.indexOf(buttonWidget) == 3) {
                    buttonWidget.visible = false;
                }
                if (!ConfigHandler.REMOVE_BUGS && buttons.indexOf(buttonWidget) == 4) {
                    buttonWidget.setWidth(204);
                    buttonWidget.x = this.width / 2 - 102;
                }
            }

            if (ConfigHandler.REMOVE_BUGS) {
                if (buttons.indexOf(buttonWidget) == 4) {
                    buttonWidget.visible = false;
                }
                if (!ConfigHandler.REMOVE_FEEDBACK && buttons.indexOf(buttonWidget) == 3) {
                    buttonWidget.setWidth(204);
                }
            }

            if (ConfigHandler.REMOVE_FEEDBACK && ConfigHandler.REMOVE_BUGS) {
                if (buttons.indexOf(buttonWidget) >= 5) {
                    buttonWidget.y -= 24;
                }
            }

            buttonWidget.x -= ConfigHandler.X_OFFSET_PAUSE;
            buttonWidget.y -= ConfigHandler.Y_OFFSET_PAUSE;
        }
    }
}

