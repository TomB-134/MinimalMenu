package minimalmenu;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MinimalMenu implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "minimalmenu";
    public static final String MOD_NAME = "MinimalMenu";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing MinimalMenu... searching for mod menu installation.");
        if (getIsModInstalled("modmenu")) {
            log(Level.INFO, "Mod menu is installed, will adjust.");
        } if (getIsModInstalled("better_mod_button")) {
            log(Level.WARN, "Advised removal of Better Mod Button for cleaner look.");
        }
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

    public static boolean getIsModInstalled(String MOD_ID) {
        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            if(mod.getMetadata().getId().equals(MOD_ID)) { return true; }
        }
        return false;
    }
}