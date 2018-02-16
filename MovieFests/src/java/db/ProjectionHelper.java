/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.Projection;
import entities.User;
import java.io.Serializable;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author milanlazarevic
 */
public class ProjectionHelper implements Serializable{
    
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
}
