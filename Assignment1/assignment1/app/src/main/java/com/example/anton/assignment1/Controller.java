package com.example.anton.assignment1;


import android.content.res.Resources;

import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;

public class Controller {

    private String username = "hej";
    private String password = "hejhej";
    private UserActivity userActivity;
    private fragment_income fragmentIncome;
    private fragment_expenditure fragmentExpenditure;
    private fragment_user fragmentUser;
    private fragment_main fragmentMain;
    private fragment_search fragmentSearch;
    private PieChartHandler pieChartHandler;
    private int currentFragment = 0;
    private DatabaseIF db;
    private String[] toggleStates = new String[2];


    public Controller(){

    }

    public Controller(UserActivity userActivity){

        this.userActivity = userActivity;
        this.fragmentIncome = new fragment_income();
        this.fragmentExpenditure = new fragment_expenditure();
        this.fragmentUser = new fragment_user();
        this.fragmentMain = new fragment_main();
        this.fragmentSearch = new fragment_search();
        this.pieChartHandler = new PieChartHandler();
        this.db = new DatabaseIF();

        fragmentIncome.setController(this);
        fragmentExpenditure.setController(this);
        fragmentUser.setController(this);
        fragmentMain.setController(this);
        fragmentSearch.setController(this);
        initializeResources();
        userActivity.setFragment(fragmentMain, true);
    }

    private void initializeResources() {
        Resources res = userActivity.getResources();
        toggleStates[0] = res.getString(R.string.toggleBtnOff);
        toggleStates[1] = res.getString(R.string.toggleBtnOn);

    }


    public void signUp(){

    }

    public void setSpinnerChoice(){
        fragmentSearch.setSpinnerChoice();
    }

    public boolean login(String username, String password) {

        if(this.username.equals(username) && this.password.equals(password)){
            return true;
        }else {
            return false;
        }
    }

    public void updateUsername(){
        fragmentMain.setUserName("Welcome " + username);
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
            }
        }
        currentFragment = position;
    }

    public void addChartData(Boolean state) {
        ArrayList<Transaction> transactions;
        PieData pieData;
        if(!state){
            transactions = db.getExpenditures();
            pieData = pieChartHandler.mapExpenditure(transactions);
            fragmentMain.setTbOn(toggleStates[1]);
        }else{
            transactions = db.getIncome();
            pieData = pieChartHandler.mapIncome(transactions);
            fragmentMain.setTbOff(toggleStates[0]);
        }


        fragmentMain.setPieChartData(pieData);

    }

    public void setTransactionAdapter() {
        if(currentFragment == 3){
            fragmentExpenditure.setAdapter(db.getExpenditures());
        }else{
            fragmentIncome.setAdapter(db.getIncome());
        }

    }
}
