package minimalmenu.mixin;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

@Mixin(value = Screen.class, priority = 1100)
public abstract class ScreenMixin extends AbstractParentElement implements Drawable {
    @Shadow protected MinecraftClient client;
    @Shadow public int width;
    @Shadow public int height;

    @Shadow protected abstract <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement);

    @Inject(method = "init", at = @At("RETURN"))
    private void init(MinecraftClient client, int width, int height, CallbackInfo info) {
        if (ConfigHandler.DEV_MODE) {
            MinimalMenu.printButtonInfo((Screen)(Object)this);
        }

        if ((Screen)(Object)this instanceof TitleScreen) {
            afterTitleScreenInit();
        } else if ((Screen)(Object)this instanceof GameMenuScreen) {
            afterGameMenuScreenInit();
        }
    }

   private void afterTitleScreenInit() {

        final int spacing = 24;
        int yOffset = 0;
        int folderPosY = 0;
        for (ClickableWidget button : Screens.getButtons((Screen)(Object)this)) {
            if (ConfigHandler.REMOVE_SINGLEPLAYER) {
                if (MinimalMenu.buttonMatchesKey(button, "menu.singleplayer")) {
                    button.visible = false;
                    yOffset += spacing;
                }
            }

            if (ConfigHandler.REMOVE_MULTIPLAYER) {
                if (MinimalMenu.buttonMatchesKey(button, "menu.multiplayer")) {
                    button.visible = false;
                    yOffset += spacing;
                }
            }

            if (ConfigHandler.REMOVE_REALMS) {
                if (MinimalMenu.buttonMatchesKey(button, "menu.online")) {
                    button.visible = false;
                    yOffset += spacing;
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
                }
                if (ConfigHandler.REMOVE_ACCESSIBILITY) {
                    folderPosY = button.y - yOffset;
                }
            }
            if (MinimalMenu.buttonMatchesKey(button, "modmenu.title")) {
                if (!ConfigHandler.REMOVE_ACCESSIBILITY) {
                    folderPosY = button.y - yOffset;
                }
            }

            button.x -= ConfigHandler.X_OFFSET_TITLE;
            button.y -= ConfigHandler.Y_OFFSET_TITLE;
            button.y -= yOffset;
        }

       if (ConfigHandler.ADD_FOLDER_TS) {
           this.addDrawableChild(new ButtonWidget(this.width / 2 + 104, folderPosY, 20, 20, new LiteralText("."), (button) -> {
               MinimalMenu.openMinecraftFolder(this.client);
           }));
       }
    }

    private void afterGameMenuScreenInit() {
        final int buttonWidth = 204;
        final int spacing = 24;
        int yOffset = 0;
        boolean removeLan = this.client.isInSingleplayer() ? ConfigHandler.REMOVE_LANSP : ConfigHandler.REMOVE_LANMP;
        for (ClickableWidget button : Screens.getButtons((Screen)(Object)this)) {
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
