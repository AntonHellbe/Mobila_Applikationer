package com.antonhellbegmail.labb2c;

import android.app.Fragment;
import android.content.res.Resources;

/**
 * Created by Anton on 2017-09-07.
 */

public class Controller {

    private Instruction[] instructions = new Instruction[3];
    private int index = 0;
    private InputFragment frag;

    public Controller(InputFragment ui){
        this.frag = ui;
        initializeResources();
    }

    public void initializeResources() {
        Resources res = frag.getActivity().getResources();
        String whatToDo = res.getString(R.string.whattodo);
        String content = res.getString(R.string.content);
        instructions[0] = new Instruction(whatToDo, content);
        instructions[1] = new Instruction(res.getString(R.string.whattodo2), res.getString(R.string.content2));
        instructions[2] = new Instruction(res.getString(R.string.whattodo3), res.getString(R.string.content3));
    }


    public void previousClick(){
        index--;
        if(index < 0){
            index = instructions.length - 1;
        }
        frag.setWhatToDo(instructions[index].getWhatToDo());
        frag.setContent(instructions[index].getWhatToDo());
    }

    public void nextClick(){
        index++;
        if(index >= instructions.length){
            index = 0;
        }
        frag.setWhatToDo(instructions[index].getWhatToDo());
        frag.setContent(instructions[index].getContent());
    }

}
