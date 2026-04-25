package util;

import model.Nota;
import model.Seccion;
import system.SistemaAcademy;

import java.io.FileWriter;

public class ReportesCSV {

    public static boolean reporteNotasSeccion(String codigoSeccion) {
        try {
            String nombre = FechaUtil.ahora().replace("/", "_").replace(":", "_").replace(" ", "_") + "_NotasSeccion.csv";
            FileWriter fw = new FileWriter(nombre);

            fw.write("Curso,Seccion,Estudiante,Etiqueta,Ponderacion,Nota,Fecha,Promedio,Estado\n");

            for (int i = 0; i < SistemaAcademy.totalNotas; i++) {
                Nota n = SistemaAcademy.notas[i];

                if (n != null && n.getCodigoSeccion().equalsIgnoreCase(codigoSeccion)) {
                    double promedio = SistemaAcademy.calcularPromedioSeccionEstudiante(codigoSeccion, n.getCodigoEstudiante());
                    String estado = promedio >= 61 ? "Aprobado" : "Reprobado";

                    fw.write(n.getCodigoCurso() + ","
                            + n.getCodigoSeccion() + ","
                            + n.getCodigoEstudiante() + ","
                            + n.getEtiqueta() + ","
                            + n.getPonderacion() + ","
                            + n.getNota() + ","
                            + n.getFechaRegistro() + ","
                            + promedio + ","
                            + estado + "\n");
                }
            }

            fw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean reporteInscripciones() {
        try {
            String nombre = FechaUtil.ahora().replace("/", "_").replace(":", "_").replace(" ", "_") + "_Inscripciones.csv";
            FileWriter fw = new FileWriter(nombre);

            fw.write("Curso,Seccion,Instructor,Semestre,Horario,TotalInscritos\n");

            for (int i = 0; i < SistemaAcademy.totalSecciones; i++) {
                Seccion s = SistemaAcademy.secciones[i];

                if (s != null) {
                    fw.write(s.getCodigoCurso() + ","
                            + s.getCodigoSeccion() + ","
                            + s.getCodigoInstructor() + ","
                            + s.getSemestre() + ","
                            + s.getHorario() + ","
                            + s.getTotalInscritos() + "\n");
                }
            }

            fw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}