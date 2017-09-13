package com.example.anton.assignment1;

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
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        return transactions;

    }


}
