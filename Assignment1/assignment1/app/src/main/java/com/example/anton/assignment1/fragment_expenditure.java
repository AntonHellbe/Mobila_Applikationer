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

public class fragment_expenditure extends Fragment {

    private ListView listView;
    private String[] contents = {"Hej", "hopp", "det", "är", "jobbigt"};
    private Controller controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expenditure, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void initializeComponents(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.lvOutcome);
        listView.setAdapter(new FinanceAdapter(getActivity(), contents));

    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    private void registerListeners() {
    }
}
