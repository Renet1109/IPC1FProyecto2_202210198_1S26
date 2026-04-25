package model;

import java.io.Serializable;

public class Seccion implements Serializable {
    private String codigoSeccion;
    private String codigoCurso;
    private String codigoInstructor;
    private String semestre;
    private String horario;
    private boolean abierta;
    private String[] estudiantesInscritos;
    private int totalInscritos;

    public Seccion(String codigoSeccion, String codigoCurso, String codigoInstructor, String semestre, String horario, boolean abierta) {
        this.codigoSeccion = codigoSeccion;
        this.codigoCurso = codigoCurso;
        this.codigoInstructor = codigoInstructor;
        this.semestre = semestre;
        this.horario = horario;
        this.abierta = abierta;
        this.estudiantesInscritos = new String[200];
        this.totalInscritos = 0;
    }

    public String getCodigoSeccion() {
        return codigoSeccion;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public String getCodigoInstructor() {
        return codigoInstructor;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getHorario() {
        return horario;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setCodigoInstructor(String codigoInstructor) {
        this.codigoInstructor = codigoInstructor;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public int getTotalInscritos() {
        return totalInscritos;
    }

    public String[] getEstudiantesInscritos() {
        return estudiantesInscritos;
    }

    public boolean estaInscrito(String codigoEstudiante) {
        for (int i = 0; i < totalInscritos; i++) {
            if (estudiantesInscritos[i].equals(codigoEstudiante)) {
                return true;
            }
        }
        return false;
    }

    public boolean inscribirEstudiante(String codigoEstudiante) {
        if (estaInscrito(codigoEstudiante)) {
            return false;
        }
        if (totalInscritos < estudiantesInscritos.length) {
            estudiantesInscritos[totalInscritos] = codigoEstudiante;
            totalInscritos++;
            return true;
        }
        return false;
    }

    public boolean desasignarEstudiante(String codigoEstudiante) {
        for (int i = 0; i < totalInscritos; i++) {
            if (estudiantesInscritos[i].equals(codigoEstudiante)) {
                for (int j = i; j < totalInscritos - 1; j++) {
                    estudiantesInscritos[j] = estudiantesInscritos[j + 1];
                }
                estudiantesInscritos[totalInscritos - 1] = null;
                totalInscritos--;
                return true;
            }
        }
        return false;
    }
}