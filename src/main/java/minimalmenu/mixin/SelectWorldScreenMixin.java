package minimalmenu.mixin;

import java.io.File;
import java.util.List;

import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import minimalmenu.config.ConfigHandler;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.util.Util;

@Mixin(SelectWorldScreen.class)
public abstract class SelectWorldScreenMixin extends ScreenMixin {
    @Shadow @Final protected Screen parent;

    @Inject(method = "init", at = @At("HEAD"))
    public void init(CallbackInfo info) {
        List<ClickableWidget> buttons = Screens.getButtons((Screen)(Object)this);
        
        if (ConfigHandler.ADD_SAVES) {
            buttons.add(
                ButtonWidget.builder(
                    Text.translatable("minimalmenu.screen.singleplayer.saves"),
                    button -> {
                        assert this.client != null;
                        File file = client.runDirectory.toPath().resolve("saves").toFile(); //Create saves file from current running directory.
                        Util.getOperatingSystem().open(file);
                    }
                )
                .position(this.width / 2 - 232, this.height - 28)
                .size(72, 20)
                .build()
            );
        }
        if (ConfigHandler.ADD_RELOAD_SAVES) {
            buttons.add(
                ButtonWidget.builder(
                    Text.translatable("minimalmenu.screen.singleplayer.reload"),
                    button -> {
                        assert this.client != null;
                        this.client.setScreenAndRender(new SelectWorldScreen(parent)); //Refresh screen, by creating a new one.
                    }
                )
                .position(this.width / 2 - 232, this.height - 52)
                .size(72, 20)
                .build()
            );
        }
    }
}
