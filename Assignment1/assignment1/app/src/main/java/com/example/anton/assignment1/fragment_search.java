package com.example.anton.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-13.
 */

public class fragment_search extends Fragment {

    private Spinner spinner;
    private Controller controller;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.setSpinnerChoice();
    }

    private void initializeComponents(View rootView) {
        spinner = (Spinner) rootView.findViewById(R.id.spinnerDates);
    }

    private void registerListeners() {

    }

    public void setSpinnerChoice() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerChoice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    public void setController(Controller controller){
        this.controller = controller;
    }
}
