package com.example.anton.assignment1;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Float.parseFloat;

public class Controller {

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
    private FragmentTransaction fragmentTransaction;
    private PieChartHandler pieChartHandler;
    private String fromFragment = "";
    private Boolean informationSaved = false;

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
    private int transactionId = 0;
    private Transaction selectedTransaction;
    private float incomeBalance = 0;
    private float expenditureBalance = 0;

    private ArrayList<String> rescuedUserInformation = null;
    private ArrayList<String> rescuedMainInformation = null;


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
        this.fragmentTransaction = new FragmentTransaction();

        this.pieChartHandler = new PieChartHandler();
        this.db = new DatabaseIF(userActivity, this);

        fragmentTransaction.setController(this);
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

    public int getCurrentFragment(){
        return this.currentFragment;
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
        switch (position) {
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
                userActivity.startBarCodeActivity();
                break;
            case 6:
                userActivity.setFragment(fragmentAdd, true);
                break;
            case 7:
                userActivity.setFragment(fragmentTransaction, true);
                break;
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


        ArrayList<Transaction> trans = db.getAllTransactions(currentUser.getUsername());
        for(Transaction tran: trans){
            if(tran.getType().equals("Income")){
                incomeBalance += tran.getAmount();
            }else{
                expenditureBalance -= tran.getAmount();
            }
        }
        fragmentMain.setTvBalanceExpenditure(String.valueOf(expenditureBalance));
        fragmentMain.setTvBalanceIncome(String.valueOf(incomeBalance));
        fragmentMain.setTotalBalance(String.valueOf(incomeBalance + expenditureBalance));
        incomeBalance = 0;
        expenditureBalance = 0;

        pieData.setValueTextSize(10f);
        fragmentMain.setPieChartData(pieData);

    }

    public void setAddFragment(){
        if(currentFragment == 3){
            fromFragment = "Expenditure";
        }else{
            fromFragment = "Income";
        }
        currentFragment = 6;
        changeFragment(currentFragment);
    }

    public void setFragmentTransaction(){
        if(currentFragment == 3){
            fromFragment = "Expenditure";
        }else{
            fromFragment = "Income";
        }

        currentFragment = 7;
        changeFragment(currentFragment);
    }



    public void setTransactionAdapter() {
        if (!fromFragment.equals("")) {
            if (fromFragment.equals("Expenditure")) {
                currentFragment = 3;
            } else if (fromFragment.equals("Income")) {
                currentFragment = 2;
            }
        }

        if (currentFragment == 3) {
            fragmentExpenditure.setAdapter(db.getExpenditureRange(dateSelected));
        }else if(currentFragment == 2){
            fragmentIncome.setAdapter(db.getIncomeRange(dateSelected));
        }

        fromFragment = "";
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
            case "Today":
                fragmentSearch.setTvFromDate("From: " + getCalculatedDate(0));
                fragmentSearch.setTvToDate("To: " + getCalculatedDate(0));
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
        outState.putParcelable("selectedTransaction", selectedTransaction);
        outState.putInt("transactionId", transactionId);
        outState.putString("dateSelected", dateSelected);
        outState.putString("customDateFrom", customDateFrom);
        outState.putString("customDateTo", customDateTo);

        if(currentFragment == 6){
            outState.putInt("rbChecked", fragmentAdd.getCheckId());
            outState.putString("title", fragmentAdd.getEtTitle());
            outState.putString("amount", fragmentAdd.getEtAmount());
            outState.putString("date", fragmentAdd.getBtnDate());
            outState.putString("barcodeid", fragmentAdd.getEtBarCodeIt());
            outState.putBoolean("savedInformation", informationSaved = true);
            if(fragmentAdd.getsCategoryObject() != null){
                outState.putString("category", fragmentAdd.getsCategory());
            }else{
                outState.putString("category", "null");
            }

        }

        return outState;
    }

    public void rescueMission(Bundle savedInstanceState) {
        currentUser = savedInstanceState.getParcelable("currentUser");
        fragmentMain.setState(savedInstanceState.getBoolean("piechart"));
        changeFragment(savedInstanceState.getInt("currentFragment"));
        dateSelected = savedInstanceState.getString("dateSelected");
        informationSaved = savedInstanceState.getBoolean("savedInformation");
        selectedTransaction = savedInstanceState.getParcelable("selectedTransaction");
        transactionId = savedInstanceState.getInt("transactionId");

        if(dateSelected.equals("Other")){
            customDateFrom = savedInstanceState.getString("customDateFrom");
            customDateTo = savedInstanceState.getString("customDateTo");
        }

        if(informationSaved){
            rescuedUserInformation = new ArrayList<>();
            rescuedUserInformation.add(Integer.toString(savedInstanceState.getInt("rbChecked")));
            rescuedUserInformation.add(savedInstanceState.getString("title"));
            rescuedUserInformation.add(savedInstanceState.getString("amount"));
            rescuedUserInformation.add(savedInstanceState.getString("date"));
            rescuedUserInformation.add(savedInstanceState.getString("barcodeid"));
            if(!savedInstanceState.getString("category").equals("null")){
                switch(Integer.parseInt(rescuedUserInformation.get(0))){
                    case R.id.rbInc:
                        switch (savedInstanceState.getString("category")){
                            case "Salary":
                                rescuedUserInformation.add(String.valueOf(0));
                                break;
                            case "Other":
                                rescuedUserInformation.add(String.valueOf(1));
                                break;
                        }
                        break;
                    case R.id.rbExpend:
                        switch (savedInstanceState.getString("category")){
                            case "Travel":
                                rescuedUserInformation.add(String.valueOf(0));
                                break;
                            case "Leisure":
                                rescuedUserInformation.add(String.valueOf(1));
                                break;
                            case "Food":
                                rescuedUserInformation.add(String.valueOf(2));
                                break;
                            case "Other":
                                rescuedUserInformation.add(String.valueOf(3));
                                break;
                            case "Accommodation":
                                rescuedUserInformation.add(String.valueOf(4));
                                break;
                        }
                        break;
                }
            }else{
                rescuedUserInformation.add("null");
            }
        }
        informationSaved = false;

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


    public Boolean addTransaction() {
        if(fragmentAdd.getExpense().equals("") || fragmentAdd.getEtTitle().equals("") ||
                fragmentAdd.getEtAmount().equals("") || fragmentAdd.getsCategory().equals("") || fragmentAdd.getBtnDate().equals("YYYY-MM-DD")){
            return false;
        }

        try{
            Transaction trans = new Transaction(fragmentAdd.getExpense(), fragmentAdd.getEtTitle(), currentUser.getUsername(),
                    parseFloat(fragmentAdd.getEtAmount()), fragmentAdd.getsCategory(), fragmentAdd.getBtnDate());
            db.addTransaction(trans);
        }catch(Exception e){
            return false;
        }

        if(!fragmentAdd.getEtBarCodeIt().equals("Press to scan barcode")){
            BarCode barCode = new BarCode(fragmentAdd.getEtBarCodeIt(), fragmentAdd.getEtTitle(), fragmentAdd.getsCategory(), parseFloat(fragmentAdd.getEtAmount()));
            if(db.addBarCode(barCode)){

            }
        }

        return true;
    }

    public void moveBack(String expense) {
        switch(expense){
            case "Income":
                currentFragment = 2;
                userActivity.setFragment(fragmentIncome, true);
                break;
            case "Expenditure":
                currentFragment = 3;
                userActivity.setFragment(fragmentExpenditure, true);
                break;
        }
    }

    public void clearOptions() {
        fragmentAdd.clearRadioGroup();
        fragmentAdd.setBtnDate("YYYY-MM-DD");
        fragmentAdd.setEtAmount("");
        fragmentAdd.setEtTitle("");
        fragmentAdd.setEtBarCodeId("Press to scan barcode");
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
                currentFragmentMain = pos;
                mainActivity.setFragment(fragmentLogin, true);
                break;
            case 1:
                currentFragmentMain = pos;
                mainActivity.setFragment(fragmentSignup, true);
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
        Log.e("currentFragmentMain", currentFragmentMain + "");
        outState.putInt("currentFragmentMain", currentFragmentMain);
        informationSaved = true;
        outState.putBoolean("savedInformation", informationSaved);
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
        informationSaved = restoredInformation.getBoolean("savedInformation");
        rescuedMainInformation = new ArrayList<>();
        switch(currentFragmentMain){
            case 0:
                rescuedMainInformation.add(restoredInformation.getString("username"));
                rescuedMainInformation.add(restoredInformation.getString("password"));
                break;
            case 1:
                rescuedMainInformation.add(restoredInformation.getString("username"));
                rescuedMainInformation.add(restoredInformation.getString("name"));
                rescuedMainInformation.add(restoredInformation.getString("lastname"));
                rescuedMainInformation.add(restoredInformation.getString("password"));
                break;
        }
        swapMainFragment(currentFragmentMain);
    }


    public void updateBarCodeInformation(Intent data) {
        String id = data.getStringExtra("SCAN_RESULT");
        BarCode barcode = db.getBarCode(id);
        if(barcode != null){
            fragmentAdd.setBarCode(barcode);
        }else{
            fragmentAdd.setBarCodeId(id);
        }

    }

    public BarCode setBarCodeInformation(BarCode barCode) {
        if(barCode != null){
            fragmentAdd.setEtTitle(barCode.getTitle());
            fragmentAdd.setEtAmount(String.valueOf(barCode.getAmount()));
            fragmentAdd.checkExpenditure();
            fragmentAdd.setExpense("Expenditure");
            fragmentAdd.setEtBarCodeId(barCode.getId());

            switch(barCode.getCategory()){
                case "Travel":
                    fragmentAdd.setsCategory(0);
                    break;
                case "Leisure":
                    fragmentAdd.setsCategory(1);
                    break;
                case "Food":
                    fragmentAdd.setsCategory(2);
                    break;
                case "Other":
                    fragmentAdd.setsCategory(3);
                    break;
                case "Accommodation":
                    fragmentAdd.setsCategory(4);
                    break;
            }

        }

        setFromFragment();

        return barCode = null;
    }

    public String setBarId(String id){
        if(!id.equals("")){
            fragmentAdd.setEtBarCodeId(id);
        }
        return id = "";
    }

    public void setSearchSpinner() {
        switch(dateSelected){
            case "Year":
                fragmentSearch.setSpinner(0);
                break;
            case "Month":
                fragmentSearch.setSpinner(1);
                break;
            case "Week":
                fragmentSearch.setSpinner(2);
                break;
            case "Today":
                fragmentSearch.setSpinner(3);
                break;
            case "Other":
                fragmentSearch.setSpinner(4);
                break;
        }
    }

    public void setFromFragment(){
        this.fromFragment = "";
    }

    public void startBarCodeActivity(){
        userActivity.startBarCodeActivity();
    }

    public void possibleRescueMission() {
        if(rescuedUserInformation != null){
            fragmentAdd.setRgType(Integer.parseInt(rescuedUserInformation.get(0)));;
            fragmentAdd.setEtTitle(rescuedUserInformation.get(1));
            fragmentAdd.setEtAmount(rescuedUserInformation.get(2));
            fragmentAdd.setBtnDate(rescuedUserInformation.get(3));
            fragmentAdd.setEtBarCodeId(rescuedUserInformation.get(4));
            if(!rescuedUserInformation.get(5).equals("null")){
                fragmentAdd.setsCategory(Integer.parseInt(rescuedUserInformation.get(5)));
            }
            rescuedUserInformation = null;
        }

    }

    public void setTransactionId(String id){
        this.transactionId = Integer.parseInt(id);
        this.selectedTransaction = db.getTransaction(transactionId);
    }

    public void setTransactionInformation() {

        fragmentTransaction.setTvDisplayTitle(selectedTransaction.getTitle());
        fragmentTransaction.setTvDisplayType(selectedTransaction.getType());
        fragmentTransaction.setTvDisplayCategory(selectedTransaction.getCategory());
        fragmentTransaction.setTvDisplayAmount(String.valueOf(selectedTransaction.getAmount()));
        fragmentTransaction.setTvDisplayDate(selectedTransaction.getDate());
    }

    public void restoreLoginInformation() {

        if(informationSaved){
            fragmentLogin.setEtUsername(rescuedMainInformation.get(0));
            fragmentLogin.setEtPassword(rescuedMainInformation.get(1));
            informationSaved = false;
            rescuedMainInformation = null;
        }

    }

    public void restoreSignupInformation() {
        if(informationSaved){
            fragmentSignup.setEtUsername(rescuedMainInformation.get(0));
            fragmentSignup.setEtName(rescuedMainInformation.get(1));
            fragmentSignup.setEtLastname(rescuedMainInformation.get(2));
            fragmentSignup.setEtPassword(rescuedMainInformation.get(3));
            informationSaved = false;
            rescuedMainInformation = null;
        }
    }

    public void finishTransaction(Boolean result) {
        if(result){
            Toast.makeText(userActivity, "Transaction added!", Toast.LENGTH_SHORT).show();
            moveBack(fragmentAdd.getExpense());
            clearOptions();
        }else{
            Toast.makeText(userActivity, "One or more fields missing!", Toast.LENGTH_SHORT).show();
        }
    }
}

