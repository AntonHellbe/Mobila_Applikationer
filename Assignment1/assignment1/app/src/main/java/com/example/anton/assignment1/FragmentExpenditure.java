package com.example.anton.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-09-12.
 */

public class FragmentExpenditure extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;

    private Controller controller;
    private RecyclerView.LayoutManager mLayoutManager;
    private TransactionAdapter transactionAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expenditure, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.setTransactionAdapter();
    }

    private void registerListeners() {
        fabAdd.setOnClickListener(new FABActionListener());
    }

    private void initializeComponents(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
        fabAdd = (FloatingActionButton) rootView.findViewById(R.id.fabAddExpenditure);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        transactionAdapter = new TransactionAdapter();
        transactionAdapter.setController(controller);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setAdapter(ArrayList<Transaction> transactionArrayList){
        transactionAdapter.setTransactions(transactionArrayList);
        recyclerView.setAdapter(transactionAdapter);
    }

    private class FABActionListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            controller.setAddFragment();
        }
    }

}
