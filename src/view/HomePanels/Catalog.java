package view.HomePanels;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Libro;
import view.HomePanels.components.LibroCard;
import view.fonts.SFProFont;

public class Catalog extends JPanel {

  JPanel catalogoContainer;
  JTextField txtSearch;

  public Catalog(List<Libro> libros) {
    initComponents();
    añadirLibros(libros);
  }

  public void initComponents() {

    // Título
    JLabel lblTitulo = new JLabel("Catálogo de Libros", SwingConstants.CENTER);
    lblTitulo.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 20));

    // barra de búsqueda
    JPanel searchContainer = new JPanel();
    JLabel lblSearch = new JLabel("Buscar 🔍");
    txtSearch = new JTextField();
    txtSearch.setToolTipText("Buscar");
    searchContainer.add(lblSearch);
    searchContainer.add(txtSearch);

    // Contenedor de los libros
    catalogoContainer = new JPanel();
    catalogoContainer.setLayout(new GridLayout(0, 3, 10, 10)); // 0 filas significa que se agregan filas dinámicamente

    // Se agregan los componentes al panel
    setLayout(new GridLayout(0, 1, 10, 10));
    add(lblTitulo);
    add(searchContainer);
    add(catalogoContainer);
  }

  public void añadirLibros(List<Libro> libros) {
    for (Libro libro : libros) {
      catalogoContainer.add(new LibroCard(libro));
    }
  }
}