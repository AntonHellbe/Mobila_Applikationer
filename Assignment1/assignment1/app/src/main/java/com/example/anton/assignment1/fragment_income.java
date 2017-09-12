package com.example.anton.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * Created by Anton on 2017-09-12.
 */

public class fragment_income extends Fragment {

    private ListView listView;
    private String[] contents = {"hello", "my", "name", "is", "slim shady"};
    private Controller controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_income, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void initializeComponents(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.lvIncome);
        listView.setAdapter(new FinanceAdapter(getActivity(), contents));

    }
    
    public void setController(Controller controller){
        this.controller = controller;
    }

    private void registerListeners() {
    }
}
