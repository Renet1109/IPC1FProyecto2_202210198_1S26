package view;

import javax.swing.JFileChooser;
import util.CSVImporter;
import model.Nota;
import system.Sesion;
import system.SistemaAcademy;
import util.FechaUtil;
import util.LoggerBitacora;
import util.Validador;

import javax.swing.*;

public class InstructorNotasView extends JFrame {

    private JTextField txtCurso, txtSeccion, txtEstudiante, txtEtiqueta, txtPonderacion, txtNota;
    private JTextArea area;

    public InstructorNotasView() {
        setTitle("Gestión de Notas");
        setSize(650, 520);
        setLayout(null);
        setLocationRelativeTo(null);

        addLabel("Código Curso:", 30);
        addLabel("Código Sección:", 70);
        addLabel("Código Estudiante:", 110);
        addLabel("Etiqueta:", 150);
        addLabel("Ponderación:", 190);
        addLabel("Nota:", 230);

        txtCurso = addField(170, 30);
        txtSeccion = addField(170, 70);
        txtEstudiante = addField(170, 110);
        txtEtiqueta = addField(170, 150);
        txtPonderacion = addField(170, 190);
        txtNota = addField(170, 230);

        JButton btnCrear = new JButton("Crear");
        btnCrear.setBounds(390, 30, 150, 30);
        add(btnCrear);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(390, 70, 150, 30);
        add(btnBuscar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(390, 110, 150, 30);
        add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(390, 150, 150, 30);
        add(btnEliminar);

        JButton btnVer = new JButton("Ver Notas");
        btnVer.setBounds(390, 190, 150, 30);
        add(btnVer);
        JButton btnCSV = new JButton("Cargar CSV");
        btnCSV.setBounds(390, 265, 150, 30); 
        add(btnCSV);

      btnCSV.addActionListener(e -> cargarCSV());
        
        JButton btnReporte = new JButton("Reporte CSV");
btnReporte.setBounds(390, 230, 150, 30);
add(btnReporte);

btnReporte.addActionListener(e -> {
    boolean ok = util.ReportesCSV.reporteNotasSeccion(txtSeccion.getText());
    JOptionPane.showMessageDialog(this, ok ? "Reporte CSV generado." : "Error al generar reporte.");
});


        area = new JTextArea();
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(30, 290, 570, 160);
        add(scroll);

        btnCrear.addActionListener(e -> crear());
        btnBuscar.addActionListener(e -> buscar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnVer.addActionListener(e -> mostrarNotas());

        setVisible(true);
    }

    private void addLabel(String texto, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setBounds(30, y, 140, 25);
        add(lbl);
    }

    private JTextField addField(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 180, 25);
        add(txt);
        return txt;
    }
    
    private void cargarCSV() {
    JFileChooser chooser = new JFileChooser();
    int opcion = chooser.showOpenDialog(this);

    if (opcion == JFileChooser.APPROVE_OPTION) {
        String ruta = chooser.getSelectedFile().getAbsolutePath();
        String resultado = CSVImporter.cargarNotas(ruta, Sesion.usuarioActual.getCodigo());
        JOptionPane.showMessageDialog(this, resultado);
        mostrarNotas();
    }
}

    private boolean validarBase() {
        
        if (!SistemaAcademy.instructorTieneSeccion(Sesion.usuarioActual.getCodigo(), txtSeccion.getText())) {
            JOptionPane.showMessageDialog(this, "No tienes asignada esta sección.");
            return false;
        }

        if (!SistemaAcademy.estudianteInscrito(txtEstudiante.getText(), txtSeccion.getText())) {
            JOptionPane.showMessageDialog(this, "El estudiante no está inscrito en esta sección.");
            return false;
        }

        if (!Validador.esDouble(txtPonderacion.getText()) || !Validador.esDouble(txtNota.getText())) {
            JOptionPane.showMessageDialog(this, "Nota o ponderación inválida.");
            return false;
        }

        double nota = Double.parseDouble(txtNota.getText());
        double ponderacion = Double.parseDouble(txtPonderacion.getText());

        if (!Validador.notaValida(nota) || !Validador.ponderacionValida(ponderacion)) {
            JOptionPane.showMessageDialog(this, "Nota debe ser 0-100 y ponderación mayor a 0.");
            return false;
        }

        return true;
    }

    private void crear() {
        if (!validarBase()) return;
        double nuevaPonderacion = Double.parseDouble(txtPonderacion.getText());
double sumaActual = SistemaAcademy.sumaPonderaciones(txtSeccion.getText(), txtEstudiante.getText());

if (sumaActual + nuevaPonderacion > 100) {
    JOptionPane.showMessageDialog(this, "No puedes superar el 100% de ponderación. Actual: " + sumaActual + "%");
    return;
}

        if (SistemaAcademy.buscarNota(txtCurso.getText(), txtSeccion.getText(), txtEstudiante.getText(), txtEtiqueta.getText()) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe una nota con esa etiqueta.");
            return;
        }

        Nota n = new Nota(
                txtCurso.getText(),
                txtSeccion.getText(),
                txtEstudiante.getText(),
                txtEtiqueta.getText(),
                Double.parseDouble(txtPonderacion.getText()),
                Double.parseDouble(txtNota.getText()),
                FechaUtil.hoyISO()
        );

        SistemaAcademy.agregarNota(n);
        SistemaAcademy.guardarTodo();

        LoggerBitacora.registrar("INSTRUCTOR", Sesion.usuarioActual.getCodigo(), "CREAR_NOTA", "EXITOSA", "Nota creada.");
        JOptionPane.showMessageDialog(this, "Nota creada.");
        mostrarNotas();
    }

    private void buscar() {
        Nota n = SistemaAcademy.buscarNota(txtCurso.getText(), txtSeccion.getText(), txtEstudiante.getText(), txtEtiqueta.getText());

        if (n == null) {
            JOptionPane.showMessageDialog(this, "Nota no encontrada.");
            return;
        }

        txtPonderacion.setText(String.valueOf(n.getPonderacion()));
        txtNota.setText(String.valueOf(n.getNota()));
    }

    private void actualizar() {
        if (!validarBase()) return;
        
        double nuevaPonderacion = Double.parseDouble(txtPonderacion.getText());
double sumaSinEstaNota = SistemaAcademy.sumaPonderacionesSinEtiqueta(
        txtSeccion.getText(),
        txtEstudiante.getText(),
        txtEtiqueta.getText()
);

if (sumaSinEstaNota + nuevaPonderacion > 100) {
    JOptionPane.showMessageDialog(this, "No puedes superar el 100% de ponderación. Actual sin esta nota: " + sumaSinEstaNota + "%");
    return;
}

        Nota n = SistemaAcademy.buscarNota(txtCurso.getText(), txtSeccion.getText(), txtEstudiante.getText(), txtEtiqueta.getText());

        if (n == null) {
            JOptionPane.showMessageDialog(this, "Nota no existe.");
            return;
        }

        n.setPonderacion(Double.parseDouble(txtPonderacion.getText()));
        n.setNota(Double.parseDouble(txtNota.getText()));

        SistemaAcademy.guardarTodo();
        
        LoggerBitacora.registrar(
    "INSTRUCTOR",
    Sesion.usuarioActual.getCodigo(),
    "ACTUALIZAR_NOTA",
    "EXITOSA",
    "Nota actualizada"
);
        
        
        JOptionPane.showMessageDialog(this, "Nota actualizada.");
        mostrarNotas();
    }

    private void eliminar() {
        boolean ok = SistemaAcademy.eliminarNota(txtCurso.getText(), txtSeccion.getText(), txtEstudiante.getText(), txtEtiqueta.getText());

        if (ok) {
            SistemaAcademy.guardarTodo();
            
            LoggerBitacora.registrar(
    "INSTRUCTOR",
    Sesion.usuarioActual.getCodigo(),
    "ELIMINAR_NOTA",
    "EXITOSA",
    "Nota eliminada"
);
            
            JOptionPane.showMessageDialog(this, "Nota eliminada.");
            mostrarNotas();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la nota.");
        }
    }

    private void mostrarNotas() {
        String texto = "NOTAS REGISTRADAS\n\n";

        for (int i = 0; i < SistemaAcademy.totalNotas; i++) {
            Nota n = SistemaAcademy.notas[i];

            if (n != null && n.getCodigoSeccion().equalsIgnoreCase(txtSeccion.getText())) {
                double promedio = SistemaAcademy.calcularPromedioSeccionEstudiante(n.getCodigoSeccion(), n.getCodigoEstudiante());
                String estado = promedio >= 61 ? "Aprobado" : "Reprobado";

                texto += "Curso: " + n.getCodigoCurso()
                        + " | Sección: " + n.getCodigoSeccion()
                        + " | Estudiante: " + n.getCodigoEstudiante()
                        + " | " + n.getEtiqueta()
                        + " | Pond: " + n.getPonderacion()
                        + " | Nota: " + n.getNota()
                        + " | Promedio: " + promedio
                        + " | Estado: " + estado
                        + "\n";
            }
        }

        area.setText(texto);
    }
}