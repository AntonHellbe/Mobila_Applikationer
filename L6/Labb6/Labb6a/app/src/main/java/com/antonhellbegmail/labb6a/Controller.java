package com.antonhellbegmail.labb6a;

import android.graphics.Color;

/**
 * Created by Anton on 2017-09-25.
 */

public class Controller {

    private MainActivity mainActivity;
    private Task task;


    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;

    }
    public void startAsyncTask(int pos, int max, int delay, int tv){
        task = new Task();
        task.setController(this);
        task.execute(pos, max, delay, tv);
    }


    public void updateTv1(int color){
        mainActivity.setTv1Color(color);
    }
    public void updateTv2(int color){
        mainActivity.setTv2Color(color);
    }

    public void clearTv1(){
        mainActivity.setTv1Color(Color.TRANSPARENT);
    }

    public void clearTv2(){
        mainActivity.setTv2Color(Color.TRANSPARENT);
    }


}
