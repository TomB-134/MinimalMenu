package minimalmenu.screens;

import java.util.function.Supplier;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import java.io.File;

public class FolderScreen extends Screen {
    private final Screen parent;

    public FolderScreen(Screen parent) {
        super(Text.translatable("minimalmenu.screen.folders"));
        this.parent = parent;
    }

    public void init() {
        assert client != null;
        File file = client.runDirectory.toPath().toFile();
        String[] directories = file.list((dir, name) -> new File(dir, name).isDirectory());

        assert directories != null;
        int y = (directories.length * 24) / 2;
        for (int i = 0; i <= directories.length; i++) {
            if (i == 0) {
                this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, (this.height / 2 + (i-1) * 24) - y, 200, 20, Text.literal(file.getName()), button -> {
                    Util.getOperatingSystem().open(file);
                }, Supplier::get));
                this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, (this.height / 2 + (directories.length+1) * 24) - y, 200, 20, ScreenTexts.DONE, button -> {
                    client.setScreen(parent);
                }, Supplier::get));
            }
            if (i < directories.length) {
                int x = i;
                ButtonWidget buttonWidget = new ButtonWidget(this.width / 2 - 100, (this.height / 2 + i * 24) - y, 200, 20, Text.literal(directories[x]), (button -> {
                    File fileToOpen = new File(file.getAbsolutePath() + File.separator + directories[x]);
                    System.out.println(fileToOpen.getAbsolutePath());
                    Util.getOperatingSystem().open(fileToOpen);
                }), Supplier::get);
                this.addDrawableChild(buttonWidget);
            }
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
