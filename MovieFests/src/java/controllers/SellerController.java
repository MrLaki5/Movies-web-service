/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.ProjectionWithMovie;
import beans.ReservationWithUser;
import db.ProjectionHelper;
import db.ReservationHelper;
import entities.Projection;
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
    
    private int currPrice=0;
    
    private int ticketNum=0;
    private ProjectionWithMovie currProj;
    
    //REDIRECT METHODS
    
    public String goCashSellTickets(ProjectionWithMovie elem){
        if(ticketNum==0){
            return "";
        }
        currProj=elem;
        return "sellCashierTickets?faces-redirect=true";
    }
    
    public String goSellTickets(ReservationWithUser elem){
        currElem=elem;
        Projection projection=new ProjectionHelper().getProjectionFromReservation(elem.getReservation());
        currPrice=projection.getPrice();
        return "sellTickets?faces-redirect=true";
    }
    
    //LOGIC METHODS
    
    public String buyTickets(){
        new ReservationHelper().UpdateReservationType(currElem.getReservation().getIdRes(), "Bought");
        return "registrationSeller?faces-redirect=true";
    }
    
    public String buyTicketsCashier(){
        ticketNum=0;
        return "projectionSeller?faces-redirect=true";
    }
    
    //GETTER AND SETTERS

    public int getCurrPrice() {
        return currPrice;
    }

    public void setCurrPrice(int currPrice) {
        this.currPrice = currPrice;
    }

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

    public int getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    public ProjectionWithMovie getCurrProj() {
        return currProj;
    }

    public void setCurrProj(ProjectionWithMovie currProj) {
        this.currProj = currProj;
    }
    
    public List<ProjectionWithMovie> getCurrProjections(){
        List<ProjectionWithMovie> tempList=new ProjectionHelper().getAllCurrProjectionsWMovie();
        List<ProjectionWithMovie> retList=new ArrayList<>();
        for (ProjectionWithMovie projectionWithMovie : tempList) {
            if(!projectionWithMovie.getProjection().getStatus().equals("Canceled")){
                retList.add(projectionWithMovie);
            }
        }
        return retList;
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
