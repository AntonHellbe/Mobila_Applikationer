package com.example.anton.assignment1;


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
    private MainActivity mainActivity;
    private FragmentIncome fragmentIncome;
    private FragmentExpenditure fragmentExpenditure;
    private FragmentUser fragmentUser;
    private FragmentMain fragmentMain;
    private FragmentSearch fragmentSearch;
    private FragmentAdd fragmentAdd;
    private FragmentLogin fragmentLogin;
    private FragmentSignup fragmentSignup;
    private PieChartHandler pieChartHandler;

    private int currentFragment = 0;
    private int currentFragmentMain = 0;
    private String dateSelected = "Year";
    private DatabaseIF db;
    private User currentUser;
    private String[] toggleStates = new String[2];
    private int spinnerCategory;
    private int spinnerCategory1;
    private String customDateFrom = "";
    private String customDateTo = "";


    public Controller(MainActivity mainActivity){
        this.fragmentLogin = new FragmentLogin();
        this.fragmentSignup = new FragmentSignup();
        this.mainActivity = mainActivity;

        fragmentSignup.setController(this);
        fragmentLogin.setController(this);
        this.db = new DatabaseIF(mainActivity, this);

        mainActivity.setFragment(fragmentLogin, true);


    }

    public Controller(UserActivity userActivity){
        this.userActivity = userActivity;
        this.fragmentMain = new FragmentMain();
        this.fragmentIncome = new FragmentIncome();
        this.fragmentExpenditure = new FragmentExpenditure();
        this.fragmentUser = new FragmentUser();
        this.fragmentSearch = new FragmentSearch();
        this.fragmentAdd = new FragmentAdd();

        this.pieChartHandler = new PieChartHandler();
        this.db = new DatabaseIF(userActivity, this);

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


    public String signUp(){
        String result = "";

        if(fragmentSignup.getUsername().toString().equals("") || fragmentSignup.getName().toString().equals("") ||
                fragmentSignup.getLastName().toString().equals("") || fragmentSignup.getPassword().toString().equals("")) return result = "Please fill in all fields";

        Boolean userAdded = db.addUser(fragmentSignup.getUsername().toString(), fragmentSignup.getName().toString(),
                fragmentSignup.getLastName().toString(), fragmentSignup.getPassword().toString());

        if(userAdded){
            result = "User added";

        }else {
            result = "User already exist!";
        }


        return result;
    }

    public boolean login(String username, String password) {

        User user = db.loginUser(username, password);

        if(user != null){
            currentUser = user;
            return true;

        }else {
            return false;
        }
    }

    public void updateUsername(){
        fragmentMain.setUserName("Welcome " + currentUser.getUsername());
    }

    public void setCustomDateFrom(String customDateFrom) {
        this.customDateFrom = customDateFrom;
        fragmentSearch.setTvFromDate("From: " + customDateFrom);
    }

    public void setCustomDateTo(String customDateTo) {
        this.customDateTo = customDateTo;
        fragmentSearch.setTvToDate("To: " + customDateTo);
    }

    public String getCustomDateFrom() {
        return customDateFrom;
    }

    public String getCustomDateTo() {
        return customDateTo;
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
        PieData pieData;
        if(!state){
            pieData = pieChartHandler.mapExpenditure(db.getExpenditureRange(dateSelected));
            fragmentMain.setTbOn(toggleStates[1]);
        }else{

            pieData = pieChartHandler.mapIncome(db.getIncomeRange(dateSelected));
        }
            fragmentMain.setTbOff(toggleStates[0]);

        pieData.setValueTextSize(10f);
        fragmentMain.setPieChartData(pieData);

    }

    public void setAddFragment(){
        userActivity.setFragment(fragmentAdd, true);
        currentFragment = 5;
    }



    public void setTransactionAdapter() {
        if(currentFragment == 3){
            fragmentExpenditure.setAdapter(db.getExpenditureRange(dateSelected));
        }else{
            fragmentIncome.setAdapter(db.getIncomeRange(dateSelected));
        }

    }

    public void dateSelect(String choice) {

        if(choice.equals(dateSelected)){

        }else if(choice.equals("Other")){
            fragmentSearch.startDatePicker();
            dateSelected = choice;
        }else{
             dateSelected = choice;
        }

        setTvDatesSelected();

    }

    public void setTvDatesSelected(){
        switch(dateSelected){
            case "Year":
                fragmentSearch.setTvFromDate("From: " + getCalculatedDate(0));
                fragmentSearch.setTvToDate("To: " + getCalculatedDate(-365));
                break;
            case "Month":
                fragmentSearch.setTvFromDate("From: " + getCalculatedDate(0));
                fragmentSearch.setTvToDate("To: " + getCalculatedDate(-31));
                break;
            case "Week":
                fragmentSearch.setTvFromDate("From: " + getCalculatedDate(0));
                fragmentSearch.setTvToDate("To: " + getCalculatedDate(-7));
                break;
            case "Other":
                fragmentSearch.setTvFromDate("From: " + customDateFrom);
                fragmentSearch.setTvToDate("To: " + customDateTo);
                break;
        }
    }


    public String editUser(String username, String name, String lastname, String password) {
        String result = "";
        User updatedUser;

        if (username.equals(currentUser.getUsername()) && name.equals(currentUser.getName()) && lastname.equals(currentUser.getLastname())
                && password.equals(currentUser.getPassword())) {
            return result = "No fields updated!";
        }
        updatedUser = new User(username, name, lastname, password);
        result = "User updated";

        if(!updatedUser.getUsername().equals(currentUser.getUsername())) {
            userActivity.updateNavDrawer(updatedUser.getUsername());
        }

        db.editUser(currentUser, updatedUser);

        currentUser = updatedUser;

        return result;
    }


    public String getCurrentUserName(){
        return currentUser.getUsername();
    }


    public Bundle saveInformation(Bundle outState) {
        outState.putInt("currentFragment", currentFragment);
        outState.putBoolean("piechart", fragmentMain.getState());
        outState.putParcelable("currentUser", currentUser);

        return outState;
    }

    public void rescueMission(Bundle savedInstanceState) {
        currentUser = savedInstanceState.getParcelable("currentUser");
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


    public void addTransaction(Transaction trans) {
        db.addTransaction(trans);
    }

    public void moveBack(String expense) {
        switch(expense){
            case "Income":
                changeFragment(2);
                break;
            case "Expenditure":
                changeFragment(3);
                break;
        }
    }

    public void clearOptions() {
        fragmentAdd.clearRadioGroup();
        fragmentAdd.setBtnDate("YYYY-MM-DD");
        fragmentAdd.setEtAmount("");
        fragmentAdd.setEtTitle("");
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

    public String padDate(int year, int month, int day){
        String strDay = Integer.toString(day);
        String strMonth = Integer.toString(month + 1);
        if(day < 10){
            strDay = String.format("%02d", day);
        }
        if(month < 10){
            strMonth = String.format("%02d", (month + 1));
        }

        return year + "-" + strMonth + "-" + strDay;
    }

    public void swapMainFragment(int pos){
        switch(pos){
            case 0:
                mainActivity.setFragment(fragmentLogin, true);
                currentFragmentMain = pos;
                break;
            case 1:
                mainActivity.setFragment(fragmentSignup, true);
                currentFragmentMain = pos;
                break;
        }
    }


    public void createUser(Intent intent) {
        currentUser = intent.getExtras().getParcelable("currentUser");
    }

    public void startUserActivity() {
        mainActivity.startIntent(currentUser);
    }

    public void setUserDetails() {
        fragmentUser.setEtUsername(currentUser.getUsername());
        fragmentUser.setEtName(currentUser.getName());
        fragmentUser.setEtLastname(currentUser.getLastname());
        fragmentUser.setEtPassword(currentUser.getPassword());
    }

    public void clearFields() {
        fragmentSignup.setEtUsername("");
        fragmentSignup.setEtName("");
        fragmentSignup.setEtLastname("");
        fragmentSignup.setEtPassword("");
    }

    public Bundle saveInformationMainActivity(Bundle outState) {
        outState.putInt("currentFragmentMain", currentFragmentMain);
        switch(currentFragmentMain){
            case 0:
                outState.putString("username", fragmentLogin.getEtUsername());
                outState.putString("password", fragmentLogin.getEtPassword());
                break;
            case 1:
                outState.putString("username", fragmentSignup.getUsername());
                outState.putString("name", fragmentSignup.getName());
                outState.putString("lastname", fragmentSignup.getLastName());
                outState.putString("password", fragmentSignup.getPassword());
                break;
        }

        return outState;
    }

    public void setRestoredInformation(Bundle restoredInformation) {
        currentFragmentMain = restoredInformation.getInt("currentFragmentMain");
        switch(currentFragmentMain){
            case 0:
                fragmentLogin.setEtUsername(restoredInformation.getString("username"));
                fragmentLogin.setEtPassword(restoredInformation.getString("password"));
                break;
            case 1:
                fragmentSignup.setEtUsername(restoredInformation.getString("username"));
                fragmentSignup.setEtName(restoredInformation.getString("name"));
                fragmentSignup.setEtLastname(restoredInformation.getString("lastname"));
                fragmentSignup.setEtPassword(restoredInformation.getString("password"));
                break;
        }
        swapMainFragment(currentFragmentMain);
    }
}
