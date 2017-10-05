package com.antonhellbegmail.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Anton on 2017-10-03.
 */

public class ChatFragment extends Fragment {

    private Button btnSendMsg;
    private EditText etMessage;
    private Spinner sGroups;
    private ChatAdapter chatAdapter;
    private ImageView ivCamera;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lm;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.chat_fragment, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners() {
        ivCamera.bringToFront();
        btnSendMsg.setOnClickListener(new ButtonListener());
        ivCamera.setOnClickListener(new ButtonListener());

    }

    private void initializeComponents(View rootView) {
        btnSendMsg = (Button) rootView.findViewById(R.id.btnSendMsg);
        etMessage = (EditText) rootView.findViewById(R.id.etMessage);
        sGroups = (Spinner) rootView.findViewById(R.id.spinnerGroups);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvChat);
        ivCamera = (ImageView) rootView.findViewById(R.id.imCamera);
        recyclerView.setHasFixedSize(true);
        lm = new LinearLayoutManager(getActivity());
        chatAdapter = new ChatAdapter(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(chatAdapter);
        sGroups.setOnItemSelectedListener(new SpinnerListener());
    }

    public void setSpinnerAdapter(ArrayList<String> groups){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sGroups.setAdapter(adapter);
    }

    public void clearMessageField() {
        etMessage.getText().clear();
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btnSendMsg:
                    try {
                        ((MainActivity) getActivity()).getController().sendMessage(etMessage.getText().toString());
                    }catch(Exception e){

                    }
                    break;
                case R.id.imCamera:
                    ((MainActivity)getActivity()).getController().startCamera();
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).getController().postMessages();
        super.onResume();

    }

    public void setData(ArrayList<TextMessage> txtMessage){
        ((ChatAdapter)recyclerView.getAdapter()).setData(txtMessage);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public class SpinnerListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            ((MainActivity)getActivity()).getController().updateChatGroup(adapterView.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


}
