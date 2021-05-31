package minimalmenu;

import io.github.prospector.modmenu.gui.ModsScreen;
import minimalmenu.config.ConfigHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

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