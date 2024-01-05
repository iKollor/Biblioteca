package view.HomePanels.components;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Libro;
import view.fonts.SFProFont;

public class LibroCard extends JPanel {

  JLabel lblTituloLibro;

  public LibroCard(Libro libro) {

    setPreferredSize(new Dimension(200, 400));

    // TODO: Mostrar la imagen del libro
    lblTituloLibro = new JLabel("<html><center><b>" + libro.getTitulo() + "</b></center></html>");
    lblTituloLibro.setFont(new Font(SFProFont.SF_PRO_BOLD, Font.BOLD, 24));
    add(lblTituloLibro);
  }

}
