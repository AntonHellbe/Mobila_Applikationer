package com.example.ag6505.network3;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends Activity {
    private Controller controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager();
        MainFragment mainFragment = (MainFragment)fm.findFragmentById(R.id.fragment);
        controller = new Controller(this, mainFragment, savedInstanceState);
	}

	protected void onSaveInstanceState(Bundle outState) {
		controller.onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}
	
	protected void onResume() {
		controller.onResume();
		super.onResume();
	}

	protected void onDestroy() {
        controller.onDestroy();
		super.onDestroy();
	}

}
