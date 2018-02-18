/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.MovieEnc;
import beans.ProjectionWithMovie;
import beans.ReservationWithRating;
import beans.UltraFest;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import db.FestivalHelper;
import db.LocationHelper;
import db.MovieHelper;
import db.ProjectionHelper;
import db.ReservationHelper;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import sun.misc.IOUtils;


/**
 *
 * @author milanlazarevic
 */

@ManagedBean
@SessionScoped
public class AdminController {

    public static final String image_path="/Users/milanlazarevic/Desktop/movies-site-java-faces/MovieFests/web/Images";
    
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
    
    private UltraFest UFest=null;
    private ProjectionWithMovie currEditProj=null;
    private String EditMovieForProjection;
    private String EditTempLock;
    private String EditProjErr="";
    private String EditFestErr="";
    
    private Movie newMovie=null;
    private int rootB=0;
    
    private String MovieNewName="";
    private UploadedFile file=null;
    private UploadedFile ImageFile=null;
    private String ErrorMovie="";
    private int MovieYear=0;
    private List<String> actors=null;
    private List<UploadedFile> images=null;
    private String ActorName="";
    
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
    
    public String goFestEdit(Festival fest){
        EditFestErr="";
        UFest=new FestivalHelper().getUltraFest(fest);
        return "festEdit?faces-redirect=true";
    }
    
    public String goFestEdit(){
        return "festEdit?faces-redirect=true";
    }
    
    public String goProjectionEdit(ProjectionWithMovie proj){
        EditProjErr="";
        currEditProj=proj;
        EditMovieForProjection=""+currEditProj.getMovie().getIdMovie();
        EditTempLock=currEditProj.getLocation().getAdress()+"_"+currEditProj.getLocation().getBuilding();
        return "projectionEdit?faces-redirect=true";
    }
    
    public String goNewMovie(){
        rootB=0;       
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        LoginController firstBean = (LoginController) elContext.getELResolver().getValue(elContext, null, "loginController");
        firstBean.setCurrPage(3);
        actors=new ArrayList<>();
        images=new ArrayList<>();
        newMovie=new Movie();
        newMovie.setDirector("");
        newMovie.setCountry("");
        newMovie.setAbout("");
        newMovie.setImdb("");
        newMovie.setTomato("");
        newMovie.setYoutube("");
        newMovie.setLength(0);
        ActorName="";
        file=null;
        ImageFile=null;
        ErrorMovie="";
        MovieYear=0;
        MovieNewName="";
        return "newMovie?faces-redirect=true";
    }
    
    public String goNewMovieFromFest(){
        rootB=1;
        actors=new ArrayList<>();
        images=new ArrayList<>();
        newMovie=new Movie();
        newMovie=new Movie();
        newMovie.setDirector("");
        newMovie.setCountry("");
        newMovie.setAbout("");
        newMovie.setImdb("");
        newMovie.setTomato("");
        newMovie.setYoutube("");
        newMovie.setLength(0);
        ActorName="";
        file=null;
        ImageFile=null;
        MovieYear=0;
        ErrorMovie="";
        MovieNewName="";
        return "newMovie?faces-redirect=true";
    }
    
    //LOGICS
    
    public void addImageForGalerry(){
        if(ImageFile.getFileName().equals("")){
            return;
        }
        for (int i=0; i<images.size(); i++) {
            if(images.get(i).getFileName().equals(ImageFile.getFileName())){
                return;
            }
        }
        images.add(ImageFile);
        ImageFile=null;
    }
    
    public void removeImageForGallery(String image){
        for (int i=0; i<images.size(); i++) {
            if(images.get(i).getFileName().equals(image)){
                images.remove(i);
                return;
            }
        }
    }
    
    public void addActor(){
        if(ActorName.equals("")){
            return;
        }
        for (int i=0; i<actors.size(); i++) {
            if(actors.get(i).equals(ActorName)){
                return;
            }
        }
        actors.add(ActorName);
        ActorName="";
    }
    
