package businessLogic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;

public class BLFacadeImplementation implements Serializable {
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

	public Driver validateUser(String email, String password) {
		return dataAccess.validateUser(email, password);
	}
}