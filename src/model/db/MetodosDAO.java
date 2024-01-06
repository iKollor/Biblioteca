package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import model.Autor;
import model.Estudiante;
import model.Libro;
import model.Usuario;
import model.interfaces.Estado;

public class MetodosDAO {

  // Constantes para los nombres de las columnas

  // Libro
  private static final String COL_ISBN = "ISBN";
  private static final String COL_TITULO = "titulo";
  private static final String COL_ESTADO = "estado";
  private static final String COL_ANIO = "anio";
  private static final String COL_PAGINAS = "pages";
  private static final String COL_EDITION = "edition";

  // Autor
  private static final String COL_AUTOR_ID = "id";
  private static final String COL_AUTOR_NOMBRE = "nombre";
  private static final String COL_AUTOR_NACIONALIDAD = "nacionalidad";

  public boolean Register(Usuario user) throws Exception {

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
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
  }

  public Usuario authLogin(String email, String password) throws Exception {

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
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return null;
  }

  public List<Libro> getLibros() throws Exception {
    List<Libro> libros = new ArrayList<>();
    String sql = "SELECT l.*, a.nombre AS autor_nombre, a.nacionalidad FROM libros l JOIN autores a ON l.autor_id = a.id";

    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery()) {

      while (rs.next()) {
        Libro libro = new Libro();
        libro.setISBN(rs.getInt(COL_ISBN));
        libro.setTitulo(rs.getString(COL_TITULO));
        libro.setEstado(Estado.valueOf(rs.getString(COL_ESTADO)));
        libro.setAnio(Year.of(rs.getInt(COL_ANIO)));
        libro.setPaginas(rs.getInt(COL_PAGINAS));
        libro.setEdition(rs.getInt(COL_EDITION));

        Autor autor = new Autor();
        autor.setNombre(rs.getString("autor_nombre"));
        autor.setNacionalidad(rs.getString("nacionalidad"));
        libro.setAutor(autor);

        libros.add(libro);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return libros;
  }

  public Autor getAutor(int id) throws Exception {
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
          return autor;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return null;
  }

  public List<Libro> getLibrosEscritos(Autor autor) throws Exception {
    String sql = "SELECT * FROM libros WHERE autor_id = ?";
    List<Libro> libros = new ArrayList<>();
    try (Connection cn = Conexion.Conectar();
        PreparedStatement pst = cn.prepareStatement(sql)) {
      pst.setInt(1, autor.getId());
      try (ResultSet rs = pst.executeQuery()) {
        while (rs.next()) {
          Libro libro = new Libro();
          libro.setISBN(rs.getInt(COL_ISBN));
          libro.setTitulo(rs.getString(COL_TITULO));
          libro.setEstado(Estado.valueOf(rs.getString(COL_ESTADO)));
          libro.setAnio(Year.of(rs.getInt(COL_ANIO)));
          libro.setPaginas(rs.getInt(COL_PAGINAS));
          libro.setEdition(rs.getInt(COL_EDITION));
          libro.setAutor(autor);
          libros.add(libro);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e; // Propagar la SQLException
    }
    return libros;
  }

}
