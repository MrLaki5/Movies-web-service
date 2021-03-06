/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.FestivalWithProjections;
import beans.ProjectionWithFestWithLocation;
import beans.ProjectionWithMovie;
import beans.ReservationWithRating;
import beans.UltraFest;
import beans.UltraMovie;
import db.FestivalHelper;
import db.MovieHelper;
import db.ReservationHelper;
import entities.Actor;
import entities.Feedback;
import entities.Festival;
import entities.Galery;
import entities.Location;
import entities.Projection;
import entities.Reservation;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import org.json.JSONObject;

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
    
    private int movieRate;
    private String movieComent;
    private ReservationWithRating currElem;
    private String rateErr;
    
    private UltraFest UFest;
    private UltraMovie UMovie;
    private MapModel DestModel;
    private double centerLat;
    private double centerLng;
    private List<Galery> imagesMovie=null;
    private List<Actor> actorsMovie=null;
    private int numberExpired=0;
    private double movieRatings=0;
    private List<Feedback> feedbacks=null;
    
    private int reservationsLeft=0;
    private ProjectionWithFestWithLocation currProjection=null;
    private int reservTickets=0;
    private String reservError="";
    private String reservColor="";
    
    private List<ReservationWithRating> resForMessage=null;
    
    
    private String []colorsMarker={"blue", "red", "green", "yellow", "orange", "pink", "purple"};
    
    //CONSTRUCTOR===============
    
    public UserController(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 20);
        dateTo=calendar.getTime();
    }
    
    //REDIRECT METHODS==========
    
    public String goUserMessages(){
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginController firstBean = (LoginController) elContext.getELResolver().getValue(elContext, null, "loginController");
        firstBean.setCurrPage(3);
        resForMessage=new ReservationHelper().getAllChangedReservations(getUsername());
        return  "userMessages?faces-redirect=true";
    }
    
    public String goRateMovie(ReservationWithRating elem){
        currElem=elem;
        rateErr="";
        movieRate=0;
        movieComent="";
        return "rateMovie?faces-redirect=true";
    }
    
    public String goFestDetails(Festival festival){
        UFest=festivalHelper.getUltraFest(festival);
        loadDestModel();
        return "festivalDetails?faces-redirect=true";
    }
    
    public String goFestDetailsFromMovie(){
        return "festivalDetails?faces-redirect=true";
    }
    
    
    public String goMovieDetails(ProjectionWithMovie projection){
        MovieHelper mp=new MovieHelper();
        movieRatings=mp.getRateForMovie(projection.getMovie().getIdMovie());
        UMovie=mp.getUltraMovie(projection.getMovie().getIdMovie());
        actorsMovie=mp.getActorsForMovie(projection.getMovie().getIdMovie());
        feedbacks=mp.getRateCommentsForMovie(projection.getProjection().getIdMovie());
        imagesMovie=mp.getImagesForMovie(projection.getMovie().getIdMovie());
        numberExpired=new ReservationHelper().getNumberOfExpiredReservations(getUsername());
        return "movieDetails?faces-redirect=true";
    }
    
    public String goMovieDetailsFromReservation(){
        return "movieDetails?faces-redirect=true";
    }
    
    public String goReservationProjection(ProjectionWithFestWithLocation projection){
        reservError="";
        reservColor="";
        currProjection=projection;
        reservTickets=0;
        reservationsLeft=new ReservationHelper().RemainingReservations(getUsername(), projection.getFestival());
        return "movieReservation?faces-redirect=true";
    }
    
    //LOGICS==================
    
    public boolean checkForExprBan(){
        if(numberExpired>=3){
            return true;
        }
        return false;
    }
    
    public boolean checkReservationVal(ReservationWithRating elem){
        if(checkIfCanceledProjection(elem.getProjection())){
            return false;
        }
        if(elem.getFeedback()==null && elem.getProjection().getDate().before(new Date()) && elem.getReservation().getType().equals("Bought")){
            return true;
        }
        return false;
    }
    
    public boolean checkIfCanceledProjection(Projection proj){
        if(proj.getStatus().equals("Canceled")){
            return true;
        }
        return false;
    }
    
    public boolean checkBoughtAndDone(ReservationWithRating elem){
        if(checkIfCanceledProjection(elem.getProjection())){
            return false;
        }
        if(elem.getReservation().getType().equals("Bought") && elem.getProjection().getDate().before(new Date())){
            return true;
        }
        return false;
    }
    
    public boolean check48TimeLimit(Date date){
        Date currDate=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 48);
        Date tempDate=calendar.getTime();
        if(tempDate.before(currDate)){
            return true;
        }
        return false;
    }
    
    public boolean checkCanBeCanceld(ReservationWithRating elem){
        if(checkIfCanceledProjection(elem.getProjection())){
            return false;
        }
        if(check48TimeLimit(elem.getReservation().getDate())){
            return false;
        }
        if(elem.getReservation().getType().equals("Reserved")){
            return true;
        }
        return false;
    }
    
    public boolean checkIfExpired(ReservationWithRating elem){
        if(checkIfCanceledProjection(elem.getProjection())){
            return false;
        }
        if(elem.getReservation().getType().equals("Reserved")){
            if(check48TimeLimit(elem.getReservation().getDate())){
                return true;
            }
            
        }
        return false;
    }
    
    public boolean checkIFBought(ReservationWithRating elem){
        if(checkIfCanceledProjection(elem.getProjection())){
            return false;
        }
        if(elem.getReservation().getType().equals("Bought")){
            return true;
        }
        return false;
    }
    
    public void cancelRegistration(ReservationWithRating elem){
        if(checkCanBeCanceld(elem)){
            String username=getUsername();
            if(username.equals(elem.getReservation().getUsername())){
                new ReservationHelper().RemoveReservation(elem.getReservation().getIdRes());
            }
        }
    }
    
    //feedback=null
    public String messageForUser(ReservationWithRating elem){
        if(elem.getProjection().getStatus().equals("Canceled")){
            if(elem.getReservation().getType().equals("Bought")){
                return "Projection has been canceled, go to cashier to get your money";
            }
            else{
                return "Projection has been canceled";
            }
        }
        else{
            return "Some changes have been made, projection is still up";
        }
    }
    
    public String submitRate(){
        if(movieRate==0 || movieComent.equals("")){
            rateErr="All fields must be set";
            return "";
        }
        Feedback feed=new Feedback();
        feed.setComment(movieComent);
        feed.setRate(movieRate);
        feed.setIdMovie(currElem.getMovie().getIdMovie());
        feed.setIdRes(currElem.getReservation().getIdRes());
        new ReservationHelper().RateMovie(feed);
        return "registrationUser?faces-redirect=true";
    }
    
    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    
    public String submitReservation(){
        if(reservTickets==0){
            reservError="All fields must be set";
            reservColor="text-danger";
            return "";
        }
        if(reservTickets>reservationsLeft){
            reservError="To much tickets set";
            reservColor="text-danger";
            return "";
        }
        String codeStr="";
        boolean tempFlag=true;
        ReservationHelper rp=new ReservationHelper();
        while(tempFlag){
            codeStr=getSaltString();
            tempFlag=rp.chekcIfCodeExsists(codeStr);
        }
        String username=getUsername();
        Reservation reservation=new Reservation(codeStr, new Date(), "Reserved", reservTickets, username, currProjection.getProjection().getIdProjection(), currProjection.getProjection().getStatus(), currProjection.getProjection().getVrCount());
        rp.seveReservation(reservation);
        
        reservError="Reservation successful, your code: "+codeStr;
        reservColor="text-success";
        return "";
    }
    
    public boolean checkIfReservationNotSuccess(){
        if(reservColor.equals("text-success")){
            return false;
        }
        else{
            return true;
        }
    }
    
    public void loadDestModel(){
        DestModel=new DefaultMapModel();
        centerLat=0;
        centerLng=0;
        int locCnt=0;
        for (Location festLocation : UFest.getFestLocations()) {
            
            String address=festLocation.getAdress()+" "+festLocation.getBuilding();
            
            try {
                String request="http://maps.googleapis.com/maps/api/geocode/json?address="+URLEncoder.encode(address, "UTF-8")+"&sensor=false";
                //java.net.URL link=new java.net.URL(request);
                //java.net.URLConnection conn=link.openConnection();
                //java.io.BufferedReader in= new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
                
                //new try
                java.net.URL url = new java.net.URL(request);
                // read from the URL
                java.util.Scanner scan = new java.util.Scanner(url.openStream());
                String str = new String();
                while (scan.hasNext())
                    str += scan.nextLine();
                scan.close();
                
                
                //String inputLine;
                //StringBuilder output=new StringBuilder();
                //while((inputLine=in.readLine())!=null){
                    //output.append(inputLine);
                //}
                
                //in.close();
                
                //JSONObject obj = new JSONObject(output.toString());
                JSONObject obj = new JSONObject(str);
                if (! obj.getString("status").equals("OK")){
                    continue;
                }
 
                // get the first result
                JSONObject res = obj.getJSONArray("results").getJSONObject(0);
                //System.out.println(res.getString("formatted_address"));
                JSONObject loc =
                res.getJSONObject("geometry").getJSONObject("location");
                //System.out.println("lat: " + loc.getDouble("lat") +
                       // ", lng: " + loc.getDouble("lng"));
                double latNew=loc.getDouble("lat");
                double lngNew=loc.getDouble("lng");
                centerLat+=latNew;
                centerLng+=lngNew;
                locCnt++;
                Marker tempMarker=new Marker(new LatLng(latNew, lngNew), res.getString("formatted_address"));
                tempMarker.setIcon("http://maps.google.com/mapfiles/ms/icons/"+colorsMarker[locCnt%colorsMarker.length]+"-dot.png");
                DestModel.addOverlay(tempMarker);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }
        if(locCnt!=0){
            centerLat=centerLat/locCnt;
            centerLng=centerLng/locCnt;
        }
    }
    
    public boolean checkIfEmpty(String str){
        return "".equals(str);
    }
    
    public void hideMessage(ReservationWithRating elem){
        new ReservationHelper().UpdateReservationVersion(elem.getReservation().getIdRes(), elem.getProjection().getVrCount());
        resForMessage=new ReservationHelper().getAllChangedReservations(getUsername());       
    }
    
    //GETTERS AMD SETTERS===========

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public double getMovieRatings() {
        return movieRatings;
    }

    public void setMovieRatings(double movieRatings) {
        this.movieRatings = movieRatings;
    }
    
    public List<ReservationWithRating> getResForMessage() {       
        return resForMessage;
    }

    public void setResForMessage(List<ReservationWithRating> resForMessage) {
        this.resForMessage = resForMessage;
    }

    public String getReservError() {
        return reservError;
    }

    public void setReservError(String reservError) {
        this.reservError = reservError;
    }

    public String getReservColor() {
        return reservColor;
    }

    public void setReservColor(String reservColor) {
        this.reservColor = reservColor;
    }

    public int getReservTickets() {
        return reservTickets;
    }

    public void setReservTickets(int reservTickets) {
        this.reservTickets = reservTickets;
    }

    public int getReservationsLeft() {
        return reservationsLeft;
    }

    public void setReservationsLeft(int reservationsLeft) {
        this.reservationsLeft = reservationsLeft;
    }

    public ProjectionWithFestWithLocation getCurrProjection() {
        return currProjection;
    }

    public void setCurrProjection(ProjectionWithFestWithLocation currProjection) {
        this.currProjection = currProjection;
    }

    public List<Galery> getImagesMovie() {
        return imagesMovie;
    }

    public void setImagesMovie(List<Galery> imagesMovie) {
        this.imagesMovie = imagesMovie;
    }

    public List<Actor> getActorsMovie() {
        return actorsMovie;
    }

    public void setActorsMovie(List<Actor> actorsMovie) {
        this.actorsMovie = actorsMovie;
    }

    public UltraMovie getUMovie() {
        return UMovie;
    }

    public void setUMovie(UltraMovie UMovie) {
        this.UMovie = UMovie;
    }
    
    public String getCenterMapLocation(){
        return centerLat+", "+centerLng;
    }

    public UltraFest getUFest() {
        return UFest;
    }

    public void setUFest(UltraFest UFest) {
        this.UFest = UFest;
    }
    
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

    public MapModel getDestModel() {
        return DestModel;
    }

    public void setDestModel(MapModel DestModel) {
        this.DestModel = DestModel;
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
                if(tempList.get(i).getFestival().getName().contains(festivalName)){
                    sendList.add(tempList.get(i));
                }
            }
            return sendList;
        }
    }
    
    

    public int getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(int movieRate) {
        this.movieRate = movieRate;
    }

    public String getMovieComent() {
        return movieComent;
    }

    public void setMovieComent(String movieComent) {
        this.movieComent = movieComent;
    }

    public ReservationWithRating getCurrElem() {
        return currElem;
    }

    public void setCurrElem(ReservationWithRating currElem) {
        this.currElem = currElem;
    }

    public String getRateErr() {
        return rateErr;
    }

    public void setRateErr(String rateErr) {
        this.rateErr = rateErr;
    }
    
    
    
    public List<ReservationWithRating> getReservations(){
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginController firstBean = (LoginController) elContext.getELResolver().getValue(elContext, null, "loginController");
        List<ReservationWithRating> lista=new ReservationHelper().getUserReservations(firstBean.getUesrname());
        return lista;
    }
    
    public String getUsername(){
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginController firstBean = (LoginController) elContext.getELResolver().getValue(elContext, null, "loginController");
        return firstBean.getUesrname();
    }
    
}
