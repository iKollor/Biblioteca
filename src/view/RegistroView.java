package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import view.utils.fonts.SFProFont;

public class RegistroView extends JFrame {

  public JLabel lblTitulo;

  public JTextField txtNombre;
  public JTextField txtApellido;
  public JTextField txtDNI;
  public JTextField txtEmail;
  public JPasswordField txtPassword;
  public JPasswordField txtConfirmPassword;
  public JButton btnRegistrar;
  public JButton back;

  public JTextField getTxtNombre() {
    return txtNombre;
  }

  public JTextField getTxtApellido() {
    return txtApellido;
  }

  public JTextField getTxtDNI() {
    return txtDNI;
  }

  public JTextField getTxtEmail() {
    return txtEmail;
  }

  public JPasswordField getTxtPassword() {
    return txtPassword;
  }

  public JPasswordField getTxtConfirmPassword() {
    return txtConfirmPassword;
  }

  public JButton getBtnRegistrar() {
    return btnRegistrar;
  }

  public JButton getBack() {
    return back;
  }

  public RegistroView() {
    setTitle("Sistema de Gestión de Biblioteca");
    setSize(500, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Centrar la ventana
    setResizable(false);
    setLayout(new BorderLayout());

    initComponents(); // Se inicializan los componentes
    addComponents(); // Se agregan los componentes al JFrame
  }

  public void initComponents() {
    lblTitulo = new JLabel("Registro de usuario", SwingConstants.CENTER);
    lblTitulo.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 24));

    txtNombre = new JTextField();
    txtNombre.setToolTipText("Nombre");

    txtApellido = new JTextField();
    txtApellido.setToolTipText("Apellido");

    txtDNI = new JTextField();
    txtDNI.setToolTipText("El DNI debe al menos tener 10 dígitos");

    txtEmail = new JTextField();
    txtEmail.setToolTipText("Email");

    txtPassword = new JPasswordField();
    txtPassword.setToolTipText(
        "La contraseña debe tener al menos 8 caracteres, una letra mayúscula y un número, y un carácter especial, sin espacios");

    txtConfirmPassword = new JPasswordField();
    txtConfirmPassword.setToolTipText("Confirmar contraseña, debe coincidir con la contraseña ingresada");

    btnRegistrar = new JButton("Registrar");
    btnRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    back = new JButton("<-");
    back.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
  }

  public void addComponents() {

    JPanel form = new JPanel();
    form.setLayout(new GridLayout(13, 1, 5, 5));
    form.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 70));

    form.add(new JLabel("Nombre:"));
    form.add(txtNombre);

    form.add(new JLabel("Apellido:"));
    form.add(txtApellido);

    form.add(new JLabel("DNI:"));
    form.add(txtDNI);

    form.add(new JLabel("Email:"));
    form.add(txtEmail);

    form.add(new JLabel("Contraseña:"));
    form.add(txtPassword);

    form.add(new JLabel("Confirmar contraseña:"));
    form.add(txtConfirmPassword);

    form.add(btnRegistrar);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 10));
    mainPanel.add(form, BorderLayout.CENTER);
    mainPanel.add(lblTitulo, BorderLayout.NORTH);

    add(back).setBounds(8, 8, 60, 30);

    add(mainPanel, BorderLayout.CENTER);
  }
}
