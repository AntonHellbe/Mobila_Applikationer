package com.example.ag6505.service;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by tsroax on 2014-09-29.
 */
public class Controller {
    private MainActivity activity;
    private ServiceAFragment serviceAFragment;
    private Intent serviceIntent;
    private ArrayList<Intent> intents;

    public Controller(MainActivity activity, ServiceAFragment serviceAFragment) {
        this.activity = activity;
        this.serviceAFragment = serviceAFragment;
        this.serviceAFragment.setController(this);

        this.intents = new ArrayList<Intent>();
    }

    public void startService(int serviceNbr) {


        switch (serviceNbr){
            case 0:
                serviceIntent = new Intent(activity,ServiceA.class);
                break;
            case 1:
                serviceIntent = new Intent(activity,ServiceB.class);
                break;
            case 2:
                serviceIntent = new Intent(activity,ServiceC.class);
                break;
            default:
                serviceIntent = new Intent(activity,ServiceA.class);
                break;
        }
        serviceIntent.putExtra("times", 4);
        activity.startService(serviceIntent);
        intents.add(serviceIntent);

    }

    public void stopService() {
        for (Intent i:intents){
            activity.stopService(i);
        }
    }
}
