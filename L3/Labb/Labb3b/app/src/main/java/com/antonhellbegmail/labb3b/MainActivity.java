package com.antonhellbegmail.labb3b;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        android.app.FragmentManager fm = getFragmentManager();
        InputFragment inputFragment = (InputFragment) fm.findFragmentById(R.id.fragment_input);
        ViewerFragment viewerFragment = (ViewerFragment) fm.findFragmentById(R.id.fragment_viewer);
        Controller controller = new Controller(viewerFragment, inputFragment);
    }
}
