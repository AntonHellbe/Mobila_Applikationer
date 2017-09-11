package com.antonhellbegmail.howto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Controller cont;
    private Button btnNext, btnPrev;
    private TextView tvWhatToDo;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cont = new Controller(this);
        initializeComponents();
        registerListeners();
    }

    public void initializeComponents(){
        btnNext = (Button) findViewById(R.id.btn1);
        btnPrev = (Button) findViewById(R.id.btn2);
        tvWhatToDo = (TextView) findViewById(R.id.textView1);
        tvContent = (TextView) findViewById(R.id.textView2);
        cont.nextClick();
    }

    public void registerListeners(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont.nextClick();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                cont.previousClick();
            }
        });

    }


    public void setContent(String updatecontent){
        tvContent.setText(updatecontent);
    }

    public void setWhatToDo(String whatToDo){
        tvWhatToDo.setText(whatToDo);
    }
}
