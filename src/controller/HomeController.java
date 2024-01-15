package controller;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;

import controller.services.BibliotecaServicio;
import controller.services.BibliotecaServicio.Estado;
import controller.utils.IntFilter;
import model.Autor;
import model.Libro;
import model.Prestamo;
import model.Usuario;
import view.HomeView;
import view.components.LibroCard;
import view.panels.AddAutorPanel;
import view.panels.AddLibroPanel;
import view.panels.CatalogPanel;
import view.panels.LibroPanel;
import view.panels.PrestamosPanel;
import view.utils.fonts.SFProFont;

public class HomeController implements ActionListener {

  public enum Panel {
    CATALOGO, PRESTAMOS, ADD_LIBRO, ADD_AUTOR, LIBRO_INFO
  }

  private HomeView homeView;
  private AppController controller;
  private Usuario user;

  private CatalogPanel catalogoView = null;
  private LibroPanel libroView = null;
  private AddLibroPanel addLibroView = null;
  private AddAutorPanel addAutorView = null;
  private PrestamosPanel prestamosView = null;

  private Panel currentPanel = null;
  private BibliotecaServicio service;

  public HomeController(HomeView homeView, AppController controller) {
    this.homeView = homeView;
    this.controller = controller;

    user = controller.getUser();

    service = new BibliotecaServicio(controller.getDao());

    actualizarSidebar(user);

    homeView.getSidebar().setListeners(this);
  }

