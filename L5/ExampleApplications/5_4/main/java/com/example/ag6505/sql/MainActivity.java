package com.example.ag6505.sql;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnAdd;
    private Button btnDeleteAll;
    private Button btnAdd100;
    private Button btnShow;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        registerListeners();
        controller = new MainController(this);
    }

    private void initComponents() {
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnDeleteAll = (Button)findViewById(R.id.btnDeleteAll);
        btnAdd100 = (Button)findViewById(R.id.btnAdd100);
        btnShow = (Button)findViewById(R.id.btnShow);
    }

    private void registerListeners() {
        ButtonListener bl = new ButtonListener();
        btnAdd.setOnClickListener(bl);
        btnDeleteAll.setOnClickListener(bl);
        btnAdd100.setOnClickListener(bl);
        btnShow.setOnClickListener(bl);
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnAdd : controller.addItems(); break;
                case R.id.btnDeleteAll : controller.deleteAll(); break;
                case R.id.btnAdd100: controller.add100(); break;
                case R.id.btnShow : controller.showHighscore(); break;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
