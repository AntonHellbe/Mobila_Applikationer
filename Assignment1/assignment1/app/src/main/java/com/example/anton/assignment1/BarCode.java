package com.example.anton.assignment1;

import android.util.Log;

/**
 * Created by Anton on 2017-09-18.
 */

public class BarCode {

    private String id;
    private String title;
    private String category;
    private float amount;

    public BarCode(String id, String title, String category, float amount){
        this.id = id;
        this.title = title;
        this.category = category;
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void describe(){
        Log.e("Barcode", this.id + this.title + this.category + this.amount);
    }
}
