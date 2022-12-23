package minimalmenu.mixin;

import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Collections;
import java.util.List;

@Mixin(value = Screen.class, priority = 1100)
public abstract class ScreenMixin extends AbstractParentElement implements Drawable {
    @Shadow protected MinecraftClient client;
    @Shadow public int width;
    @Shadow public int height;

    @Inject(method = "init", at = @At("RETURN"))
    private void init(MinecraftClient client, int width, int height, CallbackInfo info) {
        if ((Screen)(Object)this instanceof TitleScreen) {
            afterTitleScreenInit();
        } else if ((Screen)(Object)this instanceof GameMenuScreen) {
            afterGameMenuScreenInit();
        }
    }

   private void afterTitleScreenInit() {
        final int spacing = 24;
        int yOffset = 0;
        int posY = 0;
        List<ClickableWidget> widgetList = Screens.getButtons((Screen)(Object)this);
        Collections.reverse(widgetList);
        for (ClickableWidget button : widgetList) {
            if (ConfigHandler.REMOVE_SINGLEPLAYER) {
                if (MinimalMenu.buttonMatchesKey(button, "menu.singleplayer")) {
                    button.visible = false;
                }
            }


            if (ConfigHandler.REMOVE_MULTIPLAYER) {
                if (MinimalMenu.buttonMatchesKey(button, "menu.multiplayer")) {
                    button.visible = false;
                    yOffset -= spacing;
                }
            }

            if (ConfigHandler.REMOVE_REALMS) {
                if (MinimalMenu.buttonMatchesKey(button, "menu.online")) {
                    button.visible = false;
                    yOffset -= spacing;
                }
            }

            if (ConfigHandler.REMOVE_LANGUAGE) {
                if (MinimalMenu.buttonMatchesKey(button, "narrator.button.language")) {
                    button.visible = false;
                }
            }

            if (ConfigHandler.REMOVE_ACCESSIBILITY) {
                if (MinimalMenu.buttonMatchesKey(button, "narrator.button.accessibility")) {
                    button.visible = false;
                    posY = button.getY() - yOffset;
                }
            }

            if (MinimalMenu.buttonMatchesKey(button, "modmenu.title")) {
                if (!ConfigHandler.REMOVE_ACCESSIBILITY) {
                    posY = button.getY();
                }
            }

            if (MinimalMenu.buttonMatchesKey(button, "minimalmenu.common..minecraft")) {
                button.setY(posY);
            } else {
                button.setY(button.getY() - yOffset);
            }

            button.setX(button.getX() - ConfigHandler.X_OFFSET_TITLE);
            button.setY(button.getY() - ConfigHandler.Y_OFFSET_TITLE);
        }
    }

    private void afterGameMenuScreenInit() {
        final int buttonWidth = 204;
        final int spacing = 24;
        int yOffset = 0;
        boolean isInSingleplayer = this.client.isInSingleplayer();
        boolean isLocalLan = this.client.getServer() != null && this.client.getServer().isRemote();
        boolean removeLan = ConfigHandler.REMOVE_LANSP && isInSingleplayer && !isLocalLan;
        boolean removeReporting = ConfigHandler.REMOVE_REPORTING && (!isInSingleplayer || isLocalLan);
        for (ClickableWidget button : Screens.getButtons((Screen)(Object)this)) {
            if (removeLan) {
                if (MinimalMenu.buttonMatchesKey(button, "menu.shareToLan")) {
                    button.visible = false;
                }
                if (MinimalMenu.buttonMatchesKey(button, "menu.options")) {
                    button.setWidth(buttonWidth);
                    button.setX(this.width / 2 - buttonWidth / 2);
                }
            } else if (removeReporting) {
            	if (MinimalMenu.buttonMatchesKey(button, "menu.playerReporting")) {
                    button.visible = false;
                }
                if (MinimalMenu.buttonMatchesKey(button, "menu.options")) {
                    button.setWidth(buttonWidth);
                    button.setX(this.width / 2 - buttonWidth / 2);
                }
            }
            
            if (ConfigHandler.REMOVE_FEEDBACK) {
                if (MinimalMenu.buttonMatchesKey(button, "menu.sendFeedback")) {
                    button.visible = false;
                }
                if (!ConfigHandler.REMOVE_BUGS) {
                    if (MinimalMenu.buttonMatchesKey(button, "menu.reportBugs")) {
                        button.setWidth(buttonWidth);
                        button.setX(this.width / 2 - buttonWidth / 2);
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
                        button.setX(this.width / 2 - buttonWidth / 2);
                    }
                }
            }
            
            button.setX(button.getX() - ConfigHandler.X_OFFSET_PAUSE);
            button.setY(button.getY() - ConfigHandler.Y_OFFSET_PAUSE);
            button.setY(button.getY() - yOffset);
        }
    }
}
