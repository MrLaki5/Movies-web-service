/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import beans.ProjectionWithFestWithLocation;
import beans.UltraMovie;
import entities.Actor;
import entities.Feedback;
import entities.Festival;
import entities.Galery;
import entities.Movie;
import entities.User;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author milanlazarevic
 */
public class MovieHelper implements Serializable{
    
    
    public List<Movie> getMoviesByName(String movieName){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        List<Movie> movies=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from Movie as movie where movie.name='"+movieName+"'");
            movies=(List<Movie>) q.list();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return movies;
    }
    
    public Movie getMovieById(int id){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        Movie movie=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from Movie as movie where movie.idMovie="+id);
            movie=(Movie) q.uniqueResult();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return movie;
    }
    
    public List<Movie> getAllMovies(){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        List<Movie> movies=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from Movie as movie");
            movies=(List<Movie>) q.list();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return movies;
    }
    
    public void saveMovie(Movie movie){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(movie);
        session.getTransaction().commit();
    }
    
    public void saveActor(Actor actor){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(actor);
        session.getTransaction().commit();
    }
    
    public void saveGalery(Galery galery){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(galery);
        session.getTransaction().commit();
    }
    
    public UltraMovie getUltraMovie(int idMovie){
        Movie movie=getMovieById(idMovie);
        List<ProjectionWithFestWithLocation> locations=new ProjectionHelper().getAllCurrProjectionMovieLocationForMovie(idMovie);
        return new UltraMovie(movie, locations);
    }
    
    public List<Actor> getActorsForMovie(int idMovie){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        List<Actor> actors=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("select act from Movie movie, Actor act where act.idMovie=movie.idMovie AND movie.idMovie="+idMovie);
            actors=(List<Actor>) q.list();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return actors;
    }
    
    public List<Galery> getImagesForMovie(int idMovie){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        List<Galery> images=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("select gal from Movie movie, Galery gal where gal.idMovie=movie.idMovie AND movie.idMovie="+idMovie);
            images=(List<Galery>) q.list();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return images;
    }
    
    public double getRateForMovie(int idMovie){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        List<Feedback> feedback=null;
        double number=0;
        double cntNumber=0;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("select feed from Movie movie, Feedback feed where feed.idMovie=movie.idMovie AND movie.idMovie="+idMovie);
            feedback=(List<Feedback>) q.list();
            tx.commit();
            for (Feedback feedback1 : feedback) {
                cntNumber++;
                number+=feedback1.getRate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(cntNumber==0){
            return 1;
        }
        number=number/cntNumber;
        return number;
    }
}
