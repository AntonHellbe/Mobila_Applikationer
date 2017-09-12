package com.example.anton.assignment1;


import android.app.Activity;
import android.content.Intent;

public class Controller {

    private String username = "hej";
    private String password = "hejhej";
    private MainActivity mainActivity;
    private UserActivity userActivity;

    public Controller(){

    }


    public void signUp() {

    }

    public boolean login(String username, String password) {

        if(this.username.equals(username) && this.password.equals(password)){
            return true;
        }else {
            return false;
        }
    }

    public void setUsername(String text){

    }


}
