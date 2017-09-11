package com.example.anton.labb2c;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Anton on 2017-09-08.
 */

public class InputFragment extends Fragment{

    private Button btnNext, btnPrev;
    private Controller controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_input, container, false);
        initalizeComponents(rootView);
        registerListeners();
        return rootView;
    }

    public void setController(Controller cont){
        this.controller = cont;
    }

    public void initalizeComponents(View rootView) {
        btnNext = (Button) rootView.findViewById(R.id.btn1);
        btnPrev = (Button) rootView.findViewById(R.id.btn2);
    }

    public void registerListeners() {

        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                controller.nextClick();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                controller.previousClick();
            }
        });
    }

}
