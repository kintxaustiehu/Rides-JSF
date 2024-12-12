package beans;

import businessLogic.BLFacadeImplementation;
import domain.Driver;
import domain.Ride;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.util.Date;
import java.util.List;

public class CreateRideBean {
    private String origen;
    private String destino;
    private Date fecha;
    private int nPlaces;
    private float price;
    private String selectedDriver;

    private List<String> drivers;
    private BLFacadeImplementation facade;

    public CreateRideBean() {
        this.facade = new BLFacadeImplementation();
        this.drivers = facade.getAllDriverEmails();
    }

    public String createRide() {
        try {
            if (selectedDriver == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un conductor", null));
                return null;
            }
            
            Driver driver = facade.getDriver(selectedDriver); 

            facade.createRide(origen, destino, fecha, nPlaces, price, driver);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Viaje creado correctamente", null));
            return "QueryRides?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getnPlaces() {
        return nPlaces;
    }

    public void setnPlaces(int nPlaces) {
        this.nPlaces = nPlaces;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(String selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public List<String> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<String> drivers) {
        this.drivers = drivers;
    }
}
