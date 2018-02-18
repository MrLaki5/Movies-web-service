/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.Actor;
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
}
