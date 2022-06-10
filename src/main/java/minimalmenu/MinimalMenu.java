package minimalmenu;

import minimalmenu.config.ConfigHandler;
import minimalmenu.screens.FolderScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Objects;

public class MinimalMenu implements ClientModInitializer {
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "minimalmenu";
    public static final String MOD_NAME = "MinimalMenu";

    @Override
    public void onInitializeClient() {
        ConfigHandler.read();
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

    public static boolean buttonMatchesKey (ClickableWidget button, String key) {
        Text buttonMessage = button.getMessage();
        Text keyMessage = Text.translatable(key);
        return Objects.equals(buttonMessage, keyMessage);
    }

    public static void processButtonFolderClick(MinecraftClient client) {
        if (ConfigHandler.OPEN_FOLDER_SCREEN) {
            FolderScreen folderScreen = new FolderScreen(client.currentScreen);
            client.setScreenAndRender(folderScreen);
        } else {
            assert client != null;
            File file = client.runDirectory.toPath().toFile();
            Util.getOperatingSystem().open(file);
        }
    }
}
