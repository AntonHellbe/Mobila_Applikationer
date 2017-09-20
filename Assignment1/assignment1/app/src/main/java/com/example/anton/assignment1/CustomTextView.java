package com.example.anton.assignment1;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import static java.lang.Float.parseFloat;


/**
 * Created by Anton on 2017-09-20.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

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
    public void setBackgroundColor(@ColorInt int color) {
        super.setBackgroundColor(color);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        String temp = text.toString();
        try {
            if(parseFloat(temp) >= 0){
                super.setTextColor(Color.GREEN);
            }else{
                super.setTextColor(Color.RED);
            }
            super.setText(text, type);
        }catch(Exception e){
            super.setText(text, type);
        }
    }




}
