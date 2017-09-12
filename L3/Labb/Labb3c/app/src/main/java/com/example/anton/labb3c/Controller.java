package com.example.anton.labb3c;

import android.content.res.Resources;
import android.renderscript.ScriptGroup;

/**
 * Created by Anton on 2017-09-11.
 */

public class Controller {

    private InputFragment inputFragment;
    private ViewerFragment viewerFragment;
    private MainActivity mainActivity;
    private Instruction[] instructions = new Instruction[3];
    private int index = 0;


    public Controller(MainActivity mainActivity){
        this.inputFragment = new InputFragment();
        this.viewerFragment = new ViewerFragment();
        this.mainActivity = mainActivity;
        inputFragment.setController(this);
        viewerFragment.setController(this);
        initializeResources();

        mainActivity.setFragment(this.inputFragment, false);
        //inputFragment.setController(this);
    }

    private void initializeResources() {
        Resources res = mainActivity.getResources();
        instructions[0] = new Instruction(res.getString(R.string.content), res.getString(R.string.what_to_do));
        instructions[1] = new Instruction(res.getString(R.string.content2), res.getString(R.string.what_to_do2));
        instructions[2] = new Instruction(res.getString(R.string.content3), res.getString(R.string.what_to_do3));
    }

    public void updateFragment(int position) {
        mainActivity.setFragment(viewerFragment, true);
        index = position;
    }

    public void updateViewer() {
        viewerFragment.setTvWhatToDo(instructions[index].getWhatToDo());
        viewerFragment.setTvContent(instructions[index].getContent());
    }
}
