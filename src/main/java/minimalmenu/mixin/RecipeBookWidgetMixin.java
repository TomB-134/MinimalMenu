package minimalmenu.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeDisplayListener;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeGridAligner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(RecipeBookWidget.class)
public abstract class RecipeBookWidgetMixin extends DrawableHelper implements Drawable, Element, RecipeDisplayListener, RecipeGridAligner<Ingredient> {

    @Shadow private int leftOffset;
    @Shadow private final List<RecipeGroupButtonWidget> tabButtons = Lists.newArrayList();

    @Inject(at = @At("TAIL"), method = "findLeftEdge", cancellable = true)
    public void findLeftEdge(boolean narrow, int width, int parentWidth, CallbackInfoReturnable info) {
        info.setReturnValue((width - parentWidth) / 2);
    }

    @Inject(at = @At("TAIL"), method = "reset")
    public void reset(boolean narrow, CallbackInfo info) {
        leftOffset = 147;
        for (RecipeGroupButtonWidget buttonWidget : tabButtons) {
            buttonWidget.x=0;
        }
    }
}
