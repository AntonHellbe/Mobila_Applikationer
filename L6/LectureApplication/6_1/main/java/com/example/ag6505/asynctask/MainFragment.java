package com.example.ag6505.asynctask;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MainFragment extends Fragment {
    private Button btnStart;
    private ProgressBar progressBar;
    private Controller controller;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view) {
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        btnStart = (Button)view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startAsyncTask();
            }
        });
    }

    @Override
    public void onPause() {
        controller.saveState();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.updateUI();
    }

    public void  setController(Controller controller) {
        this.controller = controller;
    }

    public void setEnabled(boolean enabled) {
        btnStart.setEnabled(enabled);
    }

    public void setColor(int color) {
        if(btnStart!=null) {
            btnStart.setBackgroundColor(color);
        }
    }

    public void setProgressBar(int position, int max, int color) {
        progressBar.setMax(max);
        setProgress(position);
        btnStart.setBackgroundColor(color);
    }

    public void setProgress(int progress) {
        progressBar.setProgress(progress);
    }
}
