package com.example.anton.assignment1;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Anton on 2017-09-12.
 */

public class UserActivity extends AppCompatActivity {

    private String[] titles = {"Home","","Income", "Expenditure"};
    private Integer[] imgId = {R.drawable.icon0, R.drawable.icon1, R.drawable.icon2, R.drawable.icon3};
    private DrawerLayout drawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle drawerToggle;
    private TextView tvUsername;
    private Controller cont;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initializeComponenets();
        registerListeners();
        cont = new Controller(this);

    }

    private void initializeComponenets() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.left_drawer);
        tvUsername = (TextView) findViewById(R.id.tvContent);

        Intent intent = getIntent();
        titles[1] = intent.getStringExtra("userid");

        listView.setAdapter(new MenuAdapter(this, titles, imgId));
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
    }

    private void registerListeners() {
        listView.setOnItemClickListener(new ListViewListener());
    }

    private class ListViewListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            cont.changeFragment(position);
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
