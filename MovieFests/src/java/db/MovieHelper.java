/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

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
}
