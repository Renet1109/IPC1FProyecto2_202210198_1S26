package view;


import system.Sesion;
import util.LoggerBitacora;
import javax.swing.JFileChooser;
import util.CSVImporter;
import model.Curso;
import model.Instructor;
import model.Seccion;
import model.Usuario;
import system.SistemaAcademy;
import util.LoggerBitacora;
import util.Validador;

import javax.swing.*;
import java.awt.*;

public class AdminSeccionesView extends JFrame {
private void cargarCSV() {
    JFileChooser chooser = new JFileChooser();
    int opcion = chooser.showOpenDialog(this);

    if (opcion == JFileChooser.APPROVE_OPTION) {
        String ruta = chooser.getSelectedFile().getAbsolutePath();
        String resultado = CSVImporter.cargarSecciones(ruta);
        JOptionPane.showMessageDialog(this, resultado);
    }
}
    private JTextField txtCodigoSeccion, txtCodigoCurso, txtCodigoInstructor, txtSemestre, txtHorario;

    public AdminSeccionesView() {
        setTitle("Gestión de Secciones");
        setSize(500, 390);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Gestión de Secciones");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBounds(140, 15, 250, 30);
        add(titulo);

        addLabel("Código Sección:", 60);
        addLabel("Código Curso:", 100);
        addLabel("Código Instructor:", 140);
        addLabel("Semestre:", 180);
        addLabel("Horario:", 220);

        txtCodigoSeccion = addField(170, 60);
        txtCodigoCurso = addField(170, 100);
        txtCodigoInstructor = addField(170, 140);
        txtSemestre = addField(170, 180);
        txtHorario = addField(170, 220);

        JButton btnCrear = addButton("Crear", 30, 280);
        JButton btnBuscar = addButton("Buscar", 130, 280);
        JButton btnActualizar = addButton("Actualizar", 230, 280);
        JButton btnEliminar = addButton("Eliminar", 350, 280);
        JButton btnCSV = new JButton("Cargar CSV");
btnCSV.setBounds(170, 320, 140, 30);
add(btnCSV);



btnCSV.addActionListener(e -> cargarCSV());

        btnCrear.addActionListener(e -> crear());
        btnBuscar.addActionListener(e -> buscar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());

        setVisible(true);
    }

    private void addLabel(String texto, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(35, y, 130, 25);
        add(lbl);
    }

    private JTextField addField(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 230, 25);
        add(txt);
        return txt;
    }

    private JButton addButton(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 105, 30);
        add(btn);
        return btn;
    }

    private void crear() {
        String codSec = txtCodigoSeccion.getText();
        String codCurso = txtCodigoCurso.getText();
        String codInstructor = txtCodigoInstructor.getText();
        String semestre = txtSemestre.getText();
        String horario = txtHorario.getText();

        if (Validador.textoVacio(codSec) || Validador.textoVacio(codCurso)
                || Validador.textoVacio(codInstructor) || Validador.textoVacio(semestre)
                || Validador.textoVacio(horario)) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        Curso curso = SistemaAcademy.buscarCursoPorCodigo(codCurso);
        Usuario instructor = SistemaAcademy.buscarUsuarioPorCodigo(codInstructor);

        if (curso == null) {
            JOptionPane.showMessageDialog(this, "El curso no existe.");
            return;
        }

        if (!(instructor instanceof Instructor)) {
            JOptionPane.showMessageDialog(this, "El instructor no existe.");
            return;
        }

        if (SistemaAcademy.buscarSeccionPorCodigo(codSec) != null) {
            JOptionPane.showMessageDialog(this, "La sección ya existe.");
            return;
        }

        Seccion s = new Seccion(codSec, codCurso, codInstructor, semestre, horario, true);
        SistemaAcademy.agregarSeccion(s);
        SistemaAcademy.guardarTodo();
        
        
        LoggerBitacora.registrar(
        "ADMIN",
        Sesion.usuarioActual.getCodigo(),
        "CREAR_SECCION",
        "EXITOSA",
        "Sección creada: " + codSec
        );
        
        JOptionPane.showMessageDialog(this, "Sección creada correctamente.");
        limpiar();
        LoggerBitacora.registrar("TEST","1","TEST","OK","Probando");
    }

    private void buscar() {
        Seccion s = SistemaAcademy.buscarSeccionPorCodigo(txtCodigoSeccion.getText());

        if (s == null) {
            JOptionPane.showMessageDialog(this, "Sección no encontrada.");
            return;
        }

        txtCodigoCurso.setText(s.getCodigoCurso());
        txtCodigoInstructor.setText(s.getCodigoInstructor());
        txtSemestre.setText(s.getSemestre());
        txtHorario.setText(s.getHorario());
    }

    private void actualizar() {
        Seccion s = SistemaAcademy.buscarSeccionPorCodigo(txtCodigoSeccion.getText());

        if (s == null) {
            JOptionPane.showMessageDialog(this, "Sección no existe.");
            return;
        }

        s.setCodigoInstructor(txtCodigoInstructor.getText());
        s.setSemestre(txtSemestre.getText());
        s.setHorario(txtHorario.getText());

        SistemaAcademy.guardarTodo();
        LoggerBitacora.registrar(
    "ADMIN",
    Sesion.usuarioActual.getCodigo(),
    "ACTUALIZAR_SECCION",
    "EXITOSA",
    "Sección actualizada: " + txtCodigoSeccion.getText()
);
        JOptionPane.showMessageDialog(this, "Sección actualizada.");
    }

    private void eliminar() {
        boolean ok = SistemaAcademy.eliminarSeccion(txtCodigoSeccion.getText());

        if (ok) {
            SistemaAcademy.guardarTodo();
            LoggerBitacora.registrar(
    "ADMIN",
    Sesion.usuarioActual.getCodigo(),
    "ELIMINAR_SECCION",
    "EXITOSA",
    "Sección eliminada: " + txtCodigoSeccion.getText()
);
            JOptionPane.showMessageDialog(this, "Sección eliminada.");
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la sección.");
        }
    }

    private void limpiar() {
        txtCodigoSeccion.setText("");
        txtCodigoCurso.setText("");
        txtCodigoInstructor.setText("");
        txtSemestre.setText("");
        txtHorario.setText("");
    }
}