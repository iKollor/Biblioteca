package view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Libro;
import model.interfaces.Estado;
import view.utils.fonts.SFProFont;

public class LibroCard extends JPanel {

  JLabel lblTituloLibro;
  JLabel lblEstado;

  public LibroCard(Libro libro) {

    Estado estado = libro.getEstado();
    String titulo = libro.getTitulo();

    setPreferredSize(new Dimension(200, 300)); // Sugerir un tamaño preferido
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createLineBorder(Color.gray, 1));
    setBackground(new Color(69, 64, 92));
    setCursor(new Cursor(Cursor.HAND_CURSOR));
    setToolTipText("Ver detalles del libro");

    // TODO: Mostrar la imagen del libro
    lblTituloLibro = new JLabel("<html><center><b>" + titulo + "</b></center></html>", SwingConstants.CENTER);
    lblTituloLibro.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 24));

    lblEstado = new JLabel(estado.toString(), SwingConstants.CENTER);
    lblEstado.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 14));

    if (estado == Estado.DISPONIBLE) {
      lblEstado.setForeground(new Color(25, 108, 38));
    } else if (estado == Estado.AGOTADO) {
      lblEstado.setForeground(new Color(128, 35, 29));
      setBackground(new Color(55, 55, 59));
    }

    add(lblTituloLibro, BorderLayout.CENTER);
    add(lblEstado, BorderLayout.SOUTH);
  }

}
