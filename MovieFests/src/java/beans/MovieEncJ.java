/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Actor;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author milanlazarevic
 */
public class MovieEncJ {
    private Movie movie;
    private String movieNameYear;
    private List<UploadedFile> images;
    private List<Actor> actors;

    public MovieEncJ(Movie movie) {
        this.movie = movie;
        this.movieNameYear=movie.getName()+"_"+movie.getYear();
        this.images=new ArrayList<>();
        this.actors=new ArrayList<>();
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<UploadedFile> getImages() {
        return images;
    }

    public void setImages(List<UploadedFile> images) {
        this.images = images;
    }

    public String getMovieNameYear() {
        return movieNameYear;
    }

    public void setMovieNameYear(String movieNameYear) {
        this.movieNameYear = movieNameYear;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }   
}
