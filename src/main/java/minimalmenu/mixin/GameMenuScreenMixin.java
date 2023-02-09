package minimalmenu.mixin;

import com.terraformersmc.modmenu.mixin.IGridWidgetAccessor;
import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = GameMenuScreen.class, priority = 1100)
public abstract class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }


    @Inject(method = "initWidgets", at = @At("TAIL"))
    private void removeFeedbackAndBugs (CallbackInfo info){
        for (Element element : this.children() ) {
            System.out.println(element);
            if (element instanceof GridWidget) {
                for (Element child : ((GridWidget) element).children()) {
                    if (child instanceof ClickableWidget) {
                        ClickableWidget widget = (ClickableWidget) child;
                        if (MinimalMenu.buttonMatchesKey(widget, "menu.reportBugs") && ConfigHandler.REMOVE_BUGS) {
                            widget.active = false;
                            widget.visible = false;
                        }
                        if (MinimalMenu.buttonMatchesKey(widget, "menu.sendFeedback") && ConfigHandler.REMOVE_BUGS) {
                            widget.setWidth(204);
                        }
                        if (MinimalMenu.buttonMatchesKey(widget, "menu.sendFeedback") && ConfigHandler.REMOVE_FEEDBACK) {
                            widget.active = false;
                            widget.visible = false;
                        }
                        if (MinimalMenu.buttonMatchesKey(widget, "menu.reportBugs") && ConfigHandler.REMOVE_FEEDBACK) {
                            widget.setWidth(204);
                            widget.setX(widget.getX() - 106);
                        }
                        if (ConfigHandler.REMOVE_FEEDBACK && ConfigHandler.REMOVE_BUGS) {
                            String text = this.client.isInSingleplayer() ? "menu.returnToMenu" : "menu.disconnect";
                            if (MinimalMenu.buttonMatchesKey(widget, text)) {
                                widget.setY(widget.getY() - 24);
                            }
                            if (MinimalMenu.buttonMatchesKey(widget, "menu.options")) {
                                widget.setY(widget.getY() - 24);
                            }
                        }

                        widget.setX(widget.getX() - ConfigHandler.X_OFFSET_PAUSE);
                        widget.setY(widget.getY() - ConfigHandler.Y_OFFSET_PAUSE);
                    }
                }

            }
        }
    }

    @Inject(method = "initWidgets", at = @At("HEAD"))
    private void initWidgets(CallbackInfo info) {
        if (ConfigHandler.ADD_FOLDER_PS) {
            this.addDrawableChild(
                ButtonWidget.builder(
                    Text.translatable("minimalmenu.common..minecraft"),
                    button -> {
                        MinimalMenu.processButtonFolderClick(client);
                    }
                )
                .position(this.width / 2 + 104, this.height / 4 + 120 + -16)
                .size(20, 20)
                .build()
            );
        }
    }
}
