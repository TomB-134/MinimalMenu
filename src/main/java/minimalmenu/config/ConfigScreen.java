package minimalmenu.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

public class ConfigScreen {

    public static Screen getConfigScreen(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(new TranslatableText("config.title"));

        builder.setSavingRunnable(ConfigHandler::write);

        //Create categories
        ConfigCategory titleScreen = builder.getOrCreateCategory(new TranslatableText("config.category.title"));
        ConfigCategory optionsScreen = builder.getOrCreateCategory(new TranslatableText("config.category.options"));
        ConfigCategory pauseScreen = builder.getOrCreateCategory(new TranslatableText("config.category.pause"));
        ConfigCategory singePlayerScreen = builder.getOrCreateCategory(new TranslatableText("config.category.sp"));
        ConfigCategory otherOptions = builder.getOrCreateCategory(new TranslatableText("config.category.other"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        //Build title screen options
        titleScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.title.splash"), ConfigHandler.REMOVE_SPLASH)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_SPLASH = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.title.edition"), ConfigHandler.REMOVE_EDITION)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_EDITION = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.title.realms"), ConfigHandler.REMOVE_REALMS)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_REALMS = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.title.accessibility"), ConfigHandler.REMOVE_ACCESSIBILITY)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_ACCESSIBILITY = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.title.language"), ConfigHandler.REMOVE_LANGUAGE)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_LANGUAGE = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.title.copy"), ConfigHandler.REMOVE_COPYRIGHT)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_COPYRIGHT = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.title.spin"), ConfigHandler.STOP_SPIN)
                .setDefaultValue(false)
                .setTooltip(new TranslatableText("config.option.title.spin.tooltip"))
                .setSaveConsumer(newValue -> ConfigHandler.STOP_SPIN = newValue)
                .build());

        //Build options screen options
        optionsScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.options.realmsNotif"), ConfigHandler.REMOVE_REALMS_NOTIF)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_REALMS_NOTIF = newValue)
                .build());

        //Build pause screen options
        pauseScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.pause.feedback"), ConfigHandler.REMOVE_FEEDBACK)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_FEEDBACK = newValue)
                .build());

        pauseScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.pause.bugs"), ConfigHandler.REMOVE_BUGS)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_BUGS = newValue)
                .build());

        pauseScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.pause.lanSingle"), ConfigHandler.REMOVE_LANSP)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_LANSP = newValue)
                .build());

        pauseScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.pause.lanMulti"), ConfigHandler.REMOVE_LANMP)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_LANMP = newValue)
                .build());

        //Build singleplayer screen options
        singePlayerScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.sp.addSavesFolder"), ConfigHandler.ADD_SAVES)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.ADD_SAVES = newValue)
                .build());

        singePlayerScreen.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.sp.reloadSaves"), ConfigHandler.ADD_RELOAD_SAVES)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.ADD_RELOAD_SAVES = newValue)
                .build());

        //Build other options
        otherOptions.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("config.option.other.dev"), ConfigHandler.DEV_MODE)
                .setDefaultValue(false)
                .setTooltip(new TranslatableText("config.option.other.dev.tooltip"))
                .setSaveConsumer(newValue -> ConfigHandler.DEV_MODE = newValue)
                .build());

        return builder.build();
    }
}
