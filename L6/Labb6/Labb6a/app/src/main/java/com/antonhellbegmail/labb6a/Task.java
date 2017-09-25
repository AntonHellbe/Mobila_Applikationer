package com.antonhellbegmail.labb6a;

import android.graphics.Color;
import android.os.AsyncTask;

/**
 * Created by Anton on 2017-09-25.
 */

public class Task extends AsyncTask<Integer, Integer, Integer> {

    private int position;
    private boolean running;
    private int[] colors = {Color.BLUE,Color.CYAN,Color.DKGRAY, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.RED,Color.YELLOW, Color.TRANSPARENT};
    private Controller controller;


    public void setController(Controller controller){
        this.controller = controller;
    }


    @Override
    protected Integer doInBackground(Integer... params) {
        position = params[0];
        int times = params[1];
        int pause = params[2];
        int tv = params[3];

        while(running && position < times){
            try{
                Thread.sleep(pause);
            }catch(InterruptedException e){

            }
            position++;
            publishProgress(position, tv);
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        running = true;
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        controller.clearTv1();
        controller.clearTv2();
        super.onPostExecute(integer);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        switch(values[1]){
            case 0:
                controller.updateTv1(colors[values[0] % colors.length]);
                break;
            case 1:
                controller.updateTv2(colors[values[0] % colors.length]);
                break;
        }
        super.onProgressUpdate(values);
    }
}
