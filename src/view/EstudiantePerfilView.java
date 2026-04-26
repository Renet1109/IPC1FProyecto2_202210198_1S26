package view;

import system.Sesion;
import system.SistemaAcademy;

import javax.swing.*;

public class EstudiantePerfilView extends JFrame {

    private JTextField txtNombre, txtFecha, txtGenero;
    private JPasswordField txtActual, txtNueva;

    public EstudiantePerfilView() {
        setTitle("Mi Perfil");
        setSize(450, 360);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 40, 120, 25);
        add(lblNombre);

        txtNombre = new JTextField(Sesion.usuarioActual.getNombre());
        txtNombre.setBounds(170, 40, 200, 25);
        add(txtNombre);

        JLabel lblFecha = new JLabel("Fecha Nacimiento:");
        lblFecha.setBounds(40, 80, 120, 25);
        add(lblFecha);

        txtFecha = new JTextField(Sesion.usuarioActual.getFechaNacimiento());
        txtFecha.setBounds(170, 80, 200, 25);
        add(txtFecha);

        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setBounds(40, 120, 120, 25);
        add(lblGenero);

        txtGenero = new JTextField(Sesion.usuarioActual.getGenero());
        txtGenero.setBounds(170, 120, 200, 25);
        add(txtGenero);

        JLabel lblActual = new JLabel("Contraseña actual:");
        lblActual.setBounds(40, 170, 130, 25);
        add(lblActual);

        txtActual = new JPasswordField();
        txtActual.setBounds(170, 170, 200, 25);
        add(txtActual);

        JLabel lblNueva = new JLabel("Nueva contraseña:");
        lblNueva.setBounds(40, 210, 130, 25);
        add(lblNueva);

        txtNueva = new JPasswordField();
        txtNueva.setBounds(170, 210, 200, 25);
        add(txtNueva);

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setBounds(130, 260, 170, 35);
        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardar());

        setVisible(true);
    }

    private void guardar() {
        String actual = new String(txtActual.getPassword());
        String nueva = new String(txtNueva.getPassword());

        Sesion.usuarioActual.setNombre(txtNombre.getText());
        Sesion.usuarioActual.setFechaNacimiento(txtFecha.getText());
        Sesion.usuarioActual.setGenero(txtGenero.getText());

        if (!nueva.isEmpty()) {
            if (!Sesion.usuarioActual.getPassword().equals(actual)) {
                JOptionPane.showMessageDialog(this, "La contraseña actual es incorrecta.");
                return;
            }
            Sesion.usuarioActual.setPassword(nueva);
        }

        SistemaAcademy.guardarTodo();
        JOptionPane.showMessageDialog(this, "Perfil actualizado correctamente.");
    }
}