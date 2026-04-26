package view;

import model.Curso;
import system.Sesion;
import system.SistemaAcademy;
import util.LoggerBitacora;
import util.Validador;

import javax.swing.*;
import java.awt.*;

public class AdminCursosView extends JFrame {

    private JTextField txtCodigo, txtNombre, txtDescripcion, txtCreditos;

    public AdminCursosView() {

        setTitle("CRUD Cursos");
        setSize(450, 350);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Gestión de Cursos");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBounds(120, 10, 200, 30);
        add(titulo);

        addLabel("Código:", 60);
        addLabel("Nombre:", 100);
        addLabel("Descripción:", 140);
        addLabel("Créditos:", 180);

        txtCodigo = addField(150, 60);
        txtNombre = addField(150, 100);
        txtDescripcion = addField(150, 140);
        txtCreditos = addField(150, 180);

        JButton btnCrear = addButton("Crear", 40, 240);
        JButton btnBuscar = addButton("Buscar", 130, 240);
        JButton btnActualizar = addButton("Actualizar", 220, 240);
        JButton btnEliminar = addButton("Eliminar", 320, 240);

        btnCrear.addActionListener(e -> crear());
        btnBuscar.addActionListener(e -> buscar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());

        setVisible(true);
    }

    private void addLabel(String t, int y) {
        JLabel l = new JLabel(t);
        l.setBounds(40, y, 100, 25);
        add(l);
    }

    private JTextField addField(int x, int y) {
        JTextField t = new JTextField();
        t.setBounds(x, y, 200, 25);
        add(t);
        return t;
    }

    private JButton addButton(String t, int x, int y) {
        JButton b = new JButton(t);
        b.setBounds(x, y, 90, 30);
        add(b);
        return b;
    }

    // =========================
    // CRUD
    // =========================

    private void crear() {
        String codigo = txtCodigo.getText();
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        String creditosTxt = txtCreditos.getText();

        if (Validador.textoVacio(codigo) || Validador.textoVacio(nombre)) {
            JOptionPane.showMessageDialog(this, "Campos vacíos");
            return;
        }

        if (!Validador.esNumeroEntero(creditosTxt)) {
            JOptionPane.showMessageDialog(this, "Créditos inválidos");
            return;
        }

        if (SistemaAcademy.buscarCursoPorCodigo(codigo) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe");
            return;
        }

        Curso c = new Curso(codigo, nombre, descripcion, Integer.parseInt(creditosTxt));
        SistemaAcademy.agregarCurso(c);
        SistemaAcademy.guardarTodo();

        LoggerBitacora.registrar("ADMIN", Sesion.usuarioActual.getCodigo(),
                "CREAR_CURSO", "EXITOSA", "Curso: " + codigo);

        JOptionPane.showMessageDialog(this, "Curso creado");
    }

    private void buscar() {
        String codigo = txtCodigo.getText();

        Curso c = SistemaAcademy.buscarCursoPorCodigo(codigo);

        if (c == null) {
            JOptionPane.showMessageDialog(this, "No encontrado");
            return;
        }

        txtNombre.setText(c.getNombre());
        txtDescripcion.setText(c.getDescripcion());
        txtCreditos.setText(String.valueOf(c.getCreditos()));
    }

    private void actualizar() {
        String codigo = txtCodigo.getText();

        Curso c = SistemaAcademy.buscarCursoPorCodigo(codigo);

        if (c == null) {
            JOptionPane.showMessageDialog(this, "No existe");
            return;
        }

        c.setNombre(txtNombre.getText());
        c.setDescripcion(txtDescripcion.getText());
        c.setCreditos(Integer.parseInt(txtCreditos.getText()));

        SistemaAcademy.guardarTodo();

        LoggerBitacora.registrar("ADMIN", Sesion.usuarioActual.getCodigo(),
                "ACTUALIZAR_CURSO", "EXITOSA", "Curso: " + codigo);

        JOptionPane.showMessageDialog(this, "Actualizado");
    }

    private void eliminar() {
        String codigo = txtCodigo.getText();

        boolean ok = SistemaAcademy.eliminarCurso(codigo);

        if (ok) {
            SistemaAcademy.guardarTodo();

            LoggerBitacora.registrar("ADMIN", Sesion.usuarioActual.getCodigo(),
                    "ELIMINAR_CURSO", "EXITOSA", "Curso: " + codigo);

            JOptionPane.showMessageDialog(this, "Eliminado");
        } else {
            JOptionPane.showMessageDialog(this, "No encontrado");
        }
    }
}