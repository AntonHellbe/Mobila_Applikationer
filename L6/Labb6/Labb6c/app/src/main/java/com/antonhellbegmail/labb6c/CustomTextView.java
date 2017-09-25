package com.antonhellbegmail.labb6c;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Anton on 2017-09-25.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        super.setBackgroundColor(color);
    }

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    public void colorize(){
        Task task = new Task();
        task.execute(0, 20, 100);
    }


    private class Task extends AsyncTask<Integer, Integer, Integer> {
        private int[] colors = {Color.BLUE,Color.CYAN,Color.DKGRAY, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.RED,Color.YELLOW, Color.TRANSPARENT};

        private int position;
        private boolean running = false;

        public Task() {
            super();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            position = integers[0];
            int pause = integers[1];
            int times = integers[2];
            while(running && position < times){
                try{
                    Thread.sleep(pause);

                }catch(InterruptedException e){

                }
                publishProgress(position);
                position++;

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
            running = false;
            super.onPostExecute(integer);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            setBackgroundColor(colors[values[0] % colors.length]);
            super.onProgressUpdate(values);
        }
    }
}
