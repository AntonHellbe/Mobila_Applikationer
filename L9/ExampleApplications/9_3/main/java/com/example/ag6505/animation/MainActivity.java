package com.example.ag6505.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private boolean image=false;
    private float dx = -200;
    private ImageView imageView;
    private TextView textView;
//    private ImageView ivDrawable;
    private AnimationDrawable animDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        imageView = (ImageView)findViewById(R.id.imageView1);
        textView = (TextView)findViewById(R.id.textView1);
        ImageView ivDrawable = (ImageView)findViewById(R.id.imageView2);
        ivDrawable.setBackgroundResource(R.drawable.new_animation);
        animDrawable = (AnimationDrawable)ivDrawable.getBackground();
    }

    public void startAnimation(View view){
        View animationView;
        if(image)
            animationView = imageView;
        else
            animationView = textView;
        image = !image;
        switch(view.getId()) {
//            case R.id.btnRotate:rotate(animationView); break;
            case R.id.btnRotate:rotateWithXml(animationView); break;
            case R.id.btnRotateAndMove:rotateAndMove(animationView); break;
            case R.id.btnFade: fade(animationView); break;
            case R.id.btnNew: drawableAnim(); break;
        }
    }

    private void rotate(View view) {
        float start, end;
        if(view.getRotation()<180) {
            start = 0;
            end = 360;
        } else {
            start = 360;
            end = 0;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",start,end);
        animator.setDuration(2000);
        animator.start();
    }

    private void rotateAndMove(View view) {
        float start, end, x1, x2;
        if(view.getRotation()<180) {
            start = 0;
            end = 360;
        } else {
            start = 360;
            end = 0;
        }
        x1 = view.getX();
        if(x1<200 )  {
            dx = 200;
        } else if(x1>600) {
            dx = -200;
        }
        x2 = x1 + dx;

        ObjectAnimator rotate = ObjectAnimator.ofFloat(view,"rotation",start,end);
        ObjectAnimator move = ObjectAnimator.ofFloat(view,"x",x1,x2);
        AnimatorSet set = new AnimatorSet();
        rotate.setDuration(2000);
        move.setDuration(4000);
        set.playTogether(rotate, move);
        set.start();
    }

    private void fade(View view) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view,"alpha",1f,0f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view,"alpha",0f,1f);
        fadeOut.setDuration(2000);
        fadeIn.setDuration(1000);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(fadeOut, fadeIn);
        set.start();
    }

    private void rotateWithXml(View view) {
        AnimatorSet set;
        if(view.getRotation()<180)
            set = (AnimatorSet)AnimatorInflater.loadAnimator(this,R.animator.rotate_pos);
        else
            set = (AnimatorSet)AnimatorInflater.loadAnimator(this,R.animator.rotate_neg);
        set.setTarget(view);
        set.setDuration(2000);
        set.start();
    }

    private void drawableAnim() {
        animDrawable.stop();
        animDrawable.start();
    }
}
