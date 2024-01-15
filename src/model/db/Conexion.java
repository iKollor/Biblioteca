package model.db;

import java.sql.Connection;

public class Conexion {

  public static Connection Conectar() {

    Connection conn = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      // TODO: Mostrar de forma asíncrona un loader mientras se conecta a la base de datos
      conn = java.sql.DriverManager.getConnection(
          "jdbc:mysql://www.ecuinfo.net:3306/biblioteca".replace("\\", "/"), "ugjavap",
          "uguayaquil");

      System.out.println("Conexión exitosa");
      return conn;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

}
