package com.antonhellbegmail.labb6a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv1, tv2;
    private Button btn1, btn2;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        registerListeners();
        controller = new Controller(this);
    }

    private void registerListeners() {
        btn1.setOnClickListener(new ButtonListener());
        btn2.setOnClickListener(new ButtonListener());
    }

    private void initializeComponents() {
        btn1 = (Button) findViewById(R.id.btn1);
        tv1 = (TextView) findViewById(R.id.tv1);
        btn2 = (Button) findViewById(R.id.btn2);
        tv2 = (TextView) findViewById(R.id.tv2);
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btn1:
                    controller.startAsyncTask(2, 30, 100, 0);
                    break;
                case R.id.btn2:
                    controller.startAsyncTask(0, 10, 400, 1);
                    break;
            }
        }
    }

    public void setTv1Color(int color){
        tv1.setBackgroundColor(color);
    }

    public void setTv2Color(int color){
        tv2.setBackgroundColor(color);
    }


}
