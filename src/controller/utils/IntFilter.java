package controller.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class IntFilter extends DocumentFilter {

  @Override
  public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
      throws BadLocationException {
    if (string == null) {
      return;
    }

    if ((fb.getDocument().getLength() + string.length()) <= 10 && string.matches("[0-9]+")) {
      super.insertString(fb, offset, string, attr);
    } else {
      // Opcional: puedes mostrar un mensaje de error o simplemente ignorar la entrada
    }
  }

  @Override
  public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
      throws BadLocationException {
    if (text == null) {
      return;
    }

    if ((fb.getDocument().getLength() + text.length() - length) <= 10 && text.matches("[0-9]+")) {
      super.replace(fb, offset, length, text, attrs);
    } else {
      return;
    }
  }
}