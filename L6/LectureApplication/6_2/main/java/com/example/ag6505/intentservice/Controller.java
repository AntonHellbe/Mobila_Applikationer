package com.example.ag6505.intentservice;

import android.content.Intent;
import android.util.Log;

/**
 * Created by tsroax on 2014-09-29.
 */
public class Controller {
    private MainActivity activity;
    private ServiceAFragment serviceAFragment;
    private Intent serviceIntent;

    public Controller(MainActivity activity, ServiceAFragment serviceAFragment) {
        this.activity = activity;
        this.serviceAFragment = serviceAFragment;
        this.serviceAFragment.setController(this);
        serviceIntent = new Intent(activity,ServiceA.class);
    }

    public void startService() {
        serviceIntent.putExtra("times",4);
        activity.startService(serviceIntent);
    }

    public void stopService() {
        activity.stopService(serviceIntent);
    }
}
