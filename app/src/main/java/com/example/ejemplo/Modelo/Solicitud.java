package com.example.ejemplo.Modelo;

public class Solicitud {
    private String fk_idP, fk_patente, nombreInicio, latInicio, lonInicio, nombreDestino, latDestino, lonDestino;

    public Solicitud(String fk_idP, String fk_patente, String nombreInicio, String latInicio, String lonInicio, String nombreDestino, String latDestino, String lonDestino) {
        this.fk_idP = fk_idP;
        this.fk_patente = fk_patente;
        this.nombreInicio = nombreInicio;
        this.latInicio = latInicio;
        this.lonInicio = lonInicio;
        this.nombreDestino = nombreDestino;
        this.latDestino = latDestino;
        this.lonDestino = lonDestino;
    }

    public String getFk_idP() {
        return fk_idP;
    }

    public void setFk_idP(String fk_idP) {
        this.fk_idP = fk_idP;
    }

    public String getFk_patente() {
        return fk_patente;
    }

    public void setFk_patente(String fk_patente) {
        this.fk_patente = fk_patente;
    }

    public String getNombreInicio() {
        return nombreInicio;
    }

    public void setNombreInicio(String nombreInicio) {
        this.nombreInicio = nombreInicio;
    }

    public String getLatInicio() {
        return latInicio;
    }

    public void setLatInicio(String latInicio) {
        this.latInicio = latInicio;
    }

    public String getLonInicio() {
        return lonInicio;
    }

    public void setLonInicio(String lonInicio) {
        this.lonInicio = lonInicio;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }

    public String getLatDestino() {
        return latDestino;
    }

    public void setLatDestino(String latDestino) {
        this.latDestino = latDestino;
    }

    public String getLonDestino() {
        return lonDestino;
    }

    public void setLonDestino(String lonDestino) {
        this.lonDestino = lonDestino;
    }
}
