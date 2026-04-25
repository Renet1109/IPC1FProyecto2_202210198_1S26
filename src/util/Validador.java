package util;

public class Validador {

    public static boolean textoVacio(String txt) {
        return txt == null || txt.trim().isEmpty();
    }

    public static boolean esNumeroEntero(String txt) {
        try {
            Integer.parseInt(txt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean esDouble(String txt) {
        try {
            Double.parseDouble(txt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean notaValida(double nota) {
        return nota >= 0 && nota <= 100;
    }

    public static boolean ponderacionValida(double ponderacion) {
        return ponderacion > 0;
    }
}