package minimalmenu.mixin;

import com.google.common.collect.Lists;
import minimalmenu.MinimalMenu;
import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "initWidgets()V")
    private void initWidgets(CallbackInfo info) {
        if (ConfigHandler.DEV_MODE) {
            for (AbstractButtonWidget button : this.buttons) {
                MinimalMenu.printButtonInfo(button, this.buttons);
            }
        }


        //Establish a list of strings that holds the message key for each button.
        List<String> buttonsTypes = Lists.newArrayList(); //Create list
        for (AbstractButtonWidget button : this.buttons) { //Loop over each button
            buttonsTypes.add(((TranslatableText) button.getMessage()).getKey()); //Add the button's key to the list
            if (ConfigHandler.DEV_MODE) {
                System.out.println(((TranslatableText) button.getMessage()).getKey());
            }
        }

        for (int i = 0; i < this.buttons.size(); i++) { //Loop over each button
            AbstractButtonWidget buttonWidget = this.buttons.get(i); //Button widget of current iteration
            String buttonType = buttonsTypes.get(i); //Button type of current iteration

            if ((ConfigHandler.REMOVE_LANSP && this.client.isInSingleplayer()) || (ConfigHandler.REMOVE_LANMP && !this.client.isInSingleplayer())) { //Remove open to lan and all associating if else mess
                if (buttonType.equals("menu.options")) { //Fix options button position
                    buttonWidget.y = this.buttons.get(buttonsTypes.indexOf("modmenu.title")).y; //jesus fucking christ i hate it i hate it i hate it (Hack fix for #19)
                } else if (buttonType.equals("menu.shareToLan")) { //Remove lan
                    buttonWidget.visible = false;
                } else if (buttonType.equals("menu.returnToMenu")) { //Fix return to menu position
                    buttonWidget.y -= 24;
                } else if (buttonType.equals("modmenu.title")) { //Resize and reposition mod menu - yes these if else statements are definitely a sustainable development method it's fine it's finnneee
                    buttonWidget.setWidth(98);
                    buttonWidget.x = this.width / 2 + 4;
                }
            }

            if (ConfigHandler.REMOVE_FEEDBACK) {
                if (buttonType.equals("menu.sendFeedback")) { //Remove feedback button
                    buttonWidget.visible = false;
                }

                if (!ConfigHandler.REMOVE_BUGS && buttonType.equals("menu.reportBugs")) { //Adjust bugs button to accommodate lack of feedback button
                    buttonWidget.setWidth(204);
                    buttonWidget.x = this.width / 2 - 102;
                }
            }

            if (ConfigHandler.REMOVE_BUGS) {
                if (buttonType.equals("menu.reportBugs")) { //Remove bugs button
                    buttonWidget.visible = false;
                }

                if (!ConfigHandler.REMOVE_FEEDBACK && buttonType.equals("menu.sendFeedback")) { //Adjust feedback button to accommodate lack of feedback button
                    buttonWidget.setWidth(204);
                }
            }

            if (ConfigHandler.REMOVE_FEEDBACK && ConfigHandler.REMOVE_BUGS) { //If both the feedback and bugs button are no more...
                if (buttonType.equals("menu.options") || buttonType.equals("menu.shareToLan") || buttonType.equals("menu.returnToMenu") || buttonType.equals("modmenu.title")) {
                    buttonWidget.y -= 24; //Adjust height of other buttons
                }
            }

            buttonWidget.x -= ConfigHandler.X_OFFSET_PAUSE; //Offsets
            buttonWidget.y -= ConfigHandler.Y_OFFSET_PAUSE;
        }
    }
}

