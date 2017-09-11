package com.example.anton.labb2c;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HowToActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        initController();
    }

    private void initController() {
        android.app.FragmentManager fm = getFragmentManager();
        ViewerFragment viewerFragment = (ViewerFragment) fm.findFragmentById(R.id.viewer_fragment);
        InputFragment inputFragment = (InputFragment) fm.findFragmentById(R.id.input_fragment);
        Controller controller = new Controller(viewerFragment);
        inputFragment.setController(controller);
    }
}
