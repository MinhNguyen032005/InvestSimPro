package data.FontLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public interface FontLoader {
    public static Font loadFont(String filePath) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(filePath)).deriveFont(Font.PLAIN, 14f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 14);
        }
    }
    static Font loadCustomizeFont(Font font, float size) {
        return font.deriveFont(size);
    }
}
