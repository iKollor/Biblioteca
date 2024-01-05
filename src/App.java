import java.awt.Font;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import controller.AppController;
import model.MetodosDAO;
import view.LoginView;
import view.RegistroView;
import view.fonts.SFProFont;

public class App {

  public static void main(String[] args) {
    // Configuración del tema y la fuente...1
    FlatDarkPurpleIJTheme.setup();
    SFProFont.registerFonts();
    UIManager.put("defaultFont", new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 14));

    // Crear el controlador y las vistas
    MetodosDAO methods = new MetodosDAO();
    AppController controller = new AppController(methods);

    LoginView loginView = new LoginView(controller);
    RegistroView registerView = new RegistroView(controller);

    controller.setLoginView(loginView);
    controller.setRegisterView(registerView);

    // Mostrar la vista inicial
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        controller.showLoginView();
      }
    });
  }
}
