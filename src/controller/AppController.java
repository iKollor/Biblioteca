package controller;

import javax.swing.JOptionPane;

import model.Estudiante;
import model.MetodosDAO;
import model.Usuario;
import view.LoginView;
import view.RegistroView;

public class AppController {

  private LoginView loginView;
  private RegistroView registerView;
  private MetodosDAO dao; // DAO: Data Access Object
  private ValidateForm validate;

  public AppController(MetodosDAO dao) {
    this.dao = dao;
    this.validate = new ValidateForm();
  }

  public void setLoginView(LoginView loginView) {
    this.loginView = loginView;
    // TODO: Configurar listeners para los elementos de loginView aquí
  }

  public void setRegisterView(RegistroView registerView) {
    this.registerView = registerView;
    // TODO: Configurar listeners para los elementos de registerView aquí
  }

  public void showRegisterView() {
    loginView.setVisible(false);
    registerView.setVisible(true);
  }

  public void showLoginView() {
    registerView.setVisible(false);
    loginView.setVisible(true);
  }

  public void onLogin() {
    String usuario = loginView.getUsuario();
    char[] password = loginView.getPassword();

    if (validate.validateLoginForm(usuario, password)) {
      Usuario user = dao.authLogin(usuario, new String(password));
      if (user != null) {
        JOptionPane.showMessageDialog(null, "Bienvenido " + user.getNombre());
      } else {
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
      }
    } else {
      JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos, revise los campos");
    }
  }

  public void onLogout() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'onLogout'");
  }

  public void onRegister() {
    Usuario user = new Estudiante();
    user.setNombre(registerView.getNombre());
    user.setApellido(registerView.getApellido());
    user.setDni(registerView.getDNI());
    user.setEmail(registerView.getEmail());
    user.setPassword(registerView.getPassword());

    if (validate.validateRegisterForm(user)) {
      if (dao.Register(user)) {
        JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
        showLoginView();
      } else {
        JOptionPane.showMessageDialog(null, "Error al registrar usuario");
      }
    } else {
      JOptionPane.showMessageDialog(null, "Error al registrar usuario, la información no es válida");
    }
  }
}
