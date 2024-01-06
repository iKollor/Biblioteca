package controller;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.Usuario;
import model.db.MetodosDAO;
import view.HomeView;
import view.LoginView;
import view.RegistroView;
import view.panels.Catalog;
import model.Libro;

public class AppController {

  public enum ViewType {
    LOGIN, REGISTER, HOME
  }

  public enum Panel {
    CATALOGO, PRESTAMOS, USUARIOS, ADD_LIBRO, ADD_AUTOR
  }

  // Vistas
  private LoginView loginView;
  private RegistroView registerView;
  private HomeView homeView;
  // Paneles
  private Catalog catalogoView;

  // Modelos
  private MetodosDAO dao; // DAO: Data Access Object

  private ValidateForm validate = new ValidateForm(); // Validador de formularios
  private Usuario user = null; // Usuario actual

  private Panel currentPanel = null;
  private ViewType currentView = null;

  LoginController loginController = null;
  RegisterController registerController = null;

  // Constructor
  public AppController(MetodosDAO dao) {
    this.dao = dao;
  }

  public ValidateForm getValidate() {
    return validate;
  }

  public MetodosDAO getDao() {
    return dao;
  }

  public void showView(ViewType viewType) {
    // Mostrar la vista actual
    switch (viewType) {
      case LOGIN:
        loginView = new LoginView();
        loginController = new LoginController(loginView, this);
        loginView.setVisible(true);
        break;
      case REGISTER:
        registerView = new RegistroView();
        registerController = new RegisterController(registerView, this);
        registerView.setVisible(true);
        break;
      case HOME:
        homeView = new HomeView(this);
        homeView.setUser(user); // Establecer el usuario en HomeView
        homeView.setVisible(true);
        break;
    }

    // Cerrar las vistas anteriores
    if (currentView != null && currentView != viewType) {
      switch (currentView) {
        case LOGIN:
          loginView.dispose();
          break;
        case REGISTER:
          if (registerView != null)
            registerView.dispose();
          break;
        case HOME:
          if (homeView != null)
            homeView.dispose();
          break;
      }
    }
    currentView = viewType;
  }

  private void showPanel(Panel panel) {
    switch (panel) {
      case CATALOGO:
        if (catalogoView == null) {
          Catalog catalogoView = new Catalog(getLibros());
          homeView.addPanel(catalogoView, "CATALOGO");
        }
        homeView.switchPanel("CATALOGO");
        break;
      // TODO: Agregar casos para otros paneles
      default:
        throw new IllegalArgumentException("Panel no encontrado");
    }
    currentPanel = panel;
  }

  public void showCatalogoView() {
    showPanel(Panel.CATALOGO);
  }

  public void onLogout() {
    homeView.setUser(null);
    user = null;
    showView(ViewType.LOGIN);
  }

  public List<Libro> getLibros() {
    try {
      return dao.getLibros();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al obtener libros: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }
}
