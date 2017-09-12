package com.example.anton.assignment1;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Anton on 2017-09-12.
 */

public class FinanceAdapter extends ArrayAdapter<String> {

    private LayoutInflater layoutInflater;

    public FinanceAdapter(@NonNull Context context, @NonNull String[] objects) {
        super(context, R.layout.row_layout2, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView rowContent;
        if(convertView == null){
            convertView = (LinearLayout) layoutInflater.inflate(R.layout.row_layout2, parent, false);
        }
        rowContent = (TextView) convertView.findViewById(R.id.tvRowContent);
        rowContent.setText(this.getItem(position));
        return convertView;
    }
}
