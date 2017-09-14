package com.example.anton.assignment1;


import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.data.PieData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Controller {

    private String username = "hej";
    private String password = "hejhej";
    private UserActivity userActivity;
    private FragmentIncome fragmentIncome;
    private fragment_expenditure fragmentExpenditure;
    private FragmentUser fragmentUser;
    private FragmentMain fragmentMain;
    private FragmentSearch fragmentSearch;
    private FragmentAdd fragmentAdd;
    private PieChartHandler pieChartHandler;
    private int currentFragment = 0;
    private int dateSelected;
    private DatabaseIF db;
    private User currentUser;
    private String[] toggleStates = new String[2];
    private int spinnerCategory;
    private int spinnerCategory1;


    public Controller(){

    }

    public Controller(UserActivity userActivity){

        this.userActivity = userActivity;
        this.fragmentMain = new FragmentMain();
        this.fragmentIncome = new FragmentIncome();
        this.fragmentExpenditure = new fragment_expenditure();
        this.fragmentUser = new FragmentUser();
        this.fragmentSearch = new FragmentSearch();
        this.fragmentAdd = new FragmentAdd();

        this.pieChartHandler = new PieChartHandler();
        this.db = new DatabaseIF();

        fragmentAdd.setController(this);
        fragmentMain.setController(this);
        fragmentIncome.setController(this);
        fragmentExpenditure.setController(this);
        fragmentUser.setController(this);
        fragmentSearch.setController(this);
        initializeResources();
        userActivity.setFragment(fragmentMain, true);
    }

    private void initializeResources() {
        Resources res = userActivity.getResources();
        toggleStates[0] = res.getString(R.string.toggleBtnOff);
        toggleStates[1] = res.getString(R.string.toggleBtnOn);
        spinnerCategory = R.array.spinnerCategory;
        spinnerCategory1 = R.array.spinnerCategory1;

    }


    public void signUp(){

    }

    public boolean login(String username, String password) {

        if(this.username.equals(username) && this.password.equals(password)){
            return true;
        }else {
            return false;
        }
    }

    public void updateUsername(){
        fragmentMain.setUserName("Welcome " + currentUser.getUsername());
    }


    public void changeFragment(int position) {
        if(position != currentFragment){
            switch(position){
                case 0:
                    userActivity.setFragment(fragmentMain, true);
                    break;
                case 1:
                    userActivity.setFragment(fragmentUser, true);
                    break;
                case 2:
                    userActivity.setFragment(fragmentIncome, true);
                    break;
                case 3:
                    userActivity.setFragment(fragmentExpenditure, true);
                    break;
                case 4:
                    userActivity.setFragment(fragmentSearch, true);
                    break;
                case 5:
                    userActivity.setFragment(fragmentAdd, true);
            }
        }
        currentFragment = position;
    }

    public void addChartData(Boolean state) {
        PieData pieData = new PieData();
        if(!state){
            switch(dateSelected){
                case 0:
                    pieData = pieChartHandler.mapExpenditure(db.getExpendituresYear());
                    break;
                case 1:
                    pieData = pieChartHandler.mapExpenditure(db.getExpendituresMonth());
                    break;
                case 2:
                    pieData = pieChartHandler.mapExpenditure(db.getExpendituresWeek());
                    break;
            }

            fragmentMain.setTbOn(toggleStates[1]);
        }else{
            switch(dateSelected){
                case 0:
                    pieData = pieChartHandler.mapIncome(db.getIncomeYear());
                    break;
                case 1:
                    pieData = pieChartHandler.mapIncome(db.getIncomeMonth());
                    break;
                case 2:
                    pieData = pieChartHandler.mapIncome(db.getIncomeWeek());
                    break;
            }
            fragmentMain.setTbOff(toggleStates[0]);
        }
        pieData.setValueTextSize(10f);
        fragmentMain.setPieChartData(pieData);

    }

    public void setAddFragment(){
        userActivity.setFragment(fragmentAdd, true);
        currentFragment = 5;
    }



    public void setTransactionAdapter() {
        switch(dateSelected){
            case 0:
                // TODO - set Search to Year / Month / Week
                if(currentFragment == 3){
                    fragmentExpenditure.setAdapter(db.getExpendituresYear());
                }else{
                    fragmentIncome.setAdapter(db.getIncomeYear());
                }
                break;
            case 1:
                if(currentFragment == 3){
                    fragmentExpenditure.setAdapter(db.getExpendituresMonth());
                }else{
                    fragmentIncome.setAdapter(db.getIncomeMonth());
                }
                break;
            case 2:
                if(currentFragment == 3){
                    fragmentExpenditure.setAdapter(db.getExpendituresWeek());
                }else{
                    fragmentIncome.setAdapter(db.getIncomeWeek());
                }
                break;
            case 3:
                // TODO - Implement custom dates
                if(currentFragment == 3){
                    fragmentExpenditure.setAdapter(db.getExpenditures());
                }else{
                    fragmentIncome.setAdapter(db.getIncome());
                }
                break;
        }

    }

    public void dateSelect(int choice) {
        dateSelected = choice;
        setTvDatesSelected();
    }

    public void setTvDatesSelected(){
        switch(dateSelected){
            case 0:
                fragmentSearch.setTvFromDate("From: " + getCalculatedDate(0));
                fragmentSearch.setTvToDate("To: " + getCalculatedDate(-365));
                break;
            case 1:
                fragmentSearch.setTvFromDate("From: " + getCalculatedDate(0));
                fragmentSearch.setTvToDate("To: " + getCalculatedDate(-31));
                break;
            case 2:
                fragmentSearch.setTvFromDate("From: " + getCalculatedDate(0));
                fragmentSearch.setTvToDate("To: " + getCalculatedDate(-7));
                break;
            case 3:
                fragmentSearch.startDatePicker();
                break;
        }
    }

    public String getCalculatedDate(int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        if(days < -300){
            cal.add(Calendar.YEAR, -1);
        }else if(days < - 20){
            cal.add(Calendar.MONTH, -1);
        }else {
            cal.add(Calendar.DAY_OF_YEAR, days);
        }
        Date date = new Date(cal.getTimeInMillis());

        return s.format(date);
    }

    public String editUser(String username, String password) {
        String result = "";
        if(username.equals("") && password.equals("")){
            return result = "Atleast one field must be filled in";
        }else if(username.equals("")){
            return result = "Password updated";
            // Set password on user
            // Update database
        }else if(password.equals("")){
            currentUser.setUsername(username);
            userActivity.updateNavDrawer(currentUser.getUsername());
            return result = "Username updated";
            //Set username on user
            // Update database
        }else{
            //Set username and password
            // Update database
            return result = "Username and password updated";
        }

    }

    public void createUser(Intent intent) {
        User user = new User(intent.getStringExtra("userid"), intent.getStringExtra("password"));
        currentUser = user;

    }

    public String getCurrentUserName(){
        return currentUser.getUsername();
    }


    public Bundle saveInformation(Bundle outState) {
        outState.putInt("currentFragment", currentFragment);
        outState.putBoolean("piechart", fragmentMain.getState());

        return outState;
    }

    public void rescueMission(Bundle savedInstanceState) {
        fragmentMain.setState(savedInstanceState.getBoolean("piechart"));
        changeFragment(savedInstanceState.getInt("currentFragment"));

    }

    public void changeSpinner(int i) {
        switch(i){
            case 0:
                fragmentAdd.setSpinnerAdapter(spinnerCategory);
                break;
            case 1:
                fragmentAdd.setSpinnerAdapter(spinnerCategory1);
                break;
        }
    }
}
