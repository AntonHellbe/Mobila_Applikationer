package com.example.ag6505.sql;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by tsroax on 08/09/15.
 */
public class HighscoreController {
    private HighscoreActivity activity;
    private Score[] scores;
    private Parcelable[] parcelables;

    public HighscoreController(HighscoreActivity activity, Intent intent) {
        this.activity = activity;

        parcelables = intent.getParcelableArrayExtra(MainController.SCORES);
        scores = new Score[parcelables.length];
        for(int i=0; i<scores.length; i++) {
            scores[i] = (Score)parcelables[i];
        }
        activity.setAdapter(new HighscoreAdapter(activity,scores));
    }

    private class HighscoreAdapter extends ArrayAdapter<Score> {
        private LayoutInflater inflater;

        public HighscoreAdapter(Context context, Score[] scores) {
            super(context,R.layout.highscore_row,scores);
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Score score = getItem(position);
            ViewHolder holder;
            if(convertView==null) {
                convertView = inflater.inflate(R.layout.highscore_row,parent,false);
                holder = new ViewHolder();
                holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
                holder.tvPoints = (TextView)convertView.findViewById(R.id.tvPoints);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tvName.setText(score.getName());
            holder.tvPoints.setText(""+score.getPoints());
            return convertView;
        }
    }

    private class ViewHolder {
        private TextView tvName;
        private TextView tvPoints;
    }
}
