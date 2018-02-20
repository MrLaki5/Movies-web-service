/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import entities.Festival;
import entities.Location;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milanlazarevic
 */
public class FestivalWithLocations {
    Festival festival;
    List<LocElem> locations;
    List<ProjElem> projections;

    public FestivalWithLocations(Festival festival, List<LocElem> locations) {
        this.festival = festival;
        this.locations = locations;
        this.projections=new ArrayList<>();
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public List<LocElem> getLocations() {
        return locations;
    }

    public void setLocations(List<LocElem> locations) {
        this.locations = locations;
    }

    public List<ProjElem> getProjections() {
        return projections;
    }

    public void setProjections(List<ProjElem> projections) {
        this.projections = projections;
    }
    
    public void addProjection(ProjElem projection){
        projections.add(projection);
    }
    
    public void removeProjection(ProjElem projection){
        projections.remove(projection);
    }
}
