package view;

import javax.swing.*;

public class DesarrolladorView {

    public static void mostrar() {
        JOptionPane.showMessageDialog(
                null,
                "Datos del Estudiante\n\n" +
                "Nombre: René Toledo\n" +
                "Carné: 202210198\n" +
                "Curso: IPC 1\n" +
                "Proyecto: Sancarlista Academy",
                "Información del Desarrollador",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}