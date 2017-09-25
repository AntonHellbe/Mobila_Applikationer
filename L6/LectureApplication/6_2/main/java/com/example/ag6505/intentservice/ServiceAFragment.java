package com.example.ag6505.intentservice;


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
        Button btnStart = (Button)view.findViewById(R.id.btnStart);
        Button btnStop = (Button)view.findViewById(R.id.btnStop);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startService();
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
