package controller;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import controller.utils.ValidateForm;
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

    this.loginView.getTxtPassword().addKeyListener(getLoginKeyListener());
    this.loginView.getTxtUser().addKeyListener(getLoginKeyListener());
  }

  public void onLogin() {
    Usuario user = null;

    MetodosDAO dao = controller.getDao();
    ValidateForm validador = controller.getValidate();

    String usuario = loginView.getTxtUser().getText();
    char[] password = loginView.getTxtPassword().getPassword();

    if (usuario.equals("admin") && new String(password).equals("admin")) {
      JOptionPane.showMessageDialog(null, "Bienvenido administrador");
      // Actualizar el estado del usuario en HomeView
      controller.setUser(user);
      controller.showView(AppController.ViewType.HOME);
    } else if (validador.validateLoginForm(usuario, password)) {
      try {
        user = dao.authLogin(usuario, new String(password));
        System.out.println("Usuario: " + user);
        if (user != null) {
          JOptionPane.showMessageDialog(null, "Bienvenido " + user.getNombre(), "Bienvenido",
              JOptionPane.INFORMATION_MESSAGE);
          // Actualizar el estado del usuario en HomeView
          controller.setUser(user);
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
    Object source = e.getSource();

    if (source == loginView.getLblRegisterLink()) {
      loginView.getLblRegisterLink().setForeground(new Color(255, 126, 208, 255));
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    Object source = e.getSource();

    if (source == loginView.getLblRegisterLink()) {
      loginView.getLblRegisterLink().setForeground(new Color(255, 126, 208, 100));
    }
  }

  // KeyListener para el login form
  public KeyListener getLoginKeyListener() {
    return new KeyListener() {

      @Override
      public void keyTyped(java.awt.event.KeyEvent e) {
      }

      @Override
      public void keyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
          onLogin();
        }
      }

      @Override
      public void keyReleased(java.awt.event.KeyEvent e) {
      }
    };
  }

}
