package view;

import model.Seccion;
import system.Sesion;
import system.SistemaAcademy;
import util.LoggerBitacora;

import javax.swing.*;

public class EstudianteCursosView extends JFrame {

    private JTextArea area;
    private JTextField txtSeccion;

    public EstudianteCursosView() {
        setTitle("Cursos Disponibles");
        setSize(600, 420);
        setLayout(null);
        setLocationRelativeTo(null);

        area = new JTextArea();
        area.setEditable(false);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(30, 30, 520, 230);
        add(scroll);

        JLabel lbl = new JLabel("Código Sección:");
        lbl.setBounds(30, 285, 120, 25);
        add(lbl);

        txtSeccion = new JTextField();
        txtSeccion.setBounds(150, 285, 160, 25);
        add(txtSeccion);

        JButton btnInscribir = new JButton("Inscribir");
        btnInscribir.setBounds(330, 285, 100, 30);
        add(btnInscribir);

        JButton btnDesasignar = new JButton("Desasignar");
        btnDesasignar.setBounds(440, 285, 110, 30);
        add(btnDesasignar);

        btnInscribir.addActionListener(e -> inscribir());
        btnDesasignar.addActionListener(e -> desasignar());

        cargarSecciones();
        setVisible(true);
    }

    private void cargarSecciones() {
        String texto = "SECCIONES DISPONIBLES\n\n";

        for (int i = 0; i < SistemaAcademy.totalSecciones; i++) {
            Seccion s = SistemaAcademy.secciones[i];

            if (s != null && s.isAbierta()) {
                texto += "Sección: " + s.getCodigoSeccion()
                        + " | Curso: " + s.getCodigoCurso()
                        + " | Instructor: " + s.getCodigoInstructor()
                        + " | Semestre: " + s.getSemestre()
                        + " | Horario: " + s.getHorario()
                        + " | Inscritos: " + s.getTotalInscritos()
                        + "\n";
            }
        }

        area.setText(texto);
    }

    private void inscribir() {
        String codSec = txtSeccion.getText();
        Seccion s = SistemaAcademy.buscarSeccionPorCodigo(codSec);

        if (s == null) {
            JOptionPane.showMessageDialog(this, "Sección no existe.");
            return;
        }

        if (!s.isAbierta()) {
            JOptionPane.showMessageDialog(this, "Sección cerrada.");
            return;
        }

        if (s.estaInscrito(Sesion.usuarioActual.getCodigo())) {
            JOptionPane.showMessageDialog(this, "Ya estás inscrito.");
            return;
        }

        SistemaAcademy.inscripcionesPendientes++;
        boolean ok = s.inscribirEstudiante(Sesion.usuarioActual.getCodigo());
        SistemaAcademy.inscripcionesPendientes--;

        if (ok) {
            SistemaAcademy.guardarTodo();
            LoggerBitacora.registrar("ESTUDIANTE", Sesion.usuarioActual.getCodigo(), "INSCRIBIR_SECCION", "EXITOSA", "Inscripción a " + codSec);
            JOptionPane.showMessageDialog(this, "Inscripción correcta.");
            cargarSecciones();
        }
    }

    private void desasignar() {
        String codSec = txtSeccion.getText();
        Seccion s = SistemaAcademy.buscarSeccionPorCodigo(codSec);

        if (s == null) {
            JOptionPane.showMessageDialog(this, "Sección no existe.");
            return;
        }

        boolean ok = s.desasignarEstudiante(Sesion.usuarioActual.getCodigo());

        if (ok) {
            SistemaAcademy.guardarTodo();
            JOptionPane.showMessageDialog(this, "Desasignado correctamente.");
            cargarSecciones();
        } else {
            JOptionPane.showMessageDialog(this, "No estabas inscrito.");
        }
    }
}