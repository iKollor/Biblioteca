package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import model.Autor;
import model.Estudiante;
import model.Libro;
import model.Prestamo;
import model.Usuario;
import model.interfaces.Estado;

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

  public Usuario authLogin(String email, String password) throws SQLException {

    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn
            .prepareStatement("SELECT * FROM usuarios WHERE email = ? AND password = ?")) {

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
        List<Prestamo> prestamos = getPrestamos(user).stream()
            .filter(prestamo -> !prestamo.isDevuelto()).toList();

        System.out.println("Prestamos del usuario que no han sido devueltos: " + prestamos);

        List<Libro> librosPrestados = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
          System.out.println("Registro encontrado: " + prestamo.getLibro().getTitulo());
          System.out.println("Estado del libro: " + (prestamo.isDevuelto() ? "DEVUELTO" : "PRESTADO"));
          librosPrestados.add(prestamo.getLibro());
        }
        user.setLibrosPrestados(librosPrestados);
        System.out.println("Libros prestados: " + librosPrestados);
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
    String sql = "SELECT l.*, a.id AS autor_id, a.nombre AS autor_nombre, a.nacionalidad FROM libros l JOIN autores a ON l.autor_id = a.id";

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
        libro.setISBN(rs.getInt(COL_ISBN));
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
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql)) {
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

  public void addLibro(Libro libro) throws SQLException {
    Connection cn = null;
    PreparedStatement pstLibro = null;
    PreparedStatement pstCopia = null;
    PreparedStatement pstCheckISBN = null;
    ResultSet rs = null;

    String sqlLibro = "INSERT INTO libros (titulo, autor_id, year, ISBN, pages, edition) VALUES (?, ?, ?, ?, ?, ?)";

    try {
      cn = Conexion.Conectar();
      cn.setAutoCommit(false);

      // Comprobación de ISBN Existente
      boolean isbnExiste = isLibroRegistrado(libro.getISBN());

      // Inserción del Libro en caso de que no exista en la base de datos
      if (!isbnExiste) {
        pstLibro = cn.prepareStatement(sqlLibro);
        pstLibro.setString(1, libro.getTitulo());
        pstLibro.setInt(2, libro.getAutor().getId());
        pstLibro.setInt(3, libro.getAnioPublicacion().getValue());
        pstLibro.setInt(4, libro.getISBN());
        pstLibro.setInt(5, libro.getPaginas());
        pstLibro.setInt(6, libro.getEdicion());
        pstLibro.execute();

        System.out.println("Libro registrado");
      } else {
        System.out.println("El libro ya existe");
        pstLibro = cn.prepareStatement("UPDATE libros SET saldo = saldo + 1 WHERE ISBN = ?");
        pstLibro.setInt(1, libro.getISBN());
        pstLibro.execute();
        System.out.println("Saldo actualizado");
      }

      cn.commit();
    } catch (SQLException e) {
      if (cn != null)
        cn.rollback();
      throw e;
    } finally {
      if (pstLibro != null)
        pstLibro.close();
      if (pstCopia != null)
        pstCopia.close();
      if (pstCheckISBN != null)
        pstCheckISBN.close();
      if (rs != null)
        rs.close();
      if (cn != null)
        cn.close();
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

  public boolean isLibroRegistrado(int isbn) throws SQLException {
    String sql = "SELECT COUNT(*) FROM libros WHERE ISBN = ?";
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setInt(1, isbn);
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

  public Libro getLibro(int iSBN) throws SQLException {
    System.out.println("Buscando libro con ISBN: " + iSBN);
    String sql = "SELECT l.*, a.id AS autor_id, a.nombre AS autor_nombre, a.nacionalidad FROM libros l JOIN autores a ON l.autor_id = a.id WHERE l.ISBN = ?";
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setInt(1, iSBN);
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
          libro.setISBN(rs.getInt(COL_ISBN));
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

  public void getAutor(Autor autor) throws SQLException {
    String sql = "INSERT INTO autores (nombre, nacionalidad) VALUES (?, ?)";
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setString(1, autor.getNombre());
      pst.setString(2, autor.getNacionalidad());
      pst.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  public List<Prestamo> getPrestamos(Usuario user) throws SQLException {
    System.out.println("Obteniendo Préstamos");
    String sql = "SELECT * FROM prestamos WHERE user_id = ?";
    List<Prestamo> prestamos = new ArrayList<>();
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setInt(1, user.getId());
      try (ResultSet rs = pst.executeQuery()) {
        while (rs.next()) {
          Prestamo prestamo = new Prestamo();
          prestamo.setId(rs.getInt("prestamo_id"));

          // Manejar posibles fechas nulas
          Date fechaPrestamo = rs.getDate("fecha_prestamo");
          if (fechaPrestamo != null) {
            prestamo.setFechaPrestamo(fechaPrestamo.toLocalDate());
          }

          Date fechaDevolucion = rs.getDate("fecha_devolucion");
          if (fechaDevolucion != null) {
            prestamo.setFechaDevolucion(fechaDevolucion.toLocalDate());
          }
          prestamo.setDevuelto(rs.getBoolean("devuelto"));

          // Obtener el libro
          Libro libro = getLibro(rs.getInt("libro_id"));
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

  public List<Prestamo> getPrestamos() throws SQLException {
    String sql = "SELECT * FROM prestamos";
    List<Prestamo> prestamos = new ArrayList<>();
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery()) {
      while (rs.next()) {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(rs.getInt("prestamo_id"));

        Date fechaPrestamo = rs.getDate("fecha_prestamo");
        if (fechaPrestamo != null) {
          prestamo.setFechaPrestamo(fechaPrestamo.toLocalDate());
        }
        Date fechaDevolucion = rs.getDate("fecha_devolucion");
        if (fechaDevolucion != null) {
          prestamo.setFechaDevolucion(fechaDevolucion.toLocalDate());
        }
        prestamo.setDevuelto(rs.getBoolean("devuelto"));
        prestamo.setLibro(getLibro(rs.getInt("libro_id")));
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
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setInt(1, id);
      try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
          Usuario user = new Estudiante();
          user.setNombre(rs.getString("nombre"));
          user.setApellido(rs.getString("apellido"));
          user.setEmail(rs.getString("email"));
          user.setDni(rs.getString("dni"));
          user.setId(rs.getInt("id"));
          // obtener prestamos
          List<Prestamo> prestamos = getPrestamos(user);
          List<Libro> librosPrestados = new ArrayList<>();
          for (Prestamo prestamo : prestamos) {
            librosPrestados.add(prestamo.getLibro());
          }
          user.setLibrosPrestados(librosPrestados);
          System.out.println("Usuario encontrado: " + user.getNombre() + " " + user.getApellido());
          System.out.println("Prestamos: " + librosPrestados);
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
    String sqlPrestamo = "INSERT INTO prestamos (fecha_prestamo, devuelto, libro_id, user_id) VALUES (?, ?, ?, ?)";

    Connection cn = null;
    PreparedStatement pst = null;

    try {
      // Iniciar la conexión y configurar para manejar transacciones
      cn = Conexion.Conectar();
      cn.setAutoCommit(false); // Deshabilitar el auto-commit para manejar transacciones manualmente

      // Preparar y ejecutar la inserción del préstamo
      pst = cn.prepareStatement(sqlPrestamo);
      pst.setDate(1, java.sql.Date.valueOf(prestamo.getFechaPrestamo()));
      pst.setBoolean(2, prestamo.isDevuelto());
      pst.setInt(3, prestamo.getLibro().getISBN());
      pst.setInt(4, prestamo.getUsuario().getId());
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
    Connection cn = null;
    PreparedStatement pst = null;
    PreparedStatement pstCopia = null;

    try {
      cn = Conexion.Conectar();
      cn.setAutoCommit(false);

      pst = cn.prepareStatement(sql);
      pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
      pst.setBoolean(2, true);
      pst.setInt(3, selectedPrestamo.getId());
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
      closeResources(cn, pstCopia, null);
    }
  }

  private void closeResources(Connection cn, PreparedStatement pst, ResultSet rs) throws SQLException {
    if (rs != null)
      rs.close();
    if (pst != null)
      pst.close();
    if (cn != null) {
      cn.setAutoCommit(true); // Restaurar auto-commit
      cn.close();
    }
  }

  public void clearPrestamos(int user_id) throws SQLException {
    System.out.println("Limpiando prestamos del usuario " + user_id);
    String sql = "DELETE FROM prestamos WHERE user_id = ? AND devuelto = true";
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setInt(1, user_id);
      pst.execute();
      System.out.println("Prestamos eliminados");
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

}
