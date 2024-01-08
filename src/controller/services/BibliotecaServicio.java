package controller.services;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.swing.JOptionPane;

import model.Autor;
import model.Libro;
import model.Prestamo;
import model.Usuario;
import model.db.MetodosDAO;
import model.interfaces.Estado;

public class BibliotecaServicio {

  private MetodosDAO dao;

  public BibliotecaServicio(MetodosDAO dao) {
    this.dao = dao;
  }

  public Estado determinarEstado(Libro libro) {
    try {
      return libro.getSaldo() > 0 ? Estado.DISPONIBLE : Estado.AGOTADO;
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al determinar estado: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }

  public List<Libro> getLibros() {
    try {
      return dao.getLibros();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al obtener libros: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }

  public List<Autor> getAutores() {
    try {
      return dao.getAutores();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al obtener autores: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }

  public void addLibro(Libro libro) {
    try {
      dao.addLibro(libro);
      JOptionPane.showMessageDialog(null, "Libro agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al agregar libro: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public boolean isLibroRegistrado(int isbn) {
    try {
      return dao.isLibroRegistrado(isbn);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al verificar libro: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
    return false;
  }

  public Libro getLibro(int iSBN) {
    try {
      return dao.getLibro(iSBN);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al obtener libro: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
    return null;
  }

  public void addAutor(Autor autor) {
    try {
      dao.getAutor(autor);
      JOptionPane.showMessageDialog(null, "Autor agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al agregar autor: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public List<Prestamo> getPrestamos(Usuario user) {
    try {
      return dao.getPrestamos(user);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al obtener prestamos: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }

  public List<Prestamo> getPrestamos() {
    try {
      return dao.getPrestamos();
    } catch (Exception e) {
      e.getStackTrace();
      JOptionPane.showMessageDialog(null, "Error al obtener prestamos: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }

  public void addPrestamo(Prestamo prestamo) {
    try {
      dao.addPrestamo(prestamo);
      JOptionPane.showMessageDialog(null, "Prestamo agregado correctamente", "Éxito",
          JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLIntegrityConstraintViolationException e) {
      JOptionPane.showMessageDialog(null, "Ya no hay copias disponibles", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al agregar prestamo: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public Boolean devolucion(Prestamo selectedPrestamo) {
    try {
      dao.devolucion(selectedPrestamo);
      JOptionPane.showMessageDialog(null, "Prestamo eliminado correctamente", "Éxito",
          JOptionPane.INFORMATION_MESSAGE);
      return true;
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al eliminar prestamo: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }

  public void clearPrestamos(int user_id) {
    try {
      dao.clearPrestamos(user_id);
      JOptionPane.showMessageDialog(null, "Prestamos eliminados correctamente", "Éxito",
          JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al eliminar prestamos: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

}
