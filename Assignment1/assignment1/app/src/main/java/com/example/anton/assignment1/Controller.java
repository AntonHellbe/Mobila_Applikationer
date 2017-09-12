package com.example.anton.assignment1;


public class Controller {

    private String username = "hej";
    private String password = "hejhej";

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
}
