package minimalmenu;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import minimalmenu.config.ConfigHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.widget.AbstractButtonWidget;

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

    public static void printButtonInfo(AbstractButtonWidget buttonWidget, List<AbstractButtonWidget> buttons) {
        log(Level.INFO,"-------------------------------------");
        log(Level.INFO, "Index of button: " + buttons.indexOf(buttonWidget));
        log(Level.INFO, buttonWidget.getMessage().getString());
        log(Level.INFO,"-------------------------------------");
    }
}
