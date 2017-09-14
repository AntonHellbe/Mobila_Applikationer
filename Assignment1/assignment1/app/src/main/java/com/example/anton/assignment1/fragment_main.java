package com.example.anton.assignment1;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-12.
 */

public class fragment_main extends Fragment {

    private Controller controller;
    private TextView tvUsername;
    private PieChart pieChart;
    private ToggleButton tbBtn;
    private Boolean state = false;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners() {
        tbBtn.setOnCheckedChangeListener(new ToggleButtonListener());
    }

    private void initializeComponents(View rootView) {
        tvUsername = (TextView) rootView.findViewById(R.id.tvDisplayUsername);
        tbBtn = (ToggleButton) rootView.findViewById(R.id.tbPieChart);
        pieChart = (PieChart) rootView.findViewById(R.id.pcExpenditure);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.getDescription().setText("");
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(10f);
        //pieChart.setDescription();
    }


    public void setController(Controller controller){
        this.controller = controller;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.updateUsername();
        controller.addChartData(state);

    }

    public void setUserName(String text){
        tvUsername.setText(text);
    }

    public void setPieChartData(PieData pieData){
        pieChart.setData(pieData);
        pieChart.invalidate(); //Refresh data in Graph - Has to be called after setting data
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutQuad); // Cool animation after refreshing the data
    }

    private class ToggleButtonListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            controller.addChartData(isChecked);
            state = isChecked;
        }
    }

    public void setTbOff(String text){
        tbBtn.setTextOff(text);
    }

    public void setTbOn(String text){
        tbBtn.setTextOn(text);
    }

}
