package com.antonhellbegmail.labb3b;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Anton on 2017-09-11.
 */

public class InputFragment extends Fragment {

    private Controller cont;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_input, container, false);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    private void registerListeners() {
    }

    private void initializeComponents(View view) {
        ListView listView = (ListView) view.findViewById(R.id.lView);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, content));
    }

    public void setController(Controller cont){
        this.cont = cont;
    }
}
