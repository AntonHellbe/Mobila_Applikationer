package com.example.ag6505.backstack;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;

/**
 * Created by tsroax on 27/09/15.
 */
public class Controller {
    private MainActivity activity;
    private FragmentManager fm;
    private int activityCounter;
    private int fragmentCounter;

    public Controller(MainActivity activity, int activityCounter, int fragmentCounter) {
        this.activity = activity;
        this.activityCounter = activityCounter;
        this.fragmentCounter = fragmentCounter;
        fm = activity.getFragmentManager();
        addNewFragment(false);
    }

    private void addNewFragment(boolean addToBackstack) {
        MainFragment mainFragment = new MainFragment();
        mainFragment.setReferences(activity.toString());
        mainFragment.setActivityCounter(activityCounter);
        mainFragment.setFragmentCounter(++fragmentCounter);
        FragmentTransaction ft = fm.beginTransaction();
        if(addToBackstack)
            ft.addToBackStack(null);
        ft.replace(R.id.fragment_container, mainFragment);
        ft.commit();
    }

    public void newActivity() {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("activities",activityCounter+1);
        intent.putExtra("fragments",0);
        activity.startActivity(intent);
    }

    public void newFragment() {
        addNewFragment(true);
    }
}
