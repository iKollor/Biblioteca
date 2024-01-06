package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.AppController;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;

import model.Usuario;
import view.components.Sidebar;
import view.utils.fonts.SFProFont;

public class HomeView extends JFrame {

  private Sidebar sidebar;
  private JLabel lblDefault;
  private AppController controller;
  private JPanel mainPanel;
  private CardLayout cardLayout;

  public HomeView(AppController controller) {
    this.controller = controller;
    setTitle("Sistema de Gestión de Biblioteca");
    setSize(900, 700);
    setMinimumSize(getSize()); // Evita que la ventana se haga más pequeña
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    initComponents();
    addComponents();

    cardLayout = (CardLayout) mainPanel.getLayout();

  }

  public void setUser(Usuario user) {
    sidebar.setUser(user); // Actualiza sidebar con el nuevo usuario
  }

  public void initComponents() {
    sidebar = new Sidebar(controller);

    lblDefault = new JLabel(
        "<html><center>Seleccione una opción del menú lateral</center></html>", JLabel.CENTER);
    lblDefault.setFont(new java.awt.Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 22));

    mainPanel = new JPanel();
    cardLayout = new CardLayout();
    mainPanel.setLayout(cardLayout);
    mainPanel.add(lblDefault, "DEFAULT");

    cardLayout.show(mainPanel, "DEFAULT"); // Mostrar el panel por defecto
  }

  public void addComponents() {
    add(sidebar, BorderLayout.WEST);
    add(mainPanel, BorderLayout.CENTER);
  }

  public void switchPanel(String panelIdentifier) {
    cardLayout.show(mainPanel, panelIdentifier);
  }

  public void addPanel(JPanel panel, String panelIdentifier) {
    boolean panelExists = false;
    for (Component comp : mainPanel.getComponents()) {
      if (comp.getName() != null && comp.getName().equals(panelIdentifier)) {
        panelExists = true;
        break;
      }
    }

    if (!panelExists) {
      panel.setName(panelIdentifier); // Asigna un nombre al panel para identificarlo
      mainPanel.add(panel, panelIdentifier);
    }
  }
}
