package com.example.anton.assignment1;

/**
 * Created by Anton on 2017-09-13.
 */

class Transaction {

    private String title;
    private float amount;
    private String category;
    private String date;

    public Transaction(String title, float amount, String category, String date){
        this.category = category;
        this.amount = amount;
        this.title = title;
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
