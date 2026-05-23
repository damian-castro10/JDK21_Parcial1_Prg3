package ar.edu.unlar.programacion3.parcial1.model;

import java.util.HashMap;
import java.util.Map;

public class EstacionAnclaje {
    private String nombreUnico;
    
    private Map<Vehiculo, String> listadoVehiculo;

    public EstacionAnclaje(String nombreUnico, Map<Vehiculo, String> listadoVehiculo) {
        this.nombreUnico = nombreUnico;
        this.listadoVehiculo = new HashMap<Vehiculo, String>();
    }

    public String getNombreUnico() {
        return nombreUnico;
    }

    public void setNombreUnico(String nombreUnico) {
        this.nombreUnico = nombreUnico;
    }

    public Map<Vehiculo, String> getListadoVehiculo() {
        return listadoVehiculo;
    }

    public void setListadoVehiculo(Map<Vehiculo, String> listadoVehiculo) {
        this.listadoVehiculo = listadoVehiculo;
    }    
}
