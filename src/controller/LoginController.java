package controller;

import model.Usuario;
import system.Sesion;
import system.SistemaAcademy;
import util.LoggerBitacora;

public class LoginController {

    public Usuario autenticar(String codigo, String password) {
        Usuario usuario = SistemaAcademy.buscarUsuarioPorCodigo(codigo);

        if (usuario != null && usuario.getPassword().equals(password)) {
            Sesion.iniciar(usuario);

            LoggerBitacora.registrar(
                    usuario.getTipo(),
                    usuario.getCodigo(),
                    "LOGIN",
                    "EXITOSA",
                    "Inicio de sesión correcto"
            );

            return usuario;
        }

        LoggerBitacora.registrar(
                "DESCONOCIDO",
                codigo,
                "LOGIN",
                "FALLIDA",
                "Credenciales inválidas"
        );

        return null;
    }

    public void cerrarSesion() {
        if (Sesion.usuarioActual != null) {
            LoggerBitacora.registrar(
                    Sesion.usuarioActual.getTipo(),
                    Sesion.usuarioActual.getCodigo(),
                    "LOGOUT",
                    "EXITOSA",
                    "Cierre de sesión correcto"
            );
        }
        Sesion.cerrar();
    }
}