package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import model.Usuario;
import model.db.MetodosDAO;
import view.LoginView;

public class LoginController implements MouseListener {

  private LoginView loginView;
  private AppController controller;

  public LoginController(LoginView loginView, AppController controller) {
    this.loginView = loginView;
    this.controller = controller;

    this.loginView.getBtnLogin().addMouseListener(this);
    this.loginView.getLblRegisterLink().addMouseListener(this);
  }

  public void onLogin() {

    MetodosDAO dao = controller.getDao();
    ValidateForm validador = controller.getValidate();

    String usuario = loginView.getTxtUser().getText();
    char[] password = loginView.getTxtPassword().getPassword();

    if (usuario.equals("admin") && new String(password).equals("admin")) {
      JOptionPane.showMessageDialog(null, "Bienvenido administrador");
      controller.showView(AppController.ViewType.HOME);
    } else if (validador.validateLoginForm(usuario, password)) {
      Usuario user;
      try {
        user = dao.authLogin(usuario, new String(password));
        System.out.println("Usuario: " + user);
        if (user != null) {
          JOptionPane.showMessageDialog(null, "Bienvenido " + user.getNombre(), "Bienvenido",
              JOptionPane.INFORMATION_MESSAGE);
          controller.showView(AppController.ViewType.HOME);
        } else {
          JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al iniciar sesión, error desconocido", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos, revise los campos", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Object source = e.getSource();
    if (source == loginView.getBtnLogin()) {
      onLogin();
    }
    if (source == loginView.getLblRegisterLink()) {
      controller.showView(AppController.ViewType.REGISTER);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

}
