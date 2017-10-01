package com.antonhellbegmail.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class DisplayGroupFragment extends Fragment {

    private TextView tvGroupMembers;
    private Button btnBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.displaygroup_fragment, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners() {
        btnBack.setOnClickListener(new ButtonListener());
    }

    private void initializeComponents(View rootView) {
        tvGroupMembers = (TextView) rootView.findViewById(R.id.tvGroupMembers);
        btnBack = (Button) rootView.findViewById(R.id.btnBack);
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).getController().setMemberInformation();

        super.onResume();
    }

    public void setTvGroupMembers(String text){
        tvGroupMembers.append(text + " ");
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btnBack:
                    ((MainActivity)getActivity()).getController().setFragment(((MainActivity)getActivity()).getController().getCurrentFragment());
            }
        }
    }
}
