package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JOptionPane;

import controller.AppController.ViewType;
import model.Estudiante;
import model.Usuario;
import model.db.MetodosDAO;
import view.RegistroView;

public class RegisterController implements MouseListener {

  RegistroView registerView;
  AppController controller;

  public RegisterController(RegistroView registerView, AppController controller) {
    this.registerView = registerView;
    this.controller = controller;

    this.registerView.getBtnRegistrar().addMouseListener(this);
    this.registerView.getBack().addMouseListener(this);
  }

  public Usuario getUserFromForm() {
    Usuario user = new Estudiante();
    user.setNombre(registerView.txtNombre.getText());
    user.setApellido(registerView.txtApellido.getText());
    user.setDni(registerView.txtDNI.getText());
    user.setEmail(registerView.txtEmail.getText());
    user.setPassword(registerView.txtPassword.getPassword());
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
        JOptionPane.showMessageDialog(null, "Error al registrar usuario, el DNI o email ya existe", "Error",
            JOptionPane.ERROR_MESSAGE);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al registrar usuario, error desconocido", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    } else {
      JOptionPane.showMessageDialog(null, "Error al registrar usuario, la información no es válida");
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Object source = e.getSource();
    if (source == registerView.getBtnRegistrar()) {
      onRegister();
    }
    if (source == registerView.getBack()) {
      controller.showView(ViewType.LOGIN);
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
