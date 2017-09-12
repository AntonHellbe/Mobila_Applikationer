package com.example.anton.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Anton on 2017-09-12.
 */

public class fragment_main extends Fragment {

    private Controller controller;
    private TextView tvUsername;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initializeComponents(rootView);
        return rootView;
    }

    private void initializeComponents(View rootView) {
        tvUsername = (TextView) rootView.findViewById(R.id.tvDisplayUsername);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.updateUsername();
    }

    public void setUserName(String text){
        tvUsername.setText(text);
    }
}
