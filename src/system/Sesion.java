package system;

import model.Usuario;

public class Sesion {
    public static Usuario usuarioActual = null;

    public static void iniciar(Usuario usuario) {
        usuarioActual = usuario;
        SistemaAcademy.usuariosActivos++;
    }

    public static void cerrar() {
        if (usuarioActual != null && SistemaAcademy.usuariosActivos > 0) {
            SistemaAcademy.usuariosActivos--;
        }
        usuarioActual = null;
    }

    public static boolean haySesion() {
        return usuarioActual != null;
    }
}