/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.UserHelper;
import entities.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



/**
 *
 * @author milanlazarevic
 */

@ManagedBean
@SessionScoped
public class LoginController {
    private UserHelper userHelper=new UserHelper();
    
    private String Username;
    private String Password;
    private int currPage=0;
    
    private String RegUsername;
    private String RegFirstName;
    private String RegLastName;
    private String RegTelephone;
    private String RegEmail;
    private String RegPassword;
    private String RegConfPass;
    
    private String ForgotUsername;
    private String ForgotPassword;
    private String ForgotNewPassword;
    
    private String logErrColor="text-danger";
    private String loginErr="";
    private String registerErr="";
    private String passErr="";
    
    //LOGIC METHODS=============
    
    public String login(){
        loginErr="";
        logErrColor="";
        if("".equals(Username) || "".equals(Password)){
            loginErr="All fields must be set";
            logErrColor="text-danger";
            return "";
        }
        User user=userHelper.getUserById(Username);
        if(user==null){
            loginErr="Wrong username";
            logErrColor="text-danger";
            Username="";
            return "";
        }
        if(!user.getPassword().equals(Password)){
            loginErr="Wrong password";
            logErrColor="text-danger";
            return "";
        }
        return "";
    }
    
    public String register(){
        int errF=0;
        registerErr="";
        if("".equals(RegUsername) || "".equals(RegFirstName) ||
           "".equals(RegLastName) || "".equals(RegTelephone) ||
           "".equals(RegEmail) || "".equals(RegPassword) ||
           "".equals(RegConfPass) ){
            errF=1;
            registerErr="All fields must be set";
            return "";
        }
        if(!RegPassword.equals(RegConfPass)){
            registerErr="Passwords must be matching";
            return "";
        }
        if(RegPassword.length()<8 || RegPassword.length()>12){
            registerErr="Wrong password length";
            return "";
        }
        int bigLetNum=0;
        int smallLetNum=0;
        int numberNum=0;
        int specialNum=0;
        char pom='a';
        for(int i=0; i<RegPassword.length(); i++){
            if(pom==RegPassword.charAt(i)){
                registerErr="Two neighbour characters are same";
                return "";
            }
            pom=RegPassword.charAt(i);
            if(Character.isUpperCase(RegPassword.charAt(i))){
                bigLetNum++;
                continue;
            }
            if(i==0){
                registerErr="First character must be uppercase";
                return "";
            }
            if(Character.isLowerCase(RegPassword.charAt(i))){
                smallLetNum++;
                continue;
            }
            if(Character.isDigit(RegPassword.charAt(i))){
                numberNum++;
                continue;
            }
            if((RegPassword.charAt(i)=='#')||(RegPassword.charAt(i)=='*')||
               (RegPassword.charAt(i)=='.')||(RegPassword.charAt(i)=='!')||
               (RegPassword.charAt(i)=='?')||(RegPassword.charAt(i)=='$')){
                specialNum++;
            }
        }
        if(bigLetNum<2){
            registerErr="There must be minimum two uppercase letters in password";
            return "";
        }
        if(smallLetNum<3){
            registerErr="There must be minimum three lowercase letters in password";
            return "";
        }
        if(numberNum<1){
            registerErr="There must be minimum one numeric in password";
            return "";
        }
        if(specialNum<1){
            registerErr="There must be minimum one special character in password";
            return "";
        }
        User user=userHelper.getUserById(RegUsername);
        if(user!=null){
            registerErr="Username already exists";
            return "";
        }
        user=new User(RegUsername, RegFirstName, RegLastName, RegEmail, RegTelephone, RegPassword, "NoType");
        userHelper.addUser(user);
        if(errF==1){
            return "";
        }
        else{
            logErrColor="text-success";
            loginErr="Registration successful!";
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
    
    public String changePass(){
        if("".equals(ForgotUsername) || "".equals(ForgotPassword) || "".equals(ForgotNewPassword)){
            passErr="All fields must be set";
            return "";
        }
        return goLogin();
    }
    
    //REDIRECT METHODS============
    
    public String goRegister(){
        currPage=1;
        passErr="";
        registerErr="";
        return "register?faces-redirect=true";
    }
    
    public String goLogin(){
        currPage=0;
        passErr="";
        registerErr="";
        return "login?faces-redirect=true";
    }

    //GETHERS AND SETTERS=========
    
    public String getUesrname() {
        return Username;
    }

    public void setUesrname(String Uesrname) {
        this.Username = Uesrname;
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

    public String getLogErrColor() {
        return logErrColor;
    }

    public void setLogErrColor(String logErrColor) {
        this.logErrColor = logErrColor;
    }

    public String getPassErr() {
        return passErr;
    }

    public void setPassErr(String passErr) {
        this.passErr = passErr;
    }

    public String getForgotUsername() {
        return ForgotUsername;
    }

    public void setForgotUsername(String ForgotUsername) {
        this.ForgotUsername = ForgotUsername;
    }

    public String getForgotPassword() {
        return ForgotPassword;
    }

    public void setForgotPassword(String ForgotPassword) {
        this.ForgotPassword = ForgotPassword;
    }

    public String getForgotNewPassword() {
        return ForgotNewPassword;
    }

    public void setForgotNewPassword(String ForgotNewPassword) {
        this.ForgotNewPassword = ForgotNewPassword;
    }
    
    
}
