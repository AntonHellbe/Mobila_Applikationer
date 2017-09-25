package com.example.ag6505.service;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ServiceAFragment extends Fragment {
    private Controller controller;

    public ServiceAFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_a, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        Button btnA = (Button)view.findViewById(R.id.btnA);
        Button btnStop = (Button)view.findViewById(R.id.btnStop);
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startService(0);
            }
        });

        Button btnB = (Button)view.findViewById(R.id.btnB);
        Button btnC = (Button)view.findViewById(R.id.btnC);

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startService(1);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startService(2);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.stopService();
            }
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
