/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Festival;
import entities.Location;
import java.util.List;

/**
 *
 * @author milanlazarevic
 */
public class UltraFest {
    Festival fest;
    List<Location> festLocations;
    List<ProjectionWithMovie> projections;

    public UltraFest(Festival fest, List<Location> festLocations, List<ProjectionWithMovie> projections) {
        this.fest = fest;
        this.festLocations = festLocations;
        this.projections = projections;
    }
    
    public Festival getFest() {
        return fest;
    }

    public void setFest(Festival fest) {
        this.fest = fest;
    }

    public List<Location> getFestLocations() {
        return festLocations;
    }

    public void setFestLocations(List<Location> festLocations) {
        this.festLocations = festLocations;
    }

    public List<ProjectionWithMovie> getProjections() {
        return projections;
    }

    public void setProjections(List<ProjectionWithMovie> projections) {
        this.projections = projections;
    }
    
    
}
