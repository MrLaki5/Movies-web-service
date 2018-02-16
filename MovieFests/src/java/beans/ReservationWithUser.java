/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Reservation;
import entities.User;
import java.io.Serializable;

/**
 *
 * @author milanlazarevic
 */
public class ReservationWithUser implements Serializable{
    
    private Reservation reservation;
    private User user;

    public ReservationWithUser(Reservation reservation, User user) {
        this.reservation = reservation;
        this.user = user;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}
