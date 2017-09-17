package com.example.ag6505.sql;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        lvHighscore=(ListView)findViewById(R.id.lvHighscore);

        Intent intent = getIntent();
        new HighscoreController(this,intent);
    }

    public void setAdapter(ArrayAdapter<Score> highscoreAdapter) {
        lvHighscore.setAdapter(highscoreAdapter);
    }
}
