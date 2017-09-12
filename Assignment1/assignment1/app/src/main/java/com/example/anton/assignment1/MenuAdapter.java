package com.example.anton.assignment1;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Anton on 2017-09-12.
 */

public class MenuAdapter extends ArrayAdapter<String> {

    private LayoutInflater layoutInflater;
    private final Integer[] imgId;

    public MenuAdapter(@NonNull Context context, @NonNull String[] objects, Integer[] imgId) {
        super(context, R.layout.row_layout, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        this.imgId = imgId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = (LinearLayout) layoutInflater.inflate(R.layout.row_layout, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tvContent);
            holder.imageView = (ImageView) convertView.findViewById(R.id.ivImage);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.textView.setText(this.getItem(position));
        holder.imageView.setImageResource(imgId[position]);
        return convertView;

    }

    class ViewHolder{

        TextView textView;
        ImageView imageView;
    }
}
