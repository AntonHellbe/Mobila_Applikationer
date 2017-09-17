package com.example.ag6505.filesystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
