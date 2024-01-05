package model.interfaces;

import model.Libro;

public interface ITransaccion {
  boolean prestarLibro(Libro libro);

  boolean devolverLibro(Libro libro);
}
