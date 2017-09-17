package com.example.ag6505.filesystem;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by tsroax on 08/09/15.
 */
public class MainController {
    public final static String SCORES = "se.mah.tsroax.f5sqlite.SCORES";
    public final static String FILE_NAME = "Expences.dat";
    private MainActivity activity;

    public MainController(MainActivity activity) {
        this.activity = activity;
    }

    public void deleteAll() {
        activity.deleteFile(FILE_NAME);
    }

    public void addScores() {
        ObjectOutputStream oos=null;
        Score[] scores = {new Score("921012-1234","Albert",2000),
                new Score("921012-2335","Berry",3000),
                new Score("921012-3334","Caja",1000),
                new Score("921012-1224","Daniella",2500),
                new Score("921012-1222","Eda",2300)};
        try {
            oos = new ObjectOutputStream(activity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            for(Score score : scores) {
                oos.writeObject(score);
                oos.flush();
            }
        }catch(IOException e) {
            Log.w("addScores", e.toString());
        }
        try {
            oos.close();
        }catch(Exception e) {}
    }

    public void showHighscore() {
        ObjectInputStream ois=null;
        ArrayList<Score> list = new ArrayList<Score>();
        Score score;
        try {
            ois = new ObjectInputStream(activity.openFileInput(FILE_NAME));
            while(true) {
                score = (Score)ois.readObject();
                list.add(score);
            }
        }catch(Exception e) {
            Log.w("showHighScore",e.toString());
        }
        try {
            ois.close();
        } catch(Exception e) {}
        Score[] scores = list.toArray(new Score[]{});

        Intent intent = new Intent(activity,HighscoreActivity.class);
        intent.putExtra(MainController.SCORES,scores);
        activity.startActivity(intent);
    }

}
