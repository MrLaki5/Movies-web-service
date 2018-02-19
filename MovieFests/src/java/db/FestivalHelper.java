/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.ElemOfProLok;
import beans.FestivalWithProjections;
import beans.ProjectionWithMovie;
import beans.UltraFest;
import entities.Festival;
import entities.Location;
import entities.Movie;
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
public class FestivalHelper implements Serializable{
    
    public void saveFestival(Festival fest){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(fest);
        session.getTransaction().commit();
    }
    
    public List<Festival> getCurrentFestivals(Date dateFrom, Date dateTo){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        List<Festival> festivali=null;
        Date currDate=new Date();
        if(dateFrom==null){
            dateFrom=currDate;
        }
        if(dateTo==null){
            dateTo=currDate;
        }
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String dateStr1=sdf.format(dateFrom);
        String dateStr2=sdf.format(dateTo);
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from Festival as fest where fest.dateFrom>='"+dateStr1+"' AND fest.dateTo<='"+dateStr2+"' AND fest.dateTo>='"+sdf.format(currDate)+"'");
            festivali=(List<Festival>) q.list();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return festivali;
    }
    
    public List<Festival> getAllFestivals(){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        List<Festival> festivali=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from Festival as fest");
            festivali=(List<Festival>) q.list();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return festivali;
    }
    
    public List<Festival> getMostRecent(){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        List<Festival> festivali=null;
        Date currDate=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=sdf.format(currDate);
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from Festival as fest where fest.dateTo>='"+dateStr+"' ORDER BY fest.dateFrom ASC");
            festivali=(List<Festival>) q.list();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return festivali;
    }
    
    public List<Festival> getFestivalOfProjection(int idProjection){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        List<Festival> festival=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("select fest from Festival fest, Projection proj, OnFest ofest where fest.idFest=ofest.id.idFest AND proj.idProjection=ofest.id.idProjection AND proj.idProjection="+idProjection);
            festival=(List<Festival>) q.list();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return festival;
    }
    
    public List<FestivalWithProjections> getFestivalsAndProjections(String movieName, Date dateFrom, Date dateTo){
        Session session=null;
        List<FestivalWithProjections> festivali=null;
        Date currDate=new Date();
        if(dateFrom==null){
            dateFrom=currDate;
        }
        if(dateTo==null){
            dateTo=currDate;
        }
        LocationHelper locationHelper=new LocationHelper();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=sdf.format(currDate);
        String dateStr1=sdf.format(dateFrom);
        String dateStr2=sdf.format(dateTo);
        try{
            
            List<Movie> movies=new MovieHelper().getMoviesByName(movieName);
            
            festivali=new ArrayList<>();
            
            for (Iterator<Movie> iterator = movies.iterator(); iterator.hasNext();) {
                Movie next = iterator.next();
                List<Projection> projections=null;
                session=HibernateUtil.getSessionFactory().getCurrentSession();
                org.hibernate.Transaction tx= session.beginTransaction();
                Query q=session.createQuery("select proj from Projection proj, Festival fest, OnFest ofest where proj.idMovie="+next.getIdMovie()+" AND fest.dateTo>='"+dateStr+"' "
                        + " AND ofest.id.idProjection=proj.idProjection AND ofest.id.idFest=fest.idFest AND fest.dateFrom>='"+dateStr1+"' AND fest.dateTo<='"+dateStr2+"'");
                projections=(List<Projection>) q.list();
                tx.commit();
                
                
                
                List<beans.ElemOfProLok> elementi=new ArrayList<>();
                for (Iterator<Projection> iterator1 = projections.iterator(); iterator1.hasNext();) {
                    Projection next1 = iterator1.next();
                    Location location=locationHelper.getLocationFromProjection(next1);
                    beans.ElemOfProLok elem=new beans.ElemOfProLok(next1, location);
                    elementi.add(elem);
                    session=HibernateUtil.getSessionFactory().getCurrentSession();
                    tx= session.beginTransaction();
                    q=session.createQuery("select fest from Projection proj, Festival fest, OnFest ofest where ofest.id.idProjection=proj.idProjection AND ofest.id.idFest=fest.idFest"
                        + " AND proj.idProjection="+next1.getIdProjection());
                    List<Festival> fest=(List<Festival>) q.list();
                    tx.commit();
                    
                    for (Iterator<Festival> iterator3 = fest.iterator(); iterator3.hasNext();) {
                        Festival next3 = iterator3.next();
                        
                        int k=0;
                        for (Iterator<FestivalWithProjections> iterator2 = festivali.iterator(); iterator2.hasNext();) {
                            FestivalWithProjections next2 = iterator2.next();

                            if(next2.getFestival().getIdFest()==next3.getIdFest()){
                                k=1;
                                next2.getProjections().add(elem);
                            } 
                        }
                        if(k==0){
                            festivali.add(new FestivalWithProjections(next3, elementi));
                        }
                        
                        
                    }
                    
                    
                }
                
                
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return festivali;
    }
    
    public UltraFest getUltraFest(Festival fest){
        Session session=null;
        List<ProjectionWithMovie> projections=null;
        List<Location> locations=null;
        UltraFest ufest=null;
        try{           
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("select proj from Projection proj, Festival fest, OnFest ofest where "
                    + " ofest.id.idProjection=proj.idProjection AND ofest.id.idFest=fest.idFest AND fest.idFest="+fest.getIdFest());
            List<Projection> projectionsTemp=(List<Projection>) q.list();
            tx.commit();
            
            projections=new ProjectionHelper().getUpgradeProjections(projectionsTemp);
            
            locations=new LocationHelper().getLocationFromFest(fest);
            
            ufest=new UltraFest(fest, locations, projections);
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return ufest;
    }
    
    public void updateNameInFestival(int idFest, String name){
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Festival as fest where fest.idFest="+idFest);
            Festival festival=(Festival) q.uniqueResult();
            festival.setName(name);
            session.saveOrUpdate(festival);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateTicketNumINFest(int idFest, int num){                     
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Festival as fest where fest.idFest="+idFest);
            Festival festival=(Festival) q.uniqueResult();
            festival.setTicketNum(num);
            session.saveOrUpdate(festival);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateInfoINFest(int idFest, String info){                     
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from Festival as fest where fest.idFest="+idFest);
            Festival festival=(Festival) q.uniqueResult();
            festival.setInfo(info);
            session.saveOrUpdate(festival);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
