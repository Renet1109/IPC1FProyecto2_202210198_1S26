package util;

import model.BitacoraEvento;
import system.SistemaAcademy;

public class LoggerBitacora {

    public static void registrar(String tipoUsuario, String codigoUsuario, String operacion, String estado, String descripcion) {
        BitacoraEvento evento = new BitacoraEvento(
                FechaUtil.ahora(),
                tipoUsuario,
                codigoUsuario,
                operacion,
                estado,
                descripcion
        );

        SistemaAcademy.agregarEvento(evento);
        SistemaAcademy.guardarTodo();
    }
}