package com.antonhellbegmail.labb3b;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Anton on 2017-09-11.
 */

public class ViewerFragment extends Fragment {

    private TextView tvContent, tvWhatToDo;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewer, container, false);
        initializeComponents(rootView);
        return rootView;
    }

    private void initializeComponents(View rootView) {
        tvContent = (TextView) rootView.findViewById(R.id.tvContent);
        tvWhatToDo = (TextView) rootView.findViewById(R.id.tvWhatToDo);
    }


    public void setTvContent(String text){
        tvContent.setText(text);
    }

    public void setTvWhatToDo(String text){
        tvWhatToDo.setText(text);
    }
}
