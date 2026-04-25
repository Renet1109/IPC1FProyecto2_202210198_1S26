package model;

public class Administrador extends Usuario {

    public Administrador(String codigo, String nombre, String fechaNacimiento, String genero, String password) {
        super(codigo, nombre, fechaNacimiento, genero, password);
    }

    @Override
    public String getTipo() {
        return "ADMINISTRADOR";
    }
}