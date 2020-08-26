package minimalmenu.mixin;

import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Shadow public static final CubeMapRenderer PANORAMA_CUBE_MAP = new CubeMapRenderer(new Identifier("textures/gui/title/background/panorama"));
    @Shadow private String splashText;
    @Shadow private int copyrightTextX;
    @Shadow private final RotatingCubeMapRenderer backgroundRenderer;
    @Shadow private static Identifier EDITION_TITLE_TEXTURE = new Identifier("textures/gui/title/edition.png");

    protected TitleScreenMixin(Text title) {
        super(title);
        backgroundRenderer = new RotatingCubeMapRenderer(PANORAMA_CUBE_MAP);
    }

    @Inject(at = @At("TAIL"), method = "init()V")
    protected void init(CallbackInfo info) {
        if (ConfigHandler.DEV_MODE) {
            for (AbstractButtonWidget button : this.buttons) {
                MinimalMenu.printButtonInfo(button, this.buttons);
            }
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

        for (AbstractButtonWidget buttonWidget : this.buttons) {
            if (ConfigHandler.REMOVE_REALMS) {
                if (buttons.indexOf(buttonWidget) == 2) {
                    buttonWidget.visible = false;
                } else if (buttons.indexOf(buttonWidget) == 3 || buttons.indexOf(buttonWidget) == 4 || buttons.indexOf(buttonWidget) == 5 || buttons.indexOf(buttonWidget) == 6 || buttons.indexOf(buttonWidget) == 7) {
                    buttonWidget.y -= 24;
                }
            }

            if (ConfigHandler.REMOVE_LANGUAGE && buttons.indexOf(buttonWidget) == 4) {
                buttonWidget.visible = false;
            }

            if (ConfigHandler.REMOVE_ACCESSIBILITY && buttons.indexOf(buttonWidget) == 7) {
                buttonWidget.visible = false;
            }
        }
    }

    //Removes realms notifications from the screen.
    @Inject(at = @At("HEAD"), method = "init()V")
    protected void setRealmsNotificationsToFalse(CallbackInfo info) {
        if (ConfigHandler.REMOVE_REALMS) {
            assert this.client != null;
            this.client.options.realmsNotifications = false;
        }
    }
}
