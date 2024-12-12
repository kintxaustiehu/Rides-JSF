package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import businessLogic.BLFacadeImplementation;
import domain.Ride;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("myRides")
@SessionScoped
public class MyRidesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Ride> rides = new ArrayList<>();

	private BLFacadeImplementation facade = new BLFacadeImplementation();

	public MyRidesBean() {

	}

	public void loadUserRides() {
		// Get the current logged-in username from the login bean
		String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("username");

		if (username != null) {
			// Use facade to retrieve rides for the specific user
			this.rides = facade.getRidesByUsername(username);
		}
	}

	public List<Ride> getRides() {
		// Ensure rides are loaded before returning
		if (rides == null || rides.isEmpty()) {
			loadUserRides();
		}
		return this.rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

	public String createRide() {
		return "CreateRide";
	}

	public String signOut() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "Login";
	}
}