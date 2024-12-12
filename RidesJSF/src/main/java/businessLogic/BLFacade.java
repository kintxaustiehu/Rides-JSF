package businessLogic;

import java.util.Date;
import java.util.List;

import domain.Driver;
//import domain.Booking;
import domain.Ride;

/**
 * Interface that specifies the business logic.
 */
public interface BLFacade {

	/**
	 * This method returns all the cities where rides depart
	 * 
	 * @return collection of cities
	 */
	public List<String> getDepartCities();

	/**
	 * This method returns all the arrival destinations, from all rides that depart
	 * from a given city
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getDestinationCities(String from);

	/**
	 * This method retrieves the rides from two locations on a given date
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date);

	/**
	 * This method creates a new driver account
	 * 
	 * @param email    driver's email address
	 * @param password driver's password
	 * @param name     driver's name
	 * @return the created driver, or throws an exception if driver already exists
	 */
	public Driver createDriver(String email, String password, String name) throws Exception;

	/**
	 * This method validates a user's credentials
	 * 
	 * @param email    driver's email
	 * @param password driver's password
	 * @return the validated driver, or null if credentials are invalid
	 */
	public Driver validateUser(String email, String password);

	/**
	 * This method retrieves all rides for a specific user by their username (email)
	 * 
	 * @param username the email of the driver
	 * @return list of rides for the specified user
	 */
	public List<Ride> getRidesByUsername(String username);
}