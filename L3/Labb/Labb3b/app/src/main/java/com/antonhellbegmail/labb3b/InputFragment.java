package com.antonhellbegmail.labb3b;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Anton on 2017-09-11.
 */

public class InputFragment extends Fragment {

    private Controller cont;
    private String[] content = {"Att starta en Activity", "Att lägga till data i en Intent", "Avläsa data i en ny Activity"};
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_input, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners() {
        listView.setOnItemClickListener(new ListViewListener());
    }

    private void initializeComponents(View view) {
        listView = (ListView) view.findViewById(R.id.lView);
        listView.setAdapter(new InstructionAdapter(getActivity(), content));
    }

    public void setController(Controller cont){
        this.cont = cont;
    }

    private class ListViewListener implements android.widget.AdapterView.OnItemClickListener{

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            cont.updateChoice(position);
        }
    }
}
