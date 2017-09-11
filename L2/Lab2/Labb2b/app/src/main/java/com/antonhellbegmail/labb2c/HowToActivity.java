package com.antonhellbegmail.labb2c;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HowToActivity extends AppCompatActivity {

    private Controller cont;
    private Button btnNext, btnPrev;
    private TextView tvWhatToDo;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
    }


    //    public void setContent(String updatecontent){
    //    tvContent.setText(updatecontent);
    //}

    //public void setWhatToDo(String whatToDo){
     //   tvWhatToDo.setText(whatToDo);
    //}
}