    public void removeActor(String actor){
        for (int i=0; i<actors.size(); i++) {
            if(actors.get(i).equals(actor)){
                actors.remove(i);
                return;
            }
        }
    }
    
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
    
    public String saveEditProj(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(currEditProj.getProjection().getDate());
        calendar.add(Calendar.MINUTE, currEditProj.getMovie().getLength());
        Date projectionEnd = calendar.getTime();
        
        Location locationTr=null;
        for (Location festLocation : UFest.getFestLocations()) {
            String temp=festLocation.getAdress()+"_"+festLocation.getBuilding();
            if(temp.equals(EditTempLock)){
                locationTr=festLocation;
                break;
            }
        }
        
        int idMovie=Integer.parseInt(EditMovieForProjection);
        Movie movie=new MovieHelper().getMovieById(idMovie);
        
        
        if(currEditProj.getProjection().getDate().before(UFest.getFest().getDateFrom()) || currEditProj.getProjection().getDate().after(UFest.getFest().getDateTo())){
            EditProjErr="Time of projection is not during time of festival";
            return "";
        }
        
        for (ProjectionWithMovie projection : UFest.getProjections()) {
            String temp=projection.getLocation().getAdress()+"_"+projection.getLocation().getBuilding();
            if(EditTempLock.equals(temp) && (!Objects.equals(currEditProj.getProjection().getIdProjection(), projection.getProjection().getIdProjection()))){           
                calendar.setTime(projection.getProjection().getDate());
                calendar.add(Calendar.MINUTE, projection.getMovie().getLength());
                Date projectionEnd2=calendar.getTime();
                
                if(projection.getProjection().getDate().after(currEditProj.getProjection().getDate()) && projection.getProjection().getDate().before(projectionEnd)){
                    EditProjErr="Times crossed with another projection";
                    return "";
                }
                if(projectionEnd2.after(currEditProj.getProjection().getDate()) && projectionEnd2.before(projectionEnd)){
                    EditProjErr="Times crossed with another projection";
                    return "";
                }
            }
        }
        if(1==0){
            EditProjErr="Price can not be zero";        //TODO add price check
            return "";
        }
        
        
        ProjectionHelper pp=new ProjectionHelper();
        pp.updateTimeInProjection(currEditProj.getProjection().getIdProjection(), currEditProj.getProjection().getDate());
        currEditProj.setLocation(locationTr);
        pp.updateLocationInProjection(currEditProj.getProjection().getIdProjection(), currEditProj.getLocation());
        currEditProj.setMovie(movie);
        pp.updateMovieInProjection(currEditProj.getProjection().getIdProjection(), currEditProj.getMovie().getIdMovie());
        pp.updateVersionInProjection(currEditProj.getProjection().getIdProjection());
        return goFestEdit();
    }
    
    public String cancelProjection(){
        ProjectionHelper pp=new ProjectionHelper();
        pp.updateCancelingInProjection(currEditProj.getProjection().getIdProjection());
        pp.updateVersionInProjection(currEditProj.getProjection().getIdProjection());
        currEditProj.getProjection().setStatus("Canceled");
        return goFestEdit();
    }
    
    public String saveEditFest(){
        if(UFest.getFest().getName().equals("")){                               //TODO ADD TICKETS AND INFO
            EditFestErr="All fields must be set";
            return "";
        }
        FestivalHelper fh=new FestivalHelper();
        fh.updateNameInFestival(UFest.getFest().getIdFest(), UFest.getFest().getName());
        return "festivalBrowsing?faces-redirect=true";
    }
    
