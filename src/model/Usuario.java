package model;

import java.util.List;

public abstract class Usuario {
  public final int MAX_LIBROS_PRESTADOS = 4;

  private int id;
  private String nombre;
  private String apellido;
  private String email;
  private char[] password;
  private String dni;

  List<String> librosPrestados;
  // [1234551236, 1231224, 123123412, 125412341] ISBN Libros

  public Usuario() {
  }

  public Usuario(String nombre, String apellido, String email, char[] password, String dni,
      List<String> librosPrestados, int id) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.password = password;
    this.dni = dni;
    this.librosPrestados = librosPrestados;
    this.id = id;
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

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public char[] getPassword() {
    return password;
  }

  public void setPassword(char[] password) {
    this.password = password;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public List<String> getLibrosPrestados() {
    return librosPrestados;
  }

  public void setLibrosPrestados(List<String> librosPrestados) {
    this.librosPrestados = librosPrestados;
  }

  public boolean puedePrestar() {
    System.out.println("librosPrestados.size() " + librosPrestados.size());
    return librosPrestados.size() < MAX_LIBROS_PRESTADOS;
  }

}