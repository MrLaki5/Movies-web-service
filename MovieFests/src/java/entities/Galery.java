package entities;
// Generated Feb 14, 2018 12:46:36 PM by Hibernate Tools 4.3.1



/**
 * Galery generated by hbm2java
 */
public class Galery  implements java.io.Serializable {


     private Integer idG;
     private byte[] picture;
     private Integer idMovie;

    public Galery() {
    }

    public Galery(byte[] picture, Integer idMovie) {
       this.picture = picture;
       this.idMovie = idMovie;
    }
   
    public Integer getIdG() {
        return this.idG;
    }
    
    public void setIdG(Integer idG) {
        this.idG = idG;
    }
    public byte[] getPicture() {
        return this.picture;
    }
    
    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
    public Integer getIdMovie() {
        return this.idMovie;
    }
    
    public void setIdMovie(Integer idMovie) {
        this.idMovie = idMovie;
    }




}


