package se.mah.tsroax.f4startactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HighscoreActivity extends Activity {
    private ListView lvHighscore;
    private Score[] scores;
    private Parcelable[] parcelables;
    private String[] names;
    private int[] points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        lvHighscore=(ListView)findViewById(R.id.lvHighscore);

        Intent intent = getIntent();
        names = intent.getStringArrayExtra(MainActivity.NAMES);
        points = intent.getIntArrayExtra(MainActivity.POINTS);
        if(names==null) {
            parcelables = intent.getParcelableArrayExtra(MainActivity.SCORES);
            scores = new Score[parcelables.length];
            for(int i=0; i<scores.length; i++) {
                scores[i] = (Score)parcelables[i];
            }
        } else {
            scores = new Score[names.length];
            for(int i=0; i<names.length; i++) {
                scores[i] = new Score(names[i],points[i]);
            }
        }

        lvHighscore.setAdapter(new HighscoreAdapter(this,scores));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_highscore, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class HighscoreAdapter extends ArrayAdapter<Score> {
        private LayoutInflater inflater;

        public HighscoreAdapter(Context context, Score[] scores) {
            super(context,R.layout.highscore_row,scores);
            inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
