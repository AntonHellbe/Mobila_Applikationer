package com.example.anton.labb2c;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Anton on 2017-09-08.
 */

public class ViewerFragment extends Fragment {

    private TextView tvContent, tvWhatToDo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_viewer, container, false);
        initalizeComponents(rootView);
        return rootView;
    }

    public void initalizeComponents(View rootView) {
        tvContent = (TextView) rootView.findViewById(R.id.textView);
        tvWhatToDo = (TextView) rootView.findViewById(R.id.textView2);
    }

    public void setTvContent(String text){
        tvContent.setText(text);
    }

    public void setTvWhatToDo(String text){
        tvWhatToDo.setText(text);
    }

}
