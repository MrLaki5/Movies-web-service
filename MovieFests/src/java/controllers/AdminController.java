/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.MovieEnc;
import db.FestivalHelper;
import db.LocationHelper;
import db.MovieHelper;
import db.ProjectionHelper;
import db.UserHelper;
import entities.Festival;
import entities.Hall;
import entities.Location;
import entities.Movie;
import entities.OnFest;
import entities.OnFestId;
import entities.OnLocation;
import entities.OnLocationId;
import entities.Projection;
import entities.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        private int idLok;
        private int idHall;

        public LocElem(String location, String building) {
            this.location = location;
            this.building = building;
            this.idLok=-1;
            this.idHall=-1;
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
        
        @Override
        public String toString(){
            return location+"_"+building;
        }

        public int getIdLok() {
            return idLok;
        }

        public void setIdLok(int idLok) {
            this.idLok = idLok;
        }  

        public int getIdHall() {
            return idHall;
        }

        public void setIdHall(int idHall) {
            this.idHall = idHall;
        }
    }
    
    public class ProjElem{
        private MovieEnc movie;
        private LocElem location;
        private int price;
        private Date time;

        public ProjElem(MovieEnc movie, LocElem location, int price, Date time) {
            this.movie = movie;
            this.location = location;
            this.price = price;
            this.time = time;
        }

        public MovieEnc getMovie() {
            return movie;
        }

        public void setMovie(MovieEnc movie) {
            this.movie = movie;
        }

        public LocElem getLocation() {
            return location;
        }

        public void setLocation(LocElem location) {
            this.location = location;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }
        
    }
    
    private Map<String, String> nameMap=new HashMap<>();;
    private int tempFlag=0;
    
    private String FestivalName="";
    private String LocationName="";
    private String BuildingName="";
    private String FestInfo="";
    private Date StartDate=null;
    private Date EndDate=null;
    private String FirstStepError="";
    private List<LocElem> locations=null;
    private int TicketNum;
    
    private String MovieForProjection;
    private String tempLock;
    private int Price;
    private Date timeDate;
    private List<ProjElem> projections;
    private String SecondStepError="";
    
    //REDIRECT
    
    public String goNewFestival(){
        FestivalName="";
        LocationName="";
        BuildingName="";
        FestInfo="";
        FirstStepError="";
        Price=0;
        TicketNum=0;
        StartDate=null;
        EndDate=null;
        MovieForProjection="";
        tempLock="";
        timeDate=null;
        SecondStepError="";
        locations=new ArrayList<>();
        projections=new ArrayList<>();
        return "newFestival?faces-redirect=true";
    }
    
    public String goNewFestivalBack(){
        SecondStepError="";
        FirstStepError="";
        return "newFestival?faces-redirect=true";
    }
    
    public String goNewFestival2Back(){
        SecondStepError="";
        FirstStepError="";
        return "newFestival2?faces-redirect=true";
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
    
    public void addProjection(){
        LocElem tLock=null;
        for (LocElem location : locations) {
            if(location.toString().equals(tempLock)){
                tLock=location;
                break;
            }
        }
        if(MovieForProjection.isEmpty() || tLock==null){
            SecondStepError="All fields must be set";
            return;
        }
        int idMovie=Integer.parseInt(MovieForProjection);
        Movie movie=new MovieHelper().getMovieById(idMovie);
        if(movie==null || Price==0){
            SecondStepError="All fields must be set";
            return;
        }
        if(timeDate.before(StartDate) || timeDate.after(EndDate)){
            SecondStepError="Time of projection is not during time of festival";
            return;
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(timeDate);
        calendar.add(Calendar.MINUTE, movie.getLength());
        Date projectionEnd = calendar.getTime();
        for (ProjElem projection : projections) {         
            if(tempLock.equals(projection.getLocation().toString())){           
                calendar.setTime(projection.getTime());
                calendar.add(Calendar.MINUTE, projection.getMovie().getMovie().getLength());
                Date projectionEnd2=calendar.getTime();
                
                if(projection.getTime().after(timeDate) && projection.getTime().before(projectionEnd)){
                    SecondStepError="Times crossed with another projection";
                    return;
                }
                if(projectionEnd2.after(timeDate) && projectionEnd2.before(projectionEnd)){
                    SecondStepError="Times crossed with another projection";
                    return;
                }
            }
        }
        projections.add(new ProjElem(new MovieEnc(movie), tLock, Price, timeDate));
        MovieForProjection="";
        tempLock="";
        Price=0;
        timeDate=null;
    }
    
    public void removeLocation(LocElem temp){
        locations.remove(temp);
    }
    
    public void removeProjection(ProjElem temp){
        projections.remove(temp);
    }
    
    public String firstStepFestAdd(){
        if(FestInfo.isEmpty() || FestivalName.isEmpty() || locations.isEmpty() || StartDate==null || EndDate==null || TicketNum==0){
            FirstStepError="All fields must be set";
            return "";
        }
        if(StartDate.after(EndDate)){
            FirstStepError="Start date set after the end";
            return "";
        }
        return "newFestival2?faces-redirect=true";
    }
    
    public String secondStepAdd(){
        if(projections.isEmpty()){
            SecondStepError="There must be projections";
            return "";
        }
        SecondStepError="";
        return "newFestival3?faces-redirect=true";
    }
    
    public String thirdStepFestAdd(){
        Festival newFest=new Festival(StartDate, EndDate, FestivalName);
        new FestivalHelper().saveFestival(newFest);
        LocationHelper lp=new LocationHelper();
        ProjectionHelper pp=new ProjectionHelper();
        for (LocElem location : locations) {
            Location loc=new Location(location.getBuilding(), location.getLocation());
            lp.saveLocation(loc);
            Hall hall=new Hall(loc.getIdLok());
            lp.saveHall(hall);
            location.setIdLok(hall.getIdLok());
            location.setIdHall(hall.getIdHall());
            OnLocation onLocation=new OnLocation(new OnLocationId(newFest.getIdFest(), loc.getIdLok()));
            lp.saveOnLocation(onLocation);
        }
        for (ProjElem projection : projections) {
            Projection pro=new Projection(projection.getMovie().getMovie().getIdMovie(), projection.getLocation().getIdHall(), 0, projection.getTime(), "on");
            pro.setVersion(0);
            pp.saveProjection(pro);
            OnFest onFest=new OnFest(new OnFestId(newFest.getIdFest(), pro.getIdProjection()));
            pp.saveOnFest(onFest);
        }
        FestivalName="";
        LocationName="";
        BuildingName="";
        FestInfo="";
        FirstStepError="";
        Price=0;
        TicketNum=0;
        StartDate=null;
        EndDate=null;
        MovieForProjection="";
        tempLock="";
        timeDate=null;
        SecondStepError="";
        locations=new ArrayList<>();
        projections=new ArrayList<>();
        return "festivalBrowsing?faces-redirect=true";
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

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }

    public String getFestInfo() {
        return FestInfo;
    }

    public void setFestInfo(String FestInfo) {
        this.FestInfo = FestInfo;
    }

    public String getFirstStepError() {
        return FirstStepError;
    }

    public void setFirstStepError(String FirstStepError) {
        this.FirstStepError = FirstStepError;
    }

    public String getMovieForProjection() {
        return MovieForProjection;
    }

    public void setMovieForProjection(String MovieForProjection) {
        this.MovieForProjection= MovieForProjection;
    }

    public String getTempLock() {
        return tempLock;
    }

    public void setTempLock(String tempLock) {
        this.tempLock = tempLock;
    }

    public int getTicketNum() {
        return TicketNum;
    }

    public void setTicketNum(int TicketNum) {
        this.TicketNum = TicketNum;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public Date getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(Date timeDate) {
        this.timeDate = timeDate;
    }

    public List<ProjElem> getProjections() {
        return projections;
    }

    public void setProjections(List<ProjElem> projections) {
        this.projections = projections;
    }

    public String getSecondStepError() {
        return SecondStepError;
    }

    public void setSecondStepError(String SecondStepError) {
        this.SecondStepError = SecondStepError;
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
    
    public List<MovieEnc> getAllMovies(){
        List<Movie> tempList=new MovieHelper().getAllMovies();
        List<MovieEnc> retList=new ArrayList<>();
        for (Movie movie : tempList) {
            retList.add(new MovieEnc(movie));
        }
        return retList;
    }
    
    public String formatDate(Date date){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }
}
