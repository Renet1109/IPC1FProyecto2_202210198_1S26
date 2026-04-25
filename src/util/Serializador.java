package util;

import java.io.*;

public class Serializador {

    public static void guardar(String nombreArchivo, Object objeto) {
        try {
            ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
            salida.writeObject(objeto);
            salida.close();
        } catch (Exception e) {
            System.out.println("Error al guardar " + nombreArchivo + ": " + e.getMessage());
        }
    }

    public static Object cargar(String nombreArchivo) {
        try {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo));
            Object obj = entrada.readObject();
            entrada.close();
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

    public static void guardarEntero(String nombreArchivo, int valor) {
        try {
            ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
            salida.writeInt(valor);
            salida.close();
        } catch (Exception e) {
            System.out.println("Error al guardar entero " + nombreArchivo + ": " + e.getMessage());
        }
    }

    public static Integer cargarEntero(String nombreArchivo) {
        try {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo));
            int valor = entrada.readInt();
            entrada.close();
            return valor;
        } catch (Exception e) {
            return null;
        }
    }
}