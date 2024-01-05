package model;

import java.util.List;

public class Autor {
  private int id;
  private String nombre;
  private String nacionalidad;
  private List<Libro> librosEscritos;

  public Autor(int id, String nombre, String nacionalidad, List<Libro> librosEscritos) {
    this.id = id;
    this.nombre = nombre;
    this.nacionalidad = nacionalidad;
    this.librosEscritos = librosEscritos;
  }

  public Autor() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNacionalidad() {
    return nacionalidad;
  }

  public void setNacionalidad(String nacionalidad) {
    this.nacionalidad = nacionalidad;
  }

  public List<Libro> getLibrosEscritos() {
    return librosEscritos;
  }

  public void setLibrosEscritos(List<Libro> librosEscritos) {
    this.librosEscritos = librosEscritos;
  }
}
