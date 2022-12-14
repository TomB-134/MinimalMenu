package minimalmenu.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreen {

    public static Screen getConfigScreen(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setTitle(Text.translatable("minimalmenu.config.title"));

        builder.setSavingRunnable(ConfigHandler::write);

        //Create categories
        ConfigCategory generalScreen = builder.getOrCreateCategory(Text.translatable("minimalmenu.config.category.general"));
        ConfigCategory titleScreen = builder.getOrCreateCategory(Text.translatable("minimalmenu.config.category.title"));
        ConfigCategory pauseScreen = builder.getOrCreateCategory(Text.translatable("minimalmenu.config.category.pause"));
        ConfigCategory singePlayerScreen = builder.getOrCreateCategory(Text.translatable("minimalmenu.config.category.sp"));
        ConfigCategory optionsScreen = builder.getOrCreateCategory(Text.translatable("minimalmenu.config.category.options"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        //Build general screen options
        generalScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.general.openFolderScreen"), ConfigHandler.OPEN_FOLDER_SCREEN)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.OPEN_FOLDER_SCREEN = newValue)
                .setTooltip(Text.translatable("minimalmenu.config.option.general.openFolderScreen.tooltip"))
                .build());

        //Build title screen options
        titleScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.title.splash"), ConfigHandler.REMOVE_SPLASH)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_SPLASH = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.title.edition"), ConfigHandler.REMOVE_EDITION)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_EDITION = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.title.singleplayer"), ConfigHandler.REMOVE_SINGLEPLAYER)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_SINGLEPLAYER = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.title.multiplayer"), ConfigHandler.REMOVE_MULTIPLAYER)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_MULTIPLAYER = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.title.realms"), ConfigHandler.REMOVE_REALMS)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_REALMS = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.title.accessibility"), ConfigHandler.REMOVE_ACCESSIBILITY)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_ACCESSIBILITY = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.title.language"), ConfigHandler.REMOVE_LANGUAGE)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_LANGUAGE = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.title.copy"), ConfigHandler.REMOVE_COPYRIGHT)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_COPYRIGHT = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.common.addFolder"), ConfigHandler.ADD_FOLDER_TS)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.ADD_FOLDER_TS = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.title.dirtBackground"), ConfigHandler.DIRT_BACKGROUND)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.DIRT_BACKGROUND = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startIntField(Text.translatable("minimalmenu.config.option.title.xOffset"), ConfigHandler.X_OFFSET_TITLE)
                .setDefaultValue(0)
                .setSaveConsumer(newValue -> ConfigHandler.X_OFFSET_TITLE = newValue)
                .build());

        titleScreen.addEntry(entryBuilder.startIntField(Text.translatable("minimalmenu.config.option.title.yOffset"), ConfigHandler.Y_OFFSET_TITLE)
                .setDefaultValue(0)
                .setSaveConsumer(newValue -> ConfigHandler.Y_OFFSET_TITLE = newValue)
                .build());

        //Build options screen options
        optionsScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.options.online"), ConfigHandler.REMOVE_ONLINE)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_ONLINE = newValue)
                .build());

        //Build pause screen options
        pauseScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.pause.feedback"), ConfigHandler.REMOVE_FEEDBACK)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_FEEDBACK = newValue)
                .build());

        pauseScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.pause.bugs"), ConfigHandler.REMOVE_BUGS)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_BUGS = newValue)
                .build());
                
	pauseScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.pause.reporting"), ConfigHandler.REMOVE_REPORTING)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_REPORTING = newValue)
                .build());

        pauseScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.pause.lanSingle"), ConfigHandler.REMOVE_LANSP)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.REMOVE_LANSP = newValue)
                .build());

        pauseScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.common.addFolder"), ConfigHandler.ADD_FOLDER_PS)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.ADD_FOLDER_PS = newValue)
                .build());

        pauseScreen.addEntry(entryBuilder.startIntField(Text.translatable("minimalmenu.config.option.pause.xOffset"), ConfigHandler.X_OFFSET_PAUSE)
                .setDefaultValue(0)
                .setSaveConsumer(newValue -> ConfigHandler.X_OFFSET_PAUSE = newValue)
                .build());

        pauseScreen.addEntry(entryBuilder.startIntField(Text.translatable("minimalmenu.config.option.pause.yOffset"), ConfigHandler.Y_OFFSET_PAUSE)
                .setDefaultValue(0)
                .setSaveConsumer(newValue -> ConfigHandler.Y_OFFSET_PAUSE = newValue)
                .build());

        //Build singleplayer screen options
        singePlayerScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.sp.addSavesFolder"), ConfigHandler.ADD_SAVES)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.ADD_SAVES = newValue)
                .build());

        singePlayerScreen.addEntry(entryBuilder.startBooleanToggle(Text.translatable("minimalmenu.config.option.sp.reloadSaves"), ConfigHandler.ADD_RELOAD_SAVES)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> ConfigHandler.ADD_RELOAD_SAVES = newValue)
                .build());

        return builder.build();
    }
}
