package com.example.anton.assignment1;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Anton on 2017-09-13.
 */

class Transaction implements Parcelable {
    private int id;
    private String title;
    private String type;
    private String userid;
    private float amount;
    private String category;
    private String date;

    public Transaction(String type, String title, String userid, float amount, String category, String date){
        this.id = 0;
        this.type = type;
        this.title = title;
        this.userid = userid;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public Transaction(int id, String type, String title, String userid, float amount, String category, String date){
        this.id = id;
        this.type = type;
        this.title = title;
        this.userid = userid;
        this.amount = amount;
        this.category = category;
        this.date = date;

    }

    protected Transaction(Parcel in) {
        id = in.readInt();
        title = in.readString();
        type = in.readString();
        userid = in.readString();
        amount = in.readFloat();
        category = in.readString();
        date = in.readString();
    }


    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount){
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setUserid(String userid){
        this.userid = userid;
    }

    public String getUserid(){
        return this.userid;
    }

    public void describe(){
        Log.v("Message", "" + this.category + this.amount + this.title + this.date + this.type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(userid);
        dest.writeFloat(amount);
        dest.writeString(category);
        dest.writeString(date);
    }

    public static Creator<Transaction> CREATOR = new Creator<Transaction>() {

        @Override
        public Transaction createFromParcel(Parcel parcel) {
            return new Transaction(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readFloat(), parcel.readString(), parcel.readString());
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
