
// servicio/PersonaServicio.java
package servicio;

import dao.PersonaDAO;
import modelo.Persona;

import java.sql.SQLException;
import java.util.List;

public class PersonaServicio {
    private PersonaDAO personaDAO = new PersonaDAO();

    public void agregarPersona(Persona persona) throws SQLException {
        personaDAO.agregarPersona(persona);
    }

    public void actualizarPersona(Persona persona) throws SQLException {
        personaDAO.actualizarPersona(persona);
    }

    public void eliminarPersona(int id) throws SQLException {
        personaDAO.eliminarPersona(id);
    }

    public List<Persona> obtenerTodasLasPersonas() throws SQLException {
        return personaDAO.obtenerTodasLasPersonas();
    }
}
