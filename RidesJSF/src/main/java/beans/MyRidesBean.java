package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import configuration.UtilDate;
import domain.Driver;
import domain.Ride;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("myRides")
@SessionScoped
public class MyRidesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static List<Ride> rides = new ArrayList<Ride>();

	public MyRidesBean() {
		try {
			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 1;
				year += 1;
			}

			// Create driver
			Driver driver1 = new Driver("driver1@gmail.com", "Aitor Fernandez");

			// Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 4, 7);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year, month, 6), 4, 8);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 4, 4);
			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year, month, 7), 4, 8);

			this.rides = driver1.rides;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Ride> getRides() {
		return this.rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

}