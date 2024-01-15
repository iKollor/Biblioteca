package view.utils.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

public class SFProFont {

  public static final String SF_PRO_REGULAR = "SF Pro Text Regular";
  public static final String SF_PRO_BOLD = "SF Pro Text Bold";
  public static final String SF_PRO_MEDIUM = "SF Pro Text Medium";
  public static final String SF_PRO_SEMIBOLD = "SF Pro Text Semibold";
  public static final String SF_PRO_LIGHT = "SF Pro Text Light";

  public static void registerFonts() {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    try {
      // Cargar y registrar SF Pro Regular
      registerFont(ge, "/view/utils/fonts/SF-Pro-Text-Regular.otf");

      // Cargar y registrar SF Pro Bold
      registerFont(ge, "/view/utils/fonts/SF-Pro-Text-Bold.otf");

      // Cargar y registrar SF Pro Medium
      registerFont(ge, "/view/utils/fonts/SF-Pro-Text-Medium.otf");

      // Cargar y registrar SF Pro Semibold
      registerFont(ge, "/view/utils/fonts/SF-Pro-Text-Semibold.otf");

      // Cargar y registrar SF Pro Light
      registerFont(ge, "/view/utils/fonts/SF-Pro-Text-Light.otf");

    } catch (IOException | FontFormatException e) {
      e.printStackTrace();
    }
  }

  private static void registerFont(GraphicsEnvironment ge, String fontPath)
      throws IOException, FontFormatException {
    InputStream is = SFProFont.class.getResourceAsStream(fontPath);
    if (is != null) {
      Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(7f);
      ge.registerFont(font);
    } else {
      throw new IOException("No se pudo cargar la fuente desde: " + fontPath);
    }
  }
}
