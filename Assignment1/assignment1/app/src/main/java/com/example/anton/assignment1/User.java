package com.example.anton.assignment1;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Anton on 2017-09-13.
 */

public class User implements Parcelable{

    private String username;
    private String password;
    private String lastname;
    private String name;
    private int id;

    public User(String username, String name, String lastname, String password){
        this.id = 0;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.password = password;

    }

    public User(int id, String username, String name, String lastname, String password){
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.password = password;

    }


    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void describe(){
        Log.v("User:" , "" + this.id + " " + this.username + this.name + this.lastname + this.password );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(username);
        parcel.writeString(name);
        parcel.writeString(lastname);
        parcel.writeString(password);
    }

    public static Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
