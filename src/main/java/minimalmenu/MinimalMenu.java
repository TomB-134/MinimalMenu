package minimalmenu;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import minimalmenu.config.ConfigHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;

public class MinimalMenu implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "minimalmenu";
    public static final String MOD_NAME = "MinimalMenu";

    @Override
    public void onInitialize() {
        ConfigHandler.read();
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

    public static void printButtonInfo(Screen screen, List<AbstractButtonWidget> buttons) {
        log(Level.INFO,"-------------------------------------");
        log(Level.INFO, screen.getClass().getName());

        Language language = Language.getInstance();
        for (AbstractButtonWidget button : buttons) {
            Text buttonMessage = button.getMessage();
            String buttonText = buttonMessage.getString();
            
            log(Level.INFO,"-------------------------------------");
            log(Level.INFO, "Button localized: " + buttonText);
            log(Level.INFO, "Button index:     " + buttons.indexOf(button));

            if (buttonMessage instanceof TranslatableText) {
                String buttonLangKey = ((TranslatableText) buttonMessage).getKey();
                String buttonLangText = language.get(buttonLangKey);

                log(Level.INFO, "Button key:       " + buttonLangKey);
                if (!buttonText.equals(buttonLangText)) {
                    log(Level.INFO, "Button text:      " + buttonLangText);
                }

                Object[] textArgs = ((TranslatableText) buttonMessage).getArgs();
                for (int i = 0; i < textArgs.length; i++) {
                    Object arg = textArgs[i];
                    if (arg instanceof TranslatableText) {
                        String argKey = ((TranslatableText) arg).getKey();
                        log(Level.INFO, "");
                        log(Level.INFO, "Text arg " + i + " key:   " + argKey);
                        log(Level.INFO, "Text arg " + i + " text:  " + language.get(argKey));
                    }
                }
            }
        }
        log(Level.INFO,"-------------------------------------");
    }

    public static boolean buttonMatchesKey(AbstractButtonWidget button, String key) {
        Text buttonMessage = button.getMessage();
        if (buttonMessage instanceof TranslatableText) {
            String buttonKey = ((TranslatableText) buttonMessage).getKey();
            if (buttonKey.equals(key)) {
                return true;
            }
            Object[] textArgs = ((TranslatableText) buttonMessage).getArgs();
            for (Object arg : textArgs) {
                if (arg instanceof TranslatableText) {
                    String argKey = ((TranslatableText) arg).getKey();
                    if (argKey.equals(key)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
