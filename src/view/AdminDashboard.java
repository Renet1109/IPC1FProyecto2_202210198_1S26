package view;

import controller.LoginController;
import system.Sesion;
import system.SistemaAcademy;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private JTextArea areaInfo;
    private Thread hiloSesiones;
    private boolean ejecutando = true;

    public AdminDashboard() {
        setTitle("Panel Administrador");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Panel de Administrador");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(180, 20, 300, 30);
        add(lblTitulo);

        JButton btnCrearInstructor = new JButton("Crear Instructor");
        btnCrearInstructor.setBounds(50, 80, 180, 35);
        add(btnCrearInstructor);

        JButton btnCrearEstudiante = new JButton("Crear Estudiante");
        btnCrearEstudiante.setBounds(250, 80, 180, 35);
        add(btnCrearEstudiante);

        JButton btnCrearCurso = new JButton("Crear Curso");
        btnCrearCurso.setBounds(450, 80, 140, 35);
        add(btnCrearCurso);
        
        JButton btnReporteInscripciones = new JButton("Reporte Inscripciones");
btnReporteInscripciones.setBounds(450, 125, 180, 35);
add(btnReporteInscripciones);

btnReporteInscripciones.addActionListener(e -> {
    boolean ok = util.ReportesCSV.reporteInscripciones();
    JOptionPane.showMessageDialog(this, ok ? "Reporte CSV generado." : "Error al generar reporte.");
});

        
        JButton btnBitacora = new JButton("Ver Bitácora");
btnBitacora.setBounds(50, 125, 180, 35);
add(btnBitacora);

btnBitacora.addActionListener(e -> new BitacoraView());

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(240, 350, 150, 35);
        add(btnCerrarSesion);
        
        JButton btnSecciones = new JButton("Crear Sección");
btnSecciones.setBounds(250, 125, 180, 35);
add(btnSecciones);

btnSecciones.addActionListener(e -> new AdminSeccionesView());

        areaInfo = new JTextArea();
        areaInfo.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaInfo);
        scroll.setBounds(50, 140, 540, 180);
        add(scroll);

        btnCrearInstructor.addActionListener(e -> new AdminUsuariosView("INSTRUCTOR"));
       btnCrearEstudiante.addActionListener(e -> new AdminUsuariosView("ESTUDIANTE"));
        btnCrearCurso.addActionListener(e -> JOptionPane.showMessageDialog(this, "Aquí irá la ventana CRUD de cursos."));

        btnCerrarSesion.addActionListener(e -> {
            detenerHilos();
            new LoginController().cerrarSesion();
            dispose();
            new LoginView();
        });

        iniciarHiloSesiones();

        setVisible(true);
    }

    private void iniciarHiloSesiones() {
        hiloSesiones = new Thread(() -> {
            while (ejecutando) {
                areaInfo.setText(
                        "[Thread-Sesiones]\n" +
                        "Usuario actual: " + Sesion.usuarioActual.getNombre() + "\n" +
                        "Usuarios activos: " + SistemaAcademy.usuariosActivos + "\n" +
                        "Instructores: " + SistemaAcademy.contarInstructores() + "\n" +
                        "Estudiantes: " + SistemaAcademy.contarEstudiantes() + "\n" +
                        "Cursos: " + SistemaAcademy.totalCursos + "\n" +
                        "Secciones: " + SistemaAcademy.totalSecciones + "\n" +
                        "Notas: " + SistemaAcademy.totalNotas + "\n" +
                        "Inscripciones pendientes: " + SistemaAcademy.inscripcionesPendientes + "\n"
                );

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    break;
                }
            }
        });

        hiloSesiones.start();
    }

    private void detenerHilos() {
        ejecutando = false;
        if (hiloSesiones != null) {
            hiloSesiones.interrupt();
        }
    }
}