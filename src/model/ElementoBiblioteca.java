package model;

import java.time.Year;

public abstract class ElementoBiblioteca {
  // atributos protegidos para que puedan ser accedidos por las clases hijas
  protected String titulo;
  protected Autor autor;
  protected Year anioPublicacion;

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Autor getAutor() {
    return autor;
  }

  public void setAutor(Autor autor) {
    this.autor = autor;
  }

  public Year getAnioPublicacion() {
    return anioPublicacion;
  }

  public void setAnioPublicacion(Year anioPublicacion) {
    this.anioPublicacion = anioPublicacion;
  }

  public ElementoBiblioteca(String titulo, Autor autor, Year anioPublicacion) {
    this.titulo = titulo;
    this.autor = autor;
    this.anioPublicacion = anioPublicacion;
  }

  public ElementoBiblioteca() {}

}
