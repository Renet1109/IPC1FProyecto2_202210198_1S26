package view;

import system.SistemaAcademy;

import javax.swing.*;

public class BitacoraView extends JFrame {

    public BitacoraView() {
        setTitle("Bitácora del Sistema");
        setSize(750, 450);
        setLayout(null);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(30, 30, 680, 340);
        add(scroll);

        String texto = "";

        for (int i = 0; i < SistemaAcademy.totalEventos; i++) {
            if (SistemaAcademy.bitacora[i] != null) {
                texto += SistemaAcademy.bitacora[i].toString() + "\n";
            }
        }

        area.setText(texto);
        setVisible(true);
    }
}