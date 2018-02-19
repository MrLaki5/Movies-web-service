/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Festival;
import entities.Location;
import entities.Projection;

/**
 *
 * @author milanlazarevic
 */
public class ProjectionWithFestWithLocation {
    private Projection projection;
    private Festival festival;
    private Location location;

    public ProjectionWithFestWithLocation(Projection projection, Festival festival, Location location) {
        this.projection = projection;
        this.festival = festival;
        this.location = location;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
