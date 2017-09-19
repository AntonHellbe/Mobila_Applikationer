package com.example.anton.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Anton on 2017-09-13.
 */

public class DatabaseIF {

    private TransactionDBHelper transactionDBHelper;
    private UserDBHelper userDBHelper;
    private BarCodeDBHelper barCodeDBHelper;
    private Controller controller;

    public DatabaseIF(Context context, Controller controller){
        this.transactionDBHelper = new TransactionDBHelper(context);
        this.userDBHelper = new UserDBHelper(context);
        this.barCodeDBHelper = new BarCodeDBHelper(context);
        this.controller = controller;
    }

    public void testData(){
        ArrayList<Transaction> transz = new ArrayList<>();
        Transaction trans = new Transaction("Income", "Testing1", "JohnDoe", 50.45f, "Salary", "2017-09-07");
        Transaction trans1 = new Transaction("Expenditure", "Testing2", "JohnDoe", 60.45f, "Food", "2017-09-05");
        Transaction trans2 = new Transaction("Expenditure", "Testing3", "JohnDoe", 70.45f, "Leisure", "2017-05-10");
        Transaction trans3 = new Transaction("Income", "Testing4", "JohnDoe", 77.45f, "Other", "2017-05-20");
        Transaction trans4 = new Transaction("Expenditure", "Testing5", "JohnDoe", 23.45f, "Accommodation", "2017-03-15");
        Transaction trans5 = new Transaction("Income", "Testing6", "JohnDoe", 25.45f, "Salary", "2017-01-01");
        Transaction trans6 = new Transaction("Expenditure", "Testing7", "JohnDoe", 43.45f, "Travel", "2017-06-10");
        Transaction trans7 = new Transaction("Income", "Testing8", "JohnDoe", 123.45f, "Other", "2017-03-03");
        transz.add(trans);
        transz.add(trans1);
        transz.add(trans2);
        transz.add(trans3);
        transz.add(trans4);
        transz.add(trans5);
        transz.add(trans6);
        transz.add(trans7);

        addTransactionList(transz);
    }

    public void addTransactionList(ArrayList<Transaction> transaction){
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
        db.insert(TransactionDBHelper.TABLE_NAME, "", values);
    }

    public ArrayList<Transaction> getIncomeRange(String range){
        SQLiteDatabase db = transactionDBHelper.getReadableDatabase();

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
                break;
            case "Other":
                fromDate = controller.getCustomDateFrom();
                toDate = controller.getCustomDateTo();
        }

