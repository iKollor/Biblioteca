package view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Sidebar extends JPanel {

  private JButton btnCatalogo, btnPrestamos, btnAddLibro, btnAddAutor, btnLogout;
  private JPanel userInfoContainer, buttonsContainer;

  public Sidebar() {
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(160, 600));
    setBackground(new Color(54, 51, 77));
    setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(84, 81, 127)));

    initComponents();
    addComponents();
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
    btnCatalogo = new JButton("Catálogo");
    btnPrestamos = new JButton("Historial de Préstamos");
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

  public void setListeners(ActionListener listener) {
    btnCatalogo.addActionListener(listener);
    btnPrestamos.addActionListener(listener);
    btnAddLibro.addActionListener(listener);
    btnAddAutor.addActionListener(listener);
    btnLogout.addActionListener(listener);
  }

  public List<JButton> getBotonesUsuario() {
    return List.of(btnCatalogo, btnPrestamos);
  }

  public List<JButton> getBotonesAdmin() {
    return List.of(btnCatalogo, btnPrestamos, btnAddLibro, btnAddAutor);
  }

  public JButton getBtnCatalogo() {
    return btnCatalogo;
  }

  public void setBtnCatalogo(JButton btnCatalogo) {
    this.btnCatalogo = btnCatalogo;
  }

  public JButton getBtnPrestamos() {
    return btnPrestamos;
  }

  public void setBtnPrestamos(JButton btnPrestamos) {
    this.btnPrestamos = btnPrestamos;
  }

  public JButton getBtnAddLibro() {
    return btnAddLibro;
  }

  public void setBtnAddLibro(JButton btnAddLibro) {
    this.btnAddLibro = btnAddLibro;
  }

  public JButton getBtnAddAutor() {
    return btnAddAutor;
  }

  public void setBtnAddAutor(JButton btnAddAutor) {
    this.btnAddAutor = btnAddAutor;
  }

  public JButton getBtnLogout() {
    return btnLogout;
  }

  public void setBtnLogout(JButton btnLogout) {
    this.btnLogout = btnLogout;
  }

  public JPanel getUserInfoContainer() {
    return userInfoContainer;
  }

  public void setUserInfoContainer(JPanel userInfoContainer) {
    this.userInfoContainer = userInfoContainer;
  }

  public JPanel getButtonsContainer() {
    return buttonsContainer;
  }

  public void setButtonsContainer(JPanel buttonsContainer) {
    this.buttonsContainer = buttonsContainer;
  }
}
