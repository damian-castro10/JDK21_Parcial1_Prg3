package ar.edu.unlar.programacion3.parcial1.model;

public abstract class Vehiculo {
    protected String numeroDePatente;
    protected int porcentajeDeBateria;
    protected Double tarifa;

    public Vehiculo(String numeroDePatente, int porcentajeDeBateria, Double tarifa) {
        this.numeroDePatente = numeroDePatente;
        this.porcentajeDeBateria = porcentajeDeBateria;
        this.tarifa = tarifa;
    }

    public String getNumeroDePatente() {
        return numeroDePatente;
    }

    public void setNumeroDePatente(String numeroDePatente) {
        this.numeroDePatente = numeroDePatente;
    }

    public int getPorcentajeDeBateria() {
        return porcentajeDeBateria;
    }

    public void setPorcentajeDeBateria(int porcentajeDeBateria) {
        this.porcentajeDeBateria = porcentajeDeBateria;
    }

    public Double getTarifa() {
        return tarifa;
    }

    public void setTarifa(Double tarifa) {
        this.tarifa = tarifa;
    }

    
}
