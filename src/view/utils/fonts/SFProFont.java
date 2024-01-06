package view.utils.fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class SFProFont {

  public static final String SF_PRO_REGULAR = "SF Pro Text Regular";
  public static final String SF_PRO_BOLD = "SF Pro Text Bold";
  public static final String SF_PRO_MEDIUM = "SF Pro Text Medium";
  public static final String SF_PRO_SEMIBOLD = "SF Pro Text Semibold";
  public static final String SF_PRO_LIGHT = "SF Pro Text Light";

  public static void registerFonts() {
    try {
      Font sfProRegular = Font.createFont(Font.TRUETYPE_FONT, new File("src/view/utils/fonts/SF-Pro-Text-Regular.otf"))
          .deriveFont(14f);
      Font sfProBold = Font.createFont(Font.TRUETYPE_FONT, new File("src/view/utils/fonts/SF-Pro-Text-Bold.otf"))
          .deriveFont(14f);
      Font sfProMedium = Font.createFont(Font.TRUETYPE_FONT, new File("src/view/utils/fonts/SF-Pro-Text-Medium.otf"))
          .deriveFont(14f);
      Font sfProSemibold = Font
          .createFont(Font.TRUETYPE_FONT, new File("src/view/utils/fonts/SF-Pro-Text-Semibold.otf"))
          .deriveFont(14f);
      Font sfProLight = Font.createFont(Font.TRUETYPE_FONT, new File("src/view/utils/fonts/SF-Pro-Text-Light.otf"))
          .deriveFont(14f);

      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(sfProRegular);
      ge.registerFont(sfProBold);
      ge.registerFont(sfProMedium);
      ge.registerFont(sfProSemibold);
      ge.registerFont(sfProLight);

    } catch (IOException | FontFormatException e) {
      e.printStackTrace();
    }
  }
}
