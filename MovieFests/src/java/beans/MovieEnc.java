/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Movie;

/**
 *
 * @author milanlazarevic
 */
public class MovieEnc {
    private Movie movie;
    private String idMovie;

    public MovieEnc(Movie movie) {
        this.movie = movie;
        int id=movie.getIdMovie();
        this.idMovie=""+id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }
    
}
