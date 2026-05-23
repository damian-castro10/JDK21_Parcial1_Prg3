package ar.edu.unlar.programacion3.parcial1.model;

public class Monopatin extends Vehiculo{
    private Boolean amortiguacion;

    public Monopatin(String numeroDePatente, int porcentajeDeBateria, Double tarifa, Boolean amortiguacion) {
        super(numeroDePatente, porcentajeDeBateria, tarifa);
        this.amortiguacion = amortiguacion;
    }

    public Boolean getAmortiguacion() {
        return amortiguacion;
    }

    public void setAmortiguacion(Boolean amortiguacion) {
        this.amortiguacion = amortiguacion;
    }
}
