/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Movie;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author milanlazarevic
 */
public class UltraMovie implements Serializable{
    private Movie movie;
    private List<ProjectionWithFestWithLocation> projections;

    public UltraMovie(Movie movie, List<ProjectionWithFestWithLocation> projections) {
        this.movie = movie;
        this.projections = projections;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<ProjectionWithFestWithLocation> getProjections() {
        return projections;
    }

    public void setProjections(List<ProjectionWithFestWithLocation> projections) {
        this.projections = projections;
    }
}
