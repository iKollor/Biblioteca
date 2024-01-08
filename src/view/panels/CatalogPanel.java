package view.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controller.HomeController;
import view.components.LibroCard;
import view.components.SearchPanel;
import view.utils.WrapLayout;
import view.utils.fonts.SFProFont;

public class CatalogPanel extends JPanel {

  private JPanel gridPanel; // Panel para la grilla
  private SearchPanel searchPanel; // Panel para la búsqueda

  private JPanel northContainer;
  private JScrollPane scrollPane;

  List<LibroCard> libroCard;

  public CatalogPanel() {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    setName(HomeController.Panel.CATALOGO.toString());

    initComponents();
    addComponents();
  }

  public void initComponents() {
    // Título
    JLabel lblTitulo = new JLabel("Catálogo de Libros", SwingConstants.LEFT);
    lblTitulo.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 20));

    // NORTH Container
    northContainer = new JPanel();
    northContainer.setLayout(new GridLayout(2, 1, 10, 10));
    northContainer.add(lblTitulo);

    // Crear y añadir SearchPanel
    searchPanel = new SearchPanel();
    northContainer.add(searchPanel);

    // Panel para la grilla con WrapLayout
    gridPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 10, 10));

    // ScrollPane que contiene el panel de la grilla
    scrollPane = new JScrollPane(gridPanel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
  }

  public void addComponents() {
    add(northContainer, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
  }

  public void updateCards() {
    gridPanel.removeAll();
    for (LibroCard card : libroCard) {
      gridPanel.add(card);
    }
    gridPanel.revalidate();
    gridPanel.repaint();
  }

  public List<LibroCard> getLibroCard() {
    return libroCard;
  }

  public void setLibroCard(List<LibroCard> libroCard) {
    this.libroCard = libroCard;
  }

}