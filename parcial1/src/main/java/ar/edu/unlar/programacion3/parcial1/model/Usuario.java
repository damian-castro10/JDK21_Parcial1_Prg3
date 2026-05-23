package ar.edu.unlar.programacion3.parcial1.model;

public class Usuario {
    private String ID;
    private String nombreCompleto;

    
    public Usuario(String iD, String nombreCompleto) {
        ID = iD;
        this.nombreCompleto = nombreCompleto;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
