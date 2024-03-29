package minimalmenu.screens;

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
                this.addDrawableChild(
                    ButtonWidget.builder(
                    Text.literal(file.getName()),
                        button -> {
                            Util.getOperatingSystem().open(file);
                        }
                    )
                    .position(this.width / 2 - 100, (this.height / 2 + (i-1) * 24) - y)
                    .size(200, 20)
                    .build()
                );

                this.addDrawableChild(
                    ButtonWidget.builder(
                        ScreenTexts.DONE,
                        button -> {
                            client.setScreen(parent);
                        }
                    )
                    .position(this.width / 2 - 100, (this.height / 2 + (directories.length + 1) * 24) - y)
                    .size(200, 20)
                    .build()
                );
            }
            if (i < directories.length) {
                int x = i;
                this.addDrawableChild(
                    ButtonWidget.builder(
                        Text.literal(directories[x]),
                        button -> {
                            File fileToOpen = new File(file.getAbsolutePath() + File.separator + directories[x]);
                            System.out.println(fileToOpen.getAbsolutePath());
                            Util.getOperatingSystem().open(fileToOpen);
                        }
                    )
                    .position(this.width / 2 - 100, (this.height / 2 + i * 24) - y)
                    .size(200, 20)
                    .build()
                );
            }
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
