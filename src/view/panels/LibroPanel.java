package view.panels;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.HomeController;
import controller.services.BibliotecaServicio.Estado;
import model.Libro;
import model.Usuario;
import view.components.LibroCard;
import view.utils.fonts.SFProFont;

public class LibroPanel extends JPanel {

  private JPanel topContainer, bottomContainer;

  private Libro libro;
  private Estado estado;
  private Usuario user;

  private JButton btnPrestar, btnDevolver, btnAddCopia, btnRemoveCopia;

  private LibroCard libroCard = null;

  private JLabel lblTitulo, lblAutor, lblAnioPublicacion, lblEdicion, lblPaginas, lblSaldo;

  public LibroPanel(LibroCard libroCard, Usuario user) {
    this.libroCard = libroCard;
    this.libro = libroCard.getLibro();
    this.estado = libroCard.getEstado();
    this.user = user;

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    setName(HomeController.Panel.LIBRO_INFO.toString());

    if (this.libroCard != null) {
      initComponents();
      addComponents();
    } else {
      System.out.println("LibroCard es null");
    }
  }

  public void initComponents() {
    // Contenedor Norte
    topContainer = new JPanel();
    topContainer.setLayout(new GridLayout(1, 2, 60, 2));
    // Panel izquierdo
    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    libroCard.setPreferredSize(new java.awt.Dimension(300, 400));
    leftPanel.add(libroCard);
    // Panel derecho
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    JPanel infoContainer = new JPanel(new GridLayout(0, 1, 5, 5));

    String TituloString =
        String.format("<html><div WIDTH=%d>%s</div></html>", 300, libro.getTitulo());
    lblTitulo = new JLabel(TituloString, SwingConstants.LEFT);
    lblTitulo.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 22));

    JPanel container = new JPanel(new GridLayout(0, 1, 5, 5));

    lblAutor = new JLabel(libro.getAutor().getNombre(), SwingConstants.LEFT);
    lblAutor.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 8));

    lblAnioPublicacion = new JLabel(
        "<html><b>Año de Publicación: </b>" + libro.getAnioPublicacion().toString() + "</html>",
        SwingConstants.LEFT);
    lblAnioPublicacion.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 8));

    lblEdicion =
        new JLabel(String.valueOf("<html><b>Edición: </b>" + libro.getEdicion() + "</html>"),
            SwingConstants.LEFT);
    lblEdicion.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 8));

    lblPaginas =
        new JLabel(String.valueOf("<html><b>No. de Paginas: </b>" + libro.getPaginas() + "</html>"),
            SwingConstants.LEFT);
    lblPaginas.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 8));

    lblSaldo =
        new JLabel(String.valueOf("<html><b>No. de Copias: </b>" + libro.getSaldo() + "</html>"),
            SwingConstants.LEFT);
    lblSaldo.setFont(new Font(SFProFont.SF_PRO_REGULAR, Font.PLAIN, 8));

    container.add(lblAutor);
    container.add(lblAnioPublicacion);
    container.add(lblEdicion);
    container.add(lblPaginas);
    container.add(lblSaldo);

    infoContainer.add(lblTitulo);
    infoContainer.add(container);

    rightPanel.add(infoContainer);

    // Agregar paneles al contenedor norte
    topContainer.add(leftPanel);
    topContainer.add(rightPanel);

    // Contenedor Sur
    bottomContainer = new JPanel();
    bottomContainer.setLayout(new GridLayout(1, 2, 2, 2));
    JPanel buttonsContainer = new JPanel(new FlowLayout());

    // Botones usuario
    btnPrestar = new JButton("Prestar");
    btnPrestar.setPreferredSize(new java.awt.Dimension(200, 80));
    btnPrestar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btnDevolver = new JButton("Devolver");
    btnDevolver.setPreferredSize(new java.awt.Dimension(200, 80));
    btnDevolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    btnPrestar.setEnabled(estado != Estado.AGOTADO);
    btnDevolver.setEnabled(estado != Estado.DISPONIBLE);

    // Botones admin
    btnAddCopia = new JButton("Agregar Copia");
    btnAddCopia.setPreferredSize(new java.awt.Dimension(200, 80));
    btnAddCopia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btnRemoveCopia = new JButton("Eliminar Copia");
    btnRemoveCopia.setPreferredSize(new java.awt.Dimension(200, 80));
    btnRemoveCopia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btnRemoveCopia.setEnabled(!estado.equals(Estado.AGOTADO));

    if (user == null) {
      buttonsContainer.add(btnAddCopia);
      buttonsContainer.add(btnRemoveCopia);
    } else {
      buttonsContainer.add(btnPrestar);
      // buttonsContainer.add(btnDevolver);
    }

    bottomContainer.add(buttonsContainer);
  }

  public void addComponents() {
    add(topContainer);
    add(bottomContainer);
  }

  public Libro getLibro() {
    return libro;
  }

  public void setLibro(Libro libro) {
    this.libro = libro;
  }

  public JButton getBtnPrestar() {
    return btnPrestar;
  }

  public JButton getBtnDevolver() {
    return btnDevolver;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public LibroCard getLibroCard() {
    return libroCard;
  }

  public void setLibroCard(LibroCard libroCard) {
    this.libroCard = libroCard;
  }

  public JButton getBtnAddCopia() {
    return btnAddCopia;
  }

  public void setBtnAddCopia(JButton btnAddCopia) {
    this.btnAddCopia = btnAddCopia;
  }

  public JButton getBtnRemoveCopia() {
    return btnRemoveCopia;
  }

  public void setBtnRemoveCopia(JButton btnRemoveCopia) {
    this.btnRemoveCopia = btnRemoveCopia;
  }

}
