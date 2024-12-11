package businessLogic;
import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;

import java.util.List;
import java.io.Serializable;
import java.util.Date;

public class BLFacadeImplementation implements Serializable{
    private DataAccess dataAccess = new DataAccess();
    
    public List<String> getDepartCities() {
        return dataAccess.getDepartCities();
    }
    
    public List<String> getDestinationCities(String from) {
        return dataAccess.getDestinationCities(from);
    }
    
    public List<Ride> getRides(String from, String to, Date date) {
        return dataAccess.getRides(from, to, date);
    }
    
    public Driver createDriver(String email, String name, String password) throws Exception {
        return dataAccess.createDriver(email, name, password);
    }
    
    public boolean login(String email, String password) {
    	return dataAccess.login(email, password);
    }
    
    public List<Driver> getAllDrivers() {
        return dataAccess.getAllDrivers();
    }
    
    public void createRide(String origen, String destino, Date date, int nPlaces, float price, Driver driver) throws Exception {
        dataAccess.createRide(origen, destino, date, nPlaces, price, driver);
    }
}