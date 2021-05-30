package minimalmenu.mixin;

import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractInventoryScreen.class)
public class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T> {
    public AbstractInventoryScreenMixin(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {}

    @Inject(method = "applyStatusEffectOffset", at = @At("RETURN"))
    private void applyStatusEffectOffset(CallbackInfo info) {
        if (ConfigHandler.DISABLE_POTION_OFFSET) {
            this.x = (this.width - this.backgroundWidth) / 2;
        }
    }

}
