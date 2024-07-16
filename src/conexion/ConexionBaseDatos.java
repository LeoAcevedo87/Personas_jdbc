
// dbconnection/DatabaseConnection.java
// conexion/ConexionBaseDatos.java
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static final String URL = "jdbc:mysql://localhost:3306/personas_jdbc?seSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENIA = "1234asdf";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
    }
}

