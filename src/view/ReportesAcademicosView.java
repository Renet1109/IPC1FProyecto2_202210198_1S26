package view;

import system.SistemaAcademy;
import util.ReportesCSV;

import javax.swing.*;

public class ReportesAcademicosView extends JFrame {

    public ReportesAcademicosView() {
        setTitle("Reportes Académicos");
        setSize(420, 260);
        setLayout(null);
        setLocationRelativeTo(null);

        JButton btnTop5 = new JButton("Top 5 Mejores Promedios");
        btnTop5.setBounds(80, 40, 250, 35);
        add(btnTop5);

        JButton btnBajo = new JButton("Top 5 Bajo Desempeño");
        btnBajo.setBounds(80, 90, 250, 35);
        add(btnBajo);

        JButton btnSecciones = new JButton("Reporte Secciones");
        btnSecciones.setBounds(80, 140, 250, 35);
        add(btnSecciones);

        btnTop5.addActionListener(e -> {
            boolean ok = ReportesCSV.reporteTop5Mejores();
            JOptionPane.showMessageDialog(this, ok ? "Reporte Top 5 generado." : "Error al generar reporte.");
        });

        btnBajo.addActionListener(e -> {
            boolean ok = ReportesCSV.reporteTop5Bajo();
            JOptionPane.showMessageDialog(this, ok ? "Reporte Bajo Desempeño generado." : "Error al generar reporte.");
        });

        btnSecciones.addActionListener(e -> {
            boolean ok = ReportesCSV.reporteRendimientoSecciones();
            JOptionPane.showMessageDialog(this, ok ? "Reporte Secciones generado." : "Error al generar reporte.");
        });

        setVisible(true);
    }
}