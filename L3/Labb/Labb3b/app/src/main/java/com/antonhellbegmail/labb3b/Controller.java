package com.antonhellbegmail.labb3b;



/**
 * Created by Anton on 2017-09-11.
 */

public class Controller {
    private InputFragment inputFragment;
    private ViewerFragment viewerFragment;

    public Controller(InputFragment inputFragment, ViewerFragment viewerFragment){
        this.inputFragment = inputFragment;
        this.viewerFragment = viewerFragment;
        inputFragment.setController(this);
    }

}
