/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.ReservationWithUser;
import db.ReservationHelper;
import entities.Reservation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author milanlazarevic
 */

@ManagedBean
@SessionScoped
public class SellerController implements Serializable{
    
    private String CodeF="";
    private String FName="";
    private String LName="";
    
    private ReservationWithUser currElem;
    
    //REDIRECT METHODS
    
    public String goSellTickets(ReservationWithUser elem){
        currElem=elem;
        return "sellTickets?faces-redirect=true";
    }
    
    //LOGIC METHODS
    
    public String buyTickets(){
        currElem.getReservation().setType("Bought");
        new ReservationHelper().UpdateReservation(currElem.getReservation());
        return "registrationSeller?faces-redirect=true";
    }
    
    //GETTER AND SETTERS

    public String getCodeF() {
        return CodeF;
    }

    public void setCodeF(String CodeF) {
        this.CodeF = CodeF;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public ReservationWithUser getCurrElem() {
        return currElem;
    }

    public void setCurrElem(ReservationWithUser currElem) {
        this.currElem = currElem;
    }
    
    
    
    public List<ReservationWithUser> getCurrReservations(){
        List<ReservationWithUser> tempList=null;
        tempList=new ReservationHelper().getAllCurrentReservations();
        List<ReservationWithUser> retList=new ArrayList<>();
        
        Calendar calendar=Calendar.getInstance();
        Date currDate=new Date();
        
        for (Iterator<ReservationWithUser> iterator = tempList.iterator(); iterator.hasNext();) {
            ReservationWithUser next = iterator.next();
            int k=1;
            if(!CodeF.equals("")){
                if(CodeF.equals(next.getReservation().getCode())){
                    k=1;
                }
                else{
                    k=0;
                }
            }
            if(!FName.equals("")){
                if(k==1 && FName.equals(next.getUser().getFirstname())){
                    k=1;
                }
                else{
                    k=0;
                }
            }
            if(!LName.equals("")){
                if(k==1 && LName.equals(next.getUser().getLastname())){
                    k=1;
                }
                else{
                    k=0;
                }
            }
            if(k==1){
                calendar.setTime(next.getReservation().getDate());
                calendar.add(Calendar.DAY_OF_MONTH, 2);
                Date tempD=calendar.getTime();
                if(tempD.before(currDate)){
                    k=0;
                }
            }
            if(k==1){
                retList.add(next);
            }
        }
        return retList;
    }
    
}
