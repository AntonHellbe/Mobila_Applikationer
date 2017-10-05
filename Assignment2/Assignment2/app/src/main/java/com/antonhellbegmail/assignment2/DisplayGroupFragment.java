package com.antonhellbegmail.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class DisplayGroupFragment extends Fragment {

    private TextView tvGroupMembers, tvGroupName;
    private Button btnBack, btnRegister, btnDeRegister;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DisplayGroupAdapter displayGroupAdapter;

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
        btnRegister.setOnClickListener(new ButtonListener());
        btnDeRegister.setOnClickListener(new ButtonListener());
    }

    private void initializeComponents(View rootView) {
        tvGroupName = (TextView) rootView.findViewById(R.id.tvGroup);
//        tvGroupMembers = (TextView) rootView.findViewById(R.id.tvGroupMembers);
        btnBack = (Button) rootView.findViewById(R.id.btnBack);
        btnDeRegister = (Button) rootView.findViewById(R.id.btnDeRegister);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvGroupView);
        displayGroupAdapter = new DisplayGroupAdapter();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(displayGroupAdapter);
    }

    @Override
    public void onResume() {
        //((MainActivity)getActivity()).getController().setMemberInformation();
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
                    try {
                        ((MainActivity) getActivity()).getController().setFragment(((MainActivity) getActivity()).getController().getCurrentFragment());
                    }catch(Exception e){

                    }
                    break;
                case R.id.btnRegister:
                    try {
                        ((MainActivity) getActivity()).getController().registerToExistingGroup(tvGroupName.getText().toString());
                    }catch(Exception e){

                    }
                    break;
                case R.id.btnDeRegister:
                    try {
                        ((MainActivity) getActivity()).getController().unRegisterFromGroup(tvGroupName.getText().toString());
                    }catch(Exception e){

                    }
                    break;
            }
        }
    }

    public void setBtnDeRegister(Boolean isEnabled){
        btnDeRegister.setEnabled(isEnabled);
    }

    public void setBtnRegister(Boolean isEnabled){
        btnRegister.setEnabled(isEnabled);
    }

    public void setTvGroupName(String text){
        tvGroupName.setText(text);
    }

    public void setData(ArrayList<Member> memList){
        ((DisplayGroupAdapter)recyclerView.getAdapter()).setGroupMembers(memList);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
