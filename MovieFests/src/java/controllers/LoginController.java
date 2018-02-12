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
    
    private String RegUsername;
    private String RegFirstName;
    private String RegLastName;
    private String RegTelephone;
    private String RegEmail;
    private String RegPassword;
    private String RegConfPass;
    
    private String loginErr="";
    private String registerErr="";
    
    //LOGIC METHODS=============
    
    public String login(){
        return "";
    }
    
    public String register(){
        int errF=0;
        if("".equals(RegUsername) || "".equals(RegFirstName) ||
           "".equals(RegLastName) || "".equals(RegTelephone) ||
           "".equals(RegEmail) || "".equals(RegPassword) ||
           "".equals(RegConfPass) ){
            errF=1;
            registerErr="All fields must be set";
            return goRegister();
        }
        if(!RegPassword.equals(RegConfPass)){
            registerErr="Passwords must be matching";
            return goRegister();
        }
        if(errF==1){
            return goRegister();
        }
        else{
            loginErr="Password change successful!";
            registerErr="";
            RegUsername="";
            RegFirstName="";
            RegLastName="";
            RegTelephone="";
            RegEmail="";
            RegPassword="";
            RegConfPass="";
            return goLogin();
        }
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

    public String getLoginErr() {
        return loginErr;
    }

    public void setLoginErr(String loginErr) {
        this.loginErr = loginErr;
    }

    public String getRegUsername() {
        return RegUsername;
    }

    public void setRegUsername(String RegUsername) {
        this.RegUsername = RegUsername;
    }

    public String getRegFirstName() {
        return RegFirstName;
    }

    public void setRegFirstName(String RegFirstName) {
        this.RegFirstName = RegFirstName;
    }

    public String getRegLastName() {
        return RegLastName;
    }

    public void setRegLastName(String RegLastName) {
        this.RegLastName = RegLastName;
    }

    public String getRegTelephone() {
        return RegTelephone;
    }

    public void setRegTelephone(String RegTelephone) {
        this.RegTelephone = RegTelephone;
    }

    public String getRegEmail() {
        return RegEmail;
    }

    public void setRegEmail(String RegEmail) {
        this.RegEmail = RegEmail;
    }

    public String getRegPassword() {
        return RegPassword;
    }

    public void setRegPassword(String RegPassword) {
        this.RegPassword = RegPassword;
    }

    public String getRegConfPass() {
        return RegConfPass;
    }

    public void setRegConfPass(String RegConfPass) {
        this.RegConfPass = RegConfPass;
    }

    public String getRegisterErr() {
        return registerErr;
    }

    public void setRegisterErr(String registerErr) {
        this.registerErr = registerErr;
    }
    
    
}
