package view;

import controller.LoginController;
import system.Sesion;
import system.SistemaAcademy;

import javax.swing.*;
import java.awt.*;

public class EstudianteDashboard extends JFrame {
    private JTextArea areaInfo;
    private Thread hiloInscripciones;
    private boolean ejecutando = true;

    public EstudianteDashboard() {
        setTitle("Panel Estudiante");
        setSize(650, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Panel de Estudiante");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(205, 20, 250, 30);
        add(lblTitulo);

        JButton btnInscribir = new JButton("Inscribirse a Sección");
        btnInscribir.setBounds(110, 80, 190, 35);
        add(btnInscribir);
        
        JButton btnNotas = new JButton("Ver notas");
        btnNotas.setBounds(230, 120, 180, 35);
        add(btnNotas);
        btnNotas.addActionListener(e -> new EstudianteNotasView());
        
        JButton btnDev = new JButton("Datos Estudiante");
btnDev.setBounds(230, 210, 180, 35);
add(btnDev);

btnDev.addActionListener(e -> DesarrolladorView.mostrar());

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(340, 80, 180, 35);
        add(btnCerrarSesion);
        
        
        
        JButton btnPerfil = new JButton("Mi Perfil");
btnPerfil.setBounds(230, 165, 180, 35);
add(btnPerfil);

btnPerfil.addActionListener(e -> new EstudiantePerfilView());

        areaInfo = new JTextArea();
        areaInfo.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaInfo);
        scroll.setBounds(70, 140, 500, 180);
        add(scroll);

        btnInscribir.addActionListener(e -> new EstudianteCursosView());
        btnCerrarSesion.addActionListener(e -> {
            detenerHilos();
            new LoginController().cerrarSesion();
            dispose();
            new LoginView();
        });

        iniciarHiloInscripciones();

        setVisible(true);
    }

    private void iniciarHiloInscripciones() {
        hiloInscripciones = new Thread(() -> {
            while (ejecutando) {
                areaInfo.setText(
                        "[Thread-Inscripciones]\n" +
                        "Estudiante: " + Sesion.usuarioActual.getNombre() + "\n" +
                        "Inscripciones pendientes: " + SistemaAcademy.inscripcionesPendientes + "\n" +
                        "Secciones activas: " + SistemaAcademy.totalSecciones + "\n"
                );

                try {
                    Thread.sleep(8000);
                } catch (InterruptedException ex) {
                    break;
                }
            }
        });

        hiloInscripciones.start();
    }

    private void detenerHilos() {
        ejecutando = false;
        if (hiloInscripciones != null) {
            hiloInscripciones.interrupt();
        }
    }
}