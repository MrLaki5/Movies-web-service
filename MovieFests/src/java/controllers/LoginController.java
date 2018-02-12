/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



/**
 *
 * @author milanlazarevic
 */

@ManagedBean
@SessionScoped
public class LoginController {
    private String Uesrname;
    private String Password;
    private int currPage=0;
    
    //LOGIC METHODS=============
    
    public String login(){
        return "";
    }
    
    public String register(){
        return "";
    }
    
    //REDIRECT METHODS============
    
    public String goRegister(){
        currPage=1;
        return "register";
    }
    
    public String goLogin(){
        currPage=0;
        return "login";
    }

    //GETHERS AND SETTERS=========
    
    public String getUesrname() {
        return Uesrname;
    }

    public void setUesrname(String Uesrname) {
        this.Uesrname = Uesrname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }
    
    
}
