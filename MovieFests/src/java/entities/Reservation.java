package entities;
// Generated Feb 18, 2018 8:22:58 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Reservation generated by hbm2java
 */
public class Reservation  implements java.io.Serializable {


     private Integer idRes;
     private Integer version;
     private String code;
     private Date date;
     private String type;
     private Integer ticketNum;
     private String username;
     private Integer idProjection;
     private String status;

    public Reservation() {
    }

    public Reservation(String code, Date date, String type, Integer ticketNum, String username, Integer idProjection, String status) {
       this.code = code;
       this.date = date;
       this.type = type;
       this.ticketNum = ticketNum;
       this.username = username;
       this.idProjection = idProjection;
       this.status = status;
    }
   
    public Integer getIdRes() {
        return this.idRes;
    }
    
    public void setIdRes(Integer idRes) {
        this.idRes = idRes;
    }
    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public Integer getTicketNum() {
        return this.ticketNum;
    }
    
    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public Integer getIdProjection() {
        return this.idProjection;
    }
    
    public void setIdProjection(Integer idProjection) {
        this.idProjection = idProjection;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }




}


