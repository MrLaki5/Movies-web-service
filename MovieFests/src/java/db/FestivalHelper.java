/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.Festival;
import entities.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author milanlazarevic
 */
public class FestivalHelper {
    
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
    
}
