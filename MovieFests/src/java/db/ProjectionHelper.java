/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.ProjectionWithFestWithLocation;
import beans.ProjectionWithMovie;
import entities.Festival;
import entities.Hall;
import entities.Location;
import entities.Movie;
import entities.OnFest;
import entities.Projection;
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
public class ProjectionHelper implements Serializable{
    
    public void saveProjection(Projection projection){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(projection);
        session.getTransaction().commit();
    }
    
    public void saveOnFest(OnFest onFest){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(onFest);
        session.getTransaction().commit();
    }
    
     public Projection getProjectionById(int id){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        Projection projection=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from Projection as pro where pro.idProjection="+id);
            projection=(Projection) q.uniqueResult();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return projection;
    }
     
    public List<ProjectionWithMovie> getAllCurrProjectionsWMovie(){
        
        List<ProjectionWithMovie> tempList= new ArrayList<>();
        
        Date currDate=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String dateStr1=sdf.format(currDate);
        
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("select proj from Projection proj where "
            + " proj.date>='"+dateStr1+"'");
            List<Projection> projections=(List<Projection>) q.list();
            tx.commit();
            
            for (Iterator<Projection> iterator = projections.iterator(); iterator.hasNext();) {
                Projection next = iterator.next();
                
                Movie movie=new MovieHelper().getMovieById(next.getIdMovie());
                Location location=new LocationHelper().getLocationFromProjection(next);
                
                tempList.add(new ProjectionWithMovie(next, movie, location));
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return tempList;
    }
    
    public List<ProjectionWithMovie> getUpgradeProjections(List<Projection> projections){
        List<ProjectionWithMovie> tempList= new ArrayList<>();
        
        
        try{
            for (Iterator<Projection> iterator = projections.iterator(); iterator.hasNext();) {
                Projection next = iterator.next();
                
                Movie movie=new MovieHelper().getMovieById(next.getIdMovie());
                Location location=new LocationHelper().getLocationFromProjection(next);
                
                tempList.add(new ProjectionWithMovie(next, movie, location));
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return tempList;
    
    }
    
    public void updateMovieInProjection(int idProj, int idMovie){
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Projection as res where res.idProjection="+idProj);
            Projection projection=(Projection) q.uniqueResult();
            projection.setIdMovie(idMovie);
            session.saveOrUpdate(projection);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updatePriceInProjection(int idProj, int price){                        
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Projection as res where res.idProjection="+idProj);
            Projection projection=(Projection) q.uniqueResult();
            projection.setPrice(price);
            session.saveOrUpdate(projection);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateTimeInProjection(int idProj, Date time){
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Projection as res where res.idProjection="+idProj);
            Projection projection=(Projection) q.uniqueResult();
            projection.setDate(time);
            session.saveOrUpdate(projection);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateLocationInProjection(int idProj, Location location){
        
        Hall hall= new LocationHelper().getHallFromLocation(location);
        
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Projection as res where res.idProjection="+idProj);
            Projection projection=(Projection) q.uniqueResult();
            projection.setIdHall(hall.getIdHall());
            session.saveOrUpdate(projection);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateCancelingInProjection(int idProj){
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Projection as res where res.idProjection="+idProj);
            Projection projection=(Projection) q.uniqueResult();
            projection.setStatus("Canceled");
            session.saveOrUpdate(projection);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateVersionInProjection(int idProj){
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Projection as res where res.idProjection="+idProj);
            Projection projection=(Projection) q.uniqueResult();
            projection.setVersion(projection.getVersion()+1);
            session.saveOrUpdate(projection);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List<ProjectionWithFestWithLocation> getAllCurrProjectionMovieLocationForMovie(int idMovie){
        List<ProjectionWithFestWithLocation> retList=new ArrayList<>();
        
        Date currDate=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String dateStr1=sdf.format(currDate);
        
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("select proj from Projection proj where "
            + " proj.date>='"+dateStr1+"' AND proj.idMovie="+idMovie+" AND proj.status='on'");
            List<Projection> projections=(List<Projection>) q.list();
            tx.commit();
            
            FestivalHelper fp=new FestivalHelper();
            
            for (Iterator<Projection> iterator = projections.iterator(); iterator.hasNext();) {
                Projection next = iterator.next();
                
                List<Festival> festival=fp.getFestivalOfProjection(next.getIdProjection());
                
                for (Festival festival1 : festival) {
                    Location location=new LocationHelper().getLocationFromProjection(next);               
                    retList.add(new ProjectionWithFestWithLocation(next, festival1, location));
                }
                
            }          
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return retList;
    }
    
}
