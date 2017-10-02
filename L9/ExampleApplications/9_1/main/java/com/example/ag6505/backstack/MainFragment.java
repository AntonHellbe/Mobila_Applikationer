package com.example.ag6505.backstack;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private TextView tvActivityRef;
    private TextView tvFragmentRef;
    private TextView tvActivityCounter;
    private TextView tvFragmentCounter;
    private int activityCounter;
    private int fragmentCounter;
    private String activityRef;
    private String fragmentRef;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initComponents(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void initComponents(View view) {
        tvActivityRef = (TextView)view.findViewById(R.id.tvActivityRef);
        tvFragmentRef = (TextView)view.findViewById(R.id.tvFragmentRef);
        tvActivityCounter = (TextView)view.findViewById(R.id.tvActivity);
        tvFragmentCounter = (TextView)view.findViewById(R.id.tvFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        tvActivityRef.setText(this.activityRef);
        tvFragmentRef.setText(this.fragmentRef);
        tvActivityCounter.setText(""+this.activityCounter);
        tvFragmentCounter.setText(""+this.fragmentCounter);
    }

    public void setReferences(String actStr) {
        this.activityRef = actStr;
        this.fragmentRef = this.toString();
        if(tvActivityRef!=null) {
            tvActivityRef.setText(this.activityRef);
            tvFragmentRef.setText(this.fragmentRef);
        }
    }

    public void setActivityCounter(int activityCounter) {
        this.activityCounter = activityCounter;
        if(tvActivityCounter!=null)
            tvActivityCounter.setText(""+this.activityCounter);
    }

    public void setFragmentCounter(int fragmentCounter) {
        this.fragmentCounter = fragmentCounter;
        if(tvFragmentCounter!=null)
            tvFragmentCounter.setText(""+this.fragmentCounter);
    }

}
