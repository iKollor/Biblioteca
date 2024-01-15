package view.utils;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class Loader extends JDialog {

  public Loader() {
    // Configuración básica del JDialog
    setTitle("Cargando...");
    setSize(300, 300);
    setLocationRelativeTo(null); // Centrar en la pantalla
    setResizable(false);
    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // No hacer nada al cerrar

    init();
  }

  private void init() {
    // Crear un panel para el mensaje y el icono de carga
    JPanel panel = new JPanel(new BorderLayout());
    JLabel label = new JLabel("Conectando a la base de datos...", JLabel.CENTER);
    panel.add(label, BorderLayout.NORTH);

    // Asumiendo que tienes un archivo "loading_spinner.gif" en tu proyecto
    ImageIcon loadingIcon;
    try {
      loadingIcon = new ImageIcon(getClass().getResource("loading_spinner.gif"));
      JLabel iconLabel = new JLabel(loadingIcon, JLabel.CENTER);
      panel.add(iconLabel, BorderLayout.CENTER);
    } catch (Exception e) {
      e.printStackTrace();
    }

    add(panel);
  }

  public void mostrarAsync() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        setVisible(true);
      }
    });
  }

  public void ocultarAsync() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        setVisible(false);
      }
    });
  }
}
