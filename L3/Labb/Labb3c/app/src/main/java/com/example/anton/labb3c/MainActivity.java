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
        ViewerFragment viewerFragment = (ViewerFragment) fm.findFragmentById(R.layout.fragment_viewer);
        InputFragment inputFragment = (InputFragment) fm.findFragmentById(R.layout.fragment_input);
        Controller controller = new Controller(viewerFragment, inputFragment);
        setFragment(inputFragment, true);
    }

    public void setFragment(Fragment fragment, boolean backstack){
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        if(fragment instanceof InputFragment){
            InputFragment inputFragment = new InputFragment();
            ft.replace(R.id.container, inputFragment);
        }else{
            ViewerFragment viewerFragment = new ViewerFragment();
            ft.replace(R.id.container, viewerFragment);
        }

        if(backstack){
            ft.addToBackStack(null);
        }
        ft.commit();
    }
}
