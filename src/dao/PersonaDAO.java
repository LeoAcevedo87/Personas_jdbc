// dao/PersonaDAO.java
package dao;

import conexion.ConexionBaseDatos;
import modelo.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    public void agregarPersona(Persona persona) throws SQLException {
        String consulta = "INSERT INTO personas (nombre, apellido, edad, dni) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBaseDatos.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getApellido());
            pstmt.setInt(3, persona.getEdad());
            pstmt.setInt(4, persona.getDni());
            pstmt.executeUpdate();

            // Obtener el ID generado automáticamente
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    persona.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void editarPersona(Persona persona) throws SQLException {
        if (persona == null) {
            throw new SQLException("Persona no proporcionada.");
        }

        String consulta = "UPDATE personas SET dni = ?, apellido = ?, nombre = ?, edad = ? WHERE id = ?";
        try (Connection conn = ConexionBaseDatos.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setInt(1, persona.getDni());
            pstmt.setString(2, persona.getApellido());
            pstmt.setString(3, persona.getNombre());
            pstmt.setInt(4, persona.getEdad());
            pstmt.setInt(5, persona.getId());

            int filasActualizadas = pstmt.executeUpdate();
            System.out.println("Filas actualizadas: " + filasActualizadas);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void eliminarPersona(int id) throws SQLException {
        String consulta = "DELETE FROM personas WHERE id = ?";
        try (Connection conn = ConexionBaseDatos.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Persona> obtenerTodasLasPersonas() throws SQLException {
        List<Persona> personas = new ArrayList<>();
        String consulta = "SELECT * FROM personas";
        try (Connection conn = ConexionBaseDatos.obtenerConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            while (rs.next()) {
                Persona persona = new Persona();
                persona.setId(rs.getInt("id"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setEdad(rs.getInt("edad"));
                persona.setDni(rs.getInt("dni"));
                personas.add(persona);
            }
        }

        return personas;
    }

    public Persona obtenerPersona(int id) {
        String consulta = "SELECT * FROM personas WHERE id = ?";
        System.out.println("Consulta SQL: " + consulta);
        Persona persona = null;

        try (Connection conn = ConexionBaseDatos.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(consulta)) {

            // Establecer el parámetro de la consulta
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Inicializar el objeto Persona con los datos obtenidos
                    persona = new Persona();
                    persona.setId(rs.getInt("id"));
                    persona.setDni(rs.getInt("dni"));
                    persona.setApellido(rs.getString("apellido"));
                    persona.setNombre(rs.getString("nombre"));
                    persona.setEdad(rs.getInt("edad"));

                    // Imprimir la persona para depuración
                    System.out.println(persona.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }

        return persona;
    }

}
