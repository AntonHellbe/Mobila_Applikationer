package com.example.anton.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Anton on 2017-09-19.
 */

public class FragmentTransaction extends Fragment {

    private TextView tvDisplayTitle, tvDisplayCategory, tvDisplayDate, tvDisplayAmount;
    private Controller controller;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners(){

    }

    @Override
    public void onResume() {
        super.onResume();
        controller.setTransactionInformation();
    }

    private void initializeComponents(View rootView) {
        tvDisplayTitle = (TextView) rootView.findViewById(R.id.tvDisplayTitle);
        tvDisplayCategory = (TextView) rootView.findViewById(R.id.tvDisplayCategory);
        tvDisplayAmount = (TextView) rootView.findViewById(R.id.tvDisplayAmount);
        tvDisplayDate = (TextView) rootView.findViewById(R.id.tvDisplayDate);
    }




    public void setTvDisplayTitle(String text) {
        this.tvDisplayTitle.setText(text);
    }

    public void setTvDisplayCategory(String text) {
        this.tvDisplayCategory.setText(text);
    }

    public void setTvDisplayAmount(String text){
        this.tvDisplayAmount.setText(text);
    }

    public void setTvDisplayDate(String text){
        this.tvDisplayDate.setText(text);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
