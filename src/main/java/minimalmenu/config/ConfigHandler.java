package minimalmenu.config;

import com.google.gson.*;
import minimalmenu.MinimalMenu;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.stream.JsonWriter;

public class ConfigHandler {
    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("minimalmenu.json");

    public boolean DO_LANGUAGE_BUTTON_REMOVAL;
    public boolean DO_ACCESSIBILITY_BUTTON_REMOVAL;
    public boolean DO_REALMS_REMOVAL;
    public boolean DO_CENTER_ADJUSTMENT;
    public boolean DO_SAVES_FOLDER_BUTTON;
    public boolean DO_SAVES_RELOAD_BUTTON;

    public ConfigHandler() {
        setDefaults();
    }

    public void read() {
        if (!CONFIG_PATH.toFile().exists()) {
            MinimalMenu.log(Level.WARN, "Minimal Menu config file not found, will be created with default values.");
            try (
                    final FileWriter fw = new FileWriter(CONFIG_PATH.toString());
                    final JsonWriter jw = new JsonWriter(fw);
            ) {
                jw.setIndent("    ");
                jw.beginObject()
                        .name("do_language_button_removal").value(true)
                        .name("do_accessibility_button_removal").value(true)
                        .name("do_realms_removal").value(true)
                        .name("do_center_adjustment").value(true)
                        .name("do_saves_folder_button").value(true)
                        .name("do_saves_reload_button").value(true)
                        .endObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            MinimalMenu.log(Level.INFO, "Found config file, we did it reddit.");
        }

        try (final FileReader fr = new FileReader(CONFIG_PATH.toString())) {
            final JsonElement je = new JsonParser().parse(fr);
            if (!je.isJsonObject()) { setDefaults(); }

            final JsonObject obj = je.getAsJsonObject();
            DO_LANGUAGE_BUTTON_REMOVAL = readBoolean(obj, "do_language_button_removal", true);
            DO_ACCESSIBILITY_BUTTON_REMOVAL = readBoolean(obj, "do_accessibility_button_removal", true);
            DO_REALMS_REMOVAL = readBoolean(obj, "do_realms_removal", true);
            DO_CENTER_ADJUSTMENT = readBoolean(obj, "do_center_adjustment", true);
        } catch (IOException | JsonSyntaxException exception) {
            MinimalMenu.log(Level.ERROR, "Could not read config path file.");
            exception.printStackTrace();
        }
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

    private void setDefaults() {
        DO_LANGUAGE_BUTTON_REMOVAL = true;
        DO_ACCESSIBILITY_BUTTON_REMOVAL = true;
        DO_REALMS_REMOVAL = true;
        DO_CENTER_ADJUSTMENT = true;
        DO_SAVES_FOLDER_BUTTON = true;
        DO_SAVES_RELOAD_BUTTON = true;
    }
}
