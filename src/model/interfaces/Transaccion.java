package model.interfaces;

import model.Libro;

public interface Transaccion {
  boolean prestarLibro(Libro libro);

  boolean devolverLibro(Libro libro);
}
