package ar.edu.unlar.programacion3.parcial1.model;

public class BicicletaElectrica extends Vehiculo{
    private Double tamañoCanasta;

    public BicicletaElectrica(String numeroDePatente, int porcentajeDeBateria, Double tarifa, Double tamañoCanasta) {
        super(numeroDePatente, porcentajeDeBateria, tarifa);
        this.tamañoCanasta = tamañoCanasta;
    }

    public Double getTamañoCanasta() {
        return tamañoCanasta;
    }

    public void setTamañoCanasta(Double tamañoCanasta) {
        this.tamañoCanasta = tamañoCanasta;
    }
}
