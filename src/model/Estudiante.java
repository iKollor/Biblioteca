package model;

import java.util.List;

public class Estudiante extends Usuario {

  private String carrera;

  public Estudiante() {
  }

  public Estudiante(String nombre, String apellido, String email, char[] password, String dni,
      List<Libro> librosPrestados, String carrera) {
    super(nombre, apellido, email, password, dni, librosPrestados);
    this.carrera = carrera;
  }

  public String getCarrera() {
    return carrera;
  }

  public void setCarrera(String carrera) {
    this.carrera = carrera;
  }

}
