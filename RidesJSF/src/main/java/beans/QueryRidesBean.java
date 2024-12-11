package beans;

import java.util.List;

import businessLogic.BLFacadeImplementation;

import java.util.ArrayList;
import java.util.Date;
import domain.Ride;

public class QueryRidesBean {
    private String origen;
    private String destino;
    private Date fecha;
    private List<Ride> rides;
    
    private List<String> origenes;
    private List<String> destinos;

    private BLFacadeImplementation facade = new BLFacadeImplementation();
    
    
    public QueryRidesBean() {
        this.origenes = facade.getDepartCities();
        this.destinos =  new ArrayList<>();
        this.rides = new ArrayList<>();
    }
    
    public void updateDestinos() {
        if (this.origen != null && !this.origen.isEmpty()) {
            this.destinos = facade.getDestinationCities(this.origen);
        } else {
            this.destino = null;
        }
    }
    
    public boolean searchRides() {
        if (this.origen != null && this.destino != null && this.fecha != null) {
            this.rides = facade.getRides(this.origen, this.destino, this.fecha);
            if(this.rides.isEmpty()) return false;
            return true;
        }
        return false;
    }

    // Getters y Setters
    public List<Ride> getRides() {
        return this.rides;
    }

    public String getOrigen() {
        return this.origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return this.destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    
    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}