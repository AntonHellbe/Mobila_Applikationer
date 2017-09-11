package com.example.anton.labb3c;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Anton on 2017-09-11.
 */

public class InputFragment extends Fragment {

    private Controller controller;
    private ListView listView;
    private String[] content = {"Att starta en Activity", "Att lägga till data i en Intent", "Avläsa data i en ny Activity"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewer, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners() {
        listView.setOnItemClickListener(new ListViewListener());
    }

    private void initializeComponents(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.lView);
        listView.setAdapter(new InstructionAdapter(getActivity(), content));
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }

    private class ListViewListener implements android.widget.AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            controller.updateFragment(position);
        }
    }
}