        Cursor cursor = db.rawQuery("SELECT * FROM " + TransactionDBHelper.TABLE_NAME + " WHERE " + TransactionDBHelper.COLUMN_TYPE + " = ?" + " AND " + TransactionDBHelper.COLUMN_USERID + " = ?" + " AND "
                +   "? >= " + TransactionDBHelper.COLUMN_DATE + " AND " + "? <= " + TransactionDBHelper.COLUMN_DATE, new String[]{"Income", controller.getCurrentUserName(), fromDate, toDate});
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
            Transaction trans = new Transaction(cursor.getInt(idIndex), cursor.getString(typeIndex), cursor.getString(titleIndex), cursor.getString(useridIndex),
                    cursor.getFloat(amountIndex), cursor.getString(categoryIndex), cursor.getString(dateIndex));
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
                break;
            case "Today":
                fromDate = getCalculatedDate(0);
                toDate = getCalculatedDate(0);
                break;
            case "Other":
                fromDate = controller.getCustomDateFrom();
                toDate = controller.getCustomDateTo();
        }

        SQLiteDatabase db = transactionDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TransactionDBHelper.TABLE_NAME + " WHERE " + TransactionDBHelper.COLUMN_TYPE + " = ?" + " AND " + TransactionDBHelper.COLUMN_USERID + " = ?" + " AND "
                +   "? >= " + TransactionDBHelper.COLUMN_DATE + " AND " + "? <= " + TransactionDBHelper.COLUMN_DATE, new String[]{"Expenditure", controller.getCurrentUserName(), fromDate, toDate});
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
            Transaction trans = new Transaction(cursor.getInt(idIndex), cursor.getString(typeIndex), cursor.getString(titleIndex), cursor.getString(useridIndex),
                    cursor.getFloat(amountIndex), cursor.getString(categoryIndex), cursor.getString(dateIndex));
            transactions.add(trans);
        }
        return transactions;
    }


    public Transaction getTransaction(int id){
        SQLiteDatabase db = transactionDBHelper.getWritableDatabase();
        int idIndex, titleIndex, typeIndex, useridIndex, amountIndex, categoryIndex, dateIndex;

        String query = "SELECT * FROM " + TransactionDBHelper.TABLE_NAME + " WHERE " + TransactionDBHelper.COLUMN_ID + "=" + id;

        Cursor c = db.rawQuery(query, null);

        idIndex = c.getColumnIndex(TransactionDBHelper.COLUMN_ID);
        titleIndex = c.getColumnIndex(TransactionDBHelper.COLUMN_TITLE);
        typeIndex = c.getColumnIndex(TransactionDBHelper.COLUMN_TYPE);
        useridIndex = c.getColumnIndex(TransactionDBHelper.COLUMN_USERID);
        amountIndex = c.getColumnIndex(TransactionDBHelper.COLUMN_AMOUNT);
        categoryIndex = c.getColumnIndex(TransactionDBHelper.COLUMN_CATEGORY);
        dateIndex = c.getColumnIndex(TransactionDBHelper.COLUMN_DATE);

        if(c != null) c.moveToFirst();


        Transaction transaction = new Transaction(c.getInt(idIndex), c.getString(typeIndex), c.getString(titleIndex), c.getString(useridIndex), c.getFloat(amountIndex),
                c.getString(categoryIndex), c.getString(dateIndex));


        return transaction;

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



    public Boolean addUser(String username, String name, String lastname, String password){
        SQLiteDatabase db = userDBHelper.getWritableDatabase();
//        userDBHelper.onCreate(db);

        String query = "SELECT * FROM " + UserDBHelper.TABLE_NAME + " WHERE " + UserDBHelper.COLUMN_USERID + " = ?";
        Cursor c = db.rawQuery(query, new String[]{username});

        if(c.getCount() > 0){
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(UserDBHelper.COLUMN_USERID, username);
        values.put(UserDBHelper.COLUMN_NAME, name);
        values.put(UserDBHelper.COLUMN_LASTNAME, lastname);
        values.put(UserDBHelper.COLUMN_PASSWORD, password);
        db.insert(UserDBHelper.TABLE_NAME, "", values);
        return true;
    }

    public Boolean editUser(User oldUser, User newUser){
        SQLiteDatabase db = userDBHelper.getWritableDatabase();
        if(!oldUser.getUsername().equalsIgnoreCase(newUser.getUsername())){
            ArrayList<Transaction> transactions = getAllTransactions(oldUser.getUsername());
            for(Transaction trans: transactions){
                trans.setUserid(newUser.getUsername());
            }
            addTransactionList(transactions);
        }

        ContentValues values = new ContentValues();
        values.put(UserDBHelper.COLUMN_USERID, newUser.getUsername());
        values.put(UserDBHelper.COLUMN_NAME, newUser.getName());
        values.put(UserDBHelper.COLUMN_LASTNAME, newUser.getLastname());
        values.put(UserDBHelper.COLUMN_PASSWORD, newUser.getPassword());
        db.update(UserDBHelper.TABLE_NAME, values, UserDBHelper.COLUMN_ID + " = " + oldUser.getId(),null);
        return true;
    }

    public User loginUser(String username, String password){
        SQLiteDatabase db = userDBHelper.getWritableDatabase();

//        SQLiteDatabase db2 = barCodeDBHelper.getWritableDatabase();
//        SQLiteDatabase db3 = transactionDBHelper.getWritableDatabase();
//        transactionDBHelper.onUpgrade(db3, 1, 2);
//        barCodeDBHelper.onUpgrade(db2, 1, 2);

        int idIndex, useridIndex, nameIndex, lastnameIndex, passwordIndex;

        String query = "SELECT * FROM " + UserDBHelper.TABLE_NAME + " WHERE " + UserDBHelper.COLUMN_USERID + "= ?" + " AND " + UserDBHelper.COLUMN_PASSWORD + " = ?" ;

        Cursor c = db.rawQuery(query, new String[]{username, password});

        idIndex = c.getColumnIndex(UserDBHelper.COLUMN_ID);
        useridIndex = c.getColumnIndex(UserDBHelper.COLUMN_USERID);
        nameIndex = c.getColumnIndex(UserDBHelper.COLUMN_NAME);
        lastnameIndex = c.getColumnIndex(UserDBHelper.COLUMN_LASTNAME);
        passwordIndex = c.getColumnIndex(UserDBHelper.COLUMN_PASSWORD);

        User user;
        if(c != null && c.moveToFirst()) {
            c.moveToFirst();
            user = new User(c.getInt(idIndex), c.getString(useridIndex), c.getString(nameIndex), c.getString(lastnameIndex), c.getString(passwordIndex));
            c.close();
        }else{
            user = null;
        }



        return user;

    }

    public ArrayList<Transaction> getAllTransactions(String username){
        SQLiteDatabase db = transactionDBHelper.getReadableDatabase();

        int idIndex, titleIndex, typeIndex, useridIndex, amountIndex, categoryIndex, dateIndex;

        Cursor cursor = db.rawQuery("SELECT * FROM " + TransactionDBHelper.TABLE_NAME + " WHERE " + TransactionDBHelper.COLUMN_USERID + " = ?", new String[]{username});
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
            Transaction trans = new Transaction(cursor.getInt(idIndex), cursor.getString(typeIndex), cursor.getString(titleIndex), cursor.getString(useridIndex),
                    cursor.getFloat(amountIndex), cursor.getString(categoryIndex), cursor.getString(dateIndex));
            transactions.add(trans);
        }
        return transactions;

    }

    public Boolean addBarCode(BarCode barCode){
        SQLiteDatabase db = barCodeDBHelper.getWritableDatabase();

        String query = "SELECT * FROM " + BarCodeDBHelper.TABLE_NAME + " WHERE " + BarCodeDBHelper.COLUMN_ID + " = ? ";
        Cursor c = db.rawQuery(query, new String[]{barCode.getId()});

        if(c.getCount() > 0){
            return false;
        }

        Log.e("AddbarCode", barCode.getId() + barCode.getCategory() + barCode.getTitle() + barCode.getAmount());

//        barCodeDBHelper.onCreate(db);
        ContentValues values = new ContentValues();
        values.put(BarCodeDBHelper.COLUMN_ID, barCode.getId());
        values.put(BarCodeDBHelper.COLUMN_TITLE, barCode.getTitle());
        values.put(BarCodeDBHelper.COLUMN_CATEGORY, barCode.getCategory());
        values.put(BarCodeDBHelper.COLUMN_AMOUNT, barCode.getAmount());
        db.insert(BarCodeDBHelper.TABLE_NAME, "", values);

        return true;
    }

    public void addBarCodeList(ArrayList<BarCode> barCodes){
        SQLiteDatabase db = barCodeDBHelper.getWritableDatabase();
//        barCodeDBHelper.onCreate(db);
        for(BarCode barcode: barCodes){
            ContentValues values = new ContentValues();
            values.put(BarCodeDBHelper.COLUMN_ID, barcode.getId());
            values.put(BarCodeDBHelper.COLUMN_TITLE, barcode.getTitle());
            values.put(BarCodeDBHelper.COLUMN_CATEGORY, barcode.getCategory());
            values.put(BarCodeDBHelper.COLUMN_AMOUNT, barcode.getAmount());
            db.insert(BarCodeDBHelper.TABLE_NAME, "", values);
        }

    }

    public BarCode getBarCode(String id){
        SQLiteDatabase db = barCodeDBHelper.getWritableDatabase();

//        BarCode barCode1 = new BarCode("7350015501287", "snus","Leisure", 35.45f);
//        addBarCode(barCode1);
        Log.e("GetBarCodeId" , id);
        int idIndex, titleIndex, categoryIndex, amountIndex;

        String query = "SELECT * FROM " + BarCodeDBHelper.TABLE_NAME + " WHERE " + BarCodeDBHelper.COLUMN_ID + " = ?";

        Cursor c = db.rawQuery(query, new String[]{id});

        idIndex = c.getColumnIndex(BarCodeDBHelper.COLUMN_ID);
        titleIndex = c.getColumnIndex(BarCodeDBHelper.COLUMN_TITLE);
        amountIndex = c.getColumnIndex(BarCodeDBHelper.COLUMN_AMOUNT);
        categoryIndex = c.getColumnIndex(BarCodeDBHelper.COLUMN_CATEGORY);

        BarCode barCode;


        if(c != null && c.moveToFirst()) {
            c.moveToFirst();
            barCode = new BarCode(c.getString(idIndex), c.getString(titleIndex),c.getString(categoryIndex) , c.getFloat(amountIndex));
            c.close();
        }else{
            barCode = null;
        }

        Log.e("Got following barcode", "" + barCode);

        return barCode;

    }



}
