package com.antonhellbegmail.labb3a;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.antonhellbegmail.labb3a.R.id.fragment_viewer;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        android.app.FragmentManager fm = getFragmentManager();
        ViewerFragment viewerFragment = (ViewerFragment) fm.findFragmentById(R.id.fragment_viewer);
        InputFragment inputFragment = (InputFragment) fm.findFragmentById(R.id.fragment_input);

        Controller controller = new Controller(viewerFragment, inputFragment);
    }
}
