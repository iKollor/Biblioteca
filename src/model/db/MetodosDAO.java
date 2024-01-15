package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import controller.services.BibliotecaServicio.Estado;
import model.Autor;
import model.Estudiante;
import model.Libro;
import model.Prestamo;
import model.Usuario;

public class MetodosDAO {

  // Constantes para los nombres de las columnas

  // Libro
  private static final String COL_ISBN = "ISBN";
  private static final String COL_TITULO = "titulo";
  private static final String COL_YEAR = "year";
  private static final String COL_PAGINAS = "pages";
  private static final String COL_EDITION = "edition";
  private static final String COL_SALDO = "saldo";

  // Autor
  private static final String COL_AUTOR_ID = "id";
  private static final String COL_AUTOR_NOMBRE = "nombre";
  private static final String COL_AUTOR_NACIONALIDAD = "nacionalidad";

  protected static final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

  public boolean Register(Usuario user) throws SQLException {
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(
            "INSERT INTO usuarios (nombre, apellido, email, password, dni) VALUES (?, ?, ?, ?, ?)")) {

      pst.setString(1, user.getNombre());
      pst.setString(2, user.getApellido());
      pst.setString(3, user.getEmail());
      // TODO: Encriptar la contraseña
      pst.setString(4, new String(user.getPassword()));
      pst.setString(5, user.getDni());
      pst.execute();
      System.out.println("Usuario registrado");
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
  }

