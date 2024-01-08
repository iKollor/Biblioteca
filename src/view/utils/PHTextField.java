package view.utils;

import java.awt.*;
import java.util.Map;

import javax.swing.*;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class PHTextField extends JTextField {

  private static Map<?, ?> hints;

  static {
    // Safely initialize the hints map
    try {
      hints = (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
    } catch (Exception e) {
      hints = null;
    }
  }
  private static final float FONT_SIZE_RATIO = 0.3f; // Adjust this ratio as needed

  /*
   * public static void main(final String[] args) {
   * final PHTextField tf = new PHTextField("");
   * tf.setColumns(20);
   * tf.setPlaceholder("All your base are belong to us!");
   * final Font f = tf.getFont();
   * tf.setFont(new Font(f.getName(), f.getStyle(), 30));
   * JOptionPane.showMessageDialog(null, tf);
   * }
   */
  private String placeholder;

  public PHTextField() {
  }

  public PHTextField(
      final Document pDoc,
      final String pText,
      final int pColumns) {
    super(pDoc, pText, pColumns);
  }

  public PHTextField(final int pColumns) {
    super(pColumns);
  }

  public PHTextField(final String pText) {
    super(pText);
  }

  public PHTextField(final String pText, final int pColumns) {
    super(pText, pColumns);
  }

  public String getPlaceholder() {
    return placeholder;
  }

  @Override
  protected void paintComponent(final Graphics pG) {
    super.paintComponent(pG);

    if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
      return;
    }

    final Graphics2D g = (Graphics2D) pG;

    // Apply rendering hints if available
    if (hints != null) {
      g.addRenderingHints(hints);
    }

    // Retrieve the default font from UIManager
    Font defaultFont = UIManager.getFont("defaultFont");
    if (defaultFont == null) {
      defaultFont = getFont(); // Fallback to JTextField's current font
    }

    // Adjust the font size relative to the JTextField's height
    int fontSize = (int) (getHeight() * FONT_SIZE_RATIO);
    Font scaledFont = defaultFont.deriveFont((float) fontSize);
    g.setFont(scaledFont);

    FontMetrics fm = g.getFontMetrics();
    int textAscent = fm.getAscent();
    int y = (getHeight() - fm.getHeight()) / 2 + textAscent;

    g.setColor(getDisabledTextColor());
    g.drawString(placeholder, getInsets().left, y);
  }

  public void setPlaceholder(final String s) {
    placeholder = s;
  }

}