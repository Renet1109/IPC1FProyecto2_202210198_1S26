package view;

import model.Nota;
import model.Seccion;
import system.Sesion;
import system.SistemaAcademy;

import javax.swing.*;

public class EstudianteNotasView extends JFrame {

    private JTextArea area;

    public EstudianteNotasView() {
        setTitle("Mis Calificaciones");
        setSize(650, 420);
        setLayout(null);
        setLocationRelativeTo(null);

        area = new JTextArea();
        area.setEditable(false);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(30, 30, 570, 320);
        add(scroll);

        cargarNotas();

        setVisible(true);
    }

    private void cargarNotas() {
        String codigoEstudiante = Sesion.usuarioActual.getCodigo();
        String texto = "MIS CALIFICACIONES\n\n";

        for (int i = 0; i < SistemaAcademy.totalSecciones; i++) {
            Seccion s = SistemaAcademy.secciones[i];

            if (s != null && s.estaInscrito(codigoEstudiante)) {
                texto += "Sección: " + s.getCodigoSeccion() + " | Curso: " + s.getCodigoCurso() + "\n";

                for (int j = 0; j < SistemaAcademy.totalNotas; j++) {
                    Nota n = SistemaAcademy.notas[j];

                    if (n != null
                            && n.getCodigoSeccion().equalsIgnoreCase(s.getCodigoSeccion())
                            && n.getCodigoEstudiante().equalsIgnoreCase(codigoEstudiante)) {
                        texto += "   " + n.getEtiqueta()
                                + " | Pond: " + n.getPonderacion()
                                + " | Nota: " + n.getNota()
                                + " | Fecha: " + n.getFechaRegistro()
                                + "\n";
                    }
                }

                double promedio = SistemaAcademy.calcularPromedioSeccionEstudiante(s.getCodigoSeccion(), codigoEstudiante);
                texto += "   Promedio: " + promedio + " | Estado: " + (promedio >= 61 ? "Aprobado" : "Reprobado") + "\n\n";
            }
        }

        area.setText(texto);
    }
}