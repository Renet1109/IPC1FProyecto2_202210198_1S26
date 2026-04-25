package model;

import java.io.Serializable;

public class Nota implements Serializable {
    private String codigoCurso;
    private String codigoSeccion;
    private String codigoEstudiante;
    private String etiqueta;
    private double ponderacion;
    private double nota;
    private String fechaRegistro;

    public Nota(String codigoCurso, String codigoSeccion, String codigoEstudiante, String etiqueta, double ponderacion, double nota, String fechaRegistro) {
        this.codigoCurso = codigoCurso;
        this.codigoSeccion = codigoSeccion;
        this.codigoEstudiante = codigoEstudiante;
        this.etiqueta = etiqueta;
        this.ponderacion = ponderacion;
        this.nota = nota;
        this.fechaRegistro = fechaRegistro;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public String getCodigoSeccion() {
        return codigoSeccion;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public double getPonderacion() {
        return ponderacion;
    }

    public double getNota() {
        return nota;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public void setPonderacion(double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}