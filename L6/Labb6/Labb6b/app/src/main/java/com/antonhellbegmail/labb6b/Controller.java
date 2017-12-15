package com.antonhellbegmail.labb6b;

import android.graphics.Color;

/**
 * Created by Anton on 2017-09-25.
 */

public class Controller {

    private MainActivity mainActivity;
    private Colorize colorize;
    private int index = 0;
    private int[] colors = {Color.BLUE,Color.CYAN,Color.DKGRAY, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.RED,Color.YELLOW, Color.TRANSPARENT};
    private final int SLEEP = 50;

    public Controller(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void updateTv1(int color){
        mainActivity.setTv1Color(color);
    }
    public void updateTv2(int color){
        mainActivity.setTv2Color(color);
    }

    public void clearTv1(){
        mainActivity.setTv1Color(Color.TRANSPARENT);
    }

    public void clearTv2(){
        mainActivity.setTv2Color(Color.TRANSPARENT);
    }

    public void startThreadColorize(){
        colorize = new Colorize();
        colorize.start();
    }

    private class Colorize extends Thread {

        @Override
        public void run() {
            index = index % colors.length;
            mainActivity.runOnUiThread(new SetColor(colors[index]));
            try{
                sleep(SLEEP);
            }catch(InterruptedException e){

            }
            index++;
            super.run();
        }

        private class SetColor implements Runnable{

            private int color = 0;

            public SetColor(int color){
                this.color = color;
            }

            @Override
            public void run() {
                if(color != 0){
                    updateTv1(color);
                    updateTv2(color);
                }


            }

        }


    }



}
