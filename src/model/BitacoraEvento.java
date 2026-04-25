package model;

import java.io.Serializable;

public class BitacoraEvento implements Serializable {
    private String fechaHora;
    private String tipoUsuario;
    private String codigoUsuario;
    private String operacion;
    private String estado;
    private String descripcion;

    public BitacoraEvento(String fechaHora, String tipoUsuario, String codigoUsuario, String operacion, String estado, String descripcion) {
        this.fechaHora = fechaHora;
        this.tipoUsuario = tipoUsuario;
        this.codigoUsuario = codigoUsuario;
        this.operacion = operacion;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public String getOperacion() {
        return operacion;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "[" + fechaHora + "] | [" + tipoUsuario + "] | [" + codigoUsuario + "] | [" + operacion + "] | [" + estado + "] | [" + descripcion + "]";
    }
}