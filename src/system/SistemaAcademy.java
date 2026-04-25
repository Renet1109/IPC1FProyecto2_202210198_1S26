package system;

import model.Administrador;
import model.BitacoraEvento;
import model.Curso;
import model.Estudiante;
import model.Instructor;
import model.Nota;
import model.Seccion;
import model.Usuario;
import util.Serializador;

public class SistemaAcademy {
    public static Usuario[] usuarios = new Usuario[500];
    public static int totalUsuarios = 0;

    public static Curso[] cursos = new Curso[300];
    public static int totalCursos = 0;

    public static Seccion[] secciones = new Seccion[500];
    public static int totalSecciones = 0;

    public static Nota[] notas = new Nota[5000];
    public static int totalNotas = 0;

    public static BitacoraEvento[] bitacora = new BitacoraEvento[5000];
    public static int totalEventos = 0;

    public static int usuariosActivos = 0;
    public static int inscripcionesPendientes = 0;

    public static void inicializarSistema() {
        cargarTodo();

        if (buscarUsuarioPorCodigo(Config.ADMIN_CODIGO) == null) {
            Administrador admin = new Administrador(
                    Config.ADMIN_CODIGO,
                    "Administrador General",
                    "2000-01-01",
                    "N/A",
                    Config.ADMIN_PASSWORD
            );
            agregarUsuario(admin);
            guardarTodo();
        }
    }

    public static void guardarTodo() {
        Serializador.guardar("usuarios.ser", usuarios);
        Serializador.guardarEntero("usuarios_count.ser", totalUsuarios);

        Serializador.guardar("cursos.ser", cursos);
        Serializador.guardarEntero("cursos_count.ser", totalCursos);

        Serializador.guardar("secciones.ser", secciones);
        Serializador.guardarEntero("secciones_count.ser", totalSecciones);

        Serializador.guardar("notas.ser", notas);
        Serializador.guardarEntero("notas_count.ser", totalNotas);

        Serializador.guardar("bitacora.ser", bitacora);
        Serializador.guardarEntero("bitacora_count.ser", totalEventos);
    }

    public static void cargarTodo() {
        Object objUsuarios = Serializador.cargar("usuarios.ser");
        Object objCursos = Serializador.cargar("cursos.ser");
        Object objSecciones = Serializador.cargar("secciones.ser");
        Object objNotas = Serializador.cargar("notas.ser");
        Object objBitacora = Serializador.cargar("bitacora.ser");

        Integer countUsuarios = Serializador.cargarEntero("usuarios_count.ser");
        Integer countCursos = Serializador.cargarEntero("cursos_count.ser");
        Integer countSecciones = Serializador.cargarEntero("secciones_count.ser");
        Integer countNotas = Serializador.cargarEntero("notas_count.ser");
        Integer countBitacora = Serializador.cargarEntero("bitacora_count.ser");

        if (objUsuarios != null) usuarios = (Usuario[]) objUsuarios;
        if (objCursos != null) cursos = (Curso[]) objCursos;
        if (objSecciones != null) secciones = (Seccion[]) objSecciones;
        if (objNotas != null) notas = (Nota[]) objNotas;
        if (objBitacora != null) bitacora = (BitacoraEvento[]) objBitacora;

        if (countUsuarios != null) totalUsuarios = countUsuarios;
        if (countCursos != null) totalCursos = countCursos;
        if (countSecciones != null) totalSecciones = countSecciones;
        if (countNotas != null) totalNotas = countNotas;
        if (countBitacora != null) totalEventos = countBitacora;
    }

    public static boolean agregarUsuario(Usuario usuario) {
        if (totalUsuarios >= usuarios.length) return false;
        if (buscarUsuarioPorCodigo(usuario.getCodigo()) != null) return false;

        usuarios[totalUsuarios] = usuario;
        totalUsuarios++;
        return true;
    }

