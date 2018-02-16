/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.FestivalWithProjections;
import beans.ReservationWithRating;
import db.FestivalHelper;
import db.ReservationHelper;
import entities.Festival;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author milanlazarevic
 */
@ManagedBean
@SessionScoped
public class UserController {
    
    FestivalHelper festivalHelper=new FestivalHelper();
    
    private Date dateFrom=new Date();
    private Date dateTo=new Date();
    private String festivalName="";
    private String movieName="";
    private boolean isTopFive=true;
    
    //CONSTRUCTOR===============
    
    public UserController(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 20);
        dateTo=calendar.getTime();
    }
    
    //GETTERS AMD SETTERS

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public boolean isIsTopFive() {
        return isTopFive;
    }

    public void setIsTopFive(boolean isTopFive) {
    }
    
    public void topFiveAct(){
        this.isTopFive=!this.isTopFive;
    }
    
    public Date getCurrentDate(){
        return new Date();
    }
    
    public boolean checkReservationVal(ReservationWithRating elem){
        if(elem.getFeedback()==null && elem.getProjection().getDate().before(new Date())){
            return true;
        }
        return false;
    }
    
    public boolean checkBoughtAndDone(ReservationWithRating elem){
        if(elem.getReservation().getType().equals("Bought") && elem.getProjection().getDate().before(new Date())){
            return true;
        }
        return false;
    }
    
    public List<beans.FestivalWithProjections> getFestivals(){
        List<FestivalWithProjections> tempList =null;
        ArrayList<FestivalWithProjections> sendList=new ArrayList<>();
        
        if(isTopFive==true){
            List<Festival> lista=festivalHelper.getMostRecent();
            while(lista.size()>5){
                lista.remove(lista.size()-1);
            }
            tempList=new ArrayList<>();
            for (Iterator<Festival> iterator = lista.iterator(); iterator.hasNext();) {
                Festival next = iterator.next();
                tempList.add(new FestivalWithProjections(next, null));
            }
            return tempList;
        } 
        
        if(movieName.equals("")){
            List<Festival> lista1=festivalHelper.getCurrentFestivals(dateFrom, dateTo);
            tempList=new ArrayList<>();
            for (Iterator<Festival> iterator = lista1.iterator(); iterator.hasNext();) {
                Festival next = iterator.next();
                tempList.add(new FestivalWithProjections(next, null));
            }
        }
        else{
            tempList=festivalHelper.getFestivalsAndProjections(movieName, dateFrom, dateTo);
        }
        
          
        if(festivalName.equals("")){
            return tempList;
        }
        else{
            for(int i=0; i<tempList.size();i++){
                if(tempList.get(i).getFestival().getName().equals(festivalName)){
                    sendList.add(tempList.get(i));
                }
            }
            return sendList;
        }
    }
    
    public List<ReservationWithRating> getReservations(){
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginController firstBean = (LoginController) elContext.getELResolver().getValue(elContext, null, "loginController");
        List<ReservationWithRating> lista=new ReservationHelper().getUserReservations(firstBean.getUesrname());
        return lista;
    }
}
