package com.example.anton.assignment1;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-13.
 */

public class DatabaseIF {


    public DatabaseIF(){

    }

    public ArrayList<Transaction> getIncome(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Income", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Income", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Income", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("Income", 83.54f, "Other", "Monday");
        Transaction transaction4 = new Transaction("Income", 100.54f, "Salary", "assday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        return transactions;
    }

    public ArrayList<Transaction> getExpenditures(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Expenditure", 34.54f, "Other", "Monday");
        Transaction transaction1 = new Transaction("Expenditure", 31.54f, "Leisure", "Monday");
        Transaction transaction2 = new Transaction("Expenditure", 67.54f, "Accommodation", "Monday");
        Transaction transaction3 = new Transaction("Expenditure", 83.54f, "Food", "Monday");
        Transaction transaction4 = new Transaction("Expenditure", 120.54f, "Accommodation", "Monday");
        Transaction transaction5 = new Transaction("Expenditure", 33.54f, "Food", "Someday");
        Transaction transaction6 = new Transaction("Expenditure", 66.54f, "Accommodation", "Whatday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        transactions.add(transaction5);
        transactions.add(transaction6);
        return transactions;

    }

    public ArrayList<Transaction> getExpendituresYear(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Icefishing", 34.54f, "Other", "Monday");
        Transaction transaction1 = new Transaction("Go cart", 31.54f, "Leisure", "Monday");
        Transaction transaction2 = new Transaction("Hostel", 67.54f, "Accommodation", "Monday");
        Transaction transaction3 = new Transaction("Burger", 83.54f, "Food", "Monday");
        Transaction transaction4 = new Transaction("Apartment", 120.54f, "Accommodation", "Monday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);

        return transactions;

    }

    public ArrayList<Transaction> getExpendituresMonth(){

        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Wakeboarding", 80.54f, "Other", "Monday");
        Transaction transaction1 = new Transaction("fishy fishy", 55.54f, "Leisure", "Monday");
        Transaction transaction2 = new Transaction("Vandra", 67.54f, "Accommodation", "Monday");
        Transaction transaction3 = new Transaction("Subway", 33.54f, "Food", "Monday");
        Transaction transaction4 = new Transaction("Apartment", 90.54f, "Accommodation", "Monday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);

        return transactions;

    }

    public ArrayList<Transaction> getExpendituresWeek(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Fishing", 34.54f, "Other", "Monday");
        Transaction transaction1 = new Transaction("Boat", 31.54f, "Leisure", "Monday");
        Transaction transaction2 = new Transaction("Hotel", 60.54f, "Accommodation", "Monday");
        Transaction transaction3 = new Transaction("Something", 44.54f, "Food", "Monday");
        Transaction transaction4 = new Transaction("Dingus", 80.54f, "Accommodation", "Monday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);

        return transactions;
    }

    public ArrayList<Transaction> getIncomeYear(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Work", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Mum", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Shuffing", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("Blablba", 83.54f, "Other", "Monday");
        Transaction transaction4 = new Transaction("Dingus", 100.54f, "Salary", "assday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        return transactions;

    }

    public ArrayList<Transaction> getIncomeMonth(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Hard work", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Work work", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Robbed a guy", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("free money", 83.54f, "Other", "Monday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        return transactions;

    }

    public ArrayList<Transaction> getIncomeWeek(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Sold burgers", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Sold hotdogs", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Found money", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("Something else", 83.54f, "Other", "Monday");
        Transaction transaction4 = new Transaction("Dingus", 100.54f, "Salary", "assday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        return transactions;

    }

//    public ArrayList<Transaction> getCustom(){
//    }


}
