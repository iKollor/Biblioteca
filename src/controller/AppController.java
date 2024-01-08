package controller;

import controller.utils.ValidateForm;
import model.Usuario;
import model.db.MetodosDAO;
import view.HomeView;
import view.LoginView;
import view.RegistroView;

public class AppController {

  public enum ViewType {
    LOGIN, REGISTER, HOME
  }

  // Vistas
  private LoginView loginView;
  private RegistroView registerView;
  private HomeView homeView;

  // Modelos
  private MetodosDAO dao; // DAO: Data Access Object

  private ValidateForm validate = new ValidateForm(); // Validador de formularios
  private Usuario user = null; // Usuario actual

  private ViewType currentView = null;

  LoginController loginController = null;
  RegisterController registerController = null;
  HomeController homeController = null;

  // Constructor
  public AppController(MetodosDAO dao) {
    this.dao = dao;
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
        homeView = new HomeView();
        homeController = new HomeController(homeView, this);
        homeView.setVisible(true);
        break;
    }

    // Cerrar las vistas anteriores para liberar memoria
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

  // Lógica de Negocio

  public LoginView getLoginView() {
    return loginView;
  }

  public void setLoginView(LoginView loginView) {
    this.loginView = loginView;
  }

  public RegistroView getRegisterView() {
    return registerView;
  }

  public void setRegisterView(RegistroView registerView) {
    this.registerView = registerView;
  }

  public HomeView getHomeView() {
    return homeView;
  }

  public void setHomeView(HomeView homeView) {
    this.homeView = homeView;
  }

  public Usuario getUser() {
    return user;
  }

  public void setUser(Usuario user) {
    this.user = user;
  }

  public ValidateForm getValidate() {
    return validate;
  }

  public MetodosDAO getDao() {
    return dao;
  }

}
