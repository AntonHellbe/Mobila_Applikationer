package com.example.anton.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Anton on 2017-09-12.
 */

public class UserActivity extends AppCompatActivity {

    private TextView tvUsername;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userid");
        //this.tvUsername = (TextView) findViewById(R.id.tvUsername);
        //tvUsername.setText(userName);

    }


}
