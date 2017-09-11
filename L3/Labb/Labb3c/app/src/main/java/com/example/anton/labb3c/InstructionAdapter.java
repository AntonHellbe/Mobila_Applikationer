package com.example.anton.labb3c;

import android.content.Context;
import android.support.annotation.IdRes;
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
 * Created by Anton on 2017-09-11.
 */

public class InstructionAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;


    public InstructionAdapter(@NonNull Context context, @NonNull String[] objects) {
        super(context, R.layout.row_layout, objects);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView tvRow;
        if(convertView == null){
            convertView = (LinearLayout) inflater.inflate(R.layout.row_layout, parent, false);
        }
        tvRow = (TextView) convertView.findViewById(R.id.tvRow);
        tvRow.setText(this.getItem(position));
        return convertView;
    }
}
