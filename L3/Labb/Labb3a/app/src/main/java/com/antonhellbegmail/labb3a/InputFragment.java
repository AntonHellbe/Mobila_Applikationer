package com.antonhellbegmail.labb3a;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import static com.antonhellbegmail.labb3a.R.layout.rowstyle;

public class InputFragment extends Fragment {

    private Controller controller;
    private Button colorButton;
    private ListView listView;

    private String[] content = {"RED", "BLUE", "GREEN", "BLACK"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.fragment_input, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setButtonText(String text){
        colorButton.setText(text);
    }

    private void registerListeners() {
        listView.setOnItemClickListener(new ListViewListener());
        colorButton.setOnClickListener(new buttonListener());

    }

    private void initializeComponents(View view) {

        colorButton = (Button) view.findViewById(R.id.btnColor);

        listView = (ListView) view.findViewById(R.id.lView);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, content));
    }


    private class ListViewListener implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView tv = (TextView)view;
            controller.setButtonText(tv.getText().toString());
        }
    }

    private class buttonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            controller.setColor(colorButton.getText().toString());
        }
    }




}
