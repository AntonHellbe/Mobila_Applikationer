package com.example.anton.assignment1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Anton on 2017-09-14.
 */

public class TransactionDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "transactions";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_USERID = "userid";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";

    private static final String DATABASE_NAME = "transaction.db";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CREATE =
            "create table " + TABLE_NAME + " (" + COLUMN_ID + " integer primary key autoincrement not null," +
                    COLUMN_TYPE + " text not null," + COLUMN_TITLE  + " text not null," + COLUMN_USERID + " text not null, " + COLUMN_AMOUNT + " float not null," +
                    COLUMN_CATEGORY + " text not null," + COLUMN_DATE + " text not null);";


    public TransactionDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TransactionDBHelper.class.getName(), "Upgrading database" + oldVersion + " to " + newVersion + " ,which destory all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
