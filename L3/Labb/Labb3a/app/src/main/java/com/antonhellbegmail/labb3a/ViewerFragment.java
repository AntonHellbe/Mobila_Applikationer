package com.antonhellbegmail.labb3a;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Anton on 2017-09-11.
 */

public class ViewerFragment extends Fragment {

    private TextView tvColor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.fragment_viewer, container, false);
        initializeComponents(rootView);
        return rootView;
    }

    private void initializeComponents(View view) {
        tvColor = (TextView) view.findViewById(R.id.textView);
    }

    public void setTvColor(String text){
        tvColor.setText(text);
    }

    public void setColor(int color){
        tvColor.setBackgroundColor(color);
    }

}
