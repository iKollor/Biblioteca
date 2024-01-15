package model;

import java.time.Year;

public class Libro extends ElementoBiblioteca {
  private String ISBN;
  private int paginas;
  private int edicion;
  private int saldo; // cantidad de copias disponibles

  public Libro() {}

  public Libro(String titulo, Autor autor, Year anioPublicacion, String ISBN, int paginas,
      int edicion, int saldo) {
    super(titulo, autor, anioPublicacion);
    this.ISBN = ISBN;
    this.paginas = paginas;
    this.edicion = edicion;
    this.saldo = saldo;
  }

  public String getISBN() {
    return ISBN;
  }

  public void setISBN(String iSBN) {
    ISBN = iSBN;
  }

  public int getPaginas() {
    return paginas;
  }

  public void setPaginas(int paginas) {
    this.paginas = paginas;
  }

  public int getEdicion() {
    return edicion;
  }

  public void setEdicion(int edicion) {
    this.edicion = edicion;
  }

  public int getSaldo() {
    return saldo;
  }

  public void setSaldo(int saldo) {
    this.saldo = saldo;
  }

}
