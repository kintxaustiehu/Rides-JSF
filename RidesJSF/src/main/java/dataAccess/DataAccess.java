package dataAccess;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import configuration.UtilDate;
import domain.Driver;
import domain.Ride;
import utils.JPAUtil;

public class DataAccess {
	private EntityManager em;

	public DataAccess(Boolean initializeMode) {
		this.em = JPAUtil.getEntityManager();
		if(initializeMode)
			initializeDB();
	}

	public DataAccess() {
		new DataAccess(false);
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
			Driver driver1=new Driver("driver1@gmail.com","Aitor Fernandez");
			Driver driver2=new Driver("driver2@gmail.com","Ane Gaztañaga");
			Driver driver3=new Driver("driver3@gmail.com","Test driver");

			
			//Create rides
			Ride ride1 = driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7);
			Ride ride2 = driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8);
			Ride ride3 = driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4);

			Ride ride4 = driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8);
			
			Ride ride5 = driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 3, 3);
			Ride ride6 = driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 2, 5);
			Ride ride7 = driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year,month,6), 2, 5);

			Ride ride8 = driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,14), 1, 3);

						
			em.persist(driver1);
			em.persist(driver2);
			em.persist(driver3);
			
			em.persist(ride1);
			em.persist(ride2);
			em.persist(ride3);
			em.persist(ride4);
			em.persist(ride5);
			em.persist(ride6);
			em.persist(ride7);
			em.persist(ride8);
			

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

    public List<Ride> getRides(String from, String to, Date date) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
        	return em.createQuery("SELECT r FROM Ride r WHERE r.origin = :from AND r.destination = :to AND r.date = :date", Ride.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .setParameter("date", date)
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
}