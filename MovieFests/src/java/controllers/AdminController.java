/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.FestivalHelper;
import db.UserHelper;
import entities.Festival;
import entities.User;
import java.util.ArrayList;
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

    public class LocElem{
        private String location;
        private String building;

        public LocElem(String location, String building) {
            this.location = location;
            this.building = building;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }
        
        
    }
    
    private Map<String, String> nameMap=new HashMap<>();;
    private int tempFlag=0;
    
    private String FestivalName="";
    private String LocationName="";
    private String BuildingName="";
    private List<LocElem> locations=null;
    
    //REDIRECT
    
    public String goNewFestival(){
        FestivalName="";
        LocationName="";
        BuildingName="";
        locations=new ArrayList<>();
        return "newFestival?faces-redirect=true";
    }
    
    //LOGICS
    
    public void updateUser(User user){
        new UserHelper().changeUserType(user.getUsername(), nameMap.get(user.getUsername()));
        tempFlag=0;
    }
    
    public void addLocation(){
        if(LocationName.equals("") || BuildingName.equals("")){
            return;
        }
        for (LocElem location : locations) {
            if(location.building.equals(BuildingName) && location.location.equals(LocationName)){
                BuildingName="";
                return;
            }
        }
        locations.add(new LocElem(LocationName, BuildingName));
        BuildingName="";
    }
    
    public void removeLocation(LocElem temp){
        locations.remove(temp);
    }
    
    //GETTERS AND SETTERS

    public String getFestivalName() {
        return FestivalName;
    }

    public void setFestivalName(String FestivalName) {
        this.FestivalName = FestivalName;
    }

    public List<LocElem> getLocations() {
        return locations;
    }

    public void setLocations(List<LocElem> locations) {
        this.locations = locations;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String LocationName) {
        this.LocationName = LocationName;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String BuildingName) {
        this.BuildingName = BuildingName;
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
    
    public List<Festival> getAllfestivals(){
        List<Festival> tempList=new FestivalHelper().getAllFestivals();
        List<Festival> sendList=new ArrayList<Festival>();
        if(FestivalName.equals("")){
            return tempList;
        }
        else{
            for(int i=0; i<tempList.size();i++){
                if(tempList.get(i).getName().contains(FestivalName)){
                    sendList.add(tempList.get(i));
                }
            }
            return sendList;
        }             
    }
    
    
}
