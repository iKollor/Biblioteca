package view.HomePanels;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Usuario;

public class Sidebar extends JPanel {

  List<JButton> botones;

  public Sidebar(Usuario user) {
    setLayout(null);

    initComponents(user);
    añadirBotones(botones);
  }

  public void initComponents(Usuario user) {
    // Botónes de Usuario
    JButton btnCatálogo = new JButton("Catálogo");
    JButton btnPrestamos = new JButton("Préstamos");

    // Botónes de Administrador
    JButton btnUsuarios = new JButton("Usuarios");
    JButton btnAddLibro = new JButton("Agregar Libro");

    if (user != null) {
      botones = List.of(btnCatálogo, btnPrestamos);
    } else {
      botones = List.of(btnUsuarios, btnAddLibro);
    }
  }

  public void añadirBotones(List<JButton> botones) {
    for (JButton boton : botones) {
      add(boton);
    }
  }
}
