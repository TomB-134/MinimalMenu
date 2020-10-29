package minimalmenu.mixin;

import com.google.common.collect.Lists;
import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

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

        List<String> buttonsTypes = Lists.newArrayList();
        for (AbstractButtonWidget button : this.buttons) {
            buttonsTypes.add(((TranslatableText) button.getMessage()).getKey());
        }

        for (int i = 0; i < this.buttons.size(); i++) {
            AbstractButtonWidget buttonWidget = this.buttons.get(i);
            String buttonType = buttonsTypes.get(i);

            if (ConfigHandler.REMOVE_SINGLEPLAYER) {
                if (buttonType.equals("menu.singleplayer")) {
                    buttonWidget.visible = false;
                } else if (i > buttonsTypes.indexOf("menu.singleplayer")) {
                    buttonWidget.y -= 24;
                }
            }

            if (ConfigHandler.REMOVE_MULTIPLAYER) {
                if (buttonType.equals("menu.multiplayer")) {
                    buttonWidget.visible = false;
                } else if (i > buttonsTypes.indexOf("menu.multiplayer")) {
                    buttonWidget.y -= 24;
                }
            }

            if (ConfigHandler.REMOVE_REALMS) {
                if (buttonType.equals("menu.online")) {
                    buttonWidget.visible = false;
                } else if (i > buttonsTypes.indexOf("menu.online")) {
                    buttonWidget.y -= 24;
                }
            }

            if (ConfigHandler.REMOVE_LANGUAGE && buttonType.equals("narrator.button.language")) {
                buttonWidget.visible = false;
            }

            if (ConfigHandler.REMOVE_ACCESSIBILITY && buttonType.equals("narrator.button.accessibility")) {
                buttonWidget.visible = false;
            }

            buttonWidget.x -= ConfigHandler.X_OFFSET_TITLE;
            buttonWidget.y -= ConfigHandler.Y_OFFSET_TITLE;
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
