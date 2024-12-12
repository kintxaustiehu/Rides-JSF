package dataAccess;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import configuration.UtilDate;
import domain.Driver;
import domain.Ride;
import utils.JPAUtil;

public class DataAccess {
	private EntityManager em;

	public DataAccess() {
		this.em = JPAUtil.getEntityManager();
		initializeDB();
	}
	
	public DataAccess(EntityManager em) {
		this.em = em;
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB(){
		
		em.getTransaction().begin();

		try {
			

		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=1; year+=1;}  
	    
		   
		    //Create drivers	
	   		Driver driver1 = new Driver("driver1@gmail.com","Aitor Fernandez");
			Driver driver2 = new Driver("driver2@gmail.com","Ane Gaztañaga");
			Driver driver3 = new Driver("driver3@gmail.com","Test driver");

			
			//Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4);

			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8);
			
			driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 3);
			driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5);
			driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5);

			driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,14), 1, 3);

						
			em.persist(driver1);
			em.persist(driver2);
			em.persist(driver3);
			

			em.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
    public List<String> getDepartCities() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
        	return em.createQuery("SELECT DISTINCT r.origin FROM Ride r ORDER BY r.origin", String.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<String> getDestinationCities(String from) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
        	return em.createQuery("SELECT DISTINCT r.destination FROM Ride r WHERE r.origin = :from ORDER BY r.destination", String.class)
                    .setParameter("from", from)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Ride> getRides(String origin, String destination, Date date) {
        EntityManager em = JPAUtil.getEntityManager();
        
        try {        	
        	return em.createQuery("SELECT r FROM Ride r WHERE r.origin = :origin AND r.destination = :destination", Ride.class)
                    .setParameter("origin", origin)
                    .setParameter("destination", destination)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
    public Driver createDriver(String email, String name, String password) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Query query = em.createQuery("SELECT d FROM Driver d WHERE d.email = :email");
            query.setParameter("email", email);
            if (!query.getResultList().isEmpty()) {
                throw new Exception("Ya existe un conductor con el mismo email");
            }

            Driver driver = new Driver();
            driver.setName(name);
            driver.setEmail(email);
            driver.setPassword(password);

            em.persist(driver);
            em.getTransaction().commit();
            return driver;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public List<String> getAllDriverEmails() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT d.email FROM Driver d", String.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    public Driver getDriver(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Driver d where d.email = :email", Driver.class)
            		.setParameter("email", email)
            		.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    public void createRide(String origen, String destino, Date date, int nPlaces, float price, Driver driver) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Ride ride = new Ride(origen, destino, date, nPlaces, price, driver);

            em.persist(ride);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al crear el viaje: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    public Driver validateUser(String email, String password) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			Query query = em.createQuery("SELECT d FROM Driver d WHERE d.email = :email AND d.password = :password",
					Driver.class);
			query.setParameter("email", email);
			query.setParameter("password", password);

			List<Driver> result = query.getResultList();
			if (!result.isEmpty()) {
				return result.get(0); // Return the first matching user
			}
			return null; // No user found
		} finally {
			em.close();
		}
	}
}