package com.example.anton.labb3c;

import android.content.res.Resources;
import android.renderscript.ScriptGroup;

/**
 * Created by Anton on 2017-09-11.
 */

public class Controller {

    private InputFragment inputFragment;
    private ViewerFragment viewerFragment;
    private Instruction[] instructions = new Instruction[3];

    public Controller(ViewerFragment viewerFragment, InputFragment inputFragment){
        this.inputFragment = inputFragment;
        this.viewerFragment = viewerFragment;
        inputFragment.setController(this);
        initializeResources();
    }

    private void initializeResources() {
        Resources res = viewerFragment.getActivity().getResources();
        instructions[0] = new Instruction(res.getString(R.string.content), res.getString(R.string.what_to_do));
        instructions[1] = new Instruction(res.getString(R.string.content2), res.getString(R.string.what_to_do2));
        instructions[2] = new Instruction(res.getString(R.string.content3), res.getString(R.string.what_to_do3));
    }

    public void updateFragment(int position) {

    }
}
