package minimalmenu.mixin;

import minimalmenu.config.ConfigHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.gui.RotatingCubeMapRenderer.class)
@Environment(EnvType.CLIENT)
public class RotatingCubeMapRenderer {
    @Shadow private float time;

    @Inject(at = @At("HEAD"), method = "render")
    public void render(float delta, float alpha, CallbackInfo info) {
        if (ConfigHandler.STOP_SPIN) {
            time -= delta;
        }
    }
}
