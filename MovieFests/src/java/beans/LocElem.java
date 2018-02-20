/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author milanlazarevic
 */
public class LocElem{
        private String location;
        private String building;
        private int idLok;
        private int idHall;

        public LocElem(String location, String building) {
            this.location = location;
            this.building = building;
            this.idLok=-1;
            this.idHall=-1;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }   
        
        @Override
        public String toString(){
            return location+"_"+building;
        }

        public int getIdLok() {
            return idLok;
        }

        public void setIdLok(int idLok) {
            this.idLok = idLok;
        }  

        public int getIdHall() {
            return idHall;
        }

        public void setIdHall(int idHall) {
            this.idHall = idHall;
        }
    }
