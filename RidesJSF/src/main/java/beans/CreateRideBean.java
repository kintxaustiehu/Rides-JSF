package beans;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("createRideBean")
@RequestScoped
public class CreateRideBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String departureCity;
	private String arrivalCity;
	private int numberOfSeats;
	private double price;
	private Date rideDate;

	private static final Logger LOGGER = Logger.getLogger(CreateRideBean.class.getName());

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getRideDate() {
		return rideDate;
	}

	public void setRideDate(Date rideDate) {
		this.rideDate = rideDate;
	}

	public void createRide() {
		LOGGER.log(Level.INFO, "Ride Created: {0}, {1}, {2}, {3}, {4}",
				new Object[] { departureCity, arrivalCity, numberOfSeats, price, rideDate });
		// Logic for saving the ride missing (Part 2 with Hibernate)
	}

	public String signOut() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "Login";
	}
}