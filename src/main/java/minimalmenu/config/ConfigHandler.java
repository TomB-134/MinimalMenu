package minimalmenu.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonWriter;
import minimalmenu.MinimalMenu;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ConfigHandler {
    public static boolean REMOVE_SPLASH;
    public static boolean REMOVE_REALMS;
    public static boolean REMOVE_LANGUAGE;
    public static boolean REMOVE_ACCESSIBILITY;
    public static boolean REMOVE_COPYRIGHT;

    public static boolean REMOVE_REALMS_NOTIF;

    public static boolean REMOVE_FEEDBACK;
    public static boolean REMOVE_BUGS;
    public static boolean REMOVE_LANSP;
    public static boolean REMOVE_LANMP;

    public static boolean ADD_SAVES;
    public static boolean ADD_RELOAD_SAVES;

    public static void write() {
        System.out.println("WRITE TO FILE");
    }

    public static void read() {
        System.out.println("READ");
    }
}
