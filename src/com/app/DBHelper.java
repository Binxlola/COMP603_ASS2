package com.app;

import com.app.entities.Flight;
import com.app.entities.plane.Plane;
import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Entity;
import java.util.Objects;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public final class DBHelper {

    private final Configuration config;
    private final SessionFactory sessionFactory;
    private static DBHelper dbHelper;
    
    private DBHelper() {
        this.config = new Configuration();
        this.config.configure();
        
        this.sessionFactory = this.config.buildSessionFactory();
    
    }
    
    public static DBHelper getInstance() {
        if(Objects.isNull(dbHelper)) {
            dbHelper = new DBHelper();
        }
        
        return dbHelper;
    }
    
    /**
     * Basic utility method to allow an easy save or deletion of data through hibernate by getting the current session,
     * and committing a change through a transaction.
     * Will only attempt the commit if the object passed in is classed as an Entity
     *
     * @param o      The Object of data to be saved
     * @param isSave A flag representing if change is a save or a deletion
     */
    public void saveOrRemove(Object o, boolean isSave) {
        Transaction transaction = null;
        Entity entity = o.getClass().getAnnotation(Entity.class);

        if (entity != null) {
            try (Session session = this.sessionFactory.openSession()) {
                // start a transaction
                transaction = session.beginTransaction();

                if (isSave) {
                    session.saveOrUpdate(o);
                } else {
                    session.remove(o);
                }
                // commit transaction
                transaction.commit();
            } catch (HibernateException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    public void saveOrRemove(boolean isSave, Object... entities) {
        for (Object entity : entities) {
            saveOrRemove(entity, isSave);
        }
    }
    
    /**
     * Return all flights saved to the database
     * @return A list containing all saved Flight objects
     */
    public List<Flight> findAllFlights() {
        List<Flight> results = Collections.emptyList();
        
        try (Session session = this.sessionFactory.openSession()) {
            Query<Flight> query = session.createNamedQuery("Flight_findAll", Flight.class);
            results = query.getResultList();
        } catch(HibernateException e) {
            System.err.println(e);
        }
        
        return results;
    }
    
    /**
     * Return all planes saved to the database
     * @return A list containing all saved Plane objects
     */
    public List<Plane> findAllPlanes() {
        List<Plane> results = Collections.emptyList();
        
        try (Session session = this.sessionFactory.openSession()) {
            Query<Plane> query = session.createNamedQuery("Plane_FindAll", Plane.class);
            results = query.getResultList();
        } catch(HibernateException e) {
            System.err.println(e);
        }
        
        return results;
    }
    
    /**
     * Query the database for any planes with matching class name and total row values
     * @param planeClass The plane class being searched for (gives PLANE name)
     * @param totalRows The total number of rows for the configuration
     * @return A Boolean stating if the query contained results or not
     */
    public boolean planeConfigExists(Class<? extends Plane> planeClass, int totalRows) {
        List<Plane> results = Collections.emptyList();
         
        try (Session session = this.sessionFactory.openSession()) {
            Query<Plane> query = session.createNamedQuery("Plane_FindByNameAndRows", Plane.class);
            query.setParameter("type", planeClass);
            query.setParameter("rows", totalRows);
            results = query.getResultList();
        } catch(HibernateException e) {
            System.err.println(e);
        }
        
        return !results.isEmpty();
    }
    
    public void shutdown() {
        this.sessionFactory.close();
    }
}
