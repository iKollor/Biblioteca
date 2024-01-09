package model;

import java.sql.Timestamp;

public class Prestamo {
  private int id;
  private Libro libro;
  private Usuario usuario;
  private Timestamp fechaPrestamo;
  private Timestamp fechaDevolucion;
  private boolean devuelto;

  public Prestamo(int id, Libro libro, Usuario usuario, Timestamp fechaPrestamo, Timestamp fechaDevolucion,
      boolean devuelto) {
    this.id = id;
    this.libro = libro;
    this.usuario = usuario;
    this.fechaPrestamo = fechaPrestamo;
    this.fechaDevolucion = fechaDevolucion;
    this.devuelto = devuelto;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Libro getLibro() {
    return libro;
  }

  public void setLibro(Libro libro) {
    this.libro = libro;
  }

  public Timestamp getFechaPrestamo() {
    return fechaPrestamo;
  }

  public void setFechaPrestamo(Timestamp fechaPrestamo) {
    this.fechaPrestamo = fechaPrestamo;
  }

  public Timestamp getFechaDevolucion() {
    return fechaDevolucion;
  }

  public void setFechaDevolucion(Timestamp fechaDevolucion) {
    this.fechaDevolucion = fechaDevolucion;
  }

  public boolean isDevuelto() {
    return devuelto;
  }

  public void setDevuelto(boolean devuelto) {
    this.devuelto = devuelto;
  }

  public Prestamo() {
  }

}
