package com.example.anton.assignment1;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Anton on 2017-09-12.
 */

public class UserActivity extends AppCompatActivity {

    private String[] titles = {"Home","","Income", "Expenditure", "Set dates"};
    private Integer[] imgId = {R.drawable.account_circle, R.drawable.account_edit, R.drawable.chart_bar, R.drawable.chart_bar, R.drawable.calendar_range};
    private DrawerLayout drawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle drawerToggle;
    private Controller controller;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        controller = new Controller(this);
        initializeComponenets();
        registerListeners();
        if(savedInstanceState != null){
            controller.rescueMission(savedInstanceState);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState = controller.saveInformation(outState);
        super.onSaveInstanceState(outState);
    }

    private void initializeComponenets() {
        Intent intent = getIntent();
        controller.createUser(intent);
        titles[1] = controller.getCurrentUserName();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.left_drawer);
        listView.setAdapter(new MenuAdapter(this, titles, imgId));
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);


    }

    public void updateNavDrawer(String username){
        titles[1] = username;
        listView.setAdapter(new MenuAdapter(this, titles, imgId));

    }


    private void registerListeners() {
        listView.setOnItemClickListener(new ListViewListener());
    }

    private class ListViewListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            controller.changeFragment(position);
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
