package com.antonhellbegmail.loginapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button signup, login;
    private EditText email, password;
    private SeekBar agebar;
    private TextView output;
    private ArrayList<String> pws = new ArrayList<>();
    private ArrayList<String> emailName = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.output);
        signup = (Button) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        agebar = (SeekBar) findViewById(R.id.agebar);

        agebar.setMax(250);
        agebar.setProgress(0);

        output.setText("Age: " + Integer.toString(agebar.getProgress()));


        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    emailName.add(email.getText().toString());
                    pws.add(password.getText().toString());
                    Toast.makeText(MainActivity.this, "Email: " + emailName.get(emailName.size() - 1), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Password: " + pws.get(pws.size() - 1), Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    // We dont want any errors pls
                    Toast.makeText(MainActivity.this, (CharSequence) e, Toast.LENGTH_SHORT).show();
                }


            }
        });


        login.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                try{
                    if(emailName.indexOf(email.getText().toString()) != -1 && pws.indexOf(password.getText().toString()) != -1) {
                    //if(email.getText().toString().equals(emailName) && password.getText().toString().equals(pw)){
                      Toast.makeText(MainActivity.this, "You are now logged in", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Credentials are not correct!", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "We shouldnt be here! You did something wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });

        agebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progressChanged = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                output.setText("Age: " + Integer.toString(agebar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "Age selected : " + Integer.toString(agebar.getProgress()), Toast.LENGTH_SHORT).show();

            }
        });

    }



}
