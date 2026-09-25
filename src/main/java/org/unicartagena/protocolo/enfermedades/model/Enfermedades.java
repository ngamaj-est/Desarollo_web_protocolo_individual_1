package org.unicartagena.protocolo.enfermedades.model;

public class Enfermedades {
    private int id;
    private String nombre;
    private String nombreCientifico;
    private String nivelGravedad;
    private String sintomas;
    private String medicamentos;
    private boolean esContagiosa;
    private boolean esCubiertaPorPos;
    private boolean requiereIncapacidad;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNombreCientifico() { return nombreCientifico; }
    public void setNombreCientifico(String nombreCientifico) { this.nombreCientifico = nombreCientifico; }
    public String getNivelGravedad() { return nivelGravedad; }
    public void setNivelGravedad(String nivelGravedad) { this.nivelGravedad = nivelGravedad; }
    public String getSintomas() { return sintomas; }
    public void setSintomas(String sintomas) { this.sintomas = sintomas; }
    public String getMedicamentos() { return medicamentos; }
    public void setMedicamentos(String medicamentos) { this.medicamentos = medicamentos; }
    public boolean isEsContagiosa() { return esContagiosa; }
    public void setEsContagiosa(boolean esContagiosa) { this.esContagiosa = esContagiosa; }
    public boolean isEsCubiertaPorPos() { return esCubiertaPorPos; }
    public void setEsCubiertaPorPos(boolean esCubiertaPorPos) { this.esCubiertaPorPos = esCubiertaPorPos; }
    public boolean isRequiereIncapacidad() { return requiereIncapacidad; }
    public void setRequiereIncapacidad(boolean requiereIncapacidad) { this.requiereIncapacidad = requiereIncapacidad; }
}