package ar.edu.unlar.programacion3.parcial1.model;

public class ProcesamientoPago {
    private TipoPago tipo;

    public ProcesamientoPago(TipoPago tipo) {
        this.tipo = tipo;
    }

    public TipoPago getTipo() {
        return tipo;
    }

    public void setTipo(TipoPago tipo) {
        this.tipo = tipo;
    }

    public void cobrarTarifa(TipoPago tipo, Double monto){
        System.out.println("Cobro exitoso de " + monto + " realizado con " + tipo);
    }
}
