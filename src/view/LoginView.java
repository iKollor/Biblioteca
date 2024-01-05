package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.AppController;
import view.fonts.SFProFont;

public class LoginView extends JFrame {

  public JLabel lblTitulo;
  public JTextField txtUser;
  public JPasswordField txtPassword;
  public JButton btnLogin;
  public JLabel lblRegister;
  public JLabel lblRegisterLink;

  public LoginView(AppController controller) {
    // Configuración del JFrame
    setTitle("Sistema de Gestión de Biblioteca");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // Centrar la ventana
    setResizable(false);
    setLayout(new GridBagLayout()); // Usamos un GridBagLayout para centrar el panel

    initComponents(); // Se inicializan los componentes
    addComponents(); // Se agregan los componentes al JFrame
    initEvents(controller); // Se inicializan los eventos de los componentes
    clearForm(); // Se limpia el formulario
  }

  // Se separa la inicialización de los componentes de su agregación al panel para
  // mejorar la legibilidad
  private void initComponents() {
    lblTitulo = new JLabel("Biblioteca Virtual",
        SwingConstants.CENTER);
    lblTitulo.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 20));

    // Campo de texto para el usuario y contraseña
    txtUser = new JTextField();
    txtUser.setToolTipText("Usuario");
    txtPassword = new JPasswordField();
    txtPassword.setToolTipText("Contraseña");

    // Botón de login
    btnLogin = new JButton("Login");
    // TODO: Agregar funcionalidad al botón
    btnLogin.addActionListener(e -> {
      String usuario = txtUser.getText();
      char[] password = txtPassword.getPassword();
      System.out.println("Login");
      System.out.println("Usuario: " + usuario);
      System.out.println("Contraseña: " + String.valueOf(password));
    });

    // Label para el link de registro
    lblRegister = new JLabel("¿No tienes cuenta?", SwingConstants.CENTER);

    // Label para el link de registro
    lblRegisterLink = new JLabel("Regístrate", SwingConstants.CENTER);
    lblRegisterLink.setForeground(new Color(255, 126, 208, 255));
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

  public void clearForm() {
    txtUser.setText("");
    txtPassword.setText("");
  }

  public String getUsuario() {
    return txtUser.getText();
  }

  public char[] getPassword() {
    return txtPassword.getPassword();
  }

  public void setLoginAction(Runnable loginAction) {
    btnLogin.addActionListener(e -> loginAction.run());
  }

  public void initEvents(AppController controller) {
    btnLogin.addActionListener(e -> controller.onLogin()); // Agregar evento al botón de login

    lblRegisterLink.addMouseListener(new MouseAdapter() { // Agregar evento al link de registro
      @Override
      public void mouseClicked(java.awt.event.MouseEvent e) {
        controller.showRegisterView();
      }
    });

  }
}
