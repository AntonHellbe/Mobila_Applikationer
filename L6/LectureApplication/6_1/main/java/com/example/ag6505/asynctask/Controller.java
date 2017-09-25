package com.example.ag6505.asynctask;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;

/**
 * Created by tsroax on 2014-09-29.
 */
public class Controller {
    private MainFragment mainFragment;
    private SharedPreferences sharedPreferences;
    private Colorize colorize;
    private int[] colors = {Color.BLUE,Color.CYAN,Color.DKGRAY, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.RED,Color.YELLOW};
    private final int MAX = 40, DELAY=500;

    public Controller(MainActivity activity, MainFragment fragment) {
        this.mainFragment = fragment;
        this.mainFragment.setController(this);
        sharedPreferences = activity.getSharedPreferences("F7AsyncTask", Activity.MODE_PRIVATE);
    }

    public void startAsyncTask() {
        startAsyncTask(0);
    }

    public void startAsyncTask(int startPos) {
        colorize = new Colorize();
        colorize.execute(startPos, MAX, DELAY);
    }

    public void saveState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(colorize!=null) {
            editor.putInt("startPos", colorize.getPosition());
            editor.putBoolean("running", colorize.getRunning());
            colorize.setRunning(false);
        } else {
            editor.putInt("startPos", 0);
            editor.putBoolean("running", false);
        }
        editor.commit();
    }

    public void updateUI() {
        int startPos = sharedPreferences.getInt("startPos",0);
        boolean running = sharedPreferences.getBoolean("running",false);
        int color = colors[startPos % colors.length];
        mainFragment.setProgressBar(startPos, MAX, color);
        if(running) {
            startAsyncTask(startPos);
        }
    }

    private class Colorize extends AsyncTask<Integer,Void,Void> {
        boolean running = false;
        int position;

        public int getPosition() {
            return position;
        }

        public boolean getRunning() {
            return running;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        protected void onPreExecute() {
            mainFragment.setEnabled(false);
            running = true;
        }

        @Override
        protected Void doInBackground(Integer... params) {
            position = params[0];
            int times = params[1];
            long pause = params[2];
            while(running && position<times) {
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException e) {}
                position++;
                publishProgress();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... aVoid) {
            mainFragment.setColor(colors[ position % colors.length]);
            mainFragment.setProgress(position);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            running = false;
            position = 0;
            mainFragment.setProgress(position);
            mainFragment.setEnabled(true);
        }
    }
}
