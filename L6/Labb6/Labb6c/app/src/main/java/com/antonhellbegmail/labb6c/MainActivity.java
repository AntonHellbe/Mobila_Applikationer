package com.antonhellbegmail.labb6c;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private CustomTextView customTextView;
    private Button btn1;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponenets();
        registerListeners();
    }

    private void registerListeners() {
        btn1.setOnClickListener(new ButtonListener());
    }

    private void initializeComponenets() {
        btn1 = (Button) findViewById(R.id.btn1);
        customTextView = (CustomTextView) findViewById(R.id.tv1);
    }



    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btn1:
                    startParty();
                    break;
            }
        }
    }

    public void startParty(){
        customTextView.colorize();
    }
}
