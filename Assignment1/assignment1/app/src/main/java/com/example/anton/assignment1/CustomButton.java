package com.example.anton.assignment1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Anton on 2017-09-19.
 */

public class CustomButton extends android.support.v7.widget.AppCompatButton {

    private static Paint paint;

    public CustomButton(Context context) {
        super(context);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(paint);

    }


    public void init(){
        if(paint == null) {
            paint = new Paint();
            paint.setColor(getResources().getColor(android.R.color.transparent));
//            paint.setAntiAlias(true);
////            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//            paint.setAntiAlias(true);
//
//
//            //paint.setAlpha(255);
        }

    }


}

