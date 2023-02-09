package minimalmenu.mixin;

import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Positioner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GridWidget.Adder.class)
public abstract class GridWidgetAdderMixin {

    @Shadow public abstract <T extends ClickableWidget> T add(T widget, int occupiedColumns, Positioner positioner);

    @Shadow public abstract Positioner copyPositioner();

    @Inject(method = "add(Lnet/minecraft/client/gui/widget/ClickableWidget;ILnet/minecraft/client/gui/widget/Positioner;)Lnet/minecraft/client/gui/widget/ClickableWidget;", at = @At("HEAD"), cancellable = true, remap = false)
    public <T extends ClickableWidget> void add (T widget, int occupiedColumns, Positioner positioner, CallbackInfoReturnable<T> cir) {

        boolean isInSingleplayer = MinecraftClient.getInstance().isInSingleplayer();
        boolean isLocalLan = MinecraftClient.getInstance().getServer() != null && MinecraftClient.getInstance().getServer().isRemote();
        boolean removeLan = ConfigHandler.REMOVE_LANSP && isInSingleplayer && !isLocalLan;
        boolean removeReporting = ConfigHandler.REMOVE_REPORTING && (!isInSingleplayer || isLocalLan);

        if (removeLan) { //REMOVE LAN
            if (MinimalMenu.buttonMatchesKey(widget, "menu.shareToLan")) {
                cir.cancel();
            }
            if (MinimalMenu.buttonMatchesKey(widget, "menu.options") && occupiedColumns == 1) {
                widget.setWidth(204);
                this.add(widget, 2, copyPositioner().alignBottom());
                cir.cancel();
            }
        } else if (removeReporting) { //REMOVE REPORTING
            if (MinimalMenu.buttonMatchesKey(widget, "menu.playerReporting")) {
                cir.cancel();
            }
            if (MinimalMenu.buttonMatchesKey(widget, "menu.options") && occupiedColumns == 1) {
                widget.setWidth(204);
                this.add(widget, 2, copyPositioner().alignBottom());
                cir.cancel();
            }
        }
    }
}