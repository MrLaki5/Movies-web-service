/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.Festival;
import entities.Hall;
import entities.Location;
import entities.Movie;
import entities.OnLocation;
import entities.Projection;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author milanlazarevic
 */
public class LocationHelper implements Serializable{
    
    public void saveLocation(Location location){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(location);
        session.getTransaction().commit();
    }
    
    public void saveHall(Hall hall){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(hall);
        session.getTransaction().commit();
    }
    
    public void saveOnLocation(OnLocation onLocation){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(onLocation);
        session.getTransaction().commit();
    }
    
    public Location getLocationFromProjection(Projection projection){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        Location location=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("select lok from Location lok, Hall hall, Projection projection"
                    + " where lok.idLok=hall.idLok AND hall.idHall=projection.idHall AND projection.idProjection="+projection.getIdProjection());
            location=(Location) q.uniqueResult();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return location;
    }
    
    public List<Location> getLocationFromFest(Festival fest){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        List<Location> locations=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("select lok from Location lok, Festival fest, OnLocation olok"
                    + " where lok.idLok=olok.id.idLok AND olok.id.idFest=fest.idFest AND fest.idFest="+fest.getIdFest());
            locations=(List<Location>) q.list();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return locations;
    }
}
