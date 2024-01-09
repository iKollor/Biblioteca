package model;

public class Autor {
  private int id;
  private String nombre;
  private String nacionalidad;

  public Autor(int id, String nombre, String nacionalidad) {
    this.id = id;
    this.nombre = nombre;
    this.nacionalidad = nacionalidad;
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

  // El método toString será usado por el JComboBox para obtener el texto que se
  // muestra
  @Override
  public String toString() {
    return nombre;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Autor autor = (Autor) obj;

    return id == autor.id;
  }

}
