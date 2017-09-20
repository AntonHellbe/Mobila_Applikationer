package com.example.anton.assignment1;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new Controller(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requestPermissions();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = controller.saveInformationMainActivity(outState);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            controller.setRestoredInformation(savedInstanceState);
        }

    }
    /*
      Method is here because on older version of android It's needed to request permissions to write on storage (SQLDatabase)
     */
    public void requestPermissions(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

            }
        }
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



    public void startIntent(User user){
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("currentUser", user);
        startActivity(intent);
    }


}
