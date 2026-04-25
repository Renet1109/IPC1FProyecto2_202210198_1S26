package model;

public class Estudiante extends Usuario {

    public Estudiante(String codigo, String nombre, String fechaNacimiento, String genero, String password) {
        super(codigo, nombre, fechaNacimiento, genero, password);
    }

    @Override
    public String getTipo() {
        return "ESTUDIANTE";
    }
}