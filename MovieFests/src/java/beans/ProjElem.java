/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;

/**
 *
 * @author milanlazarevic
 */
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