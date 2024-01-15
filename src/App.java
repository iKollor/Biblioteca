import java.awt.Font;
import java.awt.Insets;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;

import controller.AppController;
import model.db.MetodosDAO;
import view.utils.fonts.SFProFont;

public class App {

  public static void main(String[] args) {
    // Configuración del tema y la fuente...
    FlatDarkPurpleIJTheme.setup();
    SFProFont.registerFonts();
    UIManager.put("defaultFont", new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 14));
    UIManager.put("ScrollBar.thumbArc", 999);
    UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
    UIManager.put("ScrollBar.width", 12);

    // Instanciamos el modelo de datos
    MetodosDAO methods = new MetodosDAO();

    // Instanciamos el controlador
    AppController controller = new AppController(methods);

    // Mostrar la vista inicial, en este caso la vista de login
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        controller.showView(AppController.ViewType.LOGIN);
        
      }
    });
  }
}
