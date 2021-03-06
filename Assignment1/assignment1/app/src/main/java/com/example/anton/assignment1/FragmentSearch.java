package com.example.anton.assignment1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Anton on 2017-09-13.
 */

public class FragmentSearch extends Fragment {

    private Spinner spinner;
    private Controller controller;
    private TextView tvFromDate, tvToDate;
    private final Calendar c = Calendar.getInstance();
    private int year = c.get(Calendar.YEAR);
    private int month = c.get(Calendar.MONTH);
    private int day = c.get(Calendar.DAY_OF_MONTH);
    private int savedPosition;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        initializeComponents(rootView);
        registerListeners();
        setSpinnerChoice();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.setTvDatesSelected();
        controller.setSearchSpinner();

    }

    private void initializeComponents(View rootView) {
        spinner = (Spinner) rootView.findViewById(R.id.spinnerDates);
        tvFromDate = (TextView) rootView.findViewById(R.id.tvFrom);
        tvToDate = (TextView) rootView.findViewById(R.id.tvTo);
    }

    private void registerListeners() {
        SpinnerListener spinnerListener = new SpinnerListener();
        spinner.setOnTouchListener(spinnerListener);
        spinner.setOnItemSelectedListener(spinnerListener);

    }

    public void setSpinnerChoice() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerChoice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

        boolean userSelect = false;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            userSelect = true;
            return false;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            Log.v("Message", spinner.getSelectedItem().toString());
            savedPosition = position;
            if(userSelect){
                controller.dateSelect(spinner.getSelectedItem().toString());
                userSelect = false;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            // Not needed
        }


    }

    public void startDatePicker(){
        new DatePickerDialog(getActivity(), new fromDate(), year, month, day).show();
    }

    class fromDate implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = controller.padDate(year, month, dayOfMonth);
            controller.setCustomDateFrom(date);
            new DatePickerDialog(getActivity(), new toDate(), year, month, day).show();

        }
    }

    class toDate implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = controller.padDate(year, month, dayOfMonth);
            controller.setCustomDateTo(date);

        }
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setTvFromDate(String text){
        tvFromDate.setText(text);
    }
    public void setTvToDate(String text){
        tvToDate.setText(text);
    }

    public void setSpinner(int choice){
        spinner.setSelection(choice);
    }

}
