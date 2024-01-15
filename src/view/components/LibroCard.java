package view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import controller.services.BibliotecaServicio.Estado;
import model.Libro;
import view.utils.fonts.SFProFont;

public class LibroCard extends JPanel {

  private JLabel lblTituloLibro;
  private JLabel lblEstado;

  private Estado estado;
  private Libro libro;

  public LibroCard(Libro libro, Estado estado) {
    this.libro = libro;
    this.estado = estado;

    setPreferredSize(new Dimension(200, 300)); // Sugerir un tamaño preferido
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createLineBorder(Color.gray, 1));
    setBackground(new Color(69, 64, 92));

    // TODO: Mostrar la imagen del libro
    lblTituloLibro = new JLabel("<html><center><b>" + libro.getTitulo() + "</b></center></html>",
        SwingConstants.CENTER);
    lblTituloLibro.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 24));

    lblEstado = new JLabel(estado.toString(), SwingConstants.CENTER);
    lblEstado.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 14));

    if (estado == Estado.DISPONIBLE) {
      lblEstado.setForeground(new Color(114, 241, 184));
    } else if (estado == Estado.AGOTADO) {
      lblEstado.setForeground(new Color(254, 68, 80));
      lblTituloLibro.setForeground(Color.gray);
      setBackground(new Color(55, 55, 59));
    }

    add(lblTituloLibro, BorderLayout.CENTER);
    add(lblEstado, BorderLayout.SOUTH);
  }

  public JLabel getLblTituloLibro() {
    return lblTituloLibro;
  }

  public JLabel getLblEstado() {
    return lblEstado;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public Libro getLibro() {
    return libro;
  }

  public void setLibro(Libro libro) {
    this.libro = libro;
  }

}
