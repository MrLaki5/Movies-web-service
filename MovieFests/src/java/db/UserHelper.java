/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.User;
import java.io.Serializable;
import java.util.List;
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
    
    public void changePass(User user){
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
    }
    
    public List<User> getAllWithNoType(){
        List<User> users=null;
        
        Session session=null;
        session=HibernateUtil.getSessionFactory().getCurrentSession();
        try{
            org.hibernate.Transaction tx= session.beginTransaction();
            Query q=session.createQuery("from User as u where u.type='NoType'");
            users=(List<User>) q.list();
            tx.commit();
        }catch(Exception e){
            e.printStackTrace();
        }      
        return users;
    }
    
    public void changeUserType(String username, String newType){
        try{            
            Session session=null;
            session=HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();          
           
            Query q=session.createQuery("from User as user where user.username='"+username+"'");
            User user=(User) q.uniqueResult();
            user.setType(newType);
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
