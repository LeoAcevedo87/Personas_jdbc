// gui/PersonaGUI.java
package gui;

import modelo.Persona;
import servicio.PersonaServicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class PersonaGUI extends JFrame {
    private PersonaServicio personaServicio = new PersonaServicio();

    private JTextField campoNombre = new JTextField(20);
    private JTextField campoEdad = new JTextField(20);
    private JTextArea areaDisplay = new JTextArea(20, 40);

    public PersonaGUI() {
        setTitle("Gestión de Personas");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new GridLayout(3, 2));
        panelEntrada.add(new JLabel("Nombre:"));
        panelEntrada.add(campoNombre);
        panelEntrada.add(new JLabel("Edad:"));
        panelEntrada.add(campoEdad);

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPersona();
            }
        });
        panelEntrada.add(botonAgregar);

        JButton botonVer = new JButton("Ver Todos");
        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verTodasLasPersonas();
            }
        });
        panelEntrada.add(botonVer);

        add(panelEntrada, BorderLayout.NORTH);
        add(new JScrollPane(areaDisplay), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarPersona() {
        String nombre = campoNombre.getText();
        int edad = Integer.parseInt(campoEdad.getText());
        Persona persona = new Persona(nombre, edad);
        try {
            personaServicio.agregarPersona(persona);
            areaDisplay.setText("¡Persona agregada exitosamente! ID: " + persona.getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
            areaDisplay.setText("Error al agregar persona.");
        }
    }

    private void verTodasLasPersonas() {
        try {
            List<Persona> personas = personaServicio.obtenerTodasLasPersonas();
            areaDisplay.setText("");
            for (Persona persona : personas) {
                areaDisplay.append(persona.toString() + "\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            areaDisplay.setText("Error al recuperar personas.");
        }
    }

    public static void main(String[] args) {
        new PersonaGUI();
    }
}
