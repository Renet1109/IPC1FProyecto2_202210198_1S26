package model;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
    protected String codigo;
    protected String nombre;
    protected String fechaNacimiento;
    protected String genero;
    protected String password;

    public Usuario(String codigo, String nombre, String fechaNacimiento, String genero, String password) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.password = password;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public String getPassword() {
        return password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract String getTipo();
}