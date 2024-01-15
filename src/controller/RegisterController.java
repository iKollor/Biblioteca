package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JOptionPane;

import controller.AppController.ViewType;
import controller.utils.ValidateForm;
import model.Estudiante;
import model.Usuario;
import model.db.MetodosDAO;
import view.RegistroView;

public class RegisterController implements ActionListener {

  RegistroView registerView;
  AppController controller;

  public RegisterController(RegistroView registerView, AppController controller) {
    this.registerView = registerView;
    this.controller = controller;

    this.registerView.getBtnRegistrar().addActionListener(this);
    this.registerView.getBack().addActionListener(this);
  }

  public Usuario getUserFromForm() {
    Usuario user = new Estudiante();
    user.setNombre(registerView.getTxtNombre().getText());
    user.setApellido(registerView.getTxtApellido().getText());
    user.setDni(registerView.getTxtDNI().getText());
    user.setEmail(registerView.getTxtEmail().getText());
    user.setPassword(registerView.getTxtPassword().getPassword());
    return user;
  }

  public void onRegister() {
    MetodosDAO dao = controller.getDao();
    ValidateForm validador = controller.getValidate();

    Usuario user = getUserFromForm();
    if (validador.validateRegisterForm(user)) {
      try {
        if (dao.Register(user)) {
          JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
          controller.showView(ViewType.LOGIN);
        }
      } catch (SQLIntegrityConstraintViolationException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar usuario, el DNI o email ya existe",
            "Error", JOptionPane.ERROR_MESSAGE);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al registrar usuario, error desconocido",
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(null,
          "Error al registrar usuario, la información no es válida");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source == registerView.getBtnRegistrar()) {
      onRegister();
    }
    if (source == registerView.getBack()) {
      controller.showView(ViewType.LOGIN);
    }
  }

}
