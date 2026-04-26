package util;

import model.Usuario;
import model.Instructor;
import model.Seccion;
import model.*;
import system.SistemaAcademy;

import java.io.BufferedReader;
import java.io.FileReader;

public class CSVImporter {

    public static String cargarInstructores(String ruta) {
        int cargados = 0;
        int errores = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length != 5) {
                    errores++;
                    continue;
                }

                String codigo = datos[0].trim();

                if (SistemaAcademy.buscarUsuarioPorCodigo(codigo) != null) {
                    errores++;
                    continue;
                }

                Instructor ins = new Instructor(
                        datos[0].trim(),
                        datos[1].trim(),
                        datos[2].trim(),
                        datos[3].trim(),
                        datos[4].trim()
                );

                SistemaAcademy.agregarUsuario(ins);
                cargados++;
            }

            br.close();
            SistemaAcademy.guardarTodo();

        } catch (Exception e) {
            return "Error al leer archivo: " + e.getMessage();
        }

        return "Instructores cargados: " + cargados + "\nErrores: " + errores;
    }

    public static String cargarEstudiantes(String ruta) {
        int cargados = 0;
        int errores = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length != 5) {
                    errores++;
                    continue;
                }

                String codigo = datos[0].trim();

                if (SistemaAcademy.buscarUsuarioPorCodigo(codigo) != null) {
                    errores++;
                    continue;
                }

                Estudiante est = new Estudiante(
                        datos[0].trim(),
                        datos[1].trim(),
                        datos[2].trim(),
                        datos[3].trim(),
                        datos[4].trim()
                );

                SistemaAcademy.agregarUsuario(est);
                cargados++;
            }

            br.close();
            SistemaAcademy.guardarTodo();

        } catch (Exception e) {
            return "Error al leer archivo: " + e.getMessage();
        }

        return "Estudiantes cargados: " + cargados + "\nErrores: " + errores;
    }

    public static String cargarCursos(String ruta) {
        int cargados = 0;
        int errores = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length != 4) {
                    errores++;
                    continue;
                }

                String codigo = datos[0].trim();

                if (SistemaAcademy.buscarCursoPorCodigo(codigo) != null) {
                    errores++;
                    continue;
                }

                int creditos;

                try {
                    creditos = Integer.parseInt(datos[3].trim());
                } catch (Exception ex) {
                    errores++;
                    continue;
                }

                Curso curso = new Curso(
                        datos[0].trim(),
                        datos[1].trim(),
                        datos[2].trim(),
                        creditos
                );

                SistemaAcademy.agregarCurso(curso);
                cargados++;
            }

            br.close();
            SistemaAcademy.guardarTodo();

        } catch (Exception e) {
            return "Error al leer archivo: " + e.getMessage();
        }

        return "Cursos cargados: " + cargados + "\nErrores: " + errores;
    }

    public static String cargarNotas(String ruta, String codigoInstructor) {
        int cargadas = 0;
        int errores = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length != 6) {
                    errores++;
                    continue;
                }

                String codCurso = datos[0].trim();
                String codSeccion = datos[1].trim();
                String codEstudiante = datos[2].trim();

                double ponderacion;
                double nota;

                try {
                    ponderacion = Double.parseDouble(datos[3].trim());
                    nota = Double.parseDouble(datos[4].trim());
                } catch (Exception ex) {
                    errores++;
                    continue;
                }

                String fecha = datos[5].trim();
                String etiqueta = "Actividad" + (SistemaAcademy.totalNotas + 1);

                if (!SistemaAcademy.instructorTieneSeccion(codigoInstructor, codSeccion)) {
                    errores++;
                    continue;
                }

                if (!SistemaAcademy.estudianteInscrito(codEstudiante, codSeccion)) {
                    errores++;
                    continue;
                }

                if (!Validador.notaValida(nota) || !Validador.ponderacionValida(ponderacion)) {
                    errores++;
                    continue;
                }

                Nota n = new Nota(
                        codCurso,
                        codSeccion,
                        codEstudiante,
                        etiqueta,
                        ponderacion,
                        nota,
                        fecha
                );

                SistemaAcademy.agregarNota(n);
                cargadas++;
            }

            br.close();
            SistemaAcademy.guardarTodo();

        } catch (Exception e) {
            return "Error al leer archivo: " + e.getMessage();
        }

        return "Notas cargadas: " + cargadas + "\nErrores: " + errores;
    }
    public static String cargarSecciones(String ruta) {
    int cargadas = 0;
    int errores = 0;

    try {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(",");

            if (datos.length != 5) {
                errores++;
                continue;
            }

            String codigoSeccion = datos[0].trim();
            String codigoCurso = datos[1].trim();
            String codigoInstructor = datos[2].trim();
            String semestre = datos[3].trim();
            String horario = datos[4].trim();

            if (SistemaAcademy.buscarSeccionPorCodigo(codigoSeccion) != null) {
                errores++;
                continue;
            }

            if (SistemaAcademy.buscarCursoPorCodigo(codigoCurso) == null) {
                errores++;
                continue;
            }

            Usuario instructor = SistemaAcademy.buscarUsuarioPorCodigo(codigoInstructor);

            if (!(instructor instanceof Instructor)) {
                errores++;
                continue;
            }

            Seccion seccion = new Seccion(
                    codigoSeccion,
                    codigoCurso,
                    codigoInstructor,
                    semestre,
                    horario,
                    true
            );

            SistemaAcademy.agregarSeccion(seccion);
            cargadas++;
        }

        br.close();
        SistemaAcademy.guardarTodo();

    } catch (Exception e) {
        return "Error al leer archivo: " + e.getMessage();
    }

    return "Secciones cargadas: " + cargadas + "\nErrores: " + errores;
}
}