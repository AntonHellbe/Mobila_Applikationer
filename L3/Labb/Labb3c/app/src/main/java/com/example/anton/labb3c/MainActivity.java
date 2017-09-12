package com.example.anton.labb3c;

import android.app.Fragment;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        android.app.FragmentManager fm = getFragmentManager();
        Controller controller = new Controller(this);
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
