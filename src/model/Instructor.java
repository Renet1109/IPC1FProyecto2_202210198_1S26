package model;

public class Instructor extends Usuario {

    public Instructor(String codigo, String nombre, String fechaNacimiento, String genero, String password) {
        super(codigo, nombre, fechaNacimiento, genero, password);
    }

    @Override
    public String getTipo() {
        return "INSTRUCTOR";
    }
}