  public void actualizarSidebar(Usuario user) {
    // Actualizar UserInfoContainer
    JPanel userInfoContainer = homeView.getSidebar().getUserInfoContainer();
    userInfoContainer.removeAll();
    String userNombre =
        user == null ? "Administrador" : user.getNombre() + " " + user.getApellido();
    JLabel lblNombre = new JLabel(userNombre, SwingConstants.CENTER);
    lblNombre.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 10));
    userInfoContainer.add(lblNombre);

    // Actualizar ButtonsContainer
    JPanel buttonsContainer = homeView.getSidebar().getButtonsContainer();
    buttonsContainer.removeAll();
    if (user == null) {
      homeView.getSidebar().añadirBotones(homeView.getSidebar().getBotonesAdmin());
    } else {
      homeView.getSidebar().añadirBotones(homeView.getSidebar().getBotonesUsuario());
    }
  }

  public void showPanel(Panel panel) {

    if (currentPanel != null && currentPanel == panel)
      return;

    switch (panel) {
      case CATALOGO:
        List<Libro> libros = service.getLibros(); // Obtener la lista de libros
        List<LibroCard> libroCards = createCards(libros);
        catalogoView = new CatalogPanel();
        catalogoView.setLibroCard(libroCards);
        catalogoView.updateCards();
        catalogoView.getSearchPanel().getBtnSearch().addActionListener(this);
        homeView.addPanel(catalogoView, "CATALOGO");

        homeView.switchPanel("CATALOGO");
        break;
      case ADD_LIBRO:
        if (addLibroView == null) {
          addLibroView = new AddLibroPanel();
          addLibroView.getBtnAddLibro().addActionListener(this);
          addLibroView.setAutores(service.getAutores());
          addLibroFormEvents(service);
          addLibroView.clearForm();
          addLibroView.getTxtISBN().setText("");
          homeView.addPanel(addLibroView, "ADD_LIBRO");
        }
        homeView.switchPanel("ADD_LIBRO");
        break;
      case ADD_AUTOR:
        if (addAutorView == null) {
          addAutorView = new AddAutorPanel();
          addAutorView.clearForm();
          homeView.addPanel(addAutorView, "ADD_AUTOR");
          addAutorView.getBtnAddAutor().addActionListener(this);
        }
        homeView.switchPanel("ADD_AUTOR");
        break;
      case PRESTAMOS:
        if (prestamosView == null) {
          prestamosView = new PrestamosPanel(user);
          List<Prestamo> prestamos =
              user == null ? service.getPrestamos() : service.getPrestamos(user);
          System.out.println(prestamos);
          prestamosView.setPrestamosData(prestamos);
          prestamosView.getBtnDevolver().addActionListener(this);
          prestamosView.getBtnClearPrestamos().addActionListener(this);
          // Habilitar el botón de devolver cuando se seleccione una fila y el prestamo no
          // este devuelto
          prestamosView.getTable().getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = prestamosView.getTable().getSelectedRow();
            if (selectedRow >= 0) {
              Prestamo selectedPrestamo = prestamosView.getPrestamosList().get(selectedRow);
              if (selectedPrestamo.isDevuelto()) {
                prestamosView.getBtnDevolver().setEnabled(false);
              } else {
                prestamosView.getBtnDevolver().setEnabled(true);
              }
            } else {
              prestamosView.getBtnDevolver().setEnabled(false);
            }
          });
          homeView.addPanel(prestamosView, "PRESTAMOS");
        }
        homeView.switchPanel("PRESTAMOS");
        break;
      default:
        throw new IllegalArgumentException("Panel no encontrado");
    }

    // Cerrar vistas
    if (currentPanel != null && currentPanel != panel) {
      switch (currentPanel) {
        case CATALOGO:
          homeView.removePanel(catalogoView);
          catalogoView = null;
          break;
        case ADD_LIBRO:
          homeView.removePanel(addLibroView);
          addLibroView = null;
          break;
        case ADD_AUTOR:
          homeView.removePanel(addAutorView);
          addAutorView = null;
          break;
        case LIBRO_INFO:
          homeView.removePanel(libroView);
          libroView = null;
          break;
        case PRESTAMOS:
          homeView.removePanel(prestamosView);
          prestamosView = null;
          break;
        default:
          break;
      }
    }
    currentPanel = panel;
  }

  private List<LibroCard> createCards(List<Libro> libros) {
    List<LibroCard> libroCards = new ArrayList<>(); // Lista de LibroCard
    for (Libro libro : libros) {
      Estado estadoLibro = service.determinarEstado(libro);
      LibroCard libroCard = new LibroCard(libro, estadoLibro);
      libroCard.setCursor(new Cursor(Cursor.HAND_CURSOR));
      libroCard.setToolTipText("Ver detalles del libro");
      libroCard.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          if (currentPanel == Panel.LIBRO_INFO)
            return;
          System.out.println("Libro Mostrado: " + libroCard.getLibro().getTitulo());
          System.out.println("con estado: " + libroCard.getEstado());
          showLibroInfo(libroCard);
        }
      });
      libroCards.add(libroCard);
    }
    return libroCards;
  }

  private void showLibroInfo(LibroCard libroCard) {
    if (libroView == null) {
      libroView = new LibroPanel(libroCard, user);
      libroView.getLibroCard().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      libroView.getLibroCard().setToolTipText(null);

      // agregar listeners
      libroView.getBtnPrestar().addActionListener(this);
      libroView.getBtnDevolver().addActionListener(this);
      libroView.getBtnAddCopia().addActionListener(this);
      libroView.getBtnRemoveCopia().addActionListener(this);

      homeView.addPanel(libroView, "LIBRO_INFO");
      currentPanel = Panel.LIBRO_INFO;
    }
    homeView.switchPanel("LIBRO_INFO");
  }

  private void autoCompleteForm(String ISBN) {
    Libro libro = service.getLibro(ISBN);
    addLibroView.autoCompleteForm(libro);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    Object source = e.getSource();

    if (source == homeView.getSidebar().getBtnCatalogo()) {
      showPanel(Panel.CATALOGO);
    } else if (source == homeView.getSidebar().getBtnPrestamos()) {
      showPanel(Panel.PRESTAMOS);
    } else if (source == homeView.getSidebar().getBtnAddLibro()) {
      showPanel(Panel.ADD_LIBRO);
    } else if (source == homeView.getSidebar().getBtnAddAutor()) {
      showPanel(Panel.ADD_AUTOR);
    } else if (source == homeView.getSidebar().getBtnLogout()) {
      controller.setUser(null);
      controller.showView(AppController.ViewType.LOGIN);

    } else if (addLibroView != null && source == addLibroView.getBtnAddLibro()) {
      Libro libro = getLibroForm();
      addLibro(libro);
    } else if (addAutorView != null && source == addAutorView.getBtnAddAutor()) {
      Autor autor = getAutorForm();
      addAutor(autor);
    }
    // Verifica que libroView no sea null antes de intentar usarlo
    if (libroView != null) {
      if (source == libroView.getBtnPrestar()) {
        // Lógica para manejar el botón de prestar
        addPrestamo();
      } else if (source == libroView.getBtnDevolver()) {
        // Lógica para manejar el botón de devolver
        devolverPrestamo();
      } else if (source == libroView.getBtnAddCopia()) {
        addCopia();
      } else if (source == libroView.getBtnRemoveCopia()) {
        removeCopia();
      }
    }

    if (prestamosView != null) {
      if (source == prestamosView.getBtnDevolver()) {
        devolverPrestamo();
      } else if (source == prestamosView.getBtnClearPrestamos()) {
        clearPrestamos();
      }
    }

    if(catalogoView != null) {
      if(source == catalogoView.getSearchPanel().getBtnSearch()) {
        searchLibros();
      }
    }
  }

  private void searchLibros() {
    String titulo = catalogoView.getSearchPanel().getSearchText();
    String autor = catalogoView.getSearchPanel().getAutorSearchText();
    Estado estado = catalogoView.getSearchPanel().getEstadoSearch();
    List<Libro> libros = service.searchLibros(titulo, autor, estado);
    List<LibroCard> libroCards = createCards(libros);
    catalogoView.setLibroCard(libroCards);
    catalogoView.updateCards();
  }

  private void removeCopia() {
    Libro libro = libroView.getLibroCard().getLibro();
    service.removeCopia(libro);
    showPanel(Panel.CATALOGO);
  }

  private void addCopia() {
    Libro libro = libroView.getLibroCard().getLibro();
    service.addCopia(libro);
    showPanel(Panel.CATALOGO);
  }

  private void devolverPrestamo() {
    Prestamo prestamo =
        prestamosView.getPrestamosList().get(prestamosView.getTable().getSelectedRow());
    prestamo.setFechaDevolucion(getCurrentTimestamp());
    service.devolucion(prestamo);
    List<String> librosPrestados = new ArrayList<>(user.getLibrosPrestados());
    librosPrestados.removeIf(isbn -> isbn.equals(prestamo.getLibro().getISBN()));
    user.setLibrosPrestados(librosPrestados);
    prestamosView.setPrestamosData(service.getPrestamos(user));
    prestamosView.updateTableView();
    homeView.updateVisiblePanel();
  }

  private void addPrestamo() {

    if (libroView.getLibroCard().getLibro().getSaldo() <= 0) {
      JOptionPane.showMessageDialog(homeView, "No hay copias disponibles",
          "Error al agregar prestamo", JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (user == null) {
      JOptionPane.showMessageDialog(homeView, "Los administradores no pueden prestar libros",
          "Error al agregar prestamo", JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (user.puedePrestar() == false) {
      JOptionPane
          .showMessageDialog(homeView,
              "No puedes prestar más libros, el máximo de libros que puedes prestar es: "
                  + user.MAX_LIBROS_PRESTADOS,
              "Error al agregar prestamo", JOptionPane.ERROR_MESSAGE);
      return;
    }

    Prestamo prestamo = new Prestamo();
    prestamo.setLibro(libroView.getLibroCard().getLibro());
    prestamo.setUsuario(user);
    prestamo.setFechaPrestamo(getCurrentTimestamp());
    service.addPrestamo(prestamo);
    // Añadir libros prestados al array de libros prestados del usuario
    List<String> librosPrestados = new ArrayList<>(user.getLibrosPrestados());
    librosPrestados.add(prestamo.getLibro().getISBN());
    user.setLibrosPrestados(librosPrestados);
    showPanel(Panel.CATALOGO);
  }

  private Timestamp getCurrentTimestamp() {
    LocalDateTime now = LocalDateTime.now();
    return Timestamp.valueOf(now);
  }

  private void clearPrestamos() {
    service.clearPrestamos(user.getId());
    // Actualizar la tabla de prestamos
    prestamosView.setPrestamosData(service.getPrestamos(user));
    prestamosView.updateTableView();
    homeView.updateVisiblePanel();
  }

  private void addAutor(Autor autor) {
    service.addAutor(autor);
    addAutorView.clearForm();
  }

  private Autor getAutorForm() {
    try {
      Autor autor = new Autor();
      autor.setNombre(addAutorView.getTxtNombre().getText());
      autor.setNacionalidad(addAutorView.getTxtNacionalidad().getText());
      return autor;
    } catch (Exception e) {
      return null;
    }
  }

  private Libro getLibroForm() {
    try {
      if (addLibroView == null)
        return null;
      Libro libro = new Libro();
      libro.setTitulo(addLibroView.getTxtTitulo().getText());
      libro.setAnioPublicacion(Year.parse(addLibroView.getTxtYear().getText()));
      libro.setISBN(addLibroView.getTxtISBN().getText());
      libro.setPaginas(Integer.parseInt(addLibroView.getTxtPaginas().getText()));
      libro.setEdicion(Integer.parseInt(addLibroView.getTxtEdicion().getText()));
      libro.setAutor(addLibroView.getSelectedAutor());
      return libro;
    } catch (Exception e) {
      JOptionPane.showMessageDialog(homeView, "Error al agregar libro: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }

  private void addLibro(Libro libro) {
    service.addLibro(libro);
    addLibroView.clearForm();
  }

  private void addLibroFormEvents(BibliotecaServicio service) {
    PlainDocument doc = (PlainDocument) addLibroView.getTxtISBN().getDocument();
    doc.setDocumentFilter(new IntFilter());
    addLibroView.getTxtISBN().getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent e) {
        checkISBN();
      }

      public void removeUpdate(DocumentEvent e) {
        checkISBN();
      }

      public void insertUpdate(DocumentEvent e) {
        checkISBN();
      }

      private void checkISBN() {
        String isbn = addLibroView.getTxtISBN().getText();
        if (isbn.length() >= 10) {
          try {
            if (service.isLibroRegistrado(isbn)) {
              JOptionPane.showMessageDialog(homeView, "El libro ya está registrado!");
              autoCompleteForm(isbn);
              // Deshabilitar los campos
              addLibroView.setFormEditable(false);
              addLibroView.getBtnAddLibro().setText("Añadir copia");
            } else {
              addLibroView.setFormEditable(true);
              addLibroView.getBtnAddLibro().setText("Añadir libro");
            }
          } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(homeView, "El ISBN debe ser menor a 10 dígitos", "Error",
                JOptionPane.ERROR_MESSAGE);
          }
        } else {
          addLibroView.clearForm();
          addLibroView.setFormEditable(true);
          addLibroView.getBtnAddLibro().setText("Añadir libro");
        }
      }
    });
  }

}
