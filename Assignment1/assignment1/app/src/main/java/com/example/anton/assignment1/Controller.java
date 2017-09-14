package com.example.anton.assignment1;


import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.data.PieData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private TransactionDBHelper transactionDBHelper;


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
        this.transactionDBHelper = new TransactionDBHelper(userActivity);

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
                    pieData = pieChartHandler.mapExpenditure(getExpenditureRange("Year"));
                    break;
                case 1:
                    pieData = pieChartHandler.mapExpenditure(getExpenditureRange("Month"));
                    break;
                case 2:
                    pieData = pieChartHandler.mapExpenditure(getExpenditureRange("Week"));
                    break;
            }

            fragmentMain.setTbOn(toggleStates[1]);
        }else{
            switch(dateSelected){
                case 0:
                    pieData = pieChartHandler.mapIncome(getIncomeRange("Year"));
                    break;
                case 1:
                    pieData = pieChartHandler.mapIncome(getIncomeRange("Month"));
                    break;
                case 2:
                    pieData = pieChartHandler.mapIncome(getIncomeRange("Week"));
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
                    fragmentExpenditure.setAdapter(getExpenditureRange("Year"));
                }else{
                    fragmentIncome.setAdapter(getIncomeRange("Year"));
                }
                break;
            case 1:
                if(currentFragment == 3){
                    fragmentExpenditure.setAdapter(getExpenditureRange("Month"));
                }else{
                    fragmentIncome.setAdapter(getIncomeRange("Month"));
                }
                break;
            case 2:
                if(currentFragment == 3){
                    fragmentExpenditure.setAdapter(getExpenditureRange("Week"));
                }else{
                    fragmentIncome.setAdapter(getIncomeRange("Week"));
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

    public void testData(){
        ArrayList<Transaction> transz = new ArrayList<>();
        Transaction trans = new Transaction("Income", "Testing1", "JohnDoe", 50.45f, "Salary", "2017-09-07");
        Transaction trans1 = new Transaction("Expenditure", "Testing2", "JohnDoe", 60.45f, "Food", "2017-09-05");
        Transaction trans2 = new Transaction("Expenditure", "Testing3", "JohnDoe", 70.45f, "Leisure", "2017-05-10");
        Transaction trans3 = new Transaction("Income", "Testing4", "JohnDoe", 77.45f, "Other", "2017-04-20");
        Transaction trans4 = new Transaction("Expenditure", "Testing5", "JohnDoe", 23.45f, "Accomodation", "2017-03-15");
        Transaction trans5 = new Transaction("Income", "Testing6", "JohnDoe", 25.45f, "Salary", "2017-01-01");
        Transaction trans6 = new Transaction("Expenditure", "Testing7", "JohnDoe", 43.45f, "Travel", "2017-10-10");
        Transaction trans7 = new Transaction("Income", "Testing8", "JohnDoe", 123.45f, "Other", "2017-03-03");
        transz.add(trans);
        transz.add(trans1);
        transz.add(trans2);
        transz.add(trans3);
        transz.add(trans4);
        transz.add(trans5);
        transz.add(trans6);
        transz.add(trans7);

        addTransaction2(transz);
    }

    public void addTransaction2(ArrayList<Transaction> transaction){
        SQLiteDatabase db = transactionDBHelper.getWritableDatabase();
        for(int i = 0; i < transaction.size(); i++){
            ContentValues values = new ContentValues();
            values.put(TransactionDBHelper.COLUMN_TYPE, transaction.get(i).getType());
            values.put(TransactionDBHelper.COLUMN_TITLE, transaction.get(i).getTitle());
            values.put(TransactionDBHelper.COLUMN_USERID, transaction.get(i).getUserid());
            values.put(TransactionDBHelper.COLUMN_AMOUNT, transaction.get(i).getAmount());
            values.put(TransactionDBHelper.COLUMN_CATEGORY, transaction.get(i).getCategory());
            values.put(TransactionDBHelper.COLUMN_DATE, transaction.get(i).getDate());
            db.insert(TransactionDBHelper.TABLE_NAME, "", values);
        }
    }

    public void addTransaction(Transaction transaction){
        SQLiteDatabase db = transactionDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TransactionDBHelper.COLUMN_TYPE, transaction.getType());
        values.put(TransactionDBHelper.COLUMN_TITLE, transaction.getTitle());
        values.put(TransactionDBHelper.COLUMN_USERID, transaction.getUserid());
        values.put(TransactionDBHelper.COLUMN_AMOUNT, transaction.getAmount());
        values.put(TransactionDBHelper.COLUMN_CATEGORY, transaction.getCategory());
        values.put(TransactionDBHelper.COLUMN_DATE, transaction.getDate());
        Log.v("Controller", "" + values);
        db.insert(TransactionDBHelper.TABLE_NAME, "", values);
    }

    public ArrayList<Transaction> getIncomeRange(String range){
        SQLiteDatabase db = transactionDBHelper.getWritableDatabase();
        //transactionDBHelper.onUpgrade(db, 1, 2);
        //testData();
        int idIndex, titleIndex, typeIndex, useridIndex, amountIndex, categoryIndex, dateIndex;
        String fromDate = "";
        String toDate = "";
        switch(range){
            case "Year":
                fromDate = getCalculatedDate(0);
                toDate = getCalculatedDate(-365);
                break;
            case "Month":
                fromDate = getCalculatedDate(0);
                toDate = getCalculatedDate(-31);
                break;
            case "Week":
                fromDate = getCalculatedDate(0);
                toDate = getCalculatedDate(-7);
        }


//        transactionDBHelper.onUpgrade(db, 1, 2);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TransactionDBHelper.TABLE_NAME + " WHERE " + TransactionDBHelper.COLUMN_TYPE + " = ?" + " AND " + TransactionDBHelper.COLUMN_USERID + " = ?" + " AND "
                +   "? >= " + TransactionDBHelper.COLUMN_DATE + " AND " + "? <= " + TransactionDBHelper.COLUMN_DATE, new String[]{"Income", getCurrentUserName(), fromDate, toDate});
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        idIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_ID);
        titleIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_TITLE);
        typeIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_TYPE);
        useridIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_USERID);
        amountIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_AMOUNT);
        categoryIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_CATEGORY);
        dateIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_DATE);

        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            Transaction trans = new Transaction(cursor.getString(typeIndex), cursor.getString(titleIndex), cursor.getString(useridIndex),
                    cursor.getFloat(amountIndex), cursor.getString(categoryIndex), cursor.getString(dateIndex));
            trans.setId(cursor.getInt(idIndex));
            transactions.add(trans);
        }
        return transactions;
    }

    public ArrayList<Transaction> getExpenditureRange(String range){

        int idIndex, titleIndex, typeIndex, useridIndex, amountIndex, categoryIndex, dateIndex;
        String fromDate = "";
        String toDate = "";
        switch(range){
            case "Year":
                fromDate = getCalculatedDate(0);
                toDate = getCalculatedDate(-365);
                break;
            case "Month":
                fromDate = getCalculatedDate(0);
                toDate = getCalculatedDate(-31);
                break;
            case "Week":
                fromDate = getCalculatedDate(0);
                toDate = getCalculatedDate(-7);
        }

        SQLiteDatabase db = transactionDBHelper.getWritableDatabase();
//        transactionDBHelper.onUpgrade(db, 1, 2);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TransactionDBHelper.TABLE_NAME + " WHERE " + TransactionDBHelper.COLUMN_TYPE + " = ?" + " AND " + TransactionDBHelper.COLUMN_USERID + " = ?" + " AND "
                +   "? >= " + TransactionDBHelper.COLUMN_DATE + " AND " + "? <= " + TransactionDBHelper.COLUMN_DATE, new String[]{"Expenditure", getCurrentUserName(), fromDate, toDate});
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        idIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_ID);
        titleIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_TITLE);
        typeIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_TYPE);
        useridIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_USERID);
        amountIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_AMOUNT);
        categoryIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_CATEGORY);
        dateIndex = cursor.getColumnIndex(TransactionDBHelper.COLUMN_DATE);

        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            Transaction trans = new Transaction(cursor.getString(typeIndex), cursor.getString(titleIndex), cursor.getString(useridIndex),
                    cursor.getFloat(amountIndex), cursor.getString(categoryIndex), cursor.getString(dateIndex));
            trans.setId(cursor.getInt(idIndex));
            transactions.add(trans);
        }
        return transactions;
    }

    public Controller getInstance(){
        return this;
    }

}
