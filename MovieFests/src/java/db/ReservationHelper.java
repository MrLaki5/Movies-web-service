/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.ReservationWithRating;
import beans.ReservationWithUser;
import controllers.LoginController;
import controllers.UserController;
import entities.Feedback;
import entities.Festival;
import entities.Movie;
import entities.Projection;
import entities.Reservation;
import entities.User;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
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
    
    public void UpdateReservationVersion(Integer idRes, int newVersion){
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Reservation as res where res.idRes="+idRes);
            Reservation rezervacija=(Reservation) q.uniqueResult();
            rezervacija.setVrCount(newVersion);
            session.saveOrUpdate(rezervacija);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List<ReservationWithUser> getAllCurrentReservations(){
        List<ReservationWithUser> tempList=new ArrayList<>();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        UserController firstBean = (UserController) elContext.getELResolver().getValue(elContext, null, "userController");
        Date currDate=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr1=sdf.format(currDate);
        
        try{
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            org.hibernate.Transaction tx= session.beginTransaction();
            
            Query q=session.createQuery("select reg from Reservation reg, Projection proj where reg.idProjection=proj.idProjection AND proj.date>='"+dateStr1+"' AND "
                    + " reg.type='Reserved' AND proj.status='on'");
            List<Reservation> rezervacije=(List<Reservation>) q.list();
            tx.commit();
            
            for (Iterator<Reservation> iterator = rezervacije.iterator(); iterator.hasNext();) {
                Reservation next = iterator.next();
                
                
                if(firstBean.check48TimeLimit(next.getDate())){
                    continue;
                }
                
                
                User user=new UserHelper().getUserById(next.getUsername());
                
                tempList.add(new ReservationWithUser(next, user));               
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return tempList;
    }
    
    public int getNumberOfExpiredReservations(String username){
        int number=0;
        
        Date currDate=new Date();;
        
        Calendar calendar=Calendar.getInstance();
        
        try{
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("select reg from Reservation reg, Projection proj where reg.username='"+username+"' AND reg.idProjection=proj.idProjection AND proj.status!='Canceled'");
            List<Reservation> rezervacije=(List<Reservation>) q.list();
            tx.commit();
            for (Reservation reservation : rezervacije) {
                calendar.setTime(reservation.getDate());
                calendar.add(Calendar.DAY_OF_MONTH, 2);
                Date tempDate=calendar.getTime();
                if(tempDate.before(currDate) && reservation.getType().equals("Reserved")){
                    number++;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return number;
    }
    
    public int RemainingReservations(String username, Festival festival){
        int number=festival.getTicketNum();
        
        List<Projection> projections=new ProjectionHelper().getAllProjectionsForFestival(festival.getIdFest());
        
        
        for (Projection projection : projections) {
            try{
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from Reservation as reg where reg.username='"+username+"' AND reg.idProjection="+projection.getIdProjection());
            List<Reservation> rezervacije=(List<Reservation>) q.list();
            tx.commit();
            for (Reservation reservation : rezervacije) {
                number-=reservation.getTicketNum();
            }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return number;
    }
    
    public boolean chekcIfCodeExsists(String code){
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Reservation as res where res.code='"+code+"'");
            Reservation rezervacija=(Reservation) q.uniqueResult();
            session.getTransaction().commit();
            if(rezervacija==null){
                return false;
            }
            else{
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public void seveReservation(Reservation reservation){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(reservation);
        session.getTransaction().commit();
    }
    
    public List<ReservationWithRating> getAllChangedReservations(String username){
        List<ReservationWithRating> retList=new ArrayList<ReservationWithRating>();
        
        Date currDate=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr1=sdf.format(currDate);
        
        try{
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("select reg from Reservation reg, Projection proj where reg.username='"+username+"' "
                    + " AND reg.idProjection=proj.idProjection AND reg.vrCount!=proj.vrCount AND proj.date>='"+dateStr1+"'");
            List<Reservation> rezervacije=(List<Reservation>) q.list();
            tx.commit();
            
            for (Iterator<Reservation> iterator = rezervacije.iterator(); iterator.hasNext();) {
                Reservation next = iterator.next();
                Projection projection=new ProjectionHelper().getProjectionById(next.getIdProjection());
                Movie movie=new MovieHelper().getMovieById(projection.getIdMovie());
                retList.add(new ReservationWithRating(next, null, movie, projection));               
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return retList; 
    }
    
}
