package com.antonhellbegmail.labb3a;

import android.graphics.Color;
import android.view.View;

/**
 * Created by Anton on 2017-09-11.
 */

public class Controller {

    private ViewerFragment viewerFragment;
    private InputFragment inputFragment;

    public Controller(ViewerFragment viewerFragment, InputFragment inputFragment){
        this.viewerFragment = viewerFragment;
        this.inputFragment = inputFragment;
        this.inputFragment.setController(this);
    }

    public void setBackGround(String color){
        viewerFragment.setColor(Color.BLUE);
        viewerFragment.setTvColor(color);
    }

    public void setButtonText(String text){
        inputFragment.setButtonText(text);
    }

    public void setColor(String color){
        switch(color){
            case "BLUE":
                viewerFragment.setColor(Color.BLUE);
                break;
            case "GREEN":
                viewerFragment.setColor(Color.GREEN);
                break;
            case "RED":
                viewerFragment.setColor(Color.RED);
                break;
            case "BLACK":
                viewerFragment.setColor(Color.BLACK);
                break;
        }
    }
}
