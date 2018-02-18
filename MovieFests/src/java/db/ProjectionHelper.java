/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.ProjectionWithMovie;
import entities.Festival;
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
    
}