    public String saveMovie(){
        
        if(file==null){
            ErrorMovie="Movie artwork must be set";
            return "";
        }

        if(MovieNewName.equals("") || MovieYear<1900 || newMovie.getDirector().equals("") || newMovie.getCountry().equals("") || newMovie.getAbout().equals("")
                || newMovie.getLength()==0 || actors.size()==0){
            ErrorMovie="All fields must be set";
            return "";
        }

        String imageName="";
        
        if(!file.getFileName().equals("")){
            String filename = "1"; 
            String extension = file.getContentType().split("/")[1];
            try {
                Path temp=Paths.get(AdminController.image_path);
                Path file1 = Files.createTempFile(temp, filename + "-", "." + extension);
                InputStream input = file.getInputstream();
                Files.copy(input, file1, StandardCopyOption.REPLACE_EXISTING);
                String []nizStr=file1.toString().split("/");
                imageName=nizStr[nizStr.length-1];
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        
        newMovie.setName(MovieNewName);
        newMovie.setYear(MovieYear);

        new MovieHelper().saveMovie(newMovie);

        if(rootB==1){
            return goNewFestival2Back();
        }
        else{
            return goNewMovie();
        }
        
    }
    
    //GETTERS AND SETTERS

    public UploadedFile getImageFile() {
        return ImageFile;
    }

    public void setImageFile(UploadedFile ImageFile) {
        this.ImageFile = ImageFile;
    }
    
    public List<UploadedFile> getImages() {
        return images;
    }

    public void setImages(List<UploadedFile> images) {
        this.images = images;
    }

    public String getActorName() {
        return ActorName;
    }

    public void setActorName(String ActorName) {
        this.ActorName = ActorName;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public int getMovieYear() {
        return MovieYear;
    }

    public void setMovieYear(int MovieYear) {
        this.MovieYear = MovieYear;
    }

    public String getMovieNewName() {
        return MovieNewName;
    }

    public void setMovieNewName(String MovieNewName) {
        this.MovieNewName = MovieNewName;
    }

    public String getErrorMovie() {
        return ErrorMovie;
    }

    public void setErrorMovie(String ErrorMovie) {
        this.ErrorMovie = ErrorMovie;
    }
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public Movie getNewMovie() {
        return newMovie;
    }

    public void setNewMovie(Movie newMovie) {
        this.newMovie = newMovie;
    }

    public int getRootB() {
        return rootB;
    }

    public void setRootB(int rootB) {
        this.rootB = rootB;
    }

    public String getEditFestErr() {
        return EditFestErr;
    }

    public void setEditFestErr(String EditFestErr) {
        this.EditFestErr = EditFestErr;
    }
    
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

    public UltraFest getUFest() {
        return UFest;
    }

    public void setUFest(UltraFest UFest) {
        this.UFest = UFest;
    }

    public ProjectionWithMovie getCurrEditProj() {
        return currEditProj;
    }

    public void setCurrEditProj(ProjectionWithMovie currEditProj) {
        this.currEditProj = currEditProj;
    }

    public String getEditMovieForProjection() {
        return EditMovieForProjection;
    }

    public void setEditMovieForProjection(String EditMovieForProjection) {
        this.EditMovieForProjection = EditMovieForProjection;
    }

    public String getEditTempLock() {
        return EditTempLock;
    }

    public void setEditTempLock(String EditTempLock) {
        this.EditTempLock = EditTempLock;
    }

    public String getEditProjErr() {
        return EditProjErr;
    }

    public void setEditProjErr(String EditProjErr) {
        this.EditProjErr = EditProjErr;
    }
    
    public boolean checkIfCanceled(ProjectionWithMovie proj){
        
        if(proj.getProjection().getStatus().equals("Canceled")){
            return true;
        }
        else{
            return false;
        }
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
    
    public List<LocElem> getAllULocations(){
        List<LocElem> locks=new ArrayList<>();
        for (Location lock : UFest.getFestLocations()) {
            locks.add(new LocElem(lock.getAdress(), lock.getBuilding()));
        }
        return locks;
    }
    
    public String formatDate(Date date){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }
}
