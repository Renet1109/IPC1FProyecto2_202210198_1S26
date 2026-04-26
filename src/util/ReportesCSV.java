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
    public static boolean reporteTop5Mejores() {
    try {
        String nombre = FechaUtil.ahora().replace("/", "_").replace(":", "_").replace(" ", "_") + "_Top5Mejores.csv";
        FileWriter fw = new FileWriter(nombre);

        fw.write("Posicion,CodigoEstudiante,Seccion,Promedio,Estado\n");

        String[] estudiantes = new String[500];
        String[] secciones = new String[500];
        double[] promedios = new double[500];
        int total = 0;

        for (int i = 0; i < SistemaAcademy.totalSecciones; i++) {
            if (SistemaAcademy.secciones[i] != null) {
                String[] inscritos = SistemaAcademy.secciones[i].getEstudiantesInscritos();

                for (int j = 0; j < SistemaAcademy.secciones[i].getTotalInscritos(); j++) {
                    estudiantes[total] = inscritos[j];
                    secciones[total] = SistemaAcademy.secciones[i].getCodigoSeccion();
                    promedios[total] = SistemaAcademy.calcularPromedioSeccionEstudiante(secciones[total], estudiantes[total]);
                    total++;
                }
            }
        }

        for (int i = 0; i < total - 1; i++) {
            for (int j = 0; j < total - i - 1; j++) {
                if (promedios[j] < promedios[j + 1]) {
                    double tempP = promedios[j];
                    promedios[j] = promedios[j + 1];
                    promedios[j + 1] = tempP;

                    String tempE = estudiantes[j];
                    estudiantes[j] = estudiantes[j + 1];
                    estudiantes[j + 1] = tempE;

                    String tempS = secciones[j];
                    secciones[j] = secciones[j + 1];
                    secciones[j + 1] = tempS;
                }
            }
        }

        int limite = total < 5 ? total : 5;

        for (int i = 0; i < limite; i++) {
            fw.write((i + 1) + ","
                    + estudiantes[i] + ","
                    + secciones[i] + ","
                    + promedios[i] + ","
                    + (promedios[i] >= 61 ? "Aprobado" : "Reprobado") + "\n");
        }

        fw.close();
        return true;
    } catch (Exception e) {
        return false;
    }
}

public static boolean reporteTop5Bajo() {
    try {
        String nombre = FechaUtil.ahora().replace("/", "_").replace(":", "_").replace(" ", "_") + "_Top5BajoDesempeno.csv";
        FileWriter fw = new FileWriter(nombre);

        fw.write("Posicion,CodigoEstudiante,Seccion,Promedio,Recomendacion\n");

        String[] estudiantes = new String[500];
        String[] secciones = new String[500];
        double[] promedios = new double[500];
        int total = 0;

        for (int i = 0; i < SistemaAcademy.totalSecciones; i++) {
            if (SistemaAcademy.secciones[i] != null) {
                String[] inscritos = SistemaAcademy.secciones[i].getEstudiantesInscritos();

                for (int j = 0; j < SistemaAcademy.secciones[i].getTotalInscritos(); j++) {
                    estudiantes[total] = inscritos[j];
                    secciones[total] = SistemaAcademy.secciones[i].getCodigoSeccion();
                    promedios[total] = SistemaAcademy.calcularPromedioSeccionEstudiante(secciones[total], estudiantes[total]);
                    total++;
                }
            }
        }

        for (int i = 0; i < total - 1; i++) {
            for (int j = 0; j < total - i - 1; j++) {
                if (promedios[j] > promedios[j + 1]) {
                    double tempP = promedios[j];
                    promedios[j] = promedios[j + 1];
                    promedios[j + 1] = tempP;

                    String tempE = estudiantes[j];
                    estudiantes[j] = estudiantes[j + 1];
                    estudiantes[j + 1] = tempE;

                    String tempS = secciones[j];
                    secciones[j] = secciones[j + 1];
                    secciones[j + 1] = tempS;
                }
            }
        }

        int limite = total < 5 ? total : 5;

        for (int i = 0; i < limite; i++) {
            fw.write((i + 1) + ","
                    + estudiantes[i] + ","
                    + secciones[i] + ","
                    + promedios[i] + ","
                    + "Tutoría y reforzamiento académico\n");
        }

        fw.close();
        return true;
    } catch (Exception e) {
        return false;
    }
}

public static boolean reporteRendimientoSecciones() {
    try {
        String nombre = FechaUtil.ahora().replace("/", "_").replace(":", "_").replace(" ", "_") + "_RendimientoSecciones.csv";
        FileWriter fw = new FileWriter(nombre);

        fw.write("Curso,Seccion,Instructor,PromedioGeneral,Aprobados,Reprobados\n");

        for (int i = 0; i < SistemaAcademy.totalSecciones; i++) {
            if (SistemaAcademy.secciones[i] != null) {
                String codSec = SistemaAcademy.secciones[i].getCodigoSeccion();
                String[] inscritos = SistemaAcademy.secciones[i].getEstudiantesInscritos();

                double suma = 0;
                int aprobados = 0;
                int reprobados = 0;
                int total = SistemaAcademy.secciones[i].getTotalInscritos();

                for (int j = 0; j < total; j++) {
                    double promedio = SistemaAcademy.calcularPromedioSeccionEstudiante(codSec, inscritos[j]);
                    suma += promedio;

                    if (promedio >= 61) {
                        aprobados++;
                    } else {
                        reprobados++;
                    }
                }

                double promedioGeneral = total == 0 ? 0 : suma / total;

                fw.write(SistemaAcademy.secciones[i].getCodigoCurso() + ","
                        + codSec + ","
                        + SistemaAcademy.secciones[i].getCodigoInstructor() + ","
                        + promedioGeneral + ","
                        + aprobados + ","
                        + reprobados + "\n");
            }
        }

        fw.close();
        return true;
    } catch (Exception e) {
        return false;
    }
}
public static boolean exportarBitacora() {
    try {
        String nombre = FechaUtil.ahora().replace("/", "_").replace(":", "_").replace(" ", "_") + "_Bitacora.csv";
        FileWriter fw = new FileWriter(nombre);

        fw.write("FechaHora,TipoUsuario,CodigoUsuario,Operacion,Estado,Descripcion\n");

        for (int i = 0; i < SistemaAcademy.totalEventos; i++) {
            if (SistemaAcademy.bitacora[i] != null) {
                fw.write(SistemaAcademy.bitacora[i].getFechaHora() + ","
                        + SistemaAcademy.bitacora[i].getTipoUsuario() + ","
                        + SistemaAcademy.bitacora[i].getCodigoUsuario() + ","
                        + SistemaAcademy.bitacora[i].getOperacion() + ","
                        + SistemaAcademy.bitacora[i].getEstado() + ","
                        + SistemaAcademy.bitacora[i].getDescripcion().replace(",", " ") + "\n");
            }
        }

        fw.close();
        return true;

    } catch (Exception e) {
        return false;
    }
}
}