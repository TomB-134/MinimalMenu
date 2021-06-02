package minimalmenu.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.RotatingCubeMapRenderer;

@Mixin(RotatingCubeMapRenderer.class)
public abstract class RotatingCubeMapRendererMixin {
    @Shadow private float time;

    @Inject(method = "render", at = @At("HEAD"))
    public void render(float delta, float alpha, CallbackInfo info) {
        if (ConfigHandler.STOP_SPIN) {
            time -= delta;
        }
    }
}