  public Usuario authLogin(String email, String password) throws SQLException, Exception {
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst =
            cn.prepareStatement("SELECT * FROM usuarios WHERE email = ? AND password = ?")) {

      pst.setString(1, email);
      pst.setString(2, password);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        Usuario user = new Estudiante();
        user.setNombre(rs.getString("nombre"));
        user.setApellido(rs.getString("apellido"));
        user.setEmail(rs.getString("email"));
        user.setDni(rs.getString("dni"));
        user.setId(rs.getInt("id"));
        System.out.println("Usuario encontrado: " + user.getNombre() + " " + user.getApellido());
        // obtener libros prestados
        List<Prestamo> prestamos =
            getPrestamos(user).stream().filter(prestamo -> !prestamo.isDevuelto()).toList();

        System.out.println("Prestamos del usuario que no han sido devueltos: " + prestamos);

        List<Libro> librosPrestados = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
          System.out.println("Registro encontrado: " + prestamo.getLibro().getTitulo());
          librosPrestados.add(prestamo.getLibro());
        }
        user.setLibrosPrestados(librosPrestados.stream().map(libro -> libro.getISBN()).toList());
        System.out.println("Libros prestados del usuario: " + librosPrestados);
        return user;
      }
      System.out.println("Usuario no encontrado");
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return null;
  }

  public List<Libro> getLibros() throws SQLException {
    List<Libro> libros = new ArrayList<>();
    String sql =
        "SELECT l.*, a.id AS autor_id, a.nombre AS autor_nombre, a.nacionalidad FROM libros l JOIN autores a ON l.autor_id = a.id";

    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery()) {

      while (rs.next()) {
        Autor autor = new Autor();
        autor.setId(rs.getInt("autor_id"));
        autor.setNombre(rs.getString("autor_nombre"));
        autor.setNacionalidad(rs.getString("nacionalidad"));

        // Dentro del método getLibros()...
        Libro libro = new Libro();
        libro.setTitulo(rs.getString(COL_TITULO));
        libro.setAutor(autor);
        libro.setAnioPublicacion(Year.of(rs.getInt(COL_YEAR)));
        libro.setISBN(rs.getString(COL_ISBN));
        libro.setPaginas(rs.getInt(COL_PAGINAS));
        libro.setEdicion(rs.getInt(COL_EDITION));
        libro.setSaldo(rs.getInt(COL_SALDO));

        libros.add(libro);
      }
      System.out.println("Libros encontrados: " + libros.size());
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return libros;
  }

  public Autor getAutor(int id) throws SQLException {
    String sql = "SELECT * FROM autores WHERE id = ?";

    try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setInt(1, id);
      try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
          Autor autor = new Autor();
          autor.setId(rs.getInt(COL_AUTOR_ID));
          autor.setNombre(rs.getString(COL_AUTOR_NOMBRE));
          autor.setNacionalidad(rs.getString(COL_AUTOR_NACIONALIDAD));
          // No llamamos a getLibrosEscritos para evitar la carga circular, la carga
          // circular es cuando un objeto tiene una referencia a otro objeto y este otro
          // objeto tiene una referencia al objeto original, por ejemplo, un autor tiene
          // una lista de libros y cada libro tiene una referencia al autor, esto puede
          // causar problemas de memoria y rendimiento

          System.out.println("Autor encontrado");
          return autor;
        }
      }
      System.out.println("Autor no encontrado");
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return null;
  }

  public void addAutor(Autor autor) throws SQLException {
    String sql = "INSERT INTO autores (nombre, nacionalidad) VALUES (?, ?)";
    try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setString(1, autor.getNombre());
      pst.setString(2, autor.getNacionalidad());
      pst.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  public void addLibro(Libro libro) throws SQLException {
    PreparedStatement pstLibro = null;
    PreparedStatement pstCopia = null;
    ResultSet rs = null;
    Connection cn = null;

    String sqlLibro =
        "INSERT INTO libros (titulo, autor_id, year, ISBN, pages, edition) VALUES (?, ?, ?, ?, ?, ?)";

    try {
      cn = Conexion.Conectar();;
      cn.setAutoCommit(false);

      // Comprobación de ISBN Existente
      boolean isbnExiste = isLibroRegistrado(libro.getISBN());

      // Inserción del Libro en caso de que no exista en la base de datos
      if (!isbnExiste) {
        pstLibro = cn.prepareStatement(sqlLibro);
        pstLibro.setString(1, libro.getTitulo());
        pstLibro.setInt(2, libro.getAutor().getId());
        pstLibro.setInt(3, libro.getAnioPublicacion().getValue());
        pstLibro.setString(4, libro.getISBN());
        pstLibro.setInt(5, libro.getPaginas());
        pstLibro.setInt(6, libro.getEdicion());
        pstLibro.execute();

        System.out.println("Libro registrado");
      } else {
        System.out.println("El libro ya existe");
        addCopia(libro);
        return;
      }

      cn.commit();
    } catch (SQLException e) {
      if (cn != null)
        cn.rollback();
      throw e;
    } finally {
      if (pstCopia != null)
        pstCopia.close();
      closeResources(cn, pstLibro, rs);
    }
  }

  public List<Autor> getAutores() throws SQLException {
    List<Autor> autores = new ArrayList<>();
    String sql = "SELECT * FROM autores";
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery()) {
      while (rs.next()) {
        Autor autor = new Autor();
        autor.setId(rs.getInt(COL_AUTOR_ID));
        autor.setNombre(rs.getString(COL_AUTOR_NOMBRE));
        autor.setNacionalidad(rs.getString(COL_AUTOR_NACIONALIDAD));
        autores.add(autor);
      }
      System.out.println("Autores encontrados: " + autores.size());
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return autores;
  }

  public boolean isLibroRegistrado(String isbn) throws SQLException {
    String sql = "SELECT COUNT(*) FROM libros WHERE ISBN = ?";
    try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setString(1, isbn);
      try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
          return rs.getInt(1) > 0;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return false;
  }

  public Libro getLibro(String iSBN) throws SQLException {
    System.out.println("Buscando libro con ISBN: " + iSBN);
    String sql =
        "SELECT l.*, a.id AS autor_id, a.nombre AS autor_nombre, a.nacionalidad FROM libros l JOIN autores a ON l.autor_id = a.id WHERE l.ISBN = ?";
    try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setString(1, iSBN);
      try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
          Autor autor = new Autor();
          autor.setId(rs.getInt("autor_id"));
          autor.setNombre(rs.getString("autor_nombre"));
          autor.setNacionalidad(rs.getString("nacionalidad"));

          Libro libro = new Libro();
          libro.setTitulo(rs.getString(COL_TITULO));
          libro.setAutor(autor);
          libro.setAnioPublicacion(Year.of(rs.getInt(COL_YEAR)));
          libro.setISBN(rs.getString(COL_ISBN));
          libro.setPaginas(rs.getInt(COL_PAGINAS));
          libro.setEdicion(rs.getInt(COL_EDITION));
          libro.setSaldo(rs.getInt(COL_SALDO));

          return libro;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return null;
  }

  public List<Prestamo> getPrestamos(Usuario user) throws SQLException, Exception {
    System.out.println("Obteniendo Préstamos");
    String sql = "SELECT * FROM prestamos WHERE user_id = ?";
    List<Prestamo> prestamos = new ArrayList<>();
    try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setInt(1, user.getId());
      try (ResultSet rs = pst.executeQuery()) {
        while (rs.next()) {
          Prestamo prestamo = new Prestamo();
          prestamo.setId(rs.getInt("prestamo_id"));

          // Manejar posibles fechas nulas
          Timestamp fechaPrestamo = rs.getTimestamp("fecha_prestamo");
          if (fechaPrestamo != null) {
            prestamo.setFechaPrestamo(fechaPrestamo);
          }

          Timestamp fechaDevolucion = rs.getTimestamp("fecha_devolucion");
          if (fechaDevolucion != null) {
            prestamo.setFechaDevolucion(fechaDevolucion);
          }

          prestamo.setDevuelto(rs.getBoolean("devuelto"));

          // Obtener el libro
          Libro libro = getLibro(rs.getString("libro_id"));
          prestamo.setLibro(libro);

          prestamo.setUsuario(user);
          prestamos.add(prestamo);
        }
        System.out.println("Prestamos encontrados: " + prestamos.size());
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return prestamos;
  }

  public List<Prestamo> getPrestamos() throws SQLException, Exception {
    String sql = "SELECT * FROM prestamos";
    List<Prestamo> prestamos = new ArrayList<>();
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery()) {
      while (rs.next()) {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(rs.getInt("prestamo_id"));

        // Manejar posibles fechas nulas
        Timestamp fechaPrestamo = rs.getTimestamp("fecha_prestamo");
        if (fechaPrestamo != null) {
          prestamo.setFechaPrestamo(fechaPrestamo);
        }

        Timestamp fechaDevolucion = rs.getTimestamp("fecha_devolucion");
        if (fechaDevolucion != null) {
          prestamo.setFechaDevolucion(fechaDevolucion);
        }

        prestamo.setDevuelto(rs.getBoolean("devuelto"));
        prestamo.setLibro(getLibro(rs.getString("libro_id")));
        prestamo.setUsuario(getUsuario(rs.getInt("user_id")));
        prestamos.add(prestamo);
      }
      System.out.println("Prestamos encontrados: " + prestamos.size());
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return prestamos;
  }

  private Usuario getUsuario(int id) {
    String sql = "SELECT * FROM usuarios WHERE id = ?";
    try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setInt(1, id);
      try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
          Usuario user = new Estudiante();
          user.setNombre(rs.getString("nombre"));
          user.setApellido(rs.getString("apellido"));
          user.setEmail(rs.getString("email"));
          user.setDni(rs.getString("dni"));
          user.setId(rs.getInt("id"));
          System.out.println("Usuario encontrado: " + user.getNombre() + " " + user.getApellido());
          return user;
        }
      }
      System.out.println("Usuario no encontrado");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void addPrestamo(Prestamo prestamo) throws SQLException {

    System.out.println("Registrando nuevo préstamo del libro: " + prestamo.getLibro().getTitulo());

    String sqlPrestamo =
        "INSERT INTO prestamos (fecha_prestamo, devuelto, libro_id, user_id) VALUES (?, ?, ?, ?)";

    PreparedStatement pst = null;
    Connection cn = Conexion.Conectar();
    try {
      // Iniciar la conexión y configurar para manejar transacciones
      cn = Conexion.Conectar();;
      cn.setAutoCommit(false); // Deshabilitar el auto-commit para manejar transacciones manualmente

      // Preparar y ejecutar la inserción del préstamo
      pst = cn.prepareStatement(sqlPrestamo);
      pst.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
      pst.setBoolean(2, prestamo.isDevuelto());
      pst.setString(3, prestamo.getLibro().getISBN());
      pst.setInt(4, prestamo.getUsuario().getId());

      removeCopia(prestamo.getLibro());

      pst.execute();
      System.out.println("Prestamo registrado");

      cn.commit();
    } catch (SQLException e) {
      // En caso de error, revertir la transacción
      if (cn != null) {
        cn.rollback();
      }
      e.printStackTrace();
      throw e; // Propagar la excepción
    } finally {
      // Cerrar los recursos
      closeResources(cn, pst, null);
    }
  }

  public void devolucion(Prestamo selectedPrestamo) throws SQLException {

    String sql = "UPDATE prestamos SET fecha_devolucion = ?, devuelto = ? WHERE prestamo_id = ?";
    PreparedStatement pst = null;
    Connection cn = Conexion.Conectar();
    try {
      cn.setAutoCommit(false);

      pst = cn.prepareStatement(sql);
      pst.setTimestamp(1, java.sql.Timestamp.valueOf(LocalDateTime.now()));
      pst.setBoolean(2, true);
      pst.setInt(3, selectedPrestamo.getId());

      addCopia(selectedPrestamo.getLibro());
      pst.execute();
      System.out.println("Prestamo actualizado");

      cn.commit();
    } catch (SQLException e) {
      if (cn != null) {
        cn.rollback();
      }
      e.printStackTrace();
      throw e;
    } finally {
      closeResources(cn, pst, null);
    }
  }

  private void closeResources(Connection cn, PreparedStatement pst, ResultSet rs)
      throws SQLException {
    if (rs != null)
      rs.close();
    if (pst != null)
      pst.close();
    if (cn != null) {
      cn.setAutoCommit(true); // Restaurar auto-commit
    }
  }

  public void clearPrestamos(int user_id) throws SQLException {
    System.out.println("Limpiando prestamos del usuario " + user_id);
    String sql = "DELETE FROM prestamos WHERE user_id = ? AND devuelto = true";
    try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setInt(1, user_id);
      pst.execute();
      System.out.println("Prestamos eliminados");
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  public void addCopia(Libro libro) {
    String sql = "UPDATE libros SET saldo = saldo + 1 WHERE ISBN = ?";
    try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setString(1, libro.getISBN());
      pst.execute();
      System.out.println("Copia agregada");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void removeCopia(Libro libro) {
    String sql = "UPDATE libros SET saldo = saldo - 1 WHERE ISBN = ?";
    try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setString(1, libro.getISBN());
      pst.execute();
      System.out.println("Copia eliminada");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Libro> searchLibros(String titulo, String autor, Estado estado) {
    List<Libro> libros = new ArrayList<>();
    String sql =
        "SELECT l.*, a.id AS autor_id, a.nombre AS autor_nombre, a.nacionalidad FROM libros l JOIN autores a ON l.autor_id = a.id WHERE l.titulo LIKE ? AND a.nombre LIKE ? "
            + (estado == Estado.DISPONIBLE ? "AND l.saldo > 0"
                : estado == Estado.AGOTADO ? "AND l.saldo = 0" : "");
    try (Connection cn = Conexion.Conectar(); PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setString(1, "%" + titulo + "%");
      pst.setString(2, "%" + autor + "%");
      try (ResultSet rs = pst.executeQuery()) {
        while (rs.next()) {
          Autor autorObj = new Autor();
          autorObj.setId(rs.getInt("autor_id"));
          autorObj.setNombre(rs.getString("autor_nombre"));
          autorObj.setNacionalidad(rs.getString("nacionalidad"));

          Libro libro = new Libro();
          libro.setTitulo(rs.getString(COL_TITULO));
          libro.setAutor(autorObj);
          libro.setAnioPublicacion(Year.of(rs.getInt(COL_YEAR)));
          libro.setISBN(rs.getString(COL_ISBN));
          libro.setPaginas(rs.getInt(COL_PAGINAS));
          libro.setEdicion(rs.getInt(COL_EDITION));
          libro.setSaldo(rs.getInt(COL_SALDO));

          libros.add(libro);
        }
        System.out.println("Libros encontrados: " + libros.size());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return libros;
  }

}
