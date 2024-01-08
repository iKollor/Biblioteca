package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;

import view.components.Sidebar;
import view.utils.fonts.SFProFont;

public class HomeView extends JFrame {

  private Sidebar sidebar;
  private JLabel lblDefault;
  private JPanel mainPanel;

  public HomeView() {
    setTitle("Sistema de Gestión de Biblioteca");
    setSize(900, 700);
    setMinimumSize(getSize());
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    initComponents();
    addComponents();
  }

  public void initComponents() {
    sidebar = new Sidebar();

    lblDefault = new JLabel("<html><center>Seleccione una opción del menú lateral</center></html>", JLabel.CENTER);
    lblDefault.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 22));

    mainPanel = new JPanel(new CardLayout());
    mainPanel.add(lblDefault, "DEFAULT");
  }

  public void addComponents() {
    add(sidebar, BorderLayout.WEST);
    add(mainPanel, BorderLayout.CENTER);
  }

  // Métodos para interactuar con sidebar
  public Sidebar getSidebar() {
    return sidebar;
  }

  public void switchPanel(String panelIdentifier) {
    ((CardLayout) mainPanel.getLayout()).show(mainPanel, panelIdentifier);
  }

  public void addPanel(JPanel panel, String panelIdentifier) {
    mainPanel.add(panel, panelIdentifier);
  }

  public void removePanel(JPanel panel) {
    mainPanel.remove(panel);
  }

  public void updateVisiblePanel() {
    mainPanel.revalidate();
    mainPanel.repaint();
    revalidate();
    repaint();
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }
}
