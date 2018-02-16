/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Feedback;
import entities.Movie;
import entities.Projection;
import entities.Reservation;
import java.io.Serializable;

/**
 *
 * @author milanlazarevic
 */
public class ReservationWithRating implements Serializable
{
    Reservation reservation;
    Feedback feedback;
    Projection projection;
    Movie movie;

    public ReservationWithRating(Reservation reservation, Feedback feedback, Movie movie, Projection projection) {
        this.reservation = reservation;
        this.feedback = feedback;
        this.projection=projection;
        this.movie=movie;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
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
