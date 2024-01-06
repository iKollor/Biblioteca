package view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.AppController;
import model.Usuario;
import view.utils.fonts.SFProFont;

public class Sidebar extends JPanel {

  private JButton btnCatálogo, btnPrestamos, btnUsuarios, btnAddLibro, btnAddAutor, btnLogout;
  private JPanel userInfoContainer, buttonsContainer;

  private AppController controller;
  private Usuario user;

  public Sidebar(AppController controller) {
    this.controller = controller;
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(160, 600));
    setBackground(new Color(54, 51, 77));
    setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(84, 81, 127)));

    initComponents();
    addComponents();
    initEvents();
  }

  public void setUser(Usuario user) {
    this.user = user;
    updateUserInfoContainer();
    updateButtonsContainer();
  }

  private void initComponents() {
    // Contenedor de información de usuario
    userInfoContainer = new JPanel();
    userInfoContainer.setBackground(new Color(84, 81, 127));
    userInfoContainer.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    userInfoContainer.setLayout(new FlowLayout(FlowLayout.CENTER));

    // Contenedor de botones
    buttonsContainer = new JPanel();
    buttonsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    buttonsContainer.setPreferredSize(new Dimension(160, 400));

    // Botones
    btnCatálogo = new JButton("Catálogo");
    btnPrestamos = new JButton("Préstamos");
    btnUsuarios = new JButton("Usuarios");
    btnAddLibro = new JButton("Agregar Libro");
    btnAddAutor = new JButton("Agregar Autor");

    // Botón de logout
    btnLogout = new JButton("Cerrar Sesión");
    setButtonProperties(btnLogout);
  }

  private void addComponents() {
    add(userInfoContainer, BorderLayout.NORTH);
    add(buttonsContainer, BorderLayout.CENTER);
    add(btnLogout, BorderLayout.SOUTH);
  }

  private void updateUserInfoContainer() {
    userInfoContainer.removeAll();
    String userNombre = user == null ? "Administrador" : user.getNombre() + " " + user.getApellido();
    JLabel lblNombre = new JLabel(userNombre, SwingConstants.CENTER);
    lblNombre.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 18));
    userInfoContainer.add(lblNombre);
  }

  private void updateButtonsContainer() {
    buttonsContainer.removeAll();
    if (user == null) { // Si no hay usuario, mostrar botones de administrador
      añadirBotones(List.of(btnUsuarios, btnAddLibro, btnAddAutor, btnCatálogo, btnPrestamos));
    } else {
      añadirBotones(List.of(btnCatálogo, btnPrestamos));
    }
  }

  private void setButtonProperties(JButton boton) {
    boton.setPreferredSize(new Dimension(160, 50));
    boton.setBackground(null);
    boton.setForeground(Color.WHITE);
    boton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    boton.setFocusPainted(false);
    boton.setBorderPainted(false);
    boton.setOpaque(true);
    boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    boton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        boton.setBackground(new Color(84, 81, 127, 70)); // Color hover
      }

      @Override
      public void mouseExited(MouseEvent e) {
        boton.setBackground(null); // Color original
      }
    });
  }

  public void añadirBotones(List<JButton> botones) {
    for (JButton boton : botones) {
      setButtonProperties(boton);
      buttonsContainer.add(boton);
    }
  }

  public void initEvents() {
    btnLogout.addActionListener(e -> controller.onLogout()); // Agregar evento al botón de logout
    btnCatálogo.addActionListener(e -> controller.showCatalogoView()); // Agregar evento al botón de catálogo

    // TODO: Agregar eventos a los botones
    // btnPrestamos.addActionListener(e -> controller.showPrestamosView()); //
    // Agregar evento al botón de préstamos

    // btnUsuarios.addActionListener(e -> controller.showUsuariosView()); // Agregar
    // evento al botón de usuarios
    // btnAddLibro.addActionListener(e -> controller.showAddLibroView()); // Agregar
    // evento al botón de agregar libro
  }
}
