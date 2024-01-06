package model.db;

import java.sql.Connection;

public class Conexion {
  public Connection conn = null;

  public static Connection Conectar() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn = java.sql.DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proyectopoo", "root", "aula2010");
      return conn;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }
}
