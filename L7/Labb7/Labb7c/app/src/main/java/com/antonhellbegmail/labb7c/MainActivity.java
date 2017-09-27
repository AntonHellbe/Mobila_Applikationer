package com.antonhellbegmail.labb7c;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager();
        MainFragment mainFragment = (MainFragment)fm.findFragmentById(R.id.fragment);
        controller = new Controller(this,mainFragment);

    }

    @Override
    protected void onDestroy() {
        controller.disconnectClicked();
        super.onDestroy();
    }

    public ReceiveListener getListener(){
        return controller.getListener();
    }

    public Controller getController() {
        return this.controller;
    }
}
