/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.FestivalHelper;
import entities.Festival;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
    
    public List<Festival> getFestivals(){
        List<Festival> tempList =festivalHelper.getCurrentFestivals(dateFrom, dateTo);
        ArrayList<Festival> sendList=new ArrayList<>();
        if(festivalName.equals("")){
            return tempList;
        }
        else{
            for(int i=0; i<tempList.size();i++){
                if(tempList.get(i).equals(festivalName)){
                    sendList.add(tempList.get(i));
                }
            }
            return sendList;
        }
    }
}
