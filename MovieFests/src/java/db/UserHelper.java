/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.User;
import java.io.Serializable;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author milanlazarevic
 */
public class UserHelper implements Serializable{  
    
    public User getUserById(String username){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        User user=null;
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from User as u where u.username='"+username+"'");
            user=(User) q.uniqueResult();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    
    public void addUser(User user){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }
}
