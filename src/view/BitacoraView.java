package view;

import system.SistemaAcademy;
import util.ReportesCSV;

import javax.swing.*;

public class BitacoraView extends JFrame {

    public BitacoraView() {
        setTitle("Bitácora del Sistema");
        setSize(750, 500);
        setLayout(null);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(30, 30, 680, 340);
        add(scroll);

        JButton btnExportar = new JButton("Exportar CSV");
        btnExportar.setBounds(290, 390, 160, 35);
        add(btnExportar);

        String texto = "";

        for (int i = 0; i < SistemaAcademy.totalEventos; i++) {
            if (SistemaAcademy.bitacora[i] != null) {
                texto += SistemaAcademy.bitacora[i].toString() + "\n";
            }
        }

        area.setText(texto);

        btnExportar.addActionListener(e -> {
            boolean ok = ReportesCSV.exportarBitacora();
            JOptionPane.showMessageDialog(this, ok ? "Bitácora exportada correctamente." : "Error al exportar.");
        });

        setVisible(true);
    }
}