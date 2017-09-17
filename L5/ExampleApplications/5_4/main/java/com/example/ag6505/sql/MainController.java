package com.example.ag6505.sql;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by tsroax on 08/09/15.
 */
public class MainController {
    public final static String SCORES = "se.mah.tsroax.f5sqlite.SCORES";
    private HighscoreDBHelper dbHelper;
    private MainActivity activity;

    public MainController(MainActivity activity) {
        this.activity = activity;
        dbHelper = new HighscoreDBHelper(activity);
    }


    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(HighscoreDBHelper.TABLE_NAME, null, null);
    }

    public void add100() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE " + HighscoreDBHelper.TABLE_NAME + " " +
                "SET " + HighscoreDBHelper.COLUMN_POINTS + "=" + HighscoreDBHelper.COLUMN_POINTS + "+100 " +
                "WHERE " + HighscoreDBHelper.COLUMN_POINTS + "<2500";
        db.execSQL(sql);
    }

    public void addItems() {
        Score[] scores = {new Score("921012-1234","Albert",2000),
                new Score("921012-2335","Berry",3000),
                new Score("921012-3334","Caja",1000),
                new Score("921012-1224","Daniella",2500),
                new Score("921012-1222","Eda",2300)};
        for(Score score : scores) {
            addScore(score);
        }
        scores[3].setPoints(scores[3].getPoints() + 500);
        updateScore(scores[3]);
    }

    public void addScore(Score score) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HighscoreDBHelper.COLUMN_ID, score.getId());
        values.put(HighscoreDBHelper.COLUMN_NAME, score.getName());
        values.put(HighscoreDBHelper.COLUMN_POINTS, score.getPoints());
        db.insert(HighscoreDBHelper.TABLE_NAME, "", values); // Kastar ej exception, -1 om fel
    }

    public void updateScore(Score score) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String sql = "UPDATE " + HighscoreDBHelper.TABLE_NAME + " " +
//                "SET " + HighscoreDBHelper.COLUMN_POINTS + "=" + score.getPoints() + " " +
//                "WHERE " + HighscoreDBHelper.COLUMN_ID + "='" + score.getId() +"'";
//        Log.d("updateScore",sql + ":" + score.getPoints());
//        db.execSQL(sql);
        ContentValues values = new ContentValues();
        values.put(HighscoreDBHelper.COLUMN_NAME, score.getName());
        values.put(HighscoreDBHelper.COLUMN_POINTS, score.getPoints());
        Log.d("updateScore", score.getId() + "=" + HighscoreDBHelper.COLUMN_ID);
        db.update(HighscoreDBHelper.TABLE_NAME, values, HighscoreDBHelper.COLUMN_ID + "='" + score.getId() + "'", null);
    }

    public void showHighscore() {
        int idIndex, nameIndex, pointsIndex;
        Score[] scores=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + HighscoreDBHelper.TABLE_NAME +
                " ORDER BY " + HighscoreDBHelper.COLUMN_POINTS, null);

        scores = new Score[cursor.getCount()];
        idIndex = cursor.getColumnIndex(HighscoreDBHelper.COLUMN_ID);
        nameIndex = cursor.getColumnIndex(HighscoreDBHelper.COLUMN_NAME);
        pointsIndex = cursor.getColumnIndex(HighscoreDBHelper.COLUMN_POINTS);

        for (int i = 0; i < scores.length; i++) {
            cursor.moveToPosition(i);
            scores[i] = new Score(cursor.getString(idIndex),
                    cursor.getString(nameIndex),
                    cursor.getInt(pointsIndex));
        }

        Intent intent = new Intent(activity,HighscoreActivity.class);
        intent.putExtra(SCORES,scores);
        activity.startActivity(intent);
    }
}
