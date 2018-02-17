/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import db.FestivalHelper;
import db.UserHelper;
import entities.Festival;
import entities.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;



/**
 *
 * @author milanlazarevic
 */

@ManagedBean
@SessionScoped
public class LoginController {
    private UserHelper userHelper=new UserHelper();
    private FestivalHelper festivalHelper=new FestivalHelper();
    
    private int isLoged=0;
    
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
    
    private Date dateFrom=new Date();
    private Date dateTo=new Date();
    private String festivalName="";
    
    //CONSTRUCTOR===============
    
    public LoginController(){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 20);
        dateTo=calendar.getTime();
    }
    
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
        if(user.getType().equals("NoType")){
            loginErr="Registration not confirmed";
            logErrColor="text-danger";
            return "";
        }
        
        if(user.getType().equals("User")){
            isLoged=1;
            return goFestivals();
        }
        if(user.getType().equals("Seller")){
            isLoged=2;
            return goRegistrationSeller();
        }
        return "";
    }
    
    public String logout(){
        isLoged=0;
        FacesContext context=FacesContext.getCurrentInstance();
        HttpSession session=(HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return goLogin();
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
        User user=userHelper.getUserById(ForgotUsername);
        if(user==null){
            passErr="Wrong username";
            return "";
        }
        if(!user.getPassword().equals(ForgotPassword)){
            passErr="Wrong password";
            return "";
        }
        
        if(ForgotNewPassword.length()<8 || ForgotNewPassword.length()>12){
            passErr="Wrong password length";
            return "";
        }
        int bigLetNum=0;
        int smallLetNum=0;
        int numberNum=0;
        int specialNum=0;
        char pom='a';
        for(int i=0; i<ForgotNewPassword.length(); i++){
            if(pom==ForgotNewPassword.charAt(i)){
                passErr="Two neighbour characters are same";
                return "";
            }
            pom=ForgotNewPassword.charAt(i);
            if(Character.isUpperCase(ForgotNewPassword.charAt(i))){
                bigLetNum++;
                continue;
            }
            if(i==0){
                passErr="First character must be uppercase";
                return "";
            }
            if(Character.isLowerCase(ForgotNewPassword.charAt(i))){
                smallLetNum++;
                continue;
            }
            if(Character.isDigit(ForgotNewPassword.charAt(i))){
                numberNum++;
                continue;
            }
            if((ForgotNewPassword.charAt(i)=='#')||(ForgotNewPassword.charAt(i)=='*')||
               (ForgotNewPassword.charAt(i)=='.')||(ForgotNewPassword.charAt(i)=='!')||
               (ForgotNewPassword.charAt(i)=='?')||(ForgotNewPassword.charAt(i)=='$')){
                specialNum++;
            }
        }
        if(bigLetNum<2){
            passErr="There must be minimum two uppercase letters in password";
            return "";
        }
        if(smallLetNum<3){
            passErr="There must be minimum three lowercase letters in password";
            return "";
        }
        if(numberNum<1){
            passErr="There must be minimum one numeric in password";
            return "";
        }
        if(specialNum<1){
            passErr="There must be minimum one special character in password";
            return "";
        }
        
        user.setPassword(ForgotNewPassword);
        userHelper.changePass(user);
        ForgotNewPassword="";
        ForgotPassword="";
        ForgotUsername="";
        logErrColor="text-success";
        loginErr="Password changed!";
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
    
    public String goFestivals(){
        currPage=1;
        return "indexUser?faces-redirect=true";
    }
    
    public String goUnregFestivals(){
        currPage=2;
        return "indexUnregistered?faces-redirect=true";
    }
    
    public String goRegistrationUser(){
        currPage=2;
        return "registrationUser?faces-redirect=true";
    }
    
    public String goRegistrationSeller(){
        currPage=1;
        return "registrationSeller?faces-redirect=true";
    }
    
    public String goProjectionSeller(){
        currPage=2;
        return "projectionSeller?faces-redirect=true";
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

    public int getIsLoged() {
        return isLoged;
    }

    public void setIsLoged(int isLoged) {
        this.isLoged = isLoged;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        if(dateFrom.after(dateTo)){
            return;
        }
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        if(dateTo.before(dateFrom)){
            return;
        }
        this.dateTo = dateTo;
    }

    public String getFestivalName() {
        return festivalName;
    }

    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }
    
    public List<Festival> getFestivals(){
        List<Festival> tempList =festivalHelper.getCurrentFestivals(dateFrom, dateTo);
        ArrayList<Festival> sendList=new ArrayList<>();
        if(festivalName.equals("")){
            return tempList;
        }
        else{
            for(int i=0; i<tempList.size();i++){
                if(festivalName.contains(tempList.get(i).getName())){
                    sendList.add(tempList.get(i));
                }
            }
            return sendList;
        }
    }
}
