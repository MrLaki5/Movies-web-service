/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.ReservationWithRating;
import beans.ReservationWithUser;
import entities.Feedback;
import entities.Movie;
import entities.Projection;
import entities.Reservation;
import entities.User;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    
    public void RemoveReservation(Integer idRes){
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Reservation as res where res.idRes="+idRes);
            Reservation rezervacija=(Reservation) q.uniqueResult();
            session.delete(rezervacija);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void RateMovie(Feedback feed){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(feed);
        session.getTransaction().commit();
    }
    
    public void UpdateReservationType(Integer idRes, String newType){
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Reservation as res where res.idRes="+idRes);
            Reservation rezervacija=(Reservation) q.uniqueResult();
            rezervacija.setType(newType);
            session.saveOrUpdate(rezervacija);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List<ReservationWithUser> getAllCurrentReservations(){
        List<ReservationWithUser> tempList=new ArrayList<>();
        
        Date currDate=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String dateStr1=sdf.format(currDate);
        
        try{
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            org.hibernate.Transaction tx= session.beginTransaction();
            
            Query q=session.createQuery("select reg from Reservation reg, Projection proj where reg.idProjection=proj.idProjection AND proj.date>='"+dateStr1+"' AND "
                    + " reg.type='Reserved'");
            List<Reservation> rezervacije=(List<Reservation>) q.list();
            tx.commit();
            
            for (Iterator<Reservation> iterator = rezervacije.iterator(); iterator.hasNext();) {
                Reservation next = iterator.next();
                
                User user=new UserHelper().getUserById(next.getUsername());
                
                tempList.add(new ReservationWithUser(next, user));               
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return tempList;
    }
    
}
