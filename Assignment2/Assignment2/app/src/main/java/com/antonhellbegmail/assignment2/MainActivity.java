package com.antonhellbegmail.assignment2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Controller controller;
    private TextView tvUsername;
    private final int THUMBNAIL = 1;
    private final int CAMERA_PICTURE = 2;
    private Uri pictureUri;


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        controller.rescueMission();
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        controller.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        controller.onDestroy();
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        controller.onPause();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestPermissions();
        controller = new Controller(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationListener());
        tvUsername = navigationView.getHeaderView(0).findViewById(R.id.tvUsername);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        changeLanguage(id);


        return super.onOptionsItemSelected(item);
    }


    public void changeLanguage(int id){
        String language = controller.getDataFragment().getCurrentLocale();
        if(id == R.id.sv) {
            if (language.equals("sv")) {
                return;
            }
            controller.getDataFragment().setCurrentLocale("sv");
            language = "sv";
            Locale locale = new Locale(language);
            Configuration config = new Configuration();
            config.locale = locale;
            getApplicationContext().getResources().updateConfiguration(config, null);
            recreate();
        }
        if(id == R.id.en) {
            if (language.equals("en")) {
                return;
            }
            controller.getDataFragment().setCurrentLocale("en");
            language = "en";
            Locale locale = new Locale(language);
            Configuration config = new Configuration();
            config.locale = locale;
            getApplicationContext().getResources().updateConfiguration(config, null);
            recreate();
        }
    }



    public void setUsername(String text) {
        this.tvUsername.setText(text);
    }


    private class NavigationListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.map) {
                controller.setFragment(R.id.map);
            } else if (id == R.id.members) {
                controller.setFragment(R.id.members);
            } else if (id == R.id.groups) {
                controller.setFragment(R.id.groups);
            } else if (id == R.id.chat) {
                controller.setFragment(R.id.chat);
            }else if(id == R.id.register){
                controller.setFragment(R.id.register);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }

    public Controller getController(){
        return this.controller;
    }

    public void setFragment(Fragment fragment, boolean backstack){
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container1, fragment);

        if(backstack){
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    public void startCameraActivity(int mode){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(mode == 1){
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, THUMBNAIL);
            }

        }else if(mode == 2){
            try {
                if (Build.VERSION.SDK_INT >= 24) {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String fileName = "JPEG_" + timeStamp + ".jpg";
                    File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    pictureUri = Uri.fromFile(new File(dir, fileName));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                    startActivityForResult(intent, CAMERA_PICTURE);;

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_PICTURE && resultCode == Activity.RESULT_OK){
            String pathToPicture = pictureUri.getPath();
            controller.compress(pathToPicture);

        }
    }

    public void requestPermissions(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.INTERNET}, 0);


        }
    }


}
