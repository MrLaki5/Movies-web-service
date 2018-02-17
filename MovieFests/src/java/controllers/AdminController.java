/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.UserHelper;
import entities.User;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author milanlazarevic
 */

@ManagedBean
@SessionScoped
public class AdminController {
    
    String newType="";
    Map<String, String> nameMap=new HashMap<>();;
    int tempFlag=0;
    
    //LOGICS
    
    public void updateUser(User user){
        new UserHelper().changeUserType(user.getUsername(), nameMap.get(user.getUsername()));
        tempFlag=0;
    }
    
    //GETTERS AND SETTERS

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }
    
    public List<User> getUnconfirmedUsers(){
        List<User> users=new UserHelper().getAllWithNoType();
        if(tempFlag==0){
            for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
                User next = iterator.next();
                nameMap.put(next.getUsername(), next.getType());
            }
        }
        return users;
    }
    
    public String getUserType(){
        return "NoType";
    }
    
    public void setUserType(String typeS){
        String []niz=typeS.split(" ");
        nameMap.replace(niz[1], niz[0]);
        tempFlag=1;
    }   
}
