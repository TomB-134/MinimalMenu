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
            if (ConfigHandler.REMOVE_LANSP && buttons.indexOf(buttonWidget) == 6 && this.client.isInSingleplayer()) {
                buttonWidget.visible = false;
            }

            if (ConfigHandler.REMOVE_LANMP && buttons.indexOf(buttonWidget) == 6 && !this.client.isInSingleplayer()) {
                buttonWidget.visible = false;
            }
        }
//        MinimalMenu.getConfigHandler().read();
//        for (AbstractButtonWidget buttonWidget : this.buttons) {
//            boolean doRemoval = false;
//            if (MinimalMenu.getConfigHandler().ONLY_REMOVE_LAN_IF_NOT_CLIENT && !this.client.isInSingleplayer()) {
//                doRemoval = true;
//            }
//            if (!MinimalMenu.getConfigHandler().ONLY_REMOVE_LAN_IF_NOT_CLIENT) {
//                doRemoval = true;
//            }
//
//            if (this.buttons.indexOf(buttonWidget) == 3 && MinimalMenu.getConfigHandler().DO_FEEDBACK_BUTTON_REMOVAL) {
//                buttonWidget.visible = false;
//            }
//            if (this.buttons.indexOf(buttonWidget) == 4 && MinimalMenu.getConfigHandler().DO_REPORT_BUGS_BUTTON_REMOVAL) {
//                buttonWidget.visible = false;
//            }
//
//            if (MinimalMenu.getConfigHandler().DO_REPORT_BUGS_BUTTON_REMOVAL && MinimalMenu.getConfigHandler().DO_FEEDBACK_BUTTON_REMOVAL && MinimalMenu.allInstalledIDS().contains("modmenu")) {
//                if (this.buttons.indexOf(buttonWidget) == 8 || this.buttons.indexOf(buttonWidget) == 7 || this.buttons.indexOf(buttonWidget) == 6 || this.buttons.indexOf(buttonWidget) == 5) {
//                    buttonWidget.y -= 24;
//                }
//
//                if (MinimalMenu.getConfigHandler().DO_CENTER_ADJUSTMENT) {
//                    buttonWidget.y += 24;
//                }
//            }
//
//            if (!MinimalMenu.getConfigHandler().DO_FEEDBACK_BUTTON_REMOVAL && MinimalMenu.getConfigHandler().DO_REPORT_BUGS_BUTTON_REMOVAL && MinimalMenu.allInstalledIDS().contains("modmenu")) {
//                if (this.buttons.indexOf(buttonWidget) == 8) {
//                    buttonWidget.setWidth(98);
//                    buttonWidget.x = this.width / 2 + 4;
//                }
//                if (this.buttons.indexOf(buttonWidget) == 8 || this.buttons.indexOf(buttonWidget) == 7 || this.buttons.indexOf(buttonWidget) == 6 || this.buttons.indexOf(buttonWidget) == 5) {
//                    buttonWidget.y -= 24;
//                }
//                if (MinimalMenu.getConfigHandler().DO_CENTER_ADJUSTMENT) {
//                    buttonWidget.y += 24;
//                }
//            }
//            if (MinimalMenu.getConfigHandler().DO_FEEDBACK_BUTTON_REMOVAL && !MinimalMenu.getConfigHandler().DO_REPORT_BUGS_BUTTON_REMOVAL && MinimalMenu.allInstalledIDS().contains("modmenu")) {
//                if (this.buttons.indexOf(buttonWidget) == 8) {
//                    buttonWidget.setWidth(98);
//                    buttonWidget.x = this.width / 2 - 102;
//                }
//                if (this.buttons.indexOf(buttonWidget) == 8 || this.buttons.indexOf(buttonWidget) == 7 || this.buttons.indexOf(buttonWidget) == 6 || this.buttons.indexOf(buttonWidget) == 5) {
//                    buttonWidget.y -= 24;
//                }
//                if (MinimalMenu.getConfigHandler().DO_CENTER_ADJUSTMENT) {
//                    buttonWidget.y += 24;
//                }
//            }
//
//            if (MinimalMenu.getConfigHandler().DO_FEEDBACK_BUTTON_REMOVAL && MinimalMenu.getConfigHandler().DO_REPORT_BUGS_BUTTON_REMOVAL && !MinimalMenu.allInstalledIDS().contains("modmenu")) {
//                if (this.buttons.indexOf(buttonWidget) == 5 || this.buttons.indexOf(buttonWidget) == 6 || this.buttons.indexOf(buttonWidget) == 7) {
//                    buttonWidget.y -= 24;
//                }
//                if (MinimalMenu.getConfigHandler().DO_CENTER_ADJUSTMENT) {
//                    buttonWidget.y += 24;
//                }
//            }
//
//            if (MinimalMenu.getConfigHandler().DO_OPEN_TO_LAN_REMOVAL) {
//                if (this.buttons.indexOf(buttonWidget) == 6) {
//                    if (doRemoval) {
//                        buttonWidget.visible = false;
//                    }
//                }
//                if (this.buttons.indexOf(buttonWidget) == 5) {
//                    if (doRemoval) {
//                        buttonWidget.setWidth(204);
//                        buttonWidget.x = this.width / 2 - 102;
//                    }
//                }
//            }


    }
}

