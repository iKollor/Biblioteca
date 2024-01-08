package model;

import java.time.Year;

public class Libro extends ElementoBiblioteca {
  private int ISBN;
  private int paginas;
  private int edicion;
  private int saldo; // cantidad de copias disponibles

  public Libro() {
  }

  public Libro(String titulo, Autor autor, Year anioPublicacion, int iSBN, int paginas, int edicion, int saldo) {
    super(titulo, autor, anioPublicacion);
    ISBN = iSBN;
    this.paginas = paginas;
    this.edicion = edicion;
    this.saldo = saldo;
  }

  public int getISBN() {
    return ISBN;
  }

  public void setISBN(int iSBN) {
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