/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Location;
import entities.Projection;

/**
 *
 * @author milanlazarevic
 */
public class ElemOfProLok{
        private Projection projection;
        private Location location;

        public ElemOfProLok(Projection projection, Location location) {
            this.projection = projection;
            this.location = location;
        }
        
        public Projection getProjection() {
            return projection;
        }

        public void setProjection(Projection projection) {
            this.projection = projection;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }    
    }