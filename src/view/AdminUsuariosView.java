package view;

import util.LoggerBitacora;
import system.Sesion;
import javax.swing.JFileChooser;
import util.CSVImporter;
import model.Estudiante;
import model.Instructor;
import model.Usuario;
import system.SistemaAcademy;
import util.Validador;

import javax.swing.*;
import java.awt.*;

public class AdminUsuariosView extends JFrame {

    private JTextField txtCodigo, txtNombre, txtFecha, txtGenero, txtPassword;
    private JButton btnCrear, btnBuscar, btnActualizar, btnEliminar;
    private String tipo;

    public AdminUsuariosView(String tipo) {
        this.tipo = tipo;

        setTitle("CRUD " + tipo);
        setSize(450, 400);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblTitulo = new JLabel("Gestión de " + tipo);
        lblTitulo.setBounds(120, 10, 200, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitulo);

        // Labels
        addLabel("Código:", 50);
        addLabel("Nombre:", 90);
        addLabel("Fecha Nac:", 130);
        addLabel("Género:", 170);
        addLabel("Password:", 210);

        // TextFields
        txtCodigo = addField(150, 50);
        txtNombre = addField(150, 90);
        txtFecha = addField(150, 130);
        txtGenero = addField(150, 170);
        txtPassword = addField(150, 210);

        // Botones
        btnCrear = addButton("Crear", 40, 270);
        btnBuscar = addButton("Buscar", 130, 270);
        btnActualizar = addButton("Actualizar", 220, 270);
        btnEliminar = addButton("Eliminar", 320, 270);
        JButton btnCSV = new JButton("Cargar CSV");
        btnCSV.setBounds(150, 320, 140, 30);
        add(btnCSV);

        btnCSV.addActionListener(e -> cargarCSV());

        // Eventos
        btnCrear.addActionListener(e -> crear());
        btnBuscar.addActionListener(e -> buscar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());

        setVisible(true);
    }

    private void addLabel(String text, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(40, y, 100, 25);
        add(lbl);
    }

    private JTextField addField(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 200, 25);
        add(txt);
        return txt;
    }

    private JButton addButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 90, 30);
        add(btn);
        return btn;
    }
    
    private void cargarCSV() {
    JFileChooser chooser = new JFileChooser();
    int opcion = chooser.showOpenDialog(this);

    if (opcion == JFileChooser.APPROVE_OPTION) {
        String ruta = chooser.getSelectedFile().getAbsolutePath();
        String resultado;

        if (tipo.equals("INSTRUCTOR")) {
            resultado = CSVImporter.cargarInstructores(ruta);
        } else {
            resultado = CSVImporter.cargarEstudiantes(ruta);
        }

        JOptionPane.showMessageDialog(this, resultado);
    }
}

    // ============================
    // FUNCIONES CRUD
    // ============================

    private void crear() {
        
        String codigo = txtCodigo.getText();
        String nombre = txtNombre.getText();
        String fecha = txtFecha.getText();
        String genero = txtGenero.getText();
        String password = txtPassword.getText();

        if (Validador.textoVacio(codigo) || Validador.textoVacio(nombre) || Validador.textoVacio(password)) {
            JOptionPane.showMessageDialog(this, "Campos obligatorios vacíos");
            return;
        }

        if (SistemaAcademy.buscarUsuarioPorCodigo(codigo) != null) {
            JOptionPane.showMessageDialog(this, "El código ya existe");
            return;
        }

        Usuario nuevo;

        if (tipo.equals("INSTRUCTOR")) {
            nuevo = new Instructor(codigo, nombre, fecha, genero, password);
        } else {
            nuevo = new Estudiante(codigo, nombre, fecha, genero, password);
        }

        SistemaAcademy.agregarUsuario(nuevo);
        SistemaAcademy.guardarTodo();
        LoggerBitacora.registrar(
    "ADMIN",
    Sesion.usuarioActual.getCodigo(),
    "CREAR_USUARIO",
    "EXITOSA",
    "Usuario creado: " + codigo
);

        JOptionPane.showMessageDialog(this, "Usuario creado correctamente");
        limpiar();
    }

    private void buscar() {
        String codigo = txtCodigo.getText();

        Usuario u = SistemaAcademy.buscarUsuarioPorCodigo(codigo);

        if (u == null) {
            JOptionPane.showMessageDialog(this, "No encontrado");
            return;
        }

        txtNombre.setText(u.getNombre());
        txtFecha.setText(u.getFechaNacimiento());
        txtGenero.setText(u.getGenero());
        txtPassword.setText(u.getPassword());
    }

    private void actualizar() {
        String codigo = txtCodigo.getText();

        Usuario u = SistemaAcademy.buscarUsuarioPorCodigo(codigo);

        if (u == null) {
            JOptionPane.showMessageDialog(this, "No existe");
            return;
        }

        u.setNombre(txtNombre.getText());
        u.setPassword(txtPassword.getText());

        SistemaAcademy.guardarTodo();
        
        LoggerBitacora.registrar(
    "ADMIN",
    Sesion.usuarioActual.getCodigo(),
    "ACTUALIZAR_USUARIO",
    "EXITOSA",
    "Usuario actualizado: " + codigo
);

        JOptionPane.showMessageDialog(this, "Actualizado correctamente");
    }

    private void eliminar() {
        String codigo = txtCodigo.getText();

        boolean eliminado = SistemaAcademy.eliminarUsuario(codigo);

        if (eliminado) {
            SistemaAcademy.guardarTodo();
            JOptionPane.showMessageDialog(this, "Eliminado correctamente");
            limpiar();
            
            LoggerBitacora.registrar(
    "ADMIN",
    Sesion.usuarioActual.getCodigo(),
    "ELIMINAR_USUARIO",
    "EXITOSA",
    "Usuario eliminado: " + codigo
);
            
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el usuario");
        }
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtFecha.setText("");
        txtGenero.setText("");
        txtPassword.setText("");
    }
}