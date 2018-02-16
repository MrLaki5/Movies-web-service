/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.ReservationWithRating;
import entities.Feedback;
import entities.Movie;
import entities.Projection;
import entities.Reservation;
import entities.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author milanlazarevic
 */
public class ReservationHelper  implements Serializable{
    
    public Feedback getFeedBack(Reservation res){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        Feedback feedback=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from Feedback as feed where feed.idRes="+res.getIdRes());
            feedback=(Feedback) q.uniqueResult();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return feedback;
    }
    
    public List<ReservationWithRating> getUserReservations(String username){
        
        List<ReservationWithRating> retList=new ArrayList<ReservationWithRating>();
        try{
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from Reservation as reg where reg.username='"+username+"'");
            List<Reservation> rezervacije=(List<Reservation>) q.list();
            tx.commit();
            
            for (Iterator<Reservation> iterator = rezervacije.iterator(); iterator.hasNext();) {
                Reservation next = iterator.next();
                Feedback feedback=getFeedBack(next);
                Projection projection=new ProjectionHelper().getProjectionById(next.getIdProjection());
                Movie movie=new MovieHelper().getMovieById(projection.getIdMovie());
                retList.add(new ReservationWithRating(next, feedback, movie, projection));               
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return retList;
    }
    
}
