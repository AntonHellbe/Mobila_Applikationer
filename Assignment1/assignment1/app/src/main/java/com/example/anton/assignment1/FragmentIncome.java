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

import java.util.ArrayList;


/**
 * Created by Anton on 2017-09-12.
 */

public class FragmentIncome extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Transaction> income = new ArrayList<>();
    private Controller controller;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter transactionAdapter;
    private FloatingActionButton fabAdd;

    @Override
    public void onResume() {
        super.onResume();
        controller.setTransactionAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_income, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners() {
        fabAdd.setOnClickListener(new FABListener());
    }

    private void initializeComponents(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
        fabAdd = (FloatingActionButton) rootView.findViewById(R.id.fabAdd);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
    }
    
    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setAdapter(ArrayList<Transaction> transactionArrayList){
        transactionAdapter = new TransactionAdapter(transactionArrayList);
        recyclerView.setAdapter(transactionAdapter);
    }


    private class FABListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            controller.setAddFragment();
        }
    }

}
