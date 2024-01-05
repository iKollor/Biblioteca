package model;

import java.time.Year;

import model.interfaces.Estado;

public class Libro {
  private int ISBN; // International Standard Book Number es un identificador único para libros
  private String titulo;
  private Estado estado;
  private Autor autor;
  private Year anio;
  private int paginas;
  private int edition;

  public Libro() {
  }

  public Libro(int iSBN, String titulo, Estado estado, Autor autor, Year anio, int paginas, int edition) {
    ISBN = iSBN;
    this.titulo = titulo;
    this.estado = estado;
    this.autor = autor;
    this.anio = anio;
    this.paginas = paginas;
    this.edition = edition;
  }

  public Year getAnio() {
    return anio;
  }

  public void setAnio(Year anio) {
    this.anio = anio;
  }

  public int getPaginas() {
    return paginas;
  }

  public void setPaginas(int paginas) {
    this.paginas = paginas;
  }

  public int getEdition() {
    return edition;
  }

  public void setEdition(int edition) {
    this.edition = edition;
  }

  public int getISBN() {
    return ISBN;
  }

  public void setISBN(int iSBN) {
    ISBN = iSBN;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public Autor getAutor() {
    return autor;
  }

  public void setAutor(Autor autor) {
    this.autor = autor;
  }
}
