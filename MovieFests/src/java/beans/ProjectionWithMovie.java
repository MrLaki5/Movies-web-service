/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Location;
import entities.Movie;
import entities.Projection;
import java.io.Serializable;

/**
 *
 * @author milanlazarevic
 */
public class ProjectionWithMovie implements Serializable{
    Projection projection;
    Movie movie;
    Location location;

    public ProjectionWithMovie(Projection projection, Movie movie, Location location) {
        this.projection = projection;
        this.movie = movie;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    
    
}
