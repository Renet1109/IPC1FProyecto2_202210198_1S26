package sancarlistaacademy;

import system.SistemaAcademy;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
        SistemaAcademy.inicializarSistema();
        new LoginView();
    }
}