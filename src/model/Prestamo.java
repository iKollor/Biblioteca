package model;

import java.time.LocalDate;

public class Prestamo {
  private int id;
  private Libro libro;
  private Usuario usuario;
  private LocalDate fechaPrestamo;
  private LocalDate fechaDevolucion;
  private boolean devuelto;

  public Prestamo(int id, Libro libro, Usuario usuario, LocalDate fechaPrestamo, LocalDate fechaDevolucion,
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

  public LocalDate getFechaPrestamo() {
    return fechaPrestamo;
  }

  public void setFechaPrestamo(LocalDate fechaPrestamo) {
    this.fechaPrestamo = fechaPrestamo;
  }

  public LocalDate getFechaDevolucion() {
    return fechaDevolucion;
  }

  public void setFechaDevolucion(LocalDate fechaDevolucion) {
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