    public static Usuario buscarUsuarioPorCodigo(String codigo) {
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i] != null && usuarios[i].getCodigo().equalsIgnoreCase(codigo)) {
                return usuarios[i];
            }
        }
        return null;
    }

    public static boolean eliminarUsuario(String codigo) {
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i] != null && usuarios[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < totalUsuarios - 1; j++) {
                    usuarios[j] = usuarios[j + 1];
                }
                usuarios[totalUsuarios - 1] = null;
                totalUsuarios--;
                return true;
            }
        }
        return false;
    }

    public static boolean agregarCurso(Curso curso) {
        if (totalCursos >= cursos.length) return false;
        if (buscarCursoPorCodigo(curso.getCodigo()) != null) return false;

        cursos[totalCursos] = curso;
        totalCursos++;
        return true;
    }

    public static Curso buscarCursoPorCodigo(String codigo) {
        for (int i = 0; i < totalCursos; i++) {
            if (cursos[i] != null && cursos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return cursos[i];
            }
        }
        return null;
    }

    public static boolean agregarSeccion(Seccion seccion) {
        if (totalSecciones >= secciones.length) return false;
        if (buscarSeccionPorCodigo(seccion.getCodigoSeccion()) != null) return false;

        secciones[totalSecciones] = seccion;
        totalSecciones++;
        return true;
    }

    public static Seccion buscarSeccionPorCodigo(String codigoSeccion) {
        for (int i = 0; i < totalSecciones; i++) {
            if (secciones[i] != null && secciones[i].getCodigoSeccion().equalsIgnoreCase(codigoSeccion)) {
                return secciones[i];
            }
        }
        return null;
    }

    public static boolean agregarNota(Nota nota) {
        if (totalNotas >= notas.length) return false;
        notas[totalNotas] = nota;
        totalNotas++;
        return true;
    }

    public static void agregarEvento(BitacoraEvento evento) {
        if (totalEventos < bitacora.length) {
            bitacora[totalEventos] = evento;
            totalEventos++;
        }
    }

    public static double calcularPromedioSeccionEstudiante(String codigoSeccion, String codigoEstudiante) {
        double suma = 0;
        double sumaPonderacion = 0;

        for (int i = 0; i < totalNotas; i++) {
            if (notas[i] != null
                    && notas[i].getCodigoSeccion().equalsIgnoreCase(codigoSeccion)
                    && notas[i].getCodigoEstudiante().equalsIgnoreCase(codigoEstudiante)) {
                suma += notas[i].getNota() * notas[i].getPonderacion();
                sumaPonderacion += notas[i].getPonderacion();
            }
        }

        if (sumaPonderacion == 0) return 0;
        return suma / sumaPonderacion;
    }

    public static int contarEstudiantes() {
        int c = 0;
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i] instanceof Estudiante) c++;
        }
        return c;
    }

    public static int contarInstructores() {
        int c = 0;
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i] instanceof Instructor) c++;
        }
        return c;
    }

    public static int contarAdministradores() {
        int c = 0;
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i] instanceof Administrador) c++;
        }
        return c;
    }
    public static boolean eliminarCurso(String codigo) {
    for (int i = 0; i < totalCursos; i++) {
        if (cursos[i] != null && cursos[i].getCodigo().equalsIgnoreCase(codigo)) {
            for (int j = i; j < totalCursos - 1; j++) {
                cursos[j] = cursos[j + 1];
            }
            cursos[totalCursos - 1] = null;
            totalCursos--;
            return true;
        }
    }
    return false;
}

public static boolean eliminarSeccion(String codigoSeccion) {
    for (int i = 0; i < totalSecciones; i++) {
        if (secciones[i] != null && secciones[i].getCodigoSeccion().equalsIgnoreCase(codigoSeccion)) {
            for (int j = i; j < totalSecciones - 1; j++) {
                secciones[j] = secciones[j + 1];
            }
            secciones[totalSecciones - 1] = null;
            totalSecciones--;
            return true;
        }
    }
    return false;
}

public static boolean instructorTieneSeccion(String codigoInstructor, String codigoSeccion) {
    Seccion s = buscarSeccionPorCodigo(codigoSeccion);
    return s != null && s.getCodigoInstructor().equalsIgnoreCase(codigoInstructor);
}

public static boolean estudianteInscrito(String codigoEstudiante, String codigoSeccion) {
    Seccion s = buscarSeccionPorCodigo(codigoSeccion);
    return s != null && s.estaInscrito(codigoEstudiante);
}

public static Nota buscarNota(String codigoCurso, String codigoSeccion, String codigoEstudiante, String etiqueta) {
    for (int i = 0; i < totalNotas; i++) {
        if (notas[i] != null
                && notas[i].getCodigoCurso().equalsIgnoreCase(codigoCurso)
                && notas[i].getCodigoSeccion().equalsIgnoreCase(codigoSeccion)
                && notas[i].getCodigoEstudiante().equalsIgnoreCase(codigoEstudiante)
                && notas[i].getEtiqueta().equalsIgnoreCase(etiqueta)) {
            return notas[i];
        }
    }
    return null;
}

public static boolean eliminarNota(String codigoCurso, String codigoSeccion, String codigoEstudiante, String etiqueta) {
    for (int i = 0; i < totalNotas; i++) {
        if (notas[i] != null
                && notas[i].getCodigoCurso().equalsIgnoreCase(codigoCurso)
                && notas[i].getCodigoSeccion().equalsIgnoreCase(codigoSeccion)
                && notas[i].getCodigoEstudiante().equalsIgnoreCase(codigoEstudiante)
                && notas[i].getEtiqueta().equalsIgnoreCase(etiqueta)) {

            for (int j = i; j < totalNotas - 1; j++) {
                notas[j] = notas[j + 1];
            }

            notas[totalNotas - 1] = null;
            totalNotas--;
            return true;
        }
    }
    return false;
}
}