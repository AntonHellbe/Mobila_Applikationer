package com.example.anton.assignment1;

/**
 * Created by Anton on 2017-09-13.
 */

public class User {

    private String username;
    private String password;
    private float balance;

    public User(String username, String password, float balance){
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.balance = 0;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setBalance(float balance){
        this.balance = balance;
    }

    public float getBalance(){
        return this.balance;
    }
}
