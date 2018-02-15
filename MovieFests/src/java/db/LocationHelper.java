/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.Location;
import entities.Movie;
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
}
