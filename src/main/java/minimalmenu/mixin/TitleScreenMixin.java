package minimalmenu.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    @Shadow private String splashText;
    @Shadow private int copyrightTextX;
    @Shadow private static Identifier EDITION_TITLE_TEXTURE;

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void init(CallbackInfo info) {
        if (ConfigHandler.DEV_MODE) {
            MinimalMenu.printButtonInfo(this, this.buttons);
        }

        if (ConfigHandler.REMOVE_EDITION) {
            EDITION_TITLE_TEXTURE = new Identifier("minimalmenu", "textures/gui/title/edition_empty.png");
        } else {
            EDITION_TITLE_TEXTURE = new Identifier("textures/gui/title/edition.png");
        }

        if (ConfigHandler.REMOVE_SPLASH) {
            splashText = null;
        }

        if (ConfigHandler.REMOVE_COPYRIGHT) {
            copyrightTextX = 1000000000;
        }

        final int spacing = 24;
        int yOffset = 0;
        for (AbstractButtonWidget button : this.buttons) {
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
            }

            button.x -= ConfigHandler.X_OFFSET_TITLE;
            button.y -= ConfigHandler.Y_OFFSET_TITLE;
            button.y -= yOffset;
        }
    }

    //Removes realms notifications from the screen.
    @Inject(method = "init", at = @At("HEAD"))
    protected void setRealmsNotificationsToFalse(CallbackInfo info) {
        if (ConfigHandler.REMOVE_REALMS) {
            assert this.client != null;
            this.client.options.realmsNotifications = false;
        }
    }
}
