package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
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

public class LoginView extends JFrame {

  private JLabel lblRegister, lblTitulo;

  // Estos van a tener eventos asociados, asi que creamos los métodos de acceso
  private JTextField txtUser;
  private JPasswordField txtPassword;
  private JButton btnLogin;
  private JLabel lblRegisterLink;

  public JTextField getTxtUser() {
    return txtUser;
  }

  public void setTxtUser(JTextField txtUser) {
    this.txtUser = txtUser;
  }

  public JPasswordField getTxtPassword() {
    return txtPassword;
  }

  public void setTxtPassword(JPasswordField txtPassword) {
    this.txtPassword = txtPassword;
  }

  public JButton getBtnLogin() {
    return btnLogin;
  }

  public void setBtnLogin(JButton btnLogin) {
    this.btnLogin = btnLogin;
  }

  public JLabel getLblRegisterLink() {
    return lblRegisterLink;
  }

  public void setLblRegisterLink(JLabel lblRegisterLink) {
    this.lblRegisterLink = lblRegisterLink;
  }

  public LoginView() {
    // Configuración del JFrame
    setTitle("Sistema de Gestión de Biblioteca");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Centrar la ventana
    setResizable(false);
    setLayout(new GridBagLayout()); // Usamos un GridBagLayout para centrar el panel

    initComponents();
    addComponents();
  }

  private void initComponents() {
    lblTitulo = new JLabel("Biblioteca Virtual", SwingConstants.CENTER);
    lblTitulo.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 20));

    // Campo de texto para el usuario y contraseña
    txtUser = new JTextField();
    txtUser.setToolTipText("Usuario");
    txtPassword = new JPasswordField();
    txtPassword.setToolTipText("Contraseña");

    // Botón de login
    btnLogin = new JButton("Login");
    btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    // Label para el link de registro
    lblRegister = new JLabel("¿No tienes cuenta?", SwingConstants.CENTER);

    // Label para el link de registro
    lblRegisterLink = new JLabel("Regístrate", SwingConstants.CENTER);
    lblRegisterLink.setForeground(new Color(255, 126, 208, 100));
    lblRegisterLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
  }

  // Función para agregar los componentes al panel
  private void addComponents() {

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(8, 1, 5, 5));

    panel.add(lblTitulo);
    panel.add(new JLabel("Usuario:"));
    panel.add(txtUser);
    panel.add(new JLabel("Contraseña:"));
    panel.add(txtPassword);
    panel.add(btnLogin);

    JPanel panelRegister = new JPanel();
    panelRegister.setLayout(new GridLayout(2, 1, 2, 2));
    panelRegister.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    panelRegister.add(lblRegister);
    panelRegister.add(lblRegisterLink);

    panel.add(panelRegister);

    panel.setPreferredSize(new Dimension(400, 400));
    add(panel);
  }

}
