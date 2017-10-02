package com.example.ag6505.backstack;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Controller controller;
    private Button btnActivity;
    private Button btnFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        registerListeners();
        Intent intent = getIntent();
        int activityCounter = intent.getIntExtra("activities",1);
        int fragmentCounter = intent.getIntExtra("fragments",0);
        controller = new Controller(this,activityCounter,fragmentCounter);
    }

    private void initComponents() {
        btnActivity = (Button)findViewById(R.id.btnActivity);
        btnFragment = (Button)findViewById(R.id.btnFragment);
    }

    private void registerListeners() {
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.newActivity();
            }
        });
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.newFragment();
            }
        });
    }

}
