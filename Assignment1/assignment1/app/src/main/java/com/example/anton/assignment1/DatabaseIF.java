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
        Transaction transaction = new Transaction("Income", "ababa", "Work", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Income", "ababa","Mum", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Income", "ababa","Shuffing", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("income", "ababa","Blablba", 83.54f, "Other", "Monday");
        Transaction transaction4 = new Transaction("income", "ababa","Dingus", 100.54f, "Salary", "assday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        return transactions;
    }

    public ArrayList<Transaction> getExpenditures(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Income", "ababa", "Work", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Income", "ababa","Mum", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Income", "ababa","Shuffing", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("income", "ababa","Blablba", 83.54f, "Other", "Monday");
        Transaction transaction4 = new Transaction("income", "ababa","Dingus", 100.54f, "Salary", "assday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        return transactions;

    }

    public ArrayList<Transaction> getExpendituresYear(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Income", "ababa", "Work", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Income", "ababa","Mum", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Income", "ababa","Shuffing", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("income", "ababa","Blablba", 83.54f, "Other", "Monday");
        Transaction transaction4 = new Transaction("income", "ababa","Dingus", 100.54f, "Salary", "assday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);

        return transactions;

    }

    public ArrayList<Transaction> getExpendituresMonth(){

        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Income", "ababa", "Work", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Income", "ababa","Mum", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Income", "ababa","Shuffing", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("income", "ababa","Blablba", 83.54f, "Other", "Monday");
        Transaction transaction4 = new Transaction("income", "ababa","Dingus", 100.54f, "Salary", "assday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);

        return transactions;

    }

    public ArrayList<Transaction> getExpendituresWeek(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Income", "ababa", "Work", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Income", "ababa","Mum", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Income", "ababa","Shuffing", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("income", "ababa","Blablba", 83.54f, "Other", "Monday");
        Transaction transaction4 = new Transaction("income", "ababa","Dingus", 100.54f, "Salary", "assday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);

        return transactions;
    }

    public ArrayList<Transaction> getIncomeYear(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Income", "ababa", "Work", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Income", "ababa","Mum", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Income", "ababa","Shuffing", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("income", "ababa","Blablba", 83.54f, "Other", "Monday");
        Transaction transaction4 = new Transaction("income", "ababa","Dingus", 100.54f, "Salary", "assday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        return transactions;

    }

    public ArrayList<Transaction> getIncomeMonth(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Income", "ababa", "Work", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Income", "ababa","Mum", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Income", "ababa","Shuffing", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("income", "ababa","Blablba", 83.54f, "Other", "Monday");
        Transaction transaction4 = new Transaction("income", "ababa","Dingus", 100.54f, "Salary", "assday");
        transactions.add(transaction);
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        return transactions;

    }

    public ArrayList<Transaction> getIncomeWeek(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction("Income", "ababa", "Work", 34.54f, "Salary", "Monday");
        Transaction transaction1 = new Transaction("Income", "ababa","Mum", 31.54f, "Salary", "Tuesday");
        Transaction transaction2 = new Transaction("Income", "ababa","Shuffing", 67.54f, "Other", "Fryday");
        Transaction transaction3 = new Transaction("income", "ababa","Blablba", 83.54f, "Other", "Monday");
        Transaction transaction4 = new Transaction("income", "ababa","Dingus", 100.54f, "Salary", "assday");
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
