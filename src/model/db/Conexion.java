package model.db;

import java.sql.Connection;

import javax.swing.SwingUtilities;

import view.utils.Loader;

public class Conexion {

  public static Connection Conectar() {
    final Loader loader = new Loader();

    // Mostrar el loader en el Event Dispatch Thread
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        loader.setVisible(true);
      }
    });

    Connection conn = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      // TODO: Mostrar de forma asíncrona un loader mientras se conecta a la base de
      // datos

      conn = java.sql.DriverManager.getConnection("jdbc:mysql://www.ecuinfo.net:3306/biblioteca", "ugjavap",
          "uguayaquil");

      System.out.println("Conexión exitosa");
      return conn;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    } finally {
      // Cerrar el loader en el Event Dispatch Thread
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          loader.dispose();
        }
      });
    }
  }

}
