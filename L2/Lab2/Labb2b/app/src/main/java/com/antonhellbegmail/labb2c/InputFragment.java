package com.antonhellbegmail.labb2c;

import android.app.Fragment;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class InputFragment extends Fragment{

    private Controller cont;
    private Button btnNext, btnPrev;
    private TextView tvWhatToDo;
    private TextView tvContent;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View rootView = inflater.inflate(R.layout.fragment_viewer, container, false);
        cont = new Controller(this);
        initializeComponents(rootView);
        registerListeners();
        return rootView;
    }

    public void initializeComponents(View rootView){
        btnNext = (Button) rootView.findViewById(R.id.btn1);
        btnPrev = (Button) rootView.findViewById(R.id.btn2);
        tvWhatToDo = (TextView) rootView.findViewById(R.id.textView1);
        tvContent = (TextView) rootView.findViewById(R.id.textView2);
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
