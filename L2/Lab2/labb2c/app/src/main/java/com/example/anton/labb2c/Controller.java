package com.example.anton.labb2c;

import android.content.res.Resources;

/**
 * Created by Anton on 2017-09-08.
 */

public class Controller {

    //private InputFragment inputFragment;
    private ViewerFragment viewerFragment;
    private Instruction[] instructions = new Instruction[3];
    private int index = 0;

    public Controller(ViewerFragment viewerFragment){
        this.viewerFragment = viewerFragment;
        initializeResources();
    }

    public void initializeResources() {
        Resources res = viewerFragment.getActivity().getResources();
        String whatToDo = res.getString(R.string.what_to_do);
        String content = res.getString(R.string.content);
        instructions[0] = new Instruction(whatToDo, content);
        instructions[1] = new Instruction(res.getString(R.string.what_to_do2), res.getString(R.string.content2));
        instructions[2] = new Instruction(res.getString(R.string.what_to_do3), res.getString(R.string.content3));
    }

    public void nextClick(){
        index++;
        if(index >= instructions.length - 1){
            index = 0;
        }
        viewerFragment.setTvContent(instructions[index].getContent());
        viewerFragment.setTvWhatToDo(instructions[index].getWhatToDo());
    }

    public void previousClick(){
        index--;
        if(index < 0){
            index = instructions.length - 1;
        }
        viewerFragment.setTvContent(instructions[index].getContent());
        viewerFragment.setTvWhatToDo(instructions[index].getWhatToDo());
    }

}
