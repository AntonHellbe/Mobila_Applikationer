package com.example.anton.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * Created by Anton on 2017-09-12.
 */

public class fragment_user extends Fragment {

    private EditText etUsername;
    private Controller controller;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        initializeComponents(rootView);
        return rootView;
    }

    private void initializeComponents(View rootView) {
        etUsername = (EditText) rootView.findViewById(R.id.etUsername);
    }

    //public void setTvUsername(String username) {
    //    this.etUsername.setText(username);
    //}

    public void setController(Controller controller){
        this.controller = controller;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.updateUsername();
    }
}
