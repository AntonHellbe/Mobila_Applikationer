package com.example.anton.labb3c;

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
    private Controller controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewer, container, false);
        initializeComponents(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.updateViewer();
    }


    private void initializeComponents(View view) {
        tvContent = (TextView) view.findViewById(R.id.tvContent);
        tvWhatToDo = (TextView) view.findViewById(R.id.tvWhatToDo);
    }

    public void setTvContent(String text){
        tvContent.setText(text);
    }

    public void setTvWhatToDo(String text){
        tvWhatToDo.setText(text);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
