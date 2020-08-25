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
    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("minimalmenu.json");

    public static boolean REMOVE_SPLASH;
    public static boolean REMOVE_REALMS;
    public static boolean REMOVE_LANGUAGE;
    public static boolean REMOVE_ACCESSIBILITY;
    public static boolean REMOVE_COPYRIGHT;
    public static boolean STOP_SPIN;

    public static boolean REMOVE_REALMS_NOTIF;

    public static boolean REMOVE_FEEDBACK;
    public static boolean REMOVE_BUGS;
    public static boolean REMOVE_LANSP;
    public static boolean REMOVE_LANMP;

    public static boolean ADD_SAVES;
    public static boolean ADD_RELOAD_SAVES;

    public static void write() {
        try (
            final FileWriter fw = new FileWriter(CONFIG_PATH.toString());
            final JsonWriter jw = new JsonWriter(fw);
        ) {
            jw.setIndent("    ");
            jw.beginObject()
                    .name("REMOVE_SPLASH").value(REMOVE_SPLASH)
                    .name("REMOVE_REALMS").value(REMOVE_REALMS)
                    .name("STOP_SPIN").value(STOP_SPIN)
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void read() { //Runs on init to get data from json file.
        if (CONFIG_PATH.toFile().exists()) {
            try (final FileReader fr = new FileReader(CONFIG_PATH.toString())) {
                final JsonElement je = new JsonParser().parse(fr);
                if (!je.isJsonObject()) {
                    setDefaults();
                }

                final JsonObject object = je.getAsJsonObject();
                REMOVE_SPLASH = readBoolean(object, "REMOVE_SPLASH", false);
                REMOVE_REALMS = readBoolean(object, "REMOVE_REALMS", false);
                STOP_SPIN = readBoolean(object, "STOP_SPIN", false);
            } catch (IOException | JsonSyntaxException e) {
                e.printStackTrace();
            }
        } else {
            setDefaults();
        }
    }

    private static void setDefaults() {
        REMOVE_SPLASH = false;
        REMOVE_REALMS = false;
        STOP_SPIN = false;
    }

    private static boolean readBoolean(JsonObject json, String key, boolean fallback) {
        final JsonElement el = json.get(key);
        if (el == null || !el.isJsonPrimitive()) return fallback;
        try {
            return el.getAsBoolean();
        } catch (ClassCastException e) {
            return fallback;
        }
    }
}
