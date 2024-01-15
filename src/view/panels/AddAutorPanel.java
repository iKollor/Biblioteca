package view.panels;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.HomeController;
import view.utils.PHTextField;
import view.utils.fonts.SFProFont;

public class AddAutorPanel extends JPanel {
  private PHTextField txtNombre, txtNacionalidad;

  private JPanel formContainer, northContainer;
  private JButton btnAddAutor;

  public AddAutorPanel() {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    setName(HomeController.Panel.ADD_AUTOR.toString());

    initComponents();
    addComponents();
  }

  private void initComponents() {

    // NORTH
    northContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
    northContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    JLabel lblTitle = new JLabel("Agregar autor", JLabel.LEFT);
    lblTitle.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 22));
    northContainer.add(lblTitle);

    // CENTER
    formContainer = new JPanel();
    formContainer.setLayout(new GridLayout(0, 1, 5, 5));

    txtNombre = new PHTextField(20);
    txtNombre.setPlaceholder("Nombre");
    txtNombre.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 18));

    txtNacionalidad = new PHTextField(20);
    txtNacionalidad.setPlaceholder("Nacionalidad");
    txtNacionalidad.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 18));

    btnAddAutor = new JButton("Agregar autor");
    btnAddAutor.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 22));
    btnAddAutor.setCursor(new Cursor(Cursor.HAND_CURSOR));

    formContainer.add(txtNombre);
    formContainer.add(txtNacionalidad);
    formContainer.add(btnAddAutor);
  }

  private void addComponents() {
    add(northContainer, BorderLayout.NORTH);
    add(formContainer, BorderLayout.CENTER);
  }

  public void clearForm() {
    txtNombre.setText("");
    txtNacionalidad.setText("");
  }

  public JButton getBtnAddAutor() {
    return btnAddAutor;
  }

  public void setBtnAddAutor(JButton btnAddAutor) {
    this.btnAddAutor = btnAddAutor;
  }

  public PHTextField getTxtNombre() {
    return txtNombre;
  }

  public void setTxtNombre(PHTextField txtNombre) {
    this.txtNombre = txtNombre;
  }

  public PHTextField getTxtNacionalidad() {
    return txtNacionalidad;
  }

  public void setTxtNacionalidad(PHTextField txtNacionalidad) {
    this.txtNacionalidad = txtNacionalidad;
  }
}
