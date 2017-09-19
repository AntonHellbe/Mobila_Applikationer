package com.example.anton.assignment1;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

/**
 * Created by Anton on 2017-09-12.
 */

public class UserActivity extends AppCompatActivity {

    private String[] titles = {"Home","","Income", "Expenditure", "Set dates", "Scan Barcode"};
    private Integer[] imgId = {R.drawable.account_circle, R.drawable.account_edit, R.drawable.chart_bar, R.drawable.chart_bar, R.drawable.calendar_range, R.drawable.barcode_scan};
    private DrawerLayout drawerLayout;
    private ListView listView;
    private Controller controller;
    private Toolbar toolbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        controller = new Controller(this);
        initializeComponenets();
        registerListeners();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = controller.saveInformation(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            controller.rescueMission(savedInstanceState);
        }
    }

    private void initializeComponenets() {
        Intent intent = getIntent();
        controller.createUser(intent);
        titles[1] = controller.getCurrentUserName();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.left_drawer);
        listView.setAdapter(new MenuAdapter(this, titles, imgId));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_close, R.string.drawer_open);
        //drawerLayout.setDrawerListener(toggle);
        toggle.syncState();




    }

    public void updateNavDrawer(String username){
        titles[1] = username;
        listView.setAdapter(new MenuAdapter(this, titles, imgId));

    }


    private void registerListeners() {
        listView.setOnItemClickListener(new ListViewListener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.v()

        if(resultCode == RESULT_OK){
            controller.updateBarCodeInformation(data);
            controller.setAddFragment();

        }else{
            controller.changeFragment(0);
        }
    }

    public void startBarCodeActivity() {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
        startActivityForResult(intent, 0);
    }

    private class ListViewListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            controller.changeFragment(position);
            controller.setFromFragment();
            drawerLayout.closeDrawers();
        }
    }

    public void setFragment(Fragment fragment, boolean backstack){
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);

        if(backstack){
            ft.addToBackStack(null);
        }
        ft.commit();
    }




}
