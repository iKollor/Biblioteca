package model.utils;

import model.Autor;

public class AutorItem {
  private String name;
  private Autor autor;

  public AutorItem(Autor autor) {
    this.name = autor.getNombre();
    this.autor = autor;
  }

  public AutorItem(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name; // Esto es lo que se mostrará en el JComboBox
  }

  public Autor getAutor() {
    return autor;
  }
}
