package com.example.anton.assignment1;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 2017-09-13.
 */

public class PieChartHandler {


    public PieChartHandler(){

    }

    public PieData mapExpenditure(ArrayList<Transaction> arrList){
        float food = 0f;
        float travel = 0f;
        float other = 0f;
        float leisure = 0f;
        float accommodation = 0f;

        for(Transaction trans: arrList){
            switch(trans.getCategory()){
                case "Travel":
                    travel += trans.getAmount();
                    break;
                case "Other":
                    other += trans.getAmount();
                    break;
                case "Food":
                    food += trans.getAmount();
                    break;
                case "Leisure":
                    leisure += trans.getAmount();
                    break;
                case "Accommodation":
                    accommodation += trans.getAmount();
                    break;

            }
        }

        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        PieEntry foodEntry = new PieEntry(food, "Food");
        PieEntry travelEntry = new PieEntry(travel, "Travel");
        PieEntry otherEntry = new PieEntry(other, "Other");
        PieEntry leisureEntry = new PieEntry(leisure, "Leisure");
        PieEntry accommodationEntry = new PieEntry(accommodation, "Accommodation");

        pieEntries.add(foodEntry);
        pieEntries.add(travelEntry);
        pieEntries.add(otherEntry);
        pieEntries.add(leisureEntry);
        pieEntries.add(accommodationEntry);

        ArrayList<PieEntry> pieEntries1 = new ArrayList<>();
        for(int i = 0; i < pieEntries.size(); i++){
            if(pieEntries.get(i).getValue() !=  0f){
                pieEntries1.add(pieEntries.get(i));
            }
        }

        PieDataSet pieSet = new PieDataSet(pieEntries1, "Expenditure");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.MAGENTA);
        colors.add(Color.CYAN);

        pieSet.setColors(colors);

        PieData pieData = new PieData(pieSet);


        return pieData;
    }

    public PieData mapIncome(ArrayList<Transaction> arrList){
        float salary = 0;
        float other = 0;

        for(Transaction trans: arrList){
            switch(trans.getCategory()){
                case "Salary":
                    salary += trans.getAmount();
                    break;
                case "Other":
                    other += trans.getAmount();
                    break;

            }
        }

        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();
        PieEntry salaryEntry = new PieEntry(salary, "Salary");
        PieEntry otherEntry = new PieEntry(other, "Other");
        if(salaryEntry.getValue() != 0f){
            pieEntries.add(salaryEntry);
        }
        if(otherEntry.getValue() != 0f){
            pieEntries.add(otherEntry);
        }

        PieDataSet pieSet = new PieDataSet(pieEntries, "Income");

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);

        pieSet.setColors(colors);

        PieData pieData = new PieData(pieSet);

        return pieData;



    }
}
