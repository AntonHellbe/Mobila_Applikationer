package com.antonhellbegmail.labb6d;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Anton on 2017-09-25.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    private int index = 0;
    private int[] colors = {Color.BLUE,Color.CYAN,Color.DKGRAY, Color.GRAY, Color.GREEN, Color.MAGENTA, Color.RED,Color.YELLOW, Color.TRANSPARENT};
    private Paint paint;

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint();
        paint.setColor(colors[index % colors.length]);
        index++;
        canvas.drawPaint(paint);
        super.onDraw(canvas);
    }

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startThread(){
        DiscoThread discoThread = new DiscoThread();
        Intent intent = new Intent();
        intent.putExtra("times", 500);
        discoThread.setMyIntent(intent);
        discoThread.start();
    }

    private void update(){
        post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }



    private class DiscoThread extends Thread {

        private Intent myIntent;

        public void setMyIntent(Intent intent){
            myIntent = intent;
        }

        @Override
        public void run() {
            for(int i = 0; i < myIntent.getIntExtra("times", 0); i++){
                try{
                    update();
                    Thread.sleep(25);
                }catch (InterruptedException e){}

            }
        }
    }

}
