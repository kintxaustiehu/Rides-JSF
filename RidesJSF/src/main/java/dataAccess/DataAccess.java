package dataAccess;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import domain.Driver;
import domain.Ride;
import utils.JPAUtil;

public class DataAccess {
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
            return em.createQuery("SELECT DISTINCT destination FROM Ride WHERE origin = :from ORDER BY r.destination", String.class)
                     .setParameter("from", from)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Ride> getRides(String from, String to, Date date) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT * FROM Ride WHERE r.origin = :from AND destination = :to AND date = :date", Ride.class)
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

            Query query = em.createQuery("SELECT * FROM Driver WHERE email = :email");
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