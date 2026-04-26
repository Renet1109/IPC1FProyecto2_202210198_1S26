package view;

import controller.LoginController;
import system.Sesion;
import system.SistemaAcademy;

import javax.swing.*;
import java.awt.*;

public class InstructorDashboard extends JFrame {
    private JTextArea areaInfo;
    private Thread hiloEstadisticas;
    private boolean ejecutando = true;

    public InstructorDashboard() {
        setTitle("Panel Instructor");
        setSize(650, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Panel de Instructor");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(210, 20, 240, 30);
        add(lblTitulo);

        JButton btnNotas = new JButton("Gestión de Notas");
        btnNotas.setBounds(120, 80, 180, 35);
        add(btnNotas);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(340, 80, 180, 35);
        add(btnCerrarSesion);
        
        JButton btnDev = new JButton("Datos Estudiante");
btnDev.setBounds(230, 210, 180, 35);
add(btnDev);

btnDev.addActionListener(e -> DesarrolladorView.mostrar());
        areaInfo = new JTextArea();
        areaInfo.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaInfo);
        scroll.setBounds(70, 140, 500, 180);
        add(scroll);

        btnNotas.addActionListener(e -> new InstructorNotasView());
        btnCerrarSesion.addActionListener(e -> {
            detenerHilos();
            new LoginController().cerrarSesion();
            dispose();
            new LoginView();
        });

        iniciarHiloEstadisticas();

        setVisible(true);
    }

    private void iniciarHiloEstadisticas() {
        hiloEstadisticas = new Thread(() -> {
            while (ejecutando) {
                areaInfo.setText(
                        "[Thread-Estadísticas]\n" +
                        "Instructor: " + Sesion.usuarioActual.getNombre() + "\n" +
                        "Cursos activos: " + SistemaAcademy.totalCursos + "\n" +
                        "Estudiantes registrados: " + SistemaAcademy.contarEstudiantes() + "\n" +
                        "Calificaciones registradas: " + SistemaAcademy.totalNotas + "\n"
                );

                try {
                    Thread.sleep(15000);
                } catch (InterruptedException ex) {
                    break;
                }
            }
        });

        hiloEstadisticas.start();
    }

    private void detenerHilos() {
        ejecutando = false;
        if (hiloEstadisticas != null) {
            hiloEstadisticas.interrupt();
        }
    }
}